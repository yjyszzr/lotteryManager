package com.fh.controller.lottery.rechargecard;

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
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.lottery.rechargecard.RechargeCardManager;

/** 
 * 说明：RechargeCard
 * 创建人：FH Q313596790
 * 创建时间：2018-08-21
 */
@Controller
@RequestMapping(value="/rechargecard")
public class RechargeCardController extends BaseController {
	
	String menuUrl = "rechargecard/list.do"; //菜单地址(权限用)
	@Resource(name="rechargecardService")
	private RechargeCardManager rechargecardService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增RechargeCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("recharge_card_id_id", this.get32UUID());	//主键
		pd.put("is_delete", "0");	
		pd.put("type", "0");	//充值卡类型
		pd.put("add_user", user.getNAME());
		pd.put("add_time", DateUtilNew.getCurrentTimeLong());
		pd.put("img_url", "");
		pd.put("status", "0");		
		rechargecardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**获取连级数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getRechargeCardList")
	@ResponseBody
	public Object getRechargeCardList() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = rechargecardService.listAll(pd);
		List<PageData> pdList = new ArrayList<PageData>();
		for(PageData d :varList){
			PageData pdf = new PageData();
			pdf.put("recharge_card_id", d.getString("recharge_card_id"));
			pdf.put("recharge_card_name", d.getString("name"));
			pdList.add(pdf);
		}
		map.put("list", pdList);	
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除RechargeCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		rechargecardService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改RechargeCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		rechargecardService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表RechargeCard");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = rechargecardService.list(page);	//列出RechargeCard列表
		for(PageData pageData:varList) {
			pageData.put("add_time", DateUtilNew.getCurrentTimeString(Long.valueOf(String.valueOf(pageData.get("add_time"))), DateUtilNew.datetimeFormat));
			if("0".equals(String.valueOf(pageData.get("type")))) {
				pageData.put("type", "充值赠红包类型 ");
			}
		}
		
		mv.setViewName("lottery/rechargecard/rechargecard_list");
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
		mv.setViewName("lottery/rechargecard/rechargecard_edit");
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
		pd = rechargecardService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/rechargecard/rechargecard_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除RechargeCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			rechargecardService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出RechargeCard到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("充值卡名称");	//2
		titles.add("备注3");	//3
		titles.add("充值卡状态");	//4
		titles.add("添加人");	//5
		titles.add("添加时间");	//6
		titles.add("充值卡描述");	//7
		titles.add("备注8");	//8
		titles.add("实际价值");	//9
		titles.add("充值卡类型");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = rechargecardService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("recharge_card_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("name"));	    //2
			vpd.put("var3", varOList.get(i).getString("img_url"));	    //3
			vpd.put("var4", varOList.get(i).get("status").toString());	//4
			vpd.put("var5", varOList.get(i).getString("add_user"));	    //5
			vpd.put("var6", varOList.get(i).getString("add_time"));	    //6
			vpd.put("var7", varOList.get(i).getString("description"));	    //7
			vpd.put("var8", varOList.get(i).get("is_delete").toString());	//8
			vpd.put("var9", varOList.get(i).getString("real_value"));	    //9
			vpd.put("var10", varOList.get(i).getString("type"));	    //10
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
