package com.java.moudle.appoint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.service.DepartmentInfoService;
import com.java.moudle.webservice.service.DoctorInfoService;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;


@RestController
@RequestMapping("${regpath}/appoint")
public class AppointController extends BaseController {

    @Inject
    private HospitalInfoService hospitalInfoService;
    @Inject
    private DepartmentInfoService departmentInfoService;
    @Inject
    private DoctorInfoService doctorInfoService;
    @Inject
    private SubscribeService subscribeService;
   
    /**
     * @Description: 查询该区域的医院
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getHospitalList")
    public String getHospitalList(HospitalInfo hospitalInfo, PageModel page){
    	try{
    		hospitalInfoService.getHospitalList(hospitalInfo, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域的医院 惠民不分页
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getHospitalListNoPage")
    public String getHospitalListNoPage(HospitalInfo hospitalInfo){
    	try{
    		List<HospitalInfo> list = hospitalInfoService.getHospitalListNoPage(hospitalInfo);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查看医院详情
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getHospitalDetail")
    public String getHospitalDetail(String id){
    	try{
    		if(!StringUtil.isNull(id)) {
    			return jsonResult(hospitalInfoService.getHospitalDetail(id));
    		}else {
    			return jsonResult(null, 10000, "医院的标识为空");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	   
    /**
     * @Description: 查询该区域的科室（网站端）
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDepartmentList")
    public String getDepartmentList(DepartmentInfo info, PageModel page){
    	try{
    		if (info != null) {
    			departmentInfoService.getDepartmentList(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    @RequestMapping("getDepartmentInfoList")
    public String getDepartmentInfoList(DepartmentInfo info, PageModel page){
    	try{
    		if (info != null) {
    			departmentInfoService.getDepartmentInfoList(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域的科室惠民不分页
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDepartmentListNoPage")
    public String getDepartmentListNoPage(DepartmentInfo info){
    	try{
    		if (info != null) {
    			List<DepartmentInfo> list = departmentInfoService.getDepartmentListNoPage(info);
				return jsonResult(list);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询科室的详情
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDepartmentDetail")
    public String getDepartmentDetail(DepartmentInfo info){
    	try{
    		if (info != null) {
				return jsonResult(departmentInfoService.getDepartmentDetail(info));
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域的医生
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorList")
    public String getDoctorList(DoctorInfo info, PageModel page){
    	try{
    		if (info != null) {
    			doctorInfoService.getDoctorList(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域的医生惠民不分页
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorListNoPage")
    public String getDoctorListNoPage(DoctorInfo info){
    	try{
    		if (info != null) {
    			List<DoctorInfo> list = doctorInfoService.getDoctorListNoPage(info);
				return jsonResult(list);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域关注的医生
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorListFollow")
    public String getDoctorListFollow(DoctorInfo info, PageModel page){
    	try{
    		if (info != null) {
    			doctorInfoService.getFollowDoctorList(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询医生的详情
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorDetail")
    public String getDoctorDetail(String id){
    	try{
    		if (id != null) {
				return jsonResult(doctorInfoService.getDoctorInfoById(id));
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取区划和医院的总数
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getAreaAndHospitNum")
    public String getAreaAndHospitNum(){
    	try{
    		Map<String, Object> resultMap = new HashMap<>();
    		//获取医院的总数
    		String hospitalNum = hospitalInfoService.getHospitalNum();
    		//获取区划的总数
    		String areaNum = hospitalInfoService.getHospitalAreaNum();
    		resultMap.put("hospitalNum", hospitalNum);
    		resultMap.put("areaNum", areaNum);
    		return jsonResult(resultMap);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取某个医院的医生和预约量的总数
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorAndSubNum")
    public String getDoctorAndSubNum(String hospitId){
    	try{
    		if(StringUtil.isNull(hospitId)) 
    			return jsonResult("", 10000, "医院的唯一标识为空");
    		Map<String, Object> resultMap = new HashMap<>();
    		//获取医生总数
    		String doctorNum = doctorInfoService.getDoctorNumByHospitId(hospitId);
    		//获取预约量总数
    		String subNum = subscribeService.getSubNumByHospitId(hospitId);
    		resultMap.put("doctorNum", doctorNum);
    		resultMap.put("subNum", subNum);
    		return jsonResult(resultMap);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取某个科室的医生数量和预约数量
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDoctorNumAndSubNumByDept")
    public String getDoctorNumAndSubNumByDept(String hospitalId, String deptCode){
    	try{
    		if(StringUtil.isNull(hospitalId) || StringUtil.isNull(deptCode)) 
    			return jsonResult("", 10000, "医生的唯一标识为空");
    		return jsonResult(departmentInfoService.getDoctorNumAndSubNumByDept(hospitalId, deptCode));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取某个医生的预约和关注数量
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getSubNumAndConcernNum")
    public String getSubNumAndConcernNum(String doctorId){
    	try{
    		if(StringUtil.isNull(doctorId)) 
    			return jsonResult("", 10000, "医生的唯一标识为空");
    		return jsonResult(subscribeService.getSubNumAndConcernNum(doctorId));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 医生关注保存
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("saveDoctorConcern")
    public String saveDoctorConcern(String doctorId){
    	try{
    		if(StringUtil.isNull(doctorId)) 
    			return jsonResult("", 10000, "医生的唯一标识为空");
    		SysUser user = SysUtil.sysUser(request, response);
    		if(user == null)
    			return jsonResult("", 1004, "用户未登录");
    		return jsonResult(doctorInfoService.saveDoctorConcern(doctorId, user.getId()));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询医生是否被关注
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("queryDoctorConcern")
    public String queryDoctorConcern(String doctorId){
    	try{
    		if(StringUtil.isNull(doctorId)) 
    			return jsonResult("", 10000, "医生的唯一标识为空");
    		SysUser user = SysUtil.sysUser(request, response);
    		if(user == null)
    			return jsonResult("", 1004, "用户未登录");
    		return jsonResult(doctorInfoService.queryConcernInfo(doctorId, user.getId()));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 取消医生关注
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("quitDoctorConcern")
    public String quitDoctorConcern(String doctorId){
    	try{
    		if(StringUtil.isNull(doctorId)) 
    			return jsonResult("", 10000, "医生的唯一标识为空");
    		SysUser user = SysUtil.sysUser(request, response);
    		if(user == null)
    			return jsonResult("", 1004, "用户未登录");
    		doctorInfoService.quitDoctorConcern(doctorId, user.getId());
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询该区域的科室(后台管理)
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getDepartmentListByUser")
    public String getDepartmentListByUser(DepartmentInfo info, PageModel page){
    	try{
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		if (info != null) {
    			info.setHospitId(user.getHospitalInfo().getId());
    			departmentInfoService.getDepartmentList(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
}
