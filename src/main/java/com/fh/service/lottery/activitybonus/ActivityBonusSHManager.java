package com.fh.service.lottery.activitybonus;

import java.util.List;
import com.fh.entity.Page;
import com.fh.entity.param.BonusParam;
import com.fh.util.PageData;

/** 
 * 说明： 优惠券接口
 * 创建人：FH Q313596790
 * 创建时间：2018-05-05
 * @version
 */
public interface ActivityBonusSHManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
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
	
	/**query列表 by type
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> queryListByType(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量给用户派送红包
	 * @param List<Integer> userIdlist,BonusParam bonusParam
	 * @throws Exception
	 */
	public int batchInsertUserBonus(List<Integer> userIdlist,BonusParam bonusParam)throws Exception;
	
	public List<PageData> queryTotalBonusByMonth(List<String> varList)throws Exception;
	
	
	public PageData sellerUserBonushTotal(List<String> varList)throws Exception;

	public PageData findRechargeCardByRechargeCardId(PageData pd)throws Exception;

	public  PageData findBonusByRechargeCardId(PageData pd)throws Exception;
	
}

