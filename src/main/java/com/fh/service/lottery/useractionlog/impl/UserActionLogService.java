package com.fh.service.lottery.useractionlog.impl;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.useractionlog.UserActionLogManager;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 说明： 用户操作日志 创建人：FH Q313596790 创建时间：2018-06-06
 * 
 * @version
 */
@Service("userActionLogService")
public class UserActionLogService implements UserActionLogManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param status
	 *            状态（1成功，0失败）
	 * @param type
	 *            操作类型（0 update 1 save 2 delete）
	 * @param obj
	 *            操作对象
	 * @param text
	 *            操作内容
	 * @throws Exception
	 */
	public void save(String status, String type, String obj, String text) throws Exception {
		PageData pd = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		pd.put("user_id", user.getUSER_ID());
		pd.put("user_name", user.getNAME());
		pd.put("action_ip", user.getIP());
		String actionTime = LocalDateTime.now().format(DateUtilNew.datetimeFormat);
		pd.put("action_time", DateUtilNew.getMilliSecondsByStr(actionTime));
		pd.put("action_status", status);
		pd.put("action_type", type);
		pd.put("action_object", obj);
		pd.put("action_text", text);
		dao.save("UserActionLogMapper.save", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserActionLogMapper.datalistPage", page);
	}

	/**
	 * update记录
	 * 
	 * @param status
	 *            状态（1成功，0失败）
	 * @param name
	 *            操作对象
	 * @param oldObj
	 *            原始数据
	 * @param newObj
	 *            修改数据
	 * @throws Exception
	 */
	@Override
	public void saveByObject(String status, String name, PageData oldPage, PageData newPage) throws Exception {
		String action = "";
		Set<String> keySet = newPage.keySet();
		for (String key : keySet) {
			String oldValue = oldPage.getString(key);
			String newValue = newPage.getString(key);
			if (!oldValue.equals("") && !newValue.equals("") && !oldValue.equals(newValue)) {
				if( key.equals("content")) {
					action = action +"修改文章内容；";
				}else {
					action = action + key + "：" + oldValue + " -> " + newValue + "；";
				}
			}
		}
		if (!action.equals("")) {
			PageData pd = new PageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
			pd.put("user_id", user.getUSER_ID());
			pd.put("user_name", user.getNAME());
			pd.put("action_ip", user.getIP());
			String actionTime = LocalDateTime.now().format(DateUtilNew.datetimeFormat);
			pd.put("action_time", DateUtilNew.getMilliSecondsByStr(actionTime));
			pd.put("action_status", status);
			pd.put("action_type", "0");
			pd.put("action_object", name);
			pd.put("action_text", action);
			dao.save("UserActionLogMapper.save", pd);
		}
	}

}
