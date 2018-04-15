package com.fh.controller.erp.erporder;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.OrderShippingStatusEnum;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.JsonUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.express.enums.ShippingStatusEnum;
import com.fh.service.erp.erporder.ErpOrderManager;

/** 
 * 说明：订单状态确认
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 */
@Controller
@RequestMapping(value="/erporder")
public class ErpOrderController extends BaseController {
	
	String menuUrl = "erporder/list.do"; //菜单地址(权限用)
	@Resource(name="erporderService")
	private ErpOrderManager erporderService;
	
	
	/**
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/updateErpOrder")
	public void updateErpOrder(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ErpOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		/*List<PageData> list=erporderService.listDeliveryByStoreId(pd);
		String str_delivery_org_id="";
		if(list.size()>0) {
			str_delivery_org_id=list.get(0).getString("delivery_org_id");
		}
		boolean checkexists =false;
	    if(!"".equals(str_delivery_org_id)) {
	    	String order_sn = pd.getString("order_sn");				//关键词检索条件
	    	Integer delivery_org_id=Integer.valueOf(str_delivery_org_id);
			erporderService.updateErpOrder(order_sn, OrderShippingStatusEnum.TYPE3_TRANSPORT.getCode(),delivery_org_id);
			checkexists=true;
	    }*/
		boolean checkexists =false;
	    try {
			String order_sn = pd.getString("order_sn");				//关键词检索条件
			erporderService.updateErpOrder(order_sn, OrderShippingStatusEnum.TYPE3_TRANSPORT.getCode());
			checkexists=true;
		} catch (Exception e) {
			e.printStackTrace();
			checkexists =false;
		}
	    Map<String, Object> map = new HashMap<String, Object>();
		if (checkexists) {
			map.put("msg", "success");
		} else {
			map.put("msg", "failse");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
		out.close();
	}
	@RequestMapping(value="/updateErpOrderDeliveryOrg")
	public void updateErpOrderDeliveryOrg(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ErpOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		/*List<PageData> list=erporderService.listDeliveryByStoreId(pd);
		String str_delivery_org_id="";
		if(list.size()>0) {
			str_delivery_org_id=list.get(0).getString("delivery_org_id");
		}
		boolean checkexists =false;
	    if(!"".equals(str_delivery_org_id)) {
	    	String order_sn = pd.getString("order_sn");				//关键词检索条件
	    	Integer delivery_org_id=Integer.valueOf(str_delivery_org_id);
			erporderService.updateErpOrder(order_sn, OrderShippingStatusEnum.TYPE3_TRANSPORT.getCode(),delivery_org_id);
			checkexists=true;
	    }*/
		boolean checkexists =false;
		try {
			String order_sn = pd.getString("order_sn");				//关键词检索条件
			String delivery_org_id = pd.getString("delivery_org_id");				//关键词检索条件
			if(StringUtils.isNotBlank(order_sn) && StringUtils.isNotBlank(delivery_org_id)) {
				erporderService.updateErpOrderDeliveryOrg(order_sn, delivery_org_id);
				checkexists=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkexists =false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkexists) {
			map.put("msg", "success");
		} else {
			map.put("msg", "failse");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ErpOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		erporderService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ErpOrder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String order_sn = pd.getString("order_sn");				//关键词检索条件
		if(null != order_sn && !"".equals(order_sn)){
			pd.put("order_sn", order_sn.trim());
		}
		String shipping_status=pd.getString("shipping_status");	
		if (null != shipping_status && !"".equals(shipping_status)) {
			pd.put("shipping_status", shipping_status.trim());
		} else {
			pd.put("shipping_status", "-1");
		}
		
		String store_id=pd.getString("store_id");	
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		} else {
			pd.put("store_id", "-1");
		}
		
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			Date dt = DateUtil.fomatDate(lastStart);
			pd.put("lastStartTime", dt.getTime() / 1000);
		} else {
			pd.put("lastStartTime", "");
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			Date dtEnd = DateUtil.fomatLongDate(lastEnd + " 23:59:59");
			pd.put("lastEndTime", dtEnd.getTime() / 1000);
		} else {
			pd.put("lastEndTime", "");
		}

		page.setPd(pd);
		List<PageData>	varList = erporderService.list(page);	//列出ErpOrder列表
		for(PageData pageData:varList) {
			String order_id = pageData.getString("order_id");
		
			String order_goods_name=getOrderName(order_id);
			pageData.put("order_goods_name", order_goods_name);
	
			String  add_time=pageData.getString("add_time");
			
			String  add_time_show=getTime(add_time);
			pageData.put("add_time_show", add_time_show);
			String  temp_shipping_status=pageData.getString("shipping_status");
			String  shipping_status_show=getShippingStatus(temp_shipping_status);;
			pageData.put("shipping_status_show", shipping_status_show);
		
		}
		/*<td class='center'>${var.order_sn}</td>
		<td class='center'>${var.order_goods_name}</td>
		
		<td class='center'>${var.consignee}</td>
		<td class='center'>${var.tel}</td>
		<td class='center'>${var.address}</td>
		<td class='center'>${var.add_time_show}</td>
		<td class='center'>${var.shipping_status_show}</td>
		*/
		
//		List<PageData>	varListShop = erporderService.listAllShop();	
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_VIRTUAL};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		
		mv.setViewName("erp/erporder/erporder_list");
		mv.addObject("varList", varList);
		mv.addObject("storeList", list);
//		mv.addObject("varListShop", varListShop);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	private String getOrderName(String order_id) throws Exception {
		PageData pd = new PageData();
		pd.put("order_id", order_id);
		List<PageData>	varList = erporderService.listAllOrderGoods(pd);	//列出ErpOrder列表
		String name="";
		for(PageData pageData:varList) {
			name=name+pageData.getString("goods_name")+",";
		}
		if(name.length()>0) {
			name=name.substring(0, name.length()-1);
		}
		return name;
	}
	private String getTime(String  add_time) {
		Long time=0L;
		try {
			time=Long.valueOf(add_time);
			time=time*1000;
			Date dt=new Date(time);
			String date=DateUtil.dateToLongString(dt);
			return date;
		}
		catch(Exception ee) {
			
		}
		return "";
	}
	private String getShippingStatus(String shipping_status) {

		return OrderShippingStatusEnum.getName(shipping_status);
		
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
		mv.setViewName("erp/erporder/erporder_edit");
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
		pd = erporderService.findById(pd);	//根据ID读取
		mv.setViewName("erp/erporder/erporder_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ErpOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			erporderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ErpOrder到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = erporderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("pgt_order_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("order_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("order_sn"));	    //3
			vpd.put("var4", varOList.get(i).get("user_id").toString());	//4
			vpd.put("var5", varOList.get(i).get("order_status").toString());	//5
			vpd.put("var6", varOList.get(i).get("shop_id").toString());	//6
			vpd.put("var7", varOList.get(i).get("site_id").toString());	//7
			vpd.put("var8", varOList.get(i).get("store_id").toString());	//8
			vpd.put("var9", varOList.get(i).getString("consignee"));	    //9
			vpd.put("var10", varOList.get(i).getString("tel"));	    //10
			vpd.put("var11", varOList.get(i).getString("address"));	    //11
			vpd.put("var12", varOList.get(i).get("add_time").toString());	//12
			vpd.put("var13", varOList.get(i).get("shipping_status").toString());	//13
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
