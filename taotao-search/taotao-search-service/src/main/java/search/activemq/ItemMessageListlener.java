package search.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import search.mapper.SearchMapper;
import search.service.SearchService;
import taotao.common.pojo.SearchItem;


public class ItemMessageListlener implements MessageListener {

	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SearchService searchService;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			
			if(message instanceof TextMessage )
			{
				
			TextMessage textMessage = (TextMessage) message;
			//取消息内容
			String id = textMessage.getText();
			
			//接收到消息则处理事务
			
			SearchItem searchItem= searchMapper.getSearchItemById(Long.parseLong(id));
			
			searchService.addSolrDateSingle(searchItem);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
		
		
		
		// TODO Auto-generated method stub

	}


