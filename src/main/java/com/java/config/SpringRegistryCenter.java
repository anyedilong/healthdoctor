package com.java.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

//@Component
public class SpringRegistryCenter {
//	
//	   @NacosInjected
//	    private NamingService namingService;
//
//	    @Value("${server.port}")
//	    private int serverPort;
//
//	    @Value("${spring.application.name}")
//	    private String applicationName;
//
//	    @PostConstruct
//	    public void registerInstance() throws NacosException {
//	        namingService.registerInstance(applicationName, "192.168.1.249", serverPort);
//	    }

}
