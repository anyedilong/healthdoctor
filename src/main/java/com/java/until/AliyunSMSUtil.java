package com.java.until;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.java.moudle.webservice.dto.AliyunTemplateDto;

@Component
public class AliyunSMSUtil {
	
    //产品名称:云通信短信API产品,开发者无需替换
	private static String product = "Dysmsapi";
    
    //产品域名,开发者无需替换
    private static String domain = "dysmsapi.aliyuncs.com";

    // 自己的AK-在阿里云访问控制台寻找
    private static String accessKeyId;
    
    private static String accessKeySecret;

    // 短信签名-可在短信控制台中找到
    private static String smsSignature;
    
    // 短信模板-可在短信控制台中找到
    private static String smsTemplate;
    
    // 提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    private static String outId;
    
    // 超时时间 默认10000
    private static String smsTimeout;
    
    // 短信模板
    private static String smsTemplateContent;

    @NacosValue(value="${smsData.accessKeyId}", autoRefreshed=true)
	public void setAccessKeyId(String accessKeyId) {
		AliyunSMSUtil.accessKeyId = accessKeyId;
	}

    @NacosValue(value="${smsData.accessKeySecret}",autoRefreshed=true)
	public void setAccessKeySecret(String accessKeySecret) {
		AliyunSMSUtil.accessKeySecret = accessKeySecret;
	}

    @NacosValue(value="${smsData.smsSignature}", autoRefreshed=true)
	public void setSmsSignature(String smsSignature) {
		AliyunSMSUtil.smsSignature = smsSignature;
	}

    @NacosValue(value="${smsData.smsTemplate}", autoRefreshed=true)
	public void setSmsTemplate(String smsTemplate) {
		AliyunSMSUtil.smsTemplate = smsTemplate;
	}
	
    @NacosValue(value="${smsData.outId}", autoRefreshed=true)
	public void setOutId(String outId) {
		AliyunSMSUtil.outId = outId;
	}

    @NacosValue(value="${smsData.smsTimeout}", autoRefreshed=true)
	public void setSmsTimeout(String smsTimeout) {
		AliyunSMSUtil.smsTimeout = smsTimeout;
	}

    @NacosValue(value="${smsData.smsTemplateContent}", autoRefreshed=true)
	public void setSmsTemplateContent(String smsTemplateContent) {
		AliyunSMSUtil.smsTemplateContent = smsTemplateContent;
	}

    // 短信模板-可在短信控制台中找到
    private static String subTemplate;
    // 短信模板
    private static String subTemplateContent;
 // 短信模板-可在短信控制台中找到
    private static String canSubTemplate;
    // 短信模板
    private static String canSubTemplateContent;
    
    @NacosValue(value="${smsData.subTemplate}", autoRefreshed=true)
	public void setSubTemplate(String subTemplate) {
		AliyunSMSUtil.subTemplate = subTemplate;
	}

    @NacosValue(value="${smsData.subTemplateContent}", autoRefreshed=true)
	public void setSubTemplateContent(String subTemplateContent) {
		AliyunSMSUtil.subTemplateContent = subTemplateContent;
	}

    @NacosValue(value="${smsData.canSubTemplate}", autoRefreshed=true)
	public void setCanSubTemplate(String canSubTemplate) {
		AliyunSMSUtil.canSubTemplate = canSubTemplate;
	}

    @NacosValue(value="${smsData.canSubTemplateContent}", autoRefreshed=true)
	public void setCanSubTemplateContent(String canSubTemplateContent) {
		AliyunSMSUtil.canSubTemplateContent = canSubTemplateContent;
	}

    /**
     * 	预约成功和取消预约发送短信方法
     * @param phoneNumber 待发送手机号
     * @param smsContent 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为{"name":"Tom", "code":"123"}
     * @param outId 
     */
    public static SendSmsResponse sendSubSms(String phoneNumber, AliyunTemplateDto info) throws ClientException {
    	try {
    		//可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", smsTimeout);
            System.setProperty("sun.net.client.defaultReadTimeout", smsTimeout);
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(smsSignature);
    		//1. 预约成功 2.取消预约
            if("1".equals(info.getType())) {
            	//必填:短信模板-可在短信控制台中找到
                request.setTemplateCode(subTemplate);
                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
                String templat = subTemplateContent;
                templat = templat.replace("hzmc", info.getName()).replace("docInfo", (info.getOrgName()+info.getDepName()+info.getDoctorName()))
                		.replace("subTime", info.getSubTime()).replace("depName", info.getDepName());
                request.setTemplateParam(templat);
    		}else if("2".equals(info.getType())) {
    			//必填:短信模板-可在短信控制台中找到
                request.setTemplateCode(canSubTemplate);
                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
                String templat = canSubTemplateContent;
                templat = templat.replace("docInfo", (info.getOrgName()+info.getSubTime()+info.getDepName()+info.getDoctorName()));
                request.setTemplateParam(templat);
    		}else {
    			return null;
    		}
            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(outId);
            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            return sendSmsResponse;
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e.toString());
    		return null;
    	}
    }
    
	/**
     * 	发送验证码短信方法
     * @param phoneNumber 待发送手机号
     * @param smsContent 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为{"name":"Tom", "code":"123"}
     * @param outId 
     */
    public static SendSmsResponse sendSms(String phoneNumber, String smsContent) throws ClientException {
    	try {
    		System.out.println("手机号：" + phoneNumber);
    		System.out.println("验证码：" + smsContent);
    		String templat = smsTemplateContent;
    		smsContent = templat.replace("content", smsContent);
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", smsTimeout);
            System.setProperty("sun.net.client.defaultReadTimeout", smsTimeout);

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(smsSignature);
            
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsTemplate);
            
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(smsContent);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(outId);

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            
            return sendSmsResponse;
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e.toString());
    		return null;
    	}
    }
    
    /**
     * 查询明细
     * @param phoneNumber 待发送手机号
     * @param time 查询时间
     */
    public static QuerySendDetailsResponse querySendDetails(String phoneNumber, Date time) {
    	// 这里填了之前Demo的一些默认值
    	return querySendDetails(phoneNumber, null, time, 10L, 1L);
    }
    /**
     * 查询明细
     * @param phoneNumber 待发送手机号
     * @param time 查询时间
     * @param bizId 流水号
     */
    public static QuerySendDetailsResponse querySendDetails(String phoneNumber, Date time, String bizId) {
    	// 这里填了之前Demo的一些默认值
    	return querySendDetails(phoneNumber, bizId, time, 10L, 1L);
    }
    /**
     * 查询明细
     * @param phoneNumber 待发送手机号
     * @param time 查询时间
     * @param bizId 流水号
     * @param startPage 当前页码从1开始计数
     * @param pageSize 页大小
     */
    public static QuerySendDetailsResponse querySendDetails(String phoneNumber, String bizId, Date time, Long startPage, Long pageSize) {
    	try {
	        //可自助调整超时时间
	        System.setProperty("sun.net.client.defaultConnectTimeout", smsTimeout);
	        System.setProperty("sun.net.client.defaultReadTimeout", smsTimeout);
	
	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
	        IAcsClient acsClient = new DefaultAcsClient(profile);

	        //组装请求对象
	        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
	        
	        //必填-号码
	        request.setPhoneNumber(phoneNumber);
	        
	        //可选-流水号
	        request.setBizId(bizId);
	        
	        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
	        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	        request.setSendDate(ft.format(time));
	        
	        //必填-页大小
	        request.setPageSize(pageSize);
	        
	        //必填-当前页码从1开始计数
	        request.setCurrentPage(startPage);
	
	        //hint 此处可能会抛出异常，注意catch
	        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
	
	        return querySendDetailsResponse;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
    }
}
