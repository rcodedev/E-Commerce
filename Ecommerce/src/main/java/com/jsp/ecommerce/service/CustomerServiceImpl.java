package com.jsp.ecommerce.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dao.ProductDao;
import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.dto.PaymentDto;
import com.jsp.ecommerce.entity.Cart;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.CustomerOrder;
import com.jsp.ecommerce.entity.Item;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.exception.OutOfStockException;
import com.jsp.ecommerce.mapper.ProductMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	

	private final ProductDao productDao;
	private final ProductMapper productMapper;
	private final UserDao userDao;
	@Value("${razaorpay.key}")
	private String razorPayKey;
	@Value("${razaorpay.secret}")
	private String razorPaySecret;

	@Override
	public Map<String, Object> getProducts(int page, int size, String sort, boolean desc, String name, String category,
			String lowerRange, String higherRange) {
		List<Product> products = new ArrayList<Product>();
		if (name == null && category == null && lowerRange == null && higherRange == null) {
			products = productDao.getAllApprovedProducts(page, size, sort, desc);
		} else {
			Set<Product> set = new HashSet<Product>();
			if (name != null)
				set.addAll(productDao.getAllProductByName(name, page, size, sort, desc));
			if (category != null)
				set.addAll(productDao.getAllProductByCategory(category, page, size, sort, desc));
			if (lowerRange != null && higherRange != null)
				set.addAll(productDao.getAllProductByPrice(Double.parseDouble(lowerRange),
						Double.parseDouble(higherRange), page, size, sort, desc));

			products.addAll(set);
		}
		if(products == null)
			throw new NoSuchElementException("No Products Found .");
		return Map.of("message", "Products Found", "products", productMapper.toProductDtoList(products));
	}
	
	

	@Override
	public Map<String, Object> addToCart(Long id, String email, String size) {
		Product product = productDao.getProductById(id);
		Customer customer = userDao.findCustomerByEmail(email);
		if (product.getStock() <= 0)
			throw new OutOfStockException(product.getName() + " is Out of Stock");
		if (!product.getSize().contains(size))
			throw new OutOfStockException(size + " is Out of Stock");
		Cart cart = customer.getCart();
		if (cart == null) {
			cart = new Cart();
			customer.setCart(cart);
		}
		List<Item> items = cart.getItems();
		if (items == null) {
			items = new ArrayList<Item>();
			cart.setItems(items);
		}

		if (items.isEmpty()) {
			Item item = new Item();
			item.setProduct(product);
			item.setQuantity(1);
			item.setSize(size);
			items.add(item);
		} else {
			boolean flag = true;
			for (Item item : items) {
				if (item.getProduct().getId() == product.getId()) {
					item.setQuantity(item.getQuantity() + 1);
					item.setSize(size);
					flag = false;
					break;
				}
			}
			if (flag) {
				Item item = new Item();
				item.setProduct(product);
				item.setQuantity(1);
				item.setSize(size);
				items.add(item);
			}
		}
		product.setStock(product.getStock() - 1);
		productDao.save(product);
		userDao.save(customer);
		return Map.of("message", product.getName() + " added to Cart Success", "product",
				productMapper.toProductDto(product));
	}

	@Override
	public Map<String, Object> viewCart(String email) {
		Customer customer = userDao.findCustomerByEmail(email);
		Cart cart = customer.getCart();
		if (cart == null)
			throw new NoSuchElementException("No Items in Cart");
		List<Item> items = cart.getItems();
		if (items.isEmpty())
			throw new NoSuchElementException("No Items in Cart");
		return Map.of("message", "Cart Found Success", "items", productMapper.toItemsDtoList(items));
	}

	@Override
	public Map<String, Object> removeFromCart(Long id, String email) {
		Customer customer = userDao.findCustomerByEmail(email);
		Product product = productDao.getProductById(id);

		Cart cart = customer.getCart();
		if (cart == null)
			throw new NoSuchElementException(product.getName() + " is Not in Cart");
		List<Item> items = cart.getItems();
		if (items.isEmpty())
			throw new NoSuchElementException(product.getName() + " is Not in Cart");
		boolean flag = false;

		for (Item item : items) {
			if (item.getProduct().getId() == id) {
				if (item.getQuantity() > 1) {
					item.setQuantity(item.getQuantity() - 1);
					productDao.saveItem(item);
				} else {
					items.remove(item);
					userDao.save(customer);
					productDao.deleteItem(item);
				}
				flag = true;
				break;
			}
		}
		if (!flag)
			throw new NoSuchElementException(product.getName() + " is Not in Cart");
		product.setStock(product.getStock() + 1);
		productDao.save(product);
		return Map.of("message", "Product Removed From cart Success", "product", productMapper.toProductDto(product));
	}
	
	
	
	@Override
	public Map<String, Object> buyFromCart(String email, String address) {
		Customer customer = userDao.findCustomerByEmail(email);
		Cart cart = customer.getCart();
		if (cart == null)
			throw new NoSuchElementException("No Items in Cart");
		List<Item> items = cart.getItems();
		if (items.isEmpty())
			throw new NoSuchElementException("No Items in Cart");

		double amount = items.stream().mapToDouble(x -> x.getQuantity() * x.getProduct().getPrice()).sum();
		String orderId = null;
		try {
			RazorpayClient client = new RazorpayClient(razorPayKey, razorPaySecret);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", amount * 100);
			jsonObject.put("currency", "INR");

			Order order = client.orders.create(jsonObject);
			orderId = order.get("id");

		} catch (RazorpayException e) {
			e.printStackTrace();
			throw new RuntimeException("Something Went Wrong");
		}

		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setAddress(address);
		customerOrder.setAmount(amount);
		customerOrder.setCustomer(customer);

		List<Item> orderItems = new ArrayList<Item>();
		for (Item item : items) {
			Item item2 = new Item();
			item2.setProduct(item.getProduct());
			item2.setQuantity(item.getQuantity());
			item2.setSize(item.getSize());
			orderItems.add(item2);
		}
		customerOrder.setItems(orderItems);
		customerOrder.setOrderId(orderId);

		userDao.saveOrder(customerOrder);

		PaymentDto paymentDto = new PaymentDto(razorPayKey, amount * 100, "INR", orderId, customer.getName(), email,
				customer.getUser().getMobile(), "/customers/confirm-payment/" + customerOrder.getId());

		return Map.of("message", "Order Created Success Make Payment to Place Order", "order", paymentDto);
	}

	@Override
	public Map<String, Object> confirmPayment(Long id, String razorpay_payment_id) {
		CustomerOrder order = userDao.getOrder(id);
		order.setPaymentId(razorpay_payment_id);
		order.setPaymentStatus(true);
		userDao.saveOrder(order);
		Customer customer = order.getCustomer();
		List<Item> items = customer.getCart().getItems();
		customer.getCart().setItems(new ArrayList<Item>());
		userDao.save(customer);
		productDao.deleteItems(items);
		return Map.of("message", "Payment Success Order Placed", "order", productMapper.toOrderDto(order));
	}
	
	@Override
	public Map<String, Object> getAllOrders(String email) {
		Customer customer = userDao.findCustomerByEmail(email);
		
		List<CustomerOrder> orders=userDao.getAllOrders(customer);
		return Map.of("message","Orders Found","orders",productMapper.toOrderDtos(orders));
	}
}
