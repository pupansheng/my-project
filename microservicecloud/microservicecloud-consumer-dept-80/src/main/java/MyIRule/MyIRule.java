package MyIRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;

@Configuration
public class MyIRule {
	
	
	@Bean
	public IRule myrule(){
		
		return new MyIRule_PPS();
	}

}
