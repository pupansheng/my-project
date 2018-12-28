package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy   //开启路由代理
public class Application_zuul_9527 {
	
	public static void main(String... args){
		
	SpringApplication.run(Application_zuul_9527.class,args);
	
	}

}
