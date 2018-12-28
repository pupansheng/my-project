package content.serviceim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import content.service.ContentCataService;
import taotao.common.jedis.JedisClient;
import taotao.common.page.EasyUIDataGridResult;
import taotao.common.page.EasyUiTreeNode;
import taotao.common.utils.E3Result;
import taotao.dao.TbContentCategoryMapper;
import taotao.dao.TbContentMapper;
import taotao.manager.pojo.TbContent;
import taotao.manager.pojo.TbContentCategory;
import taotao.manager.pojo.TbContentCategoryExample;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemExample;
import taotao.manager.pojo.TbContentCategoryExample.Criteria;
import taotao.manager.pojo.TbContentExample;
@Service
public class ContentCataServiceImp implements ContentCataService {

	@Autowired
	private TbContentCategoryMapper  tbContentCategoryMapper;
	
	@Autowired
	private TbContentMapper  tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${rediskey}")
	private String rediskey;
	/*
	 * 根据父ID查询分类子节点
	 * 
	 * */
	@Override
	public List<EasyUiTreeNode> findContentCata(Long parentID) {
		
		TbContentCategoryExample examp=new TbContentCategoryExample();
		Criteria createCriteria = examp.createCriteria();
		createCriteria.andParentIdEqualTo(parentID);
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(examp);
		
		List<EasyUiTreeNode> result=new ArrayList();
		
		for(TbContentCategory q:selectByExample)
		{
			EasyUiTreeNode w=new EasyUiTreeNode();
			w.setId(q.getId());
			w.setState(q.getIsParent()?"closed":"open");
			w.setText(q.getName());
			result.add(w);
			
		}
		
		// TODO Auto-generated method stub
		return result;
	}
	
	/*
	 * 插入分类
	 * 
	 * */
	@Override
	public E3Result insertContentCata(Long parentId, String name) {
		
		TbContentCategory t=new TbContentCategory();
		t.setParentId(parentId);
		t.setName(name);
		//1 表示正常  2表示 过期
		t.setStatus(1);
		t.setSortOrder(1);
		
		t.setCreated(new Date());
		t.setUpdated(new Date());
		
		//新插入的节点一定是子节点
		t.setIsParent(false);
		tbContentCategoryMapper.insert(t);
		
		//判断插入的节点是否为父节点  不是则改成父节点
		
		TbContentCategory y=tbContentCategoryMapper.selectByPrimaryKey(parentId);
		
		if(!y.getIsParent())
		y.setIsParent(true);
		
		tbContentCategoryMapper.updateByPrimaryKeySelective(y);
		
		// TODO Auto-generated method stub
		return E3Result.ok(t);
	}
	
	/*
	 * 
	 * 删除节点
	 * 
	 * */

	@Override
	public void deleteContentCata(Long id) {
		
		//递归删除
		//System.out.println("进入删除事务！！+id="+id);
		TbContentCategoryExample examp=new TbContentCategoryExample();
		Criteria createCriteria = examp.createCriteria();
		createCriteria.andParentIdEqualTo(id);
		
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(examp);
		
	
		   for(TbContentCategory j:selectByExample)
		   {
			this.deleteContentCata(j.getId());
		   }
		
		
		
		TbContentCategory selectByPrimaryKey = tbContentCategoryMapper.selectByPrimaryKey(id);
		Long parent=selectByPrimaryKey.getParentId();
		System.out.println(" parent:  "+parent);
		TbContentCategory selectByPrimaryKeyp = tbContentCategoryMapper.selectByPrimaryKey(parent);
		
		//删除节点
		//System.out.println("删除节点"+id);
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		//查看父节点还有没有子节点
		TbContentCategoryExample examp1=new TbContentCategoryExample();
		Criteria createCriteria1 = examp1.createCriteria();
		createCriteria1.andParentIdEqualTo(parent);
		List<TbContentCategory> selectByExample1 = tbContentCategoryMapper.selectByExample(examp1);
		boolean f=false;
		for(TbContentCategory y:selectByExample1)
		 {
		 f=true;
		 break;
		}
		//没有则将父节点变为子节点
		    if(!f)
		   {
			System.out.println("将父节点变为叶子节点");
			selectByPrimaryKeyp.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKeySelective(selectByPrimaryKeyp);
		   }
		
		
		
		}
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		
	
/*
 * 更新节点
 * 
 * 
 * */
	@Override
	public void updateContentCata(Long Id, String name) {
		
		TbContentCategory t=new TbContentCategory();
		t.setId(Id);
		t.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(t);
		
		// TODO Auto-generated method stub
		
	}
	/*
	 * 
	 * 查找内容分页
	 * 
	 * 
	 * */

	@Override
	public EasyUIDataGridResult findProductContent(int now, int rows,Long categoryId) {
		       //设置分页信息
		      //now 现在页  rows 一页的记录数
		
				PageHelper.startPage(now,rows);
				//查询
				
				TbContentExample example=new TbContentExample();
				
				taotao.manager.pojo.TbContentExample.Criteria createCriteria = example.createCriteria();
				
				createCriteria.andCategoryIdEqualTo(categoryId);
				
				List<TbContent> list = tbContentMapper.selectByExample(example);
				
				//将查询结果包装到页面上
			    PageInfo<TbContent> pageinfo =new PageInfo<TbContent>(list);
			    
				EasyUIDataGridResult easyUi=new EasyUIDataGridResult(pageinfo.getTotal(),list);
				
				 return easyUi;
				// TODO Auto-generated method stub
	}
/*
 * 存取内容
 * 
 * 
 * */
	@Override
	public E3Result saveContent(TbContent t) {
		
		//先删除Redis里面的数据  做到缓存同步
		
		try {
			jedisClient.hdel(rediskey,t.getCategoryId().toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		E3Result r=new E3Result();
		try{
		t.setUpdated(new Date());
		t.setCreated(new Date());
		
		
		tbContentMapper.insert(t);
		
		
		r.setStatus(200);
		}
		catch(Exception e)
		{
			r.setStatus(100);
		}
		return r;
	}

	@Override
	public E3Result updateContent(TbContent t) {
		
		//先删除Redis里面的数据  做到缓存同步
		
		try {
			jedisClient.hdel(rediskey,t.getCategoryId().toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		E3Result r=new E3Result();
		
		try{
		t.setUpdated(new Date());
		t.setCreated(new Date());
		
		tbContentMapper.updateByPrimaryKeySelective(t);
		
		
		r.setStatus(200);
		}
		catch(Exception e)
		{
			r.setStatus(100);
		}
		return r;
		
		
		
		// TODO Auto-generated method stub
	}

	@Override
	public E3Result deleteContent(Long [] t) {
		
		//先删除Redis里面的数据  做到缓存同步
		try {
			jedisClient.hdel(rediskey);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        E3Result r=new E3Result();
		
		try{
			for(int i=0;i<t.length;i++)
	        tbContentMapper.deleteByPrimaryKey((t[i]));
			
		    r.setStatus(200);
		}
		catch(Exception e)
		{
			r.setStatus(100);
		}
		return r;
	}
	
	
	
	

}
