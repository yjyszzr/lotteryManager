package com.fh.controller.lottery.userrecharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userrecharge.UserRechargeManager;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：用充值现模块 创建人：FH Q313596790 创建时间：2018-05-02
 */
@Controller
@RequestMapping(value = "/userrecharge")
public class UserRechargeController extends BaseController {

	String menuUrl = "userrecharge/list.do"; // 菜单地址(权限用)
	@Resource(name = "userrechargeService")
	private UserRechargeManager userrechargeService;

	@Resource(name="useraccountmanagerService")
    private UserAccountManagerManager useraccountmanagerService;
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表UserRecharge");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String account_sn = pd.getString("account_sn");
		if (null != account_sn && !"".equals(account_sn)) {
			pd.put("account_sn", account_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		page.setPd(pd);
		BigDecimal failAmount = new BigDecimal(0);
		BigDecimal successAmount = new BigDecimal(0);
		BigDecimal unfinished = new BigDecimal(0);
		List<PageData> varList = userrechargeService.list(page); // 列出UserRecharge列表
		if (varList.size() > 0) {
		    List<PageData> useraccountList = useraccountmanagerService.findByOrderSns(varList);
		    Map<String, String> useraccountMap = new HashMap<String, String>(useraccountList.size());
		    useraccountList.forEach(item -> useraccountMap.put(item.getString("order_sn"), item.getString("donation_money")));
		    for (int i = 0; i < varList.size(); i++) {
		        PageData pageData = new PageData();
		        pageData = varList.get(i);
		        if (null != pageData.get("status") && !"".equals(pageData.get("status"))) {
		            if ("1".equals(pageData.get("status").toString())) {
		                if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
		                    BigDecimal Big1 = new BigDecimal(pageData.getString("amount"));
		                    successAmount = successAmount.add(Big1);
		                }
		            } else if ("2".equals(pageData.get("status").toString())) {
		                if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
		                    BigDecimal Big2 = new BigDecimal(pageData.getString("amount"));
		                    failAmount = failAmount.add(Big2);
		                }
		            } else if ("0".equals(pageData.get("status").toString())) {
		                if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
		                    BigDecimal Big0 = new BigDecimal(pageData.getString("amount"));
		                    unfinished = unfinished.add(Big0);
		                }
		            }
		        }
		        varList.get(i).put("donationMoney", useraccountMap.get(pageData.getString("recharge_sn")));
		    }
		}
		mv.setViewName("lottery/userrecharge/userrecharge_list");
		mv.addObject("varList", varList);
		mv.addObject("successAmount", successAmount);
		mv.addObject("failAmount", failAmount);
		mv.addObject("unfinished", unfinished);
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
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelConsumer到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("充值单编号"); // 1
		titles.add("交易流水号"); // 2
		titles.add("用户昵称"); // 3
		titles.add("电话"); // 4
		titles.add("充值金额"); // 5
		titles.add("赠送金额"); // 6
		titles.add("充值方式"); // 7
		titles.add("状态"); // 8
		titles.add("时间"); // 9
		dataMap.put("titles", titles);
		List<PageData> varOList = userrechargeService.listAll(pd);
		if (varOList.size() > 0) {
            List<PageData> useraccountList = useraccountmanagerService.findByOrderSns(varOList);
            Map<String, String> useraccountMap = new HashMap<String, String>(useraccountList.size());
            useraccountList.forEach(item -> useraccountMap.put(item.getString("order_sn"), item.getString("donation_money")));
    		List<PageData> varList = new ArrayList<PageData>();
    		for (int i = 0; i < varOList.size(); i++) {
    			PageData vpd = new PageData();
    			vpd.put("var1", varOList.get(i).getString("recharge_sn")); // 1
    			vpd.put("var2", varOList.get(i).getString("payment_id")); // 2
    			vpd.put("var3", varOList.get(i).getString("user_name")); // 3
    			vpd.put("var4", varOList.get(i).getString("mobile")); // 4
    			vpd.put("var5", varOList.get(i).getString("amount") + "元"); // 5
    			vpd.put("var6", StringUtil.isEmptyStr(useraccountMap.get(varOList.get(i).getString("recharge_sn"))) ? "0" : useraccountMap.get(varOList.get(i).getString("recharge_sn")) + "元"); // 6
    			vpd.put("var7", varOList.get(i).getString("payment_name")); // 7
    			BigDecimal big = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("add_time")) ? "0" : varOList.get(i).getString("add_time"));
    			BigDecimal big1000 = new BigDecimal(1000);
    			vpd.put("var8", DateUtil.toSDFTime(Long.parseLong(big.multiply(big1000).toString()))); // 8
    			String status = varOList.get(i).getString("status");
    			if (status.equals("1")) {
    				vpd.put("var9", "成功"); // 9
    			} else if (status.equals("2")) {
    				vpd.put("var9", "失败"); // 9
    			} else {
    				vpd.put("var9", "未完成"); // 9
    			}
    			varList.add(vpd);
    		}
		dataMap.put("varList", varList);
		}
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
