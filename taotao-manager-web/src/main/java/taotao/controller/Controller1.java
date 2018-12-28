package taotao.controller;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import itemservice.Itemservice;
import search.service.SearchService;
import taotao.common.page.EasyUIDataGridResult;
import taotao.common.utils.E3Result;

@Controller
public class Controller1 {
	@Resource(name="itemService")
	private Itemservice itemservice;
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/")
	public String test()
	{
		return "index";
	}
	@RequestMapping("/{page}")
	public String page(@PathVariable String page)
	{
		System.out.println("进入“1.2”");
		return page;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult itempageList(Integer page,Integer rows)
	{
		System.out.println(page+"  进入分页 "+rows);
		EasyUIDataGridResult  result=itemservice.findProducts(page, rows);
		
		return result;
		
	}
	
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result impotItemIndex() {
		E3Result result = searchService.addSolrData();
		return result;
	}

}

	
	
	
	
	


