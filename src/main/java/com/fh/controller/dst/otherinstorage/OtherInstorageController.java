package com.fh.controller.dst.otherinstorage;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.google.gson.JsonObject;
import com.fh.service.dst.otherinstorage.OtherInstorageManager;
import com.fh.service.dst.otherinstoragedetail.OtherInstorageDetailManager;
import com.fh.service.dst.otherinstoragestockbatch.OtherInstorageStockBatchManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：其他入库单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 */
@Controller
@RequestMapping(value="/otherinstorage")
public class OtherInstorageController extends BaseController {
	
	String menuUrl = "otherinstorage/list.do"; //菜单地址(权限用)
	@Resource(name="otherinstorageService")
	private OtherInstorageManager otherinstorageService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;
	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="otherinstoragedetailService")
	private OtherInstorageDetailManager otherinstoragedetailService;
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	@Resource(name="otherinstoragestockbatchService")
	private OtherInstorageStockBatchManager otherinstoragestockbatchService;
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OtherInstorage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
//		pd.put("other_instorage_id", this.get32UUID());	//主键
	//	pd.put("other_instorage_id", "0");	//ID
		User user = getSessionUser();
		pd.remove("other_instorage_id");
	//	pd.put("auditby", user.getNAME());	//审核人
//		pd.put("audit_time", Tools.date2Str(new Date()));	//审核时间
		pd.put("createby", user.getNAME());	//创建人
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		pd.put("updateby", user.getNAME());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		pd.put("status", 0);
		otherinstorageService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除OtherInstorage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		otherinstorageService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(PrintWriter pw ) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OtherInstorage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		//如果是提交，需判断各批次数量是否与订单数量相等
		if("1".equals(pd.getString("isCommit"))) {
			String badBatch = isBatchAmountGood( pd);
			if(badBatch.length()>0){
				pw.print(badBatch);//各批次数量之和与订单数量不符，请检查
				
				pw.flush();
				pw.close();
				
			}else {
				
				pd.put("commitby", getSessionUser().getUSER_ID());
				pd.put("commit_time", curTime);
				otherinstorageService.edit(pd);
				pw.flush();
				pw.close();
			}
			return null;
		}
		otherinstorageService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	private String  isBatchAmountGood( PageData pd) throws Exception {
		List<PageData> otherDetails = otherinstoragedetailService.listAll(pd);
		String ret = "";
		if(otherDetails!=null && otherDetails.size()>0) {
			for(PageData detailpd : otherDetails ) {
				String isopen_batch = detailpd.getString("isopen_batch");
				if(!"1".equals(isopen_batch)) {
					continue;
				}
					int OrderQuanty = Integer.parseInt(detailpd.getString("quantity"));
				 //批次信息
				 PageData query =  new PageData();
				 query.put("other_instorage_detail_id", detailpd.getString("other_instorage_detail_id"));
				 List<PageData> batchs  = otherinstoragestockbatchService.listAll(query);
				 	int batchQuanty = 0;
				 	//各批次之和
				 if(batchs!=null && batchs.size()>0) {
					 for(PageData batch : batchs) {
						batchQuanty += Integer.parseInt(batch.getString("quantity"));
					 }
				 }
				 if(OrderQuanty != batchQuanty) {
					 ret += ","+detailpd.getString("sku_encode");
				 }
			}
		}else {//没有明细不允许提交
			return "201";
		}
		return ret.replaceFirst(",", "");
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherInstorage");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_GIFT,StoreTypeConstants.STORE_LOSS};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
			page.setPd(pd);
			List<PageData>	varList = otherinstorageService.list(page);	//列出OtherInstorage列表
			if(CollectionUtils.isNotEmpty(varList)) {
				RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
				RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
//				fillUserName(varList,"commitby",userService);
//				fillUserName(varList,"auditby",userService);
//    			varList.parallelStream().forEach(var->{
//    				PageData varPd = new PageData();
//    				
//    				varPd.put("USER_ID", var.getString("auditby"));
//					try {
//						PageData pageData = userService.findById(varPd);
//						if(pageData != null) {
//							var.put("auditby", pageData.getString("NAME"));
//	    				}
//					} catch (Exception e) {
//					}
//    			});
    		}
			mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("dst/otherinstorage/otherinstorage_list");
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
		String code=getSerialConfigUtilService.getSerialCode(FormType.OTHER_INSTORAGE_CODE);
		pd.put("other_instorage_code", code);
		mv.setViewName("dst/otherinstorage/otherinstorage_edit");
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
		pd = otherinstorageService.findById(pd);	//根据ID读取
		mv.setViewName("dst/otherinstorage/otherinstorage_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	/**
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/auditPass")
	public ModelAndView  auditPass(PrintWriter pw)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改auditPass");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "auditPass")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("auditby", getSessionUser().getUSER_ID());
		pd.put("audit_time", Tools.date2Str(new Date()));	
		otherinstorageService.saveToNotice(pd,getSessionUser());
		JSONObject jb  = new JSONObject();
		pw.write("1");
		pw.flush();
		pw.close();
		return null;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除OtherInstorage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			otherinstorageService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出OtherInstorage到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("编码");	//2
		titles.add("仓库编号");	//3
		titles.add("仓库名称");	//4
		titles.add("状态");	//5
		titles.add("备注");	//6
		titles.add("审核人");	//7
		titles.add("审核时间");	//8
		titles.add("创建人");	//9
		titles.add("创建时间");	//10
		titles.add("更新人");	//11
		titles.add("更新时间");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = otherinstorageService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("other_instorage_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("other_instorage_code"));	    //2
			vpd.put("var3", varOList.get(i).getString("store_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("store_name"));	    //4
			vpd.put("var5", varOList.get(i).get("status").toString());	//5
			vpd.put("var6", varOList.get(i).getString("remark"));	    //6
			vpd.put("var7", varOList.get(i).getString("auditby"));	    //7
			vpd.put("var8", varOList.get(i).getString("audit_time"));	    //8
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
}
