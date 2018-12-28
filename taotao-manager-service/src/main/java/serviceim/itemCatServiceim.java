package serviceim;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itemservice.ItemCatService;
import taotao.common.page.EasyUiTreeNode;
import taotao.dao.TbItemCatMapper;
import taotao.manager.pojo.TbItemCat;
import taotao.manager.pojo.TbItemCatExample;
import taotao.manager.pojo.TbItemCatExample.Criteria;
@Service
public class itemCatServiceim implements ItemCatService {
	@Resource(name="tbItemCatMapper")
	private TbItemCatMapper tbItemCatMapper;
	
/*
 * 寻找分类方法
 * 
 * 
 * */
	@Override
	public List<EasyUiTreeNode> findCat(Long parentId) {
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		
		
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(example);
		
		List<EasyUiTreeNode> list=new ArrayList();
		
		for(TbItemCat t:selectByExample)
		{
			EasyUiTreeNode e =new EasyUiTreeNode();
			e.setId(t.getId());
			e.setText(t.getName());
			e.setState(t.getIsParent()?"closed":"open");
			list.add(e);
		}
		
		
		// TODO Auto-generated method stub
		 return list;
	}

}
