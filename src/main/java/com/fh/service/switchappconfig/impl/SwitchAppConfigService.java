package com.fh.service.switchappconfig.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.switchappconfig.SwitchAppConfigManager;
import com.fh.util.PageData;

/** 
 * 说明： app配置
 * 创建人：FH Q313596790
 * 创建时间：2018-04-16
 * @version
 */
@Service("switchappconfigService")
public class SwitchAppConfigService implements SwitchAppConfigManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SwitchAppConfigMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SwitchAppConfigMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SwitchAppConfigMapper.edit", pd);
	}
	
	/**修改开关
	 * @param pd
	 * @throws Exception
	 */
	public void changeChannelSwitch(PageData pd)throws Exception{
		dao.update("SwitchAppConfigMapper.changeChannelSwitch", pd);
	}

	/**
	 * 查找所有appName
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAppName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SwitchAppConfigMapper.listAppName",pd);
	}	
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listSubDictByParentId(String parentId) throws Exception {
		return (List<PageData>) dao.findForList("SwitchAppConfigMapper.listSubDictByParentId", parentId);
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SwitchAppConfigMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SwitchAppConfigMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SwitchAppConfigMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SwitchAppConfigMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**查询渠道
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryChannel() throws Exception {
		return (List<PageData>)dao.findForList("SwitchAppConfigMapper.findChannels", "");

	}

	@Override
	public PageData queryChannelByCondition(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageData> querySwitchAppConfig(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SwitchAppConfigMapper.querySwitchAppConfig", pd);
	}

	@Override
	public List<PageData> listByAppCodeName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SwitchAppConfigMapper.listAppName",pd);
	}

	@Override
	public List<PageData> queryList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("SwitchAppConfigMapper.queryList",page);
	}

}

