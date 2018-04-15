package com.fh.service.dst.inboundcheckdetail.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明： 批次质检
 * 创建人：FH Q313596790
 * 创建时间：2017-10-02
 * @version
 */
@Service("inboundcheckdetailService")
public class InboundCheckDetailService implements InboundCheckDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundcheckdetailService;
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InboundCheckDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundCheckDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InboundCheckDetailMapper.edit", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateById(PageData pd)throws Exception{
		dao.update("InboundCheckDetailMapper.updateById", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundCheckDetailMapper.datalistPage", page);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundCheckDetailMapper.dataByStatuslistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundCheckDetailMapper.listAll", pd);
	}
	
	/**列表(按条件)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByWhere(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundCheckDetailMapper.datalistByWhere", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundCheckDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundCheckDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public String completeCheck(PageData pd) throws Exception {
		List<PageData> checkDetails = inboundcheckdetailService.listAll(pd);
		String mess ="success"; 
		if(checkDetails!=null && checkDetails.size()>0 ) {
			int checkedTotal = 0;
			for(PageData checkDetail :checkDetails ) {
				if(checkDetail.getString("status").equals("0")) {
					//有未提交质的检单,不允许标记完成
					return "200";
				}
				checkedTotal += Integer.parseInt( checkDetail.getString("total_quantity") );
			}
			if(checkedTotal !=(int) Double.parseDouble(pd.getString("quantity"))) {
				return "202";
			}
			//修改状态
//			PageData statePd = new PageData();
//			 质检完成
			pd.put("status", "2");
			inboundnoticestockbatchService.updateStatus(pd);
			//判断同一入库通知单明细的批次是否已经全是已质检，如果全是已质检修改通知单明细单为已质检
			PageData queryBatchOfDetail = new PageData();
			queryBatchOfDetail.put("notice_detail_id",checkDetails.get(0).getString("notice_detail_id"));
			List<PageData> BatchsOfDetail = inboundnoticestockbatchService.listAll(queryBatchOfDetail);
			if(BatchsOfDetail.size()>0) {
				
				boolean isOk = true;
				//逐一检查状态
				for(PageData batch : BatchsOfDetail) {
					if(Integer.parseInt(batch.getString("status"))<2) {
						isOk = false;
						break;
					}
				}
				if(isOk) {
					PageData inDetail = new PageData();
					inDetail.put("notice_detail_id", checkDetails.get(0).getString("notice_detail_id"));
					inDetail.put("status", "3");
					//updateby = #{updateby},   update_time = #{update_time}
					Session session = Jurisdiction.getSession();
					User user = (User)session.getAttribute(Const.SESSION_USER);
					inDetail.put("updateby", user.getUSERNAME());
					inDetail.put("update_time", new Timestamp(System.currentTimeMillis()));
					inboundnoticedetailService.updateState(inDetail);
				}
			}
			
			return "success";
		}else {
			//未质检
			return "201";
		}

	}
	
}

