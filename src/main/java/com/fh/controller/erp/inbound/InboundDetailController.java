package com.fh.controller.erp.inbound;

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
import com.fh.util.express.enums.GoodAttrType;
import com.fh.service.erp.inbounddetail.InboundDetailManager;

/** 
 * 说明：入库明细
 * 创建人：FH Q313596790
 * 创建时间：2017-09-14
 */
@Controller
@RequestMapping(value="/inbounddetail")
public class InboundDetailController extends BaseController {
	
	String menuUrl = "inbounddetail/list.do"; //菜单地址(权限用)
	@Resource(name="inbounddetailService")
	private InboundDetailManager inbounddetailService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_detail_id", this.get32UUID());	//主键
		pd.put("inbound_detail_id", "0");	//备注1
		pd.put("inbound_code", "");	//备注2
		pd.put("purchase_code", "");	//备注3
		pd.put("purchase_detail_id", "0");	//备注4
		pd.put("inbound_notice_code", "");	//备注5
		pd.put("notice_detail_id", "0");	//备注6
		pd.put("sku_id", "0");	//备注7
		pd.put("status", "0");	//备注12
		pd.put("createby", "");	//备注13
		pd.put("create_time", "");	//备注14
		pd.put("updateby", "");	//备注15
		pd.put("update_time", "");	//备注16
		inbounddetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inbounddetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inbounddetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String inbound_code = pd.getString("inbound_code");				//关键词检索条件
		if(null != inbound_code && !"".equals(inbound_code)){
			pd.put("inbound_code", inbound_code.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inbounddetailService.list(page);	//列出InboundDetail列表
		for(PageData pageData:varList) {
			String attr_cate=pageData.getString("attr_cate");	
			pageData.put("attr_cate", GoodAttrType.getByCode(attr_cate));
			
		}
		mv.setViewName("erp/inbounddetail/inbounddetail_list");
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
		mv.setViewName("erp/inbounddetail/inbounddetail_edit");
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
		pd = inbounddetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/inbounddetail/inbounddetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inbounddetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundDetail到excel");
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
		titles.add("物料名称");	//8
		titles.add("物料编码");	//9
		titles.add("物料规格");	//10
		titles.add("数量");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		dataMap.put("titles", titles);
		List<PageData> varOList = inbounddetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("inbound_detail_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_code"));	    //2
			vpd.put("var3", varOList.get(i).getString("purchase_code"));	    //3
			vpd.put("var4", varOList.get(i).get("purchase_detail_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("inbound_notice_code"));	    //5
			vpd.put("var6", varOList.get(i).get("notice_detail_id").toString());	//6
			vpd.put("var7", varOList.get(i).get("sku_id").toString());	//7
			vpd.put("var8", varOList.get(i).getString("sku_name"));	    //8
			vpd.put("var9", varOList.get(i).getString("sku_encode"));	    //9
			vpd.put("var10", varOList.get(i).getString("spec"));	    //10
			vpd.put("var11", varOList.get(i).get("quantity").toString());	//11
			vpd.put("var12", varOList.get(i).get("status").toString());	//12
			vpd.put("var13", varOList.get(i).getString("createby"));	    //13
			vpd.put("var14", varOList.get(i).getString("create_time"));	    //14
			vpd.put("var15", varOList.get(i).getString("updateby"));	    //15
			vpd.put("var16", varOList.get(i).getString("update_time"));	    //16
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
