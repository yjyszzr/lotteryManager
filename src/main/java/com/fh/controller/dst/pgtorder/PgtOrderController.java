package com.fh.controller.dst.pgtorder;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.OrderShippingStatusEnum;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.pgtorder.PgtOrderManager;
import com.fh.service.erp.deliverylog.DeliveryLogManager;
import com.fh.service.erp.deliverymember.DeliveryMemberManager;
import com.fh.service.erp.deliveryorg.DeliveryOrgManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/** 
 * 说明：pgt订单
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 */
@Controller
@RequestMapping(value="/pgtorder")
public class PgtOrderController extends BaseController {
	
	String menuUrl = "pgtorder/list.do"; //菜单地址(权限用)
	@Resource(name="pgtorderService")
	private PgtOrderManager pgtorderService;
	@Resource(name="deliverymemberService")
	private DeliveryMemberManager deliverymemberService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="deliverylogService")
	private DeliveryLogManager deliverylogService;
	
	@Resource(name="deliveryorgService")
	private DeliveryOrgManager deliveryorgService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PgtOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("pgt_order_id", this.get32UUID());	//主键
		
		pgtorderService.save(pd);
		//加第三方配送日志
		if("1".equals(pd.getString("is_selfship_commit"))){
			pd = pgtorderService.findById(pd);
			PageData recorde = new PageData();
			recorde.put("order_sn",pd.getString("order_sn") );
			Timestamp curTime = new Timestamp(System.currentTimeMillis());
			recorde.put("delivery_log_time", curTime);
			User user = getSessionUser();
			//默认数据
			recorde.put("createby",user.getUSER_ID());
			recorde.put("create_time",curTime);
			recorde.put("updateby",user.getUSER_ID());
			recorde.put("update_time", curTime);
			recorde.put("delivery_log_status", 0);
		//	delivery_org_id
			
//			PageData org = deliveryorgService.findById(pd);
//			String orgName="";
//			if(org!=null) {
//				orgName =  org.getString("delivery_org_name");
//				//recorde.put("delivery_org_name", org.getString("delivery_org_name"));
//			}
			
//订单已转交第三方快递公司{快递公司名称}，快递单号｛快递单号｝
			String content = "订单已转交第三方快递公司"+pd.getString("logistics_company_name")+"，快递单号"+pd.getString("logistics_code");
			recorde.put("delivery_log_content", content);
			deliverylogService.save(recorde);
		}
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PgtOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtorderService.delete(pd);
		out.write("success");
		out.close();
	}
	/**删除与配送任务的关联
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delTaskOrder")
	public void delTaskOrder(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PgtOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData upPd = new PageData();
		upPd.put("pgt_order_id", pd.getString("pgt_order_id"));
		upPd.put("pgt_order_id_del_order", "y");
		pgtorderService.edit(upPd);;
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PgtOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if("1".equals(pd.getString("is_selfship"))){
			pd.put("shippingTime",  new Timestamp(System.currentTimeMillis()));
		}
		pgtorderService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/updateState")
	public ModelAndView updateState(PrintWriter pw) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"updateState");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtorderService.edit(pd);
		//货物到达
		if((OrderShippingStatusEnum.TYPE4_INDELIVERY.getCode()+"").equals(pd.getString("shipping_status"))) {
			pd = pgtorderService.findById(pd);
			PageData recorde = new PageData();
			recorde.put("order_sn",pd.getString("order_sn") );
			Timestamp curTime = new Timestamp(System.currentTimeMillis());
			recorde.put("delivery_log_time", curTime);
			User user = getSessionUser();
			//默认数据
			recorde.put("createby",user.getUSER_ID());
			recorde.put("create_time",curTime);
			recorde.put("updateby",user.getUSER_ID());
			recorde.put("update_time", curTime);
			recorde.put("delivery_log_status", 0);
		//	delivery_org_id
			
			PageData org = deliveryorgService.findById(pd);
			String orgName="";
			if(org!=null) {
				orgName =  org.getString("delivery_org_name");
				//recorde.put("delivery_org_name", org.getString("delivery_org_name"));
			}
			
//订单已到{配送组织名称}
			String content = "订单已到"+orgName;
			recorde.put("delivery_log_content", content);
			deliverylogService.save(recorde);

		}
		pw.write("1");
		pw.flush();
		pw.close();
		return null;
	}
	/**批量添加订单到配送任务
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/boundOrdersToTask")
	public ModelAndView boundOrdersToTask(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("is_selfship", "0");
		pgtorderService.boundOrdersToTask(pd);
		out.print("1");
		out.flush();
		out.close();
		return null;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PgtOrder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		//状态过滤
		if("2".equals(pd.getString("operType"))) {
			pd.put("shipping_statusIn", "4");
		}else if("0".equals(pd.getString("operType"))) {
			pd.put("shipping_statusIn", "3,4");
		}
		PageData orgPara = new PageData();
		orgPara.put("user_id", getSessionUser().getUSER_ID());
		List<PageData> orgs = deliverymemberService.listAll(orgPara);
		List<PageData>	varList; //列出PgtOrder列表
		if(orgs!=null) {
			for(int i=orgs.size()-1;i>=0;i--) {
				if(StringUtil.isEmptyStr(orgs.get(i).getString("delivery_org_name"))){
					orgs.remove(i);
				}
			}
		}
		mv.addObject("deliveryOrgs", orgs);
		if(orgs==null || orgs.size()==0) {
			varList = new ArrayList<>();
		}else {
			String delivery_org_id = pd.getString("delivery_org_id");
			if(StringUtil.isEmptyStr(delivery_org_id)) {
				pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
			}else {
				boolean isValible = false;
				//判断是否越权
				for(int i=0;i<orgs.size();i++) {
					if(delivery_org_id.equals(orgs.get(i).getString("delivery_org_id"))) {
						isValible = true;
						break;
					}
				}
				if(!isValible) {
					pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
				}
			}
			
			varList = pgtorderService.list(page);	
		}
		
		mv.setViewName("dst/pgtorder/pgtorder_list");
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
		mv.setViewName("dst/pgtorder/pgtorder_edit");
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
		pd = pgtorderService.findById(pd);	//根据ID读取
		mv.setViewName("dst/pgtorder/pgtorder_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去物流公司修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goLogisticsEdit")
	public ModelAndView goLogisticsEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = pgtorderService.findById(pd);	//根据ID读取
		mv.setViewName("dst/pgtorder/pgtorder_logisticsEdit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PgtOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pgtorderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PgtOrder到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("订单编号");	//3
		titles.add("用户ID");	//4
		titles.add("状态");	//5
		titles.add("店铺ID");	//6
		titles.add("站点ID");	//7
		titles.add("备注8");	//8
		titles.add("承销商");	//9
		titles.add("电话");	//10
		titles.add("地址");	//11
		titles.add("添加时间");	//12
		titles.add("物料状态");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = pgtorderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("pgt_order_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("order_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("order_sn"));	    //3
			vpd.put("var4", varOList.get(i).get("user_id").toString());	//4
			vpd.put("var5", varOList.get(i).get("order_status").toString());	//5
			vpd.put("var6", varOList.get(i).get("shop_id").toString());	//6
			vpd.put("var7", varOList.get(i).get("site_id").toString());	//7
			vpd.put("var8", varOList.get(i).get("store_id").toString());	//8
			vpd.put("var9", varOList.get(i).getString("consignee"));	    //9
			vpd.put("var10", varOList.get(i).getString("tel"));	    //10
			vpd.put("var11", varOList.get(i).getString("address"));	    //11
			vpd.put("var12", varOList.get(i).get("add_time").toString());	//12
			vpd.put("var13", varOList.get(i).get("shipping_status").toString());	//13
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
