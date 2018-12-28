package taotao.cart.service;

import java.util.List;

import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbItem;

public interface CartService {

	
	public E3Result addProcuctCart(long itemId,long userId,int num);
	public E3Result combCart(long userId,List<TbItem>itemlist);//合并购物车
	public List<TbItem> getCartByUser(long userID);
	public E3Result  updateCart(long userId,int num,long itemId);
	public E3Result  deleteCart(long userId,long itemId);
	public E3Result  clearCart(long userId);
	
	
	
}
