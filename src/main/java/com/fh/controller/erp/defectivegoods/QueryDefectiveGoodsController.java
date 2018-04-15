package com.fh.controller.erp.defectivegoods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.erp.defectivegoods.DefectiveGoodsManager;
import com.fh.service.erp.defectivegoodsdetail.DefectiveGoodsDetailManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * 说明：不良品表 创建人：FH Q313596790 创建时间：2017-10-11
 */
@Controller
@RequestMapping(value = "/querydefectivegoods")
public class QueryDefectiveGoodsController extends BaseController {

	String menuUrl = "querydefectivegoods/list.do"; // 菜单地址(权限用)
	
	@Resource(name = "defectivegoodsService")
	private DefectiveGoodsManager defectivegoodsService;

	@Resource(name = "defectivegoodsdetailService")
	private DefectiveGoodsDetailManager defectivegoodsdetailService;
	
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
		}
		String defective_code = pd.getString("defective_code"); // 关键词检索条件
		if (null != defective_code && !"".equals(defective_code)) {
			pd.put("defective_code", defective_code.trim());
		}
		page.setPd(pd);
		List<PageData> varList = defectivegoodsService.list(page); // 列出DefectiveGoods列表
		if(CollectionUtils.isNotEmpty(varList)) {
			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
		}
		mv.setViewName("erp/query/querydefectivegoods_list");
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

	@RequestMapping(value="/detail")
	public ModelAndView detail(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AllocationDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData query = new PageData();
		query.put("defective_code", pd.get("defective_code"));
		List<PageData>	varList = defectivegoodsdetailService.listByCode(query);	//列出AllocationDetail列表
		mv.setViewName("erp/query/querydefectivegoodsdetail_list");
		
		pd = defectivegoodsService.findByCode(query);
		List<PageData>	userList = new ArrayList<>();
		userList.add(pd);
		RedisUtil.getUserInfoById(userList, "commitby", "NAME", "commitby");
		RedisUtil.getUserInfoById(userList, "auditby", "NAME", "auditby");
		mv.addObject("varList", varList);
		mv.addObject("pd", userList.get(0));
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

}
