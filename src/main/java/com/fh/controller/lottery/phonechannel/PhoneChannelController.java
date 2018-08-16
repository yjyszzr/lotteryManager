package com.fh.controller.lottery.phonechannel;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.fh.service.lottery.articleclassify.ArticleClassifyManager;
import com.fh.service.lottery.phonechannel.PhoneChannelManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：APP渠道管理 创建人：FH Q313596790 创建时间：2018-08-14
 */
@Controller
@RequestMapping(value = "/phonechannel")
public class PhoneChannelController extends BaseController {

	String menuUrl = "phonechannel/list.do"; // 菜单地址(权限用)
	@Resource(name = "phonechannelService")
	private PhoneChannelManager phonechannelService;
	@Resource(name = "articleclassifyService")
	private ArticleClassifyManager articleclassifyService;
	@Resource(name = "userActionLogService")
	private UserActionLogService userActionLogService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增PhoneChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		phonechannelService.save(pd);
		userActionLogService.save("1", "1", "APP资讯渠道管理", "添加移动端渠道:" + pd.getString("channel_name"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除PhoneChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = phonechannelService.findById(pd);
		phonechannelService.delete(pd);
		userActionLogService.save("1", "1", "APP资讯渠道管理", "删除APP资讯渠道:" + pd.toString());
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
		logBefore(logger, Jurisdiction.getUsername() + "修改PhoneChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pdOld = new PageData();
		PageData pd = new PageData();
		pd = this.getPageData();
		pdOld = phonechannelService.findById(pd);
		phonechannelService.edit(pd);
		userActionLogService.saveByObject("1", "APP资讯渠道管理,修改App资讯渠道:" + pd.getString("channel_name"), pdOld, pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表PhoneChannel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = phonechannelService.list(page); // 列出PhoneChannel列表
		List<PageData> articleclassifyList = articleclassifyService.listAll(pd);
		Map<String, PageData> pageDataMap = new HashMap<String, PageData>();
		articleclassifyList.forEach(s -> pageDataMap.put(s.getString("id"), s));
		mv.setViewName("lottery/phonechannel/phonechannel_list");
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = varList.get(i);
			if (!pageData.getString("article_classify_ids").equals("0")) {
				List<PageData> articleclassifys = new ArrayList<PageData>();
				String str = pageData.getString("article_classify_ids");
				List<String> resultStr = Arrays.asList(str.split(","));
				for (int j = 0; j < resultStr.size(); j++) {
					PageData resultPageData = pageDataMap.get(resultStr.get(j));
					if (resultPageData != null) {
						articleclassifys.add(resultPageData);
					}
				}
				varList.get(i).put("articleclassifyList", articleclassifys);
			} else {
				varList.get(i).put("articleclassifyList", articleclassifyList);
			}
		}
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
		mv.setViewName("lottery/phonechannel/phonechannel_edit");
		List<PageData> articleclassifyList = articleclassifyService.listAll(pd);
		mv.addObject("articleclassifyList", articleclassifyList);
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
		pd = phonechannelService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/phonechannel/phonechannel_edit");
		String str = pd.getString("article_classify_ids");
		Map<String, PageData> pageDataMap = new HashMap<String, PageData>();

		List<PageData> articleclassifyList = articleclassifyService.listAll(pd);
		articleclassifyList.forEach(s -> pageDataMap.put(s.getString("id"), s));
		List<String> resultStr = Arrays.asList(str.split(","));
		for (int i = 0; i < articleclassifyList.size(); i++) {
			PageData resultPageData = articleclassifyList.get(i);
			articleclassifyList.get(i).put("isCheck", 0);
			for (int j = 0; j < resultStr.size(); j++) {
				if (resultPageData.getString("id").equals(resultStr.get(j))) {
					articleclassifyList.get(i).put("isCheck", 1);
				}
			}
		}
		mv.addObject("articleclassifyList", articleclassifyList);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除PhoneChannel");
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
			phonechannelService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出PhoneChannel到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("渠道编号"); // 2
		titles.add("渠道名称"); // 3
		titles.add("app编码"); // 4
		titles.add("资讯类型"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = phonechannelService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("channel")); // 2
			vpd.put("var3", varOList.get(i).getString("channel_name")); // 3
			vpd.put("var4", varOList.get(i).get("app_code_name").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("article_classify_ids")); // 5
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
