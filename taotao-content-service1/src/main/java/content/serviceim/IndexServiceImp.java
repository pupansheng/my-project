package content.serviceim;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import content.service.IndexService;
import taotao.common.jedis.JedisClient;
import taotao.common.utils.JsonUtils;
import taotao.dao.TbContentMapper;
import taotao.manager.pojo.TbContent;
import taotao.manager.pojo.TbContentExample;
import taotao.manager.pojo.TbContentExample.Criteria;
@Service
public class IndexServiceImp implements IndexService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${rediskey}")
	private String rediskey;
	
	
	@Override
	public List<TbContent> getContentbyId(Long id) {
		
		//先从redis里面查  如果有则直接返回结果
		try{
			String hget = jedisClient.hget(rediskey,id+"");
			if(StringUtils.isNotBlank(hget))
			{
				List<TbContent> t=JsonUtils.jsonToList(hget,TbContent.class);
				
				return t;
			}
			
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
		TbContentExample example=new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		 createCriteria.andCategoryIdEqualTo(id);
		 //把结果存入redis
		 
		 List<TbContent> list1 = tbContentMapper.selectByExample(example);
		 
		 try{
			 jedisClient.hset(rediskey,id+"",JsonUtils.objectToJson(list1));
			 
		     }
		 catch(Exception e)
		 {
			 
			 System.out.println(e.getMessage());
		 }
		 
		   return list1;
		
		// TODO Auto-generated method stub
		
	}

}
