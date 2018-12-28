package com.atguigu.springcloud.service;

import java.util.List;

import com.atguigu.springcloud.entities.Dept;

public interface DeptService {
	public boolean addDept(Dept dept);
	public List<Dept> findAll();
	public Dept findById(Long id);

}
