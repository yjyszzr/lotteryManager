package com.fh.controller.lottery.complain;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.fh.service.lottery.complain.ComplainManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：投诉建议 创建人：FH Q313596790 创建时间：2018-05-06
 */
@Controller
@RequestMapping(value = "/complain")
public class ComplainController extends BaseController {

	String menuUrl = "complain/list.do"; // 菜单地址(权限用)
	@Resource(name = "complainService")
	private ComplainManager complainService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Complain");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("complainer", "0"); // 投诉者
		pd.put("complain_time", "0"); // 投诉时间
		pd.put("complain_content", ""); // 投诉内容
		pd.put("is_read", "0"); // 是否已读
		complainService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/toReadOrUnread")
	public void toReadOrUnread(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "置顶或者上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		complainService.edit(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Complain");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		complainService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Complain");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdForComplain = new PageData();
		pdForComplain = complainService.findById(pd); // 根据ID读取
		List<ReplyEntity> replyEntityList = new ArrayList<ReplyEntity>();
		if (null != JSONObject.parseArray(pdForComplain.getString("reply"), ReplyEntity.class)) {
			replyEntityList = JSONObject.parseArray(pdForComplain.getString("reply"), ReplyEntity.class);
		}
		ReplyEntity replyEntity = new ReplyEntity();
		replyEntity.setReply(pd.getString("reply"));
		replyEntity.setTime(DateUtilNew.getCurrentTimeLong());
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		replyEntity.setUserName(user.getUSERNAME());
		replyEntityList.add(replyEntity);
		pd.put("reply", list2Json(replyEntityList).toString());
		complainService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	public static JSONArray list2Json(List<ReplyEntity> list) {
		JSONArray json = new JSONArray();
		for (ReplyEntity rEntity : list) {
			JSONObject jo = new JSONObject();
			jo.put("userName", rEntity.getUserName());
			jo.put("time", rEntity.getTime());
			jo.put("reply", rEntity.getReply());
			json.add(rEntity);
		}
		return json;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Complain");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String complain_content = pd.getString("complain_content");
		if (null != complain_content && !"".equals(complain_content)) {
			pd.put("complain_content", complain_content.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		page.setPd(pd);
		List<PageData> varList = complainService.list(page); // 列出Complain列表
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			List<ReplyEntity> replyEntityList = new ArrayList<ReplyEntity>();
			replyEntityList = JSONObject.parseArray(pageData.getString("reply"), ReplyEntity.class);
			varList.get(i).put("replyEntityList", replyEntityList);
		}
		mv.setViewName("lottery/complain/complain_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/complain/complain_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = complainService.findById(pd); // 根据ID读取
		List<ReplyEntity> varList = new ArrayList<ReplyEntity>();
		varList = JSONObject.parseArray(pd.getString("reply"), ReplyEntity.class);
		pd.put("varList", varList);
		mv.setViewName("lottery/complain/complain_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Complain");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			complainService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Complain到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("投诉者"); // 2
		titles.add("投诉时间"); // 3
		titles.add("投诉内容"); // 4
		titles.add("是否已读"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = complainService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("complainer").toString()); // 2
			vpd.put("var3", varOList.get(i).get("complain_time").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("complain_content")); // 4
			vpd.put("var5", varOList.get(i).get("is_read").toString()); // 5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
