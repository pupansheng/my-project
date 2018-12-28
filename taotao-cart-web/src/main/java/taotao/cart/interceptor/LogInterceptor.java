package taotao.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sso.service.SsoService;
import taotao.common.utils.CookieUtils;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbUser;

public class LogInterceptor implements HandlerInterceptor {

	@Autowired
	private SsoService ssoServicel;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String token=CookieUtils.getCookieValue(request, "user");
		if(StringUtils.isBlank(token))
			return true;
		
		E3Result userByToken = ssoServicel.getUserByToken(token);
		
		if(userByToken.getStatus()!=200)
			return true;
		
		TbUser u=(TbUser)userByToken.getData();
		
		
		request.setAttribute("user",u);
		
		
		
		// TODO Auto-generated method stub
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
