package com.fh.controller.dst.purchase;

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

import com.fh.common.PurchaseConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.purchase.PurchaseManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.purchasegroup.PurchaseGroupManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.StringUtil;

/** 
 * 说明：采购订单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/purchase")
public class PurchaseController extends BaseController {
	
	String menuUrl = "purchase/list.do"; //菜单地址(权限用)
	@Resource(name="purchaseService")
	private PurchaseManager purchaseService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;
	@Resource(name="purchasegroupService")
	private PurchaseGroupManager purchasegroupService;
	@Resource(name="userService")
	private UserManager userService;

	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Purchase");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();		//默认数据
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
//		pd.put("purchase_id", this.get32UUID());	//主键
		purchaseService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Purchase");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchaseService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(PrintWriter pw) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Purchase");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//如果是“提交”动作，需判断是否有明细
		if("1".equals(pd.getString("isCommit"))) {
			List<PageData> ds = purchasedetailService.listAll(pd);
			if(ds==null || ds.isEmpty()) {
				pw.write("200");
				pw.flush();
				pw.close();
				return null ;
			}
		}
		/**
		 * p.commit_time,
		p.commitby,
		p.complete_time,
		p.commit_time,
		p.completeby,
		p.audit_time,
		p.auditby
		 */
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby", getSessionUser().getUSER_ID());
		pd.put("update_time", curTime);
		if("1".equals(pd.getString("isUpdateStaus"))) {
			int status = Integer.parseInt( pd.getString("status"));
			switch (status) {
			case PurchaseConstants.BUSINESS_STATUS_CREATE:
				pd.put("commitby", getSessionUser().getUSER_ID());
				pd.put("commit_time", curTime);
				break;
			case PurchaseConstants.BUSINESS_STATUS_COMPLETE:
				pd.put("completeby", getSessionUser().getUSER_ID());
				pd.put("complete_time", curTime);
				break;
			case PurchaseConstants.BUSINESS_STATUS_AGREE:
			case PurchaseConstants.BUSINESS_STATUS_REJECT:
				pd.put("auditby", getSessionUser().getUSER_ID());
				pd.put("audit_time", curTime);
				break;

			default:
				break;
			}
		}
		purchaseService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Purchase");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.listCommom(page);
		mv.setViewName("dst/purchase/purchase_list");
		return mv;
	}
	/**
	报表
	*/
	@RequestMapping(value="/listForReport")
	public  ModelAndView listForReport(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Purchase报表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.listCommom(page);
		List<PageData>	varList = (List<PageData>) mv.getModel().get("varList");
		//采购组查询条件------结束
		RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
//				fillUserName(varList,"auditby",userService);
		mv.setViewName("dst/purchase/purchase_list_report");
		return mv;
	}
	
	
	private ModelAndView listCommom(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//采购组
		fillPurcharseGPs(pd, mv);
		
		if(StringUtil.isEmptyStr(pd.getString("status"))){
			String bsType=pd.getString("bsType");
					if(StringUtil.isEmptyStr(bsType)) {
						pd.put("notStatus", PurchaseConstants.BUSINESS_STATUS_COMPLETE);
					}else if(bsType.equals("1")) {
						pd.put("status", PurchaseConstants.BUSINESS_STATUS_CREATE);
					}else if(bsType.equals("2")) {
						pd.put("status", PurchaseConstants.BUSINESS_STATUS_AGREE);
					}
			
		}
		page.setPd(pd);
		List<PageData>	varList = null;	//列出Purchase列表
		//采购组查询条件，每个用户只能查自己所属采购组的订单
		if(pd.getString("purchase_group_id")==null || pd.getString("purchase_group_id").trim().equals("")) {
			String userId = getSessionUser().getUSER_ID();
			//admin 可以查所有的,不是admin需加过滤条件
			if(	userId.equals("1")) {
				varList = purchaseService.list(page);
			}else {//加过滤条件，如果不属于任何组，直接返回空
				List<PageData> gps = (List<PageData>)mv.getModel().get("gpList");
				if(gps.size()==0) {
					varList = new ArrayList<>();
				}else {
					String gpids = "(";
					for(PageData gp  : gps) {
						gpids+=gp.getString("purchase_group_id")+",";
					}
					gpids = gpids.substring(0, gpids.length()-1);
					gpids +=")";
					pd.put("purchase_group_ids", gpids);
					page.setPd(pd);
					varList = purchaseService.list(page);
				}
			}
			//purchase_group_ids
		}else {
			varList = purchaseService.list(page);
		}
		//采购组查询条件------结束
//		fillUserName(varList,"commitby",userService);
		RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");

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
		String code=getSerialConfigUtilService.getSerialCode(FormType.PURCHASE_CODE);
		mv.setViewName("dst/purchase/purchase_edit");
		mv.addObject("msg", "save");
		//默认数据
		pd.put("purchase_code",code);
		pd.put("bill_type", PurchaseConstants.BILL_TYPE_NORM);//标准票据
		pd.put("status", PurchaseConstants.BUSINESS_STATUS_NO_COMMIT);
		//默认数据结束
		//采购组
		fillPurcharseGPs(pd, mv);
		
		
				 
		
		mv.addObject("pd", pd);
		return mv;
	}	
	/**
	 * 向request中添加采购组信息
	 * @param pd
	 * @param mv
	 * @throws Exception
	 */
	private void fillPurcharseGPs(PageData pd,ModelAndView mv) throws Exception {
		PageData gpParas = new PageData();
		gpParas.put("type", pd.getString("business_type"));
		String userId = getSessionUser().getUSER_ID();
		//admin 可以查所有的
		if(userId.equals("1")) {
			mv.addObject("gpList",purchasegroupService.listAll(gpParas));
		}else {
			gpParas.put("user_id", userId);
			mv.addObject("gpList",purchasegroupService.listAllGPByUser(gpParas));
		}
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
		pd = purchaseService.findById(pd);	//根据ID读取
		mv.setViewName("dst/purchase/purchase_edit");
		//采购组
		fillPurcharseGPs(pd, mv);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Purchase");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchaseService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Purchase到excel");
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
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = purchaseService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("purchase_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("purchase_code"));	    //2
			vpd.put("var3", varOList.get(i).getString("supplier_sn"));	    //3
			vpd.put("var4", varOList.get(i).get("bill_type").toString());	//4
			vpd.put("var5", varOList.get(i).get("business_type").toString());	//5
			vpd.put("var6", varOList.get(i).getString("purchase_date"));	    //6
			vpd.put("var7", varOList.get(i).getString("purchase_dep"));	    //7
			vpd.put("var8", varOList.get(i).getString("purchase_org"));	    //8
			vpd.put("var9", varOList.get(i).get("status").toString());	//9
			vpd.put("var10", varOList.get(i).getString("createby"));	    //10
			vpd.put("var11", varOList.get(i).getString("create_time"));	    //11
			vpd.put("var12", varOList.get(i).getString("updateby"));	    //12
			vpd.put("var13", varOList.get(i).getString("update_time"));	    //13
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
