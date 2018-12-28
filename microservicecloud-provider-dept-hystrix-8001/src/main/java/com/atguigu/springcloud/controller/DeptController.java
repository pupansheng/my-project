package com.atguigu.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	
	@RequestMapping("/dept/list")
	@ResponseBody
	public List<Dept> getAll(){
		return  deptService.findAll();	
	}
	
	@RequestMapping("/dept/get/{id}")
	@ResponseBody
	@HystrixCommand(fallbackMethod="errorGet")//如果调用这个方法出错或者超时 则条用备用方法
	public Dept getOne(@PathVariable("id") Long id){
		Dept d=null;
		d=deptService.findById(id);	
		if(d==null){
			throw new RuntimeException("出错");
		}
		return  d;	
	}
	 
	@RequestMapping(value="/dept/add",method = RequestMethod.POST)
	@ResponseBody
	public boolean add(Dept dept){
		System.out.println(dept+"provider1---------------------------------------------------------------------------------");
		return  deptService.addDept(dept);	
	}
	public Dept errorGet(@PathVariable("id") Long id){
		
		return new Dept().setDname("该编号没有信息").setDb_source("无来源与数据库").setDeptno(id);
		
	}
	

}
