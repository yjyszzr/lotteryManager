package com.fh.controller.erp.directsales;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.directsales.DirectSalesManager;
import com.fh.service.erp.directsalescustomer.DirectSalesCustomerManager;
import com.fh.service.erp.directsalesdetail.DirectSalesDetailManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：内部直销管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/directsales")
public class DirectSalesController extends BaseController {
	
	String menuUrl = "directsales/list.do"; //菜单地址(权限用)
	@Resource(name="directsalesService")
	private DirectSalesManager directsalesService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService  getSerialConfigUtilService;
	
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;

	@Resource(name="directsalesdetailService")
	private DirectSalesDetailManager directsalesdetailService;
	
	@Resource(name = "saveInboundService")
	private SaveInboundManager saveInboundService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="directsalescustomerService")
	private DirectSalesCustomerManager directsalescustomerService;
	

	
	/**完成
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Map<String, String> update() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		logBefore(logger, Jurisdiction.getUsername()+"审批InboundNotice状态");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return map;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		String status = pd.getString("status");
		String direct_sales_code = pd.getString("direct_sales_code");
		
		if("1".equals(status)) {
			PageData queryCustomer = new PageData();
			queryCustomer.put("direct_sales_code", direct_sales_code);
			List<PageData>	varCustomers = directsalescustomerService.listByCode(queryCustomer);
			if(varCustomers==null ||varCustomers.size()==0) {
				map.put("msg", "fail");
				map.put("info", "未添加客户");
				return map;
			}
			PageData queryDetail = new PageData();
			queryDetail.put("direct_sales_code", direct_sales_code);
			List<PageData>	varDetails = directsalesdetailService.listByCode(queryDetail);
			if(varDetails==null ||varDetails.size()==0) {
				map.put("msg", "fail");
				map.put("info", "未添加明细");
				return map;
			}
			pd.put("commitby", getSessionUser().getUSER_ID());
			pd.put("commit_time", new Date());
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			directsalesService.update(pd);
		}
		if("2".equals(status)) {
			PageData queryDetail = new PageData();
			queryDetail.put("direct_sales_code", direct_sales_code);
			List<PageData>	varDetails = directsalesdetailService.listByCode(queryDetail);
			Double amount = 0.0;
			for (PageData pageData : varDetails) {
				Double temp = Double.valueOf(pageData.getString("amount"));
				amount += temp;
			}
			pd.put("direct_sales_code", pd.getString("direct_sales_code"));
			pd.put("priceby", user.getUSER_ID());
			pd.put("price_time", new Date());
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			pd.put("amount", amount);
			directsalesService.updatePrice(pd);
			return map;
		}
		if("3".equals(status)) {
			PageData queryCustomer = new PageData();
			queryCustomer.put("direct_sales_code", direct_sales_code);
			List<PageData>	varCustomers = directsalescustomerService.listByCode(queryCustomer);
			Double amount1 = 0.0;
			for (PageData pageData : varCustomers) {
				Double temp = Double.valueOf(pageData.getString("amount"));
				amount1 += temp;
			}
			PageData queryDetail = new PageData();
			queryDetail.put("direct_sales_code", direct_sales_code);
			List<PageData>	varDetails = directsalesdetailService.listByCode(queryDetail);
			Double amount = 0.0;
			for (PageData pageData : varDetails) {
				Double temp = Double.valueOf(pageData.getString("amount"));
				amount += temp;
			}
			if (amount.doubleValue() != amount1.doubleValue()) {
				map.put("msg", "fail");
				map.put("info", "分摊金额"+amount1+"与总额"+amount+"不相等");
				return map;
			}
			
			pd.put("direct_sales_code", pd.getString("direct_sales_code"));
			pd.put("shareby", user.getUSER_ID());
			pd.put("share_time", new Date());
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			//
			directsalesService.updateShare(pd);
			return map;
		}
		if("4".equals(status)) {
			pd.put("direct_sales_code", pd.getString("direct_sales_code"));
			pd.put("auditby", user.getUSER_ID());
			pd.put("audit_time", new Date());
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			directsalesService.updateAudit(pd);
			saveInboundService.saveDirectSales(direct_sales_code, user);
		}
		if("8".equals(status)) {
			pd.put("direct_sales_code", pd.getString("direct_sales_code"));
			pd.put("auditby", user.getUSER_ID());
			pd.put("audit_time", new Date());
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			directsalesService.updateAudit(pd);
		}
		return map;

	}
	
	private Map<String, Object> getUserNam() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData>	varList = userService.listAllUsername(new PageData());
		for(PageData pd : varList) {
			map.put(pd.getString("USER_ID"), pd.get("NAME"));
		}
		return map;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DirectSales");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("direct_sales_id", this.get32UUID());	//主键status
		
		String direct_sales_code = getSerialConfigUtilService.getSerialCode(FormType.DIRECT_SALES_CODE);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("direct_sales_code", direct_sales_code);
		pd.put("status", "0");
		pd.put("auditby", null);
		pd.put("audit_time", null);
		pd.put("createby", user.getUSER_ID());
		pd.put("create_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		
		directsalesService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DirectSales");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		directsalesService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DirectSales");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		directsalesService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DirectSales");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//编码
		String sh_direct_sales_code = pd.getString("sh_direct_sales_code");				
		if(null != sh_direct_sales_code && !"".equals(sh_direct_sales_code)){
			pd.put("sh_direct_sales_code", sh_direct_sales_code.trim());
		}
		//仓库名称或编码
		String store_sn_name = pd.getString("store_sn_name");				
		if(null != store_sn_name && !"".equals(store_sn_name)){
			pd.put("store_sn_name", store_sn_name.trim());
		}
		//类型
		String sh_type = pd.getString("sh_type");		
		if(null != sh_type && !"".equals(sh_type)){
			pd.put("sh_type", sh_type.trim());
		}
		//状态
		String sh_status = pd.getString("sh_status");		
		if(null != sh_status && !"".equals(sh_status)){
			pd.put("sh_status", sh_status.trim());
		}
		String[] types = {StoreTypeConstants.STORE_LOSS};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
//            	pd.put("store_sn", list.get(0).getString("store_sn"));
            	pd.put("store_sn",pd.get(""));
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	
        	page.setPd(pd);
    		List<PageData>	varList = directsalesService.list(page);	//列出DirectSales列表
    		if(CollectionUtils.isNotEmpty(varList)) {
    			RedisUtil.getUserInfoById(varList, "priceby", "NAME", "priceby");
    			RedisUtil.getUserInfoById(varList, "shareby", "NAME", "shareby");
    			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
    			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    		}
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/directsales/directsales_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/auditlist")
	public ModelAndView auditlist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DirectSales");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//编码
		String sh_direct_sales_code = pd.getString("sh_direct_sales_code");				
		if(null != sh_direct_sales_code && !"".equals(sh_direct_sales_code)){
			pd.put("sh_direct_sales_code", sh_direct_sales_code.trim());
		}
		//仓库名称或编码
		String store_sn_name = pd.getString("store_sn_name");				
		if(null != store_sn_name && !"".equals(store_sn_name)){
			pd.put("store_sn_name", store_sn_name.trim());
		}
		//类型
		String sh_type = pd.getString("sh_type");		
		if(null != sh_type && !"".equals(sh_type)){
			pd.put("sh_type", sh_type.trim());
		}
		//状态
		String sh_status = pd.getString("sh_status");		
		if(null != sh_status && !"".equals(sh_status)){
			pd.put("sh_status", sh_status.trim());
		}
		String[] types = {StoreTypeConstants.STORE_LOSS};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
//            	pd.put("store_sn", list.get(0).getString("store_sn"));
            	pd.put("store_sn",pd.get(""));
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	
        	page.setPd(pd);
    		List<PageData>	varList = directsalesService.list(page);	//列出DirectSales列表
    		if(CollectionUtils.isNotEmpty(varList)) {
    			RedisUtil.getUserInfoById(varList, "priceby", "NAME", "priceby");
    			RedisUtil.getUserInfoById(varList, "shareby", "NAME", "shareby");
    			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
    			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    		}
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/directsales/auditdirectsales_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("erp/directsales/directsales_edit");
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
		pd = directsalesService.findById(pd);	//根据ID读取
		mv.setViewName("erp/directsales/directsales_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DirectSales");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			directsalesService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DirectSales到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("内部直销编号");	//1
		titles.add("类型");	//2
		titles.add("状态");	//3
		titles.add("仓库编号");	//4
		titles.add("仓库名称");	//5
		titles.add("申请说明");	//6
		titles.add("审批人");	//7
		titles.add("审批时间");	//8
		titles.add("创建人");	//9
		titles.add("创建时间");	//10
		titles.add("更新人");	//11
		titles.add("更新时间");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = directsalesService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("direct_sales_code"));	    //1
			vpd.put("var2", varOList.get(i).get("type").toString());	//2
			vpd.put("var3", varOList.get(i).get("status").toString());	//3
			vpd.put("var4", varOList.get(i).getString("store_sn"));	    //4
			vpd.put("var5", varOList.get(i).getString("store_name"));	    //5
			vpd.put("var6", varOList.get(i).getString("remark"));	    //6
			vpd.put("var7", varOList.get(i).getString("auditby"));	    //7
			vpd.put("var8", varOList.get(i).getString("audit_time"));	    //8
			vpd.put("var9", varOList.get(i).getString("createby"));	    //9
			vpd.put("var10", varOList.get(i).getString("create_time"));	    //10
			vpd.put("var11", varOList.get(i).getString("updateby"));	    //11
			vpd.put("var12", varOList.get(i).getString("update_time"));	    //12
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
