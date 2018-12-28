package taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import item.pojo.ItemWithImages;
import itemservice.SerachService;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemDesc;

@Controller
public class ItemController {
	@Autowired
	private SerachService serachService;
	
	@RequestMapping("/item/{id}")
	public String Item(@PathVariable Long id,Model model){
		
		
		
		TbItem tbItem = serachService.getTbItemById(id);
		TbItemDesc findItemDesc = serachService.findItemDesc(String.valueOf(id));
		
		ItemWithImages a=new ItemWithImages(tbItem);
		
		
		model.addAttribute("item",a);
		model.addAttribute("itemDesc", findItemDesc);
		return "item";

		
		
		
	}
	
	
	
	
	

}
