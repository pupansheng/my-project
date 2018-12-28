package serviceim;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import itemservice.SerachService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import taotao.common.jedis.JedisClient;
import taotao.common.utils.JsonUtils;
import taotao.dao.TbItemDescMapper;
import taotao.dao.TbItemMapper;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemDesc;
@Service
public class SerachServiceIm implements SerachService {

	@Resource(name="tbItemMapper")
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private JedisClient jedisClient;
	@Value("${qianzhui}")
	private String qianzhui;
	
	@Override
	public TbItemDesc findItemDesc(String id) {
		
		//先从redis里面查  查到则直接返回
		try{
			
			
			
			 String str= jedisClient.get(qianzhui+id+":Desc");
			 
			 if(str!=null&&!"".equals(str))
			 {
				 
				 TbItemDesc jsonToPojo = JsonUtils.jsonToPojo(str,TbItemDesc.class);
				 
				 return jsonToPojo;
				 
			 }
			
			
		}
	    catch(Exception e){
		
	     }
		
		  TbItemDesc selectByPrimaryKey = tbItemDescMapper.selectByPrimaryKey(Long.parseLong(id));
		  //存到redis  设置过期时间
		  try{
			  
		     String str2=JsonUtils.objectToJson(selectByPrimaryKey);
		  
		      jedisClient.set(qianzhui+id+":Desc", str2);
		 
		      //设置过期时间
		      jedisClient.expire(qianzhui+id+":Desc",3600);
		      
		      
		      
		  }
		  catch(Exception e)
		  {
			  
		  }
		  
		  
		  
		  
		  return selectByPrimaryKey;
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public TbItem getTbItemById(Long id) {
		
		//先从redis里面查  查到则直接返回
				try{
					
					
					
					 String str= jedisClient.get(qianzhui+id+":Base");
					 
					 if(str!=null&&!"".equals(str))
					 {
						 
						 TbItem jsonToPojo = JsonUtils.jsonToPojo(str,TbItem.class);
						 
						 return jsonToPojo;
						 
					 }
					
					
				}
			    catch(Exception e){
				
			     }
		
		
		
		  TbItem selectByPrimaryKey = tbItemMapper.selectByPrimaryKey(id);
		  
		  try{
			  
			     String str2=JsonUtils.objectToJson(selectByPrimaryKey);
			  
			      jedisClient.set(qianzhui+id+":Base", str2);
			 
			      //设置过期时间
			      jedisClient.expire(qianzhui+id+":Base",3600);
			      
			      
			      
			  }
			  catch(Exception e)
			  {
				  
			  }
			  
			  return selectByPrimaryKey;
		  
		
		
		
	}

}
