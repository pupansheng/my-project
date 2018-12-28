package com.atguigu.springcloud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptClientService;

@RestController
public class Controller {
	
	/*@Autowired
	private RestTemplate restTemplate;
	private String url="http://MICROSERVICECLOUD-DEPT";*/
	@Autowired
	private DeptClientService deptClientService;
	
	@RequestMapping("/consumer/dept/list")
	public List<Dept> getAll(){
		
	return deptClientService.findAll();
		
	}
	
	@RequestMapping(value="/consumer/dept/get/{id}")
	public Dept getOne(@PathVariable("id") Long id){
		 
	return deptClientService.findById(id);
	
	}
	
	@RequestMapping(value="/consumer/dept/add",method=RequestMethod.POST)
	public boolean add(Dept dept){
		System.out.println(dept+"fegin---------------------------------------------------------------------------------");
	return deptClientService.addDept(dept);
		
	}
	
	
	
	

}
