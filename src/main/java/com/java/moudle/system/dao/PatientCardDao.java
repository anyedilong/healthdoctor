package com.java.moudle.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.PatientCardRepository;
import com.java.moudle.system.domain.PatientCard;
import com.java.moudle.tripartdock.dto.PatientCardDto;
import com.java.moudle.tripartdock.dto.PatientPersonDto;
import com.java.until.dba.BaseDao;


@Named
public class PatientCardDao extends BaseDao<PatientCardRepository, PatientCard> {

	
	public List<PatientCard> getCardNum(String hospitalId, String mpId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from patient_card a ");
		sql.append(" where a.hospital_id = :hospitalId ");
		sql.append(" and a.mp_id = :mpId ");
		sql.append(" and a.status = '0' ");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("hospitalId", hospitalId);
		paramMap.put("mpId", mpId);
		return queryList(sql.toString(), paramMap, PatientCard.class);
	}
	
	public List<PatientPersonDto> getCardNumInfoList(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name ");
		sql.append(" from medical_personnel a ");
		sql.append(" where a.user_id = :userId ");
		sql.append(" and a.status = '1' ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		List<PatientPersonDto> list = queryList(sql.toString(), paramMap, PatientPersonDto.class);
		if(list != null && list.size() > 0) {
			for(PatientPersonDto dto : list) {
				StringBuffer sql1 = new StringBuffer();
				sql1.append(" select r.id, r.card_num, i.name as hospitalName, i.image_url ");
				sql1.append(" from patient_card r ");
				sql1.append(" join hospital_info i on r.hospital_id = i.id ");
				sql1.append(" where r.mp_id = :mpId ");
				sql1.append(" and r.status = '0' ");
				Map<String, Object> paramMap1 = new HashMap<>();
				paramMap1.put("mpId", dto.getId());
				List<PatientCardDto> pcList = queryList(sql1.toString(), paramMap1, PatientCardDto.class);
				if(pcList != null && pcList.size() > 0) {
					dto.setNum(pcList.size()+"");
					dto.setPcards(pcList);
				}else {
					dto.setNum("0");
					dto.setPcards(new ArrayList<>());
				}
			}
		}
		return list;
	}
	
	public void deleteMpCard(String id) {
		repository.deleteMpCard(id);
	}
}
