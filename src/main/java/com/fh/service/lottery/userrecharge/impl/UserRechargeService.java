package com.fh.service.lottery.userrecharge.impl;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.userrecharge.UserRechargeManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明： 用户提现模块 创建人：FH Q313596790 创建时间：2018-05-02
 * 
 * @version
 */
@Service("userrechargeService")
public class UserRechargeService implements UserRechargeManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("UserRechargeMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("UserRechargeMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("UserRechargeMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"UserRechargeMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserRechargeMapper.listAll",
				pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserRechargeMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserRechargeMapper.deleteAll", ArrayDATA_IDS);
	}
	/**
	 * 总充值金额和人数
	 * 
	 * @throws Exception
	 */
	@Override
	public PageData findTotalRecharge(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserRechargeMapper.findTotalRecharge", pd);
	}

    /**
     *查询个人个人充值赠送的大礼包金额综合
     *
     * @throws Exception
     */
    @Override
    public List<PageData> queryTotalRechareCardByMobiles(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("UserRechargeMapper.queryTotalRechareCardByMobiles", pd);
    }

}
