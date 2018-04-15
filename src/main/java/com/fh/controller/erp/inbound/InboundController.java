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

import org.apache.commons.collections.CollectionUtils;
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
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.express.enums.InboundType;
import com.fh.service.erp.inbound.InboundManager;

/** 
 * 说明：入库单查询
 * 创建人：FH Q313596790
 * 创建时间：2017-09-14
 */
@Controller
@RequestMapping(value="/inbound")
public class InboundController extends BaseController {
	
	String menuUrl = "inbound/list.do"; //菜单地址(权限用)
	@Resource(name="inboundService")
	private InboundManager inboundService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Inbound");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_id", this.get32UUID());	//主键
		pd.put("inbound_id", "0");	//自动id
		pd.put("status", "0");	//备注10
		inboundService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Inbound");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Inbound");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Inbound");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String inbound_type = pd.getString("inbound_type");				//关键词检索条件
		if(null != inbound_type && !"".equals(inbound_type)){
			pd.put("inbound_type", inbound_type.trim());
		}
		String purchase_code = pd.getString("purchase_code");				//关键词检索条件
		if(null != purchase_code && !"".equals(purchase_code)){
			pd.put("purchase_code", purchase_code.trim());
		}
		String inbound_notice_code = pd.getString("inbound_notice_code");				//关键词检索条件
		if(null != inbound_notice_code && !"".equals(inbound_notice_code)){
			pd.put("inbound_notice_code", inbound_notice_code.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_EXPRESS,StoreTypeConstants.STORE_GIFT,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_QUARANTINE,StoreTypeConstants.STORE_VIRTUAL};
		List<PageData> list = GetOwnStore.getOwnStore(types);
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null) {
            	pd.put("store_sn", list.get(0).getString("store_sn"));
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = inboundService.list(page);	//列出Inbound列表
    		for(PageData pageData:varList) {
    		   String type=pageData.getString("inbound_type");
    		   pageData.put("inbound_type", InboundType.getByCode(type));
    		}
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/inbound/inbound_list");
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
		mv.setViewName("erp/inbound/inbound_edit");
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
		pd = inboundService.findById(pd);	//根据ID读取
		mv.setViewName("erp/inbound/inbound_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Inbound");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Inbound到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("自动id");	//1
		titles.add("入库单号");	//2
		titles.add("入库类型");	//3
		titles.add("业务单号");	//4
		titles.add("业务单明细号");	//5
		titles.add("到货通知号");	//6
		titles.add("业务时间");	//7
		titles.add("仓库编号");	//8
		titles.add("仓库名称");	//9
		titles.add("备注10");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("inbound_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_code"));	    //2
			vpd.put("var3", varOList.get(i).get("inbound_type").toString());	//3
			vpd.put("var4", varOList.get(i).getString("purchase_code"));	    //4
			vpd.put("var5", varOList.get(i).get("purchase_detail_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("inbound_notice_code"));	    //6
			vpd.put("var7", varOList.get(i).getString("business_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("store_sn"));	    //8
			vpd.put("var9", varOList.get(i).getString("store_name"));	    //9
			vpd.put("var10", varOList.get(i).get("status").toString());	//10
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
