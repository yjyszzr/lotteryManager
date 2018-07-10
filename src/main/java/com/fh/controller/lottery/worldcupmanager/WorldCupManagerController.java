package com.fh.controller.lottery.worldcupmanager;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axis.utils.SessionUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.worldcupcontry.WorldCupContryManager;
import com.fh.service.lottery.worldcupmanager.WorldCupManagerManager;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：世界杯冠亚军推演模块 创建人：FH Q313596790 创建时间：2018-07-09
 */
@Controller
@RequestMapping(value = "/worldcupmanager")
public class WorldCupManagerController extends BaseController {

	String menuUrl = "worldcupmanager/list.do"; // 菜单地址(权限用)
	@Resource(name = "worldcupmanagerService")
	private WorldCupManagerManager worldcupmanagerService;
	@Resource(name = "worldcupcontryService")
	private WorldCupContryManager worldcupcontryService;

	/**
	 * 检查该奖项是否为空
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkThePrizeIsNull")
	public void checkThePrizeIsNull(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "检查是否为空 ----方法为:checkThePrizeIsNull");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<Integer, Map<String, Integer>> cupMap = getWorldCupMap(pd);
		PageData page = worldcupmanagerService.findById(pd);
		Integer value = Integer.parseInt(page.getString("prize_value"));
		if (value != 0) {
			Map<String, Integer> map = cupMap.get(value);
			if (map.isEmpty()) {
				out.write("false");
			} else {
				out.write("true");
			}
		} else {
			boolean flag = true;
			for (Integer key : cupMap.keySet()) {
				if (cupMap.get(key).isEmpty()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				out.write("true");
			} else {
				out.write("false");
			}
		}
		out.close();
	}

	/**
	 * 计算并更新用户中奖状态
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUserRewardStatus")
	public void updateUserRewardStatus(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "更新用户中奖状态");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = worldcupmanagerService.findById(pd);
		Map<Integer, Map<String, Integer>> map = getWorldCupMap(pd);
		worldcupmanagerService.updateUserRewardStatus(pd, map);
		out.write("success");
		out.close();
	}

	/**
	 * 派奖给用户
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/rewardToUser")
	public void rewardToUser(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "派奖给用户");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupmanagerService.rewardToUser(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 开奖给用户
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/openThePrize")
	public ModelAndView openThePrize() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "开奖给用户");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		SessionUtils.generateSession();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USERROL);
		// 世界杯真实赛果
		Map<Integer, Map<String, Integer>> worldCupResultMap = getWorldCupMap(pd);
		PageData pdForAverage = worldcupmanagerService.findById(pd);
		// 获取该奖项的总金额 根据人数计算平均中奖金额
		BigDecimal big = new BigDecimal(pdForAverage.getString("quota"));
		BigDecimal bigNum = new BigDecimal(pd.getString("people_num"));
		BigDecimal average = big.divide(bigNum, 2);
		pd.put("average", average);// 设置平均值
		pd.put("prize_value", pdForAverage.getString("prize_value"));// 设置奖项枚举值
		pd.put("audit_time", DateUtilNew.getCurrentTimeLong());// 审核时间
		pd.put("auditor_id", user.getUSER_ID());// 审核人ID
		pd.put("auditor", user.getUSERNAME());// 审核人名称
		worldcupmanagerService.openThePrize(pd, worldCupResultMap);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	private Map<Integer, Map<String, Integer>> getWorldCupMap(PageData pd) throws Exception {
		List<PageData> listAll = worldcupcontryService.listAll(pd);
		Map<Integer, Map<String, Integer>> cupMap = new HashMap<Integer, Map<String, Integer>>();
		Map<String, Integer> contry16Map = new HashMap<String, Integer>();
		Map<String, Integer> contry8Map = new HashMap<String, Integer>();
		Map<String, Integer> contry4Map = new HashMap<String, Integer>();
		Map<String, Integer> contry2Map = new HashMap<String, Integer>();
		Map<String, Integer> contry1Map = new HashMap<String, Integer>();
		for (int i = 0; i < listAll.size(); i++) {
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_16"))) {
				contry16Map.put(listAll.get(i).getString("is_16"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_8"))) {
				contry8Map.put(listAll.get(i).getString("is_8"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_4"))) {
				contry4Map.put(listAll.get(i).getString("is_4"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_2"))) {
				contry2Map.put(listAll.get(i).getString("is_2"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_1"))) {
				contry1Map.put(listAll.get(i).getString("is_1"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
		}
		cupMap.put(1, contry1Map);
		cupMap.put(2, contry2Map);
		cupMap.put(4, contry4Map);
		cupMap.put(8, contry8Map);
		cupMap.put(16, contry16Map);
		return cupMap;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表WorldCupManager");
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
		List<PageData> varList = worldcupmanagerService.list(page); // 列出WorldCupManager列表
		for (int i = 0; i < varList.size(); i++) {
			Integer prizeValue = Integer.parseInt(varList.get(i).getString("prize_value"));
			PageData pada = new PageData();
			pada.put("prize_value", prizeValue);
			logger.info("prizeValue=========================================================================================" + prizeValue);
			Integer num = Integer.parseInt(StringUtil.isEmptyStr(varList.get(i).getString("people_num")) ? "0" : varList.get(i).getString("people_num"));
			if (num == null || num == 0) {
				Integer peopleNum = worldcupmanagerService.findPeopleNumByStatus(pada);
				varList.get(i).put("people_num", peopleNum);
				BigDecimal big = new BigDecimal(varList.get(i).getString("quota"));
				if (peopleNum == 0 || peopleNum == null) {
					varList.get(i).put("average", 0);
				} else {
					BigDecimal bigPeopleNum = new BigDecimal(peopleNum.toString());
					varList.get(i).put("average", big.divide(bigPeopleNum, 2).doubleValue());
				}
			}
		}
		mv.setViewName("lottery/worldcupmanager/worldcupmanager_list");
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
		pd = worldcupmanagerService.findById(pd); // 根据ID读取
		Integer prizeValue = Integer.parseInt(pd.getString("prize_value"));
		PageData pada = new PageData();
		pada.put("prize_value", prizeValue);
		Integer peopleNum = worldcupmanagerService.findPeopleNumByStatus(pada);
		pd.put("people_num", peopleNum);
		BigDecimal big = new BigDecimal(pd.getString("quota"));
		if (peopleNum == 0) {
			pd.put("average", 0);
		} else {
			BigDecimal bigPeopleNum = new BigDecimal(peopleNum.toString());
			pd.put("average", big.divide(bigPeopleNum, 2).doubleValue());
		}
		mv.setViewName("lottery/worldcupmanager/worldcupmanager_edit");
		mv.addObject("msg", "openThePrize");
		mv.addObject("pd", pd);
		return mv;
	}

}
