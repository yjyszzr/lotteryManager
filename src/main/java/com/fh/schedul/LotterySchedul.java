package com.fh.schedul;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fh.service.lottery.channel.ChannelConsumerManager;
import com.fh.service.lottery.channel.ChannelDistributorManager;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.Logger;
import com.fh.util.PageData;

@Configuration
@EnableScheduling
public class LotterySchedul {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Resource(name = "orderService")
	private OrderManager orderService;
	@Resource(name = "channeloptionlogService")
	private ChannelOptionLogManager channeloptionlogService;
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "channeldistributorService")
	private ChannelDistributorManager channeldistributorService;
	@Resource(name = "channelconsumerService")
	private ChannelConsumerManager channelconsumerService;

	/**
	 * 更新新注册用户和订单到渠道操作记录表
	 * 
	 * @throws Exception
	 *             每分钟跑一次
	 * @Scheduled(cron = "0 0/1 * * * ?")
	 */
	// 每小时跑一次
	@Scheduled(cron = "0 0 * * * ?")
	public void updateNewUserAndOrderToChannelOperationLog() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -1);// 1小时前
		logger.info("每小时扫描一次===============扫描开始");
		int insertNum = 0;
		List<PageData> channelOperationList = new ArrayList<PageData>();
		List<PageData> userList = usermanagercontrollerService.findAll();
		PageData pd = new PageData();
		List<PageData> distributorList = channeldistributorService.listAll(pd);
		List<PageData> orderList = orderService.selectByTime(sdf.format(c.getTime()));
		// 封装下单所有的信息
		if (orderList.size() > 0) {
			for (int i = 0; i < orderList.size(); i++) {
				PageData channelOperationPageData = new PageData();
				for (int j = 0; j < userList.size(); j++) {
					if (orderList.get(i).getString("user_id").equals(userList.get(j).get("user_id"))) {
						// 封装用户相关信息
						channelOperationPageData.put("user_name", userList.get(j).get("user_name"));
						channelOperationPageData.put("id_card_num", userList.get(j).get("id_code"));
						channelOperationPageData.put("true_name", userList.get(j).get("real_name"));
						channelOperationPageData.put("mobile", userList.get(j).get("mobile"));
					}
				}
				for (int k = 0; k < distributorList.size(); k++) {
					if (orderList.get(i).getString("user_id").equals(distributorList.get(k).get("user_id"))) {
						// 封装消费者的所属店员以及店铺信息
						channelOperationPageData.put("channel_id", distributorList.get(k).get("channel_id"));
						channelOperationPageData.put("distributor_id", distributorList.get(k).get("channel_distributor_id"));
					}
				}
				// 封装下单信息
				channelOperationPageData.put("user_id", orderList.get(i).getString("user_id"));
				channelOperationPageData.put("operation_node", "2");
				channelOperationPageData.put("status", "1");
				channelOperationPageData.put("source", "h5");
				channelOperationPageData.put("option_amount", orderList.get(i).getString("money_paid"));
				channelOperationPageData.put("option_time", orderList.get(i).getString("ticket_time"));
				channelOperationList.add(channelOperationPageData);
			}
		}

		// 封装用户首次登录所有信息
		List<PageData> consumerList = channelconsumerService.selectByTime(sdf.format(c.getTime()));
		for (int i = 0; i < consumerList.size(); i++) {
			PageData channelOperationPageData = new PageData();
			for (int j = 0; j < userList.size(); j++) {
				if (consumerList.get(i).getString("user_id").equals(userList.get(j).get("user_id"))) {
					// 封装用户相关信息
					channelOperationPageData.put("user_name", userList.get(j).get("user_name"));
					channelOperationPageData.put("id_card_num", userList.get(j).get("id_code"));
					channelOperationPageData.put("true_name", userList.get(j).get("real_name"));
					channelOperationPageData.put("mobile", userList.get(j).get("mobile"));
				}
			}
			for (int k = 0; k < distributorList.size(); k++) {
				if (consumerList.get(i).getString("user_id").equals(distributorList.get(k).get("user_id"))) {
					// 封装消费者的所属店员以及店铺信息
					channelOperationPageData.put("channel_id", distributorList.get(k).get("channel_id"));
					channelOperationPageData.put("distributor_id", distributorList.get(k).get("channel_distributor_id"));
				}
			}
			// 封装首次登录信息
			channelOperationPageData.put("user_id", consumerList.get(i).getString("user_id"));
			channelOperationPageData.put("operation_node", "1");
			channelOperationPageData.put("status", "1");
			channelOperationPageData.put("source", "h5");
			channelOperationPageData.put("option_amount", "");
			channelOperationPageData.put("option_time", consumerList.get(i).getString("frist_login_time"));
			channelOperationList.add(channelOperationPageData);
		}
		if (channelOperationList.size() > 0) {
			insertNum = channeloptionlogService.insertList(channelOperationList);
		}
		logger.info(" 扫描结束,更新完成!共更新" + insertNum + "条。");
	}
}
