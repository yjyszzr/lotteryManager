package com.fh.controller.order;

import java.io.PrintWriter;
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

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.order.OrderManager;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;


/** 
 * 说明：订单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-20
 */
@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController {
	
	String menuUrl = "order/list.do"; //菜单地址(权限用)
	@Resource(name="orderService")
	private OrderManager orderService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("pgt_order_id", this.get32UUID());	//主键
		orderService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Order");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = orderService.list(page);	//列出Order列表
		mv.setViewName("system/order/order_list");
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
		mv.setViewName("system/order/order_edit");
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
		pd = orderService.findById(pd);	//根据ID读取
		mv.setViewName("system/order/order_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			orderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Order到excel");
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
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		titles.add("备注20");	//20
		titles.add("备注21");	//21
		titles.add("备注22");	//22
		titles.add("备注23");	//23
		titles.add("备注24");	//24
		titles.add("备注25");	//25
		titles.add("备注26");	//26
		titles.add("备注27");	//27
		titles.add("备注28");	//28
		titles.add("备注29");	//29
		titles.add("备注30");	//30
		titles.add("备注31");	//31
		titles.add("备注32");	//32
		titles.add("备注33");	//33
		titles.add("备注34");	//34
		titles.add("备注35");	//35
		titles.add("备注36");	//36
		titles.add("备注37");	//37
		titles.add("备注38");	//38
		titles.add("备注39");	//39
		titles.add("备注40");	//40
		titles.add("备注41");	//41
		titles.add("备注42");	//42
		titles.add("备注43");	//43
		titles.add("备注44");	//44
		titles.add("备注45");	//45
		titles.add("备注46");	//46
		titles.add("备注47");	//47
		titles.add("备注48");	//48
		titles.add("备注49");	//49
		titles.add("备注50");	//50
		titles.add("备注51");	//51
		titles.add("备注52");	//52
		titles.add("备注53");	//53
		titles.add("备注54");	//54
		titles.add("备注55");	//55
		titles.add("备注56");	//56
		titles.add("备注57");	//57
		titles.add("备注58");	//58
		titles.add("备注59");	//59
		titles.add("备注60");	//60
		titles.add("备注61");	//61
		titles.add("备注62");	//62
		titles.add("备注63");	//63
		titles.add("备注64");	//64
		titles.add("备注65");	//65
		titles.add("备注66");	//66
		titles.add("备注67");	//67
		titles.add("备注68");	//68
		titles.add("备注69");	//69
		titles.add("备注70");	//70
		titles.add("备注71");	//71
		titles.add("备注72");	//72
		titles.add("备注73");	//73
		titles.add("备注74");	//74
		titles.add("备注75");	//75
		titles.add("备注76");	//76
		titles.add("备注77");	//77
		titles.add("备注78");	//78
		titles.add("备注79");	//79
		dataMap.put("titles", titles);
		List<PageData> varOList = orderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("pgt_order_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("order_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("order_sn"));	    //3
			vpd.put("var4", varOList.get(i).getString("parent_sn"));	    //4
			vpd.put("var5", varOList.get(i).get("user_id").toString());	//5
			vpd.put("var6", varOList.get(i).get("order_status").toString());	//6
			vpd.put("var7", varOList.get(i).get("shop_id").toString());	//7
			vpd.put("var8", varOList.get(i).get("site_id").toString());	//8
			vpd.put("var9", varOList.get(i).get("store_id").toString());	//9
			vpd.put("var10", varOList.get(i).get("pickup_id").toString());	//10
			vpd.put("var11", varOList.get(i).get("shipping_status").toString());	//11
			vpd.put("var12", varOList.get(i).get("pay_status").toString());	//12
			vpd.put("var13", varOList.get(i).getString("consignee"));	    //13
			vpd.put("var14", varOList.get(i).getString("region_code"));	    //14
			vpd.put("var15", varOList.get(i).getString("region_name"));	    //15
			vpd.put("var16", varOList.get(i).getString("address"));	    //16
			vpd.put("var17", varOList.get(i).getString("address_lng"));	    //17
			vpd.put("var18", varOList.get(i).getString("address_lat"));	    //18
			vpd.put("var19", varOList.get(i).get("receiving_mode").toString());	//19
			vpd.put("var20", varOList.get(i).getString("tel"));	    //20
			vpd.put("var21", varOList.get(i).getString("email"));	    //21
			vpd.put("var22", varOList.get(i).getString("postscript"));	    //22
			vpd.put("var23", varOList.get(i).getString("best_time"));	    //23
			vpd.put("var24", varOList.get(i).get("pay_id").toString());	//24
			vpd.put("var25", varOList.get(i).getString("pay_code"));	    //25
			vpd.put("var26", varOList.get(i).getString("pay_name"));	    //26
			vpd.put("var27", varOList.get(i).getString("pay_sn"));	    //27
			vpd.put("var28", varOList.get(i).get("is_cod").toString());	//28
			vpd.put("var29", varOList.get(i).getString("order_amount"));	    //29
			vpd.put("var30", varOList.get(i).get("order_points").toString());	//30
			vpd.put("var31", varOList.get(i).getString("money_paid"));	    //31
			vpd.put("var32", varOList.get(i).getString("goods_amount"));	    //32
			vpd.put("var33", varOList.get(i).getString("inv_fee"));	    //33
			vpd.put("var34", varOList.get(i).getString("shipping_fee"));	    //34
			vpd.put("var35", varOList.get(i).getString("cash_more"));	    //35
			vpd.put("var36", varOList.get(i).getString("discount_fee"));	    //36
			vpd.put("var37", varOList.get(i).getString("change_amount"));	    //37
			vpd.put("var38", varOList.get(i).getString("shipping_change"));	    //38
			vpd.put("var39", varOList.get(i).getString("surplus"));	    //39
			vpd.put("var40", varOList.get(i).getString("user_surplus"));	    //40
			vpd.put("var41", varOList.get(i).getString("user_surplus_limit"));	    //41
			vpd.put("var42", varOList.get(i).get("bonus_id").toString());	//42
			vpd.put("var43", varOList.get(i).get("shop_bonus_id").toString());	//43
			vpd.put("var44", varOList.get(i).getString("bonus"));	    //44
			vpd.put("var45", varOList.get(i).getString("shop_bonus"));	    //45
			vpd.put("var46", varOList.get(i).get("store_card_id").toString());	//46
			vpd.put("var47", varOList.get(i).getString("store_card_price"));	    //47
			vpd.put("var48", varOList.get(i).get("integral").toString());	//48
			vpd.put("var49", varOList.get(i).getString("integral_money"));	    //49
			vpd.put("var50", varOList.get(i).get("give_integral").toString());	//50
			vpd.put("var51", varOList.get(i).getString("order_from"));	    //51
			vpd.put("var52", varOList.get(i).get("add_time").toString());	//52
			vpd.put("var53", varOList.get(i).get("pay_time").toString());	//53
			vpd.put("var54", varOList.get(i).get("shipping_time").toString());	//54
			vpd.put("var55", varOList.get(i).get("confirm_time").toString());	//55
			vpd.put("var56", varOList.get(i).get("delay_days").toString());	//56
			vpd.put("var57", varOList.get(i).get("order_type").toString());	//57
			vpd.put("var58", varOList.get(i).get("service_mark").toString());	//58
			vpd.put("var59", varOList.get(i).get("send_mark").toString());	//59
			vpd.put("var60", varOList.get(i).get("shipping_mark").toString());	//60
			vpd.put("var61", varOList.get(i).get("buyer_type").toString());	//61
			vpd.put("var62", varOList.get(i).get("evaluate_status").toString());	//62
			vpd.put("var63", varOList.get(i).get("evaluate_time").toString());	//63
			vpd.put("var64", varOList.get(i).get("end_time").toString());	//64
			vpd.put("var65", varOList.get(i).get("is_distrib").toString());	//65
			vpd.put("var66", varOList.get(i).get("distrib_status").toString());	//66
			vpd.put("var67", varOList.get(i).getString("is_show"));	    //67
			vpd.put("var68", varOList.get(i).get("is_delete").toString());	//68
			vpd.put("var69", varOList.get(i).getString("order_data"));	    //69
			vpd.put("var70", varOList.get(i).getString("mall_remark"));	    //70
			vpd.put("var71", varOList.get(i).getString("shop_remark"));	    //71
			vpd.put("var72", varOList.get(i).getString("store_remark"));	    //72
			vpd.put("var73", varOList.get(i).getString("close_reason"));	    //73
			vpd.put("var74", varOList.get(i).get("cash_user_id").toString());	//74
			vpd.put("var75", varOList.get(i).get("last_time").toString());	//75
			vpd.put("var76", varOList.get(i).get("order_cancel").toString());	//76
			vpd.put("var77", varOList.get(i).getString("refuse_reason"));	    //77
			vpd.put("var78", varOList.get(i).get("sub_order_id").toString());	//78
			vpd.put("var79", varOList.get(i).get("is_freebuy").toString());	//79
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
