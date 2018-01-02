package answer.king.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import answer.king.AppBaseMvcTest;
import answer.king.model.Item;
import answer.king.service.ItemService;

@WebMvcTest(ItemController.class)
public class ItemControllerTest extends AppBaseMvcTest {
	  
	  @MockBean
	  private ItemService itemService;
	  
	  protected JacksonTester<Item> jsonTester;	  
	  
	  @Test
      public void itemControllerMvcTest() throws Exception {    	  
          mockMvc.perform(get("/item"))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

      }
      
      
     @Test
  	 public void saveItem() throws Exception {
  		Item mockItem = new Item();
  		mockItem.setId(1l);
  		mockItem.setName("Spring");
  		mockItem.setPrice(BigDecimal.valueOf(10));
  		
  		final String itemJson = jsonTester.write(mockItem).getJson();
  		Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(mockItem);
  		mockMvc.perform(post("/item")
	  				.contentType(MediaType.APPLICATION_JSON_UTF8)
	  				.content(itemJson)
	  				)
  				.andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1)))
				.andExpect(jsonPath("name", is("Spring")))
				.andExpect(jsonPath("price", is(10)));

  	}
    
     
     @Test
     public void getItem() throws Exception{
    	 Item mockItem = new Item();
   		mockItem.setId(1l);
   		mockItem.setName("Spring");
   		mockItem.setPrice(BigDecimal.valueOf(10));
   		
   		Item mockItem2 = new Item();
  		mockItem2.setId(2l);
  		mockItem2.setName("SpringBoot");
  		mockItem2.setPrice(BigDecimal.valueOf(20));
    	
   		List<Item> allItems = Lists.newArrayList();
   		allItems.add(mockItem);
   		allItems.add(mockItem2);
   		
    	Mockito.when(itemService.getAll()).thenReturn(allItems);
    			
    	mockMvc.perform(get("/item"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$[0].id", is(1)))
    			.andExpect(jsonPath("$[0].name", is("Spring")))
    			.andExpect(jsonPath("$[0].price", is(10)))
    			.andExpect(jsonPath("$[1].name", is("SpringBoot")));
    	}
}
