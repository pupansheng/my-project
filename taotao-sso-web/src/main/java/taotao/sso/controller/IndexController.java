package taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sso.service.SsoService;
import taotao.common.utils.CookieUtils;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbUser;

@Controller
public class IndexController {
	
	@Autowired
	private SsoService ssoService;
	@Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;
	@RequestMapping("/page/register")
	public String  regster()
	{
		return "register";
	}
	@RequestMapping("/page/login")
	public String  pagelogin(String redirect,Model model)
	{
		model.addAttribute("redirect", redirect);
		
		return "login";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable String type) {
		
		E3Result e3Result = ssoService.checkData(param, type);
		return e3Result;
	}
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user) {
		E3Result result = ssoService.createUser(user);
		return result;
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		// 1、接收两个参数。
		// 2、调用Service进行登录。
		E3Result result = ssoService.login(username, password);
		if(result.getStatus()!=200){
			
			return  result;
			
		}
		// 3、从返回结果中取token，写入cookie。Cookie要跨域。
		String token = result.getData().toString();
		
		CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		
		// 4、响应数据。Json数据。e3Result，其中包含Token。
		return result;
		
	}
	/*
	 * 4.2springmvc 支持的jsonp处理方式
	 * 
	 * */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		
		E3Result result = ssoService.getUserByToken(token);
		
		if(StringUtils.isNotBlank(callback))
		{
			
			
		MappingJacksonValue w=new MappingJacksonValue(result);
			
			w.setJsonpFunction(callback);
			
			
			return w;
			
		}
		
		return result;
	}

}
