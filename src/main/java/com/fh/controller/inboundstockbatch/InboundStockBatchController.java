package com.fh.controller.inboundstockbatch;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.inboundstockbatch.InboundStockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.TwoDimensionCode;


/** 
 * 说明：入库明细批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 */
@Controller
@RequestMapping(value="/inboundstockbatch")
public class InboundStockBatchController extends BaseController {
	
	String menuUrl = "inboundstockbatch/list.do"; //菜单地址(权限用)
	@Resource(name="inboundstockbatchService")
	private InboundStockBatchManager inboundstockbatchService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InboundStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("inbound_stock_batch_id", this.get32UUID());	//主键
		inboundstockbatchService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除InboundStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundstockbatchService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InboundStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundstockbatchService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表InboundStockBatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inboundstockbatchService.listAll(pd);	//列出InboundStockBatch列表
		mv.setViewName("erp/inboundstockbatch/inboundstockbatch_list");
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
		mv.setViewName("system/inboundstockbatch/inboundstockbatch_edit");
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
		pd = inboundstockbatchService.findById(pd);	//根据ID读取
		mv.setViewName("system/inboundstockbatch/inboundstockbatch_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InboundStockBatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundstockbatchService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	@RequestMapping(value="/printbatchPage")
	public ModelAndView printbatchPage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询批次数量，生成二维码
		pd = inboundstockbatchService.findById(pd);	//根据ID读取
		int num=0;
		String batchNo="";
		if(pd!=null)
		{
			if(pd!=null&&pd.get("quantity")!=null)
			{
			   Double d=Double.parseDouble(pd.get("quantity").toString());
			   num=d.intValue();
			}
			batchNo=pd.get("batch_code").toString();
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
				for(int i=0;i<num;i++)
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
		
		mv.setViewName("erp/inboundstockbatch/printbatchpage");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("imglist", imgListPd);
		return mv;
	}
	

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出InboundStockBatch到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundstockbatchService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("inbound_stock_batch_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("inbound_code"));	    //2
			vpd.put("var3", varOList.get(i).get("inbound_detail_id").toString());	//3
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
	
	/**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    private String autoGenericCode(Integer code, int num) {
        String result = "";
        result = String.format("%0" + num + "d",code);
        return result;
    }
	
}
