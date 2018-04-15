package com.fh.controller.dst.purchasereturndetail;

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
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.purchasereturn.impl.PurchaseReturnService;
import com.fh.service.dst.purchasereturndetail.PurchaseReturnDetailManager;
import com.fh.service.dst.purchasereturndetail.impl.PurchaseReturnDetailService;

/** 
 * 说明：采购退货明细
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/purchasereturndetail")
public class PurchaseReturnDetailController extends BaseController {
	
	String menuUrl = "purchasereturndetail/list.do"; //菜单地址(权限用)
	@Resource(name="purchasereturndetailService")
	private PurchaseReturnDetailService purchasereturndetailService;
	
	@Resource(name="purchasereturnService")
	private PurchaseReturnService purchaseReturnService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseReturnDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("sku_id", "0");	//skuId
		pd.put("createby", "");	//创建人
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		pd.put("updateby", "");	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		purchasereturndetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseReturnDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasereturndetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseReturnDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasereturndetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseReturnDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData statusPd = purchaseReturnService.findById(pd);	//根据ID读取
		pd.put("status", statusPd.get("status"));
		PageData queryPd = new PageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			queryPd.put("keywords", keywords.trim());
		}
		String purchaseReturnCode = pd.getString("purchase_return_code");
		if(null != purchaseReturnCode && !"".equals(purchaseReturnCode)){
			queryPd.put("purchase_return_code", purchaseReturnCode);
		}
		page.setPd(queryPd);
		List<PageData>	varList = purchasereturndetailService.list(page);	//列出PurchaseReturnDetail列表
		mv.setViewName("erp/purchasereturndetail/purchasereturndetail_list");
		if(CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(var->{
				if(var.get("return_quantity") == null) {
					var.put("return_quantity", 0);
				}
				if(var.get("send_quantity") == null) {
					var.put("send_quantity", 0);
				}
				if(var.get("quantity") == null) {
					var.put("quantity", 0);
				}
			});
		}
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
		mv.setViewName("erp/purchasereturndetail/purchasereturndetail_edit");
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
		pd = purchasereturndetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/purchasereturndetail/purchasereturndetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseReturnDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasereturndetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseReturnDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("采购退货编号");	//1
		titles.add("采购订单编号");	//2
		titles.add("状态");	//3
		titles.add("skuId");	//4
		titles.add("商品名称");	//5
		titles.add("退货数量");	//6
		titles.add("已发货数量");	//7
		titles.add("出库数量");	//8
		titles.add("单位");	//9
		titles.add("退货说明");	//10
		titles.add("创建人");	//11
		titles.add("创建时间");	//12
		titles.add("更新人");	//13
		titles.add("更新时间");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasereturndetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("purchase_return_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("purchase_code"));	    //2
			vpd.put("var3", varOList.get(i).get("status").toString());	//3
			vpd.put("var4", varOList.get(i).get("sku_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("sku_name"));	    //5
			vpd.put("var6", varOList.get(i).get("return_quantity").toString());	//6
			vpd.put("var7", varOList.get(i).get("send_quantity").toString());	//7
			vpd.put("var8", varOList.get(i).getString("quantity"));	    //8
			vpd.put("var9", varOList.get(i).getString("unit"));	    //9
			vpd.put("var10", varOList.get(i).getString("remark"));	    //10
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
