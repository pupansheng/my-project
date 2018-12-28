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
	public Dept getOne(@PathVariable("id") Long id){
		
		return  deptService.findById(id);	
	}
	 
	@RequestMapping(value="/dept/add",method = RequestMethod.POST)
	@ResponseBody
	public boolean add(Dept dept){
		System.out.println(dept+"provider2---------------------------------------------------------------------------------");
		return  deptService.addDept(dept);	
	}
	

}
