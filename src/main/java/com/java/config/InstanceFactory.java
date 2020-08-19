package com.java.config;

import org.springframework.context.ApplicationContext;

public class InstanceFactory {
	// Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
    	InstanceFactory.applicationContext = applicationContext;  
    }  

    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }
	
	/**
	 * 获取托管bean
	 * @param beanType
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType){
		return applicationContext.getBean(beanType);
	}
	
	/**
	 * 根据beanName获取托管bean
	 * @param beanName
	 * @return
	 */
	public static Object getInstance(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据bean类型和beanname获取托管bean
	 * @param beanType
	 * @param beanName
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType, String beanName){
		return applicationContext.getBean(beanName, beanType);
	}
}
