package com.fh.controller.erp.directsalesdetail;

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
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.erp.directsalesdetail.DirectSalesDetailManager;

/** 
 * 说明：内部直销管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/directsalesdetail")
public class DirectSalesDetailController extends BaseController {
	
	String menuUrl = "directsalesdetail/list.do"; //菜单地址(权限用)
	@Resource(name="directsalesdetailService")
	private DirectSalesDetailManager directsalesdetailService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listByCode")
	public ModelAndView listByCode(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DirectSalesCustomer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		List<PageData>	varList = directsalesdetailService.listByCode(pd);	//列出DirectSalesCustomer列表
		mv.setViewName("erp/directsalescustomer/directsalescustomer_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DirectSalesDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("direct_sales_detail_id", this.get32UUID());	//主键
		
		PageData query = new PageData();
		query.put("direct_sales_code", pd.get("direct_sales_code"));
		query.put("sku_id", pd.get("sku_id"));
		
		if (directsalesdetailService.queryGoodSku(query)!=null) {
			mv.addObject("msg","fail");
			mv.addObject("info","已存在该商品!");
			mv.setViewName("info_fail");
			return mv;
		}
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("price", 0);
		pd.put("status", 0);
		pd.put("createby", user.getUSER_ID());
		pd.put("create_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		
		directsalesdetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DirectSalesDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		directsalesdetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DirectSalesDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		Object price = pd.get("price");
		if(price == null) {
			pd.put("price", 0);
		}
		
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		directsalesdetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DirectSalesDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		
		List<PageData>	varList = directsalesdetailService.listByCode(pd);	//列出DirectSalesDetail列表
		mv.setViewName("erp/directsalesdetail/directsalesdetail_list");
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
		mv.setViewName("erp/directsalesdetail/directsalesdetail_edit");
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
		pd = directsalesdetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/directsalesdetail/directsalesdetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DirectSalesDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			directsalesdetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DirectSalesDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("内部直销订单编码");	//1
		titles.add("商品ID");	//2
		titles.add("商品名称");	//3
		titles.add("数量");	//4
		titles.add("单位");	//5
		titles.add("单价");	//6
		titles.add("金额");	//7
		titles.add("状态");	//8
		titles.add("创建人");	//9
		titles.add("创建时间");	//10
		titles.add("更新人");	//11
		titles.add("更新时间");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = directsalesdetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("direct_sales_code"));	    //1
			vpd.put("var2", varOList.get(i).get("sku_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("sku_name"));	    //3
			vpd.put("var4", varOList.get(i).get("quantity").toString());	//4
			vpd.put("var5", varOList.get(i).getString("unit"));	    //5
			vpd.put("var6", varOList.get(i).getString("price"));	    //6
			vpd.put("var7", varOList.get(i).getString("amount"));	    //7
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
	
	@RequestMapping(value="/detailpricelist")
	public ModelAndView detailPricelist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DirectSalesDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = directsalesdetailService.listByCode(pd);	//列出DirectSalesDetail列表
		mv.setViewName("erp/directsalesdetail/directsalesdetailprice_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	 /**去修改页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goEditPrice")
		public ModelAndView goEditPrice()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String unit = pd.getString("unit");
			pd = directsalesdetailService.findById(pd);	//根据ID读取
			pd.put("unit", unit);
			mv.setViewName("erp/directsalesdetail/directsalesdetailprice_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			return mv;
		}
		
		/**修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/editprice")
		public ModelAndView editprice() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"修改DirectSalesDetail");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			PageData pdPrice=new PageData();
			pdPrice.put("price", pd.get("price"));
			pdPrice.put("updateby", pd.get("updateby"));
			pdPrice.put("direct_sales_detail_id", pd.get("direct_sales_detail_id"));
			
			directsalesdetailService.updatePrice(pd);
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
}
