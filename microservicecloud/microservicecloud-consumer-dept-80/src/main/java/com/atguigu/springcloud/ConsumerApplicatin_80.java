package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import MyIRule.MyIRule;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MyIRule.class)//使用自定义的特殊规则
public class ConsumerApplicatin_80 {
	public static void main(String... args){
		
	SpringApplication.run(ConsumerApplicatin_80.class,args);
	
	
	}
}
