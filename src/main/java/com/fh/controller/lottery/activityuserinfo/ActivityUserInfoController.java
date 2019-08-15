package com.fh.controller.lottery.activityuserinfo;

import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.lottery.activityuserinfo.ActivityUserInfoManager;

/** 
 * 说明：活动用户邀请模块
 * 创建人：FH Q313596790
 * 创建时间：2019-08-08
 */
@Controller
@RequestMapping(value="/activityuserinfo")
public class ActivityUserInfoController extends BaseController {
	
	String menuUrl = "activityuserinfo/list.do"; //菜单地址(权限用)
	@Resource(name="activityuserinfoService")
	private ActivityUserInfoManager activityuserinfoService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ActivityUserInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("_id", this.get32UUID());	//主键
		pd.put("id", "0");	//id
		pd.put("user_id", "0");	//userId
		activityuserinfoService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除ActivityUserInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		activityuserinfoService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ActivityUserInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		activityuserinfoService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ActivityUserInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = activityuserinfoService.list(page);	//列出ActivityUserInfo列表
		mv.setViewName("lottery/activityuserinfo/activityuserinfo_list");
		for (int i = 0; i < varList.size(); i++) {
			BigDecimal  history_total_withdrawable_reward = new BigDecimal(varList.get(i).getString("history_total_withdrawable_reward")== "" ?"0":varList.get(i).getString("history_total_withdrawable_reward"));
			BigDecimal  history_invitation_number_reward = new BigDecimal(varList.get(i).getString("history_invitation_number_reward") == "" ?"0":varList.get(i).getString("history_invitation_number_reward"));
			BigDecimal  history_total_return_reward = new BigDecimal(varList.get(i).getString("history_total_return_reward")== "" ?"0":varList.get(i).getString("history_total_return_reward"));
			varList.get(i).put("dwljxf_reward",  history_total_withdrawable_reward.subtract(history_invitation_number_reward).subtract(history_total_return_reward));
		}
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
		mv.setViewName("lottery/activityuserinfo/activityuserinfo_edit");
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
		pd = activityuserinfoService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/activityuserinfo/activityuserinfo_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	@RequestMapping(value="/goDetials")
	public ModelAndView goDetials()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> pdList = activityuserinfoService.findChildInfoByUserId(pd);	//根据ID读取
		mv.setViewName("lottery/activityuserinfo/activityuser_child_info");
		mv.addObject("msg", "edit");
		mv.addObject("pdList", pdList);
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ActivityUserInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			activityuserinfoService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ActivityUserInfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("（用户ID）");	//1
		titles.add("电话");	//2
		titles.add("下级有效用户数量");	//3
		titles.add("邀请人数奖励总计");	//4
		titles.add("消费百分比返利奖总计");	//5
		titles.add("档位累计消费奖励总计");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = activityuserinfoService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("mobile"));	    //2
			vpd.put("var3", varOList.get(i).get("invitation_number").toString());	//3
			vpd.put("var4", varOList.get(i).getString("history_invitation_number_reward"));	    //4
			vpd.put("var5", varOList.get(i).getString("history_total_return_reward"));	    //5
			BigDecimal  history_total_withdrawable_reward = new BigDecimal(varOList.get(i).getString("history_total_withdrawable_reward")== "" ?"0":varOList.get(i).getString("history_total_withdrawable_reward"));
			BigDecimal  history_invitation_number_reward = new BigDecimal(varOList.get(i).getString("history_invitation_number_reward")== "" ?"0":varOList.get(i).getString("history_invitation_number_reward"));
			BigDecimal  history_total_return_reward = new BigDecimal(varOList.get(i).getString("history_total_return_reward")== "" ?"0":varOList.get(i).getString("history_total_return_reward"));
			vpd.put("var6",  history_total_withdrawable_reward.subtract(history_invitation_number_reward).subtract(history_total_return_reward));	    //6
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
