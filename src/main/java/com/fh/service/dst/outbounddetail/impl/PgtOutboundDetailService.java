package com.fh.service.dst.outbounddetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.outbounddetail.PgtOutboundDetailManager;
import com.fh.util.PageData;

/**
 * 说明： dst 创建人：FH Q313596790 创建时间：2017-09-10
 * 
 * @version
 */
@Service("pgtoutbounddetailService")
public class PgtOutboundDetailService implements PgtOutboundDetailManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public Object save(PageData pd) throws Exception {
		return dao.save("PgtOutboundDetailMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("PgtOutboundDetailMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("PgtOutboundDetailMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundDetailMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundDetailMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PgtOutboundDetailMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("PgtOutboundDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByOutBoudnDetilCode(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("PgtOutboundDetailMapper.findByOutBoudnDetilCode", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> queryByPurchase(PageData pd)  throws Exception {
		return (List<PageData>)dao.findForList("PgtOutboundDetailMapper.queryByPurchaseCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryOutboundDetilByCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundDetailMapper.queryOutboundDetilByCode", pd);
	}

}
