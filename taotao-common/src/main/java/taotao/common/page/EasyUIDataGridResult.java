package taotao.common.page;

import java.io.Serializable;
import java.util.List;
/*
 这是传递页面数据的类
 
 */
public class EasyUIDataGridResult implements Serializable {
	private Long total;
	private List rows;
	
	public EasyUIDataGridResult(Long l, List rows) {
		super();
		this.total = l;
		this.rows = rows;
	}
	
	
	
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
	

}
