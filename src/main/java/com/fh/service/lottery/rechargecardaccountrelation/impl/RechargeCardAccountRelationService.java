package com.fh.service.lottery.rechargecardaccountrelation.impl;

import com.fh.dao.DaoSupport3;
import com.fh.service.lottery.rechargecardaccountrelation.RechargeCardAccountRelationManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("rechargeCardAccountRelationService")
public class RechargeCardAccountRelationService implements RechargeCardAccountRelationManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RechargeCardAccountRelationMapper.save", pd);
	}
	

	
}

