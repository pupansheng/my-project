package taotao.cart.serviceim;

import java.util.ArrayList;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import taotao.cart.service.CartService;
import taotao.common.jedis.JedisClient;
import taotao.common.utils.E3Result;
import taotao.common.utils.JsonUtils;
import taotao.dao.TbItemMapper;
import taotao.manager.pojo.TbItem;
@Service
public class CartServiceImp implements CartService {
    @Autowired
	private JedisClient jedisClient;
    @Autowired
    private  TbItemMapper tbItemMapper;
    @Value("${CART_ID}")
    private String CART_ID;
    
    
	@Override
	public E3Result addProcuctCart(long itemId, long userId,int num) {
		
		//取  如不为空加上数量
		String m=jedisClient.hget(CART_ID+":"+userId,itemId+"");
        TbItem item=null;
		//不为空
		
		
		if(StringUtils.isNotBlank(m))
		{
			
			item=JsonUtils.jsonToPojo(m,TbItem.class);
			int n=item.getNum();
			item.setNum(n+num);
			jedisClient.hset(CART_ID+":"+userId,itemId+"" ,JsonUtils.objectToJson(item) );
			return  E3Result.ok();
		}
		//不存在
		
		
		TbItem t=tbItemMapper.selectByPrimaryKey(itemId);
		
		
		t.setNum(num);
		String im=t.getImage();
		if(StringUtils.isNotBlank(im))
		{
			t.setImage(im.split(",")[0]);
			
		}
		jedisClient.hset(CART_ID+":"+userId,itemId+"" ,JsonUtils.objectToJson(t) );
	
		return E3Result.ok();
		 
	}


	@Override
	public E3Result combCart(long userId, List<TbItem> itemlist) {
		
		
		List<TbItem> list=this.getCartByUser(userId);
		if(itemlist!=null)
		{
			if(list!=null)
	     	itemlist.addAll(list);
		
	     	 return 	E3Result.build(200, "", itemlist);
		
		}
		else
		{
		
			return 	E3Result.build(200, "",list);
		
			
		}
	    
	}


	@Override
	public List<TbItem> getCartByUser(long userId) {
		
		List<String> list=jedisClient.hvals(CART_ID+":"+userId);//根据Key查找 
		if(list==null||list.size()==0)
			return null;
		List<TbItem> listItem=new ArrayList();
		
		for(String s:list)
		{
			TbItem item=JsonUtils.jsonToPojo(s,TbItem.class);
			
			listItem.add(item);
		}
		
		// TODO Auto-generated method stub
		
		return listItem;
	}


	@Override
	public E3Result updateCart(long userId, int num, long itemId) {
		
	     String s=jedisClient.hget(CART_ID+":"+userId,itemId+"");
	     if(StringUtils.isNotBlank(s)){
	     TbItem m=JsonUtils.jsonToPojo(s,TbItem.class);
	     
	     m.setNum(num);
	     
	     jedisClient.hset(CART_ID+":"+userId,itemId+"",JsonUtils.objectToJson(m));
	     
	     return null;
	     
	     }
		 return null;
	}


	@Override
	public E3Result deleteCart(long userId, long itemId) {
		jedisClient.hdel(CART_ID+":"+userId,itemId+"");
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public E3Result clearCart(long userId) {
		
		
		jedisClient.del(CART_ID+":"+userId);
		
		// TODO Auto-generated method stub
		return E3Result.ok();
	}

}
