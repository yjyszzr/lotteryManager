package com.fh.service.lottery.superwhitelist;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 超级白名单接口
 * 创建人：FH Q313596790
 * 创建时间：2018-12-26
 * @version
 */
public interface SuperWhiteListManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	public void saveUserBonus(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 流水（全部）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAccount(Page page)throws Exception;
	
	public List<PageData> listAccountAll(PageData pd)throws Exception;
	
	public List<PageData> listStoreAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	public PageData findUserByUserid(PageData pd)throws Exception;
	
	public PageData getSumRechargeCardRealValue(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public void recharge(PageData pd)throws Exception;
	
	public void deduction(PageData pd)throws Exception;
	
}

