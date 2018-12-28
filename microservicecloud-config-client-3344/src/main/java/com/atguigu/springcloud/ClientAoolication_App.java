package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientAoolication_App {
	
	public static void main(String args[]){
		
	SpringApplication.run(ClientAoolication_App.class,args);
	
	}
}
