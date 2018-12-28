package serviceim;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import itemservice.Itemservice;
import taotao.common.page.EasyUIDataGridResult;
import taotao.common.utils.E3Result;
import taotao.common.utils.IDUtils;
import taotao.dao.TbItemDescMapper;
import taotao.dao.TbItemMapper;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemDesc;
import taotao.manager.pojo.TbItemExample;
public class ItemServiceim implements Itemservice {
	@Resource(name="tbItemMapper")
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Override
	public void test() {
		// TODO Auto-generated method stub
		System.out.println("测试！！！！！！");
	}
	/*
	 * 根据需要找到所有商品
	 * now 当前页
	 * row 请求中一页需要显示的数据数量
	 * */
	@Override
	public EasyUIDataGridResult findProducts(int now, int rows) {
		//设置分页信息
		PageHelper.startPage(now,rows);
		//查询
		TbItemExample example=new TbItemExample();
		
		if(tbItemMapper==null)
		{
			System.out.println("空值！！！！！！！！！！！！！！！！！！！！！");
		}
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		
		
		//将查询结果包装到页面上
	    PageInfo<TbItem> pageinfo =new PageInfo<TbItem>(list);
		EasyUIDataGridResult easyUi=new EasyUIDataGridResult(pageinfo.getTotal(),list);
		 return easyUi;
		// TODO Auto-generated method stub
		
	}
	/*
	 * 增加商品
	 * 
	 * 
	 * */
	@Override
	public E3Result insertProduct(TbItem tbLtem, String desc) {
		//生成商品Id
		try{
		final Long id=IDUtils.genItemId();
		
		tbLtem.setId(id);
		//商品性质 1 正常 2 下架 3删除
		tbLtem.setStatus((byte) 1);
		tbLtem.setCreated(new Date());
		tbLtem.setUpdated(new Date());
		
		tbItemMapper.insert(tbLtem);
		
		
		//商品详细描述
		TbItemDesc d=new TbItemDesc();
		d.setItemDesc(desc);
		d.setItemId(id);
		d.setCreated(new Date());
		d.setUpdated(new Date());
		tbItemDescMapper.insert(d);
		
		//发送一个商品添加消息
				jmsTemplate.send(topicDestination, new MessageCreator(){
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(id + "");
						return textMessage;
					}
				});

		
		
		// TODO Auto-generated method stub
		return E3Result.ok();
		}
		catch(Exception e)
		{
			return E3Result.ok();
			//return E3Result.;
			
		}
	}
	
	
}
