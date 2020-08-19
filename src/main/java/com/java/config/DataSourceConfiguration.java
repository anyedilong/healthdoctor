package com.java.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration {

	@NacosValue(value="${spring.datasource.username}",autoRefreshed=true)
	private String user;

	@NacosValue(value="${spring.datasource.password}",autoRefreshed=true)
	private String password;
	
	@NacosValue(value="${spring.datasource.dataSourceClassName}",autoRefreshed=true)
	private String dataSourceClassName;

	@NacosValue(value="${spring.datasource.poolName}",autoRefreshed=true)
	private String poolName;

	@NacosValue(value="${spring.datasource.connectionTimeout}",autoRefreshed=true)
	private int connectionTimeout;

	@NacosValue(value="${spring.datasource.maxLifetime}",autoRefreshed=true)
	private int maxLifetime;

	@NacosValue(value="${spring.datasource.maximumPoolSize}",autoRefreshed=true)
	private int maximumPoolSize;

	@NacosValue(value="${spring.datasource.minimumIdle}",autoRefreshed=true)
	private int minimumIdle;

	@NacosValue(value="${spring.datasource.idleTimeout}",autoRefreshed=true)
	private int idleTimeout;

	@NacosValue(value="${spring.datasource.connectionTestQuery}",autoRefreshed=true)
	private String connectionTestQuery;

	@NacosValue(value="${spring.datasource.driverType}",autoRefreshed=true)
	private String driverType;

	@NacosValue(value="${spring.datasource.serverName}",autoRefreshed=true)
	private String serverName;

	@NacosValue(value="${spring.datasource.databaseName}",autoRefreshed=true)
	private String databaseName;

	@NacosValue(value="${spring.datasource.portNumber}",autoRefreshed=true)
	private int portNumber;

	@Bean("dataSource")
	public DataSource primaryDataSource() {
		
		Properties dsProps = new Properties();
		dsProps.put("user", user);
		dsProps.put("password", password);
		dsProps.put("databaseName", databaseName);
		dsProps.put("serverName", serverName);
		dsProps.put("portNumber", portNumber);
		dsProps.put("driverType", driverType);
		Properties configProps = new Properties();
		configProps.put("dataSourceClassName", dataSourceClassName);
		configProps.put("poolName", poolName);
		configProps.put("maximumPoolSize", maximumPoolSize);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("connectionTimeout", connectionTimeout);
		configProps.put("idleTimeout", idleTimeout);
		configProps.put("connectionTestQuery", connectionTestQuery);
		configProps.put("dataSourceProperties", dsProps);
		HikariConfig hc = new HikariConfig(configProps);

		final HikariDataSource hikariDataSource = new HikariDataSource(hc);
		return hikariDataSource;
	}


}
