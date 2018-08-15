package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.Page;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.IPAddressUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.MobileAddressUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：用户列表 创建人
 */
@Controller
@RequestMapping(value = "/userdata")
public class UserDataController extends BaseController {

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
		String mobileC = pd.getString("mobile");
		if (null != mobileC && !"".equals(mobileC)) {
			pd.put("mobile1", mobileC);
		}
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		if (pd.isEmpty()) {
			lastStart = LocalDate.now().toString();
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart + " 00:00:00"));
			pd.put("lastStart", lastStart);
		}
		if (null != lastStart && !"".equals(lastStart)) {
			lastStart = lastStart + " 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		if (null != lastEnd && !"".equals(lastEnd)) {
			lastEnd = lastEnd + " 23:59:59";
		} else {
			lastEnd = LocalDate.now() + " 23:59:59";
		}
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));

		page.setPd(pd);
		List<PageData> varList = usermanagercontrollerService.listDetailTwo(page); // 列出UserManagerController列表
		if (varList != null) {
			int size = varList.size();
			for (int i = 0; i < size; i++) {
				PageData pData = varList.get(i);
				Integer userId = (int) pData.get("user_id");
				// 获取个人充值总消费
				Double val = useraccountmanagerService.getTotalConsumByUserId(userId);
				if (val == null) {
					val = 0d;
				}
					if(checkSumStart(pd,Math.abs(val),"totalStart") || checkSumEnd(pd,Math.abs(val),"totalEnd")) {
						varList.remove(i);
						i--;
						size--;
						continue;
					}
				pData.put("total", Math.abs(val));
				// 获取个人充值总金额
				Double valR = useraccountmanagerService.getTotalRechargeByUserId(userId);
				if (valR == null) {
					valR = 0d;
				}
					if(checkSumStart(pd,valR,"rtotalStart") || checkSumEnd(pd,valR,"rtotalEnd")) {
						varList.remove(i);
						i--;
						size--;
						continue;
					}
				pData.put("rtotal", valR);
				// 获取个人获奖总金额
				Double valA = useraccountmanagerService.getTotalAwardByUserId(userId);
				if (valA == null) {
					valA = 0d;
				}
					if(checkSumStart(pd,valA,"atotalStart") || checkSumEnd(pd,valA,"atotalEnd")) {
						varList.remove(i);
						i--;
						size--;
						continue;
					}
				pData.put("atotal", valA);
				// 获取个人累计提现
				Double valW = useraccountmanagerService.totalWithdraw(userId);
				if (valW == null) {
					valW = 0d;
				}
				pData.put("wtotal", valW);
				// 性别判断
				String idCode = pData.getString("id_code");
				int sex = 0;
				if (!idCode.equals("") && idCode != null) {
					if (idCode.length() == 18) {
						sex = Integer.parseInt(idCode.substring(16, 17));
					} else {
						sex = Integer.parseInt(idCode.substring(14, 15));
					}
					if (sex % 2 == 0) {
						pData.put("sex", "2");
					} else {
						pData.put("sex", "1");
					}
					pData.put("age", IdNOToAge(idCode));
				}
				String mobile = pData.getString("mobile");
				if (!mobile.equals("") && mobile != null) {
					String area = MobileAddressUtils.getProvinceByIp(mobile);
					pData.put("area", area);
				}
				// String regIP = pData.getString("reg_ip");
				// if (!regIP.equals("") && regIP != null) {
				// String area = IPAddressUtils.getProvinceByIp(regIP);
				// pData.put("area", area);
				// }
			}
			page.setTotalResult(size);
		}
		mv.setViewName("lottery/datastatistics/userdata_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出OrderPlayData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID"); // 1
		titles.add("用户昵称"); // 2
		titles.add("级别"); // 3
		titles.add("手机号"); // 4
		titles.add("真实姓名"); // 5
		titles.add("身份证号"); // 6
		titles.add("性别"); // 7
		titles.add("年龄"); // 8
		titles.add("终端"); // 9
		titles.add("地域"); // 10
		titles.add("渠道"); // 11
		titles.add("累计消费"); // 12
		titles.add("累计充值"); // 13
		titles.add("累计中奖"); // 14
		titles.add("累计提现"); // 15
		titles.add("账户余额"); // 16
		titles.add("注册时间"); // 17
		titles.add("最后登录时间"); // 18
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("titles", titles);
		String mobileC = pd.getString("mobile");
		if (null != mobileC && !"".equals(mobileC)) {
			pd.put("mobile1", mobileC);
		}
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart+" 00:00:00"));
			page.setShowCount(65000);// 单页显示条数，为了全部导出应用
		}
		String lastEnd = pd.getString("lastEnd"); // 结束检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd+" 23:59:59"));
			page.setShowCount(65000);// 单页显示条数，为了全部导出应用
		}
		page.setPd(pd);
		List<PageData> list = usermanagercontrollerService.listDetailTwo(page);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("user_id")); // 1
			vpd.put("var2", list.get(i).getString("nickname")); // 2
			vpd.put("var3", "*"); // 3
			vpd.put("var4", list.get(i).getString("mobile")); // 4
			vpd.put("var5", list.get(i).getString("real_name")); // 5
			vpd.put("var6", list.get(i).getString("id_code")); // 6
			// 性别判断
			String idCode = list.get(i).getString("id_code");
			int sex = 0;
			if (!idCode.equals("") && idCode != null) {
				if (idCode.length() == 18) {
					sex = Integer.parseInt(idCode.substring(16, 17));
				} else {
					sex = Integer.parseInt(idCode.substring(14, 15));
				}
				if (sex % 2 == 0) {
					vpd.put("var7", "女"); // 7
				} else {
					vpd.put("var7", "男");
				}
				vpd.put("var8", IdNOToAge(idCode)); // 8
			}
			vpd.put("var9", list.get(i).getString("brand")); // 9
			String mobile = list.get(i).getString("mobile");
			if (!mobile.equals("") && mobile != null) {
				String area = MobileAddressUtils.getProvinceByIp(mobile);
				vpd.put("var10", area);// 10
			}
			// String regIP = list.get(i).getString("reg_ip");
			// if (!regIP.equals("") && regIP != null) {
			// String area = IPAddressUtils.getProvinceByIp(regIP);
			// vpd.put("var10", area); // 10
			// }
			vpd.put("var11", list.get(i).getString("phone_channel")); // 11
			if(list.get(i).get("phone_channel") == "" || list.get(i).get("phone_channel") == null) {
				vpd.put("var11", list.get(i).getString("device_channel")); // 11
			}

			Integer userId = (int) list.get(i).get("user_id");
			Double val = useraccountmanagerService.getTotalConsumByUserId(userId);
			if (val == null) {
				val = 0d;
			}
				if(checkSumStart(pd,Math.abs(val),"totalStart") || checkSumStart(pd,Math.abs(val),"totalEnd")) {
					continue;
				}
			// 获取个人充值总金额
			Double valR = useraccountmanagerService.getTotalRechargeByUserId(userId);
			if (valR == null) {
				valR = 0d;
			}
				if(checkSumStart(pd,valR,"rtotalStart") || checkSumStart(pd,valR,"rtotalEnd")) {
					continue;
				}
			// 获取个人获奖总金额
			Double valA = useraccountmanagerService.getTotalAwardByUserId(userId);
			if (valA == null) {
				valA = 0d;
			}
				if(checkSumStart(pd,valA,"atotalStart") || checkSumStart(pd,valA,"atotalEnd")) {
					continue;
				}
			// 获取个人累计提现
			Double valW = useraccountmanagerService.totalWithdraw(userId);
			if (valW == null) {
				valW = 0d;
			}
			vpd.put("var12", Math.abs(val)); // 12
			vpd.put("var13", valR); // 13
			vpd.put("var14", valA); // 14
			vpd.put("var15", valW); // 15

			vpd.put("var16", new BigDecimal(list.get(i).getString("user_money_limit"))
					.add(new BigDecimal(list.get(i).getString("user_money")))); // 16
			vpd.put("var17", DateUtil.toSDFTime(Long.parseLong(list.get(i).getString("reg_time")) * 1000)); // 17
			vpd.put("var18", DateUtil.toSDFTime(Long.parseLong(list.get(i).getString("last_time")) * 1000)); // 18

			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	public int IdNOToAge(String IdNO) {
		int leh = IdNO.length();
		String dates = "";
		if (leh == 18) {
			dates = IdNO.substring(6, 10);
			int year = LocalDate.now().getYear();
			int u = year - Integer.parseInt(dates);
			return u;
		} else {
			dates = IdNO.substring(6, 8);
			int year = LocalDate.now().getYear();
			int u = year - Integer.parseInt("19" + dates);
			return u;
		}

	}
	
	public boolean checkSumStart(PageData pd,Double val,String obj) {
		if(null != pd.get(obj) && !"".equals(pd.get(obj))) {
			Double total = Double.parseDouble(pd.getString(obj));
			if(total > val ) {
				return true;
			}
		}
		return false;
	}
	public boolean checkSumEnd(PageData pd,Double val,String obj) {
		if(null != pd.get(obj) && !"".equals(pd.get(obj))) {
			Double total = Double.parseDouble(pd.getString(obj));
			if(total < val ) {
				return true;
			}
		}
		return false;
	}

}
