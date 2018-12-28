package content.service;

import java.util.List;

import taotao.manager.pojo.TbContent;

public interface IndexService {
	
	public List<TbContent> getContentbyId(Long id);

}
