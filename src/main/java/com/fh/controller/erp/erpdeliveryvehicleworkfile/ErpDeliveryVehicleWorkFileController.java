package com.fh.controller.erp.erpdeliveryvehicleworkfile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.fh.service.erp.erpdeliveryvehicleworkfile.ErpDeliveryVehicleWorkFileManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：配送文件
 * 创建人：FH Q313596790
 * 创建时间：2017-10-26
 */
@Controller
@RequestMapping(value="/erpdeliveryvehicleworkfile")
public class ErpDeliveryVehicleWorkFileController extends BaseController {
	
	String menuUrl = "erpdeliveryvehicleworkfile/list.do"; //菜单地址(权限用)
	@Resource(name="erpdeliveryvehicleworkfileService")
	private ErpDeliveryVehicleWorkFileManager erpdeliveryvehicleworkfileService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ErpDeliveryVehicleWorkFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		pd = this.getPageData();
		fillPageData(request,pd);
		User user = getSessionUser();
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", curTime);
		String url = saveChekFiles(pd,request);
		pd.put("url", url);
		erpdeliveryvehicleworkfileService.save(pd);
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
		pd.put("delivery_org_id", request.getParameter("delivery_org_id"));
		pd.put("vehicle_id", 0);//预留
		pd.put("delivery_vehicle_work_id", request.getParameter("delivery_vehicle_work_id"));
		pd.put("url", request.getParameter("url"));
		pd.put("remarks", request.getParameter("remarks"));
		pd.put("status", 0);//预留
	}
	
	private String saveChekFiles(PageData pd,HttpServletRequest request) {
		{
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			String delivery_vehicle_work_id=pd.getString("delivery_vehicle_work_id");
			String parentPath = deliveryBasepath + delivery_vehicle_work_id + "/";
			
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
							return fileName;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	/**查看上传的文件
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/downloadCheckFile")
	public void downloadCheckFile(HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData file = erpdeliveryvehicleworkfileService.findById(pd);
		
		if(file == null) {
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.getWriter().print(new String("文件不存在".getBytes("gb2312"), "ISO8859-1"));
			return ;
		}
		File f  =  new File(getParentPath(file.getString("delivery_vehicle_work_id"))+file.getString("url"));
		if(!f.exists()) {
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.getWriter().print(new String("文件不存在".getBytes("gb2312"), "ISO8859-1"));
			return ;
		}
		byte[] bytes = Files.readAllBytes(Paths.get(getParentPath(file.getString("delivery_vehicle_work_id"))+file.getString("url")));
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(new String(file.getString("url").getBytes("gb2312"), "ISO8859-1")));
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(bytes);
        toClient.flush();
        toClient.close();
	}
	
	/**
	 * 获取父路径
	 * @param delivery_vehicle_work_id
	 * @return
	 */
	private String getParentPath(String delivery_vehicle_work_id) {
		String parentPath = deliveryBasepath + delivery_vehicle_work_id + "/";
		return parentPath;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ErpDeliveryVehicleWorkFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		erpdeliveryvehicleworkfileService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ErpDeliveryVehicleWorkFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		erpdeliveryvehicleworkfileService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ErpDeliveryVehicleWorkFile");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = erpdeliveryvehicleworkfileService.list(page);	//列出ErpDeliveryVehicleWorkFile列表
		mv.setViewName("erp/erpdeliveryvehicleworkfile/erpdeliveryvehicleworkfile_list");
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
		mv.setViewName("erp/erpdeliveryvehicleworkfile/erpdeliveryvehicleworkfile_edit");
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
		pd = erpdeliveryvehicleworkfileService.findById(pd);	//根据ID读取
		mv.setViewName("erp/erpdeliveryvehicleworkfile/erpdeliveryvehicleworkfile_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ErpDeliveryVehicleWorkFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			erpdeliveryvehicleworkfileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ErpDeliveryVehicleWorkFile到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = erpdeliveryvehicleworkfileService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("delivery_vehicle_work_file_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("delivery_org_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("vehicle_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("delivery_vehicle_work_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("url"));	    //5
			vpd.put("var6", varOList.get(i).getString("remarks"));	    //6
			vpd.put("var7", varOList.get(i).get("status").toString());	//7
			vpd.put("var8", varOList.get(i).getString("createby"));	    //8
			vpd.put("var9", varOList.get(i).getString("create_time"));	    //9
			vpd.put("var10", varOList.get(i).getString("updateby"));	    //10
			vpd.put("var11", varOList.get(i).getString("update_time"));	    //11
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
