package search.mapper;

import java.util.List;

import taotao.common.pojo.SearchItem;

public interface SearchMapper {
	
	public List<SearchItem> getSerachItem();
	public SearchItem getSearchItemById(Long id);

}
