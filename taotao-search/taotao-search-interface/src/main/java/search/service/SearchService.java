package search.service;



import taotao.common.pojo.SearchItem;
import taotao.common.pojo.SearchResult;
import taotao.common.utils.E3Result;

public interface SearchService {
	public E3Result addSolrData();
	public E3Result addSolrDateSingle(SearchItem s);
	public SearchResult search(String keyworld,int page,int rows);
	
}
