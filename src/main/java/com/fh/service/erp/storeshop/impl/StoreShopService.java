package com.fh.service.erp.storeshop.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.erp.storeshop.StoreShopManager;
import com.fh.service.erp.szyshop.SzyShopManager;
import com.fh.service.erp.szyshopping.SzyShoppingManager;

/**
 * 说明： 仓库店铺关联管理 创建人：FH Q313596790 创建时间：2017-09-19
 * 
 * @version
 */
@Service("storeshopService")
public class StoreShopService implements StoreShopManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	@Resource(name = "szystoreService")
	private SzyStoreManager szystoreService;
	@Resource(name = "szyshoppingService")
	private SzyShoppingManager szyShoppingService;
	@Resource(name = "szyshopService")
	private SzyShopManager szyShopService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("StoreShopMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("StoreShopMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("StoreShopMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreShopMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreShopMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreShopMapper.findById", pd);
	}
	
	/**
	 * 通过Storeid获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByStoreId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreShopMapper.findByStoreId", pd);
	}
	
	/**
	 * 通过Shopid获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByShopId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreShopMapper.findByShopId", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("StoreShopMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 仓库id列表
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public List storeList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> list = szystoreService.listAll(pd);
		return list;
	}

	/**
	 * 店铺id列表
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public List shopList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		// List<PageData> list = szyShoppingService.listAll(pd);
		List<PageData> list = szyShopService.listAll(pd);
		return list;
	}

	@Override
	public PageData findByStore(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreShopMapper.findByStore", pd);
	}

	

	@Override
	public PageData findByStoreIdOnly(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreShopMapper.findByStoreIdOnly", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUserId(PageData pd) throws Exception {
		List<PageData> list=(List<PageData>) dao.findForList("StoreShopMapper.listAllByUserId", pd);
		return list;
	}
}
