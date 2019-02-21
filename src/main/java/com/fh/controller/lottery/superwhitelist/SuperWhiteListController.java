package com.fh.controller.lottery.superwhitelist;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.enums.SNBusinessCodeEnum;
import com.fh.service.lottery.customer.CustomerManager;
import com.fh.service.lottery.rechargecard.impl.RechargeCardService;
import com.fh.service.lottery.superwhitelist.SuperWhiteListManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SNGenerator;

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
	
	@Resource(name="rechargecardService")
	private RechargeCardService rechargecardService;
	
	@Resource(name="customerService")
	private CustomerManager customerService;
	
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
//		pd.put("user_id_id", this.get32UUID());	//主键
		pd.put("user_id", "0");	//用户id
		pd.put("mobile", "");	//手机号
		pd.put("user_money", "");	//账户余额
		superwhitelistService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
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
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		String user_id = pd.getString("user_id");		
//		if(null != user_id && !"".equals(user_id)){
//			pd.put("user_id", user_id.trim());
//		}
//		String user_name = pd.getString("user_name");
//		if (null != user_name && !"".equals(user_name)) {
//			pd.put("user_name", user_name.trim());
//		}
//		String nickname = pd.getString("nickname");
//		if (null != nickname && !"".equals(nickname)) {
//			pd.put("nickname", nickname.trim());
//		}
//		String mobile = pd.getString("mobile");
//		if (null != mobile && !"".equals(mobile)) {
//			pd.put("mobile", mobile.trim());
//		}
		
		page.setPd(pd);
		List<PageData>	varList = superwhitelistService.list(page);	//列出SuperWhiteList列表
		mv.setViewName("lottery/superwhitelist/superwhitelist_list");
 
		if (null != varList && varList.size() > 0) {
			for (PageData pageData : varList) {
				try {
//					String user_id = pageData.getString("user_id");
//					String store_id = pageData.getString("store_id");
							
					String recharge_card_real_value = "";
					PageData _pageData = this.superwhitelistService.getSumRechargeCardRealValue(pageData);
					if (null != _pageData) {
						recharge_card_real_value = _pageData.getString("recharge_card_real_value");
						pageData.put("recharge_card_real_value", recharge_card_real_value);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		 
		}
		
		mv.addObject("varList", varList);
		
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SuperWhiteList到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID");	//1
		titles.add("用户名");	//2
		titles.add("昵称");	//3
		titles.add("手机号");	//4
		titles.add("账户余额");	//5
		titles.add("大礼包总金额");	//6
		titles.add("店铺");	//7
		 
		dataMap.put("titles", titles);
		
		
//		String mobile = pd.getString("mobile");
//		if (null != mobile && !"".equals(mobile)) {
//			pd.put("mobile", mobile.trim());
//		}
		List<PageData> varOList = superwhitelistService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("user_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("nickname"));	    //3
			vpd.put("var4", varOList.get(i).getString("mobile"));	    //4
			vpd.put("var5", varOList.get(i).getString("money"));	

			try {
//				String user_id = pageData.getString("user_id");
//				String store_id = pageData.getString("store_id");
						
				String recharge_card_real_value = "";
				PageData _pageData = this.superwhitelistService.getSumRechargeCardRealValue(varOList.get(i));
				if (null != _pageData) {
					recharge_card_real_value = _pageData.getString("recharge_card_real_value");
					vpd.put("var6", recharge_card_real_value);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			vpd.put("var7", varOList.get(i).getString("name"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
//	/**列表
//	 * @param page
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/listUserAccount")
//	public ModelAndView listUserAccount(Page page) throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"列表SuperWhiteList");
//		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		
//		String user_id = pd.getString("user_id");		
//		if(null != user_id && !"".equals(user_id)){
//			pd.put("user_id", user_id.trim());
//		}
////		String user_name = pd.getString("user_name");
////		if (null != user_name && !"".equals(user_name)) {
////			pd.put("user_name", user_name.trim());
////		}
////		String nickname = pd.getString("nickname");
////		if (null != nickname && !"".equals(nickname)) {
////			pd.put("nickname", nickname.trim());
////		}
////		String mobile = pd.getString("mobile");
////		if (null != mobile && !"".equals(mobile)) {
////			pd.put("mobile", mobile.trim());
////		}
//		
//		page.setPd(pd);
//		List<PageData>	varList = this.userAccountManagerService.list(page);	//列出SuperWhiteList列表
//		mv.setViewName("lottery/superwhitelist/superwhitelist_listUserAccount");
//		mv.addObject("varList", varList);
//		mv.addObject("pd", pd);
//		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
//		return mv;
//	}
	
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
		pd = superwhitelistService.findById(pd);	//根据ID读取
		
		List<PageData> rechargeCardList = rechargecardService.listAll(pd);
		pd.put("rechargeCardList", rechargeCardList);
		
		mv.setViewName("lottery/superwhitelist/superwhitelist_recharge");
		mv.addObject("msg", "recharge");
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
		List<PageData>	varList = this.superwhitelistService.listAccount(page);
		
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
		titles.add("余额记录");	//4
		titles.add("大礼包余额");
		titles.add("状态");	//5
		titles.add("扣款原因");	//6
		titles.add("充值时间");	//7
		titles.add("店铺");	//8
		titles.add("充值状态");	//9
		titles.add("操作人");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = superwhitelistService.listAccountAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("mobile"));	    //2
			vpd.put("var3", varOList.get(i).getString("cur_balance"));	    //3
			vpd.put("var4", varOList.get(i).getString("amount"));	    //4
			
//			String recharge_card_real_value = "";
//			try {
////				String user_id = pageData.getString("user_id");
////				String store_id = pageData.getString("store_id");
//				
//				PageData _pageData = this.superwhitelistService.getSumRechargeCardRealValue(varOList.get(i));
//				if (null != _pageData) {
//					recharge_card_real_value = _pageData.getString("recharge_card_real_value");
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
			vpd.put("var5", varOList.get(i).getString("recharge_card_real_value"));
			
			String processTypeStr = varOList.get(i).getString("process_type");
			if (null != processTypeStr && processTypeStr.equals("1")) {
				processTypeStr = "中奖";
			} else if (null != processTypeStr && processTypeStr.equals("2")) {
				processTypeStr = "充值";
			} else if (null != processTypeStr && processTypeStr.equals("3")) {
				processTypeStr = "购彩";
			} else if (null != processTypeStr && processTypeStr.equals("5")) {
				processTypeStr = "红包";
			} else if (null != processTypeStr && processTypeStr.equals("6")) {
				processTypeStr = "账户回滚";
			} else if (null != processTypeStr && processTypeStr.equals("7")) {
				processTypeStr = "购券";
			} else if (null != processTypeStr && processTypeStr.equals("8")) {
				processTypeStr = "退款";	
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
			vpd.put("var9", varOList.get(i).get("name").toString());	//8
			
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
				// TODO: handle exception
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
		pd = superwhitelistService.findById(pd);	//根据ID读取
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
//		superwhitelistService.deduction(pd);
		superwhitelistService.deductionToMoneyLimit(pd);
		
		pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("admin_user", user.getNAME());
		pd.put("amount", "-" + pd.get("number"));
		PageData _pd = new PageData();
		_pd = superwhitelistService.findById(pd);
		pd.put("cur_balance", null != _pd.getString("money") ? _pd.getString("money") : 0);
		pd.put("user_id", _pd.getString("user_id"));
		pd.put("store_id", _pd.getString("store_id"));
		pd.put("add_time",time);
		pd.put("status", "1");
		pd.put("id", "");
		userAccountManagerService.save(pd);
				
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
//		superwhitelistService.recharge(pd);
//		充值到不可提现余额
		superwhitelistService.rechargeToMoneyLimit(pd);
		
//		recharge_card_id
//		recharge_card_real_value
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
//		pd.put("amount", pd.get("type").equals("-") ? "-" + pd.get("number") : pd.get("number"));
		pd.put("amount", pd.get("number"));
		PageData _pd = new PageData();
		_pd = superwhitelistService.findById(pd);
		pd.put("cur_balance", null != _pd.getString("money") ? _pd.getString("money") : 0);
		pd.put("user_id", _pd.getString("user_id"));
		pd.put("store_id", _pd.getString("store_id"));
		pd.put("add_time",time);
		if (pd.getString("type").equals("1")) {
			pd.put("process_type", "2");
		} else if (pd.getString("type").equals("0")) {
			pd.put("process_type", "8");
		}
		pd.put("status", "1");
		pd.put("id", "");
		
		userAccountManagerService.save(pd);
		
//		try {
//			if (pd.getString("type").equals("1")) {
//				String user_id = _pd.getString("user_id");
//				PageData _customer = new PageData();
//				_customer.put("user_id", user_id);
//				_customer.put("first_pay_time", time);
//				String mobile = "";
//				PageData _user = this.superwhitelistService.findUserByUserid(_pd);
//				mobile = _user.getString("mobile");
//				_customer.put("mobile", mobile);
//				
//				this.customerService.setFirstPayTime(_customer);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
				
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
