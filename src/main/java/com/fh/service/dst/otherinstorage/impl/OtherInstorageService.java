package com.fh.service.dst.otherinstorage.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.otherinstorage.OtherInstorageManager;
import com.fh.service.dst.otherinstoragedetail.OtherInstorageDetailManager;
import com.fh.service.dst.otherinstoragestockbatch.OtherInstorageStockBatchManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.FormType;
import com.fh.util.PageData;

/** 
 * 说明： 其他入库单
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
@Service("otherinstorageService")
public class OtherInstorageService implements OtherInstorageManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;
	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="otherinstoragedetailService")
	private OtherInstorageDetailManager otherinstoragedetailService;
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	@Resource(name="otherinstoragestockbatchService")
	private OtherInstorageStockBatchManager otherinstoragestockbatchService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OtherInstorageMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OtherInstorageMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OtherInstorageMapper.edit", pd);
	}
	
	public void updateStatus(PageData pd)throws Exception{
		dao.update("OtherInstorageMapper.updateStatus", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OtherInstorageMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OtherInstorageMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OtherInstorageMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OtherInstorageMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void saveToNotice(PageData pd, User user) throws Exception {
		// TODO Auto-generated method stub
		//根据ID查询其它入库单
		PageData mainpd = findById(pd);
		pd.put("other_instorage_code", mainpd.getString("other_instorage_code"));
		//生成到货通知单
		String inbound_notice_code=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
		mainpd.put("inbound_notice_code", inbound_notice_code);	//到货通知单编码
		 
		 String bill_type = "7";//单据类型 其他入库
		
		 mainpd.put("bill_type", bill_type);	
		 mainpd.put("purchase_code", mainpd.getString("other_instorage_code"));	//业务单据编码
//		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
//		pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
//		pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
//		pd.put("pre_arrival_time", jsonOb.getString("pre_arrival_time"));	//到货仓库名称
		 mainpd.put("status", "0");	//状态
	//	pd.put("createby",getSessionUser().getUSER_ID());
		 mainpd.put("create_time", new Timestamp(System.currentTimeMillis()));
		 
		inboundnoticeService.save(mainpd);
		
		List<PageData> otherDetails = otherinstoragedetailService.listAll(pd);
		if(otherDetails!=null && otherDetails.size()>0) {
			for(PageData detailpd : otherDetails ) {
				detailpd.put("inbound_code", inbound_notice_code);
				detailpd.put("purchase_code",  mainpd.getString("other_instorage_code"));
//				 pd.put("sku_id", detail.getString("sku_id"));
//				 pd.put("sku_name", detail.getString("sku_name"));
//				 pd.put("spec", detail.getString("spec"));
//				 pd.put("sku_encode", detail.getString("sku_encode"));
				 detailpd.put("pre_arrival_quantity", detailpd.getString("quantity"));
				 detailpd.put("pre_arrival_quantity", detailpd.getString("quantity"));
				 detailpd.put("good_quantity",detailpd.getString("quantity"));//入库数量
				 detailpd.put("bad_quantity", "0");
				 detailpd.put("purchase_detail_id", detailpd.getString("other_instorage_detail_id"));
				// pd.put("createby",getSessionUser().getNAME());
				 detailpd.put("create_time", new Timestamp(System.currentTimeMillis()));
				 inboundnoticedetailService.save(detailpd);
				 //插入批次信息
				 PageData query =  new PageData();
				 query.put("other_instorage_detail_id", detailpd.getString("other_instorage_detail_id"));
				 List<PageData> batchs  = otherinstoragestockbatchService.listAll(query);
				 if(batchs!=null && batchs.size()>0) {
					 for(PageData batch : batchs) {
						 batch.put("inbound_notice_code", inbound_notice_code);
						 batch.put("notice_detail_id", detailpd.getString("notice_detail_id"));
						 batch.put("status", 2);
						 inboundnoticestockbatchService.save(batch);
					 }
				 }
				 
			}
		}
		//查询入库单明细		
				
		
		
		edit(pd);
	}

	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OtherInstorageMapper.findByCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> querylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OtherInstorageMapper.querylistPage", page);
	}
	
}

