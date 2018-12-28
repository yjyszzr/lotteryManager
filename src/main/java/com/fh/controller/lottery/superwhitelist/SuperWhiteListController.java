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
import com.fh.enums.SNBusinessCodeEnum;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SNGenerator;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.lottery.superwhitelist.SuperWhiteListManager;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountService;

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
		
		String user_id = pd.getString("user_id");		
		if(null != user_id && !"".equals(user_id)){
			pd.put("user_id", user_id.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String nickname = pd.getString("nickname");
		if (null != nickname && !"".equals(nickname)) {
			pd.put("nickname", nickname.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		
		page.setPd(pd);
		List<PageData>	varList = superwhitelistService.list(page);	//列出SuperWhiteList列表
		mv.setViewName("lottery/superwhitelist/superwhitelist_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("lottery/superwhitelist/superwhitelist_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
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
		mv.setViewName("lottery/superwhitelist/superwhitelist_recharge");
		mv.addObject("msg", "recharge");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value="/goUserAccounts")
	public ModelAndView goUserAccounts() throws Exception {
		/**
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userAccountManagerService.findByUserIdStoreId(pd);	//根据ID读取
		mv.setViewName("lottery/superwhitelist/superwhitelist_userAccounts");
		mv.addObject("msg", "deduction");
		mv.addObject("pd", pd);
		return mv;
		 */
		
		logBefore(logger, Jurisdiction.getUsername()+"列表UserAccounts");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String user_id = pd.getString("user_id");		
		if(null != user_id && !"".equals(user_id)){
			pd.put("user_id", user_id.trim());
		}
		String store_id = pd.getString("store_id");
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		}
  
		List<PageData>	varList = this.userAccountManagerService.findByUserIdStoreId(pd);	//列出SuperWhiteList列表
		mv.setViewName("lottery/superwhitelist/superwhitelist_userAccounts");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		pd.put("type", "0");
		pd.put("last_time", time);
		boolean flag = true;
		superwhitelistService.recharge(pd);
		
		pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
//		pd.put("admin_user", "");
		pd.put("amount", "-" + pd.get("number"));
		PageData _pd = new PageData();
		_pd = superwhitelistService.findById(pd);
		pd.put("cur_balance", _pd.getString("money"));
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
		superwhitelistService.recharge(pd);
		
		pd.put("account_sn", SNGenerator.nextSN(SNBusinessCodeEnum.ACCOUNT_SN.getCode()));
//		pd.put("admin_user", "");
		pd.put("amount", pd.get("type").equals("-") ? "-" + pd.get("number") : pd.get("number"));
		PageData _pd = new PageData();
		_pd = superwhitelistService.findById(pd);
		pd.put("cur_balance", _pd.getString("money"));
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
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SuperWhiteList到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户id");	//1
		titles.add("备注2");	//2
		titles.add("手机号");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("账户余额");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		titles.add("备注20");	//20
		titles.add("备注21");	//21
		titles.add("备注22");	//22
		titles.add("备注23");	//23
		titles.add("备注24");	//24
		titles.add("备注25");	//25
		titles.add("备注26");	//26
		titles.add("备注27");	//27
		titles.add("备注28");	//28
		titles.add("备注29");	//29
		titles.add("备注30");	//30
		titles.add("备注31");	//31
		titles.add("备注32");	//32
		titles.add("备注33");	//33
		titles.add("备注34");	//34
		titles.add("备注35");	//35
		titles.add("备注36");	//36
		titles.add("备注37");	//37
		titles.add("备注38");	//38
		titles.add("备注39");	//39
		titles.add("备注40");	//40
		titles.add("备注41");	//41
		dataMap.put("titles", titles);
		List<PageData> varOList = superwhitelistService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("user_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("mobile"));	    //3
			vpd.put("var4", varOList.get(i).getString("email"));	    //4
			vpd.put("var5", varOList.get(i).getString("password"));	    //5
			vpd.put("var6", varOList.get(i).getString("salt"));	    //6
			vpd.put("var7", varOList.get(i).getString("nickname"));	    //7
			vpd.put("var8", varOList.get(i).get("sex").toString());	//8
			vpd.put("var9", varOList.get(i).get("birthday").toString());	//9
			vpd.put("var10", varOList.get(i).getString("detail_address"));	    //10
			vpd.put("var11", varOList.get(i).getString("headimg"));	    //11
			vpd.put("var12", varOList.get(i).getString("user_money"));	    //12
			vpd.put("var13", varOList.get(i).getString("user_money_limit"));	    //13
			vpd.put("var14", varOList.get(i).getString("frozen_money"));	    //14
			vpd.put("var15", varOList.get(i).get("pay_point").toString());	//15
			vpd.put("var16", varOList.get(i).get("rank_point").toString());	//16
			vpd.put("var17", varOList.get(i).get("reg_time").toString());	//17
			vpd.put("var18", varOList.get(i).getString("reg_ip"));	    //18
			vpd.put("var19", varOList.get(i).get("last_time").toString());	//19
			vpd.put("var20", varOList.get(i).getString("last_ip"));	    //20
			vpd.put("var21", varOList.get(i).getString("mobile_supplier"));	    //21
			vpd.put("var22", varOList.get(i).getString("mobile_province"));	    //22
			vpd.put("var23", varOList.get(i).getString("mobile_city"));	    //23
			vpd.put("var24", varOList.get(i).getString("reg_from"));	    //24
			vpd.put("var25", varOList.get(i).getString("surplus_password"));	    //25
			vpd.put("var26", varOList.get(i).getString("pay_pwd_salt"));	    //26
			vpd.put("var27", varOList.get(i).get("user_status").toString());	//27
			vpd.put("var28", varOList.get(i).get("pass_wrong_count").toString());	//28
			vpd.put("var29", varOList.get(i).get("user_type").toString());	//29
			vpd.put("var30", varOList.get(i).getString("is_real"));	    //30
			vpd.put("var31", varOList.get(i).getString("user_remark"));	    //31
			vpd.put("var32", varOList.get(i).get("add_time").toString());	//32
			vpd.put("var33", varOList.get(i).getString("push_key"));	    //33
			vpd.put("var34", varOList.get(i).getString("device_channel"));	    //34
			vpd.put("var35", varOList.get(i).get("is_business").toString());	//35
			vpd.put("var36", varOList.get(i).get("lon").toString());	//36
			vpd.put("var37", varOList.get(i).get("lat").toString());	//37
			vpd.put("var38", varOList.get(i).getString("city"));	    //38
			vpd.put("var39", varOList.get(i).getString("province"));	    //39
			vpd.put("var40", varOList.get(i).get("has_third_user_id").toString());	//40
			vpd.put("var41", varOList.get(i).get("is_super_white").toString());	//41
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
