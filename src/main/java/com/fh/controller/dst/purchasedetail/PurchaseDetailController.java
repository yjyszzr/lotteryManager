package com.fh.controller.dst.purchasedetail;

import java.io.PrintWriter;
import java.sql.Timestamp;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.purchase.PurchaseManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/** 
 * 说明：采购单详情
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/purchasedetail")
public class PurchaseDetailController extends BaseController {
	
	String menuUrl = "purchasedetail/list.do"; //菜单地址(权限用)
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="purchaseService")
	private PurchaseManager purchaseService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		//默认数据
				pd.put("createby",user.getUSER_ID());
				Timestamp curTime = new Timestamp(System.currentTimeMillis());
				pd.put("create_time",curTime);
				pd.put("updateby",user.getUSER_ID());
				pd.put("update_time", curTime);
//		pd.put("detail_id", this.get32UUID());	//主键
		pd.put("detail_id", "0");	//ID
		purchasedetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasedetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasedetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv =this.listCommon(page);
		mv.setViewName("dst/purchasedetail/purchasedetail_list");
		//采购单
		PageData user  = purchaseService.findByPurchaseCode( this.getPageData());
		fillUserName(user, "commitby");
		fillUserName(user, "auditby");
		mv.addObject("pd",user);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/listForReport")
	public ModelAndView listForReport(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseDetail ForReport");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv =this.listCommon(page);
		mv.setViewName("dst/purchasedetail/purchasedetail_list_report");
		//采购单
		PageData user  = purchaseService.findByPurchaseCode( this.getPageData());
		fillUserName(user, "commitby");
		fillUserName(user, "auditby");
		mv.addObject("pd",user);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;

	}
	private ModelAndView listCommon(Page page) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				String purchase_code = pd.getString("purchase_code");
				if(StringUtil.isEmptyStr(purchase_code)) {
					pd.put("purchase_code", purchase_code);
				}
				page.setPd(pd);
				List<PageData>	varList = purchasedetailService.list(page);	//列出PurchaseDetail列表
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
				mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
				return mv;
	}
	 
		@RequestMapping(value="/listForInbound")
		public ModelAndView listForInbound(Page page) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseDetail for inbound");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			String purchase_code = pd.getString("purchase_code");
			if(StringUtil.isEmptyStr(purchase_code)) {
				pd.put("purchase_code", purchase_code);
			}
//			String code=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
//			pd.put("inbound_notice_code", code);
			String code=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_PRE_NOTICE_CODE);
			pd.put("inbound_pre_notice_code", code);
			page.setPd(pd);
			List<PageData>	varList = purchasedetailService.list(page);	//列出PurchaseDetail列表
			mv.setViewName("dst/purchasedetail/purchasedetail_list_forInbound");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		/**
		 * 领料
		 * @param page
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/listPurchaseReturn")
		public ModelAndView listPurchaseReturn(Page page) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"列表list forPurchaseReturn");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			String purchase_code = pd.getString("purchase_code");
			if(StringUtil.isEmptyStr(purchase_code)) {
				pd.put("purchase_code", purchase_code);
			}
			String code=getSerialConfigUtilService.getSerialCode(FormType.PURCHASE_RETURN_CODE);
			pd.put("purchase_return_code", code);
			page.setPd(pd);
			List<PageData>	varList = purchasedetailService.list(page);	//列出PurchaseDetail列表
			mv.setViewName("dst/purchasedetail/purchasedetail_list_forPurchaseReturn");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		
		@RequestMapping(value="/listForSend")
		public ModelAndView listForSend(Page page) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseDetail for send");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			String purchase_code = pd.getString("purchase_code");
			if(StringUtil.isEmptyStr(purchase_code)) {
				pd.put("purchase_code", purchase_code);
			}
			
			page.setPd(pd);
			List<PageData>	varList = purchasedetailService.list(page);	//列出PurchaseDetail列表
			mv.setViewName("dst/purchasedetail/purchasedetail_list_forSend");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		@RequestMapping(value="/updateSendQuantity")
		public void updateSendQuantity(PrintWriter out) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"updateSendQuantity");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "updateSendQuantity")){out.println("无权限");} //校验权限
			PageData pd = new PageData();
			pd = this.getPageData();
			String jsonStr = pd.getString("quantityJson");
			JSONObject jsonOb = JSONObject.parseObject(jsonStr);
			JSONArray array = jsonOb.getJSONArray("quantities");
			for(int i=0; i<array.size();i++) {
				JSONObject quanty = array.getJSONObject(i);
				 pd = new PageData();
				 pd.put("detail_id", quanty.getString("purchase_detail_id"));
				 pd.put("total_send_quantity", quanty.getString("total_send_quantity"));
				 purchasedetailService.updateQuantityForAdd(pd);
			}
			out.print("success");
			out.close();
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
		
		//默认值设置
		pd.put("total_send_quantity", "0");
		pd.put("total_sended_quantity", "0");
		pd.put("good_quantity", "0");
		pd.put("bad_quantity", "0");
		pd.put("status", "0");
		
		//默认值设置结束

		mv.setViewName("dst/purchasedetail/purchasedetail_edit");
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
		String  supplier_sn = pd.getString("supplier_sn");
		pd = purchasedetailService.findById(pd);	//根据ID读取
		pd.put("supplier_sn", supplier_sn);
		mv.setViewName("dst/purchasedetail/purchasedetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasedetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("采购单编码");	//2
		titles.add("sku_id");	//3
		titles.add("数量");	//4
		titles.add("单位");	//5
		titles.add("可发货数量");	//6
		titles.add("已发货数量");	//7
		titles.add("良品数量");	//8
		titles.add("不良品数量");	//9
		titles.add("单价");	//10
		titles.add("含税单价");	//11
		titles.add("税率");	//12
		titles.add("状态");	//13
		titles.add("预计到货时间");	//14
		titles.add("创建人");	//15
		titles.add("创建时间");	//16
		titles.add("更新人");	//17
		titles.add("更新时间");	//18
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasedetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("detail_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("purchase_code"));	    //2
			vpd.put("var3", varOList.get(i).get("sku_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("quantity").toString());	//4
			vpd.put("var5", varOList.get(i).getString("unit"));	    //5
			vpd.put("var6", varOList.get(i).get("total_send_quantity").toString());	//6
			vpd.put("var7", varOList.get(i).get("total_sended_quantity").toString());	//7
			vpd.put("var8", varOList.get(i).get("good_quantity").toString());	//8
			vpd.put("var9", varOList.get(i).get("bad_quantity").toString());	//9
			vpd.put("var10", varOList.get(i).getString("price"));	    //10
			vpd.put("var11", varOList.get(i).getString("tax_price"));	    //11
			vpd.put("var12", varOList.get(i).getString("tax_rate"));	    //12
			vpd.put("var13", varOList.get(i).get("status").toString());	//13
			vpd.put("var14", varOList.get(i).getString("pre_arrival_time"));	    //14
			vpd.put("var15", varOList.get(i).getString("createby"));	    //15
			vpd.put("var16", varOList.get(i).getString("create_time"));	    //16
			vpd.put("var17", varOList.get(i).getString("updateby"));	    //17
			vpd.put("var18", varOList.get(i).getString("update_time"));	    //18
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
