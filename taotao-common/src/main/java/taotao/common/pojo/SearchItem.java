package taotao.common.pojo;

import java.io.Serializable;

public class SearchItem implements Serializable {
	
	 private String  id;
	 private String title;
	 private String sell_point;
	 private Long price;
	 //图片可能有很多张放在同一个字符串里用逗号分隔 所以需要处理
	 private String image;
	 private String cid;
	 private String  name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public String[] getImages() {
		if(image!=null&&!image.equals(""))
		{
			String [] s=image.split(",");
		return s;
		}
		else
			return null;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
