package com.fh.controller.erp.inboundnotice;

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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.inbounddetail.InboundDetailManager;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.BarcodeUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Tools;
import com.fh.util.express.enums.InboundType;

/** 
 * 说明：到货通知单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/inboundnotice")
public class InboundNoticeController extends BaseController {
	
	String menuUrl = "inboundnotice/list.do"; //菜单地址(权限用)
	@Resource(name="inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;
	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;
	@Resource(name = "saveInboundService")
	private SaveInboundManager saveInboundService;
	@Resource(name="inbounddetailService")
	private InboundDetailManager inbounddetailService;
	@Resource(name="inboundService")
	private InboundManager inboundService;
	
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundcheckdetailService;
	
	/**更新状态
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/arrived")
	public Map<String, String> arrived() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		logBefore(logger, Jurisdiction.getUsername()+"更新InboundNotice状态");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return map;} //校验权限
		
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String inbound_notice_code = pd.getString("inbound_notice_code");
		try {
			saveInboundService.saveArrivedHandler(pd, inbound_notice_code);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "fail");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}
	
	/**更新状态
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Map<String, String> update() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "fail");
		logBefore(logger, Jurisdiction.getUsername()+"更新InboundNotice状态");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				map.put("info", "没有权限!");
				return map;
			} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		map = saveInboundService.saveFinishInboundHandler(map, pd);
		return map;
	}

	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("inbound_notice_code", "");	//到货通知单编码
		pd.put("bill_type", "0");	//单据类型
		pd.put("purchase_code", "");	//业务单据编码
		pd.put("purchase_detail_id", "0");	//业务单据明细ID
		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
		pd.put("store_sn", "");	//到货仓库编码
		pd.put("store_name", "");	//到货仓库名称
		pd.put("status", "0");	//状态
		inboundnoticeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 通过采购单创建到货单，包含详情
	 */
	@RequestMapping(value="/saveDetail")
	public void saveDetail(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "saveDetail")){out.println("无权限");} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticeService.saveDetail(pd,getSessionUser());
	/*	String jsonStr = pd.getString("inboundJson");
		JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("inbound_notice_code", jsonOb.getString("inbound_notice_code"));	//到货通知单编码
		 String business_type = jsonOb.getString("business_type");
		 String bill_type = "";//单据类型 1：采购，2：生产,与采购、生产单的定义不一致，需转换
		if( business_type.equals("0")) {
			bill_type = "1";
		}else if(business_type.equals("1")) {
			bill_type = "2";
		}
		pd.put("bill_type", bill_type);	
		pd.put("purchase_code", jsonOb.getString("purchase_code"));	//业务单据编码
//		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
		pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
		pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
		pd.put("pre_arrival_time", jsonOb.getString("pre_arrival_time"));	//到货仓库名称
		pd.put("status", "0");	//状态
		pd.put("createby",getSessionUser().getUSER_ID());
		pd.put("create_time", new Timestamp(System.currentTimeMillis()));
		 
		inboundnoticeService.save(pd);
		//插入到货单详情
		JSONArray details = jsonOb.getJSONArray("details");
		for(int i=0;i<details.size();i++) {
			JSONObject detail = details.getJSONObject(i);
			 pd = new PageData();
			 pd.put("inbound_code", jsonOb.getString("inbound_notice_code"));
			 pd.put("purchase_code", jsonOb.getString("purchase_code"));
			 pd.put("sku_id", detail.getString("sku_id"));
			 pd.put("sku_name", detail.getString("sku_name"));
			 pd.put("spec", detail.getString("spec"));
			 pd.put("sku_encode", detail.getString("sku_encode"));
			 pd.put("pre_arrival_quantity", detail.getString("pre_arrival_quantity"));
			 pd.put("good_quantity", "0");
			 pd.put("bad_quantity", "0");
			 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
			 pd.put("createby",getSessionUser().getUSER_ID());
			 pd.put("create_time", new Timestamp(System.currentTimeMillis()));
			 inboundnoticedetailService.save(pd);
			//修改采购单已发货数量
			 pd = new PageData();
			 pd.put("total_sended_quantity", detail.getString("pre_arrival_quantity"));
			 //减少可发货
			 pd.put("total_send_quantity", (0-Integer.parseInt(detail.getString("pre_arrival_quantity"))));
			 pd.put("detail_id", detail.getString("purchase_detail_id"));
			 purchasedetailService.updateQuantityForAdd(pd);
		}
		*/
		
		out.print("success");
		out.close();
	}
	/**
	 * 通过其他入库单创建到货单，包含详情
	 */
	@RequestMapping(value="/saveDetailForOther")
	public void saveDetailForOther(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增saveDetailForOther");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "saveDetailForOther")){out.println("无权限");} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String jsonStr = pd.getString("inboundJson");
		JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("inbound_notice_code", jsonOb.getString("inbound_notice_code"));	//到货通知单编码
		 String business_type = jsonOb.getString("business_type");
		 String bill_type = "7";//单据类型 其他入库
		
		pd.put("bill_type", bill_type);	
		pd.put("purchase_code", jsonOb.getString("purchase_code"));	//业务单据编码
//		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
		pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
		pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
		pd.put("pre_arrival_time", jsonOb.getString("pre_arrival_time"));	//到货仓库名称
		pd.put("status", "0");	//状态
		pd.put("createby",getSessionUser().getUSER_ID());
		pd.put("create_time", new Timestamp(System.currentTimeMillis()));
		 
		inboundnoticeService.save(pd);
		//插入到货单详情
		JSONArray details = jsonOb.getJSONArray("details");
		for(int i=0;i<details.size();i++) {
			JSONObject detail = details.getJSONObject(i);
			 pd = new PageData();
			 pd.put("inbound_code", jsonOb.getString("inbound_notice_code"));
			 pd.put("purchase_code", jsonOb.getString("purchase_code"));
			 pd.put("sku_id", detail.getString("sku_id"));
			 pd.put("sku_name", detail.getString("sku_name"));
			 pd.put("spec", detail.getString("spec"));
			 pd.put("sku_encode", detail.getString("sku_encode"));
			 pd.put("pre_arrival_quantity", detail.getString("pre_arrival_quantity"));
			 pd.put("good_quantity", "0");
			 pd.put("bad_quantity", "0");
			 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
			 pd.put("createby",getSessionUser().getNAME());
			 pd.put("create_time", new Timestamp(System.currentTimeMillis()));
			 inboundnoticedetailService.save(pd);
			//修改采购单已发货数量	TODO
//			 pd = new PageData();
//			 pd.put("total_sended_quantity", detail.getString("pre_arrival_quantity"));
//			 pd.put("detail_id", detail.getString("purchase_detail_id"));
//			 purchasedetailService.updateQuantityForAdd(pd);
		}
		
		out.print("success");
		out.close();
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){out.println("无权限");} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticeService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundNotice");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
			
		//业务编码
		String sh_purchase_code = pd.getString("sh_purchase_code");
		if (null != sh_purchase_code && !"".equals(sh_purchase_code)) {
			pd.put("sh_purchase_code", sh_purchase_code.trim());
		}
		//编码
		String sh_direct_sales_code = pd.getString("sh_direct_sales_code");
		if (null != sh_direct_sales_code && !"".equals(sh_direct_sales_code)) {
			pd.put("sh_direct_sales_code", sh_direct_sales_code.trim());
		}
		// 仓库名称或编码
		String store_sn_name = pd.getString("store_sn_name");
		if (null != store_sn_name && !"".equals(store_sn_name)) {
			pd.put("store_sn_name", store_sn_name.trim());
		}
		// 类型
		String sh_type = pd.getString("sh_type");
		if (null != sh_type && !"".equals(sh_type)) {
			pd.put("sh_type", sh_type.trim());
		}
		// 状态
		String sh_status = pd.getString("sh_status");
		if (null != sh_status && !"".equals(sh_status)) {
			pd.put("sh_status", sh_status.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_VIRTUAL};
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
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = inboundnoticeService.list(page);	//列出InboundNotice列表
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/inboundnotice/inboundnotice_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listForCheck")
	public ModelAndView listForCheck(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundNotice");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
			
		//业务编码
		String sh_purchase_code = pd.getString("sh_purchase_code");
		if (null != sh_purchase_code && !"".equals(sh_purchase_code)) {
			pd.put("sh_purchase_code", sh_purchase_code.trim());
		}
		//编码
		String sh_direct_sales_code = pd.getString("sh_direct_sales_code");
		if (null != sh_direct_sales_code && !"".equals(sh_direct_sales_code)) {
			pd.put("sh_direct_sales_code", sh_direct_sales_code.trim());
		}
		// 仓库名称或编码
		String store_sn_name = pd.getString("store_sn_name");
		if (null != store_sn_name && !"".equals(store_sn_name)) {
			pd.put("store_sn_name", store_sn_name.trim());
		}
		// 类型
		String sh_type = pd.getString("sh_type");
		if (null != sh_type && !"".equals(sh_type)) {
			pd.put("sh_type", sh_type.trim());
		}
		if ("99".equals(sh_type)) {
			pd.remove("sh_type");
		}
		// 状态
		String sh_status = pd.getString("sh_status");
		if (null != sh_status && !"".equals(sh_status)) {
			pd.put("sh_status", sh_status.trim());
		}
		if ("99".equals(sh_status)) {
			pd.remove("sh_status");
		}
		String[] types = {StoreTypeConstants.STORE_GOOD};
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
    		List<PageData>	varList = inboundnoticeService.listForCheck(page);	//列出InboundNotice列表
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/inboundnotice/inboundnotice_list_for_check");
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
		mv.setViewName("erp/inboundnotice/inboundnotice_edit");
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
		pd = inboundnoticeService.findById(pd);	//根据ID读取
		mv.setViewName("erp/inboundnotice/inboundnotice_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	/**
	 * 打印入库单
	 * @return 视图
	 * @throws Exception
	 */
	@RequestMapping(value="printInboundNotices")
	public ModelAndView printInBoundNotices()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询入库单信息
		String noticeIds = pd.getString("sn");
		List<PageData> listPd = new ArrayList<PageData>();
		if(noticeIds!=null&&!noticeIds.equals("")) {
			String[] noticeIdArr = noticeIds.split(",");
			List<PageData> pagelist = inboundnoticeService.listByNoticesIds(noticeIdArr);
			RedisUtil.getUserInfoById(pagelist, "createby", "NAME", "name");
			if(pagelist!=null) {
				for(PageData pdItem:pagelist) {
					pdItem.put("inbound_type", InboundType.getByCode(Tools.ifNull(pdItem.getString("inbound_type"))));
					List<PageData> listDetil = inbounddetailService.findAllByInboundDetilCode(pdItem);
					pdItem.put("details", listDetil);
				}
			}
			listPd=pagelist;
		}
		BarcodeUtil.createBarcode(listPd, "purchase_code", "barcode");
		mv.setViewName("erp/inboundnotice/printInboundNotices");
		mv.addObject("list",listPd);
		return mv;
	}
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundnoticeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundNotice到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("到货通知单编码");	//1
		titles.add("单据类型");	//2
		titles.add("业务单据编码");	//3
		titles.add("业务单据明细ID");	//4
		titles.add("预计到货日期");	//5
		titles.add("实际到货日期");	//6
		titles.add("到货仓库编码");	//7
		titles.add("到货仓库名称");	//8
		titles.add("状态");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundnoticeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("inbound_notice_code"));	    //1
			vpd.put("var2", varOList.get(i).get("bill_type").toString());	//2
			vpd.put("var3", varOList.get(i).getString("purchase_code"));	    //3
			vpd.put("var4", varOList.get(i).get("purchase_detail_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("pre_arrival_time"));	    //5
			vpd.put("var6", varOList.get(i).getString("arrival_time"));	    //6
			vpd.put("var7", varOList.get(i).getString("store_sn"));	    //7
			vpd.put("var8", varOList.get(i).getString("store_name"));	    //8
			vpd.put("var9", varOList.get(i).get("status").toString());	//9
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
