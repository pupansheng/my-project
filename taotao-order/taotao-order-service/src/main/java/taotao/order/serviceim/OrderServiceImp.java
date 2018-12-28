package taotao.order.serviceim;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import taotao.common.jedis.JedisClient;
import taotao.common.utils.E3Result;
import taotao.dao.TbOrderItemMapper;
import taotao.dao.TbOrderMapper;
import taotao.dao.TbOrderShippingMapper;
import taotao.manager.pojo.TbItemDesc;
import taotao.manager.pojo.TbOrder;
import taotao.manager.pojo.TbOrderItem;
import taotao.manager.pojo.TbOrderShipping;
import taotao.order.pojo.OrderInfo;
import taotao.order.service.OrderService;
@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbItemShippingMapper;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDISINCR}")
	private String REDISINCR;
	@Value("${REDISINITVALUE}")
	private String REDISINITVALUE;
	
	@Value("${REDIS_DESC}")
	private String REDIS_DESC;
	@Value("${REDIS_DESC_VALUE}")
	private String REDIS_DESC_VALUE;
	
	
	@Override
	public E3Result creatOrder(OrderInfo info) {
		
		//生成订单号 使用redis 的incr生成
		//补全TbOrder属性
		if(!jedisClient.exists(REDISINCR)){
			jedisClient.set(REDISINCR,REDISINITVALUE);
		}
		
		String s = jedisClient.incr(REDISINCR).toString();
		
		info.setOrderId(s);
		//1 未付款  2已付款  。。。
		info.setStatus(1);
		
		info.setCreateTime(new Date());
		info.setUpdateTime(new Date());
		
		//插入订单表
		tbOrderMapper.insert(info);
		
		//插入订单详情表
		List<TbOrderItem> orderItems = info.getOrderItems();
		if(!jedisClient.exists(REDIS_DESC)){
			jedisClient.set(REDIS_DESC,REDIS_DESC_VALUE);
		}
		for(TbOrderItem order:orderItems){
			
			//生成id
			String ss=jedisClient.incr(REDIS_DESC).toString();
			order.setId(ss);
			order.setOrderId(s);
			tbOrderItemMapper.insert(order);
			
			
			
		}
		//插入订单配送表
		TbOrderShipping ship=info.getOrderShipping();
		
		ship.setOrderId(s);
		ship.setCreated(new Date());
		ship.setUpdated(new Date());
		
		tbItemShippingMapper.insert(ship);
		
		
		//清空购物车
		
		
		return  E3Result.ok(s);
		
		

	}

}
