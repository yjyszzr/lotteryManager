package com.fh.controller.erp.warningexpire;

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
import org.apache.commons.lang.StringUtils;
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
import com.fh.service.erp.warningexpire.WarningExpireManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：库存预警
 * 创建人：FH Q313596790
 * 创建时间：2017-12-06
 */
@Controller
@RequestMapping(value="/warningexpire")
public class WarningExpireController extends BaseController {
	
	String menuUrl = "warningexpire/list.do"; //菜单地址(权限用)
	@Resource(name="warningexpireService")
	private WarningExpireManager warningexpireService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增WarningExpire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("warning_expire_id", this.get32UUID());	//主键
		pd.put("store_id", "0");	//仓库Id
		pd.put("sku_id", "0");	//物料Id
		pd.put("createby", "");	//创建人
		pd.put("create_time", "");	//创建时间
		pd.put("updateby", "");	//更新人
		pd.put("update_time", "");	//更新时间
		warningexpireService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除WarningExpire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		warningexpireService.delete(pd);
		out.write("success");
		out.close();
	}
	
	@RequestMapping(value="/flush")
	@ResponseBody
	public Object flush() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pageData = new PageData();
		List<PageData> pdList = new ArrayList<PageData>();
		List<PageData> warningExpires = warningexpireService.selectWarningExpire(new PageData());
		List<PageData> warningExpireBatchs = warningexpireService.selectwarningExpireBatch(new PageData());
		if(CollectionUtils.isNotEmpty(warningExpires)) {
			for(PageData pd : warningExpires) {
				pd.put("warning_date", DateUtil.fomatDate(pd.getString("warning_date")));
				pd.put("createby", getSessionUser().getUSER_ID());	//创建人
				pd.put("create_time", DateUtil.getSdfTimes());	//创建时间
				pd.put("updateby", getSessionUser().getUSER_ID());	//更新人
				pd.put("update_time", DateUtil.getSdfTimes());	//更新时间
			}
		}
		if(CollectionUtils.isNotEmpty(warningExpireBatchs)) {
			for(PageData pd : warningExpireBatchs) {
				pd.put("warning_date", DateUtil.fomatDate(pd.getString("warning_date")));
				pd.put("createby", getSessionUser().getUSER_ID());	//创建人
				pd.put("create_time", DateUtil.getSdfTimes());	//创建时间
				pd.put("updateby", getSessionUser().getUSER_ID());	//更新人
				pd.put("update_time", DateUtil.getSdfTimes());	//更新时间
			}
		}
		try {
			warningexpireService.saveWarningData(warningExpires,warningExpireBatchs);
			pageData.put("msg", "ok");
		} catch (Exception e) {
			pageData.put("msg", "no");
		}
		pdList.add(pageData);
		map.put("list", pdList);
		return AppUtil.returnObject(pageData, map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改WarningExpire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		warningexpireService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表WarningExpire");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sku_encode = pd.getString("sku_encode");
		String sku_name = pd.getString("sku_name");
		if(null != sku_encode && !"".equals(sku_encode)){
			pd.put("sku_encode", sku_encode.trim());
		}
		if(null != sku_name && !"".equals(sku_name)){
			pd.put("sku_name", sku_name.trim());
		}
		if(StringUtils.isEmpty(pd.getString("warning_date"))){
			pd.put("warning_date", DateUtil.getDay());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if(CollectionUtils.isNotEmpty(list)) {
			mv.addObject("storeList", list);
			if(StringUtils.isEmpty(pd.getString("store_sn"))) {
				pd.put("store_sn", list.get(0).getString("store_sn"));
			}
		}
		page.setPd(pd);
		List<PageData> varList = warningexpireService.list(page);	//列出WarningExpire列表
		mv.setViewName("erp/warningexpire/warningexpire_list");
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
		mv.setViewName("erp/warningexpire/warningexpire_edit");
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
		pd = warningexpireService.findById(pd);	//根据ID读取
		mv.setViewName("erp/warningexpire/warningexpire_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除WarningExpire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			warningexpireService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出WarningExpire到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("预警日期");	//1
		titles.add("仓库Id");	//2
		titles.add("仓库编码");	//3
		titles.add("仓库名称");	//4
		titles.add("物料Id");	//5
		titles.add("物料编码");	//6
		titles.add("物料名称");	//7
		titles.add("规格");	//8
		titles.add("单位");	//9
		titles.add("提前预警天数");	//10
		titles.add("预警数量");	//11
		titles.add("创建人");	//12
		titles.add("创建时间");	//13
		titles.add("更新人");	//14
		titles.add("更新时间");	//15
		dataMap.put("titles", titles);
		List<PageData> varOList = warningexpireService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("warning_date"));	    //1
			vpd.put("var2", varOList.get(i).get("store_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("store_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("store_name"));	    //4
			vpd.put("var5", varOList.get(i).get("sku_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("sku_encode"));	    //6
			vpd.put("var7", varOList.get(i).getString("sku_name"));	    //7
			vpd.put("var8", varOList.get(i).getString("spec"));	    //8
			vpd.put("var9", varOList.get(i).getString("unit_name"));	    //9
			vpd.put("var10", varOList.get(i).get("warning_days").toString());	//10
			vpd.put("var11", varOList.get(i).get("quantity").toString());	//11
			vpd.put("var12", varOList.get(i).getString("createby"));	    //12
			vpd.put("var13", varOList.get(i).getString("create_time"));	    //13
			vpd.put("var14", varOList.get(i).getString("updateby"));	    //14
			vpd.put("var15", varOList.get(i).getString("update_time"));	    //15
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
