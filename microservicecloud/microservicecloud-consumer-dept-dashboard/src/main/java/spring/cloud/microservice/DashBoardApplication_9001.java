package spring.cloud.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard//监控注解（豪猪）类似tomcat的管理
public class DashBoardApplication_9001{

	
	public static void main(String [] args){
		
		SpringApplication.run(DashBoardApplication_9001.class, args);
		
		
	}
	
}
