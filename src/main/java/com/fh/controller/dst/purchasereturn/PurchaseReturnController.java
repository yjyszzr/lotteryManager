package com.fh.controller.dst.purchasereturn;

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
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.impl.OutboundNoticeService;
import com.fh.service.dst.outboundnoticedetail.impl.OutboundNoticeDetailService;
import com.fh.service.dst.purchasedetail.impl.PurchaseDetailService;
import com.fh.service.dst.purchasegroup.PurchaseGroupManager;
import com.fh.service.dst.purchasereturn.PurchaseReturnManager;
import com.fh.service.dst.purchasereturndetail.impl.PurchaseReturnDetailService;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：采购退货管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/purchasereturn")
public class PurchaseReturnController extends BaseController {
	
	String menuUrl = "purchasereturn/list.do"; //菜单地址(权限用)
	@Resource(name="purchasereturnService")
	private PurchaseReturnManager purchasereturnService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="purchasedetailService")
	private PurchaseDetailService purchaseDetailService;
	
	@Resource(name="purchasereturndetailService")
	private PurchaseReturnDetailService purchaseReturnDetailService;
	
	@Resource(name="outboundnoticeService")
	private OutboundNoticeService outboundnoticeService;
	
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailService outboundNoticeDetailService;
	
	@Resource(name="purchasegroupService")
	private PurchaseGroupManager purchasegroupService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseReturn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 状态：0 待审 1 通过 2 退货中 8 驳回 9 完成
		pd.put("status", 0);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("createby", user.getUSER_ID()); // 创建人
		pd.put("create_time", new Date()); // 创建时间
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		purchasereturnService.save(pd);
		
		//向采购退货明细表插入采购明细数据
		PageData detailPd = new PageData();
		detailPd.put("purchase_code", pd.getString("purchase_code"));
		List<PageData> pds = purchaseDetailService.listByParam(detailPd);
		if(CollectionUtils.isNotEmpty(pds)) {
			for (PageData pageData : pds) {
				PageData purchaseDetailPd = new PageData();
				purchaseDetailPd.put("purchase_return_code", pd.get("purchase_return_code"));
				purchaseDetailPd.put("purchase_code", pd.get("purchase_code"));
				purchaseDetailPd.put("status", 0);
				purchaseDetailPd.put("sku_id", pageData.get("sku_id"));
				purchaseDetailPd.put("purchase_detail_id", pageData.get("detail_id"));
				purchaseDetailPd.put("sku_name", pageData.get("sku_name"));
				purchaseDetailPd.put("return_quantity", pageData.get("good_quantity"));
				purchaseDetailPd.put("send_quantity", 0);
				purchaseDetailPd.put("quantity", 0);
				purchaseDetailPd.put("unit", pageData.get("unit"));
				purchaseDetailPd.put("createby", user.getUSER_ID()); // 创建人
				purchaseDetailPd.put("create_time", new Date()); // 创建时间
				purchaseDetailPd.put("updateby", user.getUSER_ID()); // 更新人
				purchaseDetailPd.put("update_time", new Date()); // 更新时间
				purchaseReturnDetailService.save(purchaseDetailPd);
			}
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 保存退货单，含详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveDetail")
	public ModelAndView saveDetail(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增退货单");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "saveDetail")){out.println("无权限");} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String jsonStr = pd.getString("returnJson");
		JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("purchase_return_code", jsonOb.getString("purchase_return_code"));	
		pd.put("purchase_code", jsonOb.getString("purchase_code"));	
		pd.put("supplier_sn", jsonOb.getString("supplier_sn"));	
		pd.put("type", jsonOb.getString("business_type"));	
		pd.put("remark", " ");	
		
		/**				jsonStr+="{purchase_return_code:'${pd.purchase_return_code}',supplier_sn:'${pd.supplier_sn}',purchase_code:'"+$("#purchase_code").val()+"'
		 * ,details:["+detailStr+"]";

		 * purchase_return_code,	
		purchase_code,	
		supplier_sn,	
		status,	
		remark,	
		auditby,	
		audit_time,	
		createby,	
		create_time,	
		updateby,	
		update_time,	
		return_id
		 */
		pd.put("status", 0);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("createby", user.getUSER_ID()); // 创建人
		pd.put("create_time", new Date()); // 创建时间
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		 
		purchasereturnService.save(pd);
		//插入tui货单详情
		JSONArray details = jsonOb.getJSONArray("details");
		for(int i=0;i<details.size();i++) {
			JSONObject detail = details.getJSONObject(i);
			 pd = new PageData();
			 pd.put("purchase_return_code", jsonOb.getString("purchase_return_code"));
			 pd.put("purchase_code", jsonOb.getString("purchase_code"));
			 pd.put("sku_id", detail.getString("sku_id"));
			 pd.put("sku_encode", detail.getString("sku_encode"));
			 pd.put("sku_name", detail.getString("sku_name"));
			 pd.put("return_quantity", detail.getString("return_quantity"));
			 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
			 pd.put("send_quantity", "0");
			 pd.put("quantity", "0");
			 pd.put("status", "0");
			 pd.put("unit", detail.getString("unit"));
			 pd.put("createby",getSessionUser().getUSER_ID());
			 pd.put("create_time", new Timestamp(System.currentTimeMillis()));
			 pd.put("updateby",getSessionUser().getUSER_ID());
			 pd.put("update_time", new Timestamp(System.currentTimeMillis()));
			 purchaseReturnDetailService.save(pd);
			
			 /**
			  * purchase_return_code,	
		purchase_code,	
		status,	
		sku_id,	
		sku_encode,
		purchase_detail_id,
		sku_name,	
		return_quantity,	
		send_quantity,	
		quantity,	
		unit,	
		remark,	
		createby,	
		create_time,	
		updateby,	
		update_time,	
		return_detail_id
			  */
		}
		
		out.print("success");
		out.close();
		return null;
	}
	
	/**审核通过
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/audited")
	public void audited(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//状态0 待审 1 通过 2 退货中 8 驳回 9 完成
		pd.put("status", 1);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("auditby", user.getUSER_ID());
		pd.put("audit_time", new Date());
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		purchasereturnService.update(pd);
		out.write(pd.toString());
		out.write("success");
		out.close();
	}
	
	/**退货
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/returnGoods")
	public ModelAndView returnGoods() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		,1,3,CT00000025,PUR00000210,石板大米,1,0,0,0,11,,;,2,4,CT00000025,PUR00000210,玉米阿萨德,2,0,0,0,k,,;
		String returnGoods = pd.getString("returnGoodsData");
		List<Map<String,Object>> rowDatas = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotEmpty(returnGoods)) {
			String[] list = returnGoods.split(";");
			for(int i=0;i<list.length;i++) {
				String row = list[i];
				if(StringUtils.isNotEmpty(row)) {
					String[] rows = row.split(",");
					Map<String,Object> map = new HashMap<String,Object>();
					//[,1,3,CT00000025,PUR00000210,石板大米,1,0,0,0,11,,;]
					//for(int j=0;j<rows.length;j++) {
						//if(j == 2)
							map.put("return_detail_id", rows[2]);
					//	if(j == 5)
							map.put("sku_name", rows[6]);
					//	if(j == 6)
							map.put("sku_id", rows[8]);
					//	if(j == 8)
							map.put("send_quantity", rows[13]);
				//	}
					rowDatas.add(map);
				}
			}
		}
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		PageData purchasereturn = purchasereturnService.findById(pd);
		//采购退货通过，插入发货通知单数据
		{
			PageData pd2 = new PageData();
			String outbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
			pd2.put("outbound_notice_code", outbound_notice_code);
			Object store_sn = pd.get("store_sn");
			Object store_name = pd.get("store_name");
			pd2.put("store_sn", store_sn);
			pd2.put("store_name", store_name);
			//状态 0:领料 1:调拨 2:采购退货 3:报损 4:内销 8:生产退货
			if(Integer.parseInt(pd.get("menu_type").toString()) == 0) {
				pd2.put("bill_type", 2);
			}else {
				pd2.put("bill_type", 8);
			}
			Object purchase_return_code = purchasereturn.get("purchase_return_code");
			Object pre_send_time = pd.get("pre_send_time");
			pd2.put("purchase_code", purchase_return_code);
			pd2.put("pre_send_time", pre_send_time);
			//状态 0:创建 10:已出库
			pd2.put("status", 0);
			pd2.put("createby", user.getUSER_ID());	//更新人
			pd2.put("create_time", new Date());	//更新时间
			pd2.put("updateby", user.getUSER_ID());	//更新人
			pd2.put("update_time", new Date());	//更新时间
			outboundnoticeService.save(pd2);
			
			if(CollectionUtils.isNotEmpty(rowDatas)) {
				for (Map<String, Object> map : rowDatas) {
					if(Integer.parseInt(map.get("send_quantity").toString()) == 0) continue;
					PageData pd1 = new PageData();
					pd1.put("outbound_notice_code", outbound_notice_code);
					pd1.put("purchase_code", purchase_return_code);
					Object return_detail_id = map.get("return_detail_id");
					pd1.put("purchase_detail_id", return_detail_id);
					Object sku_id = map.get("sku_id");
					pd1.put("sku_id", sku_id);
					Object sku_name = map.get("sku_name");
					pd1.put("sku_name", sku_name);
					Object send_quantity = map.get("send_quantity");
					pd1.put("pre_send_quantity", send_quantity);
					pd1.put("send_quantity", 0);
					//状态 预留
					pd1.put("status", 0);
					pd1.put("createby", user.getUSER_ID());	//更新人
					pd1.put("create_time", new Date());	//更新时间
					pd1.put("updateby", user.getUSER_ID());	//更新人
					pd1.put("update_time", new Date());	//更新时间
					outboundNoticeDetailService.save(pd1);
					PageData returnDetailUp = new PageData();
					//退货详情中增加已发货
					returnDetailUp.put("return_detail_id", return_detail_id);
					returnDetailUp.put("send_quantity_add", send_quantity);
					returnDetailUp.put("updateby", user.getUSER_ID());	//更新人
					returnDetailUp.put("update_time", new Date());	//更新时间
					purchaseReturnDetailService.edit(returnDetailUp);
					
				}
			}
		}
		//状态0 待审 1 通过 2 退货中 8 驳回 9 完成
		//校验是否已全部发货，如果已全部发货，置为完成
		List<PageData> noComps = purchaseReturnDetailService.queryNoCompletes(purchasereturn);
		if(noComps!=null && noComps.size()>0) {
			pd.put("status", 2);
		}else {
			pd.put("status", 9);
		}
		
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		purchasereturnService.updateStatus(pd);
		mv.addObject("status", pd.get("status"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**驳回
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/reject")
	public void reject(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//状态 0 待审 1 通过 2 退货中 8 驳回 9 完成
		pd.put("status", 8);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("auditby", user.getUSER_ID());
		pd.put("audit_time", new Date());
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		purchasereturnService.update(pd);
		out.write("success");
		out.close();
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseReturn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasereturnService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseReturn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasereturnService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**去审核界面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAudited")
	public ModelAndView goAudited() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd = purchasereturnService.findById(pd);	//根据ID读取
		mv.setViewName("erp/purchasereturn/purchasereturn_audit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseReturn");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		String status = pd.getString("status");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != status && !"".equals(status)){
			pd.put("status", status.trim());
		}else {
			pd.put("status", "");
		}
		page.setPd(pd);
		List<PageData>	varList = null;	//列出PurchaseReturn列表
		//采购组
				fillPurcharseGPs(pd, mv);
		//采购组查询条件，每个用户只能查自己所属采购组的订单
				if(pd.getString("purchase_group_id")==null || pd.getString("purchase_group_id").trim().equals("")) {
					String userId = getSessionUser().getUSER_ID();
					//admin 可以查所有的,不是admin需加过滤条件
					if(	userId.equals("1")) {
						varList = purchasereturnService.list(page);
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
							varList = purchasereturnService.list(page);
						}
					}
					//purchase_group_ids
				}else {
					varList =purchasereturnService.list(page);
				}
		
		mv.setViewName("erp/purchasereturn/purchasereturn_list");
		if (CollectionUtils.isNotEmpty(varList)) {
			for (PageData var : varList) {
				Object auditby = var.get("auditby");
				try {
					if(auditby != null) {
						User auditUser = userService.getUserAndRoleById(auditby.toString());
						if (auditUser != null)
							var.put("auditby", auditUser.getNAME());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		RedisUtil.getUserInfoById(varList, "createby", "NAME", "createby");
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
		mv.setViewName("erp/purchasereturn/purchasereturn_edit");
		String purchaseReturnCode = getSerialConfigUtilService.getSerialCode(FormType.PURCHASE_RETURN_CODE);
		pd.put("purchase_return_code", purchaseReturnCode);
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
		pd = purchasereturnService.findById(pd);	//根据ID读取
		mv.setViewName("erp/purchasereturn/purchasereturn_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseReturn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasereturnService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseReturn到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("退货编码");	//1
		titles.add("备注3");	//2
		titles.add("供应商编码");	//3
		titles.add("状态");	//4
		titles.add("退货说明");	//5
		titles.add("审批人");	//6
		titles.add("审批时间");	//7
		titles.add("创建人");	//8
		titles.add("创建时间");	//9
		titles.add("更新人");	//10
		titles.add("更新时间");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasereturnService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("purchase_return_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("purchase_code"));	    //2
			vpd.put("var3", varOList.get(i).getString("supplier_sn"));	    //3
			vpd.put("var4", varOList.get(i).get("status").toString());	//4
			vpd.put("var5", varOList.get(i).getString("remark"));	    //5
			vpd.put("var6", varOList.get(i).getString("auditby"));	    //6
			vpd.put("var7", varOList.get(i).getString("audit_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("createby"));	    //8
			vpd.put("var9", varOList.get(i).getString("create_time"));	    //9
			vpd.put("var10", varOList.get(i).getString("updateby"));	    //10
			vpd.put("var11", varOList.get(i).getString("update_time"));	    //11
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
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
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
