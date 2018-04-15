package com.fh.controller.dst.otheroutstorage;

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
import com.fh.service.dst.otheroutstorage.OtherOutstorageManager;
import com.fh.service.dst.otheroutstoragedetail.OtherOutstorageDetailManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：其他出库
 * 创建人：FH Q313596790
 * 创建时间：2017-09-20
 */
@Controller
@RequestMapping(value="/queryotheroutstorage")
public class QueryOtherOutstorageController extends BaseController {
	
	String menuUrl = "queryotheroutstorage/list.do"; //菜单地址(权限用)
	
	@Resource(name="otheroutstorageService")
	private OtherOutstorageManager otheroutstorageService;

	@Resource(name="otheroutstoragedetailService")
	private OtherOutstorageDetailManager otheroutstoragedetailService;

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherOutstorage");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_GIFT,StoreTypeConstants.STORE_LOSS};
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
        List<String> storeSnList = new ArrayList<String>();
		for(int i = 0 ;i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
			page.setPd(pd);
			List<PageData>	varList = otheroutstorageService.querylist(page);	//列出OtherOutstorage列表
			if(CollectionUtils.isNotEmpty(varList)) {
				RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
				RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    		}
			mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/query/queryotheroutstorage_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		query.put("other_outstorage_code", pd.get("other_outstorage_code"));

		List<PageData>	varList = otheroutstoragedetailService.listByCode(query);	//列出AllocationDetail列表
		mv.setViewName("erp/query/queryotheroutstoragedetail_list");
		
		pd = otheroutstorageService.findByCode(query);
		List<PageData>	userList = new ArrayList<>();
		userList.add(pd);
		RedisUtil.getUserInfoById(userList, "commitby", "NAME", "commitby");
		RedisUtil.getUserInfoById(userList, "auditby", "NAME", "auditby");
		RedisUtil.getUserInfoById(userList, "verifyby", "NAME", "verifyby");
		mv.addObject("varList", varList);
		mv.addObject("pd", userList.get(0));
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
