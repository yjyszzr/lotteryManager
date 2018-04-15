package com.fh.service.batchgenerate.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.batchgenerate.BatchGenerateManager;
import com.fh.util.PageData;


/** 
 * 说明： 批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-18
 * @version
 */
@Service("batchgenerateService")
public class BatchGenerateService implements BatchGenerateManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BatchGenerateMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BatchGenerateMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BatchGenerateMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BatchGenerateMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BatchGenerateMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BatchGenerateMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BatchGenerateMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public String generateBatchNo(String batchNo) throws Exception
	{
		PageData pdParameter=new PageData();
		pdParameter.put("batch_no",batchNo.trim());
		PageData pdResult=findByBatchNo(pdParameter);
		if(pdResult==null)
		{
			pdParameter.put("latest_no",1);
			//插入数据
			save(pdParameter);
		}
		else
		{
			int lastNo=Integer.parseInt(pdResult.get("latest_no").toString());
			//更新数据库
			lastNo=lastNo+1;
			edit(pdParameter);
			return batchNo.trim()+lastNo;
			
		}
		return batchNo.trim()+1;
	}

	public PageData findByBatchNo(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("BatchGenerateMapper.findByBatchNo", pd);
	}
	
}

