package com.fh.controller.stockgoods;

import java.io.PrintWriter;
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

import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.service.stockgoods.StockGoodsManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;


/** 
 * 说明：库存
 * 创建人：FH Q313596790
 * 创建时间：2017-09-13
 */
@Controller
@RequestMapping(value="/stockgoods")
public class StockGoodsController extends BaseController {
	
	String menuUrl = "stockgoods/list.do"; //菜单地址(权限用)
	@Resource(name="stockgoodsService")
	private StockGoodsManager stockgoodsService;
	
	@Resource(name="szystoreService")
	private SzyStoreManager szyStoreManager;
	
	@Resource(name="stockbatchService")
	private StockBatchManager stockbatchService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增StockGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("stock_id", this.get32UUID());	//主键
		stockgoodsService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除StockGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		stockgoodsService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改StockGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		stockgoodsService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表StockGoods");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sku_encode = pd.getString("sku_encode");				//关键词检索条件
		String keywords = pd.getString("keywords");				//关键词检索条件
		String store_sn=pd.getString("ware");	
		
		if(null != sku_encode && !"".equals(sku_encode)){
			pd.put("sku_encode", sku_encode.trim());
		}
		
		if(null != keywords && !"".equals(keywords)){
			pd.put("sku_name", keywords.trim());
		}
		
		if(null != store_sn && !"".equals(store_sn)){
			pd.put("store_sn", store_sn);
		}
		String[] types = {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_EXPRESS,StoreTypeConstants.STORE_GIFT,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_QUARANTINE,StoreTypeConstants.STORE_VIRTUAL};
		List<PageData> list = GetOwnStore.getOwnStore(types);
        if(CollectionUtils.isNotEmpty(list)) {
        	mv.addObject("storeList", list);
        	if(pd.get("stores") == null) {
            	pd.put("store_sn", list.get(0).getString("store_sn"));
            }else {
            	pd.put("store_sn",pd.get("stores"));
            }
        	page.setPd(pd);
    		List<PageData>	varList = stockgoodsService.list(page);	//列出StockGoods列表
    		mv.addObject("varList", varList);
        }else {
        	mv.addObject("varList", "");
        }
		mv.setViewName("erp/stockgoods/stockgoods_list");
//		PageData pdStore=new PageData();
//		List<PageData> storeList=szyStoreManager.listAll(pdStore);
//		
//		mv.addObject("storelist", storeList);
		
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
		mv.setViewName("system/stockgoods/stockgoods_edit");
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
		pd = stockgoodsService.findById(pd);	//根据ID读取
		mv.setViewName("system/stockgoods/stockgoods_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除StockGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			stockgoodsService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出StockGoods到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("库存ID");	//1
		titles.add("仓库ID");	//2
		titles.add("商品ID");	//3
		titles.add("skuid");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("库存");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = stockgoodsService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("stock_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("store_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("goods_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("sku_id").toString());	//4
			vpd.put("var5", varOList.get(i).get("min_number_warn").toString());	//5
			vpd.put("var6", varOList.get(i).get("max_number_warn").toString());	//6
			vpd.put("var7", varOList.get(i).get("goods_number").toString());	//7
			vpd.put("var8", varOList.get(i).get("first_goods_number").toString());	//8
			vpd.put("var9", varOList.get(i).getString("unit_cost"));	    //9
			vpd.put("var10", varOList.get(i).get("is_deleted").toString());	//10
			vpd.put("var11", varOList.get(i).getString("stock_remark"));	    //11
			vpd.put("var12", varOList.get(i).get("admin_id").toString());	//12
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
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/defaultexcel")
	public ModelAndView exportDefaultExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出StockGoods到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sku_encode = pd.getString("sku_encode");
		String sku_name = pd.getString("sku_name");
		if (null != sku_encode && !"".equals(sku_encode)) {
			pd.put("sku_encode", sku_encode.trim());
		}
		if (null != sku_name && !"".equals(sku_name)) {
			pd.put("sku_name", sku_name.trim());
		}
		List<PageData> varOList = stockgoodsService.excelList(pd);
		RedisUtil.getStoreInfoBySn(varOList, "store_sn", "store_id", "store_id");
		List<PageData> varList = new ArrayList<PageData>();
		int maxBatch = 0;
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", objectToString(i + 1)); // 1
			vpd.put("var2", objectToString(varOList.get(i).get("store_sn"))); // 2
			vpd.put("var3", objectToString(varOList.get(i).get("store_name"))); // 3
			vpd.put("var4", objectToString(varOList.get(i).get("sku_encode"))); // 4
			vpd.put("var5", objectToString(varOList.get(i).get("sku_name"))); // 5
			vpd.put("var6", objectToString(varOList.get(i).get("goods_number"))); // 6
			vpd.put("var7", objectToString(varOList.get(i).get("unit_name"))); // 7
			vpd.put("var8", objectToString(varOList.get(i).get("spec"))); // 8
			String attr_cate = objectToString(varOList.get(i).get("attr_cate"));
			String cate = "商品";
			if ("0".equals(attr_cate)) {
				cate = "原材料";
			}
			if ("1".equals(attr_cate)) {
				cate = "包材";
			}
			vpd.put("var9", cate); // 9
			PageData pageData = new PageData();
			pageData.put("sku_id", varOList.get(i).get("sku_id"));
			pageData.put("store_id", varOList.get(i).get("store_id"));
			List<PageData> stockBatchs = stockbatchService.listAllByParam(pageData);
			if(stockBatchs.size() > maxBatch) {
				maxBatch = stockBatchs.size();
			}
			if(CollectionUtils.isNotEmpty(stockBatchs)) {
				int index = 10;
				for(int j=0;j < stockBatchs.size();j++) {
					vpd.put("var"+index, objectToString(stockBatchs.get(j).get("batch_code")));
					index++;
					vpd.put("var"+index, objectToString(stockBatchs.get(j).get("quantity")));
					index++;
				}
			}
			varList.add(vpd);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("序号"); // 1
		titles.add("仓库编码"); // 2
		titles.add("仓库名称"); // 3
		titles.add("商品编码"); // 4
		titles.add("商品名称"); // 5
		titles.add("库存"); // 6
		titles.add("单位"); // 7
		titles.add("规格"); // 8
		titles.add("属性分类"); // 9
		for(int i=0;i<maxBatch;i++) {
			titles.add("批次编码");
			titles.add("批次数量");
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	String objectToString(Object object) {
		if (object != null) {
			return object.toString();
		}
		return "";
	}
	
}
