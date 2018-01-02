package answer.king;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import answer.king.controller.ItemController;

/**
 * This class is a base MVC test class and needs to be extended by other Controller test classes.
 * Common app related initialization (before/after tests, etc) can be added to this class.
 * @author Pushkar
 *
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class AppBaseMvcTest {

	  @Autowired
      protected MockMvc mockMvc;	  
	  
	  @Autowired protected ObjectMapper objectMapper;	
	  
	  @Before
	  public void setup() {
	       JacksonTester.initFields(this, objectMapper);	       
	  }
}
