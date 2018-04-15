package com.fh.controller.erp.goodsshoperpstock;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.erp.goodsshoperpstock.GoodsShopErpStockManager;
import com.fh.service.erp.storeshop.StoreShopManager;
import com.fh.service.goods.GoodsManager;
import com.fh.service.stockgoods.StockGoodsManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：商城店铺与ERP仓库对应 创建人：FH Q313596790 创建时间：2017-10-02
 */
@Controller
@RequestMapping(value = "/goodsshoperpstock")
public class GoodsShopErpStockController extends BaseController {

	String menuUrl = "goodsshoperpstock/list.do"; // 菜单地址(权限用)
	@Resource(name = "goodsshoperpstockService")
	private GoodsShopErpStockManager goodsshoperpstockService;
	@Resource(name = "storeshopService")
	private StoreShopManager storeshopService;
	@Resource(name = "stockgoodsService")
	private StockGoodsManager stockgoodsService;
	@Resource(name = "goodsService")
	private GoodsManager goodsService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增GoodsShopErpStock");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("goods_id", this.get32UUID()); //主键
		goodsshoperpstockService.save(pd);
		mv.addObject("msg", "success");
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
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除GoodsShopErpStock");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsshoperpstockService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改GoodsShopErpStock");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsshoperpstockService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		logBefore(logger, Jurisdiction.getUsername() + "列表GoodsShopErpStock");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		User user = getSessionUser();
		// 默认数据
		PageData pdUser = new PageData();
		if (user.getROLE_ID().equals(Const.SYSTEM_MANAGER_ID)) {
			// 本用user_id store_name
			pdUser.put("user_id", "");
		} else {
			pdUser.put("user_id", user.getUSER_ID());
		}
		List<PageData> varstoreList = storeshopService.listAllByUserId(pdUser);
		String store_id = pd.getString("store_id"); // 关键词检索条件
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		} else {
			if (varstoreList.size() > 0) {
				PageData pageTemp = varstoreList.get(0);
				pd.put("store_id", pageTemp.getString("store_id"));

			} else {
				pd.put("store_id", "0");
			}
		}
		PageData pdShop = storeshopService.findByStoreIdOnly(pd);

		String shop_name = "";
		String shop_id = "0";
		if (pdShop != null) {
			shop_name = pdShop.getString("shop_name");
			shop_id = pdShop.getString("shop_id");
		}
		page.setPd(pd);
		// 列出GoodsShopErpStock列表
		List<PageData> varList = stockgoodsService.listByStore(page);

		PageData pdshopGoodsQuery = new PageData();
		pdshopGoodsQuery.put("shop_id", shop_id);
		for (PageData PageData : varList) {
			String sku_encode = PageData.getString("sku_encode");

			pdshopGoodsQuery.put("goods_sn", sku_encode);
			List<PageData> list = goodsshoperpstockService.listShopGoodsAll(pdshopGoodsQuery);
			if (list != null && list.size() == 1) {
				PageData pdshopGoods = goodsshoperpstockService.findByIdByShopAndSn(pdshopGoodsQuery);
				if (pdshopGoods != null) {
					PageData.put("shop_name", shop_name);
					PageData.put("shop_goods_id", pdshopGoods.getString("goods_id"));
					PageData.put("shop_goods_sn", pdshopGoods.getString("goods_sn"));
					PageData.put("shop_goods_name", pdshopGoods.getString("goods_name"));
					PageData.put("shop_goods_number", pdshopGoods.getString("goods_number"));
				} else {
					PageData.put("shop_name", shop_name);
					PageData.put("shop_goods_id", "");
					PageData.put("shop_goods_sn", "");
					PageData.put("shop_goods_name", "");
					PageData.put("shop_goods_number", "");
				}
			} else {
				PageData.put("shop_name", shop_name);
				PageData.put("shop_goods_id", "");
				PageData.put("shop_goods_sn", "");
				PageData.put("shop_goods_name", "");
				PageData.put("shop_goods_number", "");
			}
		}

		mv.setViewName("erp/goodsshoperpstock/goodsshoperpstock_list");
		mv.addObject("varList", varList);
		mv.addObject("varstoreList", varstoreList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/syngoods")
	public ModelAndView syngoodslist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表GoodsShopErpStock");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		User user = getSessionUser();
		// 默认数据
		PageData pdUser = new PageData();
		if (user.getROLE_ID().equals(Const.SYSTEM_MANAGER_ID)) {
			// 本用user_id store_name
			pdUser.put("user_id", "");
		} else {
			pdUser.put("user_id", user.getUSER_ID());
		}
		List<PageData> varstoreList = storeshopService.listAllByUserId(pdUser);
		String store_id = pd.getString("store_id"); // 关键词检索条件
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		} else {
			if (varstoreList.size() > 0) {
				PageData pageTemp = varstoreList.get(0);
				pd.put("store_id", pageTemp.getString("store_id"));

			} else {
				pd.put("store_id", "0");
			}
		}
		PageData pdShop = storeshopService.findByStoreIdOnly(pd);

		String shop_name = "";
		String shop_id = "0";
		if (pdShop != null) {
			shop_name = pdShop.getString("shop_name");
			shop_id = pdShop.getString("shop_id");
		}
		page.setPd(pd);
		// 列出GoodsShopErpStock列表
		List<PageData> varList = stockgoodsService.listALLByStore(pd);

		PageData pdshopGoodsQuery = new PageData();
		pdshopGoodsQuery.put("shop_id", shop_id);
		Iterator<PageData> iter = varList.iterator();
		while (iter.hasNext()) {
			PageData pageData = iter.next();
			// for (PageData pageData : varList) {
			String sku_encode = pageData.getString("sku_encode");
			pdshopGoodsQuery.put("goods_sn", sku_encode);
			List<PageData> list = goodsshoperpstockService.listShopGoodsAll(pdshopGoodsQuery);
			if (list == null||list.size() == 0) {
				pageData.put("shop_name", shop_name);
				pageData.put("desc", "商城中无商品编码");
			} 
			else if (list.size() == 1) {
				PageData pdshopGoods = goodsshoperpstockService.findByIdByShopAndSn(pdshopGoodsQuery);
				if (pdshopGoods != null) {
					pageData.put("shop_name", shop_name);
					pageData.put("desc", "0");
				    iter.remove();
				} else {
					pageData.put("shop_name", shop_name);
					pageData.put("desc", "商城中无商品编码");
				}
			} else {
				pageData.put("shop_name", shop_name);
				pageData.put("desc", "商城中有多个相同商品编码的商品");
			}
		}

		mv.setViewName("erp/goodsshoperpstock/goodsshop_list");
		mv.addObject("varList", varList);

		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 同步库存
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/synstore")
	public void synstore(PrintWriter out) throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();

		String store_id = pd.getString("store_id"); // 关键词检索条件
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		} else {
			out.write("success");
			out.close();
			return;
		}
		PageData pdShop = storeshopService.findByStoreIdOnly(pd);

		String shop_id = "0";
		if (pdShop != null) {
			shop_id = pdShop.getString("shop_id");
		} else {
			out.write("success");
			out.close();
			return;
		}

		// 列出GoodsShopErpStock列表
		List<PageData> varList = stockgoodsService.listALLByStore(pd);

		PageData pdshopGoodsQuery = new PageData();
		pdshopGoodsQuery.put("shop_id", shop_id);
		for (PageData PageData : varList) {
			String sku_encode = PageData.getString("sku_encode");
			pdshopGoodsQuery.put("goods_sn", sku_encode);
			int is_pre_sales = goodsService.findBySkuEncode(sku_encode);
			if (is_pre_sales == 1) {
				continue;
			}
			List<PageData> list = goodsshoperpstockService.listShopGoodsAll(pdshopGoodsQuery);
			if (list != null && list.size() == 1) {
				PageData pdshopGoods = goodsshoperpstockService.findByIdByShopAndSn(pdshopGoodsQuery);
				if (pdshopGoods != null) {
					/*
					 * PageData.put("shop_name", shop_name); PageData.put("shop_goods_id",
					 * pdshopGoods.getString("goods_id")); PageData.put("shop_goods_number",
					 * pdshopGoods.getString("goods_number"));
					 */
					pdshopGoods.put("goods_number", PageData.getString("goods_number"));
					goodsshoperpstockService.edit(pdshopGoods);

				}
			}
		}

		out.write("success");
		out.close();
	}

	/**
	 * 同步库存 90%到店铺
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/synstore90")
	public void synstore90(PrintWriter out) throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();

		String store_id = pd.getString("store_id"); // 关键词检索条件
		if (null != store_id && !"".equals(store_id)) {
			pd.put("store_id", store_id.trim());
		} else {
			out.write("success");
			out.close();
			return;
		}
		PageData pdShop = storeshopService.findByStoreIdOnly(pd);

		String shop_id = "0";
		if (pdShop != null) {
			shop_id = pdShop.getString("shop_id");
		} else {
			out.write("success");
			out.close();
			return;
		}

		// 列出GoodsShopErpStock列表
		List<PageData> varList = stockgoodsService.listALLByStore(pd);

		PageData pdshopGoodsQuery = new PageData();
		pdshopGoodsQuery.put("shop_id", shop_id);
		for (PageData PageData : varList) {
			String sku_encode = PageData.getString("sku_encode");
			pdshopGoodsQuery.put("goods_sn", sku_encode);
			int is_pre_sales = goodsService.findBySkuEncode(sku_encode);
			if (is_pre_sales == 1) {
				continue;
			}
			List<PageData> list = goodsshoperpstockService.listShopGoodsAll(pdshopGoodsQuery);
			if (list != null && list.size() == 1) {
				PageData pdshopGoods = goodsshoperpstockService.findByIdByShopAndSn(pdshopGoodsQuery);
				if (pdshopGoods != null) {
					/*
					 * PageData.put("shop_name", shop_name); PageData.put("shop_goods_id",
					 * pdshopGoods.getString("goods_id")); PageData.put("shop_goods_number",
					 * pdshopGoods.getString("goods_number"));
					 */
					String strnumber = PageData.getString("goods_number");
					try {
						Double goods_number = Double.valueOf(strnumber);
						goods_number = (double) Math.round(goods_number * 9 / 10);
						pdshopGoods.put("goods_number", goods_number.intValue());
						goodsshoperpstockService.edit(pdshopGoods);
					} catch (Exception ee) {
						logBefore(logger, ee.getMessage());
					}

				}
			}
		}

		out.write("success");
		out.close();
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
		mv.setViewName("erp/goodsshoperpstock/goodsshoperpstock_edit");
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
		pd = goodsshoperpstockService.findById(pd); // 根据ID读取
		mv.setViewName("erp/goodsshoperpstock/goodsshoperpstock_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除GoodsShopErpStock");
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
			goodsshoperpstockService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出GoodsShopErpStock到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("备注2"); // 2
		titles.add("备注3"); // 3
		titles.add("备注7"); // 4
		titles.add("备注8"); // 5
		titles.add("备注9"); // 6
		titles.add("备注16"); // 7
		titles.add("备注18"); // 8
		titles.add("备注19"); // 9
		titles.add("备注57"); // 10
		dataMap.put("titles", titles);
		List<PageData> varOList = goodsshoperpstockService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("goods_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("goods_name")); // 2
			vpd.put("var3", varOList.get(i).get("cat_id").toString()); // 3
			vpd.put("var4", varOList.get(i).get("shop_id").toString()); // 4
			vpd.put("var5", varOList.get(i).get("sku_open").toString()); // 5
			vpd.put("var6", varOList.get(i).get("sku_id").toString()); // 6
			vpd.put("var7", varOList.get(i).get("goods_number").toString()); // 7
			vpd.put("var8", varOList.get(i).getString("goods_sn")); // 8
			vpd.put("var9", varOList.get(i).getString("goods_barcode")); // 9
			vpd.put("var10", varOList.get(i).get("goods_sort").toString()); // 10
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
