package com.fh.controller.dst.outboundnotice;

import java.io.File;
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
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.outbounddetail.impl.PgtOutboundDetailService;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.order.OrderManager;
import com.fh.util.AppUtil;
import com.fh.util.BarcodeUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.TwoDimensionCode;

/** 
 * 说明：发货单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/outboundnotice")
public class OutboundNoticeController extends BaseController {
	
	String menuUrl = "outboundnotice/list.do"; //菜单地址(权限用)
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	
	@Resource(name="orderService")
	private OrderManager orderManager;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OutboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("outbound_notice_id", this.get32UUID());	//主键
		pd.put("purchase_detail_id", "0");	//业务单据明细ID
		outboundnoticeService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除OutboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		outboundnoticeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OutboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		outboundnoticeService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表OutboundNotice");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		
		String type=pd.getString("name");	
		if(null != type && !"".equals(type))
		{
		    pd.put("bill_type", type);
		}
		
		String warename=pd.getString("warename");	
		String purchasecode=pd.getString("purchase_code");	
		String status=pd.getString("status");	
		if(null != warename && !"".equals(warename))
		{
			 pd.put("warename", warename);
		}
		
		if(null != purchasecode && !"".equals(purchasecode))
		{
			 pd.put("purchase_code", purchasecode);
		}
		
		if(null != status && !"".equals(status))
		{
			 pd.put("status", status);
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
    		List<PageData>	varList = outboundnoticeService.list(page);	//列出OutboundNotice列表
    		mv.addObject("varList", varList);
        }else {
    		mv.addObject("varList", "");
        }
		mv.setViewName("erp/outboundnotice/outboundnotice_list");
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
		mv.setViewName("erp/outboundnotice/outboundnotice_edit");
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
		pd = outboundnoticeService.findById(pd);	//根据ID读取
		mv.setViewName("erp/outboundnotice/outboundnotice_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除OutboundNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			outboundnoticeService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 通过其他出库单创建出货单，包含详情
	 */
	@RequestMapping(value="/saveDetailForOther")
	public void saveDetailForOther(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"saveDetailForOther");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "saveDetailForOther")){out.println("无权限");} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String jsonStr = pd.getString("outboundJson");
		JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("outbound_notice_code", jsonOb.getString("outbound_notice_code"));	//到货通知单编码
		 String business_type = jsonOb.getString("business_type");
		 String bill_type = "6";//单据类型 其他入库
		
		pd.put("bill_type", bill_type);	
		pd.put("purchase_code", jsonOb.getString("purchase_code"));	//业务单据编码
//		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
		pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
		pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
		pd.put("pre_send_time", jsonOb.getString("pre_send_time"));	
		pd.put("status", "0");	//状态
		pd.put("createby",getSessionUser().getUSER_ID());
		pd.put("create_time", new Timestamp(System.currentTimeMillis()));
		 
		outboundnoticeService.save(pd);
		//插入出货单详情
		JSONArray details = jsonOb.getJSONArray("details");
		for(int i=0;i<details.size();i++) {
			JSONObject detail = details.getJSONObject(i);
			 pd = new PageData();
			 pd.put("outbound_notice_code", jsonOb.getString("outbound_notice_code"));
			 pd.put("purchase_code", jsonOb.getString("purchase_code"));
			 pd.put("sku_id", detail.getString("sku_id"));
			 pd.put("sku_name", detail.getString("sku_name"));
			 pd.put("spec", detail.getString("spec"));
			 pd.put("sku_encode", detail.getString("sku_encode"));
			 pd.put("pre_send_quantity", detail.getString("pre_send_quantity"));
			 pd.put("send_quantity", 0);
			 pd.put("status", 0);
//			 pd.put("good_quantity", "0");
//			 pd.put("bad_quantity", "0");
			 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
			 pd.put("createby",getSessionUser().getNAME());
			 pd.put("create_time", new Timestamp(System.currentTimeMillis()));
			 outboundnoticedetailService.save(pd);
			//修改采购单已发货数量	TODO
//			 pd = new PageData();
//			 pd.put("total_sended_quantity", detail.getString("pre_arrival_quantity"));
//			 pd.put("detail_id", detail.getString("purchase_detail_id"));
//			 purchasedetailService.updateQuantityForAdd(pd);
		}
		
		out.print("success");
		out.close();
	}
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出OutboundNotice到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("发货单编码");	//1
		titles.add("仓库编号");	//2
		titles.add("仓库名称");	//3
		titles.add("单据类型");	//4
		titles.add("业务单据编码");	//5
		titles.add("业务单据明细ID");	//6
		titles.add("预计发货日期");	//7
		titles.add("实际发货日期");	//8
		titles.add("状态");	//9
		titles.add("创建人");	//10
		titles.add("创建时间");	//11
		titles.add("更新人");	//12
		titles.add("更新时间");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = outboundnoticeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("outbound_notice_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("store_sn"));	    //2
			vpd.put("var3", varOList.get(i).getString("store_name"));	    //3
			vpd.put("var4", varOList.get(i).get("bill_type").toString());	//4
			vpd.put("var5", varOList.get(i).getString("purchase_code"));	    //5
			vpd.put("var6", varOList.get(i).get("purchase_detail_id").toString());	//6
			vpd.put("var7", varOList.get(i).getString("pre_send_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("send_time"));	    //8
			vpd.put("var9", varOList.get(i).get("status").toString());	//9
			vpd.put("var10", varOList.get(i).getString("createby"));	    //10
			vpd.put("var11", varOList.get(i).getString("create_time"));	    //11
			vpd.put("var12", varOList.get(i).getString("updateby"));	    //12
			vpd.put("var13", varOList.get(i).getString("update_time"));	    //13
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
	@RequestMapping(value="/printsendform")
	public ModelAndView printbatchPage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询批次数量，生成二维码
		String orderSnStr=pd.getString("sn");
		List<PageData>  listPd=new ArrayList<PageData>();
		if(orderSnStr!=null&&!orderSnStr.equals(""))
		{
			File dir = new File(PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE);
			if (!dir.isDirectory()) 
			{
				dir.mkdir();
			}
		   String[] arr=orderSnStr.split(",");
		   List<PageData> pageList=orderManager.listByOrderSn(arr);
		   if(pageList!=null)
		   {
			   for(PageData pdItem:pageList)
			   {
				   String encoderImgId = this.get32UUID()+".png"; 
				   String encoderContent =pdItem.getString("order_sn");
				   pdItem.put("img", encoderImgId);
				   try {
						String filePath = PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + encoderImgId;  //存放路径
						TwoDimensionCode.encoderQRCode(encoderContent, filePath, "png");							//执行生成二维码
					} catch (Exception e) 
					{
						logBefore(logger,e.getMessage());
					}
			   }
		   }
		   listPd=pageList;
		}
		BarcodeUtil.createBarcode(listPd, "order_sn", "barcode");
		mv.setViewName("erp/outboundnotice/printsendform");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("list", listPd);
		return mv;
	}
	@RequestMapping(value="/printOutboundNotices")
	public ModelAndView printOutboundNotices()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询批次数量，生成二维码
		String noticeIds=pd.getString("sn");
		List<PageData>  listPd=new ArrayList<PageData>();
		if(noticeIds!=null&&!noticeIds.equals("")){
			String[] noticeIdArr=noticeIds.split(",");
			List<PageData> pageList=outboundnoticeService.listByNoticeIds(noticeIdArr);
			if(pageList!=null){
				for(PageData pdItem:pageList){
					PageData order = outboundnoticeService.orderConsigeen(pdItem);
					Object consignee = "未知";
					if(order != null) {
						consignee = order.get("consignee") == null?consignee:order.get("consignee");
					}
					pdItem.put("consignee", consignee);
					List<PageData> listAll = outboundnoticedetailService.findAllByOutBoundNoticeCode(pdItem);
					pdItem.put("details", listAll);
				}
				listPd=pageList;
				outboundnoticeService.updatePrintCount(noticeIdArr);
			}
		}
		BarcodeUtil.createBarcode(listPd, "outbound_notice_code", "barcode");
		mv.setViewName("erp/outboundnotice/printOutboundNotices");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("list", listPd);
		return mv;
	}
	
	
}
