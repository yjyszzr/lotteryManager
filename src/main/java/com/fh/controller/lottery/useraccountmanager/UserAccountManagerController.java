package com.fh.controller.lottery.useraccountmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.dto.PayAllDTO;
import com.fh.entity.dto.PayLogDTO;
import com.fh.service.lottery.paylog.impl.PayLogService;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：流水相关
 * 创建人：FH Q313596790
 * 创建时间：2018-04-24
 */
@Controller
@RequestMapping(value="/useraccountmanager")
public class UserAccountManagerController extends BaseController {
	
	String menuUrl = "useraccountmanager/list.do"; //菜单地址(权限用)
	@Resource(name="useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;
	
	@Resource(name="paylogService")
	private PayLogService payLogService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增UserAccountManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("id_id", this.get32UUID());	//主键
		pd.put("id", "0");	//数据库表ID
		pd.put("account_sn", "");	//流水号
		pd.put("user_id", "0");	//用户ID
		pd.put("admin_user", "");	//操作人
		pd.put("amount", "");	//操作金额
		pd.put("cur_balance", "");	//当前变动后的总余额
		pd.put("add_time", "0");	//添加时间
		pd.put("last_time", "0");	//最后操作时间
		pd.put("note", "");	//操作备注
		pd.put("parent_sn", "");	//备注12
		pd.put("third_part_name", "");	//三方支付名称
		pd.put("third_part_paid", "");	//三方支付金额
		pd.put("user_surplus", "");	//可提现余额
		pd.put("user_surplus_limit", "");	//不可提现余额
		pd.put("bonus_price", "");	//备注19
		pd.put("status", "0");	//状态
		useraccountmanagerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除UserAccountManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		useraccountmanagerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改UserAccountManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		useraccountmanagerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表UserAccountManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = useraccountmanagerService.list(page);	//列出UserAccountManager列表
		mv.setViewName("lottery/useraccountmanager/useraccountmanager_list");
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
		mv.setViewName("lottery/useraccountmanager/useraccountmanager_edit");
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
		pd = useraccountmanagerService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/useraccountmanager/useraccountmanager_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除UserAccountManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			useraccountmanagerService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	 /**conpareMoney
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/conpareMoney")
	@ResponseBody
	public Object conpareMoney() throws Exception{
		List<PayLogDTO> excelPdList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		PageData pd = new PageData();
		PayAllDTO payAllDto = this.resolvePayOrderSnExcel("D:\\wxPayInfo.xls"); 
		excelPdList = payAllDto.getPayLogDTOList();
		Map<String, PayLogDTO> excelMap = excelPdList.stream().collect(Collectors.toMap(PayLogDTO::getTradeNo, a -> a,(k1,k2)->k1));
		Map<String, PayLogDTO> dbMap = this.queryDBPayLogList(excelPdList);
		for(PayLogDTO ePd:excelPdList) {
			PayLogDTO dbPayLog = dbMap.get(ePd.getTradeNo());
			if( null == dbPayLog ) {
				System.out.println("异常数据,原因为不存在:"+ePd.getTradeNo());
			}
			if(null != dbPayLog && 0 == dbPayLog.getIsPaid()) {
				System.out.println("异常数据，原因为存在但未支付:"+ePd.getTradeNo());
			}
			if(Double.doubleToLongBits(ePd.getReceiveAmout())  != Double.doubleToLongBits(dbPayLog.getReceiveAmout())) {
				System.out.println("异常数据，原因为存在但不相同:"+ePd.getTradeNo());
			}
		}

		this.compareDBMapAndExcelMap(dbMap,excelMap);
		System.out.println("比较结束");
		return AppUtil.returnObject(pd, map);
	}
	
	/** 
	 * 以我们的db为比较基础
	 * @param dbMap
	 * @param excelMap
	 */
	public void  compareDBMapAndExcelMap(Map<String, PayLogDTO> dbMap,  Map<String, PayLogDTO> excelMap) {
		 for (Map.Entry<String, PayLogDTO> entry : dbMap.entrySet()) {
			   String key = entry.getKey().toString();
			   PayLogDTO value = entry.getValue();
			   if(null == excelMap.get(key)) {
				   System.out.println("excel 中不存在"+key);
			   }
		}
	}
	
	public Map<String,PayLogDTO> queryDBPayLogList(List<PayLogDTO> excelPdList) throws Exception{
		List<String> payOrderSns = excelPdList.stream().map(s->s.getTradeNo()).collect(Collectors.toList());
		PageData pd = new PageData();
		pd.put("array", payOrderSns.toArray(new String[payOrderSns.size()]));
		List<PageData> dbPdList = payLogService.listAll(pd);
		List<PayLogDTO> dbPdDTOList = new ArrayList<>();
		dbPdList.stream().forEach(s->{
			PayLogDTO pld = new PayLogDTO();
			pld.setTradeNo(s.getString("trade_no"));
			pld.setReceiveAmout(Double.parseDouble(s.getString("order_amount")));
			pld.setIsPaid("true".equals(s.getString("is_paid"))?1:0);
			dbPdDTOList.add(pld);
		});
		
		Double dbTotalMoney = dbPdDTOList.stream().mapToDouble(PayLogDTO::getReceiveAmout).sum();
		System.out.println("db 中的总金额："+dbTotalMoney);
		Map<String, PayLogDTO> dbMap = dbPdDTOList.stream().collect(Collectors.toMap(PayLogDTO::getTradeNo, a -> a,(k1,k2)->k1));
		return dbMap;
	}
	
	/**
	 * 解析excel
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public PayAllDTO resolvePayOrderSnExcel(String bonusExcelUrl) throws FileNotFoundException, IOException {
		PayAllDTO payAallDto = new PayAllDTO();
		List<PayLogDTO> payLogDTOList = new ArrayList<>();
        File file=new File(bonusExcelUrl);
        HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(file));
        Sheet sheet=workbook.getSheetAt(0);//读取第一个 sheet
        Row row=null;
        int count=sheet.getPhysicalNumberOfRows();
        //逐行处理 excel 数据
        DecimalFormat df = new DecimalFormat("#.00"); 
        for (int i = 2; i < count - 1; i++) {
        	PayLogDTO pld = new PayLogDTO();
        	row=sheet.getRow(i);
            Cell tradeNoCell = row.getCell(2);
            Cell receiveMoneyCell = row.getCell(4);
            Cell sxfCell = row.getCell(5);
            pld.setTradeNo(this.returnCellData(tradeNoCell));
            Double ra = Double.parseDouble(this.returnCellData(receiveMoneyCell));
            pld.setReceiveAmout(ra);
            Double sxf = Double.parseDouble(this.returnCellData(sxfCell));
            if(Double.doubleToLongBits(sxf) != Double.doubleToLongBits(this.leave2Num(ra.doubleValue() * 0.006))) {
            	System.out.println(this.returnCellData(tradeNoCell)+"手续费不对");
            }
            pld.setSxf(sxf);
            payLogDTOList.add(pld);
        }
        workbook.close();
        
        Double totalReceiveMoney = payLogDTOList.stream().mapToDouble(PayLogDTO::getReceiveAmout).sum();
        payAallDto.setPayLogDTOList(payLogDTOList);
        payAallDto.setTotalPayMoney(totalReceiveMoney);
        System.out.println("excel中总的金额："+totalReceiveMoney);
		return payAallDto;
	}
	
	public double leave2Num(double d) {
		BigDecimal bigDecimal = new BigDecimal(d);
		double d1 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d1;
	}
	
	public BigDecimal strToBigDecimal(String str) {
		BigDecimal bMin = new BigDecimal(str); 
		return bMin.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public String returnCellData(Cell cell) {
        String data =  cell.getStringCellValue(); //nf.format(cell.getNumericCellValue());
        if (data.indexOf(",") >= 0) {
        	data = data.replace(",", "");
        }
        return data;
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出UserAccountManager到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("数据库表ID");	//1
		titles.add("流水号");	//2
		titles.add("用户ID");	//3
		titles.add("操作人");	//4
		titles.add("操作金额");	//5
		titles.add("当前变动后的总余额");	//6
		titles.add("添加时间");	//7
		titles.add("最后操作时间");	//8
		titles.add("操作备注");	//9
		titles.add("流水类型");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("支付编号");	//13
		titles.add("商品名称");	//14
		titles.add("三方支付名称");	//15
		titles.add("三方支付金额");	//16
		titles.add("可提现余额");	//17
		titles.add("不可提现余额");	//18
		titles.add("备注19");	//19
		titles.add("状态");	//20
		dataMap.put("titles", titles);
		List<PageData> varOList = useraccountmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("account_sn"));	    //2
			vpd.put("var3", varOList.get(i).get("user_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("admin_user"));	    //4
			vpd.put("var5", varOList.get(i).getString("amount"));	    //5
			vpd.put("var6", varOList.get(i).getString("cur_balance"));	    //6
			vpd.put("var7", varOList.get(i).get("add_time").toString());	//7
			vpd.put("var8", varOList.get(i).get("last_time").toString());	//8
			vpd.put("var9", varOList.get(i).getString("note"));	    //9
			vpd.put("var10", varOList.get(i).get("process_type").toString());	//10
			vpd.put("var11", varOList.get(i).getString("order_sn"));	    //11
			vpd.put("var12", varOList.get(i).getString("parent_sn"));	    //12
			vpd.put("var13", varOList.get(i).getString("pay_id"));	    //13
			vpd.put("var14", varOList.get(i).getString("payment_name"));	    //14
			vpd.put("var15", varOList.get(i).getString("third_part_name"));	    //15
			vpd.put("var16", varOList.get(i).getString("third_part_paid"));	    //16
			vpd.put("var17", varOList.get(i).getString("user_surplus"));	    //17
			vpd.put("var18", varOList.get(i).getString("user_surplus_limit"));	    //18
			vpd.put("var19", varOList.get(i).getString("bonus_price"));	    //19
			vpd.put("var20", varOList.get(i).get("status").toString());	//20
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
