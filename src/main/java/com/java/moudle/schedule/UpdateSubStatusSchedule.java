package com.java.moudle.schedule;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.domain.SubscribeInfoHistory;
import com.java.moudle.appoint.service.SubscribeHistoryService;
import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.system.domain.SubBlackBill;
import com.java.moudle.system.service.SubBlackBillService;
import com.java.until.ToJavaUtils;
import com.java.until.UUIDUtil;

/**
 * 	预约数据更新
 * @author Administrator
 */

@Component
@Configuration      
@EnableScheduling 
public class UpdateSubStatusSchedule {

	@Inject
	private SubscribeService subscribeService;
	@Inject
	private SubscribeHistoryService subscribeHistoryService;
	@Inject
	private SubBlackBillService subBlackBillService;
	
	//更新过期的预约数据
	@Scheduled(cron="0 0 * * * ?")
	private void updateSubExec() {
		try {
			List<SubscribeInfo> list = subscribeService.getOverTimeSubList();
			if(list != null && list.size() > 0) {
				for(SubscribeInfo info : list) {
					SubscribeInfoHistory hisInfo = new SubscribeInfoHistory();
					ToJavaUtils.copyFields(info, hisInfo);
			    	//更新预约表
			        info.setStatus("3");
			        info.setValidFlg("1");
			        subscribeService.save(info);
			        //保存预约历史表
					hisInfo.setSubscribeId(hisInfo.getId());
					hisInfo.setId(UUIDUtil.getUUID());
					subscribeHistoryService.save(hisInfo);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//归档无故爽约的用户
	@Scheduled(cron="0 0 * * * ?")
	private void statsSubExec() {
		try {
			List<String> list = subscribeService.getOverTimeStatusList();
			if(list != null && list.size() > 0) {
				for(String userId : list) {
					SubBlackBill info = new SubBlackBill();
					info.setId(UUIDUtil.getUUID());
					info.setUserId(userId);
					info.setCreateTime(new Date());
					subBlackBillService.save(info);
					subscribeService.updateSubValidFlg(userId, "2");
				}
			}
			subBlackBillService.deleteBeforeThreeMonthData();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
