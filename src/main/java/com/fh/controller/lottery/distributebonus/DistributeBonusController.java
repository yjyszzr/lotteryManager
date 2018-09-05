package com.fh.controller.lottery.distributebonus;

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
import com.fh.service.lottery.distributebonus.DistributeBonusManager;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;

/** 
 * 说明：派发红包管理
 * 创建人：FH Q313596790
 * 创建时间：2018-09-03
 */
@Controller
@RequestMapping(value="/distributebonus")
public class DistributeBonusController extends BaseController {
	
	String menuUrl = "distributebonus/list.do"; //菜单地址(权限用)
	@Resource(name="distributebonusService")
	private DistributeBonusManager distributebonusService;
	
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DistributeBonus");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("chooseOne");
        pd.put("bonus_id", pd.getString("selectBonus"));
        pd.put("status", "0");
        pd.put("add_time", DateUtilNew.getCurrentTimeLong());
        pd.put("add_user", user.getNAME());
        pd.put("type", type);
        if("1".equals(type)) {
        	PageData queryPd = new PageData();
        	queryPd.put("mobile", Long.valueOf(pd.getString("receiver")));
	       	PageData userPd = usermanagercontrollerService.queryUserByMobile(queryPd);
	       	if(null == userPd) {
	    		mv.addObject("msg","没有该用户,请核查");
	    		mv.setViewName("save_result");
	    		return mv;
	       	}
	       	String userId = userPd.getString("user_id");
	       	pd.put("user_id", Integer.valueOf(userId));
	       	pd.put("receiver",pd.getString("receiver"));
        }else if("2".equals(type)) {
        	pd.put("file_url", pd.getString("file_url"));
        }
		distributebonusService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DistributeBonus");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		distributebonusService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DistributeBonus");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		distributebonusService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DistributeBonus");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = distributebonusService.list(page);	//列出DistributeBonus列表
		mv.setViewName("lottery/distributebonus/distributebonus_list");
		for(PageData one:varList) {
			String fileUrl = one.getString("file_url");
			one.put("file_url", fileUrl.substring(fileUrl.lastIndexOf("/")));
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
		mv.setViewName("lottery/distributebonus/distributebonus_edit");
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
		pd = distributebonusService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/distributebonus/distributebonus_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DistributeBonus");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			distributebonusService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DistributeBonus到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("活动红包id");	//2
		titles.add("接收人手机号");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("派发状态");	//6
		titles.add("添加时间");	//7
		titles.add("提交人");	//8
		titles.add("审核时间");	//9
		titles.add("审核人");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = distributebonusService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).get("bonus_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("receiver").toString());	//3
			vpd.put("var4", varOList.get(i).get("user_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("file_url"));	    //5
			vpd.put("var6", varOList.get(i).get("status").toString());	//6
			vpd.put("var7", varOList.get(i).get("add_time").toString());	//7
			vpd.put("var8", varOList.get(i).getString("add_user"));	    //8
			vpd.put("var9", varOList.get(i).get("pass_time").toString());	//9
			vpd.put("var10", varOList.get(i).getString("pass_user"));	    //10
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
