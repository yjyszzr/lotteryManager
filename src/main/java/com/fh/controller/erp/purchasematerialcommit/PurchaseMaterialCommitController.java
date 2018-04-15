package com.fh.controller.erp.purchasematerialcommit;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.dst.purchase.PurchaseManager;
import com.fh.service.erp.hander.MaterialCommitManager;
import com.fh.service.erp.purchasematerialcommit.PurchaseMaterialCommitManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：领料提交明细
 * 创建人：FH Q313596790
 * 创建时间：2017-10-12
 */
@Controller
@RequestMapping(value="/purchasematerialcommit")
public class PurchaseMaterialCommitController extends BaseController {
	
	String menuUrl = "purchasematerialcommit/list.do"; //菜单地址(权限用)
	@Resource(name="purchasematerialcommitService")
	private PurchaseMaterialCommitManager purchasematerialcommitService;
	
	@Resource(name="purchaseService")
	private PurchaseManager purchaseService;
	
	@Resource(name="materialcommitService")
	private MaterialCommitManager materialcommitService;
	/**更新状态
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/submit")
	public Map<String, String> arrived() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		logBefore(logger, Jurisdiction.getUsername()+"物料submit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return map;} //校验权限
		
		
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			materialcommitService.saveCommit(pd);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "fail");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseMaterialCommit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("material_detail_id", this.get32UUID());	//主键
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		pd.put("status", 0);
		pd.put("createby", user.getUSER_ID());
		pd.put("create_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		purchasematerialcommitService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseMaterialCommit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasematerialcommitService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseMaterialCommit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		purchasematerialcommitService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseMaterialCommit");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		List<PageData>	varList = purchasematerialcommitService.listById(pd);	//列出PurchaseMaterialCommit列表
		mv.setViewName("erp/purchasematerialcommit/purchasematerialcommit_list");
		mv.addObject("varList", varList);
		PageData query = new PageData();
		query.put("purchase_code", pd.get("purchase_code"));
		pd.put("supplier_sn", purchaseService.findByCode(query).get("supplier_sn"));
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
		mv.setViewName("erp/purchasematerialcommit/purchasematerialcommit_edit");
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
		Object supplier_sn = pd.get("supplier_sn");
		pd = purchasematerialcommitService.findById(pd);	//根据ID读取
		pd.put("supplier_sn", supplier_sn);
		mv.setViewName("erp/purchasematerialcommit/purchasematerialcommit_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseMaterialCommit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasematerialcommitService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseMaterialCommit到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("采购单编码");	//1
		titles.add("采购单明细ID");	//2
		titles.add("仓库编码");	//3
		titles.add("仓库名称");	//4
		titles.add("物料ID");	//5
		titles.add("物料编码");	//6
		titles.add("物料名称");	//7
		titles.add("领料数量");	//8
		titles.add("物料单位");	//9
		titles.add("领料状态");	//10
		titles.add("备注12");	//11
		titles.add("备注13");	//12
		titles.add("备注14");	//13
		titles.add("备注15");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasematerialcommitService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("purchase_code"));	    //1
			vpd.put("var2", varOList.get(i).get("purchase_detail_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("store_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("store_name"));	    //4
			vpd.put("var5", varOList.get(i).get("sku_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("sku_encode"));	    //6
			vpd.put("var7", varOList.get(i).getString("sku_name"));	    //7
			vpd.put("var8", varOList.get(i).getString("quantity"));	    //8
			vpd.put("var9", varOList.get(i).getString("unit"));	    //9
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
