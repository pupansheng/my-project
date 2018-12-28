package taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	
  private List<SearchItem> list;
  private int totalPages;//总页数
  private int totalCount;//总记录数
public List<SearchItem> getList() {
	return list;
}
public void setList(List<SearchItem> list) {
	this.list = list;
}
public int getTotalPages() {
	return totalPages;
}
public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
}
public int getTotalCount() {
	return totalCount;
}
public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
}
  
	

}
