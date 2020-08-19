package com.java.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java.moudle.webservice.service.HisDataExchangeWebService;
import com.java.moudle.webservice.service.impl.HisDataExchangeWebServiceImpl;


@Configuration
public class WebServiceConfig {
 
	
	@SuppressWarnings("all")
	@Bean
    public ServletRegistrationBean myServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }
	
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
 
    @Bean
    public HisDataExchangeWebService hisDataExchangeWebService() {
        return new HisDataExchangeWebServiceImpl();
    }
 
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), hisDataExchangeWebService());
        endpoint.publish("/hisDataExchange");
        return endpoint;
    }
 
}
