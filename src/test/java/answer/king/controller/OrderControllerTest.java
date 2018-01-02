package answer.king.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import answer.king.AppBaseMvcTest;
import answer.king.model.Item;
import answer.king.model.LineItem;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.OrderRepository;
import answer.king.service.ItemService;
import answer.king.service.OrderService;
import answer.king.util.InvalidDataException;

@WebMvcTest(OrderController.class)
public class OrderControllerTest extends AppBaseMvcTest{
	
	 @MockBean
	 private ItemService itemService;

	 @MockBean
	 private OrderService orderService;
	 
	 protected JacksonTester<Order> jsonTester;	  
	 
/*	 @MockBean
	 private OrderRepository orderRepository;
	 
	 @MockBean
	 private ItemRepository itemRepository;*/
     
	/* @Before
	 public void before() {
	     MockitoAnnotations.initMocks(this);
	     this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
	 }*/
	
	 @Test
     public void orderControllerMvcTest() throws Exception {
		
        mockMvc.perform(get("/order"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
     }
	 

	 @Test
	 public void getAllOrders() throws Exception {
		 Order order = new Order();
		 order.setId(1l);
		 order.setPaid(Boolean.TRUE);
		 
		 List<Order> orders = Lists.newArrayList();
		 orders.add(order);
		 
		 final String orderJson = jsonTester.write(order).getJson();
		 Mockito.when(orderService.getAll()).thenReturn(orders);
		 
		 RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
					"/order").accept(
					MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			System.out.println("Response: "+result.getResponse().getContentAsString());
			String expected = "[{\"id\":1,\"paid\":true,\"lineItems\":null,\"receipt\":null}]";
			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
	 }
	
	@Test
	public void createOrderTest() throws Exception {
		Order order = buildOrder();
		order.setLineItems(Lists.newArrayList());
		
		final String orderJson = jsonTester.write(order).getJson();
  		Mockito.when(orderService.save(Mockito.any(Order.class))).thenReturn(order);
  		mockMvc.perform(post("/order")
	  				.contentType(MediaType.APPLICATION_JSON_UTF8)
	  				.content(orderJson)
	  				)
  				.andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1)))
				.andExpect(jsonPath("paid", is(Boolean.FALSE)));
	}
	
	@Test
	public void addItemToOrderTest() throws Exception {
		Order order = buildOrder();
		
		Item item = buildItem();
		
		LineItem lineItem1 = buildLineItem(item);		
		
		List<LineItem> lineItems = Lists.newArrayList();
		lineItems.add(lineItem1);
		order.setLineItems(lineItems);
		
		final String orderJson = jsonTester.write(order).getJson();
		
		System.out.println("Order Json: "+orderJson);
  		Mockito.when(orderService.addItem(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(order);
  		
		mockMvc.perform(put("/order/{id}/addItem/{itemId}/{quantity}", order.getId(), item.getId(), lineItem1.getQuantity())
				.accept(MediaType.APPLICATION_JSON_UTF8)
  				.content(orderJson))
				.andExpect(status().isOk());
		verify(orderService, times(1)).addItem(order.getId(), item.getId(), lineItem1.getQuantity());
	}
	
	@Test
	public void payOrderTest() throws Exception {
		
		Order order = buildOrder();		
		Item item = buildItem();		
		LineItem lineItem1 = buildLineItem(item);
		
		List<LineItem> lineItems = Lists.newArrayList();
		lineItems.add(lineItem1);		
		order.setLineItems(lineItems);
		
		Receipt receipt = buildReceipt(order);
		final String orderJson = jsonTester.write(order).getJson();
		System.out.println("Order Json: "+orderJson);
  		
		Mockito.when(orderService.pay(Mockito.anyLong(), Mockito.any())).thenReturn(receipt);
  		
		mockMvc.perform(put("/order/{id}/pay", 1L)
	  				.contentType(MediaType.APPLICATION_JSON_UTF8)
	  				.content(orderJson)
	  				)
  				.andExpect(status().isOk());
		
	}


	private Receipt buildReceipt(Order order) {
		Receipt receipt = new Receipt();
		receipt.setOrder(order);
		receipt.setReceiptId(1l);
		receipt.setPayment(new BigDecimal(20));
		return receipt;
	}


	private LineItem buildLineItem(Item item) {
		LineItem lineItem1 = new LineItem();
		lineItem1.setLineItemId(1l);
		lineItem1.setQuantity(2l);
		lineItem1.setItem(item);
		return lineItem1;
	}


	private Item buildItem() throws InvalidDataException {
		Item item = new Item();
		item.setId(1l);
		item.setName("Item");
		item.setPrice(new BigDecimal(10));
		return item;
	}


	private Order buildOrder() {
		Order order = new Order();
		order.setId(1l);
		order.setPaid(Boolean.FALSE);
		return order;
	}
}
