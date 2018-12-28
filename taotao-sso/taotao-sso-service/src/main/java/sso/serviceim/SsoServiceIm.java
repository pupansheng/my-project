package sso.serviceim;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import sso.service.SsoService;
import taotao.common.jedis.JedisClient;
import taotao.common.utils.E3Result;
import taotao.common.utils.JsonUtils;
import taotao.dao.TbUserMapper;
import taotao.manager.pojo.TbUser;
import taotao.manager.pojo.TbUserExample;
import taotao.manager.pojo.TbUserExample.Criteria;
@Service
public class SsoServiceIm implements SsoService {
	@Autowired
	private TbUserMapper tbUserMapper;
@Autowired
	private JedisClient jedisClient;
   @Value("${USER_INFO}")
   private String USER_INFO;
   @Value("${SESSION_EXPIRE}")
   private int SESSION_EXPIRE;
	@Override
	public E3Result checkData(String param, String type) {
		
		
		TbUserExample example=new TbUserExample();
		Criteria createCriteria = example.createCriteria();
	
		if(type.equals("1")){
			
			
			
			createCriteria.andUsernameEqualTo(param);
			
			
		}
		else if(type.equals("2")){
			
			
			
			createCriteria.andPhoneEqualTo(param);
			
			
		}
       else if(type.equals("3")){
			
			
			
			createCriteria.andEmailEqualTo(param);
			
			
		}
		else{
			
			return E3Result.build(400, "非法的参数",100);
		}
		
		List<TbUser> selectByExample = tbUserMapper.selectByExample(example);
		if(selectByExample.size()>0&&selectByExample!=null){
			
			return E3Result.ok(200);
		}
		
		
		return  E3Result.ok(100);
		
	}

	@Override
	public E3Result createUser(TbUser user) {
		
		// 1、使用TbUser接收提交的请求。
		
		/*		if (StringUtils.isBlank(user.getUsername())) {
					return E3Result.build(400, "用户名不能为空");
				}
				if (StringUtils.isBlank(user.getPassword())) {
					return E3Result.build(400, "密码不能为空");
				}
				//校验数据是否可用
				E3Result result = checkData(user.getUsername(), "1");
				if (!(boolean) result.getData()) {
					return E3Result.build(400, "此用户名已经被使用");
				}
				//校验电话是否可以
				if (StringUtils.isNotBlank(user.getPhone())) {
					result = checkData(user.getPhone(), "2");
					if (!(boolean) result.getData()) {
						return E3Result.build(400, "此手机号已经被使用");
					}
				}
				//校验email是否可用
				if (StringUtils.isNotBlank(user.getEmail())) {
					result = checkData(user.getEmail(), "3");
					if (!(boolean) result.getData()) {
						return E3Result.build(400, "此邮件地址已经被使用");
					}
				}*/
				// 2、补全TbUser其他属性。
				user.setCreated(new Date());
				user.setUpdated(new Date());
				// 3、密码要进行MD5加密。
				String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
				user.setPassword(md5Pass);
				// 4、把用户信息插入到数据库中。
				tbUserMapper.insert(user);
				// 5、返回E3Result。
				return E3Result.ok();

	}

	@Override
	public E3Result login(String username, String password) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//查询用户信息
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return E3Result.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//校验密码
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return E3Result.build(400, "用户名或密码错误");
		}
		// 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
		String token = UUID.randomUUID().toString();
		// 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
		// 4、使用String类型保存Session信息。可以使用“前缀:token”为key
		user.setPassword(null);
		jedisClient.set(USER_INFO + ":" + token, JsonUtils.objectToJson(user));
		// 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
		jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
		// 6、返回e3Result包装token。
		return E3Result.build(200,null,token);
	}

	@Override
	public E3Result getUserByToken(String token) {
		
		String json = jedisClient.get(USER_INFO + ":" + token);
		if (StringUtils.isBlank(json)) {
			// 3、如果查询不到数据。返回用户已经过期。
			return E3Result.build(400, "用户登录已经过期，请重新登录。");
		}
		// 4、如果查询到数据，说明用户已经登录。
		// 5、需要重置key的过期时间。
		jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
		// 6、把json数据转换成TbUser对象，然后使用e3Result包装并返回。
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		
		return E3Result.ok(user);
	}

		
		
		
	
	}

		
	
	

	


