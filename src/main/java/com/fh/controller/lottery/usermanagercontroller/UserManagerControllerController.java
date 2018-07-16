package com.fh.controller.lottery.usermanagercontroller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.Page;
import com.fh.entity.sms.RspSmsCodeEntity;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RandomUtil;
import com.fh.util.SmsUtil;

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
		usermanagercontrollerService.changeUserSwitch(pd);
		mv = getDetailView(mv);
		return mv;
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

	@RequestMapping(value = "/toDetail")
	public ModelAndView toDetail() throws Exception {
		ModelAndView mv = getDetailView(null);
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
				buyTicketAllAmount += Double.parseDouble(userAccountPd.get("amount").toString());
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
		if (userEntity != null) {
			if (op != null) {
				if (op.equals("frozen")) {
					userEntity.put("user_status", UserManagerControllerManager.STATUS_FREEN);
				} else {
					userEntity.put("user_status", UserManagerControllerManager.STATUS_NORMAL);
				}
			}
		}

		redisDaoImpl.delRedisKey(USER_SESSION_PREFIX + Integer.valueOf(userEntity.getString("user_id")));

		usermanagercontrollerService.edit(userEntity);

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
		for(PageData updateBank:userBankpdList) {
			updateBank.put("user_id", Integer.valueOf(userEntity.getString("user_id")));
			userbankmanagerService.updateUserBankDelete(updateBank);
		}
		
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
}
