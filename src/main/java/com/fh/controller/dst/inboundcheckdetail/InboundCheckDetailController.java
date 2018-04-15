package com.fh.controller.dst.inboundcheckdetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.dst.qualitycheckfile.QualityCheckFileManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** 
 * 说明：批次质检
 * 创建人：FH Q313596790
 * 创建时间：2017-10-02
 */
@Controller
@RequestMapping(value="/inboundcheckdetail")
public class InboundCheckDetailController extends BaseController {
	
	String menuUrl = "inboundcheckdetail/list.do"; //菜单地址(权限用)
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundcheckdetailService;
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;

	@Resource(name = "qualitycheckfileService")
	private QualityCheckFileManager qualitycheckfileService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundCheckDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd  = new PageData();
		//PageData pd = new PageData();
		fillPageData(request,pd);
		
		pd.put("status",0);
		User user = getSessionUser();
		//默认数据
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
//		pd.put("pgt_inbound_check_detail_id", this.get32UUID());	//主键
	//	pd.put("pgt_inbound_check_detail_id", "0");	//id
		inboundcheckdetailService.save(pd);
		saveChekFiles(pd,request);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 填充pagedata，form 加了属性 enctype="multipart/form-data"之后，无法自动获取pagedata
	 * @param request
	 * @param pd
	 */
	private void fillPageData(HttpServletRequest request,PageData pd) {
		pd.put("pgt_inbound_check_detail_id", request.getParameter("pgt_inbound_check_detail_id"));
		pd.put("inbound_notice_code", request.getParameter("inbound_notice_code"));
		pd.put("notice_detail_id", request.getParameter("notice_detail_id"));
		pd.put("purchase_code", request.getParameter("purchase_code"));
		pd.put("purchase_detail_id", request.getParameter("purchase_detail_id"));
		pd.put("inbound_notice_stock_batch_id", request.getParameter("inbound_notice_stock_batch_id"));
		pd.put("sku_id", request.getParameter("sku_id"));
		pd.put("sku_name", request.getParameter("sku_name"));
		pd.put("sku_encode", request.getParameter("sku_encode"));
		pd.put("total_quantity", request.getParameter("total_quantity"));
		pd.put("good_quantity", request.getParameter("good_quantity"));
		pd.put("bad_quantity", request.getParameter("bad_quantity"));
		pd.put("scrap_quantity", request.getParameter("scrap_quantity"));
		pd.put("bad_deal_type", request.getParameter("bad_deal_type"));
		pd.put("spec", request.getParameter("spec"));
		pd.put("unit_name", request.getParameter("unit_name"));
		pd.put("batch_code", request.getParameter("batch_code"));
		pd.put("note", request.getParameter("note"));
		pd.put("status", request.getParameter("status"));
		int good_quantity = Integer.parseInt(pd.getString("good_quantity"));
		int bad_quantity = Integer.parseInt(pd.getString("bad_quantity"));
		int scrap_quantity = Integer.parseInt(pd.getString("scrap_quantity"));
		pd.put("total_quantity", good_quantity+bad_quantity+scrap_quantity);
		
 
	}
	private void saveChekFiles(PageData pd,HttpServletRequest request) {
		{
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			String quality_check_id=pd.getString("pgt_inbound_check_detail_id");
			String parentPath = basepath + quality_check_id + "/";
			
		//	String parentPath = basepath + pd.getString("pgt_inbound_check_detail_id") + "/";
			File dir = new File(parentPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			while (iter.hasNext()) {
				try {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (null != file) {
						String fileName = file.getOriginalFilename();
						if (null != fileName && fileName.trim().length() > 0) {
							fileName = fileName.replaceAll(" ", "");
							String dest = parentPath + fileName;
							File file2 = new File(dest);
							if (file2.exists()) {
								if (fileName.contains(".")) {
									int index = fileName.lastIndexOf(".");
									fileName = fileName.substring(0, index) + System.currentTimeMillis() + "."
											+ fileName.substring(index + 1);
									file2 = new File(parentPath + fileName);
								}
							}
							file.transferTo(file2);
							savePageData(quality_check_id, fileName);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 获取父亲
	 * @param quality_check_id
	 * @return
	 */
	private String getParentPath(String quality_check_id) {
		String parentPath = basepath + quality_check_id + "/";
		return parentPath;
	}
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundCheckDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundcheckdetailService.delete(pd);
		out.write("success");
		out.close();
	}
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/downloadCheckFile")
	public void downloadCheckFile(HttpServletResponse response) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"downloadCheckFile");
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData file = qualitycheckfileService.findById(pd);
		
		if(file == null) {
			 response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.getWriter().print(new String("文件不存在".getBytes(),"utf-8"));
			return ;
		}
		File f  =  new File(getParentPath(file.getString("quality_check_id"))+file.getString("url"));
		if(!f.exists()) {
			 response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.getWriter().print(new String("文件不存在".getBytes(),"utf-8"));
			return ;
		}
		FileInputStream fis = new FileInputStream(f);
		byte[] buffer = new byte[4096];
		int len = fis.read(buffer);
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getString("url").getBytes(),"utf-8"));

		OutputStream os  = response.getOutputStream();
		while(len>0) {
			os.write(buffer,0,len);
			len = fis.read(buffer);
		}
		os.flush();
		os.close();
	}
		
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundCheckDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		fillPageData(request,pd);
		User user = getSessionUser();
		//默认数据
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
		inboundcheckdetailService.edit(pd);
		saveChekFiles(pd,request);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/ajaxEdit")
	public ModelAndView ajaxEdit(PrintWriter pw) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundCheckDetail ajaxEdit");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		//默认数据
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);

		if("1".equals(pd.getString("isSubmit"))) {
			pd.put("commit_time", curTime);
		}
		inboundcheckdetailService.edit(pd);
		pw.write("success");
		pw.flush();
		pw.close();
		return null;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundCheckDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inboundcheckdetailService.list(page);	//列出InboundCheckDetail列表
		
		
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/** 质检查询
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listForQuery")
	public ModelAndView listForQuery(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundCheckDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String create_time_end = pd.getString("create_time_end_real");				//关键词检索条件
		if(null != create_time_end && !"".equals(create_time_end)){
			//结束日期，“天”加1，不然时间范围有错误
			create_time_end = DateUtil.getAfterDayDate(create_time_end.trim(),1);
			pd.put("create_time_end", create_time_end);
		}
		
		page.setPd(pd);
		List<PageData>	varList = inboundcheckdetailService.list(page);	//列出InboundCheckDetail列表
		
		
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_list_forQuery");
		mv.addObject("varList", varList);
		
		mv.addObject("pd", pd);
		
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listAll")
	public ModelAndView listAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundCheckDetail listAll");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		List<PageData>	varList = inboundcheckdetailService.listAll(pd);	//列出InboundCheckDetail列表
		int checkTotal=0;
		if(varList!=null && varList.size()>0) {
			for(PageData ch : varList) {
				if(!ch.getString("status").equals("0")) {
				checkTotal += Integer.parseInt(ch.getString("total_quantity"));
				}
			}
		}
		pd.put("checkTotal", checkTotal);
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundnoticestockbatchService.findById(pd);
		//没有业务编码也和业务ID，需再查询
		PageData noticeDetailPd = inboundnoticedetailService.findById(pd);
		pd.put("purchase_code", noticeDetailPd.getString("purchase_code"));
		pd.put("purchase_detail_id", noticeDetailPd.getString("purchase_detail_id"));
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundcheckdetailService.findById(pd);	//根据ID读取
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去详情页面，“质检查询菜单使用”
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goDetail")
	public ModelAndView goDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundcheckdetailService.findById(pd);	//根据ID读取
		mv.setViewName("dst/inboundcheckdetail/inboundcheckdetail_detail");
		//质检文件
		PageData queryCF = new PageData();
		queryCF.put("quality_check_id", pd.getString("pgt_inbound_check_detail_id"));
		List<PageData> fileList = qualitycheckfileService.listAll(queryCF);
		mv.addObject("fileList", fileList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundCheckDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundcheckdetailService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundCheckDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");	//1
		titles.add("入库通知单CODE");	//2
		titles.add("入库通知单详情ID");	//3
		titles.add("业务编码");	//4
		titles.add("业务详情ID");	//5
		titles.add("入库批次ID");	//6
		titles.add("sku_id");	//7
		titles.add("商品名称");	//8
		titles.add("商品编码");	//9
		titles.add("质检总数");	//10
		titles.add("良品数");	//11
		titles.add("不良品数");	//12
		titles.add("报损数");	//13
		titles.add("处理类型");	//14
		titles.add("规格");	//15
		titles.add("单位名称");	//16
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundcheckdetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("pgt_inbound_check_detail_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_notice_code"));	    //2
			vpd.put("var3", varOList.get(i).get("notice_detail_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("purchase_code"));	    //4
			vpd.put("var5", varOList.get(i).get("purchase_detail_id").toString());	//5
			vpd.put("var6", varOList.get(i).get("inbound_notice_stock_batch_id").toString());	//6
			vpd.put("var7", varOList.get(i).get("sku_id").toString());	//7
			vpd.put("var8", varOList.get(i).getString("sku_name"));	    //8
			vpd.put("var9", varOList.get(i).getString("sku_encode"));	    //9
			vpd.put("var10", varOList.get(i).get("total_quantity").toString());	//10
			vpd.put("var11", varOList.get(i).get("good_quantity").toString());	//11
			vpd.put("var12", varOList.get(i).get("bad_quantity").toString());	//12
			vpd.put("var13", varOList.get(i).get("scrap_quantity").toString());	//13
			vpd.put("var14", varOList.get(i).get("bad_deal_type").toString());	//14
			vpd.put("var15", varOList.get(i).getString("spec"));	    //15
			vpd.put("var16", varOList.get(i).getString("unit_name"));	    //16
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/*
	 // 添加质检文件
			{
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				String parentPath = basepath + quality_check_id + "/";
				File dir = new File(parentPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				while (iter.hasNext()) {
					try {
						MultipartFile file = multiRequest.getFile(iter.next());
						if (null != file) {
							String fileName = file.getOriginalFilename();
							if (null != fileName && fileName.trim().length() > 0) {
								String dest = parentPath + fileName;
								File file2 = new File(dest);
								if (file2.exists()) {
									if (fileName.contains(".")) {
										int index = fileName.lastIndexOf(".");
										fileName = fileName.substring(0, index) + System.currentTimeMillis() + "."
												+ fileName.substring(index + 1);
										file2 = new File(parentPath + fileName);
									}
								}
								file.transferTo(file2);
								savePageData(quality_check_id, fileName);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	 */
	
	private void savePageData(String quality_check_id, String url) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		// pd = this.getPageData();
		pd.put("quality_check_id", quality_check_id);
		pd.put("createby", user.getUSER_ID()); // 创建人
		pd.put("create_time", Tools.date2Str(new Date())); // 创建时间
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", Tools.date2Str(new Date())); // 更新时间
		pd.put("url", url);
		qualitycheckfileService.save(pd);
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
