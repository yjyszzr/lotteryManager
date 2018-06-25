package com.fh.controller.lottery.questionsandanswers;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.footballmatchlottery.FootballMatchLotteryManager;
import com.fh.service.lottery.questionsandanswers.QuestionsAndAnswersManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：竞猜答题 创建人：FH Q313596790 创建时间：2018-06-15
 */
@Controller
@RequestMapping(value = "/questionsandanswers")
public class QuestionsAndAnswersController extends BaseController {

	String menuUrl = "questionsandanswers/list.do"; // 菜单地址(权限用)
	@Resource(name = "questionsandanswersService")
	private QuestionsAndAnswersManager questionsandanswersService;
	@Resource(name = "footballmatchlotteryService")
	private FootballMatchLotteryManager footballmatchlotteryService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增QuestionsAndAnswers");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("match_id", "0"); // 赛事Id
		pd.put("scope_of_activity", "0"); // 所属活动
		questionsandanswersService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		questionsandanswersService.updateStatus(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 更新问题答案
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(GuessingCompetitionEntity guessingCompetitionEntity) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "updateQuestionsAndAnswers");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// list转成json
		if (pd.getString("status").equals("2")) {
			pd.put("answer_show_time", DateUtilNew.getCurrentTimeLong());
		}
		String questionAndAnswersJson = JSONArray.fromObject(guessingCompetitionEntity.getQuestionAndAnswersEntityList()).toString();
		pd.put("question_and_answer", questionAndAnswersJson);
		// pd.put("num_of_people", guessingCompetitionEntity.getNumOfPeople());
		questionsandanswersService.updateQuestionsAndAnswers(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 派奖
	 * 
	 * @param guessingCompetitionEntity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAward")
	public ModelAndView updateAward() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "发布答案,比对用户答案,并且给用户派奖");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// list转成json
		if (pd.getString("status").equals("3")) {
			pd.put("award_time", DateUtilNew.getCurrentTimeLong());
		}
		questionsandanswersService.updateAward(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除QuestionsAndAnswers");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		questionsandanswersService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(GuessingCompetitionEntity guessingCompetitionEntity) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "添加QuestionsAndAnswers");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// list转成json
		String questionAndAnswersJson = JSONArray.fromObject(guessingCompetitionEntity.getQuestionAndAnswersEntityList()).toString();
		pd.put("id", 0);
		pd.put("question_and_answer", questionAndAnswersJson);
		// pd.put("num_of_people", 0);
		String endTime = pd.getString("endTime");
		if (null != endTime && !"".equals(endTime)) {
			pd.put("end_time", DateUtilNew.getMilliSecondsByStr(endTime));
		}
		String startTime = pd.getString("startTime");
		if (null != startTime && !"".equals(startTime)) {
			pd.put("start_time", DateUtilNew.getMilliSecondsByStr(startTime));
		}
		pd.put("create_time", DateUtilNew.getCurrentTimeLong().toString());
		pd.put("period", DateUtilNew.getCurrentyyyyMMdd() + "期");
		questionsandanswersService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表QuestionsAndAnswers");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("guessing_title"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("guessing_title", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = questionsandanswersService.list(page); // 列出QuestionsAndAnswers列表
		mv.setViewName("lottery/questionsandanswers/questionsandanswers_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("currentTime", DateUtilNew.getCurrentTimeLong());
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	// @RequestMapping(value = "/goAdd")
	// public ModelAndView goAdd() throws Exception {
	// ModelAndView mv = this.getModelAndView();
	// PageData pd = new PageData();
	// pd = this.getPageData();
	// mv.setViewName("lottery/questionsandanswers/questionsandanswers_edit");
	// mv.addObject("msg", "save");
	// mv.addObject("pd", pd);
	// return mv;
	// }

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit(Integer matchId) throws Exception {
		PageData matchPd = new PageData();
		matchPd.put("match_id", matchId);
		matchPd = footballmatchlotteryService.findById(matchPd);
		PageData questionsAndAnswers = questionsandanswersService.findByMatchId(matchId);
		ModelAndView mv = this.getModelAndView();
		// PageData pd = new PageData();
		// pd = this.getPageData();
		// pd = questionsandanswersService.findById(pd); // 根据ID读取
		if (questionsAndAnswers != null) {
			JSONArray jsonArray = JSONArray.fromObject(questionsAndAnswers.getString("question_and_answer"));
			List<QuestionAndAnswersEntity> questionAndAnswerList = (List<QuestionAndAnswersEntity>) JSONArray.toCollection(jsonArray, QuestionAndAnswersEntity.class);
			if (questionsAndAnswers.getString("status").equals("0")) {
				// 草稿状态 如果是草稿状态 跳转到草稿状态 可再次编辑 也可上线
				mv.setViewName("lottery/questionsandanswers/questionsandanswers_draft");
				mv.addObject("msg", "update");
			} else if (questionsAndAnswers.getString("status").equals("1")) {
				// 发布(上线) 如果是发布状态 跳转到 回填答案页面(答案只可编辑一次)
				mv.setViewName("lottery/questionsandanswers/questionsandanswers_backfillanswers");
				mv.addObject("msg", "update");
			} else if (questionsAndAnswers.getString("status").equals("2")) {
				// 答案回填 点击结束到派奖状态
				Integer num = questionsandanswersService.findAwardNumByQuestionsId(Integer.parseInt(questionsAndAnswers.getString("id")));
				questionsAndAnswers.put("prizewinning_num", num);
				mv.addObject("msg", "updateAward");
				mv.setViewName("lottery/questionsandanswers/questionsandanswers_award");
			} else if (questionsAndAnswers.getString("status").equals("3")) {
				// 答案 回填 跳转到点击结束到完成状态
				mv.setViewName("lottery/questionsandanswers/questionsandanswers_detail");
			}
			mv.addObject("pd", questionsAndAnswers);
			mv.addObject("questionAndAnswerList", questionAndAnswerList);
		} else {
			mv.setViewName("lottery/questionsandanswers/questionsandanswers_edit");
			mv.addObject("msg", "add");
		}
		mv.addObject("matchPd", matchPd);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除QuestionsAndAnswers");
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
			questionsandanswersService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出QuestionsAndAnswers到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("赛事Id"); // 2
		titles.add("起始时间"); // 3
		titles.add("结束时间"); // 4
		titles.add("所属活动"); // 5
		titles.add("最低参与金额"); // 6
		titles.add("奖池"); // 7
		titles.add("竞猜题"); // 8
		titles.add("人数"); // 9
		dataMap.put("titles", titles);
		List<PageData> varOList = questionsandanswersService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("match_id").toString()); // 2
			vpd.put("var3", varOList.get(i).get("start_time").toString()); // 3
			vpd.put("var4", varOList.get(i).get("end_time").toString()); // 4
			vpd.put("var5", varOList.get(i).get("scope_of_activity").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("limit_lottery_amount")); // 6
			vpd.put("var7", varOList.get(i).getString("bonus_pool")); // 7
			vpd.put("var8", varOList.get(i).getString("question_and_answer")); // 8
			vpd.put("var9", varOList.get(i).get("num_of_people").toString()); // 9
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
