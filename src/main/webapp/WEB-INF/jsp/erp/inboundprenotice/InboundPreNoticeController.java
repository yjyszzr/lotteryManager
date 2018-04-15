//package com.fh.controller.dst.inboundprenotice;
//
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import com.fh.controller.base.BaseController;
//import com.fh.entity.Page;
//import com.fh.util.AppUtil;
//import com.fh.util.ObjectExcelView;
//import com.fh.util.PageData;
//import com.fh.util.Jurisdiction;
//import com.fh.util.Tools;
//import com.fh.service.dst.inboundprenotice.InboundPreNoticeManager;
//
///** 
// * 说明：入库预通知
// * 创建人：FH Q313596790
// * 创建时间：2017-11-22
// */
//@Controller
//@RequestMapping(value="/inboundprenotice")
//public class InboundPreNoticeController extends BaseController {
//	
//	String menuUrl = "inboundprenotice/list.do"; //菜单地址(权限用)
//	@Resource(name="inboundprenoticeService")
//	private InboundPreNoticeManager inboundprenoticeService;
//	
//	/**保存
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"新增InboundPreNotice");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
////		pd.put("inbound_notice_id_id", this.get32UUID());	//主键
//		pd.put("inbound_notice_id", "0");	//备注1
//		pd.put("bill_type", "0");	//单据类型
//		pd.put("purchase_detail_id", "0");	//备注5
//		pd.put("arrival_time", "");	//实际到货日期
//		pd.put("updateby", "");	//备注13
//		pd.put("update_time", "");	//备注14
//		inboundprenoticeService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**删除
//	 * @param out
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/delete")
//	public void delete(PrintWriter out) throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"删除InboundPreNotice");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		inboundprenoticeService.delete(pd);
//		out.write("success");
//		out.close();
//	}
//	
//	/**修改
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/edit")
//	public ModelAndView edit() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"修改InboundPreNotice");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		inboundprenoticeService.edit(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**列表
//	 * @param page
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page) throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"列表InboundPreNotice");
//		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		String keywords = pd.getString("keywords");				//关键词检索条件
//		if(null != keywords && !"".equals(keywords)){
//			pd.put("keywords", keywords.trim());
//		}
//		page.setPd(pd);
//		List<PageData>	varList = inboundprenoticeService.list(page);	//列出InboundPreNotice列表
//		mv.setViewName("dst/inboundprenotice/inboundprenotice_list");
//		mv.addObject("varList", varList);
//		mv.addObject("pd", pd);
//		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
//		return mv;
//	}
//	
//	/**去新增页面
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		mv.setViewName("dst/inboundprenotice/inboundprenotice_edit");
//		mv.addObject("msg", "save");
//		mv.addObject("pd", pd);
//		return mv;
//	}	
//	
//	 /**去修改页面
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/goEdit")
//	public ModelAndView goEdit()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd = inboundprenoticeService.findById(pd);	//根据ID读取
//		mv.setViewName("dst/inboundprenotice/inboundprenotice_edit");
//		mv.addObject("msg", "edit");
//		mv.addObject("pd", pd);
//		return mv;
//	}	
//	
//	 /**批量删除
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/deleteAll")
//	@ResponseBody
//	public Object deleteAll() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundPreNotice");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		pd = this.getPageData();
//		List<PageData> pdList = new ArrayList<PageData>();
//		String DATA_IDS = pd.getString("DATA_IDS");
//		if(null != DATA_IDS && !"".equals(DATA_IDS)){
//			String ArrayDATA_IDS[] = DATA_IDS.split(",");
//			inboundprenoticeService.deleteAll(ArrayDATA_IDS);
//			pd.put("msg", "ok");
//		}else{
//			pd.put("msg", "no");
//		}
//		pdList.add(pd);
//		map.put("list", pdList);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	 /**导出到excel
//	 * @param
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/excel")
//	public ModelAndView exportExcel() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"导出InboundPreNotice到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		Map<String,Object> dataMap = new HashMap<String,Object>();
//		List<String> titles = new ArrayList<String>();
//		titles.add("备注1");	//1
//		titles.add("到货单编码");	//2
//		titles.add("单据类型");	//3
//		titles.add("业务单据编码");	//4
//		titles.add("备注5");	//5
//		titles.add("预计到货日期");	//6
//		titles.add("实际到货日期");	//7
//		titles.add("仓库编码");	//8
//		titles.add("仓库名称");	//9
//		titles.add("状态");	//10
//		titles.add("创建人");	//11
//		titles.add("创建时间");	//12
//		titles.add("备注13");	//13
//		titles.add("备注14");	//14
//		dataMap.put("titles", titles);
//		List<PageData> varOList = inboundprenoticeService.listAll(pd);
//		List<PageData> varList = new ArrayList<PageData>();
//		for(int i=0;i<varOList.size();i++){
//			PageData vpd = new PageData();
//			vpd.put("var1", varOList.get(i).get("inbound_notice_id").toString());	//1
//			vpd.put("var2", varOList.get(i).getString("inbound_notice_code"));	    //2
//			vpd.put("var3", varOList.get(i).get("bill_type").toString());	//3
//			vpd.put("var4", varOList.get(i).getString("purchase_code"));	    //4
//			vpd.put("var5", varOList.get(i).get("purchase_detail_id").toString());	//5
//			vpd.put("var6", varOList.get(i).getString("pre_arrival_time"));	    //6
//			vpd.put("var7", varOList.get(i).getString("arrival_time"));	    //7
//			vpd.put("var8", varOList.get(i).getString("store_sn"));	    //8
//			vpd.put("var9", varOList.get(i).getString("store_name"));	    //9
//			vpd.put("var10", varOList.get(i).get("status").toString());	//10
//			vpd.put("var11", varOList.get(i).getString("createby"));	    //11
//			vpd.put("var12", varOList.get(i).getString("create_time"));	    //12
//			vpd.put("var13", varOList.get(i).getString("updateby"));	    //13
//			vpd.put("var14", varOList.get(i).getString("update_time"));	    //14
//			varList.add(vpd);
//		}
//		dataMap.put("varList", varList);
//		ObjectExcelView erv = new ObjectExcelView();
//		mv = new ModelAndView(erv,dataMap);
//		return mv;
//	}
//	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
//}
