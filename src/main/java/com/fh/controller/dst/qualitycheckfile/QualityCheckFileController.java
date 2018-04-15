package com.fh.controller.dst.qualitycheckfile;

import java.io.File;
import java.io.PrintWriter;
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

import org.apache.commons.io.FileUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.qualitycheckfile.QualityCheckFileManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DbFH;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** 
 * 说明：质检文件
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value="/qualitycheckfile")
public class QualityCheckFileController extends BaseController {
	
	String menuUrl = "qualitycheckfile/list.do"; //菜单地址(权限用)
	@Resource(name="qualitycheckfileService")
	private QualityCheckFileManager qualitycheckfileService;
	
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增QualityCheckFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String quality_check_id = request.getParameter("quality_check_id");
		ModelAndView mv = this.getModelAndView();
		{
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> iter = multiRequest.getFileNames();
			String parentPath = basepath + quality_check_id + "/";
			File dir = new File(parentPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			while(iter.hasNext()){
				try {
					MultipartFile file = multiRequest.getFile(iter.next());
					if(null != file){
						String fileName = file.getOriginalFilename();
						if(null == fileName || fileName.trim().length() <= 0)continue;
						String dest =  parentPath + fileName;
						File file2 = new File(dest);
						if(file2.exists()){
							if(fileName.contains(".")){
								int index = fileName.lastIndexOf(".");
								fileName = fileName.substring(0, index) + System.currentTimeMillis() + "." + fileName.substring(index+1);
								file2 = new File(parentPath + fileName);
							}
						}
						file.transferTo(file2);
						savePageData(quality_check_id, fileName);
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		
		mv.addObject("msg","success");
		mv.addObject("quality_check_id", quality_check_id);
		mv.setViewName("save_result");
		return mv;
	}

	private void savePageData(String quality_check_id, String url) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		PageData pd = new PageData();
//		pd = this.getPageData();
		pd.put("quality_check_id", quality_check_id);	
		pd.put("createby", user.getUSER_ID());	//创建人
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		pd.put("url", url);
		qualitycheckfileService.save(pd);
	}
	
	@RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
            @RequestParam("id") String fileId)throws Exception {
       //下载文件路径
    	PageData pd = new PageData();
		pd.put("quality_check_file_id", fileId);
		pd = qualitycheckfileService.findById(pd);	//根据ID读取
	   String filename = pd.getString("url");
	   String parentPath = basepath + pd.get("quality_check_id") + "/";
       File file = new File(parentPath + filename);
       HttpHeaders headers = new HttpHeaders();  
       //下载显示的文件名，解决中文名称乱码问题  
       String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
       //通知浏览器以attachment（下载方式）打开图片
       headers.setContentDispositionFormData("attachment", downloadFielName); 
       //application/octet-stream ： 二进制流数据（最常见的文件下载）。
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
               headers, HttpStatus.CREATED);  
    }
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除QualityCheckFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData findById = qualitycheckfileService.findById(pd);
		if(findById != null){
			qualitycheckfileService.delete(pd);
			Object quality_check_id = findById.get("quality_check_id");
			Object url = findById.get("url");
			String parentPath = basepath + quality_check_id + "/"+url;
			File file = new File(parentPath);
			if(file.exists()){
				file.delete();
			}
		}
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改QualityCheckFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		qualitycheckfileService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表QualityCheckFile");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = qualitycheckfileService.list(page);	//列出QualityCheckFile列表
		mv.setViewName("erp/qualitycheckfile/qualitycheckfile_list");
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
		mv.setViewName("erp/qualitycheckfile/qualitycheckfile_edit");
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
		pd = qualitycheckfileService.findById(pd);	//根据ID读取
		mv.setViewName("erp/qualitycheckfile/qualitycheckfile_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除QualityCheckFile");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			qualitycheckfileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出QualityCheckFile到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("质检id");	//1
		titles.add("备注3");	//2
		titles.add("创建人");	//3
		titles.add("创建时间");	//4
		titles.add("更新人");	//5
		titles.add("更新时间");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = qualitycheckfileService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("quality_check_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("url"));	    //2
			vpd.put("var3", varOList.get(i).getString("createby"));	    //3
			vpd.put("var4", varOList.get(i).getString("create_time"));	    //4
			vpd.put("var5", varOList.get(i).getString("updateby"));	    //5
			vpd.put("var6", varOList.get(i).getString("update_time"));	    //6
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
