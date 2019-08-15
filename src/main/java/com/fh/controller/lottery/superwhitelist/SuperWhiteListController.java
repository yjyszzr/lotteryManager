package com.fh.controller.lottery.superwhitelist;

import com.alibaba.fastjson.JSON;
import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.controller.lottery.userwithdraw.ReqCashEntity;
import com.fh.entity.Page;
import com.fh.entity.param.DonationBonusParam;
import com.fh.entity.param.RechargePraiseParam;
import com.fh.entity.system.User;
import com.fh.enums.SNBusinessCodeEnum;
import com.fh.service.lottery.customer.CustomerManager;
import com.fh.service.lottery.rechargecard.impl.RechargeCardService;
import com.fh.service.lottery.superwhitelist.SuperWhiteListManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountService;
import com.fh.service.lottery.usermanagercontroller.impl.UserManagerControllerService;
import com.fh.service.lottery.userrecharge.impl.UserRechargeService;
import com.fh.util.*;
import com.google.gson.JsonObject;
import org.apache.axis.utils.SessionUtils;
import org.apache.axis.utils.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 说明：超级白名单
 * 创建人：FH Q313596790
 * 创建时间：2018-12-26
 */
@Controller
@RequestMapping(value="/superwhitelist")
public class SuperWhiteListController extends BaseController {

	String menuUrl = "superwhitelist/list.do"; //菜单地址(权限用)
	@Resource(name="superwhitelistService")
	private SuperWhiteListManager superwhitelistService;

	@Resource(name="useraccountService")
	private UserAccountService userAccountManagerService;


	@Resource(name="useraccountmanagerService")
	private UserAccountManagerService userAccountManagerService3;

	@Resource(name="rechargecardService")
	private RechargeCardService rechargecardService;

	@Resource(name="userrechargeService")
    private UserRechargeService userRechargeService;

	@Resource(name="customerService")
	private CustomerManager customerService;

	@Resource(name="usermanagercontrollerService")
	private UserManagerControllerService userManagerControllerService;

    @Resource(name = "urlConfig")
    private URLConfig urlConfig;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("user_id", "0");	//用户id
		pd.put("mobile", "");	//手机号
		pd.put("user_money", "");	//账户余额
		superwhitelistService.save(pd);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value="/setIsSupperWhite")
	public void setIsSupperWhite(PrintWriter out) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		superwhitelistService.setIsSupperWhite(pd);

		//如果没有钱包，则产生对应的钱包

		out.write("success");
		out.close();
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		superwhitelistService.delete(pd);
		out.write("success");
		out.close();
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		superwhitelistService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SuperWhiteList");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		page.setPd(pd);
		List<PageData>	varList = new ArrayList<>();
		String appCodeName = pd.getString("app_code_name");
		if("11".equals(appCodeName)){
			varList = userManagerControllerService.getShenHeUserList(page);
		}else {
			varList = superwhitelistService.list(page);	//列出SuperWhiteList列表
		}

		//填入该用户最近充值时间
		List<PageData> varNewList = new ArrayList<>();
		List<Integer> userIdList = varList.stream().map(s->Integer.valueOf(s.getString("user_id"))).collect(Collectors.toList());
		if(userIdList.size() > 0){
			PageData rPageData = new PageData();
			rPageData.put("userIdList",userIdList);
			Long lastStart = StringUtils.isEmpty(pd.getString("lastStart"))?0l:DateUtilNew.getMilliSecondsByStr(pd.getString("lastStart"));
			Long lastEnd   = StringUtils.isEmpty(pd.getString("lastEnd"))?0l:DateUtilNew.getMilliSecondsByStr(pd.getString("lastEnd"));
			rPageData.put("lastStart",lastStart == 0l?"":lastStart);
			rPageData.put("lastEnd", lastEnd == 0l?"":lastEnd);
            List<PageData> latestRechargeDataList = new ArrayList<>();
			if("11".equals(appCodeName)){
                latestRechargeDataList = userManagerControllerService.queryUserAccountRechargeLatest(rPageData);
            }else if("10".equals(appCodeName)){
                latestRechargeDataList = userAccountManagerService.queryUserAccountRechargeLatest(rPageData);
            }

			Map<String,Long> rMap = latestRechargeDataList.stream().collect(Collectors.toMap(s->s.getString("user_id"),s-> Long.valueOf(s.getString("add_time"))));
			for(PageData pdd:varList){
				String puserId = pdd.getString("user_id");
				Long latestR = rMap.get(puserId);
				if(lastStart > 0l && lastEnd >0l){
					if(null != latestR){
						pdd.put("recharge_time_latest",DateUtilNew.getCurrentTimeString(latestR,DateUtilNew.datetimeFormat));
						varNewList.add(pdd);
					}
				}else{
					if(null != latestR){
						pdd.put("recharge_time_latest",DateUtilNew.getCurrentTimeString(latestR,DateUtilNew.datetimeFormat));
					}else {
						pdd.put("recharge_time_latest","");
					}
					varNewList.add(pdd);
				}
			}
		}

		mv.setViewName("lottery/superwhitelist/superwhitelist_list");

		if(appCodeName.equals("10")){
            if (null != varNewList && varNewList.size() > 0) {
                for (PageData pageData : varNewList) {
                    try {
                        String recharge_card_real_value = "";
                        PageData _pageData = this.superwhitelistService.getSumRechargeCardRealValue(pageData);
                        if (null != _pageData) {
                            recharge_card_real_value = _pageData.getString("recharge_card_real_value");
                            pageData.put("recharge_card_real_value", recharge_card_real_value);
                        }else{
                            pageData.put("recharge_card_real_value", "0.00");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            List<String> mobileList = varNewList.stream().map(s->s.getString("mobile")).collect(Collectors.toList());
            if (mobileList.size()>0) {
            	PageData mobPd = new PageData();
            	mobPd.put("mobileList",mobileList);
            	List<PageData> mobilePdList = this.userRechargeService.queryTotalRechareCardByMobiles(mobPd);
            	Map<String, String> mobileReMap = mobilePdList.stream().collect(Collectors.toMap(s->s.getString("mobile"), s->s.getString("rechareTotal")));
            	for(PageData newPd:varNewList){
            		String mobile = newPd.getString("mobile");
            		String reValue = mobileReMap.get(mobile);
            		if(!StringUtils.isEmpty(reValue)){
            			newPd.put("recharge_card_real_value", reValue);
            		}else{
            			newPd.put("recharge_card_real_value", "0.00");
            		}
            	}
			}
        }

		mv.addObject("varList", varNewList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());//按钮权限
		return mv;
	}


	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SuperWhiteList到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID");	//1
		titles.add("用户名");	//2
		titles.add("昵称");	//3
		titles.add("手机号");	//4
		titles.add("最近充值时间");
		titles.add("充值金额");	//5
		titles.add("可提现余额");	//6
		titles.add("大礼包总金额");	//7
		titles.add("平台来源");	//8
		dataMap.put("titles", titles);

		String appCodeName = pd.getString("app_code_name");
		List<PageData> varOList = new ArrayList<>();
		if(appCodeName.equals("10")){
			varOList = superwhitelistService.listAll(pd);
		}else if(appCodeName.equals("11")){
			page.setPd(pd);
			varOList = userManagerControllerService.listAllNew(pd);
		}

		//填入该用户最近充值时间
		List<PageData> varNewList = new ArrayList<>();
		List<Integer> userIdList = varOList.stream().map(s->Integer.valueOf(s.getString("user_id"))).collect(Collectors.toList());
		if(userIdList.size() > 0){
			PageData rPageData = new PageData();
			rPageData.put("userIdList",userIdList);
			Long lastStart = StringUtils.isEmpty(pd.getString("lastStart"))?0l:DateUtilNew.getMilliSecondsByStr(pd.getString("lastStart"));
			Long lastEnd   = StringUtils.isEmpty(pd.getString("lastEnd"))?0l:DateUtilNew.getMilliSecondsByStr(pd.getString("lastEnd"));
			rPageData.put("lastStart",lastStart == 0l?"":lastStart);
			rPageData.put("lastEnd", lastEnd == 0l?"":lastEnd);
            List<PageData> latestRechargeDataList = new ArrayList<>();
            if("11".equals(appCodeName)){
                latestRechargeDataList = userManagerControllerService.queryUserAccountRechargeLatest(rPageData);
            }else if("10".equals(appCodeName)){
                latestRechargeDataList = userAccountManagerService.queryUserAccountRechargeLatest(rPageData);
            }
			Map<String,Long> rMap = latestRechargeDataList.stream().collect(Collectors.toMap(s->s.getString("user_id"),s-> Long.valueOf(s.getString("add_time"))));
			for(PageData pdd:varOList){
				String puserId = pdd.getString("user_id");
				Long latestR = rMap.get(puserId);
				if(lastStart > 0l && lastEnd >0l){
					if(null != latestR){
						pdd.put("recharge_time_latest",DateUtilNew.getCurrentTimeString(latestR,DateUtilNew.datetimeFormat));
						varNewList.add(pdd);
					}
				}else{
					if(null != latestR){
						pdd.put("recharge_time_latest",DateUtilNew.getCurrentTimeString(latestR,DateUtilNew.datetimeFormat));
					}else {
						pdd.put("recharge_time_latest","");
					}
					varNewList.add(pdd);
				}
			}
		}

		List<PageData> varList = new ArrayList<PageData>();
		String name = appCodeName.equals("11")?"圣和彩店":"球多多";
		for(int i=0;i<varNewList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varNewList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varNewList.get(i).getString("user_name"));	    //2
			vpd.put("var3", varNewList.get(i).getString("nickname"));	    //3
			vpd.put("var4", varNewList.get(i).getString("mobile"));	    //4
			vpd.put("var5", varNewList.get(i).getString("recharge_time_latest"));
			vpd.put("var6", varNewList.get(i).getString("money_limit"));
			vpd.put("var7", varNewList.get(i).getString("money"));

			if(appCodeName.equals("10")){
				try {
					String recharge_card_real_value = "";
					PageData _pageData = this.superwhitelistService.getSumRechargeCardRealValue(varNewList.get(i));
					if (null != _pageData) {
						recharge_card_real_value = _pageData.getString("recharge_card_real_value");
						vpd.put("var8", recharge_card_real_value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(appCodeName.equals("11")){
				vpd.put("var8", 0);
			}
			vpd.put("var9", name);
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = this.superwhitelistService.listStoreAll(pd);
		mv.setViewName("lottery/superwhitelist/superwhitelist_edit");
		mv.addObject("msg", "add");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value="/add")
	public ModelAndView add()throws Exception{
		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		List<PageData>	varList = this.superwhitelistService.listStoreAll(pd);
//		mv.setViewName("lottery/superwhitelist/superwhitelist_edit");
//		mv.addObject("msg", "add");
//		mv.addObject("varList", varList);
//		mv.addObject("pd", pd);
		return mv;
	}


	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = superwhitelistService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/superwhitelist/superwhitelist_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value="/goRecharge")
	public ModelAndView goRecharge() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String viewName= "";
		String appCodeName = pd.getString("app_code_name");
		String msg = "";
		if("11".equals(appCodeName)){
			pd.put("store_id","1");
			viewName = "lottery/superwhitelist/superwhitelist_recharge";
			msg = "recharge";
			pd = userManagerControllerService.queryUserByMobileNew(pd);
		}else{
			viewName = "lottery/superwhitelist/superwhitelist_recharge";
			msg = "recharge";
			pd = superwhitelistService.findById(pd);	//根据ID读取
		}

		List<PageData> rechargeCardList = rechargecardService.listAll(pd);
		pd.put("rechargeCardList", rechargeCardList);
		mv.setViewName(viewName);
		mv.addObject("msg", msg);
		pd.put("app_code_name",appCodeName);
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value="/listAccount")
	public ModelAndView listAccount(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表ListAccount");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String _start_add_time = pd.getString("start_add_time");
		if (null != _start_add_time && !"".equals(_start_add_time)) {
			pd.put("start_add_time", DateUtilNew.getMilliSecondsByStr(_start_add_time.trim()));
		}
		String _end_add_time = pd.getString("end_add_time");
		if (null != _end_add_time && !"".equals(_end_add_time)) {
			pd.put("end_add_time", DateUtilNew.getMilliSecondsByStr(_end_add_time.trim()));
		}
		page.setPd(pd);
		String appCodeNameStr = pd.getString("app_code_name");
		String appCodeName = StringUtils.isEmpty(appCodeNameStr)?"10":appCodeNameStr;
		List<PageData> varList = new ArrayList<>();
		if(appCodeName.equals("11")){
			varList =  userAccountManagerService3.datalistQddAccountPage(page);
		}else{
			varList = this.superwhitelistService.listAccount(page);
		}

		pd.put("start_add_time", _start_add_time.trim());
		pd.put("end_add_time", _end_add_time.trim());

		mv.setViewName("lottery/superwhitelist/superwhitelist_listAccount");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	/**充值记录导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excelAccount")
	public ModelAndView exportExcelAccount() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SuperWhiteList到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_add_time = pd.getString("start_add_time");
		if (null != start_add_time && !"".equals(start_add_time)) {
			pd.put("start_add_time", DateUtilNew.getMilliSecondsByStr(start_add_time.trim()));
		}
		String end_add_time = pd.getString("end_add_time");
		if (null != end_add_time && !"".equals(end_add_time)) {
			pd.put("end_add_time", DateUtilNew.getMilliSecondsByStr(end_add_time.trim()));
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID");	//1
		titles.add("手机号");	//2
		titles.add("帐户余额");	//3
		titles.add("金额记录");	//4
		titles.add("大礼包余额");
		titles.add("状态");	//5
		titles.add("扣款原因");	//6
		titles.add("时间");	//7
		titles.add("平台来源");	//8
		titles.add("状态");	//9
		titles.add("操作人");	//9
		dataMap.put("titles", titles);

		List<PageData> varOList = new ArrayList<>();
		String appCodeName = pd.getString("app_code_name");
		String name = appCodeName.equals("11")?"圣和彩店":"球多多";
		if(appCodeName.equals("11")){
			Page page = new Page();
			page.setShowCount(65000);
			page.setPd(pd);
			varOList = userAccountManagerService3.datalistQddAccountPage(page);
		}else{
			varOList = superwhitelistService.listAccountAll(pd);
		}

		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("mobile"));	    //2
			vpd.put("var3", varOList.get(i).getString("cur_balance"));	    //3
			vpd.put("var4", varOList.get(i).getString("amount"));	    //4
			vpd.put("var5", varOList.get(i).getString("recharge_card_real_value"));
			String processTypeStr = varOList.get(i).getString("process_type");
			if (null != processTypeStr && processTypeStr.equals("1")) {
				processTypeStr = "中奖";
			} else if (null != processTypeStr && processTypeStr.equals("2")) {
				processTypeStr = "充值到充值金额";
			}else if (null != processTypeStr && processTypeStr.equals("10")) {
				processTypeStr = "充值到可提现金额";
			} else if (null != processTypeStr && processTypeStr.equals("3")) {
				processTypeStr = "购彩";
			} else if (null != processTypeStr && processTypeStr.equals("5")) {
				processTypeStr = "红包";
			} else if (null != processTypeStr && processTypeStr.equals("11")) {
				processTypeStr = "扣款充值金额";
			} else if (null != processTypeStr && processTypeStr.equals("12")) {
				processTypeStr = "扣款可提现金额";
			} else if (null != processTypeStr && processTypeStr.equals("6")) {
				processTypeStr = "账户回滚";
			} else if (null != processTypeStr && processTypeStr.equals("7")) {
				processTypeStr = "购券";
			} else if (null != processTypeStr && processTypeStr.equals("8")) {
				processTypeStr = "退款";
			} else {
				processTypeStr = "";
			}

			vpd.put("var6", processTypeStr);	    //5

			String processTypeStr2 = varOList.get(i).getString("process_type");
			if (null != processTypeStr2 && processTypeStr2.equals("4")) {
				processTypeStr2 = "提现";
			} else if (null != processTypeStr2 && processTypeStr2.equals("9")) {
				processTypeStr2 = "输入错误";
			} else {
				processTypeStr2 = "";
			}
			vpd.put("var7", processTypeStr2);	    //6

			vpd.put("var8", DateUtil.toSDFTime(new Long(varOList.get(i).getString("add_time"))*1000));	    //7
			vpd.put("var9", name);	//8

			String statusStr = varOList.get(i).get("status").toString();
			if (null != statusStr && statusStr.equals("0")) {
				statusStr = "未完成";
			} else if (null != statusStr && statusStr.equals("1")) {
				statusStr = "成功";
			} else if (null != statusStr && statusStr.equals("2")) {
				statusStr = "失败";
			} else {
				statusStr = "";
			}
			vpd.put("var10", statusStr);	//9

			try {
				vpd.put("var11", varOList.get(i).get("admin_user").toString());	//9
			} catch (Exception e) {
			}

			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	@RequestMapping(value="/goDeduction")
	public ModelAndView goDeduction() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String appCodeName = pd.getString("app_code_name");
		if(appCodeName.equals("10")){
            pd = superwhitelistService.findById(pd);//根据ID读取
        }else if(appCodeName.equals("11")){
            pd = userManagerControllerService.findById(pd);
            pd.put("money_limit",pd.getString("user_money_limit"));
            pd.put("money",pd.getString("user_money"));
        }

        pd.put("app_code_name",appCodeName);
		mv.setViewName("lottery/superwhitelist/superwhitelist_deduction");
		mv.addObject("msg", "deduction");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value="/deduction")
	public ModelAndView deduction() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"扣款SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "deduction")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer time = DateUtilNew.getCurrentTimeLong();

		pd.put("last_time", time);
		boolean flag = true;

        pd.put("money","");
        pd.put("money_limit","");
		String refoundLoc = pd.getString("refound_loc");
		if("0".equals(refoundLoc)){// 可提现金额
			pd.put("money",pd.getString("number"));
			pd.put("process_type",12);
		}else if("1".equals(refoundLoc)){//充值金额
			pd.put("money_limit",pd.getString("number"));
			pd.put("process_type",11);
		}

        String appCodeName = pd.getString("app_code_name");
        if(appCodeName.equals("10")){
            superwhitelistService.deduction(pd);

            pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
            User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
            pd.put("admin_user", user.getNAME());
            pd.put("amount", "-" + pd.get("number"));
            PageData _pd = new PageData();
            _pd = superwhitelistService.findById(pd);
            BigDecimal bigMoney = new BigDecimal( null != _pd.getString("money") ? _pd.getString("money") : "0");
            BigDecimal bigMoneyLimit = new BigDecimal(null != _pd.getString("money_limit") ? _pd.getString("money_limit") : "0");
            pd.put("cur_balance", bigMoney.add(bigMoneyLimit).toString());
            pd.put("user_id", _pd.getString("user_id"));
            pd.put("store_id", _pd.getString("store_id"));
            pd.put("add_time",time);
            pd.put("status", "1");
            pd.put("id", "");
            userAccountManagerService.save(pd);

        }else if(appCodeName.equals("11")){
            userManagerControllerService.deduction(pd);

            pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
            User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
            pd.put("admin_user", user.getNAME());
            pd.put("amount", pd.get("number"));
            PageData pdMobile = userManagerControllerService.findById(pd);
            BigDecimal bigMoney = new BigDecimal( null != pdMobile.getString("user_money") ? pdMobile.getString("user_money") : "0");
            BigDecimal bigMoneyLimit = new BigDecimal(null != pdMobile.getString("user_money_limit") ? pdMobile.getString("user_money_limit") : "0");
            BigDecimal curBalance = bigMoney.add(bigMoneyLimit);
            pd.put("cur_balance", curBalance.toString());
            pd.put("user_id", pdMobile.getString("user_id"));
            pd.put("store_id", "1");
            pd.put("add_time",time);
            pd.put("status", "1");

            userAccountManagerService3.save(pd);
        }

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value="/refound")
	public ModelAndView refound() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"退款SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "refound")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer time = DateUtilNew.getCurrentTimeLong();
		pd.put("last_time", time);
		boolean flag = true;
		String appCodeName = pd.getString("app_code_name");
		pd.put("user_money_limit",pd.getString("number"));
		pd.put("store_id","1");

		userManagerControllerService.refoundToUserMoneyLimit(pd);

		pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("admin_user", user.getNAME());
		pd.put("amount", pd.get("number"));
		PageData pdMobile = userManagerControllerService.queryUserByMobile(pd);
		BigDecimal bigMoney = new BigDecimal( null != pdMobile.getString("user_money") ? pdMobile.getString("user_money") : "0");
		BigDecimal bigMoneyLimit = new BigDecimal(null != pdMobile.getString("user_money_limit") ? pdMobile.getString("user_money_limit") : "0");
		BigDecimal curBalance = bigMoney.add(bigMoneyLimit);
		pd.put("cur_balance", curBalance.toString());
		pd.put("user_id", pdMobile.getString("user_id"));
		pd.put("store_id", "1");
		pd.put("add_time",time);
		pd.put("process_type", "8");//退款
		pd.put("status", "1");

		userAccountManagerService3.save(pd);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


	@RequestMapping(value="/recharge")
	public ModelAndView recharge() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"充值SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "recharge")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer time = DateUtilNew.getCurrentTimeLong();
		pd.put("last_time", time);
		boolean flag = true;
		String appCodeName = pd.getString("app_code_name");

        pd.put("money","");
        pd.put("money_limit","");
		String rechargeLoc = pd.getString("recharge_loc");
		if("0".equals(rechargeLoc)){//可提现金额
			pd.put("money",pd.getString("number"));
			pd.put("process_type", "10");
		}else if("1".equals(rechargeLoc)){//充值金额
			pd.put("money_limit",pd.getString("number"));
			pd.put("process_type", "2");
		}

		if(appCodeName.equals("11")){
		    String recahrgeSn = SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode());
            pd.put("account_sn", recahrgeSn);
            User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
            pd.put("admin_user", user.getNAME());
            pd.put("amount", pd.get("number"));
            PageData pdMobile = userManagerControllerService.findById(pd);
            BigDecimal bigMoney = new BigDecimal( null != pdMobile.getString("user_money") ? pdMobile.getString("user_money") : "0");
            BigDecimal bigMoneyLimit = new BigDecimal(null != pdMobile.getString("user_money_limit") ? pdMobile.getString("user_money_limit") : "0");
            BigDecimal curBalance = bigMoney.add(bigMoneyLimit);
            pd.put("cur_balance", curBalance.toString());
            pd.put("user_id", pdMobile.getString("user_id"));
            pd.put("store_id", "1");
            pd.put("add_time",time);
            if("1".equals(rechargeLoc)){//充值到充值金额
                pd.put("process_type", "2");
            }else if("0".equals(rechargeLoc)){
                pd.put("process_type", "10");//充值到可提现金额
            }
            pd.put("status", "1");

            userAccountManagerService3.save(pd);

            userManagerControllerService.recharge(pd);

            if("1".equals(rechargeLoc)){//如果是充值到充值金额的时候送用户大礼包;充值的时候给予邀请奖励
                Integer userId = Integer.valueOf(pd.getString("user_id"));
                this.donationBonus(userId,new BigDecimal(pd.getString("number")),recahrgeSn);

                this.rechargeCount(userId,new BigDecimal(pd.getString("number")));
            }



        }else if(appCodeName.equals("10")){
            superwhitelistService.recharge(pd);//充值到不可提现余额

            String _recharge_card_id = pd.getString("recharge_card_id");
            String recharge_card_id = "";
            String recharge_card_real_value = "";
            if (null != _recharge_card_id && !"".equals(_recharge_card_id)) {
                recharge_card_id = _recharge_card_id.split("\\,")[0].toString();
                recharge_card_real_value = _recharge_card_id.split("\\,")[1].toString();
                pd.put("recharge_card_id", recharge_card_id);
                pd.put("recharge_card_real_value", recharge_card_real_value);
            }

            pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
            User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
            pd.put("admin_user", user.getNAME());
            pd.put("amount", pd.get("number"));
            PageData _pd = new PageData();
            _pd = superwhitelistService.findById(pd);
            BigDecimal bigMoney = new BigDecimal( null != _pd.getString("money") ? _pd.getString("money") : "0");
            BigDecimal bigMoneyLimit = new BigDecimal(null != _pd.getString("money_limit") ? _pd.getString("money_limit") : "0");
            pd.put("cur_balance", bigMoney.add(bigMoneyLimit).toString());

            pd.put("user_id", _pd.getString("user_id"));
            pd.put("store_id", _pd.getString("store_id"));
            pd.put("add_time",time);
            pd.put("status", "1");
            pd.put("id", "");

            userAccountManagerService.save(pd);
        }


		if (null != pd.getString("recharge_card_id") && !"".equals(pd.getString("recharge_card_id"))) {
			pd.put("bonus_sn", SNGenerator.nextSN(6));
			pd.put("receive_time", time);
			pd.put("add_time", time);
			superwhitelistService.saveUserBonus(pd);
		}

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

    /**
     * 充值的时候给予邀请奖励
     * @param userId
     * @param rechargeAmount
     * @param rechargeSn
     */
    public  void rechargeCount(Integer userId,BigDecimal rechargeAmount){
        RechargePraiseParam rechargePraiseParam = new RechargePraiseParam();
        rechargePraiseParam.setUserId(userId);
        rechargePraiseParam.setMoney(rechargeAmount);
        String reqStr = JSON.toJSONString(rechargePraiseParam);
        String result = ManualAuditUtil.ManualAuditUtil(reqStr, urlConfig.getTgActUrl(), true);
        if(!StringUtils.isEmpty(result)){
            JsonObject json = JsonUtils.NewStringToJsonObject(result);
            if(json.get("code").getAsString().equals("0")) {
                logger.info("用户"+userId+"充值"+rechargeAmount+"给予邀请奖励成功");
            }else{
                logger.error("用户"+userId+"充值"+rechargeAmount+"给予邀请奖励失败");
            }
        }else{
            logger.info("充值的时候给予邀请奖励返回数据为空");
        }

    }

    public  void donationBonus(Integer userId,BigDecimal rechargeAmount,String rechargeSn){
        ReqCashEntity reqCashEntity = new ReqCashEntity();
        SessionUtils.generateSession();
        Session session = Jurisdiction.getSession();
        DonationBonusParam donationBonusParam = new DonationBonusParam();
        donationBonusParam.setPayLogId("200000");
        donationBonusParam.setUserId(userId);
        donationBonusParam.setOrderAmount(rechargeAmount);
        donationBonusParam.setAccountSn(rechargeSn);
        String reqStr = JSON.toJSONString(donationBonusParam);
        String result = ManualAuditUtil.ManualAuditUtil(reqStr, urlConfig.getDonationBonusUrl(), true);
        if(!StringUtils.isEmpty(result)){
            JsonObject json = JsonUtils.NewStringToJsonObject(result);
            if(json.get("code").getAsString().equals("0")) {
                logger.info("用户"+userId+"充值"+rechargeAmount+"赠送优惠券成功");
            }else if(json.get("code").getAsString().equals("301051")) {
                logger.info("赠送优惠券活动过期不进行赠送");
            }else{
                logger.error("用户"+userId+"充值"+rechargeAmount+"赠送优惠券失败");
            }
        }else{
            logger.info("赠送优惠券活动返回数据为空");
        }

    }


	/**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SuperWhiteList");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			superwhitelistService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}



	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
