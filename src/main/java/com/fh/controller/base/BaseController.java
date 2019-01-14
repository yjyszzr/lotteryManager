package com.fh.controller.base;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.DbFH;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * @author FH Q313596790
 * 修改时间：2015、12、11
 */
public class BaseController extends ApplicationObjectSupport{
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	protected final static String basepath = DbFH.getCheckFileDir();
	
	protected final static String deliveryBasepath = DbFH.getDeliveryFileDir();
	/** new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表的信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	public User getSessionUser() {
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		return user;
	}
	
	public String[] getDataRights(){
		Session session = Jurisdiction.getSession();
		String[] list = (String[]) session.getAttribute(Const.SESSION_DATA_RIGHTS);
		return list;
	}
	
	public void fillUserName(List<PageData> list,String proName,UserManager userService) throws Exception {
		if(list==null) {
			return ;
		}
		for(PageData pd : list) {
			PageData user = new PageData();
			user.put("USER_ID", pd.get(proName));
			user = userService.findById(user);
			if(user !=null) {
				pd.put(proName, user.get("USERNAME"));
				
			}
		}
		
	}
	public void fillUserName(List<PageData> list,String proName) throws Exception {
		if(list==null) {
			return ;
		}
		UserManager userService =(UserManager) getApplicationContext().getBean("userService");
		for(PageData pd : list) {
			PageData user = new PageData();
			user.put("USER_ID", pd.get(proName));
			user = userService.findById(user);
			if(user !=null) {
				pd.put(proName, user.get("USERNAME"));
				
			}
		}
		
	}
	public void fillUserName(PageData pd ,String proName) throws Exception {
		if(pd==null) {
			return ;
		}
		UserManager userService =(UserManager) getApplicationContext().getBean("userService");
		//getRequest().getSession().getServletContext().get
			PageData user = new PageData();
			user.put("USER_ID", pd.get(proName));
			user = userService.findById(user);
			if(user !=null) {
				pd.put(proName, user.get("USERNAME"));
				
			}
	}
		
}
