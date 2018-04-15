package com.fh.service.erp.deliverylog;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.util.Logger;
import com.fh.util.PageData;

@Component
@EnableScheduling
public class SynDeliveryLogTask {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "deliverylogService")
	private DeliveryLogManager deliverylogService;

	@Resource(name = "syndeliverylogService")
	private DeliveryLogManager syndeliverylogService;
	
	// 每2分钟执行
	/*@Scheduled(cron = "0 0/2 * * * ?")
	void doWork() {
		logger.info("同步物流日志任务开始......");
		long begin = System.currentTimeMillis();
		try {
			sysDeliveryLog();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("同步物流日志任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}*/

	private void sysDeliveryLog() throws Exception {
		PageData querySyn = new PageData();
		List<PageData> synList= deliverylogService.synListAll(querySyn);
		for (PageData pageData : synList) {
			Object delivery_log_id = pageData.get("delivery_log_id");
			Object result = syndeliverylogService.save(pageData);
			System.out.println(result);
			PageData updateStatus = new PageData();
			updateStatus.put("delivery_log_id", delivery_log_id);
			updateStatus.put("delivery_log_status", 1);
			updateStatus.put("updateby", "");
			updateStatus.put("update_time", new Date());
			deliverylogService.updateStatus(updateStatus);
		}
	}
}
