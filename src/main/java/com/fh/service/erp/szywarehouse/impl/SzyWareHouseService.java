package com.fh.service.erp.szywarehouse.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.service.erp.szywarehouse.SzyWareHouseManager;
import com.fh.util.PageData;

@Service("szyWareHouseService")
public class SzyWareHouseService implements SzyWareHouseManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	@Override
	public int save(String storeId, String storeName) throws Exception {
		PageData pd = new PageData();
		pd.put("warehouse_code", storeId);
		PageData obj = (PageData)dao.findForObject("SzyWareHouseMapper.findByCode", pd);
		if(obj == null || StringUtils.isBlank(obj.getString("warehouse_id"))) {
			pd.put("warehouse_name", storeName);
			int rst = (int)dao.save("SzyWareHouseMapper.save", pd);
			return rst;
		}
		return 1;
	}

}
