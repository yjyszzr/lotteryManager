package com.fh.service.erp.hander;

import com.fh.util.PageData;

/** 
 * 说明： 生成出库通知单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface MaterialCommitManager{
	
	public void saveCommit(PageData pd) throws Exception;

	public void saveReturnMaterial(PageData pd) throws Exception;
}

