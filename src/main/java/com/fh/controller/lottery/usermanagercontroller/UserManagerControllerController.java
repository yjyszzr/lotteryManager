package com.fh.controller.lottery.usermanagercontroller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.Page;
import com.fh.entity.dto.CountPersonDTO;
import com.fh.entity.dto.SysUserDTO;
import com.fh.entity.sms.RspSmsCodeEntity;
import com.fh.enums.ThirdApiEnum;
import com.fh.service.lottery.activitybonus.impl.ActivityBonusService;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.NetWorkUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RandomUtil;
import com.fh.util.SmsUtil;

import net.sf.json.JSONObject;

/**
 * 说明：用户列表 创建人：FH Q313596790 创建时间：2018-04-23
 */
@Controller
@RequestMapping(value = "/usermanagercontroller")
public class UserManagerControllerController extends BaseController {

	private final static String USER_SESSION_PREFIX = "US:";

	String menuUrl = "usermanagercontroller/list.do"; // 菜单地址(权限用)

	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;

	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;

	@Resource(name = "userrealmanagerService")
	private UserRealManagerService userrealmanagerService;

	@Resource(name = "userbankmanagerService")
	private UserBankManagerService userbankmanagerService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	@Resource(name = "redisDaoImpl")
	private RedisDaoImpl redisDaoImpl;

	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;

	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "activitybonusService")
	private ActivityBonusService activityBonusService;
	
	private final static Logger logFac = LoggerFactory.getLogger(UserManagerControllerController.class);

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增UserManagerController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("user_id", this.get32UUID()); //主键
		pd.put("user_id", "0"); // 备注1
		pd.put("user_name", ""); // 备注2
		pd.put("mobile", ""); // 备注3
		pd.put("email", ""); // 备注4
		pd.put("reg_time", "0"); // 备注17
		pd.put("last_time", "0"); // 备注19
		pd.put("reg_from", ""); // 备注24
		pd.put("user_status", "0"); // 备注27
		usermanagercontrollerService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除UserManagerController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		usermanagercontrollerService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改UserManagerController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		usermanagercontrollerService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/changeUserSwitch")
	public ModelAndView changeUserSwitch() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData userEntity = usermanagercontrollerService.findById(pd);
		usermanagercontrollerService.changeUserSwitch(pd);
		String text ="is_business: "+userEntity.getString("is_business")+ " -> "+pd.getString("is_business");
		ACLOG.save("1", "0", "用户列表：交易版开关  "+pd.getString("user_id"), text);
		mv = getDetailView(mv);
		return mv;
	}


	/*
	 *  销售人员业绩总列表
	 */
	@RequestMapping(value = "/listSA")
	public ModelAndView sellerAchievementList(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Calendar cal = Calendar.getInstance();
		Integer curMonth = cal.get(Calendar.MONTH) + 1; 
		pd.put("curMonth", curMonth);
		page.setPd(pd);
		
		//月增加用户数 总增加用户数
		List<PageData> varList = usermanagercontrollerService.sellerAchieveList(page);
		
		//销售人员总红包数量(分库查询 进行组装)
		Map<String,String> bonusMap = new HashMap<>();
		for(PageData varPd:varList) {
			List<String> userIds =  usermanagercontrollerService.queryUserIdsBySellersId(varPd.getString("last_add_seller_id"));
			if(userIds.size() > 0) {
				PageData bonusTotal =  activityBonusService.sellerUserBonushTotal(userIds);
				if(bonusTotal != null) {
					bonusMap.put(varPd.getString("last_add_seller_id"), bonusTotal.getString("totalBonus"));
				}
			}
		}
		
		PageData cuPd = new PageData();
		cuPd.put("phone", pd.getString("phone"));
		List<PageData> sellers = userService.querySellers(cuPd);
		List<PageData> newVarList = new ArrayList<>();
		if(sellers.size() > 0 ) {
			Map<String,SysUserDTO> userMap = this.createUserMap(sellers);

			List<PageData> queryToalList = new ArrayList<>();
			sellers.stream().forEach(s->{
				PageData  totalPd = new PageData();
				String first_add_seller_id = s.getString("USER_ID");
				totalPd.put("user_id", first_add_seller_id);
				queryToalList.add(totalPd);
			});
			
			//销售人员月增加购彩量
			List<PageData> buyMonthList = usermanagercontrollerService.queryBuyByMonth(queryToalList);
			Map<String,String> monthMap = this.createUserMonthBuyMap(buyMonthList);
			
			//销售人员总购彩量
	        List<PageData> buyTotalList = usermanagercontrollerService.queryBuyTotal(queryToalList);
	        Map<String,String> totalMap = this.createUserTotalBuyMap(buyTotalList);
	        
			for(PageData pdata:varList) {
				if(userMap.get(pdata.getString("last_add_seller_id")) == null) {
					continue;
				}
				
				String firstSellerId = pdata.getString("last_add_seller_id");
				String totalBonus = bonusMap.get(firstSellerId);
				if(!StringUtils.isEmpty(totalBonus)) {
					pdata.put("totalBonus", totalBonus);
				}else {
					pdata.put("totalBonus", "0");
				}
				
				if(userMap.get(firstSellerId) != null) {
					SysUserDTO sysUser = userMap.get(firstSellerId);
					pdata.put("userId", String.valueOf(sysUser.getUserId()));
					pdata.put("mobile", sysUser.getMobile());
					pdata.put("username", sysUser.getUsername());				
				}else {
					pdata.put("userId", "");
					pdata.put("mobile", "");
					pdata.put("username", "");
				}
				
				if(totalMap.get(firstSellerId) != null) {
					String totalMoney = totalMap.get(firstSellerId);
					pdata.put("totalMoney", totalMoney);
				}else {
					pdata.put("totalMoney", "0");
				}
				
				if(monthMap.get(firstSellerId) != null) {
					String monthMoney = monthMap.get(firstSellerId);
					pdata.put("curMoney", monthMoney);
				}else {
					pdata.put("curMoney", "0");
				}
				newVarList.add(pdata);
			}
		}
		
		Comparator<PageData> comparator = (h1, h2) -> h1.getString("curMoney").compareTo(h2.getString("curMoney"));
		newVarList.sort(comparator.reversed());
		mv.setViewName("lottery/customer/sellerAchieve_list");
		mv.addObject("varList", newVarList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value = "/toDetail")
	public ModelAndView toDetail() throws Exception {
		ModelAndView mv = getDetailView(null);
		return mv;
	}
	
	@RequestMapping(value = "/toSellerDetail")
	@ResponseBody
	public ModelAndView toSellerDetail() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户销售业绩详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String pyear = StringUtils.isEmpty(pd.getString("pyear"))?"2019":pd.getString("pyear");
		
		pd.put("last_add_seller_id", pd.getString("user_id"));
		
		PageData queryPd = new PageData();
		queryPd.put("USER_ID", pd.getString("user_id"));
		PageData seller = userService.findById(queryPd);
		seller.put("pyear",pyear);
		seller.put("user_id",pd.getString("user_id"));
		
		//月购彩量
		List<PageData> varList = usermanagercontrollerService.sellerAchieveByMonthList(pd);
		Map<String,String> curBuyMap = this.createMonthAddBuyMap(varList);
		
		//月增加用户数
		List<PageData> personsList = usermanagercontrollerService.sellerWriteUserhList(pd);
		Map<String,CountPersonDTO> curPersonsMap = this.createMonthAddUserMap(personsList);
		
		//月增加红包数
		List<String> userIdList = usermanagercontrollerService.queryUserIdsBySellersId(pd.getString("user_id"));
		List<PageData> bonusMonthList = activityBonusService.queryTotalBonusByMonth(userIdList);
		Map<String,String> bonusMonthMap = this.createMonthAddBonusMap(bonusMonthList);
		
		//获取当前年份的12个月份的数组
		List<String> monthArr = new ArrayList<>();
			
		monthArr.add(pyear+"-01");
		monthArr.add(pyear+"-02");
		monthArr.add(pyear+"-03");
		monthArr.add(pyear+"-04");
		monthArr.add(pyear+"-05");
		monthArr.add(pyear+"-06");
		monthArr.add(pyear+"-07");
		monthArr.add(pyear+"-08");
		monthArr.add(pyear+"-09");
		monthArr.add(pyear+"-10");
		monthArr.add(pyear+"-11");
		monthArr.add(pyear+"-12");
		
		List<PageData> varNewList = new ArrayList<>();
		for(String str:monthArr) {
			PageData newpd = new PageData();
			newpd.put("eveMon", str);
			newpd.put("curPersons", "0");
			newpd.put("curBonus", "0");
			newpd.put("curMoneyPaid", "0");
			
			if(curPersonsMap.get(str) != null) {
				CountPersonDTO ctDto = curPersonsMap.get(str);
				newpd.put("curPersons",ctDto.getCurPersons());
			}
			
			if(bonusMonthMap.get(str) != null) {
				newpd.put("curBonus", bonusMonthMap.get(str));
			}
			
			if(curBuyMap.get(str) != null) {
				newpd.put("curMoneyPaid", curBuyMap.get(str));
			}
			
			varNewList.add(newpd);
		}
		
		mv.setViewName("lottery/customer/sellerAchieveByMonth_list");
		mv.addObject("varList", varNewList);
		mv.addObject("pd", seller);
		return mv;
	}

	
	/**
	 * 构造销售人员map
	 * @param sellers
	 * @return
	 */
	public Map<String,SysUserDTO> createUserMap(List<PageData> sellers){
		Map<String,SysUserDTO> map = new HashMap<String,SysUserDTO>();
		for(PageData pageData:sellers) {
			SysUserDTO sysUser = new SysUserDTO();
			sysUser.setUserId(pageData.getString("USER_ID"));
			sysUser.setMobile(pageData.getString("PHONE"));
			sysUser.setUsername(pageData.getString("USERNAME"));
			map.put(pageData.getString("USER_ID"), sysUser);
		}
		return map;
	}
	
	public Map<String,String> createUserTotalBuyMap(List<PageData> valist){
		Map<String,String> map = new HashMap<String,String>();
		for(PageData pageData:valist) {
			map.put(pageData.getString("last_add_seller_id"), pageData.getString("moneyPaid"));
		}
		return map;
	}
	
	public Map<String,String> createUserMonthBuyMap(List<PageData> valist){
		Map<String,String> map = new HashMap<String,String>();
		for(PageData pageData:valist) {
			map.put(pageData.getString("last_add_seller_id"), pageData.getString("moneyPaid"));
		}
		return map;
	}
	
	public Map<String,CountPersonDTO> createMonthAddUserMap(List<PageData> valist){
		Map<String,CountPersonDTO> map = new HashMap<String,CountPersonDTO>();
		for(PageData pageData:valist) {
			CountPersonDTO dto = new CountPersonDTO();
			dto.setFirstAddSellerId(pageData.getString("last_add_seller_id"));
			dto.setEveMon(pageData.getString("eveMon"));
			dto.setCurPersons(pageData.getString("curPersons"));
			map.put(pageData.getString("eveMon"), dto);
		}
		return map;
	}
	
	public Map<String,String> createMonthAddBonusMap(List<PageData> valist){
		Map<String,String> map = new HashMap<String,String>();
		for(PageData pageData:valist) {
			map.put(pageData.getString("eveMon"), pageData.getString("bonusTotalPrice"));
		}
		return map;
	}
	
	public Map<String,String> createMonthAddBuyMap(List<PageData> valist){
		Map<String,String> map = new HashMap<String,String>();
		for(PageData pageData:valist) {
			map.put(pageData.getString("eveMon"), pageData.getString("curMoneyPaid"));
		}
		return map;
	}
	
	/*
	 *  销售人员业绩按月列表
	 */
	@RequestMapping(value = "/sellerAchievementByMonthList")
	public ModelAndView sellerAchievementByMonthList() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = usermanagercontrollerService.sellerAchieveByMonthList(pd);
		mv.setViewName("lottery/usermanagercontroller/sellerAchieveByMonth_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}


	/**
	 * 强制
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/realMobileList")
	public ModelAndView realMobileList() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/phonevalid/realmobilevalid");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value = "/realMobileValid")
	@ResponseBody
	public Object realMobileValid() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String realName = pd.getString("real_name");
		String mobile = pd.getString("real_mobile");
		String idNo = pd.getString("id_no");
    	try {
			realName = URLDecoder.decode(realName, "UTF-8");
		} catch (Exception e) {
			logFac.error("姓名decode异常{}"+e);
		}
		String data = this.validPhone(realName, mobile, idNo);
		Map<String,String> map = new HashMap<>();
		map.put("data", data);
		return map;
	}
	
	
	/**
		key	是	string	在个人中心->我的数据,接口名称上方查看
	 	realname	是	string	姓名，需要utf8 Urlencode
	 	idcard	是	string	身份证号码
	 	mobile	是	string	手机号码
	 	type	否	int	1:返回手机运营商,不输入及其他值则不返回
	 	showid	否	int	1:返回聚合订单号,不输入及其他值则不返回
	 	province	否	int	1:返回手机号归属地，province,city,不输入不返回
	 	detail	否	string	是否显示匹配详情码,传1显示，默认不显示
	 */
	public String validPhone(String realName,String mobile,String idNo){
		String url = urlConfig.getJuheVerifyPhoneUrl();
		String juheVeryPhoneKey = urlConfig.getJuhePhoneVerifyKey();
		Map<String,String> params = new HashMap<String,String>();
		params.put("key", juheVeryPhoneKey);
		params.put("realname", realName);
		params.put("idcard", idNo);
		params.put("mobile", mobile);
		params.put("type", "1");
		params.put("showid", "1");
		params.put("province", "1");
		params.put("detail", "1");
		String data = NetWorkUtil.doGet(url, params, "UTF-8");
		JSONObject jo = JSONObject.fromObject(data);
		if(0 == Integer.valueOf(jo.getString("error_code"))) {
			logger.info(jo);
			JSONObject result = jo.getJSONObject("result");
			String rescode = result.getString("rescode");
			return ThirdApiEnum.getName(Integer.valueOf(rescode));
		}else {
			logger.error(data);
			return jo.getString("reason");
		}
	}
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表UserManagerController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String real_name = pd.getString("real_name"); // 关键词检索条件
		if (null != real_name && !"".equals(real_name)) {
			pd.put("real_name", real_name.trim());
		}
		String id_code = pd.getString("id_code");
		if (null != id_code && !"".equals(id_code)) {
			pd.put("id_code", id_code.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile);
		}
		String nickname = pd.getString("nickname");
		if (null != nickname && !"".equals(nickname)) {
			pd.put("nickname", nickname);
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			long start = DateUtilNew.getMilliSecondsByStr(lastStart);
			pd.put("lastStart1", start);
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
			pd.put("lastEnd1", end);
		}
		page.setPd(pd);
		List<PageData> varList = usermanagercontrollerService.list(page); // 列出UserManagerController列表
		if (varList != null) {
			for (int i = 0; i < varList.size(); i++) {
				PageData pData = varList.get(i);
				Integer userId = (int) pData.get("user_id");
				Double val = useraccountmanagerService.getTotalConsumByUserId(userId);
				if (val == null) {
					val = 0d;
				}
				pData.put("total", val);
				// 获取个人充值总金额
				Double valR = useraccountmanagerService.getTotalRechargeByUserId(userId);
				if (valR == null) {
					valR = 0d;
				}
				pData.put("rtotal", valR);
				// 获取个人获奖总金额
				Double valA = useraccountmanagerService.getTotalAwardByUserId(userId);
				if (valA == null) {
					valA = 0d;
				}
				pData.put("atotal", valA);
				// 提现总金额
				Double totalWithdraw = useraccountmanagerService.totalWithdraw(userId);
				if (totalWithdraw == null) {
					totalWithdraw = 0d;
				}
				pData.put("totalWithdraw", totalWithdraw);
			}
		}
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * detail详情页
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = usermanagercontrollerService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/godetail")
	public ModelAndView goDetail() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = usermanagercontrollerService.findById(pd);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_detail");
		mv.addObject("msg", "detail");
		mv.addObject("pd", pd);
		return mv;
	}


	/**
	 * 消费详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toConsumeDetail")
	public ModelAndView toConsumeDetail() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData rEntity = usermanagercontrollerService.findById(pd);
		BigDecimal allAmount = new BigDecimal(0);
		BigDecimal user_money = new BigDecimal(rEntity.getString("user_money"));

		BigDecimal frozen_money = new BigDecimal(rEntity.getString("frozen_money"));
		BigDecimal user_money_limit = new BigDecimal(rEntity.getString("user_money_limit"));
		allAmount = user_money.add(user_money_limit).subtract(frozen_money);
		// 总余额
		pd.put("allAmount", allAmount);
		// 用户可提现余额
		pd.put("user_money", user_money);
		// 红包
		Double unUseBonus = usermanagercontrollerService.findUserBonusByUserId(pd);
		int userId = Integer.parseInt(pd.get("user_id").toString());
		List<PageData> orderList = ordermanagerService.findByUserId(userId);
		Map<String, PageData> orderMap = new HashMap<String, PageData>();
		orderList.forEach(item -> orderMap.put(item.getString("order_sn"), item));
		List<PageData> userAccountList = useraccountmanagerService.findByUserId(userId);
		// 充值总金额
		double rechargeAllAmount = 0.0;
		// 获奖金额
		double rewardAllAmount = 0.0;
		// 购票金额
		double buyTicketAllAmount = 0.0;
		for (int i = 0; i < userAccountList.size(); i++) {
			PageData userAccountPd = new PageData();
			userAccountPd = userAccountList.get(i);
			int result = Integer.parseInt(userAccountPd.get("process_type").toString());
			if (result == 1) {
				rewardAllAmount += Double.parseDouble(userAccountPd.get("amount").toString());
			} else if (result == 2) {
				rechargeAllAmount += Double.parseDouble(userAccountPd.get("amount").toString());
			} else if (result == 3) {
				PageData orderPd = new PageData();
				String orderSn = userAccountPd.get("order_sn").toString();
				orderPd = orderMap.get(orderSn);
				buyTicketAllAmount += Double.parseDouble(userAccountPd.get("amount").toString());
				userAccountList.get(i).put("order", orderPd);
			}
		}
		pd.put("unUseBonus", unUseBonus == null ? 0 : unUseBonus);
		pd.put("rechargeAllAmount", rechargeAllAmount);
		pd.put("rewardAllAmount", rewardAllAmount);
		pd.put("buyTicketAllAmount", buyTicketAllAmount);
		pd.put("userAccountList", userAccountList);
		mv.addObject("pd", pd);
		mv.addObject("rentity", rEntity);
		mv.setViewName("lottery/usermanagercontroller/consume_detail_list");
		return mv;
	}

	private ModelAndView getDetailView(ModelAndView pMv) throws Exception {
		ModelAndView mv = new ModelAndView();
		if (pMv != null) {
			mv = pMv;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData rEntity = userrealmanagerService.findById(pd);
		PageData entity = usermanagercontrollerService.findById(pd);
		List<PageData> list = userbankmanagerService.listAllByUser(pd);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_detail");
		mv.addObject("entity", entity);
		mv.addObject("rentity", rEntity);
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping(value = "/freezAccount")
	public ModelAndView freezAccount() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String op = pd.getString("op");
		PageData userEntity = usermanagercontrollerService.findById(pd);
		String text = "";
		if (userEntity != null) {
			if (op != null) {
				if (op.equals("frozen")) {
					userEntity.put("user_status", UserManagerControllerManager.STATUS_FREEN);
					text = "冻结账号";
					redisDaoImpl.delRedisKey(USER_SESSION_PREFIX + Integer.valueOf(userEntity.getString("user_id")));
				} else {
					userEntity.put("user_status", UserManagerControllerManager.STATUS_NORMAL);
					text = "解冻账号";
				}
			}
		}
		usermanagercontrollerService.edit(userEntity);
		ACLOG.save("1", "0", "用户列表：" + userEntity.getString("user_id") + " " + userEntity.getString("nickname"), text);
		mv = getDetailView(mv);
		return mv;
	}

	@RequestMapping(value = "/clearRealInfo")
	public ModelAndView clearRealInfo() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData userEntity = usermanagercontrollerService.findById(pd);
		if (userEntity != null) {
			userEntity.put("is_real", 0);
		}
		usermanagercontrollerService.edit(userEntity);

		PageData updateRealPd = new PageData();
		updateRealPd.put("is_delete", 1);
		updateRealPd.put("user_id", Integer.valueOf(userEntity.getString("user_id")));
		userrealmanagerService.edit(updateRealPd);

		PageData queryBankPd = new PageData();
		queryBankPd.put("user_id", Integer.valueOf(userEntity.getString("user_id")));
		List<PageData> userBankpdList = userbankmanagerService.listAllByUser(queryBankPd);
		for (PageData updateBank : userBankpdList) {
			updateBank.put("user_id", Integer.valueOf(userEntity.getString("user_id")));
			userbankmanagerService.updateUserBankDelete(updateBank);
		}
		ACLOG.save("1", "0", "用户列表：" + userEntity.getString("user_id") + " " + userEntity.getString("nickname"), "清除实名认证");
		mv = getDetailView(mv);
		return mv;
	}

	@RequestMapping(value = "/phoneVerifySms")
	public ModelAndView phoneVerifySms() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData userEntity = usermanagercontrollerService.findById(pd);
		// 获取手机号
		String phone = userEntity.getString("mobile");
		String smsCode = RandomUtil.getRandNum(6);
		// test code
		userEntity.put("mobile", phone);
		userEntity.put("smscode", smsCode);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_smscode");
		mv.addObject("entity", userEntity);
		return mv;
	}

	@RequestMapping(value = "/postSmsCode")
	public ModelAndView postSmsCode() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String mobile = pd.getString("mobile");
		String smsCode = pd.getString("smsCode");
		RspSmsCodeEntity rspEntity = SmsUtil.sendMsgV3(mobile, smsCode, urlConfig.getServiceSmsUrl());
		pd.put("code", rspEntity.code);
		pd.put("data", rspEntity.data);
		pd.put("msg", rspEntity.msg);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_result");
		mv.addObject("rentity", pd);
		return mv;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelSellersTotal")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出销售人员销售概况到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		
		// 手机号	姓名	当月用户录入数	总录入用户量	当月购彩销售额	购彩销售总额	惠券使用总金额
		titles.add("手机号"); // 1
		titles.add("姓名"); // 2
		titles.add("当月用户录入数"); // 3
		titles.add("总录入用户量"); // 4
		titles.add("当月购彩销售额"); // 4
		titles.add("购彩销售总额"); // 5
		titles.add("优惠券使用总金额"); // 6
		dataMap.put("titles", titles);

		Page page  = new Page();
		ModelAndView modelView = this.sellerAchievementList(page);
		Map<String, Object> m = modelView.getModel();
		List<PageData> varOList = (List<PageData>) m.get("varList");
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("mobile")); // 1
			vpd.put("var2", varOList.get(i).getString("username")); // 2
			vpd.put("var3", varOList.get(i).getString("curPersons")); // 3
			vpd.put("var4", varOList.get(i).getString("totalPersons")); // 4
			vpd.put("var5", varOList.get(i).getString("curMoney")); // 5
			vpd.put("var6", varOList.get(i).getString("totalMoney")); // 6
			vpd.put("var7", varOList.get(i).getString("totalBonus")); // 6
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelSellersDetail")
	public ModelAndView excelSellersDetail() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出销售人员销售详情到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		
		// 月份	增加用户量	销售金额	红包使用量
		titles.add("月份"); // 1
		titles.add("增加用户量"); // 2
		titles.add("销售金额"); // 3
		titles.add("红包使用量"); // 4
		dataMap.put("titles", titles);

		Page page  = new Page();
		ModelAndView modelView = this.toSellerDetail();
		Map<String, Object> m = modelView.getModel();
		List<PageData> varOList = (List<PageData>) m.get("varList");
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("eveMon")); // 1
			vpd.put("var2", varOList.get(i).getString("curPersons")); // 2
			vpd.put("var3", varOList.get(i).getString("curMoneyPaid")); // 3
			vpd.put("var4", varOList.get(i).getString("curBonus")); // 4
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
