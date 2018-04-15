package com.fh.service.dst.warehouse.serialconfig.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.util.PageData;

/** 
 * 说明： 分类编码
 * 创建人：FH Q313596790
 * 创建时间：2017-08-24
 * @version
 */
@Service("getserialconfigutilService")
public class GetSerialConfigUtilService implements GetSerialConfigUtilManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	private Lock lock = new ReentrantLock();
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GetSerialConfigUtilMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("GetSerialConfigUtilMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GetSerialConfigUtilMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GetSerialConfigUtilMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GetSerialConfigUtilMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GetSerialConfigUtilMapper.findById", pd);
	}
	
	/**通过参数获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByPd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GetSerialConfigUtilMapper.findByPd", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GetSerialConfigUtilMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**获取编码
	 * @param codeType
	 * @throws Exception
	 */
	@Override
	public String getSerialCode(String codeType) throws Exception {
		if(StringUtils.isNotEmpty(codeType)) {
			if(lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
				try {
					PageData pd = new PageData();
					pd.put("type_code", codeType);
					pd = findByPd(pd);
					if(pd != null) {
						String code_prefix = pd.getString("code_prefix");
						Integer code_length = Integer.parseInt(pd.get("code_length").toString());
						Integer latest_no = Integer.parseInt(pd.get("latest_no").toString())+1;
						if(StringUtils.isNotEmpty(code_prefix) && code_length != null && latest_no != null) {
							String code = "";
							code += code_prefix;
							int count = latest_no.toString().length();
							if(count <= code_length) {
							    for(int i=0;i<code_length-count;i++) {
							    	code += "0";
							    }
							    code += latest_no.toString();
							    pd.put("latest_no", latest_no);
							    edit(pd);
								return code;
							}
						}
					}
				} catch (Exception e) {
					return null;
				} finally {
					lock.unlock();
				}
			}
		}
		return null;
	}
}

