package com.fh.controller.lottery.articleinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.article.ArticleControllerManager;
import com.fh.service.lottery.articleinfo.ArticleInfoManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：渠道文章汇总 创建人：FH Q313596790 创建时间：2018-06-27
 */
@Controller
@RequestMapping(value = "/articleinfo")
public class ArticleInfoController extends BaseController {

	String menuUrl = "articleinfo/list.do"; // 菜单地址(权限用)
	@Resource(name = "articleinfoService")
	private ArticleInfoManager articleinfoService;

	@Resource(name = "articlecontrollerService")
	private ArticleControllerManager articlecontrollerService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate")
	public ModelAndView saveOrUpdate() throws Exception {
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
			pd.put("click_number", 0);
			pd.put("article_id", 0);
			pd.put("match_id", 0);
			articlecontrollerService.save(pd);
			pd.getString("article_info_id");
			if (!StringUtil.isEmptyStr(pd.getString("article_info_id"))) {
				articleinfoService.updateStatus(Integer.parseInt(pd.getString("article_info_id")));
			}
		} else {
			articlecontrollerService.edit(pd);
		}
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ArticleInfo");
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
		List<PageData> varList = articleinfoService.list(page); // 列出ArticleInfo列表
		mv.setViewName("lottery/articleinfo/articleinfo_list");
		for (int i = 0; i < varList.size(); i++) {
			String str = varList.get(i).getString("content");
			varList.get(i).put("contentSub", str);
		}
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
		pd = articleinfoService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/articleinfo/articleinfo_edit");
		mv.addObject("msg", "saveOrUpdate");
		pd.put("list_style", 0);
		String str = pd.getString("content");
		str = str.replaceAll("\"", "”");
		pd.put("content", str.replaceAll("\'", "’"));
		mv.addObject("pd", pd);
		return mv;
	}
}
