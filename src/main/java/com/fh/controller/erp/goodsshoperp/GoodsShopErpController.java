package com.fh.controller.erp.goodsshoperp;

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
import com.fh.service.erp.goodsshoperp.GoodsShopErpManager;
import com.fh.service.goods.GoodsManager;

/**
 * 说明：商品在商城与ERP对应 创建人：FH Q313596790 创建时间：2017-09-20
 */
@Controller
@RequestMapping(value = "/goodsshoperp")
public class GoodsShopErpController extends BaseController {

	String menuUrl = "goodsshoperp/list.do"; // 菜单地址(权限用)
	@Resource(name = "goodsshoperpService")
	private GoodsShopErpManager goodsshoperpService;
	@Resource(name = "goodsService")
	private GoodsManager goodsService;
	@Resource(name = "goodsskuService")
	private GoodsSkuManager goodsSkuManager;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public Map<String, String> save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增GoodsShopErp");

		// goods_id_from_shop
		// sku_id_from_erp

		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdQuery = new PageData();
		String sku_id = pd.getString("sku_id_from_erp");
		boolean isupdate = false;
		try {
			if (null != sku_id && !"".equals(sku_id)) {
				pdQuery.put("sku_id", pd.getString("sku_id_from_erp"));
				PageData pdSkuFromErq = goodsSkuManager.findSkuById(pdQuery);

				PageData pdGoods = new PageData();
				pdGoods.put("goods_id", pd.getString("DATA_GOODS_ID"));
				pdGoods.put("goods_id_from_erp", pdSkuFromErq.getString("goods_id"));
				pdGoods.put("sku_id_from_erp", pdSkuFromErq.getString("sku_id"));

				pdGoods.put("goods_name", pdSkuFromErq.getString("sku_name"));
				pdGoods.put("goods_sn", pdSkuFromErq.getString("sku_encode"));
				pdGoods.put("goods_barcode", pdSkuFromErq.getString("sku_barcode"));
				PageData pdSku = new PageData();
				pdSku.put("goods_id", pd.getString("DATA_GOODS_ID"));
				pdSku.put("goods_id_from_erp", pdSkuFromErq.getString("goods_id"));
				pdSku.put("sku_id_from_erp", pdSkuFromErq.getString("sku_id"));
				pdSku.put("sku_name", pdSkuFromErq.getString("sku_name"));
				pdSku.put("goods_sn", pdSkuFromErq.getString("sku_encode"));
				pdSku.put("goods_barcode", pdSkuFromErq.getString("sku_barcode"));
				int tempupdate = goodsshoperpService.saveGoodsShopErp(pdGoods, pdSku);
				if (tempupdate == 0) {
					isupdate = true;
				}
			}
		} catch (Exception ee) {
			logBefore(logger, Jurisdiction.getUsername() + "异常" + ee.getMessage());
			isupdate = false;
		}
		Map<String, String> map = new HashMap<String, String>();
		if (isupdate == true) {
			map.put("msg", "success");
		} else {
			map.put("msg", "failure");
		}
		// goodsshoperpService.save(pd);
		/*
		 * out.write("success"); out.close();
		 */
		return map;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除GoodsShopErp");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsshoperpService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改GoodsShopErp");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsshoperpService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表GoodsShopErp");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String goods_name = pd.getString("goods_name"); // 关键词检索条件
		if (null != goods_name && !"".equals(goods_name)) {
			pd.put("goods_name", goods_name.trim());
		}

		String goods_sn = pd.getString("goods_sn"); // 关键词检索条件
		if (null != goods_sn && !"".equals(goods_sn)) {
			pd.put("goods_sn", goods_sn.trim());
		}
		page.setPd(pd);
		List<PageData> varList = goodsshoperpService.list(page); // 列出GoodsShopErp列表
		for (PageData pageData : varList) {
			String goods_id_from_erp = pageData.getString("goods_id_from_erp");
			if (goods_id_from_erp != null && goods_id_from_erp.length() > 0) {
				PageData pdQuery = new PageData();
				pdQuery.put("goods_id", goods_id_from_erp);
				PageData pdGoodsSku = goodsSkuManager.findByGoodsId(pdQuery);
				if (pdGoodsSku!=null&&pdGoodsSku.size()>0) {
					pageData.put("sku_name_from_erp", pdGoodsSku.getString("sku_name"));
					pageData.put("sku_encode_from_erp", pdGoodsSku.getString("sku_encode"));
					pageData.put("sku_barcode_from_erp", pdGoodsSku.getString("sku_barcode"));
				}
				else {
					pageData.put("sku_name_from_erp", "");
					pageData.put("sku_encode_from_erp", "");
					pageData.put("sku_barcode_from_erp", "");
				}
			} else {
				PageData pdQueryCode = new PageData();
				String sku_encode = pageData.getString("goods_sn");
				pdQueryCode.put("sku_encode", sku_encode);
				PageData pdGoodsSku = goodsSkuManager.findByEncode(pdQueryCode);
				if (pdGoodsSku != null && pdGoodsSku.size() > 0) {
					pageData.put("sku_name_from_erp", pdGoodsSku.getString("sku_name"));
					pageData.put("sku_encode_from_erp", pdGoodsSku.getString("sku_encode"));
					pageData.put("sku_barcode_from_erp", pdGoodsSku.getString("sku_barcode"));
				} else {
					pageData.put("sku_name_from_erp", "");
					pageData.put("sku_encode_from_erp", "");
					pageData.put("sku_barcode_from_erp", "");
				}
			}
		}
		mv.setViewName("erp/goodsshoperp/goodsshoperp_list");
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
	@RequestMapping(value = "/listForSelect")
	public ModelAndView listForSelect(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Supplier");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> varList = goodsSkuManager.listJoinGoods(page); // 列出Supplier列表
		/*
		 * PageData pdQuery = new PageData(); String sku_id =
		 * pd.getString("sku_id_from_erp"); if(null != sku_id && !"".equals(sku_id)){
		 * pdQuery.put("sku_id", pd.getString("sku_id_from_erp")); PageData pdGoodsSku =
		 * goodsSkuManager.findByGoodsId(pdQuery); varList.add(0, pdGoodsSku); }
		 */
		mv.setViewName("erp/goodsshoperp/goods_list_select");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
		pd = goodsshoperpService.findById(pd); // 根据ID读取
		mv.setViewName("erp/goodsshoperp/goodsshoperp_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除GoodsShopErp");
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
			goodsshoperpService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出GoodsShopErp到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("商城商品名称"); // 2
		titles.add("备注3"); // 3
		titles.add("商品货号"); // 4
		titles.add("商品条形码"); // 5
		titles.add("备注62"); // 6
		titles.add("备注63"); // 7
		dataMap.put("titles", titles);
		List<PageData> varOList = goodsshoperpService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("goods_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("goods_name")); // 2
			vpd.put("var3", varOList.get(i).get("sku_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("goods_sn")); // 4
			vpd.put("var5", varOList.get(i).getString("goods_barcode")); // 5
			vpd.put("var6", varOList.get(i).get("goods_id_from_erp").toString()); // 6
			vpd.put("var7", varOList.get(i).get("sku_id_from_erp").toString()); // 7
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
