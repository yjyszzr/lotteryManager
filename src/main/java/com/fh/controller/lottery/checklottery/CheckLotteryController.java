package com.fh.controller.lottery.checklottery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.checklottery.impl.CheckLotteryService;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

//@Api(value = "核查小程序功能")
//@RestController
@Controller
@RequestMapping(value = "/checklottery")
public class CheckLotteryController extends BaseController {

	@Resource(name = "checkLotteryService")
	private CheckLotteryService checkLotteryService;
	
	/**
	 * 获取店铺列表
	 * 
	 * @param out
	 * @result json:result=返回状态success:error data=返回数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/shopList",method=RequestMethod.GET)
	public void shopList(HttpServletResponse response){
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();

			String user_id = pd.getString("user_id");
			if (null != user_id && !"".equals(user_id)) {
				user_id = user_id.trim();
			}
			//根据当前用户id获取相关店铺id
			List<String> stroe_ids = checkLotteryService.findShopIDByUserId(user_id);
			//根据店铺id列表查询相关店铺名称和ID
			List<PageData> varList;
			if(stroe_ids!=null && stroe_ids.size()>0) {
				varList = checkLotteryService.findShops(stroe_ids); 
			}else {
				varList = new ArrayList<PageData>();
			}
			resultMap.put("code", "0");
			resultMap.put("msg", "获取数据成功");
			resultMap.put("data", varList);
		} catch (Exception e) {
			resultMap.put("code", "1");
			resultMap.put("msg", "网络异常");
		} finally {
			//begin寫入本地文件開始
//			WriteStringToFile("==============shopList()  begin "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"==================");
//			WriteStringToFile(JSONUtils.toJSONString(resultMap));
//			WriteStringToFile("==============shopList()  end "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"==================");
			//end寫入本地文件結束
			out.print(JSONUtils.toJSONString(resultMap));
			out.close();
		}
		
		
	}
	/**
	 * 分页列表
	 * 
	 * @param1 page
	 * @param2 out
	 * @result json:result=返回状态success:error data=返回数据
	 * @throws Exception
	 */
//	@ApiOperation(value = "获取订单分页", notes = "获取订单分页")
	@RequestMapping(value = "/list",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public void list(Page page,HttpServletResponse response,@RequestBody JSONObject jsonStr){
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			PageData pd = new PageData();
			boolean flag = jsonStr.containsKey("body");
			if(flag) {
				Map map = (Map) JSONUtils.parse(jsonStr.toJSONString());
				pd  = new PageData((Map)map.get("body"));
			}
			String store_id = pd.getString("store_id");
			if (null != store_id && !"".equals(store_id)) {
				pd.put("store_id", store_id.trim());
			}
			String print_lottery_status = pd.getString("print_lottery_status");
			if (null != print_lottery_status && !"".equals(print_lottery_status)) {
				pd.put("print_lottery_status", print_lottery_status.trim());
			}
			String lastStart = pd.getString("lastStart");
			if (null != lastStart && !"".equals(lastStart)) {
				pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
			}
			String lastEnd = pd.getString("lastEnd");
			if (null != lastEnd && !"".equals(lastEnd)) {
				pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
			}
			String showCount = pd.getString("showCount");
			if (null != showCount && !"".equals(showCount)) {
				try {
					page.setShowCount(Integer.parseInt(showCount.trim()));
				} catch (Exception e) {
					//传入的值无法转为Integer时使用page对象默认值
				}
			}
			String currentPag = pd.getString("currentPag");
			if (null != currentPag && !"".equals(currentPag)) {
				try {
					page.setCurrentPage(Integer.parseInt(currentPag.trim()));
				} catch (Exception e) {
					//传入的值无法转为Integer时使用page对象默认值
				}
			}
			page.setPd(pd);
			List<PageData> varList = checkLotteryService.list(page); // 列出Order列表
			//获取当前店铺当前时间的订单数量和出票总金额
			HashMap<String,Object> countMap = checkLotteryService.getNumAndMon(pd);
			countMap.put("datalist", varList);
			resultMap.put("code", "0");
			resultMap.put("msg", "获取数据成功");
			resultMap.put("data", countMap);
		} catch (Exception e) {
			resultMap.put("code", "1");
			resultMap.put("msg", "网络异常");
		} finally {
			out.print(JSONUtils.toJSONString(resultMap));
			out.close();
		}
		
	}
	/**
	 * 全部列表
	 * 
	 * @param out
	 * @result json:result=返回状态success:error data=返回数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/allList",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public void allList(Page page,HttpServletResponse response,@RequestBody JSONObject jsonStr){
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			PageData pd = new PageData();
			boolean flag = jsonStr.containsKey("body");
			if(flag) {
				Map map = (Map) JSONUtils.parse(jsonStr.toJSONString());
				pd  = new PageData((Map)map.get("body"));
			}
			String store_id = pd.getString("store_id");
			if (null != store_id && !"".equals(store_id)) {
				pd.put("store_id", store_id.trim());
			}
			String print_lottery_status = pd.getString("print_lottery_status");
			if (null != print_lottery_status && !"".equals(print_lottery_status)) {
				pd.put("print_lottery_status", print_lottery_status.trim());
			}
			String lastStart = pd.getString("lastStart");
			if (null != lastStart && !"".equals(lastStart)) {
				pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
			}
			String lastEnd = pd.getString("lastEnd");
			if (null != lastEnd && !"".equals(lastEnd)) {
				pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
			}
			List<PageData> varList = checkLotteryService.listAll(pd); // 列出Order列表
			//获取当前店铺当前时间的订单数量和出票总金额
			HashMap<String,Object> countMap = checkLotteryService.getNumAndMon(pd);
			countMap.put("datalist", varList);
			resultMap.put("code", "0");
			resultMap.put("msg", "获取数据成功");
			resultMap.put("data", countMap);
		} catch (Exception e) {
			resultMap.put("code", "1");
			resultMap.put("msg", "网络异常");
		} finally {
			out.print(JSONUtils.toJSONString(resultMap));
			out.close();
		}
		
	}
	/**
	 * 查看訂單詳情
	 * 
	 * @param out
	 * @result json:result=返回状态success:error data=返回数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderInfo",method=RequestMethod.GET)
	public void orderInfo(HttpServletResponse response){
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();

			String order_id = pd.getString("order_id");
			if (null != order_id && !"".equals(order_id)) {
				order_id = order_id.trim();
			}
			pd = checkLotteryService.findById(order_id); // 获取order详情
			resultMap.put("code", "0");
			resultMap.put("msg", "获取数据成功");
			resultMap.put("data", pd);
		} catch (Exception e) {
			resultMap.put("code", "1");
			resultMap.put("msg", "网络异常");
		} finally {
			out.print(JSONUtils.toJSONString(resultMap));
			out.close();
		}
	}
	/**
	 * 核查订单
	 * @ApiOperation(value = "模拟生成订单", notes = "模拟生成订单")
	   @PostMapping("/createOrder")
	 * @param out
	 * @result json:result=返回状态success:error
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOrder",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public void checkOrder(HttpServletResponse response,@RequestBody JSONObject jsonStr) {
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			PageData pd = new PageData();
			boolean flag = jsonStr.containsKey("body");
			if(flag) {
				Map map = (Map) JSONUtils.parse(jsonStr.toJSONString());
				pd  = new PageData((Map)map.get("body"));
			}
			checkLotteryService.checkOrder(pd); // 核查order
			pd = checkLotteryService.findById(pd.getString("order_id")); // 获取order详情
			resultMap.put("code", "0");
			resultMap.put("msg", "核查通过");
			resultMap.put("data", pd);
		} catch (Exception e) {
			resultMap.put("code", "1");
			resultMap.put("msg", "网络异常");
		} finally {
			out.print(JSONUtils.toJSONString(resultMap));
			out.close();
		}
	}
	/**
	 * 将接口返回结果写入本地文件中log 不需要 后删除
	 * 
	 * @param filePath
	 */
//	public void WriteStringToFile(String json) {
//		try {
//			FileWriter fw = new FileWriter("D:\\\\link.txt", true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(json+"\r\n");
//            bw.close();
//            fw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
