package com.fh.schedule;



import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fh.service.lottery.artifiprintlottery.ArtifiPrintLotteryManager;
import com.fh.service.lottery.artifiprintlotterystatisticaldata.ArtifiPrintLotteryStatisticalDataManager;
import com.fh.service.lottery.logoperation.LogOperationManager;
import com.fh.util.PageData;

@Configuration
@EnableScheduling
public class StatisticsSchedule {
	@Resource(name="artifiprintlotteryService")
	private ArtifiPrintLotteryManager artifiprintlotteryService;
	
	@Resource(name = "artifiprintlotterystatisticaldataService")
	private ArtifiPrintLotteryStatisticalDataManager artifiprintlotterystatisticaldataService;
	
 
	@Scheduled(cron = "52 * * * * ? ")
	public void moOrderStatistics()   {
		try {
			//付款金额和总数量
			PageData pdPaid = new PageData();
			pdPaid =	artifiprintlotteryService.findPaidLimitDay(pdPaid);
			pdPaid =artifiprintlotteryService.statisticalPaidData(pdPaid);
			System.out.println("pdPaid==================================================="+pdPaid);
			if (pdPaid != null) {
				PageData pdPaidHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdPaid);
					System.out.println("未统计的付款数pdPaidHasStatistical====================================="+pdPaidHasStatisticalA);
					if (pdPaidHasStatisticalA == null) {
						pdPaidHasStatisticalA	= new PageData();
						pdPaidHasStatisticalA.put("id",0);
						pdPaidHasStatisticalA.put("paid_order_num",pdPaid.getString("paid_count"));
						pdPaidHasStatisticalA.put("data_str", pdPaid.getString("add_time"));
						pdPaidHasStatisticalA.put("total_award_amount", "0");
						pdPaidHasStatisticalA.put("print_num", "0");
						BigDecimal moneyPaid =new BigDecimal(pdPaid.getString("money_paid"));
						BigDecimal bd100 =new BigDecimal("100");
						pdPaidHasStatisticalA.put("total_paid_amount",moneyPaid.divide(bd100));
						artifiprintlotterystatisticaldataService.savePaidStatistical(pdPaidHasStatisticalA);
					}else {
						pdPaidHasStatisticalA.put("paid_order_num",Integer.parseInt(pdPaidHasStatisticalA.getString("paid_order_num").equals("") ? "0" : pdPaidHasStatisticalA.getString("paid_order_num") )+Integer.parseInt(pdPaid.getString("paid_count") ));
						BigDecimal moneyPaid =new BigDecimal(pdPaid.getString("money_paid"));
						BigDecimal pd100 =new BigDecimal(100);
						BigDecimal b =new BigDecimal(pdPaidHasStatisticalA.getString("total_paid_amount").equals("") ? "0" : pdPaidHasStatisticalA.getString("total_paid_amount"));
						pdPaidHasStatisticalA.put("total_paid_amount",moneyPaid.divide(pd100).add(b).toString());
						artifiprintlotterystatisticaldataService.editPaidStatistical(pdPaidHasStatisticalA);
					}
					String orderSn = pdPaid.getString("order_sn");
					String ArrayDATA_IDS[] = orderSn.split(",");
					artifiprintlotteryService.updatePaidStatisticalByOrderSn(ArrayDATA_IDS);
			}
			
			//出票量
			PageData pdPrint = new PageData();
			pdPrint =	artifiprintlotteryService.findPrintLimitDay(pdPrint);
			pdPrint =artifiprintlotteryService.statisticalPrintData(pdPrint);
			System.out.println("pdPrint======================================="+pdPrint);
			if (pdPrint != null) {
				PageData pdPrintHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdPrint);
				System.out.println("未统计的出票数pdPrintHasStatistical==========="+pdPrintHasStatisticalA);
				if (pdPrintHasStatisticalA == null) {
//					pdPrintHasStatisticalA	= new PageData();
//					pdPrintHasStatisticalA.put("id",0);
//					pdPrintHasStatisticalA.put("print_num",pdPrint.getString("print_count"));
//					pdPrintHasStatisticalA.put("data_str", pdPrint.getString("add_time"));
//					artifiprintlotterystatisticaldataService.savePrintStatistical(pdPrintHasStatisticalA);
				}else {
					pdPrintHasStatisticalA.put("print_num",Integer.parseInt(pdPrintHasStatisticalA.getString("print_num").equals("") ? "0" : pdPrintHasStatisticalA.getString("print_num"))+Integer.parseInt(pdPrint.getString("print_count") ));
					artifiprintlotterystatisticaldataService.editPrintStatistical(pdPrintHasStatisticalA);
					String orderSn = pdPrint.getString("order_sn");
					String ArrayDATA_IDS[] = orderSn.split(",");
					artifiprintlotteryService.updatePrintStatisticalByOrderSn(ArrayDATA_IDS);
				}
			}
			
			//	派奖量
			PageData pdReward = new PageData();
			pdReward=artifiprintlotteryService.findRewardLimitDay(pdReward);
			pdReward =artifiprintlotteryService.statisticalRewardData(pdReward);
			System.out.println("pdReward=========================================="+pdReward);
			if (pdReward != null) {
				PageData pdRewardHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdReward);
				System.out.println("未统计的派奖数pdRewardHasStatistical============================================================"+pdRewardHasStatisticalA);
					if (pdRewardHasStatisticalA == null) {
//						pdRewardHasStatisticalA	= new PageData();
//						pdRewardHasStatisticalA.put("id",0);
//						pdRewardHasStatisticalA.put("data_str", pdReward.getString("add_time"));
//						pdRewardHasStatisticalA.put("total_award_amount",pdReward.getString("total_winning_money"));
//						artifiprintlotterystatisticaldataService.saveRewardStatistical(pdRewardHasStatisticalA);
					}else {
						BigDecimal a =new BigDecimal(pdReward.getString("total_winning_money"));
						BigDecimal b =new BigDecimal(pdRewardHasStatisticalA.getString("total_award_amount").equals("") ? "0" : pdRewardHasStatisticalA.getString("total_award_amount"));
						pdRewardHasStatisticalA.put("total_award_amount",a.add(b).toString());
						artifiprintlotterystatisticaldataService.editRewardStatistical(pdRewardHasStatisticalA);
						String orderSn = pdReward.getString("order_sn");
						String ArrayDATA_IDS[] = orderSn.split(",");
						artifiprintlotteryService.updateRewardStatisticalByOrderSn(ArrayDATA_IDS);
					}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	
	}
}
