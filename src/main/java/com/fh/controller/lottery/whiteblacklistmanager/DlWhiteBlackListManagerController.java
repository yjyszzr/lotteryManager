package com.fh.controller.lottery.whiteblacklistmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.dlwhiteblacklistmanager.DlWhiteBlackListManagerManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.MobileCheckUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/**
 * 说明：黑名单管理 创建人：FH Q313596790 创建时间：2018-09-26
 */
@Controller
@RequestMapping(value = "/dlwhiteblacklistmanager")
public class DlWhiteBlackListManagerController extends BaseController {

	String menuUrl = "dlwhiteblacklistmanager/list.do"; // 菜单地址(权限用)
	@Resource(name = "dlwhiteblacklistmanagerService")
	private DlWhiteBlackListManagerManager dlwhiteblacklistmanagerService;
	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;

	@RequestMapping(value = "/findByMobile", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object findByMobile() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String info = "";
		List<PageData> pageDataList = new ArrayList<PageData>();
		pageDataList = dlwhiteblacklistmanagerService.findByMobile(pd);
		if (pageDataList.size() > 0) {
			info = "false";
		} else {
			info = "true";
		}
		map.put("result", info);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增DlWhiteBlackListManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		dlwhiteblacklistmanagerService.save(pd);
		ACLOG.save("1", "1", "手机号：" + pd.getString("mobile"), "状态：" + pd.getString("is_white"));
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
		logBefore(logger, Jurisdiction.getUsername() + "删除DlWhiteBlackListManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		dlwhiteblacklistmanagerService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 更改状态
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "更改状态DlWhiteBlackListManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		dlwhiteblacklistmanagerService.updateStatus(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改DlWhiteBlackListManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		dlwhiteblacklistmanagerService.edit(pd);
		List<PageData> list = dlwhiteblacklistmanagerService.findByMobile(pd);
		ACLOG.saveByObject("1", "手机号：" + pd.getString("mobile"), list.get(0), pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表DlWhiteBlackListManager");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String mobile = pd.getString("mobile"); // 关键词检索条件
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		page.setPd(pd);
		List<PageData> varList = dlwhiteblacklistmanagerService.list(page); // 列出DlWhiteBlackListManager列表
		mv.setViewName("lottery/dlwhiteblacklistmanager/dlwhiteblacklistmanager_list");
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
		mv.setViewName("lottery/dlwhiteblacklistmanager/dlwhiteblacklistmanager_edit");
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
		pd = dlwhiteblacklistmanagerService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/dlwhiteblacklistmanager/dlwhiteblacklistmanager_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除DlWhiteBlackListManager");
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
			dlwhiteblacklistmanagerService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/updateAll")
	@ResponseBody
	public Object updateAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除DlWhiteBlackListManager");
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
			dlwhiteblacklistmanagerService.updateAll(ArrayDATA_IDS);
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData pdo = new PageData();
				pdo.put("mobile", ArrayDATA_IDS[i]);
				List<PageData> list = dlwhiteblacklistmanagerService.findByMobile(pdo);
				ACLOG.saveByObject("1", "手机号：" + pd.getString("mobile"), list.get(0), pd);
			}
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
		logBefore(logger, Jurisdiction.getUsername() + "导出DlWhiteBlackListManager到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("电话"); // 1
		titles.add("状态"); // 2
		dataMap.put("titles", titles);
		List<PageData> varOList = dlwhiteblacklistmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("mobile")); // 1
			vpd.put("var2", varOList.get(i).get("is_white").toString()); // 2
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

	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("lottery/dlwhiteblacklistmanager/uploadexcel");
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String fname = file.getOriginalFilename();
			if (fname.contains(".")) {
				fname = fname.split("\\.")[0];
			}
			String filePath = PathUtil.getClasspath() + Const.WITHDRAWALFILE;
			// 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, DateUtilNew.getCurrentDateTime2()); // 执行上传
			List<PageData> listPd = (List) readExcel(filePath, fileName, 0, 0, 0); // 执行读EXCEL操作,读出的数据导入List，2:从第3行开始；0:从第A列开始；0:第0个sheet
			List<PageData> varOList = dlwhiteblacklistmanagerService.listAll(pd);
			Map<String, PageData> pageDataMap = new HashMap<String, PageData>();
			varOList.forEach(item -> pageDataMap.put(item.getString("mobile"), item));

			for (int i = 0; i < listPd.size(); i++) {
				PageData pData = pageDataMap.get(listPd.get(i).getString("var0"));
				if (null == pData) {
					if (null != listPd.get(i).get("var0")) {
						if (MobileCheckUtils.isMobileNO(listPd.get(i).getString("var0"))) {
							pData = new PageData();
							pData.put("mobile", listPd.get(i).get("var0"));
							pData.put("is_white", listPd.get(i).get("var1") == null ? 0 : listPd.get(i).getString("var1"));
							dlwhiteblacklistmanagerService.save(pData);
							ACLOG.save("1", "1", "手机号：" + pData.getString("mobile"), "状态：" + pData.getString("is_white"));
						} else {
							System.out.println("手机号不合法==========" + listPd.get(i).getString("var0"));
						}
					}
				} else {
					System.out.println(listPd.get(i).getString("var0") + "==========该手机号已存在!");
				}
			}
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * @param filepath
	 *            //文件路径
	 * @param filename
	 *            //文件名
	 * @param startrow
	 *            //开始行号
	 * @param startcol
	 *            //开始列号
	 * @param sheetnum
	 *            //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) { // 行循环开始

				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); // 行
				int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) { // 列循环开始

					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {

						switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((long) cell.getNumericCellValue());
							break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue =
							// String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}

					varpd.put("var" + j, cellValue);

				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return varList;
	}
}
