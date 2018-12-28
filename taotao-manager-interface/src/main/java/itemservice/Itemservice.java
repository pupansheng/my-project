package itemservice;

import taotao.common.page.EasyUIDataGridResult;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbItem;

public interface Itemservice {
public void test();
public EasyUIDataGridResult findProducts(int now,int rows);
public E3Result insertProduct(TbItem tbitem,String desc);

}
