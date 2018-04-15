package com.fh.controller.dst.pgtinboundprenoticedetail;

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
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.pgtinboundprenoticedetail.PgtInboundPreNoticeDetailManager;

/** 
 * 说明：入库预通知明细
 * 创建人：FH Q313596790
 * 创建时间：2017-11-22
 */
@Controller
@RequestMapping(value="/pgtinboundprenoticedetail")
public class PgtInboundPreNoticeDetailController extends BaseController {
	
	String menuUrl = "pgtinboundprenoticedetail/list.do"; //菜单地址(权限用)
	@Resource(name="pgtinboundprenoticedetailService")
	private PgtInboundPreNoticeDetailManager pgtinboundprenoticedetailService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PgtInboundPreNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("notice_detail_id_id", this.get32UUID());	//主键
		pd.put("notice_detail_id", "0");	//备注1
		pd.put("good_quantity", "0");	//备注10
		pd.put("bad_quantity", "0");	//备注11
		pgtinboundprenoticedetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PgtInboundPreNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtinboundprenoticedetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PgtInboundPreNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtinboundprenoticedetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PgtInboundPreNoticeDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = pgtinboundprenoticedetailService.list(page);	//列出PgtInboundPreNoticeDetail列表
		mv.setViewName("dst/pgtinboundprenoticedetail/pgtinboundprenoticedetail_list");
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
		mv.setViewName("dst/pgtinboundprenoticedetail/pgtinboundprenoticedetail_edit");
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
		pd = pgtinboundprenoticedetailService.findById(pd);	//根据ID读取
		mv.setViewName("dst/pgtinboundprenoticedetail/pgtinboundprenoticedetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PgtInboundPreNoticeDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pgtinboundprenoticedetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PgtInboundPreNoticeDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("通知单编码");	//2
		titles.add("业务单编码");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("商品名称");	//6
		titles.add("商品名称");	//7
		titles.add("备注8");	//8
		titles.add("预计到货数量");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		titles.add("备注20");	//20
		titles.add("批次");	//21
		dataMap.put("titles", titles);
		List<PageData> varOList = pgtinboundprenoticedetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("notice_detail_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_code"));	    //2
			vpd.put("var3", varOList.get(i).getString("purchase_code"));	    //3
			vpd.put("var4", varOList.get(i).get("purchase_detail_id").toString());	//4
			vpd.put("var5", varOList.get(i).get("sku_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("sku_name"));	    //6
			vpd.put("var7", varOList.get(i).getString("sku_encode"));	    //7
			vpd.put("var8", varOList.get(i).getString("spec"));	    //8
			vpd.put("var9", varOList.get(i).get("pre_arrival_quantity").toString());	//9
			vpd.put("var10", varOList.get(i).get("good_quantity").toString());	//10
			vpd.put("var11", varOList.get(i).get("bad_quantity").toString());	//11
			vpd.put("var12", varOList.get(i).get("bad_deal_type").toString());	//12
			vpd.put("var13", varOList.get(i).get("quality_status").toString());	//13
			vpd.put("var14", varOList.get(i).getString("quality_time"));	    //14
			vpd.put("var15", varOList.get(i).get("quality_id").toString());	//15
			vpd.put("var16", varOList.get(i).get("status").toString());	//16
			vpd.put("var17", varOList.get(i).getString("createby"));	    //17
			vpd.put("var18", varOList.get(i).getString("create_time"));	    //18
			vpd.put("var19", varOList.get(i).getString("updateby"));	    //19
			vpd.put("var20", varOList.get(i).getString("update_time"));	    //20
			vpd.put("var21", varOList.get(i).getString("batch_code"));	    //21
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
