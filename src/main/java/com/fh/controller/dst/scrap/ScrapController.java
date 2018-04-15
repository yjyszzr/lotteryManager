package com.fh.controller.dst.scrap;

import java.io.PrintWriter;
import java.sql.Timestamp;
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
import com.fh.common.PurchaseConstants;
import com.fh.common.ScrapConstants;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.scrap.ScrapManager;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Tools;

/** 
 * 说明：报损单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/scrap")
public class ScrapController extends BaseController {
	
	String menuUrl = "scrap/list.do"; //菜单地址(权限用)
	@Resource(name="scrapService")
	private ScrapManager scrapService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="scrapdetailService")
	private ScrapDetailManager scrapdetailService;
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = getSessionUser();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("scrap_id", this.get32UUID());	//主键
		pd.put("status", ScrapConstants.BUSINESS_STATUS_UNSUBMIT);
		pd.put("auditby", "");	//审批人
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("updatetime", curTime);
		scrapService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		scrapService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("updateby", getSessionUser().getUSER_ID());
		pd.put("updatetime", new Date());
		scrapService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	@RequestMapping(value="/complete")
	public ModelAndView complete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("status", ScrapConstants.BUSINESS_STATUS_AGREE);
		User user = getSessionUser();
		pd.put("updateby",user.getUSER_ID());
		pd.put("updatetime", new Date());
		pd.put("verifyby",user.getUSER_ID());
		pd.put("verify_time", new Date());
		scrapService.complete(pd);
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	@RequestMapping(value="/refuse")
	public ModelAndView refuse() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("status", ScrapConstants.BUSINESS_STATUS_REJECT);
		User user = getSessionUser();
		pd.put("updateby",user.getUSER_ID());
		pd.put("updatetime", new Date());
		pd.put("verifyby",user.getUSER_ID());
		pd.put("verify_time", new Date());
		scrapService.verify(pd);
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	@RequestMapping(value="/completeCheck")
	public ModelAndView completeCheck() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("auditby", getSessionUser().getUSER_ID());
		pd.put("audit_time", new Date());
		pd.put("updateby", getSessionUser().getUSER_ID());
		pd.put("updatetime", new Date());
		scrapService.completeCheck(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	@RequestMapping(value="/editStatus")
	@ResponseBody
	public Object editStatus() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("updateby", getSessionUser().getUSER_ID());
		pd.put("updatetime", new Date());
		pd.put("commitby", getSessionUser().getUSER_ID());
		pd.put("commit_time", new Date());
		Page page = new Page();
		page.setPd(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> list = scrapdetailService.list(pd);
		if(list == null || list.size() <= 0) {
			map.put("msg", "请填写报损明细！");
			map.put("key", "no");
		}else {
			scrapService.commit(pd);
			map.put("key", "ok");
			map.put("msg", "执行成功！");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Scrap");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
		
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = scrapService.list(page);	//列出Scrap列表
    		if(CollectionUtils.isNotEmpty(varList)) {
    			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
    			RedisUtil.getUserInfoById(varList, "verifyby", "NAME", "verifyby");
    		}
    		mv.addObject("varList", varList);
        }else {
    		mv.addObject("varList", "");
        }
		mv.setViewName("erp/scrap/scrap_list");
		mv.addObject("pd", pd);
		mv.addObject("bsType", 0);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/list1")
	public ModelAndView list1(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Scrap");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
		
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = scrapService.list3(page);	//列出Scrap列表
    		if(CollectionUtils.isNotEmpty(varList)) {
    			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
    			RedisUtil.getUserInfoById(varList, "verifyby", "NAME", "verifyby");
    		}
    		mv.addObject("varList", varList);
        }else {
    		mv.addObject("varList", "");
        }
		mv.setViewName("erp/scrap/scrap_list");
		mv.addObject("pd", pd);
		mv.addObject("bsType", 1);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/list2")
	public ModelAndView list2(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Scrap");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = scrapService.list3(page);	//列出Scrap列表
    		if(CollectionUtils.isNotEmpty(varList)) {
    			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
    			RedisUtil.getUserInfoById(varList, "verifyby", "NAME", "verifyby");
    		}
    		mv.addObject("varList", varList);
        }else {
    		mv.addObject("varList", "");
        }
		mv.setViewName("erp/scrap/scrap_list");
		mv.addObject("pd", pd);
		mv.addObject("bsType", 2);
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
		String code=getSerialConfigUtilService.getSerialCode(FormType.SCRAP_CODE);
		pd = this.getPageData();
		mv.setViewName("erp/scrap/scrap_edit");
		mv.addObject("msg", "save");
		//默认数据
		pd.put("scrap_code",code);
		pd.put("status", ScrapConstants.BUSINESS_STATUS_CREATE);
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
		pd = scrapService.findById(pd);	//根据ID读取
		mv.setViewName("erp/scrap/scrap_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Scrap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			scrapService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Scrap到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("报损单号");	//1
		titles.add("仓库编码");	//2
		titles.add("仓库名称");	//3
		titles.add("审批状态");	//4
		titles.add("报损说明");	//5
		titles.add("审批人");	//6
		titles.add("审批时间");	//7
		titles.add("创建人");	//8
		titles.add("创建时间");	//9
		titles.add("更新人");	//10
		titles.add("更新时间");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = scrapService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("scrap_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("store_sn"));	    //2
			vpd.put("var3", varOList.get(i).getString("store_name"));	    //3
			vpd.put("var4", varOList.get(i).get("status").toString());	//4
			vpd.put("var5", varOList.get(i).getString("remark"));	    //5
			vpd.put("var6", varOList.get(i).getString("auditby"));	    //6
			vpd.put("var7", varOList.get(i).getString("audit_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("createby"));	    //8
			vpd.put("var9", varOList.get(i).getString("create_time"));	    //9
			vpd.put("var10", varOList.get(i).getString("updateby"));	    //10
			vpd.put("var11", varOList.get(i).getString("updatetime"));	    //11
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
