package com.fh.controller.lottery.banner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.TextConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.banner.BannerManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
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
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = varList.get(i);
			String imgValue = pageData.getString("banner_image");
			pageData.put("banner_image", TextConfig.URL_SHOW_IMG_CODE + imgValue);
		}
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
		pd.put("banner_image_show", TextConfig.URL_SHOW_IMG_CODE + pd.getString("banner_image"));
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
	 * 上线下线
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/onOrOffLine")
	public void onOrOffLine(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "下线或者上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		bannerService.updateByKey(pd);
		out.write("success");
		out.close();
	}

}
