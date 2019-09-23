package com.fh.controller.lottery.distributebonus;

import com.alibaba.druid.util.StringUtils;
import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.param.BonusParam;
import com.fh.entity.system.User;
import com.fh.enums.SNBusinessCodeEnum;
import com.fh.service.lottery.activitybonus.ActivityBonusManager;
import com.fh.service.lottery.activitybonus.impl.ActivityBonusSHService;
import com.fh.service.lottery.distributebonus.DistributeBonusManager;
import com.fh.service.lottery.rechargecardaccountrelation.impl.RechargeCardAccountRelationService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 说明：派发红包管理
 * 创建人：FH Q313596790
 * 创建时间：2018-09-03
 */
@Controller
@RequestMapping(value="/distributebonus")
public class DistributeBonusController extends BaseController {
	
	String menuUrl = "distributebonus/list.do"; //菜单地址(权限用)
	
	String menuUrl2 = "distributebonus/approvelist.do"; //菜单地址(权限用)
	@Resource(name="distributebonusService")
	private DistributeBonusManager distributebonusService;
	
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	
	@Resource(name = "activitybonusService")
	private ActivityBonusManager activitybonusService;

    @Resource(name = "activitybonusSHService")
    public ActivityBonusSHService activityBonusSHService;

    @Resource(name = "rechargeCardAccountRelationService")
    public RechargeCardAccountRelationService rechargeCardAccountRelationService;

	@Resource(name = "urlConfig")
	private URLConfig urlConfig;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DistributeBonus");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("chooseOne");
        pd.put("bonus_id", pd.getString("selectBonus"));
        pd.put("bonus_name", pd.getString("sname"));
        pd.put("add_time", DateUtilNew.getCurrentTimeLong());
        pd.put("add_user", user.getNAME());
        pd.put("type", type);
        if("1".equals(type)) {
            PageData mPd = new PageData();
            mPd.put("mobile",pd.getString("receiver"));
            mPd.put("app_code_name","11");
	       	PageData userPd = usermanagercontrollerService.queryUserByMobile(mPd);
	       	if(null == userPd) {
	    		mv.addObject("msg","没有该用户,请核查");
	    		mv.setViewName("save_result");
	    		return mv;
	       	}
	       	String userId = userPd.getString("user_id");
	       	pd.put("user_id", Integer.valueOf(userId));
	       	pd.put("receiver",pd.getString("receiver").trim());
        }else if("2".equals(type)) {
        	pd.put("file_url", pd.getString("file_url"));
        }

        Integer rSize = 0;
        try{
            rSize = this.donationBonusList(pd);
        }catch(Exception exception){
            pd.put("status", "2");
            logger.error("赠送大礼包异常:"+exception.getLocalizedMessage());
        }
        pd.put("status", "1");
        pd.put("bonus_num", rSize);
        distributebonusService.save(pd);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

    /**
     * 赠送大礼包对应的红包集合
     */
	public Integer donationBonusList(PageData pageData) throws Exception{
        String rechargeCardId = pageData.getString("selectBonus");
        String realValue = pageData.getString("real_value");
        List<Integer> bonusIdList = new ArrayList<>();
        PageData pdRid = new PageData();
        pdRid.put("rechargeCardId",rechargeCardId);
        List<PageData> bonusDataList = activityBonusSHService.queryActBonusByRechargeCardId(pdRid);
        bonusDataList.forEach(s->{
            String bonusId = s.getString("bonus_id");
            bonusIdList.add(Integer.valueOf(bonusId));
        });

        List<Integer> userIdList = new ArrayList<>();
        Integer rSize = 0;
        String receiver = pageData.getString("receiver");
        if(StringUtils.isEmpty(receiver)) {
            userIdList = this.resolveUserBonusExcel(pageData.getString("file_url"));
            rSize = userIdList.size();
        }else {
            PageData mPd = new PageData();
            mPd.put("mobile",receiver);
            mPd.put("app_code_name","11");
            PageData userPd = usermanagercontrollerService.queryUserByMobile(mPd);
            String userId = userPd.getString("user_id");
            userIdList.add(Integer.valueOf(userId));
            rSize = 1;
        }

        for(Integer bonusId:bonusIdList){
            BonusParam bp = this.createBonusParam(bonusId);
            int rst = this.giveUserBonus(userIdList, bp);
        }

        //赠送礼包管理表中增加记录
        PageData rPd = new PageData();
        for(Integer userId:userIdList){
            rPd.put("account_sn",userId);
            rPd.put("recharge_card_id",rechargeCardId);
            rPd.put("recharge_card_real_value",0);//不需要存储这个值
            rechargeCardAccountRelationService.save(rPd);
        }

        return rSize;
    }
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除DistributeBonus");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		distributebonusService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DistributeBonus");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		distributebonusService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DistributeBonus");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		page.setPd(pd);
		List<PageData>	varList = distributebonusService.list(page);
		mv.setViewName("lottery/distributebonus/distributebonus_list");
		List<PageData> list = this.dealList(varList);
		mv.addObject("varList", list);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/approvelist")
	public ModelAndView approvelist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DistributeBonus");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		page.setPd(pd);
		List<PageData>	varList = distributebonusService.list(page);
		mv.setViewName("lottery/distributebonus/distributebonusapprove_list");
		List<PageData> list = this.dealList(varList);
		mv.addObject("varList", list);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	public List<PageData> dealList(List<PageData> varList) throws Exception{
		for(PageData one:varList) {
			if("1".equals(one.getString("type"))) {
				one.put("file_url", "");
				one.put("file_name", "");
			}else if("2".equals(one.getString("type"))) {
				String fileUrl = one.getString("file_url");
				one.put("file_url",urlConfig.getImgShowUrl()+fileUrl);
				one.put("file_name", "点击下载查看 : "+fileUrl.substring(fileUrl.lastIndexOf("/")+1));
			}
			one.put("add_time", DateUtilNew.getCurrentTimeString(Long.valueOf(one.getString("add_time")), DateUtilNew.datetimeFormat));
			if(!StringUtils.isEmpty(one.getString("pass_time"))) {
				one.put("pass_time", DateUtilNew.getCurrentTimeString(Long.valueOf(one.getString("pass_time")), DateUtilNew.datetimeFormat));
			}else {
				one.put("pass_time", "");
			}
			one.put("status", one.getString("status"));//ApproveStatusEnums.getNameByCode(Integer.valueOf(
			PageData queryP = new PageData();
			queryP.put("bonus_id", one.getString("bonus_id"));
			PageData actBonus = activitybonusService.findById(queryP);
			if(null !=actBonus) {
				one.put("bonus_id", one.getString("bonus_id")+"(满"+actBonus.getString("min_goods_amount")+"元减"+actBonus.getString("bonus_amount")+"元)");
			}
		}
		return varList;
	}
	
	@RequestMapping(value="/approveGiveBonus")
	@ResponseBody
	public Object approveGiveBonus() throws Exception{
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		pd = this.getPageData();
		if("2".equals(pd.getString("type"))) {//拒绝
			pd.put("status", "2");
			pd.put("bonus_num", 0);
			pd.put("pass_user", user.getUSERNAME());
			pd.put("pass_time", DateUtilNew.getCurrentTimeLong());
			distributebonusService.edit(pd);
			map.put("code", "2");
			map.put("msg", "审核拒绝");
			return AppUtil.returnObject(pd, map);
		}
		
		PageData distributePd = distributebonusService.findById(pd);
		Integer bonusId = Integer.valueOf(distributePd.getString("bonus_id"));
		BonusParam bp = this.createBonusParam(bonusId);
		String receiver = distributePd.getString("receiver");
		List<Integer> userIdList = new ArrayList<>();
		if(StringUtils.isEmpty(receiver)) {
			userIdList = this.resolveUserBonusExcel(distributePd.getString("file_url"));
		}else {
			PageData userPd = usermanagercontrollerService.queryUserByMobile(receiver);
			String userId = userPd.getString("user_id");
			userIdList.add(Integer.valueOf(userId));
		}
		int rst = this.giveUserBonus(userIdList, bp);
		if(1 == rst) {
			distributePd.put("bonus_num", userIdList.size());
			distributePd.put("status", "1");
			distributePd.put("pass_user", user.getUSERNAME());
			distributePd.put("pass_time", DateUtilNew.getCurrentTimeLong());
			distributebonusService.edit(distributePd);
			map.put("code", "1");
			map.put("msg", "审核通过");
		}else{
			map.put("code", "0");
			map.put("msg", "审核失败，请联系管理员");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 根据红包id 创建赠送的红包param
	 * @param bonusId
	 * @return
	 * @throws Exception
	 */
	public BonusParam createBonusParam(Integer bonusId) throws Exception {
		BonusParam bp =new BonusParam();
		Date todayDate  = new Date();
		PageData pd = new PageData();
		pd.put("bonus_id", bonusId);
		PageData actBonus = activityBonusSHService.findById(pd);
	
		bp.setBonusId(bonusId);
		bp.setBonusSn(SNGenerator.nextSN(SNBusinessCodeEnum.BONUS_SN.getCode()));
		BigDecimal b = new BigDecimal(actBonus.getString("bonus_amount")); 
		bp.setBonusPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP));
		BigDecimal bMin = new BigDecimal(actBonus.getString("min_goods_amount")); 
		bp.setMinGoodsAmount(bMin.setScale(2, BigDecimal.ROUND_HALF_UP));
		bp.setReceiveTime(DateUtilNew.getCurrentTimeLong());
		bp.setAddTime(DateUtilNew.getCurrentTimeLong());
		bp.setStartTime(DateUtilNew.getTimeAfterDays(todayDate, Integer.valueOf(actBonus.getString("start_time")), 0, 0, 0));
		bp.setEndTime(DateUtilNew.getTimeAfterDays(todayDate, Integer.valueOf(actBonus.getString("end_time")), 23, 59, 59));
		bp.setBonusStatus(0);
		bp.setIsDelete(0);
		bp.setIsRead(0);
		return bp;
	}
	
	/**
	 * 解析红包的excel,直接去取excel中的userId
	 * @return
	 * @throws Exception 
	 */
	public List<Integer> resolveUserBonusExcel(String bonusExcelUrl) {
		List<Integer> userIdList = new ArrayList<>();
        File file=new File(urlConfig.getUploadCommonFileUrl()+bonusExcelUrl);
        try {
        	XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
        	Sheet sheet=workbook.getSheetAt(0);//读取第一个 sheet
        	Row row=null;
        	int count=sheet.getPhysicalNumberOfRows();
        	//逐行处理 excel 数据
        	NumberFormat nf = NumberFormat.getInstance();
        	for (int i = 0; i < count; i++) {
        		row=sheet.getRow(i);
        		Cell cell0 = row.getCell(0);
        		String data = nf.format(cell0.getNumericCellValue());
        		if (data.indexOf(",") >= 0) {
        			data = data.replaceAll(",", "");
        		}
        		if(!StringUtils.isEmpty(data)) {
        			userIdList.add(Integer.valueOf(data));
        		}
        		
        	}
        	workbook.close();
		} catch (Exception e) {
			System.out.println("eeeeeeeeeeeeeeeee=="+e);
			  logger.error("excel解析异常================:"+e.getLocalizedMessage());
		}
		return userIdList;
	}

	/**
	 * 给用户派发红包
	 * @throws Exception 
	 */
	public int giveUserBonus(List<Integer> userIdlist,BonusParam bonusParam) throws Exception {
		int rst = activitybonusService.batchInsertUserBonus(userIdlist, bonusParam);
		return rst;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/distributebonus/distributebonus_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = distributebonusService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/distributebonus/distributebonus_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DistributeBonus");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			distributebonusService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出DistributeBonus到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<2;i++){
			PageData vpd = new PageData();
			vpd.put("var1", "11位数字类型手机号格式"+i);
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
