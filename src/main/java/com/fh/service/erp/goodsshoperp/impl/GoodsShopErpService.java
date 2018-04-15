package com.fh.service.erp.goodsshoperp.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.erp.goodsshoperp.GoodsShopErpManager;

/**
 * 说明： 商品在商城与ERP对应 创建人：FH Q313596790 创建时间：2017-09-20
 * 
 * @version
 */
@Service("goodsshoperpService")
public class GoodsShopErpService implements GoodsShopErpManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("GoodsShopErpMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("GoodsShopErpMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("GoodsShopErpMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("GoodsShopErpMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("GoodsShopErpMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("GoodsShopErpMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public int saveGoodsShopErp(PageData pdGoods, PageData pdSku) throws Exception {
		PageData pageQuery = (PageData) dao.findForObject("GoodsShopErpMapper.findByIdFromErq", pdGoods);
		if (pageQuery != null) {
			return 1;
		} else {
			dao.update("GoodsShopErpMapper.editGoods", pdGoods);
			dao.update("GoodsShopErpMapper.editSku", pdSku);
			return 0;
		}

	}

}
