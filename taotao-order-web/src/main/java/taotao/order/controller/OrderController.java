package taotao.order.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import taotao.cart.service.CartService;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbUser;
import taotao.order.pojo.OrderInfo;
import taotao.order.service.OrderService;
@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showCart(HttpServletRequest request,Model model)
	{
		//获得用户名
		TbUser user=(TbUser)request.getAttribute("user");
		
		//1根据用户获得收获地址列表（暂用静态数据）
		
		
		//2获得支付方式列表（暂用静态数据）
		
		if(user!=null){
		//3根据用户获得购物车物品
		List<TbItem> cartByUser = cartService.getCartByUser(user.getId());
		
		model.addAttribute("cartList", cartByUser);
		}
		
		return "order-cart";
		
		
	}
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
		// 1、接收表单提交的数据OrderInfo。
		// 2、补全用户信息。
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		// 3、调用Service创建订单。
		E3Result result = orderService.creatOrder(orderInfo);
		if(result.getStatus()==200){
			//清空购物车
			cartService.clearCart(user.getId());
			
		}
		
		//取订单号
		String orderId = result.getData().toString();
		// a)需要Service返回订单号
		request.setAttribute("orderId", orderId);
		request.setAttribute("payment", orderInfo.getPayment());
		// b)当前日期加三天。
		/*DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));*/
		// 4、返回逻辑视图展示成功页面
		return "success";
	}


}
