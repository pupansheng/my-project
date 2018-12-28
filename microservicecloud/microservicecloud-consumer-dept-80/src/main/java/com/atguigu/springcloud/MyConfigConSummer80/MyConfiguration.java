package com.atguigu.springcloud.MyConfigConSummer80;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class MyConfiguration {
	
	@Bean
	@LoadBalanced  //ribbon 是基于Netflix基于客户端 实现负载均衡
	public RestTemplate re(){
		return new RestTemplate();
	}
	//配置负载均衡的策略   具体策略见官网 （也可自定义：注意自定义的规则的类不能放在@ScanPacage注解扫描的包下，
	//如果放在了下面则所有的服务规则都会是那个）
/*	@Bean
	public IRule myRule(){
		return new RandomRule();
	}*/

}
