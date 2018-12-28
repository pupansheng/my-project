package com.atguigu.springcloud.entities;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Accessors(chain=true)
public class Dept implements Serializable{
	
	private Long deptno;
	private String dname;
	private String db_source;

	public static void main(String args[]){
		
     
		
	}
	
	
	

}
