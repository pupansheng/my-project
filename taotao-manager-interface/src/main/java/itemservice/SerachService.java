package itemservice;

import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemDesc;

public interface SerachService {
public  TbItemDesc findItemDesc(String id);
public TbItem getTbItemById(Long id);
	
}
