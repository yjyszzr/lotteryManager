package com.fh.controller.lottery.customer;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.customer.CustomerManager;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.*;
import com.opensymphony.oscache.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：销售 创建人：FH Q313596790 创建时间：2019-01-14
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {

	String menuUrl = "customer/list.do"; // 菜单地址(权限用)
	@Resource(name = "customerService")
	private CustomerManager customerService;

	@Resource(name = "useraccountService")
	private UserAccountService userAccountService;

	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerService userAccountManagerService;

	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "orderService")
	private OrderManager orderService;

	@Resource(name = "userrealmanagerService")
	private UserRealManagerService userrealmanagerService;

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Customer");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
//			return;
//		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Customer");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
//			return null;
//		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/reset")
	public ModelAndView reset() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Customer");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
//			return null;
//		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData _pd = customerService.findById(pd);

//		pd.put("last_add_time",  DateUtilNew.getCurrentTimeLong());
		pd.put("last_add_time", _pd.getString("last_add_time"));
		pd.put("last_add_seller_name", " ");
		pd.put("last_add_seller_id", "(NULL)");
		pd.put("distribute_state", "0");

		customerService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/checkMobile")
	@ResponseBody
	public Object checkMobile() throws Exception {
		/**
		 * Map<String,Object> map = new HashMap<String,Object>(); List<PageData>
		 * bonusList =
		 * this.queryActivityBonusListByActType(ProjectConstant.Bonus_TYPE_GIVE_USER);
		 * 
		 * List<PageData> newbonusList = new ArrayList<>();
		 * bonusList.stream().forEach(s->{ PageData newPd = new PageData();
		 * newPd.put("bonus_id", s.getString("bonus_id")); newPd.put("min_goods_amount",
		 * s.getString("min_goods_amount")); newPd.put("bonus_price",
		 * s.getString("bonus_amount")); newbonusList.add(newPd); });
		 * 
		 * map.put("list", newbonusList); return AppUtil.returnObject(new PageData(),
		 * map);
		 **/
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData queryPd = new PageData();
			queryPd.put("USER_ID", user.getUSER_ID());
			PageData seller = userService.findById(queryPd);
			if (!StringUtil.isEmpty(seller.getString("PHONE")) && pd.getString("mobile").trim().equals(seller.getString("PHONE").trim())) {
				map.put("flag", false);
				map.put("msg", "不能添加自身手机号");
				return AppUtil.returnObject(new PageData(), map);
			}

			String user_id_2 = "";
			PageData _pd = new PageData();
			_pd.put("mobile", pd.getString("mobile"));
			PageData _user = this.userAccountService.getUserByMobile(_pd);
			if (null != _user) {
				user_id_2 = _user.getString("user_id");
			}
			pd.put("user_id_2", user_id_2);

			_pd = null;
			_pd = this.customerService.getCountOrderByMobile(pd);
			Integer _count = Integer.valueOf(_pd.getString("_count"));
			if (_count > 0) {
				map.put("flag", false);
				map.put("msg", "2018年11月7号之后有购过彩");
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

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Customer");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// ~~~~~~~~~~~~~~~
		boolean flag = true;

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData queryPd = new PageData();
		queryPd.put("USER_ID", user.getUSER_ID());
		PageData seller = userService.findById(queryPd);
		if (!StringUtil.isEmpty(seller.getString("PHONE")) && pd.getString("mobile").trim().equals(seller.getString("PHONE").trim())) {
//			map.put("flag", false);
//			map.put("msg", "不能添加自身手机号");
//			return AppUtil.returnObject(new PageData(), map);
			flag = false;
		}

		if (flag) {

			String user_id_2 = "";
			PageData _pd = new PageData();
			_pd.put("mobile", pd.getString("mobile"));
			PageData _user = this.userAccountService.getUserByMobile(_pd);
			if (null != _user) {
				user_id_2 = _user.getString("user_id");
			}
			pd.put("user_id_2", user_id_2);

			_pd = this.customerService.getCountOrderByMobile(pd); // 1
			long _count = new Long(_pd.getString("_count"));
			if (_count > 0) {
//				map.put("flag", false);
//				map.put("msg", "2019年11月7号之后有购过彩");
				flag = false;
			}
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
//				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				first_add_seller_id = user.getUSER_ID();
				first_add_seller_name = user.getNAME();
				last_add_seller_id = user.getUSER_ID();
				last_add_seller_name = user.getNAME();
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
//				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
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
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Customer");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

//		String keywords = pd.getString("keywords");				//关键词检索条件
//		if(null != keywords && !"".equals(keywords)){
//			pd.put("keywords", keywords.trim());
//		}
//		String pay_state = pd.getString("pay_state");
//		pd.put("pay_state", pay_state);

		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
		    pd.put("mobile", mobile.trim());
		}
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

//		String userName = pd.getString("user_name");
//		if(!StringUtils.isEmpty(userName)){
//			pd.put("last_add_seller_name",userName.trim());
//		}

		page.setPd(pd);
		List<PageData> varList = customerService.list(page); // 列出Customer列表

		pd.put("start_last_add_time", _start_last_add_time.trim());
		pd.put("end_last_add_time", _end_last_add_time.trim());

		mv.setViewName("lottery/customer/customer_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
//		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	/**
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/total")
	public ModelAndView total(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Customer");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

//		String keywords = pd.getString("keywords");				//关键词检索条件
//		if(null != keywords && !"".equals(keywords)){
//			pd.put("keywords", keywords.trim());
//		}
	    String mobile = pd.getString("mobile");
        if (null != mobile && !"".equals(mobile)) {
            pd.put("mobile", mobile.trim());
        }
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
		List<PageData> varList = customerService.list(page); // 列出Customer列表

		pd.put("start_last_add_time", _start_last_add_time.trim());
		pd.put("end_last_add_time", _end_last_add_time.trim());

		mv.setViewName("lottery/customer/customer_total");
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
	public ModelAndView exportExcel() throws Exception {
//		logBefore(logger, Jurisdiction.getUsername()+"导出Customer到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

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

		List<PageData> varOList = customerService.listAll(pd); // 列出Customer列表

		pd.put("start_last_add_time", _start_last_add_time.trim());
		pd.put("end_last_add_time", _end_last_add_time.trim());

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("手机号"); // 1
		titles.add("用户名"); // 2
		titles.add("用户类型"); // 3
		titles.add("用户来源"); // 4
		titles.add("是否已购彩"); // 5
		titles.add("销售员"); // 6
		titles.add("录入时间"); // 7
		titles.add("购彩时间"); // 8
		dataMap.put("titles", titles);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("mobile").toString()); // 1

			String user_name = "";
			try {
				user_name = varOList.get(i).get("user_name").toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			vpd.put("var2", user_name); // 2

			String user_state = "";
			if (varOList.get(i).getString("user_state").equals("1"))
				user_state = "新用户";
			if (varOList.get(i).getString("user_state").equals("2"))
				user_state = "老用户";
			vpd.put("var3", user_state); // 3

			String user_source = "";
			if (varOList.get(i).getString("user_source").equals("1"))
				user_source = "公司资源";
			if (varOList.get(i).getString("user_source").equals("2"))
				user_source = "微信群";
			if (varOList.get(i).getString("user_source").equals("3"))
				user_source = "QQ群";
			if (varOList.get(i).getString("user_source").equals("4"))
				user_source = "好友推荐";
			if (varOList.get(i).getString("user_source").equals("5"))
				user_source = "电话访问";
			if (varOList.get(i).getString("user_source").equals("6"))
				user_source = "其它";
			if (varOList.get(i).getString("user_source").equals("7"))
				user_source = "维护资源";
			vpd.put("var4", user_source); // 4

			String pay_state = "";
			if (varOList.get(i).getString("pay_state").equals("1")) {
				pay_state = "已购彩";

			} else if (varOList.get(i).getString("pay_state").equals("0")) {
				pay_state = "未购彩";

			}

			vpd.put("var5", pay_state); // 5

			vpd.put("var6", varOList.get(i).get("last_add_seller_name").toString()); // 6

			String last_add_time = "";
			last_add_time = varOList.get(i).get("last_add_time").toString();
			if (null != last_add_time && !last_add_time.equals("")) {
				last_add_time = DateUtil.toSDFTime(new Long(last_add_time) * 1000);
			}
			vpd.put("var7", last_add_time); // 7

			String first_pay_time = "";
			first_pay_time = varOList.get(i).getString("first_pay_time");
			if (null != first_pay_time && !first_pay_time.equals("")) {
				first_pay_time = DateUtil.toSDFTime(new Long(first_pay_time) * 1000);
			}
			vpd.put("var8", first_pay_time); // 8

			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@RequestMapping(value = "/seeTotal")
	public ModelAndView seeTotal(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData customer = this.customerService.findById(pd);
		String last_add_time = customer.getString("last_add_time");
		String mobile = customer.getString("mobile");
		PageData _pd = new PageData();
		List<PageData> ordes = new ArrayList<>();
		_pd.put("mobile", mobile);
		_pd.put("pay_status", 1);
		_pd.put("start_add_time", last_add_time);
		ordes = this.customerService.getOrdes(_pd);

		mv.setViewName("lottery/customer/customer_see_total");
		mv.addObject("customer", customer);
		mv.addObject("varList", ordes);
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
		mv.setViewName("lottery/customer/customer_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/see")
	public ModelAndView see() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		id
		PageData customer = this.customerService.findById(pd);

		List<PageData> ordes = null;
		String mobile = pd.getString("mobile");
		PageData _pd = new PageData();
		_pd.put("mobile", mobile);
//		String user_id_1 = "";
//		PageData user_1 = this.customerService.getUserByMobile(_pd);
//		if (null != user_1) {
//			user_id_1 = user_1.getString("user_id");
//		}
//
//		String user_id_2 = "";
//		PageData user_2 = this.userAccountService.getUserByMobile(_pd);
//		if (null != user_2) {
//			user_id_2 = user_2.getString("user_id");
//		}
		_pd.put("pay_status", 1);

		String start_add_time = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Calendar cal_1 = Calendar.getInstance();// 获取当前日期
			cal_1.add(Calendar.MONTH, -1);
			cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			String firstDay = format.format(cal_1.getTime());
			System.out.println("firstDay:" + firstDay);
			start_add_time = String.valueOf(format.parse(firstDay).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 不懂为啥有这个时间限制
//		_pd.put("start_add_time", start_add_time);
//		String user_id_s = "";
//		if (!StringUtil.isEmpty(user_id_1)) {
//			user_id_s = user_id_1;
//		}
//		if (!StringUtil.isEmpty(user_id_2)) {
//			user_id_s += "," + user_id_2;
//		}
//
//		_pd.put("user_id_s", user_id_s);
//		_pd.put("user_id_1", user_id_1);
//		_pd.put("user_id_2", user_id_2);
//		if (StringUtil.isEmpty(user_id_s)) {
//			ordes = null;
//		} else {
//			ordes = this.customerService.getOrdes(_pd);
//		}

		ordes = this.customerService.getOrdes(_pd);
		mv.setViewName("lottery/customer/customer_see");
//		mv.addObject("msg", "save");
		mv.addObject("customer", customer);
		mv.addObject("ordes", ordes);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = customerService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/customer/customer_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/toAssign")
	public ModelAndView toAssign(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 销售人员列表
		PageData pd = new PageData();
		List<PageData> pageDataList = userService.querySellers(pd);
		mv.addObject("pageDataList", pageDataList);
		mv.addObject("ids", ids);
		mv.setViewName("lottery/customer/assign_page");
		return mv;
	}

	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 销售人员列表
		PageData pd = new PageData();
		List<PageData> pageDataList = userService.querySellers(pd);
		mv.addObject("pageDataList", pageDataList);
		mv.setViewName("lottery/customer/upload_customer_from_excel");
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file, @RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "user_name", required = false) String userName) throws Exception {
		ModelAndView mv = this.getModelAndView();
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
//			return null;
//		}
		if (null != file && !file.isEmpty()) {
			String fname = file.getOriginalFilename();
			if (fname.contains(".")) {
				fname = fname.split("\\.")[0];
			}
			String filePath = PathUtil.getClasspath() + Const.WITHDRAWALFILE;
			// 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, DateUtilNew.getCurrentDateTime2()); // 执行上传
			List<PageData> listPd = (List) readExcel(filePath, fileName, 0, 0, 0); // 执行读EXCEL操作,读出的数据导入List，2:从第3行开始；0:从第A列开始；0:第0个sheet
			Long dataTimeToLong = Long.parseLong(DateUtilNew.getCurrentTimeLong().toString());
			PageData pds = new PageData();
			List<PageData> customerList = customerService.listAll(pds);
			Map<String, PageData> customerMap = new HashMap<String, PageData>(customerList.size());
			customerList.forEach(item -> customerMap.put(item.getString("mobile"), item));
			for (int i = 0; i < listPd.size(); i++) {
				String phone = listPd.get(i).getString("var0");
				if (phone.length() == 11) {
					String regex = "^(1)\\d{10}$";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(phone);
					boolean isMatch = m.matches();
					if (isMatch) {
						PageData pdCustomerMobile = new PageData();
						pdCustomerMobile.put("mobile", phone);
						PageData pdCustomer = new PageData();
						PageData pdCxmCustomer = userAccountManagerService.getUserByMobile(pdCustomerMobile);
						String userId1 = "";
						String userId2 = "";
						if (null != pdCxmCustomer) {
//							userId1 = pdCxmCustomer.getString("user_id");
//							PageData pdSotreCustomer = userAccountService.getOtherUserId(pdCxmCustomer);
//							PageData PageDataUserIds = new PageData();
//							pdCustomer.put("user_id", userId1);// 用户Id
//							if (null != pdSotreCustomer) {
//								userId2 = pdSotreCustomer.getString("user_id");
//							}
//							PageDataUserIds.put("user_id_1", userId1);
//							PageDataUserIds.put("user_id_2", userId2);

							//根据手机号查询订单的最早支付时间
							PageData pdMobile = new PageData();
							pdMobile.put("mobile",phone);
							PageData oldOrder = orderService.findByMobile(pdMobile);
							if (null != oldOrder) {
								pdCustomer.put("pay_state", 1);
								pdCustomer.put("first_pay_time", oldOrder.get("pay_time")); // 第一次支付的时间
							} else {
								pdCustomer.put("pay_state", 0);
								pdCustomer.put("first_pay_time", ""); // 第一次支付的时间
							}
						} else {
							pdCustomer = new PageData();
							pdCustomer.put("pay_state", 0); // 购彩状态
							pdCustomer.put("first_pay_time", ""); // 第一次支付的时间
						}
						pdCustomer.put("user_name", "");
						pdCustomer.put("mobile", listPd.get(i).getString("var0"));// 电话
						pdCustomer.put("user_state", 2); // 用户状态(新老用户)
						pdCustomer.put("user_source", 1); // 用户来源
						pdCustomer.put("first_add_time", dataTimeToLong); // 第一次分配的时间
						pdCustomer.put("last_add_time", dataTimeToLong); // 最后一次分配的时间
						pdCustomer.put("distribute_state", 1);// 是否置回
						pdCustomer.put("first_add_seller_name", userName); // 第一次分配的销售人员
						pdCustomer.put("first_add_seller_id", userId); // 第一次分配的销售人员Id
						pdCustomer.put("last_add_seller_name", userName); // 最后一次分配的销售人员Id
						pdCustomer.put("last_add_seller_id", userId); // 最后一次分配的销售人员Id
						PageData pdOldCustomer = customerMap.get(phone);
						if (null == pdOldCustomer) {
							customerService.save(pdCustomer);
						}
					}
				}
			}
		}
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/updateSaler")
	public ModelAndView updateSaler(@RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "user_name", required = false) String userName, @RequestParam(value = "ids", required = false) String ids) throws Exception {
		ModelAndView mv = this.getModelAndView();
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
//			return null;
//		}
		List<String> idList = Arrays.asList(ids.split(","));
		Long dataTimeToLong = Long.parseLong(DateUtilNew.getCurrentTimeLong().toString());
		for (int i = 0; i < idList.size(); i++) {
			PageData pd = new PageData();
			pd.put("id", idList.get(i));
			pd.put("last_add_time", dataTimeToLong); // 最后一次分配的时间
			pd.put("last_add_seller_name", userName); // 最后一次分配的销售人员Id
			pd.put("last_add_seller_id", userId); // 最后一次分配的销售人员Id
			pd.put("user_source", 7); // 改为维护资源
			customerService.updateById(pd);
		}

		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Customer");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
//			return null;
//		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			customerService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) { // 行循环开始

				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); // 行
				int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) { // 列循环开始

					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {

						switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((long) cell.getNumericCellValue());
							break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue =
							// String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}

					varpd.put("var" + j, cellValue);

				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return varList;
	}

}
