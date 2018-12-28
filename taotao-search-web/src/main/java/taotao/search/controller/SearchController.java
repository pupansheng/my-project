package taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import search.service.SearchService;
import taotao.common.pojo.SearchResult;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Value("${PAGE_ROWS}")
	private String PAGE_ROWS;
	
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
		System.out.println("进入搜索模块");
		//需要转码
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		//调用Service查询商品信息 
		SearchResult result = searchService.search(keyword, page,Integer.parseInt(PAGE_ROWS));
		//把结果传递给jsp页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getTotalCount());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getList());
		//返回逻辑视图
		return "search";
	}
	
}

	
	
	


