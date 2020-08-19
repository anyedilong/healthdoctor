package com.java;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySources;
import com.java.config.InstanceFactory;
import com.java.moudle.common.service.InitService;


@SpringBootApplication
@EnableDiscoveryClient
@NacosPropertySources({
	@NacosPropertySource(dataId = "public_Health_dict", groupId="DEFAULT_GROUP", autoRefreshed = true),
	@NacosPropertySource(dataId = "oracle_path", groupId="DEFAULT_GROUP", autoRefreshed = true),
	@NacosPropertySource(dataId = "redis_colony", groupId="DEFAULT_GROUP", autoRefreshed = true),
	@NacosPropertySource(dataId = "appointment_dict", groupId="DEFAULT_GROUP", autoRefreshed = true)
})
public class Start {
	
	public static void main(String[] args) {
		//SpringApplication.run(Start.class, args);
		//application.setWebApplicationType(WebApplicationType.SERVLET);
		SpringApplication application = new SpringApplication(Start.class);
		ApplicationContext context = application.run();
		InstanceFactory.setApplicationContext(context);
		InstanceFactory.getInstance(InitService.class).InitData();
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
	   return new RestTemplate();
	}
	
}
