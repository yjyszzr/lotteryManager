package com.fh.controller.lottery.datastatistics;

import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.Page;
import com.fh.service.lottery.order.impl.OrderService;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.util.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Resource(name = "orderService")
	private OrderService orderService;


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

		if (null != lastStart && !"".equals(lastStart)) {
			lastStart = lastStart + " 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}else{
			lastEnd = LocalDate.now() + " 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}

		if (null != lastEnd && !"".equals(lastEnd)) {
			lastEnd = lastEnd + " 23:59:59";
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		} else {
			lastEnd = LocalDate.now() + " 23:59:59";
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		page.setPd(pd);
		List<PageData> varList = usermanagercontrollerService.listDetailTwo(page); // 列出UserManagerController列表

		List<String> appstoreList = new ArrayList<>();
		appstoreList.add("c26013");
		appstoreList.add("c16010");
		appstoreList.add("c26010");
		appstoreList.add("c26011");
		appstoreList.add("c26012");
		appstoreList.add("c26014");
		appstoreList.add("c36010");
		appstoreList.add("c36011");
		appstoreList.add("c36012");
		appstoreList.add("c36013");
		appstoreList.add("c36014");
		appstoreList.add("c36015");
		appstoreList.add("c36016");

		if (varList != null) {
			int size = varList.size();
			for (int i = 0; i < size; i++) {
				PageData pData = varList.get(i);
				String mobile = (String) pData.get("mobile");
				PageData pdMobile = new PageData();
				pdMobile.put("mobile",mobile);

				String deviceChannel = (String) pData.get("device_channel");
				if("h5".equals(deviceChannel)){
					pData.put("device_channel", "H5");
				}else if(appstoreList.contains(deviceChannel)){
					pData.put("device_channel", "IOS");
				}else{
					pData.put("device_channel", "Android");
				}

				// 获取个人消费总消费
				Double val = orderService.getTotalById(pdMobile);
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
				Double valR = useraccountmanagerService.getTotalConsumByUserId(pdMobile); //orderService.getTotalById(pdMobile);
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
				Double valA =  orderService.getTotalAward(pdMobile);
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
				Double valW = useraccountmanagerService.totalWithdraw(pdMobile);
				if (valW == null) {
					valW = 0d;
				}
				pData.put("wtotal", valW);

				if (!mobile.equals("") && mobile != null) {
					String area = MobileAddressUtils.getProvinceByIp(mobile);
					pData.put("area", area);
				}
				Double userBalance = useraccountmanagerService.getBalanceByMobile(pdMobile);
				if(null == userBalance){
					userBalance = 0d;
				}
				pData.put("balance", userBalance);

			}
			page.setTotalResult(size);
		}
		mv.setViewName("lottery/datastatistics/userdata_list");
		mv.addObject("varList", varList);
//		mv.addObject("pd", pd);
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
		titles.add("手机号");
		titles.add("地域");
		titles.add("渠道");
		titles.add("累计消费");
		titles.add("累计充值");
		titles.add("累计中奖");
		titles.add("账户余额");
		titles.add("注册时间");

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

		List<String> appstoreList = new ArrayList<>();
		appstoreList.add("c26013");
		appstoreList.add("c16010");
		appstoreList.add("c26010");
		appstoreList.add("c26011");
		appstoreList.add("c26012");
		appstoreList.add("c26014");
		appstoreList.add("c36010");
		appstoreList.add("c36011");
		appstoreList.add("c36012");
		appstoreList.add("c36013");
		appstoreList.add("c36014");
		appstoreList.add("c36015");
		appstoreList.add("c36016");

		List<PageData> list = usermanagercontrollerService.listDetailTwo(page);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("mobile"));
			String mobile = list.get(i).getString("mobile");
			PageData pdmobile =new PageData();
			pdmobile.put("mobile",mobile);
			String deviceChannel = (String) list.get(i).getString("device_channel");
			if("h5".equals(deviceChannel)){
				vpd.put("var2", "H5");
			}else if(appstoreList.contains(deviceChannel)){
				vpd.put("var2", "IOS");
			}else{
				vpd.put("var2", "Android");
			}

			if (!mobile.equals("") && mobile != null) {
				String area = MobileAddressUtils.getProvinceByIp(mobile);
				vpd.put("var3", area);// 10
			}
			vpd.put("var4", list.get(i).getString("phone_channel"));
			Integer userId = (int) list.get(i).get("user_id");
			Double val = orderService.getTotalById(pdmobile);
			if (val == null) {
				val = 0d;
			}

            if(checkSumStart(pd,Math.abs(val),"totalStart") || checkSumStart(pd,Math.abs(val),"totalEnd")) {
                continue;
            }
			// 获取个人充值总金额
			Double valR = useraccountmanagerService.getTotalConsumByUserId(pdmobile);
			if (valR == null) {
				valR = 0d;
			}
				if(checkSumStart(pd,valR,"rtotalStart") || checkSumStart(pd,valR,"rtotalEnd")) {
					continue;
				}
			// 获取个人获奖总金额
			Double valA = orderService.getTotalAward(pdmobile);
			if (valA == null) {
				valA = 0d;
			}
				if(checkSumStart(pd,valA,"atotalStart") || checkSumStart(pd,valA,"atotalEnd")) {
					continue;
				}
			// 获取个人累计提现
			Double valW = useraccountmanagerService.totalWithdraw(pdmobile);
			if (valW == null) {
				valW = 0d;
			}
			Double userBalance = useraccountmanagerService.getBalanceByMobile(pdmobile);
			if(null == userBalance){
				userBalance = 0d;
			}

			vpd.put("var5", Math.abs(val));
			vpd.put("var6", valR);
			vpd.put("var7", valA);
			vpd.put("var8", valW);
			vpd.put("var9", userBalance);
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
