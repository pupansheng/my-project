package taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import content.service.ContentCataService;
import taotao.common.page.EasyUIDataGridResult;
import taotao.common.page.EasyUiTreeNode;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbContent;

@Controller
public class ContentController {
	
	@Autowired
	private ContentCataService contentCataService;
	
	@RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUiTreeNode> findContentCa(@RequestParam(name="id",defaultValue="0")Long parentId)
    {
		
		return contentCataService.findContentCata(parentId);
	
		
    }
	
	
	/*
	 * 新增节点
	 * 
	 * 
	 * */
	@RequestMapping("/content/category/create")
    @ResponseBody
	public E3Result insertContentCate(Long parentId,String name)
	{
		
		E3Result e=	contentCataService.insertContentCata(parentId, name);
		return e;
	}
	
	/*
	 * 删除节点
	 * 
	 * 
	 * */
	@RequestMapping("/content/category/delete")
	public String deleteContentCate(Long id)
	{
		System.out.println("进入删除分类"+id);
	    contentCataService.deleteContentCata(id);
	    return "content-category.jsp";
	}
	
	/*
	 * 修改节点
	 * 
	 * 
	 * */
	@RequestMapping("/content/category/update")
	public void deleteContentCate(Long id,String name)
	{
	contentCataService.updateContentCata(id, name);
	
	}
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult contentpageList(Integer page,Integer rows,@RequestParam(name="categoryId",defaultValue="89") Long categoryId)
	{
		//System.out.println("进入内容分页"+categoryId);
		return contentCataService.findProductContent(page, rows,categoryId );
	}
	
	/*
	 * 存取内容
	 * 
	 * */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result saveContent(TbContent t)
	{
		//System.out.println("进入内容分页"+categoryId);
		return contentCataService.saveContent(t);
	}
	/*
	 * 修改内容
	 * */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editContent(TbContent t)
	{
		
		return contentCataService.updateContent(t);
	}
	/*
	 * 
	 * 删除内容
	 * */
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(Long [] ids)
	{
		
		return contentCataService.deleteContent(ids);
	}
	
	
	

}
