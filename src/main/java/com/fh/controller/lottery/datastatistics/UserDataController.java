package com.fh.controller.lottery.datastatistics;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.util.TextUtils;
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
 * 说明：用户列表 创建人
 */
@Controller
@RequestMapping(value = "/userdata")
public class UserDataController extends BaseController {

	private final static String USER_SESSION_PREFIX = "US:";

	String menuUrl = "userdata/list.do"; // 菜单地址(权限用)
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
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表UserDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		if (pd.isEmpty()) {
			lastStart = LocalDate.now().toString();
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart+" 00:00:00"));
			pd.put("lastStart",lastStart);
		}
		if (null != lastStart && !"".equals(lastStart)) {
			lastStart = lastStart+" 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}  
		if (null != lastEnd && !"".equals(lastEnd)) {
			lastEnd = lastEnd+" 23:59:59";
		} else {
			lastEnd = LocalDate.now()+" 23:59:59";
		}
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		 
		page.setPd(pd);
		List<PageData> varList = usermanagercontrollerService.listDetailTwo(page); // 列出UserManagerController列表
		if (varList != null) {
			for (int i = 0; i < varList.size(); i++) {
				PageData pData = varList.get(i);
				Integer userId = (int) pData.get("user_id");
				Double val = useraccountmanagerService.getTotalConsumByUserId(userId);
				if (val == null) {
					val = 0d;
				}
				pData.put("total", Math.abs(val));
				// 获取个人充值总金额
				Double valR = useraccountmanagerService.getTotalRechargeByUserId(userId);
				if (valR == null) {
					valR = 0d;
				}
				pData.put("rtotal",valR);
				// 获取个人获奖总金额
				Double valA = useraccountmanagerService.getTotalAwardByUserId(userId);
				if (valA == null) {
					valA = 0d;
				}
				pData.put("atotal", valA);
				// 获取个人获奖总金额
				Double valW = useraccountmanagerService.getTotalWithDrawalByUserId(userId);
				if (valW == null) {
					valW = 0d;
				}
				pData.put("wtotal", valW);
			}
		}
		mv.setViewName("lottery/datastatistics/userdata_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	 
}
