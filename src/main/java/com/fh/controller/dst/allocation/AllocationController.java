package com.fh.controller.dst.allocation;

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
import org.apache.shiro.session.Session;
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
import com.fh.entity.system.User;
import com.fh.service.dst.allocation.AllocationManager;
import com.fh.service.dst.allocationdetail.AllocationDetailManager;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：调拨单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/allocation")
public class AllocationController extends BaseController {
	
	String menuUrl = "allocation/list.do"; //菜单地址(权限用)
	@Resource(name="allocationService")
	private AllocationManager allocationService;
	
	@Resource(name="allocationdetailService")
	private AllocationDetailManager allocationdetailService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getserialconfigutilService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Allocation");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//0 待提交 1 待审核 2 通过 3 驳回 4 已发货 9 已完成
		pd.put("status", 0);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("createby", user.getUSER_ID());	//创建人
		pd.put("create_time", new Date());	//创建时间
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		allocationService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Allocation");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		Page page = new Page();
		page.setPd(pd);
		List<PageData> pds = allocationdetailService.list(page);
		if(!CollectionUtils.isEmpty(pds)) {
			String[] ids = new String[pds.size()];
			for (int i=0;i< pds.size();i++) {
				ids[i] = pds.get(i).get("allocation_detail_id").toString();
			}
			allocationdetailService.deleteAll(ids);
		}
		pd = allocationService.findByCode(pd);
		if(pd != null) {
			allocationService.delete(pd);
		}
		out.write("success");
		out.close();
	}
	
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilManager getSerialConfigUtilService;

	/**提交
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/submit")
	public void submit(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//状态 0 待提交 1 待审核 2 通过 3 驳回 4 已发货 9 已完成
		pd.put("status", 1);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		pd.put("commitby", getSessionUser().getUSER_ID());//提交人
		pd.put("commit_time",curTime);//提交时间
		allocationService.update(pd);
		out.write("success");
		out.close();
	}
	
	/**审核通过
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/audited")
	public void audited(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		PageData allocation = allocationService.findById(pd);
		//todo 调拨通过，插入发货通知单数据
		{
			PageData pd2 = new PageData();
			String outbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
			pd2.put("outbound_notice_code", outbound_notice_code);
			Object store_sn = allocation.get("from_store_sn");
			Object store_name = allocation.get("from_store_name");
			pd2.put("store_sn", store_sn);
			pd2.put("store_name", store_name);
			pd2.put("bill_type", 1);
			Object purchase_code = allocation.get("allocation_code");
			Object pre_send_time = allocation.get("pre_send_time");
			pd2.put("purchase_code", purchase_code);
			pd2.put("pre_send_time", pre_send_time);
			pd2.put("status", 0);
			pd2.put("createby", user.getUSER_ID());	//更新人
			pd2.put("create_time", new Date());	//更新时间
			pd2.put("updateby", user.getUSER_ID());	//更新人
			pd2.put("update_time", new Date());	//更新时间
			outboundnoticeService.save(pd2);
			
			Page page = new Page();
			Object allocation_code= allocation.get("allocation_code");
			PageData pd3 = new PageData();
			pd3.put("allocation_code", allocation_code);
			page.setPd(pd3);
			List<PageData> list = allocationdetailService.list(page);
			for(PageData pageData: list){
				PageData pd1 = new PageData();
				pd1.put("outbound_notice_code", outbound_notice_code);
				pd1.put("purchase_code", purchase_code);
				Object purchase_detail_id = pageData.get("allocation_detail_id");
				pd1.put("purchase_detail_id", purchase_detail_id);
				Object sku_id = pageData.get("sku_id");
				pd1.put("sku_id", sku_id);
				Object sku_name = pageData.get("sku_name");
				pd1.put("sku_name", sku_name);
				Object pre_send_quantity = pageData.get("pre_quantity");
				pd1.put("pre_send_quantity", pre_send_quantity);
				pd1.put("send_quantity", 0);
				pd1.put("status", 0);
				pd1.put("createby", user.getUSER_ID());	//更新人
				pd1.put("create_time", new Date());	//更新时间
				pd1.put("updateby", user.getUSER_ID());	//更新人
				pd1.put("update_time", new Date());	//更新时间
				outboundnoticedetailService.save(pd1);
			}
		}
		//0 待提交 1 待审核 2 通过 3 驳回 4 已发货 9 已完成
		pd.put("status", 2);
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		pd.put("auditby",getSessionUser().getUSER_ID());//审核人
		pd.put("audit_time", new Date());//审核时间
		allocationService.update(pd);
		out.write("success");
		out.close();
	}
	
	/**驳回
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/reject")
	public void reject(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//0 待提交 1 待审核 2 通过 3 驳回 4 已发货 9 已完成
		pd.put("status", 3);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		allocationService.update(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Allocation");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		allocationService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Allocation");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types_out = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_GIFT};
		List<PageData> list_out = GetOwnStore.getOwnStore(types_out);
        if(CollectionUtils.isNotEmpty(list_out)) {
        	mv.addObject("storeList_out", list_out);
        	if(pd.get("stores_out") == null) {
            	pd.put("store_sn_out", "");
            }else {
            	pd.put("store_sn_out",pd.get("stores_out"));
            }
        }else {
        	mv.setViewName("erp/allocation/allocation_list");
    		mv.addObject("varList", "");
    		mv.addObject("pd", pd);
    		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
        	return mv;
        }
        String[] types_in = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_GIFT};
		List<PageData> list_in = GetOwnStore.getOwnStore(types_in);
        if(CollectionUtils.isNotEmpty(list_in)) {
        	mv.addObject("storeList_in", list_in);
        	if(pd.get("stores_in") == null) {
            	pd.put("store_sn_in", "");
            }else {
            	pd.put("store_sn_in",pd.get("stores_in"));
            }
        }
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list_in.size(); i++) {
			storeSnList.add(list_in.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		page.setPd(pd);
		List<PageData>	varList = allocationService.list(page);	//列出Allocation列表
		if(!CollectionUtils.isEmpty(varList)) {
			varList.parallelStream().forEach(var->{
				Date preSendTime = (Date)var.get("pre_send_time");
				Date sendTime = (Date)var.get("send_time");
				Date preArrivalTime = (Date)var.get("pre_arrival_time");
				Date arrivalTime = (Date)var.get("arrival_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				var.put("pre_send_time", sdf.format(preSendTime));
				if(sendTime != null) {
					var.put("send_time", sdf.format(sendTime));
				}
				var.put("pre_arrival_time", sdf.format(preArrivalTime));
				if(arrivalTime != null) {
					var.put("arrival_time", sdf.format(arrivalTime));
				}
			});
		}
		
		
		mv.setViewName("erp/allocation/allocation_list");
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
		mv.setViewName("erp/allocation/allocation_edit");
		String allocation_code = getserialconfigutilService.getSerialCode(FormType.ALLOCATION_CODE);
		pd.put("allocation_code", allocation_code);
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
		pd = allocationService.findById(pd);	//根据ID读取
		if(pd != null) {
			Date preSendTime = (Date)pd.get("pre_send_time");
			Date preArrivalTime = (Date)pd.get("pre_arrival_time");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pd.put("pre_send_time", sdf.format(preSendTime));
			pd.put("pre_arrival_time", sdf.format(preArrivalTime));
		}
		mv.setViewName("erp/allocation/allocation_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Allocation");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			allocationService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Allocation到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("调拨编号");	//1
		titles.add("调出仓库编号");	//2
		titles.add("调入仓库编号");	//3
		titles.add("调出仓库名称");	//4
		titles.add("调入仓库名称");	//5
		titles.add("预计发货时间");	//6
		titles.add("实际发货时间");	//7
		titles.add("预计到货时间");	//8
		titles.add("实际到货时间");	//9
		titles.add("状态");	//10
		titles.add("创建人");	//11
		titles.add("创建时间");	//12
		titles.add("更新人");	//13
		titles.add("更新时间");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = allocationService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("allocation_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("from_store_sn"));	    //2
			vpd.put("var3", varOList.get(i).getString("to_store_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("from_store_name"));	    //4
			vpd.put("var5", varOList.get(i).getString("to_store_name"));	    //5
			vpd.put("var6", varOList.get(i).getString("pre_send_time"));	    //6
			vpd.put("var7", varOList.get(i).getString("send_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("pre_arrival_time"));	    //8
			vpd.put("var9", varOList.get(i).getString("arrival_time"));	    //9
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
