package answer.king.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import answer.king.model.Item;
import answer.king.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Item> getAll() {
		return itemService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Item create(@RequestBody Item item) {
		return itemService.save(item);
	}
	
	//Added updated service
	@RequestMapping(method = RequestMethod.PUT)
	public Item update(@RequestBody Item item) {
		return itemService.update(item);
	}
}
