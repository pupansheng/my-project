package taotao.controller;

import java.util.HashMap;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.json.JSONUtils;

import itemservice.Itemservice;
import itemservice.SerachService;
import taotao.common.utils.E3Result;
import taotao.common.utils.FastDFSClient;
import taotao.manager.pojo.TbItem;
import taotao.manager.pojo.TbItemDesc;

@Controller
public class FileController {
	
	@Value("${IMAGE_SERVERIP}")
	private String IMAGE_SERVERIP;
	
     @Resource(name="itemService")
	private Itemservice itemservice;
     
     @Autowired
     private SerachService serachService;
	
	//由于不同的浏览器兼容性不同 所以需要解决兼容性的问题：
	//1 我们返回为 Content-type：text/plain类型 而不是application/json
	//2 返回的响应内容字符编码格式为UTF-8而不是其他编码格式
	
	//指定返回的文件格式和字符编码
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String fileUploda(MultipartFile uploadFile)
	{
		System.out.println("进入图片上传模块");
		try {
		// 1 获取上传文件的后缀 方式1
		String ext=FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		//获取上传文件的后缀 方式2
		//String originalFilename = uploadFile.getOriginalFilename();
		//String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);由于直接截取会包括.所有+1
		
		//2  创建FastDFSClient客户端
		FastDFSClient f=new FastDFSClient("classpath:conf/client.conf");
		//3  执行上传处理 并返回存取路径 这里返回的路径不包括图片服务器的IP地址 所以要加上他  为防止写死代码 所有用配置文件方式
		String url0= f.uploadFile(uploadFile.getBytes(), ext);
		
		//4 拼装图片地址
		
		String url=IMAGE_SERVERIP+url0;
		System.out.println(url+"地址");
		Map result=new HashMap();
		result.put("error", 0);
		result.put("url",url);
		
		return JSONUtils.toJSONString(result);
		
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Map result=new HashMap();
			result.put("error", 1);
			result.put("message","图片上传失败");
			System.out.println(e.getMessage()+"失败");
			return JSONUtils.toJSONString(result);
		}
		
		
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addProduct(TbItem tbitem,String desc)
	{
		return itemservice.insertProduct(tbitem, desc);
		
	}
	/*
	 * 编辑商品
	 * 
	 * */
	@RequestMapping("/rest/page/item-edit")
	public String editProduct()
	{
		System.out.println("进入编辑页");
		return "item-edit";
		
	}
	
	
	/*
	 * 查询商品描述
	 * 
	 * */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public Map findmiaoshu(@PathVariable String id)
	{
		System.out.println("进入查询商品描述模块"+id);
		
		TbItemDesc a=serachService.findItemDesc(id);
		//System.out.println(a.getItemDesc());
		Map m=new HashMap();
		
		m.put("status", 200);
		m.put("desc", a.getItemDesc());
		System.out.println(m.get("desc"));
		return m;
		
		
	}
	/*
	 * 查询商品规格  暂未实现
	 * 
	 * */
	@RequestMapping("/rest/item/param/item/query/{id}")
	public void findguige(@PathVariable String id)
	{
		
		
		
		System.out.println("进入查询商品规格模块"+id);
		
	}
	

}
