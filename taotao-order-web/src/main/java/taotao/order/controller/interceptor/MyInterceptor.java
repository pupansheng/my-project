package taotao.order.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sso.service.SsoService;
import taotao.cart.service.CartService;
import taotao.common.utils.CookieUtils;
import taotao.common.utils.E3Result;
import taotao.common.utils.JsonUtils;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbUser;

public class MyInterceptor implements HandlerInterceptor {

	@Value("${SSO_URL}")
	private String SSO_URL;
	@Autowired
	private SsoService ssoService;
	@Autowired
	private CartService cartService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否登陆
	    //1 从cookie中取user 如果不存在则转到登陆页面登陆
		
		String cookieValue = CookieUtils.getCookieValue(request, "user", "utf-8");
		if(StringUtils.isBlank(cookieValue)){
			
			response.sendRedirect(SSO_URL+"="+request.getRequestURI());
			return false;
		}
		
		//2 若存在则调用sso服务验证是否过期
		
		E3Result r= ssoService.getUserByToken(cookieValue);
		
		if(r.getStatus()!=200){
			response.sendRedirect(SSO_URL+"?redirect="+request.getRequestURI());
			return false;
			
		}
		
		//3 取到用户信息 则把用户信息存到 resquest
		TbUser user=(TbUser)r.getData();
		request.setAttribute("user",user);
		
		//4判断cookie中是否存在购物信息  若有则合并
		
		String cart= CookieUtils.getCookieValue(request,"cart", true);
		if(StringUtils.isNoneBlank(cart)){
			
			cartService.combCart(user.getId(),JsonUtils.jsonToList(cart,TbItem.class));

		}
		//放行
		
		return true;
	
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
