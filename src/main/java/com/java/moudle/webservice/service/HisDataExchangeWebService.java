package com.java.moudle.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface HisDataExchangeWebService {

	
	/**
	 * @Description: 获取科室信息
	 * @param @param user
	 * @param @return
	 * @return String
	 * @throws
	 */
	@WebMethod
	public String getDeptInfo(@WebParam(name = "deptjson") String deptjson);
	
	/**
	 * @Description: 获取医生信息
	 * @param @param user
	 * @param @return
	 * @return String
	 * @throws
	 */
	@WebMethod
	public String getDoctorlInfo(@WebParam(name = "doctorjson") String doctorjson);
	
	/**
	 * @Description: 返回就诊结果
	 * @param @param user
	 * @param @return
	 * @return String
	 * @throws
	 */
	@WebMethod
	public String updateSubStatus(@WebParam(name = "subId") String subId, @WebParam(name = "subResult") String subResult);
	
	/**
	 * @Description: 获取医院就诊变更数据
	 * @param @param user
	 * @param @return
	 * @return String
	 * @throws
	 */
	@WebMethod
	public String getHospitalChange(@WebParam(name = "changejson") String changejson);
	
}
