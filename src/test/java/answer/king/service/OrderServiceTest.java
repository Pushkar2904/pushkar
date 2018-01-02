package answer.king.service;

import static org.junit.Assert.assertNotNull;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import answer.king.AppBaseTest;
import answer.king.model.Item;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.ReceiptRepository;
import answer.king.util.InsufficientFundsException;
import answer.king.util.InvalidDataException;

public class OrderServiceTest extends AppBaseTest {
	private final static Logger LOGGER = Logger.getLogger(ItemServiceTest.class);
	
	@Autowired
	private OrderService orderService;
	
	@MockBean
	private ItemRepository itemRepositoryMock;
	
	/*@MockBean
	private ReceiptRepository receiptRepositoryMock;*/
	
	 @Before
	    public void setUp() throws InvalidDataException {
		 	        
	        Item item = new Item();
	        item.setId(1l);
	        item.setName("TestItem");
	        item.setPrice(new BigDecimal(10));
	        
	        Item anotherItem = new Item();
	        anotherItem.setId(2l);
	        anotherItem.setName("AnotherTestItem");
	        anotherItem.setPrice(new BigDecimal(20));
	       
	        Mockito.when(itemRepositoryMock.findOne(1l)).thenReturn(item);
	        Mockito.when(itemRepositoryMock.findOne(2l)).thenReturn(anotherItem);

	    }
	
	@Test
	public void validNewOrderServiceTest() {
		Order order = new Order();		
		Order savedOrder = orderService.save(order);

		LOGGER.info("Order ID: "+savedOrder.getId());
		assertNotNull(savedOrder);
		assertNotNull(savedOrder.getId());		
	}
	
	@Test
	public void validAddItemTest() {
		 
		Order order = new Order();		
		Order savedOrder = orderService.save(order);		
        orderService.addItem(savedOrder.getId(), 1l, 2l);
        orderService.addItem(savedOrder.getId(), 2l, 5l);        
        Order orders = orderService.getAll().get(0);
        Assert.assertNotNull(orders.getLineItems());
        Assert.assertEquals(orders.getLineItems().size(), 2);        
   	}

	@Test
	public void validAddItemAndPayTest() throws InsufficientFundsException {
		 
		Order order = new Order();		
		Order savedOrder = orderService.save(order);
		
        orderService.addItem(savedOrder.getId(), 1l, 2l);
        orderService.addItem(savedOrder.getId(), 2l, 5l);
        Receipt receipt = orderService.pay(savedOrder.getId(), new BigDecimal(120));
         System.out.println("Receipt id: "+receipt.getReceiptId());
        Assert.assertEquals(receipt.getOrder().getPaid(), Boolean.TRUE);
   	}
	
	@Test (expected = InsufficientFundsException.class)
	public void invalidAddItemAndPayTest() throws InsufficientFundsException {
		 
		Order order = new Order();		
		Order savedOrder = orderService.save(order);
		
        orderService.addItem(savedOrder.getId(), 1l, 2l);
        orderService.addItem(savedOrder.getId(), 2l, 5l);
        Receipt receipt = orderService.pay(savedOrder.getId(), new BigDecimal(12));
        
        Assert.assertEquals(receipt.getOrder().getPaid(), Boolean.FALSE);
   	}
	
	
	@Test
	public void duplicateItemAddAndIncrementingQuantityTest() throws InsufficientFundsException {
		
		Order order = new Order();		
		Order savedOrder = orderService.save(order);
		
        orderService.addItem(savedOrder.getId(), 1l, 2l);
        orderService.addItem(savedOrder.getId(), 1l, 5l);
        
        Receipt receipt = orderService.pay(savedOrder.getId(), new BigDecimal(70));
		
        Assert.assertEquals(receipt.getOrder().getLineItems().size(), 1);
        Assert.assertEquals(receipt.getOrder().getPaid(), Boolean.TRUE);
	}
	
	
	
}
