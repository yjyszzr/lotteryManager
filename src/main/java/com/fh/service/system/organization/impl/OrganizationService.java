package com.fh.service.system.organization.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.organization.OrganizationManager;
import com.fh.util.PageData;

/**
 * 说明： 组织架构管理 创建人：FH Q313596790 创建时间：2017-09-19
 * 
 * @version
 */
@Service("organizationService")
public class OrganizationService implements OrganizationManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("OrganizationMapper.save", pd);
	}

	/**
	 * 新增组织机构和仓库关系数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveOrgStore(List<PageData> list) throws Exception {
		dao.save("OrganizationMapper.saveOrgStore", list);
	}

	/**
	 * 新增组织机构和用户关系数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveOrgUser(List<PageData> list) throws Exception {
		dao.save("OrganizationMapper.saveOrgUser", list);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OrganizationMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("OrganizationMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrganizationMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrganizationMapper.listAll", pd);
	}

	/**
	 * 通过UserId获取用户数据权限
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listDataRightsByUserId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrganizationMapper.listDataRightsByUserId", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrganizationMapper.findById", pd);
	}

	/**
	 * 通过组织名称获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOrgName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrganizationMapper.findByOrgName", pd);
	}

	/**
	 * 通过组织机构id和仓库编码获取组织机构和仓库的关系数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByPageData(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrganizationMapper.listByPageData", pd);
	}

	/**
	 * 通过组织机构id和用户Id获取组织机构和用户的关系数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUserByPageData(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrganizationMapper.listUserByPageData", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrganizationMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过组织机构Id批量删除组织机构和仓库关系表数据
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllByOrgId(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrganizationMapper.deleteAllByOrgId", ArrayDATA_IDS);
	}

	/**
	 * 通过组织机构Id批量删除组织机构和用户关系表数据
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllUserByOrgId(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrganizationMapper.deleteAllUserByOrgId", ArrayDATA_IDS);
	}
}
