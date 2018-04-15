package com.fh.service.erp.erpdeliveryvehicleworkfile.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.erp.erpdeliveryvehicleworkfile.ErpDeliveryVehicleWorkFileManager;

/** 
 * 说明： 配送文件
 * 创建人：FH Q313596790
 * 创建时间：2017-10-26
 * @version
 */
@Service("erpdeliveryvehicleworkfileService")
public class ErpDeliveryVehicleWorkFileService implements ErpDeliveryVehicleWorkFileManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ErpDeliveryVehicleWorkFileMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ErpDeliveryVehicleWorkFileMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ErpDeliveryVehicleWorkFileMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ErpDeliveryVehicleWorkFileMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErpDeliveryVehicleWorkFileMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ErpDeliveryVehicleWorkFileMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ErpDeliveryVehicleWorkFileMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

