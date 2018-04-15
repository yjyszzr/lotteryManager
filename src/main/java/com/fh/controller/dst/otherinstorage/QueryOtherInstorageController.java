package com.fh.controller.dst.otherinstorage;

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
import com.fh.service.dst.otherinstorage.OtherInstorageManager;
import com.fh.service.dst.otherinstoragedetail.OtherInstorageDetailManager;
import com.fh.service.dst.scrap.ScrapManager;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：其他入库单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 */
@Controller
@RequestMapping(value="/queryotherinstorage")
public class QueryOtherInstorageController extends BaseController {
	
	String menuUrl = "queryotherinstorage/list.do"; //菜单地址(权限用)
	
	@Resource(name="otherinstorageService")
	private OtherInstorageManager otherinstorageService;

	@Resource(name="otherinstoragedetailService")
	private OtherInstorageDetailManager otherinstoragedetailService;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherInstorage");
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
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null || "".equals(pd.get("stores"))) {
            	pd.put("store_sn", "");
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
			page.setPd(pd);
			List<PageData>	varList = otherinstorageService.querylist(page);	//列出OtherInstorage列表
			if(CollectionUtils.isNotEmpty(varList)) {
				RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
				RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
    		}
			mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/query/queryotherinstorage_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	@RequestMapping(value="/detail")
	public ModelAndView detail(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AllocationDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData query = new PageData();
		query.put("other_instorage_code", pd.get("other_instorage_code"));

		List<PageData>	varList = otherinstoragedetailService.listByCode(query);	//列出AllocationDetail列表
		mv.setViewName("erp/query/queryotherinstoragedetail_list");
		
		pd = otherinstorageService.findByCode(query);
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
}
