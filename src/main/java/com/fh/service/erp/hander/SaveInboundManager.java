package com.fh.service.erp.hander;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fh.entity.system.User;
import com.fh.util.PageData;

import net.sf.json.JSONArray;

/** 
 * 说明： 入库单管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface SaveInboundManager{
	
	public void saveInboundHandler(String inbound_notice_code, String in_code) throws Exception;
	
	public void saveInboundDataHandler(PageData pd) throws Exception;
	
	public void saveDirectSales(String direct_sales_code, User user) throws Exception;
	/**
	 * 2017-10-09 修改到货逻辑
	 * */
	@Deprecated
	public void saveQualityStoreHandler(PageData pd, String inbound_notice_code) throws Exception;

	public void saveQuality(String basepath, HttpServletRequest request, PageData pageData)throws Exception;
	
	public void saveArrivedHandler(PageData pd, String inbound_notice_code) throws Exception;
	
	public void saveSubmit(JSONArray jsonArray) throws Exception;

	public Map<String, String> saveFinishInboundHandler(Map<String, String> map, PageData pd) throws Exception;
}

