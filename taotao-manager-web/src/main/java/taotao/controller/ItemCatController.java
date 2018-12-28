package taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import itemservice.ItemCatService;
import taotao.common.page.EasyUiTreeNode;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService ItemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUiTreeNode> findCat(@RequestParam(value="id",defaultValue="0")String id)
	{
		

		 List<EasyUiTreeNode> findCat = ItemCatService.findCat(Long.parseLong(id));
		 return findCat;
	}
	

}
