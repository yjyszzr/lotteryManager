package com.fh.service.lottery.activitybonus.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.config.URLConfig;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.entity.param.BonusParam;
import com.fh.service.lottery.activitybonus.ActivityBonusSHManager;
import com.fh.util.PageData;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 说明： 优惠券 创建人：FH Q313596790 创建时间：2018-05-05
 * 
 * @version
 */
@Service("activitybonusSHService")
public class ActivityBonusSHService implements ActivityBonusSHManager {
	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ActivityBonusSHMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ActivityBonusSHMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ActivityBonusSHMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ActivityBonusSHMapper.datalistPage", page);
	}

	/**
	 * query列表 by type
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryListByType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ActivityBonusSHMapper.queryListByType", pd);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ActivityBonusSHMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ActivityBonusSHMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ActivityBonusSHMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<PageData> queryTotalBonusByMonth(List<String> varList) throws Exception {
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i);
		}
		return (List<PageData>) dao.findForList("ActivityBonusSHMapper.queryTotalBonusByMonth", userIdList);
	}

	/**
	 * 高速批量派发红包 sdd
	 * 
	 * @param list
	 */
	public int batchInsertUserBonus(List<Integer> userIdlist, BonusParam bonusParam) {
		Connection conn = null;
		try {
			Class.forName(urlConfig.getDriverClassName3());
			conn = (Connection) DriverManager.getConnection(urlConfig.getUrl3(), urlConfig.getUsername3(),
					urlConfig.getPassword3());
			conn.setAutoCommit(false);
			String sql = "insert into dl_user_bonus(user_id,bonus_id,bonus_sn,bonus_price,receive_time,used_time,start_time,end_time,add_time,order_sn,bonus_status,is_delete,use_range,min_goods_amount,is_read) values \n"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prest = (PreparedStatement) conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for (int i = 0, size = userIdlist.size(); i < size; i++) {
				prest.setInt(1, userIdlist.get(i));
				prest.setInt(2, bonusParam.getBonusId());
				prest.setString(3, bonusParam.getBonusSn());
				prest.setBigDecimal(4, bonusParam.getBonusPrice());
				prest.setInt(5, bonusParam.getReceiveTime());
				prest.setInt(6, 0);
				prest.setInt(7, bonusParam.getStartTime());
				prest.setInt(8, bonusParam.getEndTime());
				prest.setInt(9, bonusParam.getAddTime());
				prest.setString(10, "0");
				prest.setString(11, "0");
				prest.setInt(12, 0);
				prest.setInt(13, 0);
				prest.setBigDecimal(14, bonusParam.getMinGoodsAmount());
				prest.setInt(15, bonusParam.getIsRead());
				prest.addBatch();
			}
			prest.executeBatch();
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
//				/logger.error(DateUtilNew.getCurrentDateTime() + "执行updateOrderStatus异常，且回滚异常:" + ex.getMessage());
				return -1;
			}
		}
		return 1;
	}

	/*
	 * @param pd
	 * 
	 * @throws Exception
	 */
	public PageData sellerUserBonushTotal(List<String> varList) throws Exception {
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i);
		}
		return (PageData) dao.findForObject("ActivityBonusSHMapper.sellerUserBonushTotal", userIdList);
	}

	@Override
	public PageData findRechargeCardByRechargeCardId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityBonusSHMapper.findRechargeCardByRechargeCardId", pd);
	}

	@Override
	public PageData findBonusByRechargeCardId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ActivityBonusSHMapper.findBonusByRechargeCardId", pd);
	}

}
