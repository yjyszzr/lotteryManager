package com.fh.controller.stockbatch;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.TwoDimensionCode;


/** 
 * 说明：批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-14
 */
@Controller
@RequestMapping(value="/stockbatch")
public class StockBatchController extends BaseController {
	
	String menuUrl = "stockbatch/list.do"; //菜单地址(权限用)
	@Resource(name="stockbatchService")
	private StockBatchManager stockbatchService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增StockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("stock_batch_id", this.get32UUID());	//主键
		stockbatchService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除StockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		stockbatchService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改StockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		stockbatchService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表StockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = stockbatchService.list(page);	//列出StockBatch列表
		mv.setViewName("erp/stockbatch/stockbatch_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/selectList")
	public ModelAndView selectList() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表StockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		/*String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}*/
//		page.setPd(pd);
		List<PageData>	varList = stockbatchService.findBySkuAndStoreId(pd);	//列出StockBatch列表
		String barcodesStr = pd.getString("barcodes");
		if(StringUtils.isNotBlank(barcodesStr) && varList != null && varList.size() > 0) {
			Map<String, String> map = this.checkBarcodeStr(barcodesStr);
			if(map.size() > 0) {
				for(PageData pageData: varList) {
					String batch_code = pageData.getString("batch_code");
					String value = map.get(batch_code);
					pageData.put("sel_value", value);
				}
			}
		}
		mv.setViewName("erp/stockbatch/stockbatch_select_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);//69709772525561709281:3_69709772525561709282:3_
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	private Map<String, String> checkBarcodeStr(String barcodesStr) {
		Map<String, String> map = new HashMap<String, String>();
		String[] split = barcodesStr.split("_");
		for(String str: split) {
			String[] split2 = str.split(":");
			if(split2.length >= 2) {
				map.put(split2[0], split2[1]);
			}
		}
		return map;
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
		mv.setViewName("system/stockbatch/stockbatch_edit");
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
		pd = stockbatchService.findById(pd);	//根据ID读取
		mv.setViewName("system/stockbatch/stockbatch_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除StockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			stockbatchService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出StockBatch到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = stockbatchService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("stock_batch_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("store_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("sku_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("sku_name"));	    //4
			vpd.put("var5", varOList.get(i).getString("sku_encode"));	    //5
			vpd.put("var6", varOList.get(i).getString("batch_code"));	    //6
			vpd.put("var7", varOList.get(i).get("quantity").toString());	//7
			vpd.put("var8", varOList.get(i).getString("unit_name"));	    //8
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
	
	@RequestMapping(value="/goPrint")
	public ModelAndView goPrint()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("erp/stockbatch/stockbatch_print");
		mv.addObject("msg", "print");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value="/printbatchPage")
	public ModelAndView printbatchPage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		int beginPos=0;
		int endPos=0;
		String batchNo="";
			if(pd!=null&&pd.get("beginPos")!=null)
			{
				beginPos=Integer.parseInt(pd.get("beginPos").toString());
			}
			if(pd!=null&&pd.get("endPos")!=null)
			{
				 endPos=Integer.parseInt(pd.get("endPos").toString());
			}
			batchNo=pd.get("batch_code").toString();
		
		List<PageData>  imgListPd=new ArrayList<PageData>();
		try
		{
			File dir = new File(PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE);
			if (!dir.isDirectory()) 
			{
				dir.mkdir();
			}
			if(batchNo!="")
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
		mv.setViewName("erp/stockbatch/printbatchpage");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("imglist", imgListPd);
		return mv;
	}
	
    private String autoGenericCode(Integer code, int num) {
        String result = "";
        result = String.format("%0" + num + "d",code);
        return result;
    }
	
}
