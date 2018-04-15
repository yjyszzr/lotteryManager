package com.fh.controller.dst.inboundprenotice;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.fh.controller.dst.pgtinboundprenoticedetail.PgtInboundPreNoticeDetailController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.inboundprenotice.InboundPreNoticeManager;
import com.fh.service.dst.pgtinboundprenoticedetail.PgtInboundPreNoticeDetailManager;
import com.fh.service.dst.pgtinboundprenoticedetail.impl.PgtInboundPreNoticeDetailService;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.purchasegroup.PurchaseGroupManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：入库预通知
 * 创建人：FH Q313596790
 * 创建时间：2017-11-22
 */
@Controller
@RequestMapping(value="/inboundprenotice")
public class InboundPreNoticeController extends BaseController {
	
	String menuUrl = "inboundprenotice/list.do"; //菜单地址(权限用)
	@Resource(name="inboundprenoticeService")
	private InboundPreNoticeManager inboundprenoticeService;
	
	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	
	@Resource(name="inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;
	
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;
	
	@Resource(name="pgtinboundprenoticedetailService")
	private PgtInboundPreNoticeDetailManager pgtinboundprenoticedetailService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="purchasegroupService")
	private PurchaseGroupManager purchasegroupService;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundPreNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_notice_id_id", this.get32UUID());	//主键
		pd.put("inbound_notice_id", "0");	//备注1
		pd.put("bill_type", "0");	//单据类型
		pd.put("purchase_detail_id", "0");	//备注5
		pd.put("arrival_time", "");	//实际到货日期
		pd.put("updateby", "");	//备注13
		pd.put("update_time", "");	//备注14
		inboundprenoticeService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundPreNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundprenoticeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(PrintWriter pw) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundPreNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		 User user = getSessionUser();
		 pd.put("updateby",user.getUSER_ID());
			pd.put("update_time", curTime);
		//更新状态
		if("1".equals(pd.getString("isUpdateStaus"))) {
			//如果通过则生成通知单
			if("1".equals(pd.getString("status"))) {
			  PageData notice =	inboundprenoticeService.findById(pd) ;
			//  notice.remove("inbound_notice_id");
			  String inbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
			  notice.put("inbound_notice_code", inbound_notice_code);
			  notice.put("purchase_code", notice.getString("inbound_pre_notice_code"));
			  
			  notice.put("createby",user.getUSER_ID());
			  notice.put("updateby",user.getUSER_ID());
			  notice.put("create_time", curTime);
			  notice.put("update_time", curTime);
			//  inboundnoticeService.save(notice);
			  List<PageData> noticeDetails = pgtinboundprenoticedetailService.listAll(notice);
			  List<PageData> toUpPurchaseDtail = new LinkedList<>();
			  List<PageData> toAddNoticeDtail = new LinkedList<>();
			  for(PageData detailPd : noticeDetails) {
				//修改采购单已发货数量
				PageData	 newDetail = new PageData();
				newDetail.put("total_sended_quantity", detailPd.getString("pre_arrival_quantity"));
					 //减少可发货
				newDetail.put("total_send_quantity_sub", (Integer.parseInt(detailPd.getString("pre_arrival_quantity"))));
				newDetail.put("detail_id", detailPd.getString("purchase_detail_id"));
					//校验数量
					PageData pDtail = purchasedetailService.findById(newDetail);
					if(Integer.parseInt(pDtail.getString("total_send_quantity"))<Integer.parseInt(detailPd.getString("pre_arrival_quantity"))) {
						pw.println( URLEncoder.encode(pDtail.getString("sku_name")+"可发货数量不够"));
						pw.flush();
						return null;
					}
				  
				  detailPd.put("purchase_code", notice.getString("inbound_pre_notice_code"));
				  detailPd.put("purchase_detail_id", detailPd.getString("notice_detail_id"));
				  detailPd.remove("notice_detail_id");
				  detailPd.put("createby",user.getUSER_ID());
				  detailPd.put("updateby",user.getUSER_ID());
				  detailPd.put("create_time", curTime);
				  detailPd.put("inbound_code", inbound_notice_code);
				  detailPd.put("update_time", curTime);
				  toAddNoticeDtail.add(detailPd);
				//  inboundnoticedetailService.save(detailPd);	 
				  
				//修改采购单已发货数量和可发货
				  toUpPurchaseDtail.add(newDetail);
				//  purchasedetailService.updateQuantityForAdd(newDetail);

			  }
			  for(PageData purDetail :toUpPurchaseDtail ) {
				  purchasedetailService.updateQuantityForAdd(purDetail);
			  }
			  for(PageData noticeDtail : toAddNoticeDtail ) {
				  inboundnoticedetailService.save(noticeDtail);
			  }

			  pd.put("auditby",user.getUSER_ID());
			  pd.put("audit_time", curTime);

			  inboundprenoticeService.edit(pd);
			  inboundnoticeService.save(notice);
			  pw.print(1);
			  pw.flush();
			  return null;
			}
			
		}
		
		inboundprenoticeService.edit(pd);
	
		pw.print(1);
		pw.flush();
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return null;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundPreNotice");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = listCommon(page);
		mv.setViewName("dst/inboundprenotice/inboundprenotice_list");
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listForReport")
	public ModelAndView listForReport(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundPreNotice for Report");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = inboundprenoticeService.list(page);
		RedisUtil.getUserInfoById(varList, "createby", "NAME", "createby");
		RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());
		mv.setViewName("dst/inboundprenotice/inboundprenotice_list_report");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	
	private ModelAndView listCommon(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String business_type = pd.getString("business_type");
				String bill_type="";
		if( business_type.equals("0")) {
			bill_type = "1";
		}else if(business_type.equals("1")) {
			bill_type = "2";
		}
		pd.put("bill_type", bill_type);
		page.setPd(pd);
	//	List<PageData>	varList = inboundprenoticeService.list(page);	//列出InboundPreNotice列表
		//采购组
				fillPurcharseGPs(pd, mv);
				
		List<PageData>	varList = null;	//列出Purchase列表
		//采购组查询条件，每个用户只能查自己所属采购组的订单
		if(pd.getString("purchase_group_id")==null || pd.getString("purchase_group_id").trim().equals("")) {
			String userId = getSessionUser().getUSER_ID();
			//admin 可以查所有的,不是admin需加过滤条件
			if(	userId.equals("1")) {
				varList =  inboundprenoticeService.list(page);
			}else {//加过滤条件，如果不属于任何组，直接返回空
				List<PageData> gps = (List<PageData>)mv.getModel().get("gpList");
				if(gps.size()==0) {
					varList = new ArrayList<>();
				}else {
					String gpids = "(";
					for(PageData gp  : gps) {
						gpids+=gp.getString("purchase_group_id")+",";
					}
					gpids = gpids.substring(0, gpids.length()-1);
					gpids +=")";
					pd.put("purchase_group_ids", gpids);
					page.setPd(pd);
					varList =  inboundprenoticeService.list(page);
				}
			}
			//purchase_group_ids
		}else {
			varList = inboundprenoticeService.list(page);
		}
		
		RedisUtil.getUserInfoById(varList, "createby", "NAME", "createby");
		mv.addObject("varList", varList);
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
		mv.setViewName("dst/inboundprenotice/inboundprenotice_edit");
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
		pd = inboundprenoticeService.findById(pd);	//根据ID读取
		mv.setViewName("dst/inboundprenotice/inboundprenotice_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundPreNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundprenoticeService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
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
		inboundprenoticeService.saveDetail(pd,getSessionUser());
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
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundPreNotice到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("到货单编码");	//2
		titles.add("单据类型");	//3
		titles.add("业务单据编码");	//4
		titles.add("备注5");	//5
		titles.add("预计到货日期");	//6
		titles.add("实际到货日期");	//7
		titles.add("仓库编码");	//8
		titles.add("仓库名称");	//9
		titles.add("状态");	//10
		titles.add("创建人");	//11
		titles.add("创建时间");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundprenoticeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("inbound_notice_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_notice_code"));	    //2
			vpd.put("var3", varOList.get(i).get("bill_type").toString());	//3
			vpd.put("var4", varOList.get(i).getString("purchase_code"));	    //4
			vpd.put("var5", varOList.get(i).get("purchase_detail_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("pre_arrival_time"));	    //6
			vpd.put("var7", varOList.get(i).getString("arrival_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("store_sn"));	    //8
			vpd.put("var9", varOList.get(i).getString("store_name"));	    //9
			vpd.put("var10", varOList.get(i).get("status").toString());	//10
			vpd.put("var11", varOList.get(i).getString("createby"));	    //11
			vpd.put("var12", varOList.get(i).getString("create_time"));	    //12
			vpd.put("var13", varOList.get(i).getString("updateby"));	    //13
			vpd.put("var14", varOList.get(i).getString("update_time"));	    //14
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 向request中添加采购组信息
	 * @param pd
	 * @param mv
	 * @throws Exception
	 */
	private void fillPurcharseGPs(PageData pd,ModelAndView mv) throws Exception {
		PageData gpParas = new PageData();
		gpParas.put("type", pd.getString("business_type"));
		String userId = getSessionUser().getUSER_ID();
		//admin 可以查所有的
		if(userId.equals("1")) {
			mv.addObject("gpList",purchasegroupService.listAll(gpParas));
		}else {
			gpParas.put("user_id", userId);
			mv.addObject("gpList",purchasegroupService.listAllGPByUser(gpParas));
		}
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
