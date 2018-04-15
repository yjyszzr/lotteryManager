package com.fh.controller.dst.otherinstoragestockbatch;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.goodssku.GoodsSkuManager;
import com.fh.service.dst.otherinstoragedetail.OtherInstorageDetailManager;
import com.fh.service.dst.otherinstoragestockbatch.OtherInstorageStockBatchManager;

/** 
 * 说明：其它入库批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-21
 */
@Controller
@RequestMapping(value="/otherinstoragestockbatch")
public class OtherInstorageStockBatchController extends BaseController {
	
	String menuUrl = "otherinstoragestockbatch/list.do"; //菜单地址(权限用)
	@Resource(name="otherinstoragestockbatchService")
	private OtherInstorageStockBatchManager otherinstoragestockbatchService;
	@Resource(name="otherinstoragedetailService")
	private OtherInstorageDetailManager otherinstoragedetailService;
	@Resource(name="goodsskuService")
	private GoodsSkuManager goodsskuService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OtherInstorageStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//批次号
		String  batch_code = pd.getString("batch_code");
		//校验批次号是否合法
		String sku_barcode = pd.getString("sku_barcode");
		//批次号的前13位是物料条码，如果不是则不合法
		if(!batch_code.startsWith(sku_barcode)) {
			mv.addObject("pd",pd);
			mv.addObject("msg","save");
			mv.addObject("batch_code_error",0);
			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
			return mv;
		}
		//校验批次号是否在批次表中存在，不存在则不合法
		//如果末位是x则不校验
		if(batch_code.endsWith("x") ||batch_code.endsWith("X")) {
			
		}else {
		PageData queryBatchTable = new PageData();
		//前19位为批次码，后1位为子码
		String batch_no = batch_code.substring(0, 19);
		String latest_no = batch_code.substring(19);
		//#{batch_no} and latest_no >=#{latest_no}
		queryBatchTable.put("batch_no", batch_no);
		queryBatchTable.put("latest_no", latest_no);
		List bs = otherinstoragestockbatchService.listBatch(queryBatchTable);
		if(bs==null || bs.size()==0) { 
			mv.addObject("pd",pd);
			mv.addObject("msg","save");
			mv.addObject("batch_code_error",2);
			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
			return mv;
		}
		}
		//校验批次号,是否重复
		PageData queryBatch_code = new PageData();
		queryBatch_code.put("batch_code",batch_code);
		//一个订单范围内校验重复
		queryBatch_code.put("other_instorage_code",pd.getString("other_instorage_code"));
		List<PageData> exists = otherinstoragestockbatchService.listAll(queryBatch_code);
		if(exists!=null && exists.size()>0) {
			mv.addObject("pd",pd);
			mv.addObject("msg","save");
			mv.addObject("batch_code_error",1);
			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
			return mv;
		}
				
//		pd.put("other_instorage_stock_batch_id", this.get32UUID());	//主键
		otherinstoragestockbatchService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除OtherInstorageStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		otherinstoragestockbatchService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OtherInstorageStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//批次号
		String  batch_code = pd.getString("batch_code");
		//校验批次号是否合法
		String sku_barcode = pd.getString("sku_barcode");
		//批次号的前13位是物料编码，如果不是则不合法
		if(!batch_code.startsWith(sku_barcode)) {
			mv.addObject("pd",pd);
			mv.addObject("msg","edit");
			mv.addObject("batch_code_error",0);
			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
			return mv;
		}
//		//校验批次号是否合法
//		String sku_encode = pd.getString("sku_encode");
//		//批次号的前13位是物料编码，如果不是则不合法
//		if(!batch_code.startsWith(sku_encode)) {
//			mv.addObject("pd",pd);
//			mv.addObject("msg","edit");
//			mv.addObject("batch_code_error",0);
//			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
//			return mv;
//		}
		//校验批次号是否在批次表中存在，不存在则不合法
		PageData queryBatchTable = new PageData();
		//前19位为批次码，后1位为子码
		String batch_no = batch_code.substring(0, 19);
		String latest_no = batch_code.substring(19);
		//#{batch_no} and latest_no >=#{latest_no}
		queryBatchTable.put("batch_no", batch_no);
		queryBatchTable.put("latest_no", latest_no);
		List bs = otherinstoragestockbatchService.listBatch(queryBatchTable);
		if(bs==null || bs.size()==0) {
			mv.addObject("pd",pd);
			mv.addObject("msg","edit");
			mv.addObject("batch_code_error",2);
			mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
			return mv;
		}
		if(batch_code!=null && batch_code!="") {
		//校验批次号,是否重复
				PageData queryBatch_code = new PageData();
				queryBatch_code.put("batch_code",batch_code);
				queryBatch_code.put("other_instorage_code",pd.getString("other_instorage_code"));
				List<PageData> exists = otherinstoragestockbatchService.listAll(queryBatch_code);
				if(exists!=null && exists.size()>0) {
					if(!batch_code.equals(exists.get(0).getString("batch_code"))) {
						mv.addObject("pd",pd);
						mv.addObject("msg","edit");
						mv.addObject("batch_code_error",1);
						mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
						return mv;
					}
					
				}
		}
		otherinstoragestockbatchService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherInstorageStockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = otherinstoragestockbatchService.list(page);	//列出OtherInstorageStockBatch列表
		mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/listAll")
	public ModelAndView listAll(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherInstorageStockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = otherinstoragestockbatchService.listAll(pd);	//列出OtherInstorageStockBatch列表
		mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_list");
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
		//other_instorage_detail_id
		pd = otherinstoragedetailService.findById(pd);
		
		PageData goods = goodsskuService.findByEncode(pd);
		pd.put("unit_name", pd.getString("unit"));
		pd.put("sku_barcode", goods.getString("sku_barcode"));
		mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
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
		pd = otherinstoragestockbatchService.findById(pd);	//根据ID读取
		mv.setViewName("dst/otherinstoragestockbatch/otherinstoragestockbatch_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除OtherInstorageStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			otherinstoragestockbatchService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出OtherInstorageStockBatch到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("入库编码");	//2
		titles.add("入库明细ID");	//3
		titles.add("sku_id");	//4
		titles.add("sku_name");	//5
		titles.add("sku编码");	//6
		titles.add("批次号");	//7
		titles.add("数量");	//8
		titles.add("规格");	//9
		titles.add("单位");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = otherinstoragestockbatchService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("other_instorage_stock_batch_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("other_instorage_code"));	    //2
			vpd.put("var3", varOList.get(i).get("other_instorage_detail_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("sku_id").toString());	//4
			vpd.put("var5", varOList.get(i).getString("sku_name"));	    //5
			vpd.put("var6", varOList.get(i).getString("sku_encode"));	    //6
			vpd.put("var7", varOList.get(i).getString("batch_code"));	    //7
			vpd.put("var8", varOList.get(i).get("quantity").toString());	//8
			vpd.put("var9", varOList.get(i).getString("spec"));	    //9
			vpd.put("var10", varOList.get(i).getString("unit_name"));	    //10
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
