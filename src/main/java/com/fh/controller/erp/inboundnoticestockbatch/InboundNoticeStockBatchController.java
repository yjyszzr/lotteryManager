package com.fh.controller.erp.inboundnoticestockbatch;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.batchgenerate.BatchGenerateManager;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.JsonUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.TwoDimensionCode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 说明：批次管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-18
 */
@Controller
@RequestMapping(value="/inboundnoticestockbatch")
public class InboundNoticeStockBatchController extends BaseController {
	
	String menuUrl = "inboundnoticestockbatch/list.do"; //菜单地址(权限用)
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	
	@Resource(name="batchgenerateService")
	BatchGenerateManager batchgenerateService;
	
	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundcheckdetailService;
	
	@Resource(name = "saveInboundService")
	private SaveInboundManager saveInboundService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundNoticeStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_notice_stock_batch_id", this.get32UUID());	//主键
		String batchNo = pd.getString("batch_code");

		if(batchNo.length() != 6) {
			String info = "错误的批次码:"+batchNo+",请检查!";
			mv.addObject("msg","fail");
			mv.addObject("info",info);
			mv.setViewName("info_fail");
			return mv;
		}
		
		PageData queryBatch = new PageData();
		queryBatch.put("notice_detail_id", pd.get("notice_detail_id"));
		queryBatch.put("batch_code", pd.get("batch_code"));
		queryBatch.put("sku_barcode", pd.get("sku_barcode"));
		boolean ishas = inboundnoticestockbatchService.isHasBatch(queryBatch);
		if(ishas) {
			String info = "已存在相同的批次!";
			mv.addObject("msg","fail");
			mv.addObject("info",info);
			mv.setViewName("info_fail");
			return mv;
		}
		pd.put("status", 0);
		inboundnoticestockbatchService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundNoticeStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticestockbatchService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**提交
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/submit")
	public Map<String, String> submit() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONArray jsonArray = JSONArray.fromObject(pd.getString("varList"));
		
		try {
			if(jsonArray.size()>0){
				saveInboundService.saveSubmit(jsonArray);
			}
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "fail");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundNoticeStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticestockbatchService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundNoticeStockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inboundnoticestockbatchService.listByPd(pd);	//列出InboundNoticeStockBatch列表
		mv.setViewName("erp/inboundnoticestockbatch/inboundnoticestockbatch_list");
		mv.addObject("varList", varList);
		if(CollectionUtils.isNotEmpty(varList)) {
			pd.put("status", varList.get(0).get("status"));
			JsonArray ja = new JsonArray();
			for(PageData pageData : varList) {
				String pageStr = pageData.toString(); 
				String str = pageStr.replaceAll("/", "每");
				JsonObject jo = JsonUtils.NewStringToJsonObject(str);
				ja.add(jo);
			}
			mv.addObject("varJsonArray", ja);
		}else {
			pd.put("status", 0);
		}
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**质检列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listForCheck")
	public ModelAndView listForCheck(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundNoticeStockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("status","1");//0是未提交质检的，1是提交质检的，只查询提交质检的
		pd.put("statusMin","1");//0是未提交质检的，1是提交质检的，只查询提交质检的,以及完成的
		
		page.setPd(pd);
		List<PageData>	varList = inboundnoticestockbatchService.listAll(pd);	//列出InboundNoticeStockBatch列表
		mv.setViewName("erp/inboundnoticestockbatch/inboundnoticestockbatch_list_for_check");
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
		mv.setViewName("erp/inboundnoticestockbatch/inboundnoticestockbatch_edit");
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
		pd = inboundnoticestockbatchService.findById(pd);	//根据ID读取
		mv.setViewName("erp/inboundnoticestockbatch/inboundnoticestockbatch_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	@RequestMapping(value="/goPrint")
	public ModelAndView goPrint()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundnoticestockbatchService.findById(pd);	//根据ID读取
		mv.setViewName("erp/inboundnoticestockbatch/inboundnoticestockbatch_print");
		mv.addObject("msg", "print");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundNoticeStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundnoticestockbatchService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundNoticeStockBatch到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("到货通知单编码");	//1
		titles.add("详细Id");	//2
		titles.add("商品ID");	//3
		titles.add("商品名称");	//4
		titles.add("商品编码");	//5
		titles.add("批次");	//6
		titles.add("数量");	//7
		titles.add("规格");	//8
		titles.add("单位");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundnoticestockbatchService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("inbound_notice_code"));	    //1
			vpd.put("var2", varOList.get(i).get("notice_detail_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("sku_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("sku_name"));	    //4
			vpd.put("var5", varOList.get(i).getString("sku_encode"));	    //5
			vpd.put("var6", varOList.get(i).getString("batch_code"));	    //6
			vpd.put("var7", varOList.get(i).get("quantity").toString());	//7
			vpd.put("var8", varOList.get(i).getString("spec"));	    //8
			vpd.put("var9", varOList.get(i).getString("unit_name"));	    //9
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
	
	
	@RequestMapping(value="/printbatchPage")
	public ModelAndView printbatchPage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询批次数量，生成二维码
		
		PageData pdParmeter= new PageData();
		pdParmeter.put("inbound_notice_stock_batch_id", pd.get("inbound_notice_stock_batch_id"));
		
		PageData pdresult = inboundnoticestockbatchService.findById(pdParmeter);
		int beginPos=0;
		int endPos=0;
		int num=0;
		String batchNo="";
		if(pdresult!=null)
		{
			if(pdresult!=null&&pdresult.get("quantity")!=null)
			{
			   Double d=Double.parseDouble(pdresult.get("quantity").toString());
			   num=d.intValue();
			}
			if(pd!=null&&pd.get("beginPos")!=null)
			{
				beginPos=Integer.parseInt(pd.get("beginPos").toString());
			}
			if(pd!=null&&pd.get("endPos")!=null)
			{
				 endPos=Integer.parseInt(pd.get("endPos").toString());
			}
			batchNo=pdresult.get("batch_code").toString();
		}
		List<PageData>  imgListPd=new ArrayList<PageData>();
		try
		{
			File dir = new File(PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE);
			if (!dir.isDirectory()) 
			{
				dir.mkdir();
			}
			if(batchNo!=""&&num>0)
			{
				for(int i=beginPos;i<=endPos;i++)
				{
					PageData pdImg=new PageData();
					String errInfo = "success", encoderImgId = this.get32UUID()+".png"; //encoderImgId此处二维码的图片名
					pdImg.put("path", encoderImgId);
					imgListPd.add(pdImg);
					String code=autoGenericCode(i,5);
					String encoderContent =batchNo+code;
					try {
						String filePath = PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + encoderImgId;  //存放路径
					
						TwoDimensionCode.encoderQRCode(encoderContent, filePath, "png");							//执行生成二维码
					} catch (Exception e) 
					{
						logBefore(logger,e.getMessage());
					}
				}
			}
		}
		catch (Exception ex) 
		{
			logBefore(logger,ex.getMessage());
		}
		
		mv.setViewName("erp/inboundnoticestockbatch/printbatchpage");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("imglist", imgListPd);
		return mv;
	}
	/**
	 * 质检完成
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/completeCheck")
	public ModelAndView completeCheck(PrintWriter pw)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String mess = inboundcheckdetailService.completeCheck(pd);
		
		pw.write(mess);
		pw.flush();
		pw.close();
		return  null;
	}
	
    private String autoGenericCode(Integer code, int num) {
        String result = "";
        result = String.format("%0" + num + "d",code);
        return result;
    }
	
}
