package com.fh.controller.erp.defectivegoods;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.DefetiveConstants;
import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.JsonUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.defectivegoods.DefectiveGoodsManager;
import com.fh.service.erp.defectivegoodsdetail.DefectiveGoodsDetailManager;
import com.fh.service.erp.hander.SaveOutboundNoticeManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.service.stockbatch.impl.StockBatchService;
import com.fh.service.system.user.UserManager;

/**
 * 说明：不良品表 创建人：FH Q313596790 创建时间：2017-10-11
 */
@Controller
@RequestMapping(value = "/defectivegoods")
public class DefectiveGoodsController extends BaseController {

	String menuUrl = "defectivegoods/list.do"; // 菜单地址(权限用)
	@Resource(name = "defectivegoodsService")
	private DefectiveGoodsManager defectivegoodsService;
	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name = "defectivegoodsdetailService")
	private DefectiveGoodsDetailManager defectivegoodsdetailService;
    @Resource(name="userService")
	private UserManager userService;
	
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("defective_id", this.get32UUID()); //主键
		pd.put("status", DefetiveConstants.BUSINESS_STATUS_UNSUBMIT);
		pd.put("auditby", ""); // 审批人
		User user = getSessionUser();
		pd.put("createby", user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time", curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
		defectivegoodsService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		defectivegoodsService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("updateby", getSessionUser().getUSER_ID());
		pd.put("update_time", new Date());
		defectivegoodsService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {

		logBefore(logger, Jurisdiction.getUsername() + "删除DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		boolean checkexists = defectivegoodsdetailService.existDetail(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkexists) {
			pd.put("status", DefetiveConstants.BUSINESS_STATUS_SUBMIT);
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			defectivegoodsService.updateStatus(pd);
			map.put("msg", "success");
		} else {
			map.put("msg", "failse");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
		out.close();
	}

	@RequestMapping(value = "/commit")
	public void commit(PrintWriter out) throws Exception {

		logBefore(logger, Jurisdiction.getUsername() + "删除DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		boolean checkexists = defectivegoodsdetailService.existDetail(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkexists) {
			pd.put("status", DefetiveConstants.BUSINESS_STATUS_SUBMIT);
			pd.put("updateby", getSessionUser().getUSER_ID());
			pd.put("update_time", new Date());
			pd.put("commitby", getSessionUser().getUSER_ID());
			pd.put("commit_time", new Date());
			defectivegoodsService.commit(pd);
			map.put("msg", "success");
		} else {
			map.put("msg", "failse");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表DefectiveGoods");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String[] types = { StoreTypeConstants.STORE_GOOD };
		List<PageData> storeList = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < storeList.size(); i++) {
			storeSnList.add(storeList.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
		String store_sn = pd.getString("store_sn"); // 关键词检索条件
		if (null != store_sn && !"".equals(store_sn)) {
			pd.put("store_sn", store_sn.trim());
		} else {
			pd.put("store_sn", "");
//			if (storeList.size() > 0) {
//				PageData pageTemp = storeList.get(0);
//				pd.put("store_sn", pageTemp.getString("store_sn"));
//
//			} else {
//				pd.put("store_sn", "0");
//			}
		}
		String defective_code = pd.getString("defective_code"); // 关键词检索条件
		if (null != defective_code && !"".equals(defective_code)) {
			pd.put("defective_code", defective_code.trim());
		}
		page.setPd(pd);
		List<PageData> varList = defectivegoodsService.list(page); // 列出DefectiveGoods列表
		if(CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(var->{
				PageData varPd = new PageData();
				varPd.put("USER_ID", var.getString("auditby"));
				try {
					PageData pageData = userService.findById(varPd);
					if(pageData != null) {
						var.put("auditby", pageData.getString("NAME"));
    				}
				} catch (Exception e) {
				}
				varPd = new PageData();
				varPd.put("USER_ID", var.getString("commitby"));
				try {
					PageData pageData = userService.findById(varPd);
					if(pageData != null) {
						var.put("commitby", pageData.getString("NAME"));
    				}
				} catch (Exception e) {
				}
			});
		}
		mv.setViewName("erp/defectivegoods/defectivegoods_list");
		mv.addObject("storeList", storeList);
		mv.addObject("varList", varList);
		String status = pd.getString("status");
		if (null == status || "".equals(status)) {
			pd.put("status", "-1");
		}
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
		String code = getSerialConfigUtilService.getSerialCode(FormType.DEFECTIVE_CODE);
		pd = this.getPageData();
		mv.setViewName("erp/defectivegoods/defectivegoods_edit");
		mv.addObject("msg", "save");
		// 默认数据
		pd.put("defective_code", code);
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
		PageData pdquery = new PageData();
		pdquery = this.getPageData();
		
		PageData pd = defectivegoodsService.findById(pdquery); // 根据ID读取
		
		
		mv.setViewName("erp/defectivegoods/defectivegoods_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除DefectiveGoods");
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
			defectivegoodsService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出DefectiveGoods到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("备注2"); // 2
		titles.add("备注3"); // 3
		titles.add("备注4"); // 4
		titles.add("备注5"); // 5
		titles.add("备注6"); // 6
		titles.add("备注7"); // 7
		titles.add("备注8"); // 8
		titles.add("备注9"); // 9
		titles.add("备注10"); // 10
		titles.add("备注11"); // 11
		titles.add("备注12"); // 12
		dataMap.put("titles", titles);
		List<PageData> varOList = defectivegoodsService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("defective_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("defective_code")); // 2
			vpd.put("var3", varOList.get(i).getString("store_sn")); // 3
			vpd.put("var4", varOList.get(i).getString("store_name")); // 4
			vpd.put("var5", varOList.get(i).get("status").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("remark")); // 6
			vpd.put("var7", varOList.get(i).getString("auditby")); // 7
			vpd.put("var8", varOList.get(i).getString("audit_time")); // 8
			vpd.put("var9", varOList.get(i).getString("createby")); // 9
			vpd.put("var10", varOList.get(i).getString("create_time")); // 10
			vpd.put("var11", varOList.get(i).getString("updateby")); // 11
			vpd.put("var12", varOList.get(i).getString("update_time")); // 12
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

	@Resource(name = "saveoutboundnoticeservice")
	private SaveOutboundNoticeManager saveoutboundnoticeservice;

	// 驳回
	@RequestMapping(value = "/notchecked")
	public ModelAndView notchecked() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("auditby", user.getUSERNAME());
		pd.put("audit_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		pd.put("status", DefetiveConstants.BUSINESS_STATUS_REJECT);
		defectivegoodsService.updateCheckStatus(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	// 审核通过
	@ResponseBody
	@RequestMapping(value = "/checked")
	public Map<String, String> checked() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		logBefore(logger, Jurisdiction.getUsername() + "修改DefectiveGoods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限

		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			saveoutboundnoticeservice.saveCompleteDefective(pd);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "fail");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 审核列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkList")
	public ModelAndView checkList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表DefectiveGoods");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String[] types = { StoreTypeConstants.STORE_GOOD };
		List<PageData> storeList = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < storeList.size(); i++) {
			storeSnList.add(storeList.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
		String store_sn = pd.getString("store_sn"); // 关键词检索条件
		if (null != store_sn && !"".equals(store_sn)) {
			pd.put("store_sn", store_sn.trim());
		} else {
			pd.put("store_sn", "");
//			if (storeList.size() > 0) {
//				PageData pageTemp = storeList.get(0);
//				pd.put("store_sn", pageTemp.getString("store_sn"));
//			} else {
//				pd.put("store_sn", "0");
//			}
		}

		page.setPd(pd);
		List<PageData> varList = defectivegoodsService.checkList(page); // 列出DefectiveGoods列表
		if(CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(var->{
				PageData varPd = new PageData();
				varPd.put("USER_ID", var.getString("auditby"));
				try {
					PageData pageData = userService.findById(varPd);
					if(pageData != null) {
						var.put("auditby", pageData.getString("NAME"));
    				}
				} catch (Exception e) {
				}
				varPd = new PageData();
				varPd.put("USER_ID", var.getString("commitby"));
				try {
					PageData pageData = userService.findById(varPd);
					if(pageData != null) {
						var.put("commitby", pageData.getString("NAME"));
    				}
				} catch (Exception e) {
				}
			});
		}
		mv.setViewName("erp/defectivegoods/defectivegoods_list_check");
		mv.addObject("storeList", storeList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
