package com.fh.service.erp.inboundnotice.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 到货通知单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("inboundnoticeService")
public class InboundNoticeService implements InboundNoticeManager{

	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;

	@Resource(name = "saveInboundService")
	private SaveInboundManager saveInboundService;
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("InboundNoticeMapper.findByCode", pd);
	}

	/**更新状态
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("InboundNoticeMapper.update", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InboundNoticeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundNoticeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InboundNoticeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundNoticeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundNoticeMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void saveDetail(PageData pd,User user) throws Exception {
		
		String jsonStr = pd.getString("inboundJson");
		JSONObject jsonOb = JSONObject.parseObject(jsonStr);
//		pd.put("inbound_notice_id", this.get32UUID());	//主键
		pd.put("inbound_notice_code", jsonOb.getString("inbound_notice_code"));	//到货通知单编码
		 String business_type = jsonOb.getString("business_type");
		 String bill_type = "";//单据类型 1：采购，2：生产,与采购、生产单的定义不一致，需转换
		if( business_type.equals("0")) {
			bill_type = "1";
		}else if(business_type.equals("1")) {
			bill_type = "2";
		}
		pd.put("bill_type", bill_type);	
		pd.put("purchase_code", jsonOb.getString("purchase_code"));	//业务单据编码
//		pd.put("arrival_time", Tools.date2Str(new Date()));	//实际到货日期
		pd.put("store_sn", jsonOb.getString("store_sn"));	//到货仓库编码
		pd.put("store_name", jsonOb.getString("store_name"));	//到货仓库名称
		pd.put("pre_arrival_time", jsonOb.getString("pre_arrival_time"));	//到货仓库名称
		pd.put("status", "0");	//状态
		pd.put("createby",user.getUSER_ID());
		pd.put("updateby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time", curTime);
		pd.put("update_time", curTime);
		 
		save(pd);
		//插入到货单详情
		JSONArray details = jsonOb.getJSONArray("details");
		for(int i=0;i<details.size();i++) {
			JSONObject detail = details.getJSONObject(i);
			 pd = new PageData();
			 pd.put("inbound_code", jsonOb.getString("inbound_notice_code"));
			 pd.put("purchase_code", jsonOb.getString("purchase_code"));
			 pd.put("sku_id", detail.getString("sku_id"));
			 pd.put("sku_name", detail.getString("sku_name"));
			 pd.put("spec", detail.getString("spec"));
			 pd.put("sku_encode", detail.getString("sku_encode"));
			 pd.put("pre_arrival_quantity", detail.getString("pre_arrival_quantity"));
			 pd.put("good_quantity", "0");
			 pd.put("bad_quantity", "0");
			 pd.put("purchase_detail_id", detail.getString("purchase_detail_id"));
			 pd.put("createby",user.getUSER_ID());
			 pd.put("updateby",user.getUSER_ID());
			 pd.put("create_time", curTime);
			 pd.put("update_time", curTime);
			 inboundnoticedetailService.save(pd);
			//修改采购单已发货数量
			 pd = new PageData();
			 pd.put("total_sended_quantity", detail.getString("pre_arrival_quantity"));
			 //减少可发货
			 pd.put("total_send_quantity", (0-Integer.parseInt(detail.getString("pre_arrival_quantity"))));
			 pd.put("detail_id", detail.getString("purchase_detail_id"));
			 purchasedetailService.updateQuantityForAdd(pd);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listByNoticesIds(String[] noticeIdArr) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("InboundNoticeMapper.listByNoticesIds", noticeIdArr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listForCheck(Page page) throws Exception {
		return (List<PageData>)dao.findForList("InboundNoticeMapper.listForCheck", page);
	}	
}

