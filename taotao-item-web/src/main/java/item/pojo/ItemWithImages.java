package item.pojo;

import taotao.manager.pojo.TbItem;

public class ItemWithImages extends TbItem {
	
	public ItemWithImages(TbItem tbItem)
	{
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}
	
	public ItemWithImages()
	{
		
		
	}
	public String [] getImages(){
		String image=this.getImage();
	if(image!=null&&!"".equals(image))
	{
		String ima []=image.split(",");
		
		 return ima;
		
		
		
		
	}
		
		
	return null;	
		
	
	}
	
	
	

}
