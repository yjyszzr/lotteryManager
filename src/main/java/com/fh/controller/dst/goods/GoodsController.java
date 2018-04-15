package com.fh.controller.dst.goods;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.dst.goodssku.GoodsSkuManager;
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.szystockgoods.SzyStockGoodsManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.libgoodsshop.LibGoodsShopManager;
import com.fh.service.goods.GoodsManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.MapUtil;
import com.fh.util.MergeUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * 说明：商品管理 创建人：FH Q313596790 创建时间：2017-09-14
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {

	String menuUrl = "goods/list.do"; // 菜单地址(权限用)
	@Resource(name = "goodsService")
	private GoodsManager goodsService;
	@Resource(name = "goodsskuService")
	private GoodsSkuManager goodsSkuManager;
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	@Resource(name = "libgoodsshopService")
	private LibGoodsShopManager libGoodsShopManager;
	
	@Resource(name="szystockgoodsService")
	private SzyStockGoodsManager szystockgoodsService;
	
	@Resource(name="stockbatchService")
    private StockBatchManager stockbatchService;
	
	@Resource(name="inboundService")
	private InboundManager inboundService;
	
	@Resource(name="pgtoutboundService")
	private PgtOutboundManager outboundService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Goods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("goods_id", this.get32UUID()); //主键
		pd.put("sku_id", "0"); // 备注4
		pd.put("cat_id", "0"); // 备注4
		pd.put("brand_id", "0"); // 商品品牌
		pd.put("shelf_life", "0"); // 保持期
		pd.put("store_id", "0"); // 仓库
		pd.put("valuation_type", "0"); // 计价方式
		pd.put("member_price", "0.00"); // 会员价
		pd.put("member_rebate", "0"); // 开启会员返利
		pd.put("group_goods_price", "0.00"); // 组合商品价
		pd.put("goods_images", ""); // 商品图片
		pd.put("goods_attr", ""); // 商品属性
		pd.put("is_delete", "0"); // 是否已删除
		pd.put("is_open_batch_shelf_life", "0"); // 是否开启批次保质期
		pd.put("shelf_life_warn_days", "0"); // 预警天数
		pd.put("is_stock_warn", "0"); // 库存预警
		pd.put("min_number_warn", "0"); // 最低预警值
		pd.put("max_number_warn", "0"); // 最高预警值
		pd.put("is_first_set", "0"); // 是否进行初期设置
		pd.put("remark", ""); // 备注24
		pd.put("is_comb_prod", "0"); // 备注25
		pd.put("admin_id", "0"); // 备注26
		pd.put("add_time", System.currentTimeMillis() / 1000); // 添加时间
		pd.put("last_time", System.currentTimeMillis() / 1000); // 添加时间

		try {
			String weight = pd.getString("weight"); // 关键词检索条件
			if (null == weight || ("".equals(weight.trim()))) {
				pd.put("weight", "0.000");
			}
			goodsService.save(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "fail");
			if ("error_400".equals(e.getMessage())) {
				mv.addObject("msg", "商品编码不能相同");
			} else if ("error_500".equals(e.getMessage())) {
				mv.addObject("msg", "商品条码不能相同");
			}
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, String> delete() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Goods");
		Map<String, String> mv = new HashMap<>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			goodsService.delete(pd);
		} catch (Exception e) {
			String info = e.getMessage();
			mv.put("msg","fail");
			mv.put("info",info);
			return mv;
		}
		
		mv.put("msg","success");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/synshop")
	public void synshop(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "同步Goods");
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdGoods = goodsService.findByGoodsId(pd); // 根据ID读取

		PageData pdGoodsSku = goodsSkuManager.findByGoodsId(pd); // 根据ID读取

		/*
		 * `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID', `goods_name`
		 * varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称', `cat_id` int(11) NOT NULL
		 * DEFAULT '0' COMMENT '商品分类ID', `sku_id` int(11) NOT NULL DEFAULT '0' COMMENT
		 * 'sku ID', `goods_barcode` varchar(60) NOT NULL DEFAULT '' COMMENT '商品条形码',
		 * `brand_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品品牌id',
		 */
		SortedMap<String, Object> pdShop = new TreeMap<String, Object>();
		pdShop.put("goods_id", pdGoods.getString("goods_id"));
		pdShop.put("goods_name", pdGoods.getString("goods_name"));
		pdShop.put("cat_id", pdGoods.getString("cat_id"));
		// pdShop.put("sku_id", pdGoods.get("sku_id"));
		pdShop.put("goods_barcode", pdGoods.getString("goods_barcode"));
		pdShop.put("brand_id", pdGoods.getString("brand_id"));
		pdShop.put("goods_weight", pdGoods.getString("weight"));
		pdShop.put("goods_price", pdGoodsSku.getString("goods_price"));
		/*
		 * `sku_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'SKU编号 `sku_name`
		 * varchar(100) NOT NULL DEFAULT '' COMMENT 'SKU商品名称', `goods_id` int(11) NOT
		 * NULL DEFAULT '0' COMMENT '商品ID', `spec_ids` varchar(255) NOT NULL DEFAULT ''
		 * COMMENT '商品规格id串', `spec_vids` varchar(255) NOT NULL DEFAULT '' COMMENT
		 * '商品规格值id串', `sku_encode` varchar(60) NOT NULL DEFAULT '' COMMENT '商品编码'
		 * `sku_barcode` varchar(20) NOT NULL DEFAULT '' COMMENT '商品条形码'
		 */
		pdShop.put("sku_id", pdGoodsSku.getString("sku_id"));
		pdShop.put("sku_name", pdGoodsSku.getString("sku_name"));
		pdShop.put("spec_ids", pdGoodsSku.getString("spec_ids"));
		pdShop.put("spec_vids", pdGoodsSku.getString("spec_vids"));
		pdShop.put("sku_encode", pdGoodsSku.getString("sku_encode"));
		pdShop.put("sku_barcode", pdGoodsSku.getString("sku_barcode"));

		// String strjson = JsonUtils.beanToJSONString(pdShop);
		// String url = "http://localhost:82/goods/putgoods";
		// String postResult = NetWorkUtil.doPost(DbFH.getGoodsurl(), pdShop, "UTF-8",
		// true);
		boolean isok = false;
		/*
		 * if (postResult != null && postResult.length() > 0) { try { JsonObject
		 * jsonParams = JsonUtils.StringToJsonObject(postResult); int code =
		 * JsonUtils.retrieveIntValue(jsonParams, "code"); if (code == 0) { isok = true;
		 * } } catch (Exception e) { logger.error(e.getMessage(), e); } }
		 */
		try {
			synShopGoods(pdShop);
			isok = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (isok == true) {
			// 更改同步状态
			{
				PageData pdSyn = new PageData();
				pdSyn.put("syn_date", new Date());
				pdSyn.put("goods_id", pd.get("goods_id"));
				goodsService.updateSynState(pdSyn);
			}
			out.write("success");
		} else {
			out.write("error");
		}
		out.close();
	}

	private void synShopGoods(Map<String, Object> pdShop) throws Exception {

		PageData pdGoods = new PageData();

		pdGoods.put("goods_id_from_erp", pdShop.get("goods_id"));

		pdGoods.put("sku_id_from_erp", pdShop.get("sku_id"));

		pdGoods.put("goods_name", pdShop.get("goods_name"));
		pdGoods.put("cat_id", "0");
		pdGoods.put("goods_barcode", pdShop.get("goods_barcode"));
		pdGoods.put("goods_sn", pdShop.get("sku_encode"));
		pdGoods.put("goods_price", pdShop.get("goods_price"));
		pdGoods.put("goods_weight", pdShop.get("goods_weight"));
		// goodsDo.setGoods_name(MapUtils.retrieveStringValue(pdShop, "goods_name"));
		// goodsDo.setCat_id(0);
		// goodsDo.setSku_id_from_erp(MapUtils.retrieveIntValue(pdShop, "sku_id"));
		// goodsDo.setGoods_barcode(MapUtils.retrieveStringValue(pdShop,
		// "goods_barcode"));
		// goodsDo.setGoods_sn(MapUtils.retrieveStringValue(pdShop, "sku_encode"));
		// goodsDo.setGoods_price(MapUtils.retrieveBigDecimalValue(pdShop,
		// "goods_price"));
		// goodsDo.setGoods_sn(MapUtils.retrieveStringValue(pdShop, "sku_encode"));
		// goodsDo.setGoods_weight(MapUtils.retrieveStringValue(pdShop,
		// "goods_weight"));

		pdGoods.put("brand_id", "0");
		pdGoods.put("shop_id", "0");
		pdGoods.put("sku_open", "0");
		pdGoods.put("sku_id", "0");
		pdGoods.put("goods_number", "0");
		pdGoods.put("warn_number", "0");
		pdGoods.put("goods_image", "");

		pdGoods.put("click_count", "0");
		pdGoods.put("comment_num", "0");
		pdGoods.put("sale_num", "0");
		pdGoods.put("goods_audit", "0");

		pdGoods.put("goods_status", "0");
		pdGoods.put("goods_audit", "0");
		pdGoods.put("is_delete", "0");
		pdGoods.put("is_best", "0");

		pdGoods.put("is_new", "0");
		pdGoods.put("is_hot", "0");
		pdGoods.put("is_promote", "0");
		pdGoods.put("goods_sort", "0");

		Long time = System.currentTimeMillis() / 1000;
		// goodsDo.setAdd_time(time.intValue());
		// goodsDo.setLast_time(time.intValue());

		pdGoods.put("add_time", time.intValue());
		pdGoods.put("last_time", time.intValue());

		
		PageData pdLib = libGoodsShopManager.findById(pdGoods);
		if (pdLib != null) {
			pdLib.put("goods_id_from_erp", pdShop.get("goods_id"));
			pdLib.put("sku_id_from_erp", pdShop.get("sku_id"));
			pdLib.put("goods_name", pdShop.get("goods_name"));
			pdLib.put("goods_barcode", pdShop.get("goods_barcode"));
			pdLib.put("goods_sn", pdShop.get("sku_encode"));
			pdLib.put("goods_price", pdShop.get("goods_price"));
			pdLib.put("goods_weight", pdShop.get("goods_weight"));
			libGoodsShopManager.edit(pdLib);
		} else {
			libGoodsShopManager.save(pdGoods);
		}
		// goodsDo.setBrand_id(0);

	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Goods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String weight = pd.getString("weight"); // 关键词检索条件
			if (null == weight || ("".equals(weight.trim()))) {
				pd.put("weight", "0.000");
			}
			goodsService.edit(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "fail");
			if ("error_400".equals(e.getMessage())) {
				mv.addObject("msg", "商品编码不能相同");
			} else if ("error_500".equals(e.getMessage())) {
				mv.addObject("msg", "商品条码不能相同");
			}
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/querylist")
	public ModelAndView querylist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Goods");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sku_encode = pd.getString("sku_encode");
		String sku_barcode = pd.getString("sku_barcode");
		String sku_name = pd.getString("sku_name");
		if (null != sku_encode && !"".equals(sku_encode)) {
			pd.put("sku_encode", sku_encode.trim());
		}
		if (null != sku_barcode && !"".equals(sku_barcode)) {
			pd.put("sku_barcode", sku_barcode.trim());
		}
		if (null != sku_name && !"".equals(sku_name)) {
			pd.put("sku_name", sku_name.trim());
		}
		page.setPd(pd);
		List<PageData> varList = goodsService.querylist(page); // 列出Goods列表
		mv.setViewName("erp/goods/goods_querylist");
		PageData pageData = new PageData();
		pageData.put("BIANMA", Const.GOODS_TYPE);
		pageData = dictionariesService.findByBianma(pageData);
		mv.addObject("dictionaries", pageData);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Goods");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = goodsService.list(page); // 列出Goods列表
		mv.setViewName("erp/goods/goods_list");
		PageData pageData = new PageData();
		pageData.put("BIANMA", Const.GOODS_TYPE);
		pageData = dictionariesService.findByBianma(pageData);
		mv.addObject("dictionaries", pageData);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value = "/stockinfo")
	public ModelAndView stockinfo(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = MapUtil.mapStringToPageData(pd.getString("data"));
		if(pd.get("supplier_id") != null) {
			RedisUtil.getSupplierById(pd, "supplier_id", "supplier_name", "supplier_name");
		}
		page.setPd(pd);
		List<PageData> varList = szystockgoodsService.stockBySkuIdlistPage(page);
		mv.setViewName("erp/query/querystock_list");
		float totalStock = 0L;
		if(!CollectionUtils.isEmpty(varList)) {
			RedisUtil.getStoreInfoById(varList, "store_id", "store_sn", "store_sn");
			RedisUtil.getStoreInfoById(varList, "store_id", "store_name", "store_name");
			for(PageData pageData : varList) {
				pageData.put("sku_encode", pd.getString("sku_encode"));
				pageData.put("sku_name", pd.getString("sku_name"));
				pageData.put("unit_name", pd.getString("unit_name"));
				pageData.put("spec", pd.getString("spec"));
				totalStock += Float.parseFloat(pageData.get("goods_number").toString());
			}
		}
		pd.put("totalStock", totalStock);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value = "/stockbatch")
	public ModelAndView stockbatch(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = MapUtil.mapStringToPageData(pd.getString("data"));
		page.setPd(pd);
		List<PageData> varList = stockbatchService.list(page);
		mv.setViewName("erp/query/querystockbatch_list");
		if(!CollectionUtils.isEmpty(varList)) {
			for(PageData pageData : varList) {
				pageData.put("store_sn", pd.getString("store_sn"));
				pageData.put("store_name", pd.getString("store_name"));
				pageData.put("spec", pd.getString("spec"));
			}
		}
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value = "/inOutBound")
	public ModelAndView inOutBound(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = MapUtil.mapStringToPageData(pd.getString("data"));
		List<PageData> inboundList = inboundService.listInbound(pd);
		List<PageData> outboundList = outboundService.listOutboundInfo(pd);
		List<PageData> varList = MergeUtil.mergeInBoundOutBoundByGoodsStore(inboundList, outboundList);
		mv.setViewName("erp/query/queryinoutbound_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> untils = goodsService.listUnits(pd);
		PageData pageData = new PageData();
		pageData.put("BIANMA", Const.GOODS_TYPE);
		pageData = dictionariesService.findByBianma(pageData);
		mv.addObject("utils", untils);
		mv.addObject("dictionaries", pageData);
		mv.setViewName("erp/goods/goods_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = goodsService.findById(pd); // 根据ID读取
		mv.setViewName("erp/goods/goods_edit");
		List<PageData> untils = goodsService.listUnits(pd);
		mv.addObject("utils", untils);
		PageData pageData = new PageData();
		pageData.put("BIANMA", Const.GOODS_TYPE);
		pageData = dictionariesService.findByBianma(pageData);
		mv.addObject("dictionaries", pageData);
		String cat_id1_str = "";
		String cat_id2_str = "";
		PageData pageData0 = new PageData();
		pageData0.put("PARENT_ID", pageData.getString("DICTIONARIES_ID"));
		List<PageData> cat_id1s = dictionariesService.findByParentId(pageData0);
		for (PageData cat_id1 : cat_id1s) {
			if (cat_id1.getString("DICTIONARIES_ID").equals(pd.getString("cat_id1"))) {
				cat_id1.put("selected", "selected");
				cat_id1_str = cat_id1.getString("DICTIONARIES_ID");
			} else {
				cat_id1.put("selected", "");
			}
		}
		PageData pageData1 = new PageData();
		pageData1.put("PARENT_ID", cat_id1_str);
		List<PageData> cat_id2s = dictionariesService.findByParentId(pageData1);
		for (PageData cat_id2 : cat_id2s) {
			if (cat_id2.getString("DICTIONARIES_ID").equals(pd.getString("cat_id2"))) {
				cat_id2.put("selected", "selected");
				cat_id2_str = cat_id2.getString("DICTIONARIES_ID");
			} else {
				cat_id2.put("selected", "");
			}
		}
		PageData pageData2 = new PageData();
		pageData2.put("PARENT_ID", cat_id2_str);
		List<PageData> cat_id3s = dictionariesService.findByParentId(pageData2);
		for (PageData cat_id3 : cat_id3s) {
			if (cat_id3.getString("DICTIONARIES_ID").equals(pd.getString("cat_id3"))) {
				cat_id3.put("selected", "selected");
			} else {
				cat_id3.put("selected", "");
			}
		}
		pd.put("cat_id1s", cat_id1s);
		pd.put("cat_id2s", cat_id2s);
		pd.put("cat_id3s", cat_id3s);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Goods");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			goodsService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Goods到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("商品名称"); // 1
		titles.add("商品分类"); // 2
		titles.add("备注4"); // 3
		titles.add("商品条形码"); // 4
		titles.add("商品单位"); // 5
		titles.add("商品品牌"); // 6
		titles.add("保持期"); // 7
		titles.add("仓库"); // 8
		titles.add("计价方式"); // 9
		titles.add("会员价"); // 10
		titles.add("开启会员返利"); // 11
		titles.add("供应商"); // 12
		titles.add("组合商品价"); // 13
		titles.add("商品图片"); // 14
		titles.add("商品属性"); // 15
		titles.add("是否已删除"); // 16
		titles.add("是否开启批次保质期"); // 17
		titles.add("预警天数"); // 18
		titles.add("库存预警"); // 19
		titles.add("最低预警值"); // 20
		titles.add("最高预警值"); // 21
		titles.add("是否进行初期设置"); // 22
		titles.add("备注24"); // 23
		titles.add("备注25"); // 24
		titles.add("备注26"); // 25
		titles.add("添加时间"); // 26
		titles.add("备注28"); // 27
		titles.add("单位名称"); // 28
		titles.add("是否批次管理"); // 29
		titles.add("规格"); // 30
		titles.add("属性分类"); // 31
		titles.add("税率"); // 32
		dataMap.put("titles", titles);
		List<PageData> varOList = goodsService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("goods_name")); // 1
			vpd.put("var2", varOList.get(i).get("cat_id").toString()); // 2
			vpd.put("var3", varOList.get(i).get("sku_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("goods_barcode")); // 4
			vpd.put("var5", varOList.get(i).get("unit_id").toString()); // 5
			vpd.put("var6", varOList.get(i).get("brand_id").toString()); // 6
			vpd.put("var7", varOList.get(i).get("shelf_life").toString()); // 7
			vpd.put("var8", varOList.get(i).get("store_id").toString()); // 8
			vpd.put("var9", varOList.get(i).get("valuation_type").toString()); // 9
			vpd.put("var10", varOList.get(i).getString("member_price")); // 10
			vpd.put("var11", varOList.get(i).get("member_rebate").toString()); // 11
			vpd.put("var12", varOList.get(i).get("supplier_id").toString()); // 12
			vpd.put("var13", varOList.get(i).getString("group_goods_price")); // 13
			vpd.put("var14", varOList.get(i).getString("goods_images")); // 14
			vpd.put("var15", varOList.get(i).getString("goods_attr")); // 15
			vpd.put("var16", varOList.get(i).get("is_delete").toString()); // 16
			vpd.put("var17", varOList.get(i).get("is_open_batch_shelf_life").toString()); // 17
			vpd.put("var18", varOList.get(i).get("shelf_life_warn_days").toString()); // 18
			vpd.put("var19", varOList.get(i).get("is_stock_warn").toString()); // 19
			vpd.put("var20", varOList.get(i).get("min_number_warn").toString()); // 20
			vpd.put("var21", varOList.get(i).get("max_number_warn").toString()); // 21
			vpd.put("var22", varOList.get(i).get("is_first_set").toString()); // 22
			vpd.put("var23", varOList.get(i).getString("remark")); // 23
			vpd.put("var24", varOList.get(i).get("is_comb_prod").toString()); // 24
			vpd.put("var25", varOList.get(i).get("admin_id").toString()); // 25
			vpd.put("var26", varOList.get(i).get("add_time").toString()); // 26
			vpd.put("var27", varOList.get(i).get("last_time").toString()); // 27
			vpd.put("var28", varOList.get(i).getString("unit_name")); // 28
			vpd.put("var29", varOList.get(i).get("isopen_batch").toString()); // 29
			vpd.put("var30", varOList.get(i).getString("spec")); // 30
			vpd.put("var31", varOList.get(i).get("attr_cate").toString()); // 31
			vpd.put("var32", varOList.get(i).get("tax_rate").toString()); // 32
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 判断商品编码是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasencode")
	@ResponseBody
	public Object hasencode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// var goods_id = $.trim($("#goods_id").val());
			String goods_id = pd.getString("goods_id");
			if (goods_id == null || goods_id.trim().length() == 0) {
				PageData pdSku = goodsSkuManager.findByEncode(pd);
				if (pdSku != null) {
					errInfo = "error";
				}
			} else {
				PageData pdSku = goodsSkuManager.findByEncodeAndGoodsId(pd);
				if (pdSku != null) {
					errInfo = "error";
				}
			}

		} catch (Exception e) {
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断商品编码是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasbarcode")
	@ResponseBody
	public Object hasbarcode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// var goods_id = $.trim($("#goods_id").val());
			String goods_id = pd.getString("goods_id");
			if (goods_id == null || goods_id.trim().length() == 0) {
				PageData pdSku = goodsSkuManager.findByBarcode(pd);
				if (pdSku != null) {
					errInfo = "error";
				}
			} else {
				PageData pdSku = goodsSkuManager.findByBarcodeAndGoodsId(pd);
				if (pdSku != null) {
					errInfo = "error";
				}
			}

		} catch (Exception e) {
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
	
	@ResponseBody
	@RequestMapping(value = "/refreshcache")
	public Map<String, String> refreshCache() throws Exception {
		// 判断是否意见有总仓
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			RedisUtil.setGoodsInfo(goodsService.listAll(pd));
		} catch (Exception e) {
			map.put("info", "刷新失败请稍后再试、或联系管理员");
			map.put("msg", "fail");
			return map;
		}
		map.put("info", "刷新成功");
		map.put("msg", "success");
		return map;
	}
}
