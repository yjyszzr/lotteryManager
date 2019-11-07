package com.fh.controller.lottery.article;

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

import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.information.pictures.PicturesManager;
import com.fh.service.lottery.article.ArticleControllerManager;
import com.fh.service.lottery.articleclassify.ArticleClassifyManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 说明：内容管理 创建人：FH Q313596790 创建时间：2018-04-18
 */
@Controller
@RequestMapping(value = "/articlecontroller")
public class ArticleControllerController extends BaseController {

	String menuUrl = "articlecontroller/list.do"; // 菜单地址(权限用)
	@Resource(name = "articlecontrollerService")
	private ArticleControllerManager articlecontrollerService;

	@Resource(name = "picturesService")
	private PicturesManager picturesService;

	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;

	@Resource(name = "articleclassifyService")
	private ArticleClassifyManager articleclassifyService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate")
	public ModelAndView save() throws Exception {
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername() + "新增ArticleController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("content", pd.getString("content"));
		pd.put("is_delete", 0);
		pd.put("add_time", DateUtilNew.getCurrentTimeLong()); // 备注1
		if ("1".equals(pd.get("list_style")) || "4".equals(pd.get("list_style"))) {
			pd.put("article_thumb", pd.get("article_thumb1"));

		} else if ("3".equals(pd.get("list_style"))) {
			String articleThumbAll = pd.get("article_thumb1") + "," + pd.get("article_thumb2") + "," + pd.get("article_thumb3");
			pd.put("article_thumb", articleThumbAll);
		}
		if (pd.get("article_id") == "" || "".equals(pd.get("article_id"))) {
			pd.put("is_show", 0);
			pd.put("is_stick", 0);
//			pd.put("click_number", 0);
			pd.put("article_id", 0);
			pd.put("match_id", 0);
			articlecontrollerService.save(pd);
			ACLOG.save("1", "1", "文章管理：" + pd.getString("title"), "标题：" + pd.getString("title"));
		} else {
			PageData pdOld = articlecontrollerService.findById(pd);
			articlecontrollerService.edit(pd);
			ACLOG.saveByObject("1", "文章管理：" + pdOld.getString("title"), pdOld, pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 预览
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/preLook")
	public ModelAndView preLook() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增ArticleController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		articlecontrollerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ArticleController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("app_code_name", 11);
		String article_id = pd.getString("article_id");
		if (null != article_id && !"".equals(article_id)) {
			pd.put("article_id", article_id.trim());
		}
		String title = pd.getString("title");
		if (null != title && !"".equals(title)) {
			pd.put("title", title.trim());
		}
		String author = pd.getString("author");
		if (null != author && !"".equals(author)) {
			pd.put("author", author.trim());
		}
		if (null != pd.get("match_id") && !"".equals(pd.get("match_id"))) {
			int matchId = Integer.parseInt(pd.get("match_id").toString());
			if (matchId != 0) {
				pd.put("match_id", matchId);
			} else {
				pd.put("match_id", "");
			}
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
		List<PageData> varList = articlecontrollerService.list(page); // 列出ArticleController列表
		mv.setViewName("lottery/article/articlecontroller_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 表单构建页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	@ResponseBody
	public ModelAndView saveFormbuilder() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.addObject("msg", "saveOrUpdate");
		List<PageData> articleclassifyList = articleclassifyService.listAll(pd);
		mv.addObject("articleclassifyList", articleclassifyList);
		mv.setViewName("lottery/article/articlecontroller_edit");
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
		pd = articlecontrollerService.findById(pd); // 根据ID读取
		Object obj = pd.get("article_thumb");
		String articleThumbArr = null;
		if (obj != null) {
			articleThumbArr = obj.toString();
			String[] strArray = null;
			strArray = articleThumbArr.split(",");
			if (strArray.length == 1) {
				pd.put("article_thumb1", strArray[0]);
				pd.put("article_thumb1_show", urlConfig.getImgShowUrl() + strArray[0]);// 单图做展示用
			} else if (strArray.length > 1) {
				for (int i = 0; i < strArray.length; i++) {
					pd.put("article_thumb" + (i + 1), strArray[i]);
					pd.put("article_thumb" + (i + 1) + "_show", urlConfig.getImgShowUrl() + strArray[i]);// 三张图做展示用
				}
			}
		}
		mv.setViewName("lottery/article/articlecontroller_edit");
		mv.addObject("msg", "saveOrUpdate");
		List<PageData> articleclassifyList = articleclassifyService.listAll(pd);
		mv.addObject("articleclassifyList", articleclassifyList);
		mv.addObject("pd", pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ArticleController");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = articlecontrollerService.findById(pd);
		articlecontrollerService.delete(pd);
		ACLOG.save("1", "2", "文章管理：" + pdOld.getString("title"), pdOld.getString("article_id"));
		out.write("success");
		out.close();
	}

	/**
	 * 置顶和上线
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/stickAndOnLine")
	public void isStickOrNot(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "置顶或者上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = articlecontrollerService.findById(pd);
		pd.put("stick_time", DateUtilNew.getCurrentTimeLong());
		articlecontrollerService.updateByKey(pd);
		if (pd.get("status") == null) {
			if (pd.getString("is_stick").equals("0")) {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "取消置顶");
			} else {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "置顶");
			}
		} else {
			if (pd.getString("status").equals("1")) {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "上架");
			}
			if (pd.getString("status").equals("2") && !pdOld.getString("status").equals("4")) {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "下架");
			}
			if (pd.getString("status").equals("4")) {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "过期");
			}
			if (pd.getString("status").equals("2") && pdOld.getString("status").equals("4")) {
				ACLOG.save("1", "0", "文章管理：" + pdOld.getString("title"), "恢复");
			}

		}
		out.write("success");
		out.close();
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ArticleController");
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
			articlecontrollerService.deleteAll(ArrayDATA_IDS);
			ACLOG.save("1", "2", "文章管理：批量删除", "id：" + DATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 后端预览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toPreShow")
	@ResponseBody
	public Object toPreShow() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		pd = articlecontrollerService.findById(pd);
		map.put("pd", pd);
		return map;
	}
}
