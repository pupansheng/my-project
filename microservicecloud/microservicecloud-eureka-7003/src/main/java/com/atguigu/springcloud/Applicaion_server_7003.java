package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Applicaion_server_7003 {
	
	public static void  main(String args[]){
		
	SpringApplication.run(Applicaion_server_7003.class,args);
	
	}
}
