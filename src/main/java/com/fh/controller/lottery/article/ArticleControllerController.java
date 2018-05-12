package com.fh.controller.lottery.article;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.TextConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.information.pictures.PicturesManager;
import com.fh.service.lottery.article.ArticleControllerManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
//import com.fh.util.DateUtilNew;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;

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

		} else if ("2".equals(pd.get("list_style"))) {
			String articleThumbAll = pd.get("article_thumb1") + "," + pd.get("article_thumb2") + "," + pd.get("article_thumb3");
			pd.put("article_thumb", articleThumbAll);
		}
		if (pd.get("article_id") == "" || "".equals(pd.get("article_id"))) {
			pd.put("is_show", 0);
			pd.put("is_stick", 0);
			pd.put("click_number", 0);
			pd.put("article_id", 0);
			pd.put("match_id", 0);
			articlecontrollerService.save(pd);
		} else {
			articlecontrollerService.edit(pd);
		}
		// map.put("result", "true");
		// map.put("msg", "success");
		// return map;
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
		// pd.put("article_id", this.get32UUID()); //主键
		// pd.put("article_id", "0"); //备注1
		articlecontrollerService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/edit") public ModelAndView edit() throws
	 * Exception { logBefore(logger, Jurisdiction.getUsername() +
	 * "修改ArticleController"); if (!Jurisdiction.buttonJurisdiction(menuUrl,
	 * "edit")) { return null; } // 校验权限 ModelAndView mv =
	 * this.getModelAndView(); PageData pd = new PageData(); pd =
	 * this.getPageData(); articlecontrollerService.edit(pd);
	 * mv.addObject("msg", "success"); mv.setViewName("save_result"); return mv;
	 * }
	 */

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
				pd.put("article_thumb1_show", TextConfig.URL_SHOW_IMG_CODE + strArray[0]);// 单图做展示用
			} else if (strArray.length > 1) {
				for (int i = 0; i < strArray.length; i++) {
					pd.put("article_thumb" + (i + 1), strArray[i]);
					pd.put("article_thumb" + (i + 1) + "_show", TextConfig.URL_SHOW_IMG_CODE + strArray[i]);// 三张图做展示用
				}
			}
		}
		mv.setViewName("lottery/article/articlecontroller_edit");
		mv.addObject("msg", "saveOrUpdate");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 上传缩略图
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPic")
	@ResponseBody
	public Object save(@RequestParam(required = false) MultipartFile file) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增图片");
		Map<String, String> map = new HashMap<String, String>();
		String ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID()); // 执行上传
			} else {
				System.out.println("上传失败");
			}
			pd.put("PICTURES_ID", this.get32UUID()); // 主键
			pd.put("TITLE", "图片"); // 标题
			pd.put("NAME", fileName); // 文件名
			pd.put("PATH", ffile + "/" + fileName); // 路径
			pd.put("CREATETIME", Tools.date2Str(new Date())); // 创建时间
			pd.put("MASTER_ID", "1"); // 附属与
			pd.put("BZ", "图片管理处上传"); // 备注
			Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);// 加水印
			picturesService.save(pd);
		}
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
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
		articlecontrollerService.delete(pd);
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
		articlecontrollerService.updateByKey(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ArticleController到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("文章标题"); // 2
		titles.add("备注3"); // 3
		titles.add("文章内容"); // 4
		titles.add("备注5"); // 5
		titles.add("备注6"); // 6
		titles.add("备注7"); // 7
		titles.add("备注8"); // 8
		titles.add("备注9"); // 9
		titles.add("备注10"); // 10
		titles.add("备注11"); // 11
		titles.add("备注12"); // 12
		titles.add("备注13"); // 13
		titles.add("备注14"); // 14
		titles.add("备注15"); // 15
		titles.add("备注16"); // 16
		titles.add("备注17"); // 17
		titles.add("备注18"); // 18
		titles.add("备注19"); // 19
		titles.add("备注20"); // 20
		titles.add("备注21"); // 21
		titles.add("备注22"); // 22
		titles.add("备注23"); // 23
		titles.add("备注24"); // 24
		titles.add("备注25"); // 25
		titles.add("备注26"); // 26
		titles.add("备注27"); // 27
		titles.add("备注28"); // 28
		titles.add("备注29"); // 29
		titles.add("备注30"); // 30
		dataMap.put("titles", titles);
		List<PageData> varOList = articlecontrollerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("article_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("title")); // 2
			vpd.put("var3", varOList.get(i).get("cat_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("content")); // 4
			vpd.put("var5", varOList.get(i).getString("keywords")); // 5
			vpd.put("var6", varOList.get(i).getString("jurisdiction")); // 6
			vpd.put("var7", varOList.get(i).getString("article_thumb")); // 7
			vpd.put("var8", varOList.get(i).get("add_time").toString()); // 8
			vpd.put("var9", varOList.get(i).get("is_comment").toString()); // 9
			vpd.put("var10", varOList.get(i).get("click_number").toString()); // 10
			vpd.put("var11", varOList.get(i).get("is_show").toString()); // 11
			vpd.put("var12", varOList.get(i).get("user_id").toString()); // 12
			vpd.put("var13", varOList.get(i).get("status").toString()); // 13
			vpd.put("var14", varOList.get(i).getString("link")); // 14
			vpd.put("var15", varOList.get(i).getString("source")); // 15
			vpd.put("var16", varOList.get(i).getString("summary")); // 16
			vpd.put("var17", varOList.get(i).getString("extend_cat")); // 17
			vpd.put("var18", varOList.get(i).get("is_recommend").toString()); // 18
			vpd.put("var19", varOList.get(i).getString("author")); // 19
			vpd.put("var20", varOList.get(i).get("match_id").toString()); // 20
			vpd.put("var21", varOList.get(i).getString("related_team")); // 21
			vpd.put("var22", varOList.get(i).get("list_style").toString()); // 22
			vpd.put("var23", varOList.get(i).get("is_original").toString()); // 23
			vpd.put("var24", varOList.get(i).get("is_delete").toString()); // 24
			vpd.put("var25", varOList.get(i).get("is_stick").toString()); // 25
			vpd.put("var26", varOList.get(i).get("stick_time").toString()); // 26
			vpd.put("var27", varOList.get(i).getString("price")); // 27
			vpd.put("var28", varOList.get(i).get("level").toString()); // 28
			vpd.put("var29", varOList.get(i).get("article_pv").toString()); // 29
			vpd.put("var30", varOList.get(i).get("article_uv").toString()); // 30
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
