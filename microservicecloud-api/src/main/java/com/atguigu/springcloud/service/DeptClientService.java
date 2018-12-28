package com.atguigu.springcloud.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.springcloud.entities.Dept;

@FeignClient(value="MICROSERVICECLOUD-DEPT",fallbackFactory=DeptClientServiceFallbackFactory.class)
public interface DeptClientService {
	
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean addDept(Dept dept);
	
	@RequestMapping("/dept/list")
	public List<Dept> findAll();
	
	
	@RequestMapping("/dept/get/{id}")
	public Dept findById(@PathVariable("id")Long id);

	
	

}
