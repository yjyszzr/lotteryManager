package com.fh.controller.lottery.activitybonus;

import com.fh.common.ProjectConstant;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.activitybonus.ActivityBonusManager;
import com.fh.service.lottery.rechargecard.RechargeCardManager;
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
@RequestMapping(value = "/activitybonus")
public class ActivityBonusController extends BaseController {

	String menuUrl = "activitybonus/list.do"; // 菜单地址(权限用)
	@Resource(name = "activitybonusService")
	private ActivityBonusManager activitybonusService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	@Resource(name="rechargecardService")
	private RechargeCardManager rechargecardService;
	
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增ActivityBonus");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
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

		pd.put("bonus_id", "0");
		pd.put("receive_count", "0");
		pd.put("is_enable", "0");
		pd.put("is_delete", "0");
		pd.put("add_time", DateUtilNew.getCurrentTimeLong());
		activitybonusService.save(pd);
		ACLOG.save("1", "1", "优惠券管理：新增优惠券", "金额："+pd.getString("bonus_amount"));
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ActivityBonus");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activitybonusService.findById(pd);
		activitybonusService.delete(pd);
		ACLOG.save("1", "2", "优惠券管理：删除优惠券", pdOld.toString());
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
		logBefore(logger, Jurisdiction.getUsername() + "修改ActivityBonus");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activitybonusService.findById(pd);
		
		if(String.valueOf(ProjectConstant.Bonus_TYPE_RECHARGE).equals(pd.getString("bonus_type"))) {
//			Number num = Float.parseFloat(pd.getString("recharge_chance"));
//			Integer rechargeChanceInt = num.intValue();
//			NumberFormat numberFormat = NumberFormat.getInstance();  
//			numberFormat.setMaximumFractionDigits(2);    
//			String rechargeChance = numberFormat.format((float) rechargeChanceInt / (float) 100 );  
//			pd.put("recharge_chance", rechargeChance);
		}

		activitybonusService.edit(pd);
		ACLOG.saveByObject("1", "优惠券管理：修改优惠券", pdOld, pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/onAndOnLine")
	public void isStickOrNot(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "置顶或者上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		activitybonusService.edit(pd);
		if(pd.getString("is_enable").equals("1")) {
			ACLOG.save("1","0", "优惠券管理："+pd.getString("bonus_id"), "置为上架");
		}else {
			ACLOG.save("1","0", "优惠券管理："+pd.getString("bonus_id"), "置为下架");
		}
		out.write("success");
		out.close();
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
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
			List<PageData> varList = activitybonusService.list(page); // 列出ActivityBonus列表
			Map<String,String> rechargeCardMap = this.createRechareCardMap();
			mv.setViewName("lottery/activitybonus/activitybonus_list");
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
		List<PageData> rechargeList = rechargecardService.listAll(pd);
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
		List<PageData> bonusList = activitybonusService.queryListByType(pd);
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
		List<PageData> varList = rechargecardService.listAll(pd);
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
		mv.setViewName("lottery/activitybonus/activitybonus_edit");
		List<PageData> rechargeCardList = this.createRechareCardList();
		pd.put("rechargeCardList", rechargeCardList);
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
		pd = activitybonusService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/activitybonus/activitybonus_edit");
		String result = "0";
		if (null != result) {
			int bonus_number_type = Integer.parseInt(result);
			if (bonus_number_type != 0) {
				pd.put("bonus_number_type", 1);
			} else {
				pd.put("bonus_number_type", 0);
			}
		}
		String minAmount = pd.getString("min_goods_amount");
		if (null != minAmount) {
			double min_goods_amount = Double.parseDouble(minAmount);
			if (min_goods_amount != 0.0) {
				pd.put("min_amount", 1);
			} else {
				pd.put("min_amount", 0);
			}
		}
		
		if(String.valueOf(ProjectConstant.Bonus_TYPE_RECHARGE).equals(pd.getString("bonus_type"))) {
			String endTime = pd.getString("end_time");
			pd.put("start_time", Integer.valueOf(endTime));
			
		}
		
		List<PageData> rechargeCardList = this.createRechareCardList();
		Map<String,String> rechargeCardMap = this.createRechareCardMap();
		String rechargeCardId = pd.getString("recharge_card_id");
		pd.put("rechargeCardList", rechargeCardList);
		pd.put("recharge_card_id",rechargeCardMap.get(rechargeCardId));
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ActivityBonus");
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
			activitybonusService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ActivityBonus到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
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
		List<PageData> varOList = activitybonusService.listAll(pd);
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
