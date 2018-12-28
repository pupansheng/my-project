package search.serviceim;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import search.dao.SearchDao;
import search.mapper.SearchMapper;
import search.service.SearchService;
import taotao.common.pojo.SearchItem;
import taotao.common.pojo.SearchResult;
import taotao.common.utils.E3Result;

@Service
public class SearchServiceImp implements SearchService {

	@Autowired
	private SearchMapper searchMapper;
	
	@Autowired
	private SolrServer solrServer;//采用注入  不用每次都new
	
	@Autowired
	private SearchDao searchDao;
	@Value("${KEYWORD}")
    private String KEYWORD;
	
	@Override
	public E3Result addSolrData( ){
		
		try {
		//得到所有检索数据
		List<SearchItem> serachItems = searchMapper.getSerachItem();
		
		//将数据到屠刀索引库
		
		for(SearchItem searchItem:serachItems)
		{
			
				//创建文档对象
				
				SolrInputDocument document=new SolrInputDocument();
				
				//向文档对象添加域  注意  :这些域都是在Scheam.xml里面已经定义了的
				
				document.addField("id",searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getName());

				//写入索引库
				
				 solrServer.add(document);
				 
				
				 
			} 
		 solrServer.commit();
		 
		 return  E3Result.ok();
		}
		catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return  E3Result.build(300,"商品导入索引库出错");
		
	}
	@Override
	public SearchResult search(String keyworld, int page, int rows) {
		//1 关键字  2 当前页 3 页面大小
		
		//创建对象
		 SolrQuery query=new SolrQuery();
		 
		 //设置查询条件
		 query.setQuery(keyworld);
		 
		 //设置分页条件
		
		  query.setStart((page-1)*rows);
		  
		  //设置rows
		  query.setRows(rows);
		  
		  //设置默认搜索域
		  
		 query.set("df",KEYWORD);
		  
		  //设置高亮显示
		  
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//执行查询
		SearchResult searchQuery = searchDao.searchQuery(query);
		
		
		//计算总页数
		
		int totalPage;
		
		
		int count=searchQuery.getTotalCount();
		if(count%rows==0)
		totalPage=count/rows;
		else
		totalPage=count/rows+1;
		
		searchQuery.setTotalPages(totalPage);
			
		// TODO Auto-generated method stub
		return searchQuery;
	}
	@Override
	public E3Result addSolrDateSingle(SearchItem searchItem) {
		try {
		SolrInputDocument document = new SolrInputDocument();
		// 3、使用SolrServer对象写入索引库。
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getName());
	
		// 5、向索引库中添加文档。
		
			solrServer.add(document);
			solrServer.commit();
			
			
			return E3Result.ok();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		// 4、返回成功，返回e3Result。
		
		return  E3Result.build(300,"商品导入索引库出错");
		
		
		
		
		// TODO Auto-generated method stub

	}
	

	
	
	
	
	
}
