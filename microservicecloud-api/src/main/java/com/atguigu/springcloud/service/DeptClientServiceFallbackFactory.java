package com.atguigu.springcloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {

	@Override
	public DeptClientService create(Throwable arg0) {
		
		
		// TODO Auto-generated method stub
		return new DeptClientService() {
			
			@Override
			public Dept findById(Long id) {
				return new Dept().setDname("服务暂时已下线  请稍后再尝试！！").setDb_source("没有数据来源");
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<Dept> findAll() {
				List<Dept> l=new ArrayList<Dept>();
				l.add(new Dept().setDname("服务暂时已下线  请稍后再尝试！！").setDb_source("没有数据来源"));
				// TODO Auto-generated method stub
				return l;
			}
			
			@Override
			public boolean addDept(Dept dept) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
