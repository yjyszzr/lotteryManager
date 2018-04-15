package com.fh.service.erp.synshop;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.util.Logger;

/*@Component
@EnableScheduling
public class SynStockTask {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "synStockService")
	private SynStockManager synStockService;

	// 每2分钟执行
	@Scheduled(cron = "0 0/2 * * * ?")
	void doworkInbound() {
		logger.info("入库同步库存任务开始......");
		long begin = System.currentTimeMillis();
		try {
			synStockService.SynInbound();
		} catch (Exception ee) {
		}
		long end = System.currentTimeMillis();
		logger.info("入库同步库存任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}

	@Scheduled(cron = "0 0/2 * * * ?")
	void doworkOutbound() {
		logger.info("出库同步库存任务开始......");
		long begin = System.currentTimeMillis();
		try {
			synStockService.SynOutbound();
		} catch (Exception ee) {
		}
		long end = System.currentTimeMillis();
		logger.info("出库同步库存结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}
	
	@Scheduled(cron = "0 0/2 * * * ?")
	void doworkOrder() {
		logger.info("出库订单发货状态任务开始......");
		long begin = System.currentTimeMillis();
		try {
			synStockService.SynOrderShippingStatus();
		} catch (Exception ee) {
		}
		long end = System.currentTimeMillis();
		logger.info("出库订单发货状态任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}
}*/