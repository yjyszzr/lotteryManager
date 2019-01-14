package com.fh.controller.lottery.customer;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.customer.CustomerManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.opensymphony.oscache.util.StringUtil;

/** 
 * 说明：销售
 * 创建人：FH Q313596790
 * 创建时间：2019-01-14
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {
	
	String menuUrl = "customer/list.do"; //菜单地址(权限用)
	@Resource(name="customerService")
	private CustomerManager customerService;
	
	@Resource(name="useraccountService")
	private UserAccountService userAccountManagerService;
	
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	@RequestMapping(value="/reset")
	public ModelAndView reset() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("last_add_time",  DateUtilNew.getCurrentTimeLong());
		pd.put("last_add_seller_name", "NULL");
		pd.put("last_add_seller_id", "NULL");
		pd.put("distribute_state", "0");
		
		customerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	@RequestMapping(value="/checkMobile")
	@ResponseBody
	public Object checkMobile() throws Exception{
		/**
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> bonusList = this.queryActivityBonusListByActType(ProjectConstant.Bonus_TYPE_GIVE_USER);
		
		List<PageData> newbonusList = new ArrayList<>();
		bonusList.stream().forEach(s->{
			PageData newPd = new PageData();
			newPd.put("bonus_id", s.getString("bonus_id"));
			newPd.put("min_goods_amount", s.getString("min_goods_amount"));
			newPd.put("bonus_price", s.getString("bonus_amount"));
			newbonusList.add(newPd);
		});
		
		map.put("list", newbonusList);
		return AppUtil.returnObject(new PageData(), map);
		**/
		try {
			Map<String,Object> map = new HashMap<String,Object>();

			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			PageData _pd = this.customerService.getCountOrderByMobile(pd);
			long _count = new Long(_pd.getString("_count"));
			if (_count > 0) {
				map.put("flag", false);
				map.put("msg", "2019年11月7号之后有购过彩");
				return AppUtil.returnObject(new PageData(), map);
			}
			
//			_pd = null;
//			_count = 0;
//			_pd = this.userAccountManagerService.getCountOrderByMobile(pd);
//			_count = new Long(_pd.getString("_count"));
//			if (_count > 0) {
//				map.put("flag", false);
//				map.put("msg", "2019年11月7号之后有购过彩");
//				return AppUtil.returnObject(new PageData(), map);
//			}
			
			_pd = null;
			_pd = this.customerService.findById(pd);
			if (null != _pd) {
				String distribute_state = _pd.getString("distribute_state");
				if (distribute_state.equals("1")) {
					map.put("flag", false);
					map.put("msg", "已存在");
					return AppUtil.returnObject(new PageData(), map);
				}
			}
			
			map.put("flag", true);
			map.put("msg", "可以录入");
			return AppUtil.returnObject(new PageData(), map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Customer");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		// ~~~~~~~~~~~~~~~
		boolean flag = true;
		PageData _pd = this.customerService.getCountOrderByMobile(pd);  // 1
		long _count = new Long(_pd.getString("_count"));
		if (_count > 0) {
//			map.put("flag", false);
//			map.put("msg", "2019年11月7号之后有购过彩");
			flag = false;
		}
		
//		if (flag) {
//			_pd = null;
//			_count = 0;
//			_pd = this.userAccountManagerService.getCountOrderByMobile(pd); //2 
//			_count = new Long(_pd.getString("_count"));
//			if (_count > 0) {
////				map.put("flag", false);
////				map.put("msg", "2019年11月7号之后有购过彩");
//				flag = false;
//			}
//		}
		
		PageData customer = null;
		if (flag) {
			customer = this.customerService.findById(pd);
			if (null != customer) {
				String distribute_state = customer.getString("distribute_state");
				if (distribute_state.equals("1")) {
//					map.put("flag", false);
//					map.put("msg", "已存在");
					flag = false;	
				}
			}
			
		}
		// ~~~~~~~~~~~~~~~
		
		if (flag) {
			// to db
			if (customer == null) {
				String add_time = DateUtilNew.getCurrentTimeLong() + "";
				pd.put("first_add_time", add_time);
				pd.put("last_add_time", add_time);
				
				String first_add_seller_id = null;
				String first_add_seller_name = null;
				String last_add_seller_id = null;
				String last_add_seller_name = null;
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				first_add_seller_id = user.getUSER_ID();
				first_add_seller_name = user.getNAME();
				last_add_seller_id = user.getUSER_ID();
				last_add_seller_name =  user.getNAME();
				pd.put("first_add_seller_id", first_add_seller_id);
				pd.put("first_add_seller_name", first_add_seller_name);
				pd.put("last_add_seller_id", last_add_seller_id);
				pd.put("last_add_seller_name", last_add_seller_name);
				
				pd.put("distribute_state", 1);
				
				
				PageData _user = usermanagercontrollerService.queryUserByMobile(pd.getString("mobile"));
				if (null == _user) {
					pd.put("user_state", 1);
				} else {
					pd.put("user_state", 2);
				}
				
				this.customerService.save(pd);
			} else {
				String mobile = "";
				String user_name = "";
				String user_source = "";
				mobile = pd.getString("mobile");
				user_name = pd.getString("user_name");
				user_source = pd.getString("user_source");
						
				customer.put("mobile", mobile);
				customer.put("user_name", user_name);
				customer.put("user_source", user_source);
				customer.put("distribute_state", 1);
				
				String last_add_seller_id = null;
				String last_add_seller_name = null;
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				last_add_seller_id = user.getUSER_ID();
				last_add_seller_name = user.getNAME();
				customer.put("last_add_seller_id", last_add_seller_id);
				customer.put("last_add_seller_name", last_add_seller_name);
				
				String add_time = DateUtilNew.getCurrentTimeLong() + "";
				customer.put("last_add_time", add_time);
				
				PageData _user = usermanagercontrollerService.queryUserByMobile(pd.getString("mobile"));
				if (null == _user) {
					customer.put("user_state", 1);
				} else {
					customer.put("user_state", 2);
				}
				
				this.customerService.edit(customer);
			}
			
		}
		
		
////		pd.put("_id", this.get32UUID());	//主键
//		customerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Customer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		String keywords = pd.getString("keywords");				//关键词检索条件
//		if(null != keywords && !"".equals(keywords)){
//			pd.put("keywords", keywords.trim());
//		}
		
		String _start_last_add_time = pd.getString("start_last_add_time");
		if (null != _start_last_add_time && !"".equals(_start_last_add_time)) {
			pd.put("start_last_add_time", DateUtilNew.getMilliSecondsByStr(_start_last_add_time.trim()));
		}
		String _end_last_add_time = pd.getString("end_last_add_time");
		if (null != _end_last_add_time && !"".equals(_end_last_add_time)) {
			pd.put("end_last_add_time", DateUtilNew.getMilliSecondsByStr(_end_last_add_time.trim()));
		}
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String last_add_seller_id = user.getUSER_ID();
		System.out.println("last_add_seller_id=" + last_add_seller_id);
		pd.put("last_add_seller_id", last_add_seller_id);
		
		page.setPd(pd);
		List<PageData>	varList = customerService.list(page);	//列出Customer列表
		
		pd.put("start_last_add_time", _start_last_add_time.trim());
		pd.put("end_last_add_time", _end_last_add_time.trim());
		
		mv.setViewName("lottery/customer/customer_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/** 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/total")
	public ModelAndView total(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Customer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		String keywords = pd.getString("keywords");				//关键词检索条件
//		if(null != keywords && !"".equals(keywords)){
//			pd.put("keywords", keywords.trim());
//		}
		
		String _start_last_add_time = pd.getString("start_last_add_time");
		if (null != _start_last_add_time && !"".equals(_start_last_add_time)) {
			pd.put("start_last_add_time", DateUtilNew.getMilliSecondsByStr(_start_last_add_time.trim()));
		}
		String _end_last_add_time = pd.getString("end_last_add_time");
		if (null != _end_last_add_time && !"".equals(_end_last_add_time)) {
			pd.put("end_last_add_time", DateUtilNew.getMilliSecondsByStr(_end_last_add_time.trim()));
		}
		
//		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
//		String last_add_seller_id = user.getUSER_ID();
//		System.out.println("last_add_seller_id=" + last_add_seller_id);
//		pd.put("last_add_seller_id", last_add_seller_id);
		
		page.setPd(pd);
		List<PageData>	varList = customerService.list(page);	//列出Customer列表
		
		pd.put("start_last_add_time", _start_last_add_time.trim());
		pd.put("end_last_add_time", _end_last_add_time.trim());
		
		mv.setViewName("lottery/customer/customer_total");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@RequestMapping(value="/seeTotal")
	public ModelAndView seeTotal(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		id
		PageData customer = this.customerService.findById(pd);
		
		List<PageData> ordes = null;
		String user_id = "";
		String last_add_time = "";
		try {
			user_id = customer.getString("user_id");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			last_add_time = customer.getString("last_add_time");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!StringUtil.isEmpty(user_id)
			&& !StringUtil.isEmpty(last_add_time)	
		) {
			PageData _pd = new PageData();
			_pd.put("user_id", user_id);
//			_pd.put("pay_status", 1);
			_pd.put("start_add_time", last_add_time);
			
//			String start_add_time = "";
//			try {
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
//				Calendar   cal_1=Calendar.getInstance();//获取当前日期 
//				cal_1.add(Calendar.MONTH, -1);
//				cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
//				String firstDay = format.format(cal_1.getTime());
//				start_add_time = String.valueOf(format.parse(firstDay).getTime());   
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			_pd.put("start_add_time", start_add_time);
//			_pd.put("end_add_time", System.currentTimeMillis());
			
			ordes = this.customerService.getOrdes(_pd);
			List<PageData> ordes2 = this.userAccountManagerService.getOrdes(_pd);
			if (null != ordes) {
				if (null != ordes2) ordes.addAll(ordes2);
			} else {
				ordes = ordes2;
			}
		}
		
		mv.setViewName("lottery/customer/customer_see_total");
//		mv.addObject("msg", "save");
		mv.addObject("customer", customer);
		mv.addObject("ordes", ordes);
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
		mv.setViewName("lottery/customer/customer_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	
	@RequestMapping(value="/see")
	public ModelAndView see()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		id
		PageData customer = this.customerService.findById(pd);
		
		List<PageData> ordes = null;
		String user_id = "";
		try {
			user_id = customer.getString("user_id");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!StringUtil.isEmpty(user_id)) {
			PageData _pd = new PageData();
			_pd.put("user_id", user_id);
			_pd.put("pay_status", 1);
			
			String start_add_time = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
				Calendar   cal_1=Calendar.getInstance();//获取当前日期 
				cal_1.add(Calendar.MONTH, -1);
				cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
				String firstDay = format.format(cal_1.getTime());
				start_add_time = String.valueOf(format.parse(firstDay).getTime());   
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			_pd.put("start_add_time", start_add_time);
			_pd.put("end_add_time", System.currentTimeMillis());
			ordes = this.customerService.getOrdes(_pd);
//			List<PageData> ordes2 = this.userAccountManagerService.getOrdes(_pd);
//			if (null != ordes) {
//				if (null != ordes2) ordes.addAll(ordes2);
//			} else {
//				ordes = ordes2;
//			}
		}
		
		mv.setViewName("lottery/customer/customer_see");
//		mv.addObject("msg", "save");
		mv.addObject("customer", customer);
		mv.addObject("ordes", ordes);
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
		pd = customerService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/customer/customer_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			customerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Customer到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		dataMap.put("titles", titles);
		List<PageData> varOList = customerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).get("user_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("mobile"));	    //3
			vpd.put("var4", varOList.get(i).getString("user_name"));	    //4
			vpd.put("var5", varOList.get(i).get("user_state").toString());	//5
			vpd.put("var6", varOList.get(i).get("user_source").toString());	//6
			vpd.put("var7", varOList.get(i).get("pay_state").toString());	//7
			vpd.put("var8", varOList.get(i).get("first_pay_time").toString());	//8
			vpd.put("var9", varOList.get(i).get("first_add_time").toString());	//9
			vpd.put("var10", varOList.get(i).getString("first_add_seller_name"));	    //10
			vpd.put("var11", varOList.get(i).getString("first_add_seller_id"));	    //11
			vpd.put("var12", varOList.get(i).get("last_add_time").toString());	//12
			vpd.put("var13", varOList.get(i).getString("last_add_seller_name"));	    //13
			vpd.put("var14", varOList.get(i).getString("last_add_seller_id"));	    //14
			vpd.put("var15", varOList.get(i).get("distribute_state").toString());	//15
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
