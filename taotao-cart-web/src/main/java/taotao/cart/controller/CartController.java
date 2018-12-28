package taotao.cart.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import itemservice.Itemservice;
import itemservice.SerachService;
import taotao.cart.service.CartService;
import taotao.common.utils.CookieUtils;
import taotao.common.utils.E3Result;
import taotao.common.utils.JsonUtils;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbUser;

@Controller
public class CartController {
	
	@Autowired
	private  Itemservice itemService;
	@Autowired
	private  SerachService searchService;
	@Autowired
	private CartService cartService;
	
	
	
	
	@RequestMapping("/cart/add/{id}")
	public String cat(@PathVariable Long id,@RequestParam(defaultValue="1") int num,HttpServletRequest request,HttpServletResponse response)
	{
		TbUser user=(TbUser) request.getAttribute("user");
		
		
		if(user!=null)
		{
			
			
			E3Result result=cartService.addProcuctCart(id, user.getId(), num);
			
			return "cartSuccess";
			
			
			
		}
		List<TbItem> list=this.getCart(request);
		boolean f=false;

		//System.out.println("size="+list.size());
		
		for(TbItem item:list)
		{
			if(item.getId()==id.longValue())
			{
				
		      int n=item.getNum()+num;//若存在数量加1
		      item.setNum(n);
		      f=true;
		      break;
			}
		}
		
		TbItem m=null;
		
		if(!f)//如果不存在则查
		{
			
			m=searchService.getTbItemById((long) id);
			
			String image = m.getImage();
			if(StringUtils.isNoneBlank(image))
			{
				String [] j=image.split(",");
				
				m.setImage(j[0]);
				
			}
			
			 m.setNum(num);
		}
		list.add(m);
		String ss=JsonUtils.objectToJson(list);
		
		CookieUtils.setCookie(request, response, "cart", ss,1800, true);//设置Cookie7200秒过期
		
		return "cartSuccess";
		
		
		
		
	}
	//从cookie获得商品列表
	private List<TbItem> getCart(HttpServletRequest request)
	{
		
		
	 String s=CookieUtils.getCookieValue(request,"cart", true);
		
		//System.out.println("s="+s);
		
		if(StringUtils.isNotBlank(s))
		{
			
			List<TbItem> jsonToList = JsonUtils.jsonToList(s,TbItem.class);
			
			return jsonToList;
			
			
		}
		else
			return new ArrayList<>();
		
	}
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, Model model,HttpServletResponse response){
		
		TbUser user=(TbUser) request.getAttribute("user");
				
		//取购物车商品列表
		List<TbItem> cartList = getCart(request);
		
		if(user!=null)
		{
			
			E3Result combCart = cartService.combCart(user.getId(),cartList);
			
			//清除COOKIE里面的信息
			
			CookieUtils.deleteCookie(request, response,"cart");
			
			
			model.addAttribute("cartList", combCart.getData());
			
			return "cart";
			
		}
		//传递给页面
		
		model.addAttribute("cartList", cartList);
		
		return "cart";
		
	}
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	@ResponseBody
	public String updateCartList(@PathVariable Long itemid,@PathVariable int num,HttpServletRequest request,HttpServletResponse response) {
		//取购物车商品列表
		
		TbUser u=(TbUser)request.getAttribute("user");
		List<TbItem> cartList = getCart(request);
		if(u!=null){
			cartService.updateCart(u.getId(), num, itemid);
			return "dssddsdsd";
		}
		
		for(TbItem item:cartList)
		{
			if(item.getId()==itemid.longValue())
			{
				item.setNum(num);
				 break;
			}
		}
		
		CookieUtils.setCookie(request, response, "cart",JsonUtils.objectToJson(cartList) , true);
		//传递给页面
		return "dssddsdsd";
	}
	@RequestMapping("/cart/delete/{id}")
	public String deleteCartList(HttpServletRequest request,@PathVariable Long id) {
		//取购物车商品列表
		
		TbUser u=(TbUser)request.getAttribute("user");
		
		if(u!=null){
			
			cartService.deleteCart(u.getId(), id);
			return "redirect:/cart/cart.action";
			
		}
		List<TbItem> cartList = getCart(request);
		for(TbItem item:cartList)
		{
			
			if(item.getId()==id.longValue())
			{
				cartList.remove(item);
				break;
				
			}
			
			
		}
		
		return "redirect:/cart/cart.action";
	}
	
	
	
	
	
	
	
	

}
