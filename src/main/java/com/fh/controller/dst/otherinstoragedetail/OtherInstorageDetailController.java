package com.fh.controller.dst.otherinstoragedetail;

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
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.otherinstoragedetail.OtherInstorageDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.goods.GoodsManager;

/** 
 * 说明：其它入库详情
 * 创建人：FH Q313596790
 * 创建时间：2017-09-20
 */
@Controller
@RequestMapping(value="/otherinstoragedetail")
public class OtherInstorageDetailController extends BaseController {
	
	String menuUrl = "otherinstoragedetail/list.do"; //菜单地址(权限用)
	@Resource(name="otherinstoragedetailService")
	private OtherInstorageDetailManager otherinstoragedetailService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name = "goodsService")
	private GoodsManager goodsService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OtherInstorageDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//校验物料是否重复
		PageData querySku = new PageData();
		querySku.put("other_instorage_code", pd.getString("other_instorage_code"));
		querySku.put("sku_id", pd.getString("sku_id"));
		List<PageData> existSkus = otherinstoragedetailService.listAll(querySku);
		if(existSkus!=null && existSkus.size()>0) {
			mv.addObject("pd",pd);
			mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_edit");
			mv.addObject("msg", "save");
			mv.addObject("sku_id_error",0);
			return mv;
		}
		User user = getSessionUser();
//		pd.put("other_instorage_detail_id", this.get32UUID());	//主键
		
		pd.put("arrive_quantity", 0);	//创建人
		pd.put("createby", user.getNAME());	//创建人
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		pd.put("updateby",  user.getNAME());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		// 是否开启批次 0不开启，1开启
		PageData goodsPd  = goodsService.findBySkuId(pd);
		pd.put("isopen_batch", goodsPd.getString("isopen_batch"));
		otherinstoragedetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除OtherInstorageDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		otherinstoragedetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OtherInstorageDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//校验物料是否重复
				PageData querySku = new PageData();
				querySku.put("other_instorage_code", pd.getString("other_instorage_code"));
				querySku.put("sku_id", pd.getString("sku_id"));
				List<PageData> existSkus = otherinstoragedetailService.listAll(querySku);
				if(existSkus!=null && existSkus.size()>0) {
					//如果不是当前在编辑的则是重复
					if(!existSkus.get(0).getString("other_instorage_detail_id").equals(pd.getString("other_instorage_detail_id"))) {
						mv.addObject("pd",pd);
						mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_edit");
						mv.addObject("msg", "edit");
						mv.addObject("sku_id_error",0);
						return mv;
					}
					
				}
		otherinstoragedetailService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表OtherInstorageDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = otherinstoragedetailService.list(page);	//列出OtherInstorageDetail列表
		mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_list");
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
		mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_edit");
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
		pd = otherinstoragedetailService.findById(pd);	//根据ID读取
		mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除OtherInstorageDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			otherinstoragedetailService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	@RequestMapping(value="/listForInbound")
	public ModelAndView listForInbound(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表otherinstoragedetail for inbound");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String purchase_code = pd.getString("other_instorage_code");
		if(StringUtil.isEmptyStr(purchase_code)) {
			pd.put("other_instorage_code", purchase_code);
		}
		String code=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
		pd.put("inbound_notice_code", code);
		page.setPd(pd);
		List<PageData>	varList = otherinstoragedetailService.list(page);	//列出PurchaseDetail列表
		mv.setViewName("dst/otherinstoragedetail/otherinstoragedetail_list_forInbound");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出OtherInstorageDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("入库编码");	//2
		titles.add("sku_id");	//3
		titles.add("sku编码");	//4
		titles.add("商品名称");	//5
		titles.add("规格");	//6
		titles.add("单位");	//7
		titles.add("数量");	//8
		titles.add("到货数量");	//9
		titles.add("创建人");	//10
		titles.add("创建时间");	//11
		titles.add("更新人");	//12
		titles.add("更新时间");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = otherinstoragedetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("other_instorage_detail_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("other_instorage_code"));	    //2
			vpd.put("var3", varOList.get(i).get("sku_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("sku_encode"));	    //4
			vpd.put("var5", varOList.get(i).getString("sku_name"));	    //5
			vpd.put("var6", varOList.get(i).getString("spec"));	    //6
			vpd.put("var7", varOList.get(i).getString("unit"));	    //7
			vpd.put("var8", varOList.get(i).get("quantity").toString());	//8
			vpd.put("var9", varOList.get(i).get("arrive_quantity").toString());	//9
			vpd.put("var10", varOList.get(i).getString("createby"));	    //10
			vpd.put("var11", varOList.get(i).getString("create_time"));	    //11
			vpd.put("var12", varOList.get(i).getString("updateby"));	    //12
			vpd.put("var13", varOList.get(i).getString("update_time"));	    //13
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
