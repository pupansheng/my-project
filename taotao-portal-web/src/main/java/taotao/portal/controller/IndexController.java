package taotao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import content.service.IndexService;
import taotao.manager.pojo.TbContent;

@Controller
public class IndexController {

	@Value("${content_catetetory_id}")
	private String content_catetetory_id;
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/index")
	public String index(Model model)
	{
		List<TbContent> t=indexService.getContentbyId(Long.parseLong(content_catetetory_id));
		
		model.addAttribute("ad1List",t);
		return "index";
		
		
	}
	
	
	
	
	
	
	
}
