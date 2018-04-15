package com.fh.controller.erp.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.erp.batch.BatchManager;
import com.fh.util.Jurisdiction;
import com.fh.util.MergeUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/** 
 * 说明：批次追溯
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/querybatch")
public class QueryBatch extends BaseController {

	String menuUrl = "querybatch/list.do"; //菜单地址(权限用)
	
	@Resource(name = "batchService")
	private BatchManager batchService;
	
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		
		logBefore(logger, Jurisdiction.getUsername()+"列表Allocation");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String batch_code = pd.getString("batch_code");				//关键词检索条件
		if (null == batch_code || "".equals(batch_code)) {
			mv.setViewName("erp/query/querybatch_list");
			pd.put("first", 1);
			mv.addObject("pd", pd);
			mv.addObject("batch_code", batch_code);
			mv.addObject("checkList", "");
			mv.addObject("boundList", "");
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		PageData queryPurchase = new PageData();
		queryPurchase.put("batch_code", batch_code);
		pd =batchService.findByBatchCode(queryPurchase);
		
		if (pd == null ) {
			mv.setViewName("erp/query/querybatch_list");
			mv.addObject("pd", pd);
			mv.addObject("batch_code", batch_code);
			mv.addObject("checkList", "");
			mv.addObject("boundList", "");
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		List<PageData> pds = new ArrayList<PageData>();
		pds.add(pd);
		RedisUtil.getUserInfoById(pds, "commitby", "NAME", "commitby");
		RedisUtil.getUserInfoById(pds, "auditby", "NAME", "auditby");
		RedisUtil.getUserInfoById(pds, "notice_commitby", "NAME", "notice_commitby");
		RedisUtil.getUserInfoById(pds, "notice_auditby", "NAME", "notice_auditby");
		
		PageData queryCheck = new PageData();
		queryCheck.put("batch_code", batch_code);
		List<PageData> checkList = batchService.listCheckByBatchCode(queryCheck);
		RedisUtil.getUserInfoById(checkList, "createby", "NAME", "createby");
		RedisUtil.getUserInfoById(checkList, "updateby", "NAME", "updateby");
		PageData queryBound = new PageData();
		queryBound.put("batch_code", batch_code);
		List<PageData> inboundList = batchService.listInboundByBatchCode(queryBound);
		List<PageData> outboundList = batchService.listOutBoundByBatchCode(queryBound);
		List<PageData> boundList = MergeUtil.mergeInBoundOutBoundByBatch(pd.getString("inbound_pre_notice_code"),inboundList, outboundList);
		
		mv.setViewName("erp/query/querybatch_list");
		mv.addObject("pd", pds.get(0));
		mv.addObject("batch_code", batch_code);
		mv.addObject("checkList", checkList);
		mv.addObject("boundList", boundList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
