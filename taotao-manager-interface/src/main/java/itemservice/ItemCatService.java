package itemservice;

import java.util.List;
import taotao.common.page.EasyUiTreeNode;

public interface ItemCatService {
	
	public List<EasyUiTreeNode> findCat(Long parentId);

}
