package com.java.moudle.hospital.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.hospital.dao.repository.HospitalInfoRepository;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class HospitalInfoDao extends BaseDao<HospitalInfoRepository, HospitalInfo> {

	/**
	 * @Description: 根据区划、名称、是否推荐和科室查询医院list
	 * @param @param hospitalInfo
	 * @param @return
	 * @return List<HospitalInfo>
	 * @throws
	 */
	public void getHospitalList(HospitalInfo hospitalInfo, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.area_code, a.level_type, to_char(a.introduce) as introduce, ");
		sql.append("  a.telephone, a.address, a.image_url, a.recommend ");
		sql.append(" from hospital_info a ");
		sql.append(" where a.status = '2' ");
		if (!StringUtil.isNull(hospitalInfo.getAreaCode())) {
			sql.append(" and a.area_code like concat(:areaCode, '%') ");
		}
		if (!StringUtil.isNull(hospitalInfo.getName())) {
			sql.append(" and a.name like concat(concat('%', :name), '%') ");
		}
		if (!StringUtil.isNull(hospitalInfo.getRecommend())) {
			sql.append(" and a.recommend  = :recommend ");
		}
		if(!StringUtil.isNull(hospitalInfo.getType())) {
			sql.append(" and a.type = :type ");
		}
		if(!StringUtil.isNull(hospitalInfo.getLevelType())) {
			sql.append(" and a.level_type = :levelType ");
		}
		if (!StringUtil.isNull(hospitalInfo.getDeptCodes())) {
			List<String> deptCodeList = Arrays.asList(hospitalInfo.getDeptCodes().split(","));
			sql.append(" and a.id in ( ");
			sql.append(" 	select hospit_id from department_info where code in ( ");
			for (int i = 0; deptCodeList.size() > i; i++) {
				if (i == deptCodeList.size() - 1) {
					sql.append("'" + deptCodeList.get(i) + "'");
				} else {
					sql.append("'" + deptCodeList.get(i) + "',");
				}
			}
			sql.append("))");
		}
		sql.append(" order by a.create_time desc ");
		queryPageList(sql.toString(), hospitalInfo, page, HospitalInfo.class);
	}
	/**
	 * @Description: 获取医院的总个数
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getHospitalNum() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) ");
		sql.append(" from hospital_info a ");
		return queryOne(sql.toString(), null, String.class);
	}
	/**
	 * @Description: 获取医院所在区划的总个数
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getHospitalAreaNum() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from  ");
		sql.append(" 	(select area_code ");
		sql.append(" 	 from hospital_info a group by area_code ");
		sql.append(" 	) ");
		return queryOne(sql.toString(), null, String.class);
	}
	/**
	 * @Description: 获取医院的his服务地址
	 * @param @param id
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getHisUrlByHospitId(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.his_interface_url ");
		sql.append(" from hospital_info a ");
		sql.append(" where a.status = '2' and a.id = :id ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return queryOne(sql.toString(), paramMap, String.class);
	}
	/**
	 * @Description: 获取医院的详情
	 * @param @param id
	 * @param @return
	 * @return HospitalInfo
	 * @throws
	 */
	public HospitalInfo getHospitalDetail(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.area_code, a.level_type, to_char(a.introduce) as introduce, a.telephone, a.address, ");
		sql.append("  a.image_url, a.recommend, a.legal_parson, a.legal_idcard_front, a.legal_idcard_reverse, a.bussiness_license, ");
		sql.append("  a.his_interface_url, a.status, a.type, a.create_user, a.create_time, a.remark ");
		sql.append(" from hospital_info a ");
		sql.append(" where a.id = :id ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return queryOne(sql.toString(), paramMap, HospitalInfo.class);
	}
	/**
	 * @Description: 获取审核和管理医院的列表(分页)
	 * @param @param hospitalInfo
	 * @param @param page
	 * @return void
	 * @throws
	 */
	public void getHospitalPage(HospitalInfo hospitalInfo, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.legal_parson, a.telephone, a.his_interface_url, a.status, a.create_time ");
		sql.append(" from hospital_info a ");
		sql.append(" where 1 = 1 ");
		sql.append(" and a.status in ('1', '2') ");
		if(!StringUtil.isNull(hospitalInfo.getType())) {
			sql.append(" and a.type = :type ");
		}
		if(!StringUtil.isNull(hospitalInfo.getStartDate())) {
			sql.append(" and a.create_time >= to_date(:startDate, 'yyyy-mm-dd') ");
		}
		if(!StringUtil.isNull(hospitalInfo.getEndDate())) {
			sql.append(" and a.create_time <= to_date(:endDate, 'yyyy-mm-dd') ");
		}
		sql.append(" order by a.create_time desc ");
		queryPageList(sql.toString(), hospitalInfo, page, HospitalInfo.class);
	}
	
	/**
	 * @Description: 修改机构的状态
	 * @param @param id
	 * @param @param status
	 * @return void
	 * @throws
	 */
	public void updateHospitalStatus(String id, String status, String remark) {
		HospitalInfo info = this.getHospitalDetail(id);
		info.setStatus(status);
		if(!StringUtil.isNull(remark)) {
			info.setRemark(remark);
		}
		repository.save(info);
	}
	public List<HospitalInfo> getHospitalListNoPage(HospitalInfo hospitalInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.area_code, a.level_type, to_char(a.introduce) as introduce, ");
		sql.append("  a.telephone, a.address, a.image_url, a.recommend ");
		sql.append(" from hospital_info a ");
		sql.append(" where a.status = '2' ");
		if (!StringUtil.isNull(hospitalInfo.getAreaCode())) {
			sql.append(" and a.area_code like concat(:areaCode, '%') ");
		}
		if (!StringUtil.isNull(hospitalInfo.getName())) {
			sql.append(" and a.name like concat(concat('%', :name), '%') ");
		}
		if (!StringUtil.isNull(hospitalInfo.getRecommend())) {
			sql.append(" and a.recommend  = :recommend ");
		}
		if(!StringUtil.isNull(hospitalInfo.getType())) {
			sql.append(" and a.type = :type ");
		}
		if (!StringUtil.isNull(hospitalInfo.getDeptCodes())) {
			List<String> deptCodeList = Arrays.asList(hospitalInfo.getDeptCodes().split(","));
			sql.append(" and a.id in ( ");
			sql.append(" 	select hospit_id from department_info where code in ( ");
			for (int i = 0; deptCodeList.size() > i; i++) {
				if (i == deptCodeList.size() - 1) {
					sql.append("'" + deptCodeList.get(i) + "'");
				} else {
					sql.append("'" + deptCodeList.get(i) + "',");
				}
			}
			sql.append("))");
		}
		sql.append(" order by a.create_time desc ");
		return queryList(sql.toString(), hospitalInfo, HospitalInfo.class);
	}
}
