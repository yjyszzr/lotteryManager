package com.fh.controller.dst.supplierstore;

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
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.purchase.impl.PurchaseService;
import com.fh.service.dst.supplierstore.SupplierStoreManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：供应商
 * 创建人：FH Q313596790
 * 创建时间：2017-09-24
 */
@Controller
@RequestMapping(value="/supplierstore")
public class SupplierStoreController extends BaseController {
	
	String menuUrl = "supplierstore/list.do"; //菜单地址(权限用)
	@Resource(name="supplierstoreService")
	private SupplierStoreManager supplierstoreService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SupplierStore");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		boolean ishas = supplierstoreService.isHas(pd);
		if(ishas) {
			String info = "供应商与仓库已关联!";
			mv.addObject("msg","fail");
			mv.addObject("info",info);
			mv.setViewName("info_fail");
			return mv;
		}
		
		pd.put("status", "0");	//备注6
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("createby", user.getUSER_ID());	//创建人
		pd.put("create_time", new Date());	//创建时间
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		supplierstoreService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除SupplierStore");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		supplierstoreService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SupplierStore");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		supplierstoreService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表SupplierStore");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String supplier_sn = pd.getString("supplier_sn");				//关键词检索条件
		if(null != supplier_sn && !"".equals(supplier_sn)){
			pd.put("supplier_sn", supplier_sn.trim());
		}
		
		String store_sn = pd.getString("store_sn");				//关键词检索条件
		if(null != store_sn && !"".equals(store_sn)){
			pd.put("store_sn", store_sn.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = supplierstoreService.list(page);	//列出SupplierStore列表
		mv.setViewName("erp/supplierstore/supplierstore_list");
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
		mv.setViewName("erp/supplierstore/supplierstore_edit");
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
		pd = supplierstoreService.findById(pd);	//根据ID读取
		mv.setViewName("erp/supplierstore/supplierstore_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SupplierStore");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			supplierstoreService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出SupplierStore到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("自动id");	//1
		titles.add("供应商编码");	//2
		titles.add("供应商名称");	//3
		titles.add("仓库编码");	//4
		titles.add("仓库名称");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = supplierstoreService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("supplier_store_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("supplier_sn"));	    //2
			vpd.put("var3", varOList.get(i).getString("supplier_name"));	    //3
			vpd.put("var4", varOList.get(i).getString("store_sn"));	    //4
			vpd.put("var5", varOList.get(i).getString("store_name"));	    //5
			vpd.put("var6", varOList.get(i).get("status").toString());	//6
			vpd.put("var7", varOList.get(i).getString("createby"));	    //7
			vpd.put("var8", varOList.get(i).getString("create_time"));	    //8
			vpd.put("var9", varOList.get(i).getString("updateby"));	    //9
			vpd.put("var10", varOList.get(i).getString("update_time"));	    //10
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

