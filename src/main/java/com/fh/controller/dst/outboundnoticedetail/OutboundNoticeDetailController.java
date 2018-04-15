package com.fh.controller.dst.outboundnoticedetail;

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
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：发货明细单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/outboundnoticedetail")
public class OutboundNoticeDetailController extends BaseController {
	
	String menuUrl = "outboundnoticedetail/list.do"; //菜单地址(权限用)
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OutboundNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("outbound_notice_detail_id", this.get32UUID());	//主键
		pd.put("purchase_detail_id", "0");	//业务单据明细Id
		pd.put("sku_id", "0");	//skuId
		outboundnoticedetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除OutboundNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		outboundnoticedetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OutboundNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		outboundnoticedetailService.updateSendQuantity(pd);
		//更新发货数量
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表OutboundNoticeDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = outboundnoticedetailService.findAllByOutBoundNoticeCode(pd);	//列出OutboundNoticeDetail列表
		mv.setViewName("erp/outboundnoticedetail/outboundnoticedetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/sendlist")
	public ModelAndView sendList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OutboundNoticeDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = outboundnoticeService.findById(pd);
		List<PageData>	varList = outboundnoticedetailService.findAllByOutBoundNoticeCode(pd);	//列出OutboundNoticeDetail列表
		mv.setViewName("erp/outboundnoticedetail/sendoutboundnoticedetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

		mv.addObject("outbound_notice_code", pd.get("outbound_notice_code"));
		mv.addObject("outbound_notice_id", pd.get("outbound_notice_id"));
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
		mv.setViewName("erp/outboundnoticedetail/outboundnoticedetail_edit");
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
		pd = outboundnoticedetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/outboundnoticedetail/outboundnoticedetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除OutboundNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			outboundnoticedetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出OutboundNoticeDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("发货通知编码");	//1
		titles.add("业务单据编码");	//2
		titles.add("业务单据明细Id");	//3
		titles.add("skuId");	//4
		titles.add("物料名称");	//5
		titles.add("预计发货数量");	//6
		titles.add("出库数量");	//7
		titles.add("状态");	//8
		titles.add("创建人");	//9
		titles.add("创建时间");	//10
		titles.add("更新人");	//11
		titles.add("更新时间");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = outboundnoticedetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("outbound_notice_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("purchase_code"));	    //2
			vpd.put("var3", varOList.get(i).get("purchase_detail_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("sku_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("sku_name"));	    //5
			vpd.put("var6", varOList.get(i).get("pre_send_quantity").toString());	//6
			vpd.put("var7", varOList.get(i).get("send_quantity").toString());	//7
			vpd.put("var8", varOList.get(i).get("status").toString());	//8
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
