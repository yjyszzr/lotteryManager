package com.fh.controller.erp.goodsquantityline;

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
import com.fh.service.erp.goodsquantityline.GoodsQuantityLineManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：库存线
 * 创建人：FH Q313596790
 * 创建时间：2017-12-07
 */
@Controller
@RequestMapping(value="/goodsquantityline")
public class GoodsQuantityLineController extends BaseController {
	
	String menuUrl = "goodsquantityline/list.do"; //菜单地址(权限用)
	@Resource(name="goodsquantitylineService")
	private GoodsQuantityLineManager goodsquantitylineService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增GoodsQuantityLine");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("goods_quantity_line_id", this.get32UUID());	//主键
		goodsquantitylineService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/saveQuantity")
	@ResponseBody
	public Object saveQuantity() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pageData = new PageData();
		pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		RedisUtil.getStoreBySn(pageData, "store_sn", "store_id", "store_id");
		if(pageData.get("sku_id") == null || StringUtils.isEmpty(pageData.getString("store_sn"))
				|| StringUtils.isEmpty(pageData.get("min_quantity").toString()) || StringUtils.isEmpty(pageData.get("max_quantity").toString())
				|| pageData.get("store_id") == null) {
			pageData.put("msg", "unknown");
			pdList.add(pageData);
			map.put("list", pdList);
			return AppUtil.returnObject(pageData, map);
		}
		List<PageData> list = goodsquantitylineService.selectQuantityLine(pageData);
		if(list.size() > 0) {
			pageData.put("msg", "repeat");
			pdList.add(pageData);
			map.put("list", pdList);
			return AppUtil.returnObject(pageData, map);
		}
		try {
			pageData.put("createby", getSessionUser().getUSER_ID());
			pageData.put("create_time", DateUtil.getSdfTimes());
			pageData.put("updateby", getSessionUser().getUSER_ID());
			pageData.put("update_time", DateUtil.getSdfTimes());
			goodsquantitylineService.save(pageData);
			pageData.put("msg", "ok");
		} catch (Exception e) {
			pageData.put("msg", "no");
		}
		pdList.add(pageData);
		map.put("list", pdList);
		return AppUtil.returnObject(pageData, map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除GoodsQuantityLine");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsquantitylineService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改GoodsQuantityLine");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsquantitylineService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表GoodsQuantityLine");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String[] types = {StoreTypeConstants.STORE_GOOD};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if(CollectionUtils.isNotEmpty(list)) {
			mv.addObject("storeList", list);
			if(StringUtils.isEmpty(pd.getString("store_sn"))) {
				pd.put("store_sn", list.get(0).getString("store_sn"));
			}
		}
		if(pd.get("sku_id") != null) {
			page.setPd(pd);
			List<PageData> varList = goodsquantitylineService.list(page);	//列出GoodsQuantityLine列表
			if(CollectionUtils.isNotEmpty(varList)) {
				RedisUtil.getStoreInfoById(varList, "store_id", "store_name", "store_name");
			}
			mv.addObject("varList", varList);
		}
		mv.setViewName("erp/goodsquantityline/goodsquantityline_list");
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
		mv.setViewName("erp/goodsquantityline/goodsquantityline_edit");
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
		pd = goodsquantitylineService.findById(pd);	//根据ID读取
		mv.setViewName("erp/goodsquantityline/goodsquantityline_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除GoodsQuantityLine");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			goodsquantitylineService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出GoodsQuantityLine到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注2");	//1
		titles.add("备注3");	//2
		titles.add("备注4");	//3
		titles.add("备注5");	//4
		titles.add("备注6");	//5
		titles.add("备注7");	//6
		titles.add("备注8");	//7
		titles.add("备注9");	//8
		dataMap.put("titles", titles);
		List<PageData> varOList = goodsquantitylineService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("sku_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("store_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("min_quantity").toString());	//3
			vpd.put("var4", varOList.get(i).get("max_quantity").toString());	//4
			vpd.put("var5", varOList.get(i).getString("createby"));	    //5
			vpd.put("var6", varOList.get(i).getString("create_time"));	    //6
			vpd.put("var7", varOList.get(i).getString("updateby"));	    //7
			vpd.put("var8", varOList.get(i).getString("update_time"));	    //8
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
