package com.fh.service.system.menu.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.PageData;

/**
 * 类名称：MenuService 菜单处理 创建人：FH qq 3 1 3 5 9 6 7 9 0[青苔] 修改时间：2015年10月27日
 * 
 * @version v2
 */
@Service("menuService")
public class MenuService implements MenuManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 通过ID获取其子一级菜单
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}

	/**
	 * 通过菜单ID获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}

	/**
	 * 新增菜单
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
	}

	/**
	 * 取最大ID
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}

	/**
	 * 删除菜单
	 * 
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}

	/**
	 * 编辑
	 * 
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception {
		dao.update("MenuMapper.updateMenu", menu);
	}

	/**
	 * 保存菜单图标
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.editicon", pd);
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		PageData PageData = new PageData();
		List<Menu> menuList = this.listAllMenu(PageData);
		return getAllMenuForMenuManager(MENU_ID, menuList);
	}

	private List<Menu> getAllMenuForMenuManager(String MENU_ID, List<Menu> menuList) throws Exception {
		List<Menu> menuListChild = new ArrayList<Menu>();
		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getPARENT_ID().equals(MENU_ID)) {
				menuListChild.add(menuList.get(i));
			}
		}
		for (Menu menu : menuListChild) {
			menu.setMENU_URL("menu/toEdit.do?MENU_ID=" + menu.getMENU_ID());
			menu.setSubMenu(this.getAllMenuForMenuManager(menu.getMENU_ID(), menuList));
			menu.setTarget("treeFrame");
		}
		return menuListChild;
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
		PageData PageData = new PageData();
		List<Menu> menuList = this.listAllMenu(PageData);
		return subMenuList(menuList, MENU_ID);
	}

	private List<Menu> subMenuList(List<Menu> menuListAll, String MENU_ID) throws Exception {
		List<Menu> menuList = new ArrayList<Menu>();
		for (int i = 0; i < menuListAll.size(); i++) {
			if (menuListAll.get(i).getPARENT_ID().equals(MENU_ID)) {
				menuList.add(menuListAll.get(i));
			}
		}
		for (Menu menu : menuList) {
			menu.setSubMenu(this.subMenuList(menuListAll, menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	@SuppressWarnings("unchecked")
	private List<Menu> listAllMenu(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAllMenu", pd);
	}
}
