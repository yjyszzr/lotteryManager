package com.fh.service.dst.otheroutstorage.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.otheroutstorage.OtherOutstorageManager;
import com.fh.service.dst.otheroutstoragedetail.OtherOutstorageDetailManager;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.util.FormType;
import com.fh.util.PageData;

/** 
 * 说明： 其他出库
 * 创建人：FH Q313596790
 * 创建时间：2017-09-20
 * @version
 */
@Service("otheroutstorageService")
public class OtherOutstorageService implements OtherOutstorageManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="otheroutstoragedetailService")
	private OtherOutstorageDetailManager otheroutstoragedetailService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OtherOutstorageMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OtherOutstorageMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OtherOutstorageMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OtherOutstorageMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OtherOutstorageMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OtherOutstorageMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OtherOutstorageMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void saveToNotice(PageData pd, User user) throws Exception {
		PageData mainpd = findById(pd);
		//生成到货通知单
		String outbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
		mainpd.put("outbound_notice_code", outbound_notice_code);	//到货通知单编码
		 
		 String bill_type = "6";//单据类型 其他入库
		
		 mainpd.put("bill_type", bill_type);	
		 mainpd.put("purchase_code", mainpd.getString("other_outstorage_code"));	//业务单据编码
		 mainpd.put("status", "0");	//状态
//			pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
			//pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
			//pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
		// mainpd.put("pre_send_time", pd.getString("pre_send_time"));	
			//pd.put("createby",getSessionUser().getUSER_ID());
			mainpd.put("create_time", new Timestamp(System.currentTimeMillis()));
		 
		outboundnoticeService.save(mainpd);
		PageData dtPd = new PageData();
		dtPd.put("other_outstorage_code", mainpd.getString("other_outstorage_code"));
		List<PageData> otherDetails = otheroutstoragedetailService.listAll(dtPd);
		if(otherDetails!=null && otherDetails.size()>0) {
			for(PageData detailpd : otherDetails ) {
				 
				 detailpd.put("outbound_notice_code", outbound_notice_code);
				 detailpd.put("purchase_code",  mainpd.getString("other_outstorage_code"));
				detailpd.put("pre_send_quantity", detailpd.getString("quantity"));
				 detailpd.put("send_quantity", 0);
				 detailpd.put("status", 0);
//				 detailpd.put("good_quantity", "0");
//				 detailpd.put("bad_quantity", "0");                     other_outstorage_detail_id
				 detailpd.put("purchase_detail_id", detailpd.getString("other_outstorage_detail_id"));
				// detailpd.put("createby",getSessionUser().getNAME());
				 detailpd.put("create_time", new Timestamp(System.currentTimeMillis()));
				 outboundnoticedetailService.save(detailpd);

			}
		}
		/**
		 pd = this.getPageData();
			String jsonStr = pd.getString("outboundJson");
			JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//			pd.put("inbound_notice_id", this.get32UUID());	//主键
			pd.put("outbound_notice_code", jsonOb.getString("outbound_notice_code"));	//到货通知单编码
			 String business_type = jsonOb.getString("business_type");
			 String bill_type = "6";//单据类型 其他入库
			
			pd.put("bill_type", bill_type);	
			pd.put("purchase_code", jsonOb.getString("purchase_code"));	//业务单据编码
//			pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
			pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
			pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
			pd.put("pre_send_time", jsonOb.getString("pre_send_time"));	
			pd.put("status", "0");	//状态
			pd.put("createby",getSessionUser().getUSER_ID());
			pd.put("create_time", new Timestamp(System.currentTimeMillis()));
			 
			outboundnoticeService.save(pd);
			//插入出货单详情
			JSONArray details = jsonOb.getJSONArray("details");
			for(int i=0;i<details.size();i++) {
				JSONObject detail = details.getJSONObject(i);
				 pd = new PageData();
				 pd.put("outbound_notice_code", jsonOb.getString("outbound_notice_code"));
				 pd.put("purchase_code", jsonOb.getString("purchase_code"));
				 pd.put("sku_id", detail.getString("sku_id"));
				 pd.put("sku_name", detail.getString("sku_name"));
				 pd.put("spec", detail.getString("spec"));
				 pd.put("sku_encode", detail.getString("sku_encode"));
				 pd.put("pre_send_quantity", detail.getString("pre_send_quantity"));
				 pd.put("send_quantity", 0);
				 pd.put("status", 0);
//				 pd.put("good_quantity", "0");
//				 pd.put("bad_quantity", "0");
				 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
				 pd.put("createby",getSessionUser().getNAME());
				 pd.put("create_time", new Timestamp(System.currentTimeMillis()));
				 outboundnoticedetailService.save(pd);
		 */
		edit(pd);
		
		
	}

	@Override
	public void updateStatus(PageData pd) throws Exception{
		dao.update("OtherOutstorageMapper.updateStatus", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> querylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OtherOutstorageMapper.querylistPage", page);
	}

	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OtherOutstorageMapper.findByCode", pd);
	}
	
}

