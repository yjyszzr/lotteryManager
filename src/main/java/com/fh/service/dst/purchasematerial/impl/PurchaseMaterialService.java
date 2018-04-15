package com.fh.service.dst.purchasematerial.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.purchasematerial.PurchaseMaterialManager;
import com.fh.util.PageData;

/** 
 * 说明： 用料列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("purchasematerialService")
public class PurchaseMaterialService implements PurchaseMaterialManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PurchaseMaterialMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PurchaseMaterialMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PurchaseMaterialMapper.edit", pd);
	}
	
	/**领料/退料
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("PurchaseMaterialMapper.update", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseMaterialMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseMaterialMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseMaterialMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PurchaseMaterialMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public void updateQuantity(PageData pd)throws Exception{
		dao.update("PurchaseMaterialMapper.updateQuantity", pd);
	}
	
	public void increaseQuantity(PageData pd)throws Exception{
		dao.update("PurchaseMaterialMapper.increaseQuantity", pd);
	}
	
	@Override
	public void increaseReturnQuantity(PageData pd) throws Exception {
		dao.update("PurchaseMaterialMapper.increaseReturnQuantity", pd);
	}
	@Override
	public void increaseReturnAndTotal(PageData pd) throws Exception {
		dao.update("PurchaseMaterialMapper.increaseReturnAndTotal", pd);
	}
	
	@Override
	public void callbackMaterial(Integer material_id, Integer count, String updateby, Date updateTime)
			throws Exception {
		PageData querypd=new  PageData();
		querypd.put("material_id", material_id);
		PageData pageDataDb=findById(querypd);
		if(pageDataDb!=null) {
			String total_return_quantity=pageDataDb.getString("total_return_quantity");
			Integer total_quantity=Integer.valueOf(total_return_quantity);
			total_quantity=total_quantity-count;
			if(total_quantity<0) {
				throw new Exception("物料退货数量过多");
			}
			pageDataDb.put("total_return_quantity", 0-count);
			pageDataDb.put("updateby", updateby);
			pageDataDb.put("update_time", updateTime);
			this.increaseReturnQuantity(pageDataDb);
		}else {
			throw new Exception("找不到物料单据");
		}
		
	}

	@Override
	public void editCount(PageData pd) throws Exception {
		dao.update("PurchaseMaterialMapper.editCount", pd);
	}


	@Override
	public PageData findByMaterial(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PurchaseMaterialMapper.findByMaterial", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listById(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PurchaseMaterialMapper.listById", pd);
	}
	
	@Override
	public void saveMaterialDetail(PageData pd) throws Exception{
		Object findForObject = dao.findForObject("PurchaseMaterialMapper.findMaterialDetail", pd);
		if(findForObject == null) {
			dao.save("PurchaseMaterialMapper.insertMaterialDetail", pd);
		}else {
			dao.update("PurchaseMaterialMapper.updateMaterialDetail", pd);
		}

	}

	

}

