package com.fh.controller.lottery.activitybonus;

import com.fh.common.ProjectConstant;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.activitybonus.ActivityBonusManager;
import com.fh.service.lottery.activitybonus.ActivityBonusSHManager;
import com.fh.service.lottery.rechargecard.RechargeCardManager;
import com.fh.service.lottery.rechargecard.RechargeCardSHManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：优惠券 创建人：FH Q313596790 创建时间：2018-05-05
 */
@Controller
@RequestMapping(value = "/activityBonusSH")
public class ActivityBonusSHController extends BaseController {

	String menuUrl = "activitybonusSH/list.do"; // 菜单地址(权限用)
	@Resource(name = "activitybonusSHService")
	private ActivityBonusSHManager activitybonusSHService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	@Resource(name="rechargecardSHService")
	private RechargeCardSHManager rechargecardSHService;
	
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增ActivityBonus");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("0".equals(pd.getString("bonus_number_type"))) {
			pd.put("bonus_number", "0");
		} else {
			pd.put("bonus_number", "0");
		}
		if ("0".equals(pd.getString("min_amount"))) {
			pd.put("min_goods_amount", "0");
		} else {
			pd.put("min_goods_amount", pd.getString("min_goods_amount"));
		}
		
		if(String.valueOf(ProjectConstant.Bonus_TYPE_RECHARGE).equals(pd.getString("bonus_type"))) {
//			Number num = Float.parseFloat(pd.getString("recharge_chance"));
//			Integer rechargeChanceInt = num.intValue();
//			NumberFormat numberFormat = NumberFormat.getInstance();  
//			numberFormat.setMaximumFractionDigits(2);    
//			String rechargeChance = numberFormat.format((float) rechargeChanceInt / (float) 100 );  
//			pd.put("recharge_chance", rechargeChance);
			String endTime = pd.getString("end_time");
			pd.put("start_time", 0);
			pd.put("end_time", Integer.valueOf(endTime));
		}else {
			pd.put("recharge_chance", null);
			pd.put("recharge_card_id", null);
		}
		PageData pdRechargeCard = activitybonusSHService.findRechargeCardByRechargeCardId(pd);
		PageData pdRechargeCardMoney = activitybonusSHService.findBonusByRechargeCardId(pd);
		Double doubleRechargeCardMoney = Double.parseDouble(pdRechargeCardMoney.getString("total_bonus_amount"));
		Integer totalMoney = doubleRechargeCardMoney.intValue()+Integer.parseInt(pd.getString("bonus_amount"));
		Double doubleRealValue =  Double.parseDouble(pdRechargeCard.getString("real_value"));
		if(totalMoney > doubleRealValue.intValue()) {
			mv.addObject("msg","优惠券总金额大于大礼包总金额，无法创建！");
			mv.setViewName("save_result");
			return mv;
		}
		pd.put("bonus_id", "0");
		pd.put("receive_count", "0");
		pd.put("is_enable", "0");
		pd.put("is_delete", "0");
		pd.put("add_time", DateUtilNew.getCurrentTimeLong());
		activitybonusSHService.save(pd);
		ACLOG.save("1", "1", "优惠券管理：新增优惠券", "金额："+pd.getString("bonus_amount"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 圣和列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/shlist")
	public ModelAndView shlist(Page page) throws Exception {
//		System.out.println("====== list start ======");
		try {
			logBefore(logger, Jurisdiction.getUsername() + "列表ActivityBonus");
			// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String amountStart = pd.getString("amountStart");
			if (null != amountStart && !"".equals(amountStart)) {
				pd.put("amountStart", amountStart.trim());
			}
			String amountEnd = pd.getString("amountEnd");
			if (null != amountEnd && !"".equals(amountEnd)) {
				pd.put("amountEnd", amountEnd.trim());
			}
			page.setPd(pd);
			List<PageData> varList = activitybonusSHService.list(page); // 列出ActivityBonus列表
			Map<String,String> rechargeCardMap = this.createRechareCardMap();
			mv.setViewName("lottery/activitybonussh/activity_bonus_sh_list");
			if (null != varList)
				for (int i = 0; i < varList.size(); i++) {
					try {
						PageData pageData = new PageData();
						pageData = varList.get(i);
						String min_goods_amount = pageData.getString("min_goods_amount");
						String bonusType =  pageData.getString("bonus_type");
						if(String.valueOf(ProjectConstant.Bonus_TYPE_RECHARGE).equals(bonusType)) {
//						String rechargeChance = pageData.getString("recharge_chance");
//						Number num = Float.parseFloat(rechargeChance) * 100;
//						Integer rechargeChanceInt = num.intValue();
//						pageData.put("recharge_chance", rechargeChanceInt+"%");
							pageData.put("recharge_card_name", rechargeCardMap.get(String.valueOf(pageData.get("recharge_card_id"))));
						}else {
//						pageData.put("recharge_chance", "~");
							pageData.put("recharge_card_name", "~");
						}
						
						if (null != min_goods_amount && !"".equals(min_goods_amount)) {
							if (Double.parseDouble(min_goods_amount) == 0.00) {
								pageData.put("min_goods_amount", "无门槛");
							} else {
								pageData.put("min_goods_amount", "	需消费满" + min_goods_amount + "元");
							}
						}
						String bonus_number = pageData.getString("bonus_number");
						if (null != bonus_number && !"".equals(bonus_number)) {
							if (bonus_number.equals("0")) {
								pageData.put("bonus_number", "无上限");
							}
						}
						String start_time = pageData.getString("start_time");
						if (null != start_time && !"".equals(start_time)) {
							String end_time = pageData.getString("end_time");
							if (null != end_time && !"".equals(end_time)) {
								int value = Integer.parseInt(end_time) - Integer.parseInt(start_time);
								pageData.put("end_time", value);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			
//			System.out.println("====== list end ======");
			return mv;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询充值卡Map
	 * @return
	 * @throws Exception 
	 */
	public Map<String,String> createRechareCardMap() throws Exception {
		PageData pd = new PageData();
		List<PageData> rechargeList = rechargecardSHService.listAll(pd);
		Map<String,String> rechargeMap = new HashMap<String,String>();
		for(PageData pageData:rechargeList) {
			rechargeMap.put(pageData.getString("recharge_card_id"),pageData.getString("name"));
		}
		return rechargeMap;
	}
	
	/**
	 * query Bonus List by actBonusType
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> queryActivityBonusListByActType(Integer bonusType) throws Exception{
		PageData pd = new PageData();
		pd.put("bonus_type", bonusType);
		List<PageData> bonusList = activitybonusSHService.queryListByType(pd);
		return bonusList;
	}
	
	/**
	 * query Bonus Name List by bonusType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getDistributeBonusList")
	@ResponseBody
	public Object queryActivityBonusListForDistribute() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> bonusList = this.queryActivityBonusListByActType(ProjectConstant.Bonus_TYPE_GIVE_USER);
		
		List<PageData> newbonusList = new ArrayList<>();
		bonusList.stream().forEach(s->{
			PageData newPd = new PageData();
			newPd.put("bonus_id", s.getString("bonus_id"));
			newPd.put("min_goods_amount", s.getString("min_goods_amount"));
			newPd.put("bonus_price", s.getString("bonus_amount"));
			newbonusList.add(newPd);
		});
		
		map.put("list", newbonusList);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 查询充值卡List
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> createRechareCardList() throws Exception {
		PageData pd = new PageData();
		List<PageData> varList = rechargecardSHService.listAll(pd);
		List<PageData> pdList = new ArrayList<PageData>();
		for(PageData d :varList){
			PageData pdf = new PageData();
			pdf.put("recharge_card_id", d.getString("recharge_card_id"));
			pdf.put("recharge_card_name", d.getString("name"));
			pdList.add(pdf);
		}
		return pdList;
	}
	
	/**
	 *
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goshAdd")
	public ModelAndView goshAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/activitybonussh/activity_bonus_sh_add");
		List<PageData> rechargeCardList = this.createRechareCardList();
		pd.put("rechargeCardList", rechargeCardList);
		mv.addObject("msg", "save");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ActivityBonus");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {    
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			activitybonusSHService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");   
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ActivityBonus");
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activitybonusSHService.findById(pd);
		activitybonusSHService.delete(pd);
		ACLOG.save("1", "2", "优惠券管理：删除优惠券", pdOld.toString());
		out.write("success");
		out.close();
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ActivityBonus到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("优惠券类型"); // 2
		titles.add("优惠券名称"); // 3
		titles.add("红包活动描述"); // 4
		titles.add("红包图片"); // 5
		titles.add("金额"); // 6
		titles.add("领取次数"); // 7
		titles.add("发放个数"); // 8
		titles.add("使用范围"); // 9
		titles.add("最小订单金额"); // 10
		titles.add("开始时间"); // 11
		titles.add("结束时间"); // 12
		titles.add("是否有效"); // 13
		titles.add("是否删除"); // 14
		titles.add("添加时间"); // 15
		titles.add("兑换所需积分"); // 16
		titles.add("兑换彩票数量"); // 17
		dataMap.put("titles", titles);
		List<PageData> varOList = activitybonusSHService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("bonus_id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("bonus_type").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("bonus_name")); // 3
			vpd.put("var4", varOList.get(i).getString("bonus_desc")); // 4
			vpd.put("var5", varOList.get(i).getString("bonus_image")); // 5
			vpd.put("var6", varOList.get(i).getString("bonus_amount")); // 6
			vpd.put("var7", varOList.get(i).get("receive_count").toString()); // 7
			vpd.put("var8", varOList.get(i).get("bonus_number").toString()); // 8
			vpd.put("var9", varOList.get(i).get("use_range").toString()); // 9
			vpd.put("var10", varOList.get(i).getString("min_goods_amount")); // 10
			vpd.put("var11", varOList.get(i).get("start_time").toString()); // 11
			vpd.put("var12", varOList.get(i).get("end_time").toString()); // 12
			vpd.put("var13", varOList.get(i).get("is_enable").toString()); // 13
			vpd.put("var14", varOList.get(i).get("is_delete").toString()); // 14
			vpd.put("var15", varOList.get(i).get("add_time").toString()); // 15
			vpd.put("var16", varOList.get(i).get("exchange_points").toString()); // 16
			vpd.put("var17", varOList.get(i).get("exchange_goods_number").toString()); // 17
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
