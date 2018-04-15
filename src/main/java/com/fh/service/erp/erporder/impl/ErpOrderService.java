package com.fh.service.erp.erporder.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.erp.erporder.ErpOrderManager;
import com.fh.util.PageData;

/** 
 * 说明： 订单状态确认
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 * @version
 */
@Service("erporderService")
public class ErpOrderService implements ErpOrderManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao3;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ErpOrderMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ErpOrderMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ErpOrderMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ErpOrderMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErpOrderMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ErpOrderMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ErpOrderMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listAllOrderGoods(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ErpOrderMapper.listAllOrderGoods", pd);
	}

	@Override
	public void updateErpOrder(String order_sn, Integer shipping_status) throws Exception {
		PageData pd=new PageData();
		pd.put("order_sn", order_sn);
		pd.put("shipping_status", shipping_status);
		int rst = (int)dao.update("ErpOrderMapper.edit", pd);
		//???20171108更新商城物流状态(临时)
		/*if(rst > 0) {
			pd.put("code", "receiving_term");
			PageData systemConfig = (PageData)dao3.findForObject("SzyOrderMapper.systemConfigByCode", pd);
			Integer day = 7;
			if(null != systemConfig) {
				try {
					String valueStr = (String) systemConfig.get("value");
					if (valueStr!=null || !"".equals(valueStr)) {
						Integer value = Integer.parseInt(valueStr);
						day = (value != null && value > 0)?value:24;
					}
				} catch (Exception e) {

				}
			}
			Long sec = day*24*3600l;
			pd.put("confirm_time", sec);
			dao3.update("SzyOrderMapper.updateOrderOutStatus", pd);
		}*/
	}
	@Override
	public void updateErpOrderDeliveryOrg(String order_sn, String delivery_org_id)throws Exception{
		PageData pd=new PageData();
		pd.put("order_sn", order_sn);
		pd.put("delivery_org_id", delivery_org_id);
		dao.update("ErpOrderMapper.updateErpOrderDeliveryOrg", pd);
	}
	@Override
	public List<PageData> listAllShop() throws Exception {
		return (List<PageData>)dao.findForList("ErpOrderMapper.listAllShop", null);
	}

	@Override
	public void updateErpOrder(String order_sn, Integer shipping_status, Integer delivery_org_id) throws Exception {
		PageData pd=new PageData();
		pd.put("order_sn", order_sn);
		pd.put("shipping_status", shipping_status);
		pd.put("delivery_org_id", delivery_org_id);
		dao.update("ErpOrderMapper.editShippingAndDelivery", pd);
	}

	@Override
	public List<PageData>  listDeliveryByStoreId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ErpOrderMapper.listDeliveryByStoreId", pd);
	}
	
}

