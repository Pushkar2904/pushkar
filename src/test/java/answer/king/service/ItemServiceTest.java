package answer.king.service;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import answer.king.AppBaseTest;
import answer.king.model.Item;
import answer.king.util.InvalidDataException;
	
public class ItemServiceTest extends AppBaseTest {
	private final static Logger LOGGER = Logger.getLogger(ItemServiceTest.class);			
	
	@Autowired
	private ItemService itemService;
	
	
	  @Test
	  public void validItemNameValidationTest() throws InvalidDataException {		  
		  Item item = new Item();		  
		  item.setName("Test");
		  item.setPrice(new BigDecimal("10"));
		  Item persistedItem = itemService.save(item);
		  
		  LOGGER.info("Item id: "+persistedItem.getId());
		  
		  Assert.assertNotNull(persistedItem.getId());
		  Assert.assertEquals(item.getName(), persistedItem.getName());
	  }
	  
	  @Test
	  public void validUpdateItemTest() throws InvalidDataException {
		  Item item = new Item();		  
		  item.setName("Test");
		  item.setPrice(new BigDecimal("10"));
		  Item persistedItem = itemService.save(item);
		  
		  persistedItem.setPrice(new BigDecimal(20));
		  System.out.println("persistedItem: "+ persistedItem.getId());
		  Item updatedItem = itemService.update(persistedItem);
		  System.out.println("updatedItem: "+ updatedItem.getId());
		  
		  Assert.assertEquals(updatedItem.getId(), persistedItem.getId());
		  Assert.assertEquals(updatedItem.getName(), "Test");
		  Assert.assertEquals(updatedItem.getPrice(), new BigDecimal(20));
	  }
	
	
	  @Test(expected = InvalidDataException.class)
	  public void invalidItemNameValidationTest() throws InvalidDataException {		  
		  Item item = new Item();		  
		  item.setName("Test_");
		  item.setPrice(new BigDecimal("10"));
	  }
	  
	  
		  @Test(expected = InvalidDataException.class)
		  public void emptyItemNameValidationTest() throws InvalidDataException {		  
			  Item item = new Item();		  
			  item.setName("");
			  item.setPrice(new BigDecimal("10"));
		  }
		  
	 
	  @Test(expected = InvalidDataException.class)
	  public void positiveItemPriceValidationTest() throws InvalidDataException {		  
		  Item item = new Item();		  
		  item.setName("Test");
		  item.setPrice(BigDecimal.valueOf(-10));		  
	  }
	  
	  
	  @Test(expected = NumberFormatException.class)
	  public void validItemPriceValidationTest() throws InvalidDataException {		  
		  Item item = new Item();		  
		  item.setName("Test");
		  item.setPrice(new BigDecimal("asd"));		 
	  }
	 

}
