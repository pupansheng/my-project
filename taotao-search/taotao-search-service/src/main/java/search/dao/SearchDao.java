package search.dao;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import taotao.common.pojo.SearchItem;
import taotao.common.pojo.SearchResult;
@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult searchQuery(SolrQuery q)
	{
		
		
		try {
			//根据条件查询索引库
			QueryResponse response=solrServer.query(q);
			
			
			//取查询结果总记录数
			SolrDocumentList results = response.getResults();
			
			 long totalcount=results .getNumFound();
			 
			 
			 //返回结果对象
			 
			 SearchResult s=new SearchResult();
			 
			 s.setTotalCount((int) totalcount);
			 
			 
			 //创建商品列表对象
			 
			 
			 List<SearchItem> list=new ArrayList<SearchItem>();
			 
			 //获得高亮区域
			 Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			 //遍历查询得到的集合 并包装
			 for(SolrDocument solrDocument:results)
			 {
				    SearchItem searchItem=new SearchItem();
					searchItem.setName((String) solrDocument.get("item_category_name"));
					searchItem.setId((String) solrDocument.get("id"));
					searchItem.setImage((String) solrDocument.get("item_image"));
					searchItem.setPrice((long) solrDocument.get("item_price"));
					searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
              //得到高亮-标题
					List<String> li = highlighting.get(solrDocument.get("id")).get("item_title");
					
					String item_title="";
					
					if(li!=null&&li.size()>0){
						item_title=li.get(0);
					}
					else{
						
						item_title=(String) solrDocument.get("item_title");
					}
					
					searchItem.setTitle(item_title);
					
					
					list.add(searchItem);
					
				 
			 }
			 //将查询的结果包装到结果PojO里面
			    s.setList(list);
			    
			    
			    
			
			
			return s;
			
			
			
			
			
			
			
			
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		return null;
	}
	
	
	
	
	

}
