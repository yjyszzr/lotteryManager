package com.fh.controller.dst.warehouse.erpwarestock;

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
import com.fh.service.dst.warehouse.erpgoods.impl.ErpGoodsService;
import com.fh.service.dst.warehouse.erpwarestock.ErpWareStockManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：库存管理
 * 创建人：FH Q313596790
 * 创建时间：2017-08-16
 */
@Controller
@RequestMapping(value="/erpwarestock")
public class ErpWareStockController extends BaseController {
	
	String menuUrl = "erpwarestock/list.do"; //菜单地址(权限用)
	@Resource(name="erpwarestockService")
	private ErpWareStockManager erpwarestockService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="erpgoodsService")
	private ErpGoodsService erpGoodsService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ErpWareStock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String goods_sn = pd.getString("goods_sn");
		if(StringUtils.isNotEmpty(goods_sn)) {
			PageData pdSn = new PageData();
			pdSn.put("goods_sn", goods_sn);
			pdSn = erpGoodsService.findByPd(pdSn);
			if(pdSn != null) {
				pd.put("goods_id", pdSn.get("goods_id").toString());	//商品Id
				pd.put("goods_name", pdSn.getString("goods_name"));	//商品名称
				pd.put("unit_name", pdSn.getString("unit_name"));	//计量单位
			}else {
				pd.put("goods_id","0");
				pd.put("goods_name", "");	
				pd.put("unit_name", "");	
			}
		}else {
			pd.put("goods_id","0");
			pd.put("goods_name", "");	
			pd.put("unit_name", "");	
		}
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		if(user != null) {
			pd.put("create_user", user.getUSER_ID());	//创建人
			pd.put("update_user", user.getUSER_ID());	//修改人
		}else {
			pd.put("create_user", "");	//创建人
			pd.put("update_user", "");	//修改人
		}
		pd.put("create_time", DateUtil.getTime());	//创建时间
		pd.put("update_time", DateUtil.getTime());	//修改时间
		erpwarestockService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除ErpWareStock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		erpwarestockService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ErpWareStock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		if(user != null) {
			pd.put("update_user", user.getUSER_ID());	//修改人
		}else {
			pd.put("update_user", "");	//修改人
		}
		pd.put("update_time", DateUtil.getTime());	//修改时间
		erpwarestockService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ErpWareStock");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		String lastStart = pd.getString("lastStart");				
		String lastEnd = pd.getString("lastEnd");				
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != lastStart && !"".equals(lastStart)){
			pd.put("lastStart", lastStart.trim()+" 00:00:00");
		}
		if(null != lastEnd && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd.trim()+" 23:59:59");
		}
		page.setPd(pd);
		List<PageData>	varList = erpwarestockService.list(page);	//列出ErpWareStock列表
		if(CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(pageData->{
				String userId = pageData.getString("create_user");
				if(StringUtils.isNotEmpty(userId)) {
					try {
						User user = userService.getUserAndRoleById(userId);
						if(user != null) {
							pageData.put("create_user", user.getNAME());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		mv.setViewName("warehouse/erpwarestock/erpwarestock_list");
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
		mv.setViewName("warehouse/erpwarestock/erpwarestock_edit");
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
		pd = erpwarestockService.findById(pd);	//根据ID读取
		mv.setViewName("warehouse/erpwarestock/erpwarestock_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ErpWareStock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			erpwarestockService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ErpWareStock到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("仓库编码");	//1
		titles.add("仓库名称");	//2
		titles.add("商品编码");	//3
		titles.add("商品名称");	//4
		titles.add("实际数量");	//5
		titles.add("可用数量");	//6
		titles.add("计量单位");	//7
		titles.add("创建人");	//8
		titles.add("创建时间");	//9
		titles.add("修改人");	//10
		titles.add("修改时间");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = erpwarestockService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("warehouse_code"));	    //1
			vpd.put("var2", varOList.get(i).get("warehouse_name"));	//2
			vpd.put("var3", varOList.get(i).getString("goods_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("goods_name"));	    //4
			vpd.put("var5", varOList.get(i).get("actual_quantity").toString());	//5
			vpd.put("var6", varOList.get(i).get("available_quantity").toString());	//6
			vpd.put("var7", varOList.get(i).getString("unit_name"));	    //7
			String userId = varOList.get(i).getString("create_user");
			if(StringUtils.isNotEmpty(userId)) {
				try {
					User user = userService.getUserAndRoleById(userId);
					if(user != null) {
						vpd.put("var8", user.getNAME());	    //8
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			vpd.put("var9", sdf.format(varOList.get(i).get("create_time")));	    //9
			String upUserId = varOList.get(i).getString("update_user");
			if(StringUtils.isNotEmpty(upUserId)) {
				try {
					User user = userService.getUserAndRoleById(upUserId);
					if(user != null) {
						vpd.put("var10", user.getNAME());	    //10
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			vpd.put("var11", sdf.format(varOList.get(i).get("update_time")));	    //11
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**判断该仓库的商品是否存在
	 * @return
	 */
	@RequestMapping(value="/hasCodeAndSn")
	@ResponseBody
	public Object hasCodeAndSn(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(erpwarestockService.findByPd(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
