package content.service;

import java.util.List;

import taotao.common.page.EasyUIDataGridResult;
import taotao.common.page.EasyUiTreeNode;
import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbContent;

public interface ContentCataService {
	public List<EasyUiTreeNode> findContentCata(Long parentID);
	public E3Result insertContentCata(Long parentId,String name);
	public void deleteContentCata(Long id);
	public void updateContentCata(Long Id,String name);
	public EasyUIDataGridResult findProductContent(int now,int rows,Long categoryId);
	public E3Result saveContent(TbContent t);
	
	public E3Result updateContent(TbContent t);
	public E3Result deleteContent(Long [] t);
	
	
	
}
