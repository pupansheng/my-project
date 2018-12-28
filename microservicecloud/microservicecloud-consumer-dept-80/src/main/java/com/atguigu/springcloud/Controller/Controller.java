package com.atguigu.springcloud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.entities.Dept;

@RestController
public class Controller {
	
	@Autowired
	private RestTemplate restTemplate;
	private String url="http://MICROSERVICECLOUD-DEPT";
	
	@RequestMapping("/consumer/dept/list")
	public List<Dept> getAll(){
		
	return restTemplate.getForObject(url+"/dept/list",List.class);
		
	}
	
	@RequestMapping(value="/consumer/dept/get/{id}")
	public Dept getOne(@PathVariable("id") Long id){
		 
	return restTemplate.getForObject(url+"/dept/get/"+id,Dept.class);	
	
	}
	
	@RequestMapping("/consumer/dept/add")
	public boolean add(Dept dept){
	return restTemplate.postForObject(url+"/dept/add",dept,boolean.class);	
		
	}
	
	
	
	

}
