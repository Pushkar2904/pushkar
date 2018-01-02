package answer.king.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import answer.king.model.Item;
import answer.king.model.LineItem;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.OrderRepository;
import answer.king.repo.ReceiptRepository;
import answer.king.util.InsufficientFundsException;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ReceiptRepository receiptRepository;
	
	
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	//Updated logic to increment quantity for the same item
	public Order addItem(Long id, Long itemId, Long quantity) {
		Order order = orderRepository.findOne(id);
		Item item = itemRepository.findOne(itemId);
		
		if(order.getLineItems() != null && !order.getLineItems().isEmpty() ) {			
			boolean containsItem = Boolean.FALSE;
			for(LineItem lineItem : order.getLineItems()) {
				if(lineItem.getItem().getId() == itemId) {
					lineItem.setQuantity(lineItem.getQuantity() + quantity);
					lineItem.setOrder(order);
					containsItem = Boolean.TRUE;
					break;
				}
			}
			if(!containsItem) {
				LineItem lineItem = new LineItem();
				lineItem.setItem(item);
				lineItem.setQuantity(quantity);		
				lineItem.setOrder(order);
				order.getLineItems().add(lineItem);
			}			
		}
		else {			
			LineItem lineItem = new LineItem();
			lineItem.setItem(item);
			lineItem.setQuantity(quantity);
			lineItem.setOrder(order);
			List<LineItem> lineItems = Lists.newArrayList();			
			lineItems.add(lineItem);			
			order.setLineItems(lineItems);
		}
		
		return orderRepository.save(order);		
	}

	//Updated this method to pay based on quantity and price
	public Receipt pay(Long id, BigDecimal payment) throws InsufficientFundsException {
		Order order = orderRepository.findOne(id);
		BigDecimal totalOrderPrice = new BigDecimal(0);		
		for(LineItem lineItem : order.getLineItems()) {
			totalOrderPrice = totalOrderPrice.add(lineItem.getItem().getPrice().multiply(BigDecimal.valueOf(lineItem.getQuantity())));
		}		
		if(payment.compareTo(totalOrderPrice) >= 0) {
			Receipt receipt = new Receipt();
			receipt.setPayment(payment);
			order.setPaid(Boolean.TRUE);
			receipt.setOrder(order);
			Receipt savedRecepipt = receiptRepository.save(receipt);
			order.setReceipt(savedRecepipt);
			orderRepository.save(order);
			return savedRecepipt;
		}		
		else {
			throw new InsufficientFundsException("Insufficient funds to complete the order!!");
		}
		
	}
}
