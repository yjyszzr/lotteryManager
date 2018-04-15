package com.fh.service.dst.scrap.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.outboundnoticestockbatch.OutboundNoticeStockBatchManager;
import com.fh.service.dst.scrap.ScrapManager;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明： 报损单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 * @version
 */
@Service("scrapService")
public class ScrapService implements ScrapManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ScrapMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ScrapMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ScrapMapper.edit", pd);
	}
	public void editStatus(PageData pd)throws Exception{
		dao.update("ScrapMapper.editStatus", pd);
	}
	public void completeCheck(PageData pd)throws Exception{
		dao.update("ScrapMapper.completeCheck", pd);
	}
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="scrapdetailService")
	private ScrapDetailManager scrapdetailService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="outboundnoticestockbatchService")
	private OutboundNoticeStockBatchManager outboundNoticeStockBatchService;
	

	@Transactional(rollbackFor=Exception.class)
	public void complete(PageData pd)throws Exception{
		dao.update("ScrapMapper.verify", pd);
		//生成出库通知
		{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			PageData findById = this.findById(pd);
			Object purchase_code = findById.get("scrap_code");
			Page page = new Page();
			PageData pd3 = new PageData();
			pd3.put("scrap_code", purchase_code);
			page.setPd(pd3);
			List<PageData> list = scrapdetailService.list(pd3);
			if(list == null || list.size() <= 0) {
				return;
			}
			boolean flag = true;
			for(PageData pageData: list) {
				String aq = pageData.getString("audit_quantity");
				Integer quantity = StringUtils.isBlank(aq)?0:Integer.parseInt(aq);
				if(0 < quantity) {
					flag = false;
					break;
				}
			}
			if(flag) {
				return;
			}
			PageData pd2 = new PageData();
			String outbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
			pd2.put("outbound_notice_code", outbound_notice_code);
			Object store_sn = findById.get("store_sn");
			Object store_name = findById.get("store_name");
			pd2.put("store_sn", store_sn);
			pd2.put("store_name", store_name);
			pd2.put("bill_type", 3);
			Object pre_send_time = new Date();
			pd2.put("purchase_code", purchase_code);
			pd2.put("pre_send_time", pre_send_time);
			pd2.put("status", 0);
			pd2.put("createby", user.getUSER_ID());	//更新人
			pd2.put("create_time", new Date());	//更新时间
			pd2.put("updateby", user.getUSER_ID());	//更新人
			pd2.put("update_time", new Date());	//更新时间
			outboundnoticeService.save(pd2);

			Map<String, List<PageData>> map = new HashMap<String, List<PageData>>();
			Map<String, Integer> numMap = new HashMap<String, Integer>();
			for(PageData pageData: list){
				String skuId = pageData.getString("sku_id");
				String aq = pageData.getString("audit_quantity");
				Integer quantity = StringUtils.isBlank(aq)?0:Integer.parseInt(aq);
				if(0 == quantity)continue;
				List<PageData> pds = map.get(skuId);
				Integer integer = numMap.get(skuId);
				if(pds == null) {
					pds = new ArrayList<PageData>(5);
					map.put(skuId, pds);
				}
				if(integer == null) {
					integer = 0;
				}
				pds.add(pageData);
				integer+=quantity;
				numMap.put(skuId, integer);
			}
			for(String key: map.keySet()) {
			//for(PageData pageData: list){
				Integer pre_send_quantity = numMap.get(key);
				if(null == pre_send_quantity || pre_send_quantity == 0){
					continue;
				}
				PageData pageData = map.get(key).get(0);
				PageData pd1 = new PageData();
				pd1.put("outbound_notice_code", outbound_notice_code);
				pd1.put("purchase_code", purchase_code);
				Object purchase_detail_id = pageData.get("scrap_detail_id");
				pd1.put("purchase_detail_id", purchase_detail_id);
				Object sku_id = pageData.get("sku_id");
				pd1.put("sku_id", sku_id);
				Object sku_name = pageData.get("sku_name");
				pd1.put("sku_name", sku_name);
				pd1.put("pre_send_quantity", pre_send_quantity);
				pd1.put("send_quantity", 0);
				pd1.put("status", 0);
				pd1.put("createby", user.getUSER_ID());	//更新人
				pd1.put("create_time", new Date());	//更新时间
				pd1.put("updateby", user.getUSER_ID());	//更新人
				pd1.put("update_time", new Date());	//更新时间
				outboundnoticedetailService.save(pd1);
				for(PageData pdata: map.get(key)) {
					String aq = pdata.getString("audit_quantity");
					if(null != aq && Integer.parseInt(aq) == 0)continue;
					PageData obnb = new PageData();
					obnb.put("notice_detail_id", pd1.getString("outbound_notice_detail_id"));
					obnb.put("outbound_notice_code", outbound_notice_code);
					obnb.put("sku_id", pdata.getString("sku_id"));
					obnb.put("sku_name", pdata.getString("sku_name"));
					obnb.put("sku_encode", pdata.getString("sku_encode"));
					obnb.put("batch_code", pdata.getString("batch_code"));
					obnb.put("quantity", pdata.getString("audit_quantity"));
					obnb.put("spec", pdata.getString("spec"));
					obnb.put("unit_name", pdata.getString("unit"));
					obnb.put("status", 0);
					obnb.put("createby", user.getUSER_ID());	//更新人
					obnb.put("create_time", new Date());	//更新时间
					obnb.put("updateby", user.getUSER_ID());	//更新人
					obnb.put("update_time", new Date());	//更新时间
					outboundNoticeStockBatchService.save(obnb);
				}
			}
		}
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScrapMapper.datalistPage", page);
	}
	public List<PageData> list1(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScrapMapper.datalistPage1", page);
	}
	public List<PageData> list2(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScrapMapper.datalistPage2", page);
	}
	public List<PageData> list3(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScrapMapper.datalistPage3", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ScrapMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ScrapMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ScrapMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void commit(PageData pd) throws Exception {
		dao.update("ScrapMapper.commit", pd);
		
	}

	@Override
	public void verify(PageData pd) throws Exception {
		dao.update("ScrapMapper.verify", pd);
		
	}

	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ScrapMapper.findByCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> querylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ScrapMapper.querylist", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByCodes(String[] noticeIdArr) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("ScrapMapper.findByIds", noticeIdArr);
		return list;
	}
}

