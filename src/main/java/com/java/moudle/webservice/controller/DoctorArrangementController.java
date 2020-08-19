package com.java.moudle.webservice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.domain.SubscribeInfoHistory;
import com.java.moudle.appoint.dto.SysSubscribeDto;
import com.java.moudle.appoint.service.SubscribeHistoryService;
import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.personal.domain.MedicalPersonnel;
import com.java.moudle.personal.service.MedicalPersonnelService;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.SubBlackBillService;
import com.java.moudle.system.service.SysUserService;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.dto.AliyunTemplateDto;
import com.java.moudle.webservice.dto.AppointDto;
import com.java.moudle.webservice.dto.AppointTimeDto;
import com.java.moudle.webservice.service.DepartmentInfoService;
import com.java.moudle.webservice.service.DoctorInfoService;
import com.java.until.AliyunSMSUtil;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * @ClassName: DoctorArrangementControllers
 * @Description: 从his获取医生的可预约情况
 * @author Administrator
 * @date 2019年12月2日
 */
@RestController
@RequestMapping("${regpath}/arrangment")
public class DoctorArrangementController extends BaseController {

	private String interUrl = "http://demo.sdboletong.com:8098/publichealth/ws/regDataExchange?wsdl";
	//private String interUrl = "http://10.2.0.36:8188/publichealth/ws/regDataExchange?wsdl";
    @Inject
	private SubscribeService subscribeService;
	@Inject
	private DoctorInfoService doctorInfoService;
	@Inject
	private SubscribeHistoryService subscribeHistoryService;
	@Inject
	private HospitalInfoService hospitalInfoService;
	@Inject
	private DepartmentInfoService departmentInfoService;
	@Inject
	private SysUserService userService;
	@Inject
	private SubBlackBillService subBlackBillService;
	@Inject
	private MedicalPersonnelService medicalPersonnelService;
	
	/**
	 * @Description: 获取医生的未来七天所在科室
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getDoctorDepart")
	public String getDoctorDepart(String doctorId){
        try {
        	if(StringUtil.isNull(doctorId)) {
        		return jsonResult(null, 10000, "医生标识为空");
        	}
        	DoctorInfo doctorInfo = doctorInfoService.get(doctorId);
        	
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getDoctorDepart", doctorInfo.getHisId());
            
            
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr))
    			return jsonResult("没有可以预约的科室");
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	List<AppointDto> depList = JSON.parseArray(dataJson, AppointDto.class);
            	if(depList == null) {
            		depList = new ArrayList<>();
            	}
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("departList", depList);
                return jsonResult(resultMap);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "获取医生的排班失败");
        }
    }
	
	/**
	 * @Description: (网站端)获取医生的预约排班(网站端)
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getDoctorAppointDate")
	public String getDoctorAppointDate(String doctorId, String departId){
        try {
        	DoctorInfo doctorInfo = doctorInfoService.get(doctorId);
        	HospitalInfo hospitalInfo = hospitalInfoService.get(doctorInfo.getHospitId());
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
        	JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getDoctorAppointDate", doctorInfo.getHisId(), departId);
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr))
    			return jsonResult("没有可以预约的排班");
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	
            	JSONObject dataObject = JSON.parseObject(dataJson);
            	String outDesc = hospitalInfo.getName()+"出诊时间："+dataObject.getString("outDesc");
            	String listJson = dataObject.getString("list");
            	List<AppointTimeDto> list = JSON.parseArray(listJson, AppointTimeDto.class);
            	Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("list", list);
                resultMap.put("out_desc", outDesc);
                return jsonResult(resultMap);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "获取医生的预约时间失败");
        }
    }
	/**
	 * @Description: 获取医生（上午、下午或者夜诊）的预约时间点
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getDoctorAppointTime")
	public String getDoctorAppointTime(String doctorId, String departId, String subDate){
        try {
            DoctorInfo doctorInfo = doctorInfoService.get(doctorId);
        	
            String type = "";
            String tempDate = subDate;
            tempDate = tempDate.replace("年", "-");
  
            tempDate = tempDate.replace("月", "-");
            tempDate = tempDate.replace("日", "");
            if(tempDate.contains("上午")) {
            	tempDate = tempDate.replace("上午", "");
            	type = "1";
            }else if(tempDate.contains("下午")) {
            	tempDate = tempDate.replace("下午", "");
            	type = "2";
            }else if(tempDate.contains("晚上")) {
            	tempDate = tempDate.replace("晚上", "");
            	type = "3";
            }
            
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getDoctorAppointTime", doctorInfo.getHisId(), departId, tempDate, type);
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr))
    			return jsonResult("没有可以预约的时间点");
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	List<String> list = JSON.parseArray(dataJson, String.class);
            	Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("list", list);
                return jsonResult(resultMap);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "获取医生的排班失败");
        }
    }
	
	/**
	 * @Description: 获取医生的预约排班(惠民平台app)
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getDoctorAppointDateApp")
	public String getDoctorAppointDateApp(String doctorId, String departId){
        try {
        	DoctorInfo doctorInfo = doctorInfoService.get(doctorId);
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
        	JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getDoctorAppointDateApp", doctorInfo.getHisId(), departId);
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr))
    			return jsonResult("没有可以预约的排班");
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	
            	JSONObject dataObject = JSON.parseObject(dataJson);
            	//String outDesc = hospitalInfo.getName()+"出诊时间："+dataObject.getString("outDesc");
            	String listJson = dataObject.getString("list");
            	List<AppointTimeDto> list = JSON.parseArray(listJson, AppointTimeDto.class);
               // resultMap.put("out_desc", outDesc);
                return jsonResult(list);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "获取医生的预约时间失败");
        }
    }
	
	/**
	 * @Description: 获取医生（上午、下午或者夜诊）的预约时间点
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getAppointTimeByDoctor")
	public String getAppointTimeByDoctor(String doctorId, String departId, String subDate){
        try {
            DoctorInfo doctorInfo = doctorInfoService.get(doctorId);
            
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getAppointTimeByDoctor", doctorInfo.getHisId(), departId, subDate);
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr))
    			return jsonResult("没有可以预约的时间点");
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	Map<String, Object> map = JSON.parseObject(dataJson, Map.class);
                return jsonResult(map);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "获取医生的排班失败");
        }
    }
	
    /**
     * @Description: 预约成功，给his返回预约id(网站端)
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("saveSubscribeInfo")
	public String saveSubscribeInfo(SubscribeInfo info, String subDate){
        try {
        	SysUser user = SysUtil.sysUser(request, response);
			int count = subBlackBillService.queryCapable(user.getId());
			int vailNum = subscribeService.getOverTimeStatusByUserId(user.getId());
        	if(count > 0 || vailNum > 2) {
        		return jsonResult(null, 10000, "您已失信三次；不能进行预约！");
        	}
        	
        	DoctorInfo doctorInfo = doctorInfoService.getDoctorInfoById(info.getDoctorId());
        	if(doctorInfo == null) {
        		doctorInfo = doctorInfoService.getDoctorInfoByHisId(info.getDoctorId(), "", "");
        		if(doctorInfo == null) {
        			return jsonResult(null, 10000, "医生为空");
        		}
        	}
			
			String subId = UUIDUtil.getUUID();
			if(StringUtil.isNull(subDate))
				return jsonResult(null, 10000, "就诊时间为空");
			//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("saveSubscribeInfo", subId, doctorInfo.getHisId(), subDate);
            
            String resultStr = (String)objects[0];
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            if("200".equals(retCode)) {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            	info.setId(subId);
            	info.setVisitTime(sdf.parse(subDate));
    			info.setStatus("1");
    			info.setCreateTime(new Date());
    			info.setCreateUser(user.getId());
    			info.setValidFlg("1");
    			subscribeService.save(info);
    			//发送短信
    			MedicalPersonnel medicalPersonnel = medicalPersonnelService.get(info.getMedicalPersonnelId());
    			HospitalInfo hospitalInfo = hospitalInfoService.get(doctorInfo.getHospitId());
    			DepartmentInfo depart = new DepartmentInfo();
    			depart.setCode(doctorInfo.getDeptCode());
    			depart.setHospitId(hospitalInfo.getId());
    			DepartmentInfo departmentDetail = departmentInfoService.getDepartmentDetail(depart);
    			AliyunTemplateDto message = new AliyunTemplateDto();
    			message.setName(medicalPersonnel.getName());
    			message.setOrgName(hospitalInfo.getName());
    			message.setDepName(departmentDetail.getName());
    			message.setDoctorName(doctorInfo.getName());
    			message.setSubTime(sdf.format(info.getVisitTime()));
    			message.setType("1");
    	    	AliyunSMSUtil.sendSubSms(medicalPersonnel.getPhone(), message);
            }
            return jsonResult();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "预约失败");
        }
    }
	
	/**
     * @Description: 取消预约(网站端)
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("cancelSubscribe")
	public String cancelSubscribe(String subId, String quitReason){
        try {
        	if(StringUtil.isNull(subId))
        		return jsonResult(null, 10000, "请选择预约数据");
        	SubscribeInfo subscribeInfo = subscribeService.get(subId);
        	DoctorInfo doctorInfo = doctorInfoService.get(subscribeInfo.getDoctorId());
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	if(subscribeInfo.getVisitTime() == null) {
        		return jsonResult(null, 10000, "预约时间不能为空");
        	}
        	//预约时间距离就诊时间小于24小时，不是取消
        	long time1 = subscribeInfo.getVisitTime().getTime();
        	long time2 = new Date().getTime();
        	if((time1-time2)/1000/60/60 < 24) {
        		return jsonResult(null, 10000, "取消时间距离就诊时间不能小于24小时"); 
        	}
        	SysUser user = SysUtil.sysUser(request, response);
        	if(user == null) {
        		return jsonResult(null, 10000, "未登录，请先登录！"); 
        	}
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("cancelSubscribe", subId, doctorInfo.getHisId(), sdf.format(subscribeInfo.getVisitTime()));
            
            String resultStr = (String)objects[0];
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            //his修改成功后
            if("200".equals(retCode)) {
            	SubscribeInfoHistory hisInfo = new SubscribeInfoHistory();
    			BeanUtils.copyProperties(subscribeInfo, hisInfo);
            	//更新预约表
                subscribeInfo.setStatus("4");
                subscribeInfo.setQuitReason(quitReason);
                subscribeService.save(subscribeInfo);
                //保存预约历史表
    			hisInfo.setSubscribeId(hisInfo.getId());
    			hisInfo.setId(UUIDUtil.getUUID());
    			hisInfo.setQuitReason(quitReason);
    			subscribeHistoryService.save(hisInfo);
    			//发送短信
    			MedicalPersonnel medicalPersonnel = medicalPersonnelService.get(subscribeInfo.getMedicalPersonnelId());
    			HospitalInfo hospitalInfo = hospitalInfoService.get(doctorInfo.getHospitId());
    			DepartmentInfo depart = new DepartmentInfo();
    			depart.setCode(doctorInfo.getDeptCode());
    			depart.setHospitId(hospitalInfo.getId());
    			DepartmentInfo departmentDetail = departmentInfoService.getDepartmentDetail(depart);
    			AliyunTemplateDto message = new AliyunTemplateDto();
    			message.setName(medicalPersonnel.getName());
    			message.setOrgName(hospitalInfo.getName());
    			message.setDepName(departmentDetail.getName());
    			message.setDoctorName(doctorInfo.getName());
    			message.setSubTime(sdf.format(subscribeInfo.getVisitTime()));
    			message.setType("2");
    	    	AliyunSMSUtil.sendSubSms(medicalPersonnel.getPhone(), message);
            }
            return jsonResult();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "预约失败");
        }
    }
	
	/**
     * @Description: 从his获取某个日期有排班的医生（惠民平台app）
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("getAppointDoctors")
	public String getAppointDoctors(String hospitalId, String deptCode, String parentCode, String queryDate, 
			String type, String pageSize, String pageNo){
        try {
			//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(hospitalId);
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getAppointDoctors", deptCode, queryDate, type, parentCode, pageSize, pageNo);
           
            int pageNo1 = StringUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
    		int pageSize1 = StringUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
            PageModel page = new PageModel(pageNo1, pageSize1);
            String resultStr = (String)objects[0];
            if(StringUtil.isNull(resultStr)) {
            	page.setList(new ArrayList<>());
        		page.setCount(0);
    			return jsonResult(page);
            }
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	List<DoctorInfo> resultList = new ArrayList<>();
            	
            	//获取分页属性
            	JSONObject dateObject = JSON.parseObject(dataJson);
            	Integer count = dateObject.getInteger("count");
            	String listJson = dateObject.getString("list");
            	List<DoctorInfo> list = JSON.parseArray(listJson, DoctorInfo.class);
            	if(list != null && list.size() > 0) {
            		for(DoctorInfo info : list) {
            			DoctorInfo main = doctorInfoService.getDoctorInfoByHisId(info.getId(), deptCode, hospitalId);
            			main.setOutpTime(info.getOutpTime());
            			main.setDeptId(info.getDeptId());
            			resultList.add(main);
            		}
            	}
            	page.setList(resultList);
            	page.setCount(count);
                return jsonResult(page);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(null, Integer.parseInt(retCode), retMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "预约失败");
        }
    }
	
	/**
     * @Description: 预约成功，给his返回预约id(惠民平台)
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("saveSubscribeByHC")
	public String saveSubscribeByHC(SubscribeInfo info, String subDate){
        try {
        	//确定预约人是否为本系统用户
        	SysUserDto user = userService.getUserDtoByUsername(info.getPhone());
        	if(user == null || user.getId() == null) {
        		return jsonResult("", 10000, "您不是预约挂号的用户！");
        	}
        	int count = subBlackBillService.queryCapable(user.getId());
        	int vailNum = subscribeService.getOverTimeStatusByUserId(user.getId());
        	if(count > 0 || vailNum > 2) {
        		return jsonResult("", 10000, "您已失信三次；不能进行预约！");
        	}
        	
        	DoctorInfo doctorInfo = doctorInfoService.getDoctorInfoById(info.getDoctorId());
        	if(doctorInfo == null) {
        		doctorInfo = doctorInfoService.getDoctorInfoByHisId(info.getDoctorId(), "", "");
        		if(doctorInfo == null) {
            		return jsonResult("", 10000, "没有医生数据");
        		}
        	}
			String subId = UUIDUtil.getUUID();
			if(StringUtil.isNull(subDate)) {
	    		return jsonResult("", 10000, "就诊时间为空！");
	    	}
			//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("saveSubscribeInfo", subId, doctorInfo.getHisId(), subDate);
            
            String resultStr = (String)objects[0];
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            if("200".equals(retCode)) {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            	info.setId(subId);
            	info.setVisitTime(sdf.parse(subDate));
    			info.setStatus("1");
    			info.setCreateTime(new Date());
    			info.setCreateUser(user.getId());
    			info.setValidFlg("1");
    			subscribeService.save(info);
    			//发送短信
    			MedicalPersonnel medicalPersonnel = medicalPersonnelService.get(info.getMedicalPersonnelId());
    			HospitalInfo hospitalInfo = hospitalInfoService.get(doctorInfo.getHospitId());
    			DepartmentInfo depart = new DepartmentInfo();
    			depart.setCode(doctorInfo.getDeptCode());
    			depart.setHospitId(hospitalInfo.getId());
    			DepartmentInfo departmentDetail = departmentInfoService.getDepartmentDetail(depart);
    			AliyunTemplateDto message = new AliyunTemplateDto();
    			message.setName(medicalPersonnel.getName());
    			message.setOrgName(hospitalInfo.getName());
    			message.setDepName(departmentDetail.getName());
    			message.setDoctorName(doctorInfo.getName());
    			message.setSubTime(sdf.format(info.getVisitTime()));
    			message.setType("1");
    	    	AliyunSMSUtil.sendSubSms(medicalPersonnel.getPhone(), message);
            }
    		return jsonResult(subId, 0, "预约成功");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
        }
    }
	
	/**
     * @Description: 取消预约(惠民平台)
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("cancelSubscribeHc")
	public String cancelSubscribeHc(String subId, String quitReason, String username){
        try {
        	//确定预约人是否为本系统用户
        	SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult("", 10000, "您不是预约挂号的用户！");
        	}
        	if(StringUtil.isNull(subId))
        		return jsonResult(null, 10000, "请选择预约数据");
        	SubscribeInfo subscribeInfo = subscribeService.get(subId);
        	DoctorInfo doctorInfo = doctorInfoService.get(subscribeInfo.getDoctorId());
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	if(subscribeInfo.getVisitTime() == null) {
        		return jsonResult(null, 10000, "预约时间不能为空");
        	}
        	//预约时间距离就诊时间小于24小时，不是取消
        	long time1 = subscribeInfo.getVisitTime().getTime();
        	long time2 = new Date().getTime();
        	if((time1-time2)/1000/60/60 < 24) {
        		return jsonResult(null, 10000, "取消时间距离就诊时间不能小于24小时"); 
        	}
        	//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(doctorInfo.getHospitId());
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("cancelSubscribe", subId, doctorInfo.getHisId(), sdf.format(subscribeInfo.getVisitTime()));
            
            String resultStr = (String)objects[0];
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            //his修改成功后
            if("200".equals(retCode)) {
            	SubscribeInfoHistory hisInfo = new SubscribeInfoHistory();
    			BeanUtils.copyProperties(subscribeInfo, hisInfo);
            	//更新预约表
                subscribeInfo.setStatus("4");
                subscribeInfo.setQuitReason(quitReason);
                subscribeService.save(subscribeInfo);
                //保存预约历史表
    			hisInfo.setSubscribeId(hisInfo.getId());
    			hisInfo.setId(UUIDUtil.getUUID());
    			hisInfo.setQuitReason(quitReason);
    			subscribeHistoryService.save(hisInfo);
    			//发送短信
    			MedicalPersonnel medicalPersonnel = medicalPersonnelService.get(subscribeInfo.getMedicalPersonnelId());
    			HospitalInfo hospitalInfo = hospitalInfoService.get(doctorInfo.getHospitId());
    			DepartmentInfo depart = new DepartmentInfo();
    			depart.setCode(doctorInfo.getDeptCode());
    			depart.setHospitId(hospitalInfo.getId());
    			DepartmentInfo departmentDetail = departmentInfoService.getDepartmentDetail(depart);
    			AliyunTemplateDto message = new AliyunTemplateDto();
    			message.setName(medicalPersonnel.getName());
    			message.setOrgName(hospitalInfo.getName());
    			message.setDepName(departmentDetail.getName());
    			message.setDoctorName(doctorInfo.getName());
    			message.setSubTime(sdf.format(subscribeInfo.getVisitTime()));
    			message.setType("2");
    	    	AliyunSMSUtil.sendSubSms(medicalPersonnel.getPhone(), message);
            }
            return jsonResult();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "预约失败");
        }
    }
	
	/**
     * @Description: 预约成功，查询预约订单的详细(惠民平台)
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("querySubscribeDetail")
	public String querySubscribeDetail(String subId){
        try {
        	SysSubscribeDto dto = subscribeService.querySubscribeDetail(subId);
    		return jsonResult(dto, 0, "查询成功");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
        }
    }
	
	/**
     * @Description: 从his获取某个日期有排班的医生（惠民平台app）
     * @param @return
     * @return JsonResult
     * @throws
     */
	@RequestMapping("getDoctorListApp")
	public String getDoctorListApp(DoctorInfo doctor, PageModel page){
        try {
			//获取医院his的webservice接口
        	//String interUrl = hospitalInfoService.getHisUrlByHospitId(hospitalId);
        	//判断查询是否关注
        	if("0".equals(doctor.getIsFollow())) {
        		doctorInfoService.getDoctorList(doctor, page);
        	}else {
        		//确定预约人是否为本系统用户
            	SysUserDto user = userService.getUserDtoByUsername(doctor.getCreateUser());
            	if(user == null || user.getId() == null) {
            		return jsonResult(null, 10000, "登录人不是预约挂号的用户！");
            	}
            	doctor.setUserId(user.getId());
        		doctorInfoService.getFollowDoctorList(doctor, page);
        	}
        	//把根据查询条件查询出的医生的id保存到list中
        	List<DoctorInfo> list = page.getList();
        	if(list == null || list.size() == 0) 
        		return jsonResult(page);
        	List<String> ids = new ArrayList<>();
    		for(DoctorInfo detail : list) {
        		ids.add(detail.getHisId());
        	}
        	// 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(interUrl);
            Object[] objects = new Object[0];
            objects = client.invoke("getDoctorListApp", ids);
            String resultStr = (String)objects[0];
            
            JSONObject parseObject = JSON.parseObject(resultStr);
            String retCode = parseObject.getString("retCode");
            String dataJson = parseObject.getString("data");
            if("200".equals(retCode)) {
            	List<DoctorInfo> hisList = JSON.parseArray(dataJson, DoctorInfo.class);
            	if(hisList != null && hisList.size() > 0) {
            		for(DoctorInfo info : list) {
            			for(DoctorInfo hisInfo : hisList) {
                			if(info.getHisId().equals(hisInfo.getId())) {
                				info.setOutpTime(hisInfo.getOutpTime());
                				info.setNum(hisInfo.getNum());
                			}
                		}
            		}
            	}
            	page.setList(list);
            	page.setCount(list.size());
                return jsonResult(page);
            }else {
            	String retMsg = parseObject.getString("retMsg");
            	return jsonResult(page, Integer.parseInt(retCode), retMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return jsonResult(null, -1, "系统错误");
        }
    }
}
