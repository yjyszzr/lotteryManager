package com.fh.schedule;


import com.fh.controller.lottery.datastatistics.MarketDataController;
import com.fh.entity.Page;
import com.fh.service.lottery.artifiprintlottery.ArtifiPrintLotteryManager;
import com.fh.service.lottery.artifiprintlotterystatisticaldata.ArtifiPrintLotteryStatisticalDataManager;
import com.fh.service.lottery.usermanagercontroller.impl.UserManagerControllerService;
import com.fh.util.DateUtilNew;
import com.fh.util.PageData;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class StatisticsSchedule {
	private final static Logger logger = LoggerFactory.getLogger(StatisticsSchedule.class);

	@Resource(name="artifiprintlotteryService")
	private ArtifiPrintLotteryManager artifiprintlotteryService;

	@Resource(name = "artifiprintlotterystatisticaldataService")
	private ArtifiPrintLotteryStatisticalDataManager artifiprintlotterystatisticaldataService;

	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerService userManagerControllerService;


	@Scheduled(cron = "52 * * * * ? ")
	public void moOrderStatistics()   {
		try {
			//付款金额和总数量
			PageData pdPaid = new PageData();
			pdPaid =	artifiprintlotteryService.findPaidLimitDay(pdPaid);
			if (pdPaid != null) {
			pdPaid =artifiprintlotteryService.statisticalPaidData(pdPaid);
				PageData pdPaidHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdPaid);
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
			if (pdPrint != null) {
			pdPrint =artifiprintlotteryService.statisticalPrintData(pdPrint);
				PageData pdPrintHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdPrint);
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
			if (pdReward != null) {
			pdReward =artifiprintlotteryService.statisticalRewardData(pdReward);
				PageData pdRewardHasStatisticalA =artifiprintlotterystatisticaldataService.findByTime(pdReward);
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



	//0 0 6 * * ?
	@Scheduled(cron = "0 0 6 * * ?")
	public void marketDataStatistics() throws Exception  {
		logger.info("开始收集当天的市场数据))))");
		MarketDataController marketDataController = new MarketDataController();
		Integer todayCount = userManagerControllerService.getmarketCountToday(new PageData());
		if(null != todayCount && 0 < todayCount){
			return;
		}

		Integer lastStart1 = DateUtilNew.getTimeAfterDays(new Date(),0,0,0,0) - 86400;
		Integer lastEnd1 = DateUtilNew.getTimeAfterDays(new Date(),0,23,59,59) - 86400;

		Page page = new Page();
		PageData pd = new PageData();
		LocalDate dateE = LocalDate.now();
		LocalDate dateB = LocalDate.now();

		String lastEnd = String.valueOf(lastEnd1);//pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(DateUtilNew.getCurrentTimeString(Long.valueOf(lastEnd1),DateUtilNew.date_sdf), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastStart = String.valueOf(lastStart1);//pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(DateUtilNew.getCurrentTimeString(Long.valueOf(lastStart1),DateUtilNew.date_sdf), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			dateB = dateE.plusDays(-1);
		}
		int days = (int) (dateE.toEpochDay()-dateB.toEpochDay());
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < days+1; i++) {
			PageData pageData = new PageData();
			LocalDate date = dateE.plusDays(-i);//当天的前i天
			pd.put("lastStart1",DateUtilNew.getMilliSecondsByStr(date+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(date+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = userManagerControllerService.getMarketList(page);
			for (int k = 0; k < userList.size(); k++) {
				pageData = userList.get(k);
				pageData.put("date", date);
				int userCount = Integer.parseInt(pageData.getString("count_order"));
				String device_channel = pageData.getString("device_channel");
				pageData.put("count2", getCount(date, date, 1, 1, userCount, device_channel));
				pageData.put("count3", getCount(date, date, 2, 2, userCount, device_channel));
				pageData.put("count7", getCount(date, date, 3, 6, userCount, device_channel));
				pageData.put("count15", getCount(date, date, 7, 14, userCount, device_channel));
				pageData.put("count30", getCount(date, date, 15, 29, userCount, device_channel));
				pageData.put("count90", getCount(date, date, 30, 89, userCount, device_channel));
				pageData.put("count180", getCount(date, date, 90, 179, userCount, device_channel));
				pageData.put("count360", getCount(date, date, 180, 359, userCount, device_channel));
				pageData.put("date_time",DateUtilNew.getCurrentTimeString(Long.valueOf(lastStart1), DateUtilNew.date_sdf));
				pageData.put("add_time",lastStart1);
				varList.add(pageData);
			}
		}

		if(CollectionUtils.isEmpty(varList)){
			return;
		}

		for (int i = 0;i < varList.size();i++){
			PageData insertPd = varList.get(i);
			userManagerControllerService.saveMarketData(insertPd);
		}

		logger.info("结束收集当天的市场数据))))");
	}


	public List<PageData> queryCommon(Page page){

		return null;
	}


	/**
	 * 获得留存数据
	 *
	 * @param regTime 注册日
	 * @param start 留存起（天）
	 * @param end 留存止（天）
	 * @param userCount 注册日总人数
	 * @throws Exception
	 */
	public BigDecimal getCount(LocalDate regTime, LocalDate endTime, int start, int end, int userCount, String device_channel) throws Exception {
		LocalDate startDate = regTime.plusDays(start);
		LocalDate endDate = regTime.plusDays(end);
		LocalDate nowDate = LocalDate.now();
		int days = Period.between(startDate,nowDate).getDays();
		int months = Period.between(startDate,nowDate).getMonths();
		int years = Period.between(startDate,nowDate).getYears();
		if(days<0 || months<0 || years<0) {
			return null;
		}
		PageData pd = new PageData();
		Page page = new Page();

		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(regTime+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(endTime+" 23:59:59"));

		pd.put("lastStart2", DateUtilNew.getMilliSecondsByStr(startDate+" 00:00:00"));
		pd.put("lastEnd2",DateUtilNew.getMilliSecondsByStr(endDate+" 23:59:59"));
		pd.put("device_channel",device_channel);
		page.setPd(pd);
		List<PageData> count = userManagerControllerService.getRemainUserCount(page);
		String remainCount = count.get(0).getString("count");
		BigDecimal userc = new BigDecimal(userCount);
		BigDecimal remainc = new BigDecimal(remainCount+"00");
		if(userc.compareTo(BigDecimal.ZERO)==0) {
			return userc;
		}
		return remainc.divide(userc, 2,BigDecimal.ROUND_HALF_UP);
	}

}
