package com.fh.controller.lottery.banner;

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
import com.fh.service.lottery.banner.BannerManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：banner模块 创建人：FH Q313596790 创建时间：2018-04-28
 */
@Controller
@RequestMapping(value = "/banner")
public class BannerController extends BaseController {

	String menuUrl = "banner/list.do"; // 菜单地址(权限用)
	@Resource(name = "bannerService")
	private BannerManager bannerService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Banner");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // 备注1
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);
		pd.put("create_time", DateUtilNew.getCurrentTimeLong()); // 创建时间
		bannerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除Banner");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		bannerService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Banner");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);
		bannerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Banner");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String startTimeStart = pd.getString("startTimeStart");
		if (null != startTimeStart && !"".equals(startTimeStart)) {
			pd.put("startTimeStart1", DateUtilNew.getMilliSecondsByStr(startTimeStart));
		}
		String startTimeEnd = pd.getString("startTimeEnd");
		if (null != startTimeEnd && !"".equals(startTimeEnd)) {
			pd.put("startTimeEnd1", DateUtilNew.getMilliSecondsByStr(startTimeEnd));
		}
		String endTimeStart = pd.getString("endTimeStart");
		if (null != endTimeStart && !"".equals(endTimeStart)) {
			pd.put("endTimeStart1", DateUtilNew.getMilliSecondsByStr(endTimeStart));
		}
		String endTimeEnd = pd.getString("endTimeEnd");
		if (null != endTimeEnd && !"".equals(endTimeEnd)) {
			pd.put("endTimeEnd1", DateUtilNew.getMilliSecondsByStr(endTimeEnd));
		}

		page.setPd(pd);
		List<PageData> varList = bannerService.list(page); // 列出Banner列表
		mv.setViewName("lottery/banner/banner_list");
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
		mv.setViewName("lottery/banner/banner_edit");
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
		pd = bannerService.findById(pd); // 根据ID读取
		pd.put("start_time", DateUtilNew.getCurrentTimeString(Long.parseLong(pd.get("start_time").toString()), DateUtilNew.date_sdf));
		pd.put("end_time", DateUtilNew.getCurrentTimeString(Long.parseLong(pd.get("end_time").toString()), DateUtilNew.date_sdf));
		mv.setViewName("lottery/banner/banner_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Banner");
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
			bannerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出Banner到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("标题"); // 2
		titles.add("图片路径"); // 3
		titles.add("链接"); // 4
		titles.add("链接参数列表(投放资源):  1:文章id;2:活动URL;3:赛事id "); // 5
		titles.add("排序字段"); // 6
		titles.add("是否展示"); // 7
		titles.add("创建时间"); // 8
		titles.add("投放开始时间"); // 9
		titles.add("投放结束时间"); // 10
		dataMap.put("titles", titles);
		List<PageData> varOList = bannerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("banner_name")); // 2
			vpd.put("var3", varOList.get(i).getString("banner_image")); // 3
			vpd.put("var4", varOList.get(i).getString("banner_link")); // 4
			vpd.put("var5", varOList.get(i).getString("banner_param")); // 5
			vpd.put("var6", varOList.get(i).get("banner_sort").toString()); // 6
			vpd.put("var7", varOList.get(i).get("is_show").toString()); // 7
			vpd.put("var8", varOList.get(i).get("create_time").toString()); // 8
			vpd.put("var9", varOList.get(i).get("start_time").toString()); // 9
			vpd.put("var10", varOList.get(i).get("end_time").toString()); // 10
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
