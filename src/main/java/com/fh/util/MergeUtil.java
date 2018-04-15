package com.fh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fh.util.express.enums.InboundType;
import com.fh.util.express.enums.OutboundType;

public class MergeUtil {

	/** BUSINESS_ALLOCATION 调拨 **/
	public final static int BUSINESS_ALLOCATION = 1;
	/** BUSINESS_SCRAP 报损 **/
	public final static int BUSINESS_SCRAP = 2;
	/** BUSINESS_DEFECTIVE_GOODS 不良品 **/
	public final static int BUSINESS_DEFECTIVE_GOODS = 3;
	/** BUSINESS_OTHER_INSTORAGE 其他入库 **/
	public final static int BUSINESS_OTHER_INSTORAGE = 4;
	/** BUSINESS_OTHER_OUTSTORAGE 其他出库 **/
	public final static int BUSINESS_OTHER_OUTSTORAGE = 5;
	/** BUSINESS_PURCHASE 采购 **/
	public final static int BUSINESS_PURCHASE = 6;

	public static List<PageData> mergeInBoundOutBound(int type, List<PageData> inboundList,
			List<PageData> outboundList) {
		System.out.println("入库 = " + (null == inboundList ? "null " : inboundList.size()) + " 出库= "
				+ (null == outboundList ? "null " : outboundList.size()));
		List<PageData> list = new ArrayList<PageData>();
		RedisUtil.getUserInfoById(inboundList, "createby", "NAME", "createby");
		RedisUtil.getUserInfoById(outboundList, "createby", "NAME", "createby");
		PageData pageData = null;
		switch (type) {
		case BUSINESS_ALLOCATION:
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_1.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.ALLOCATION_INBOUND_LOGISTIC.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_14.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.ALLOCATION_INBOUND_QUALITY.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_15.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.ALLOCATION_INBOUND.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			break;
		case BUSINESS_SCRAP:
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_3.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.SCRAP_INBOUND.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			break;
		case BUSINESS_DEFECTIVE_GOODS:
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_7.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.BAD_INBOUND_REJECT.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			break;
		case BUSINESS_OTHER_INSTORAGE:
			pageData = getPageDataByType(InboundType.OTHER_INSTORAGE_INBOUND.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			break;
		case BUSINESS_OTHER_OUTSTORAGE:
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_6.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			break;
		case BUSINESS_PURCHASE:
			pageData = getPageDataByType(InboundType.PURCHASE_INBOUND_QUALITY.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_10.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.PURCHASE_INBOUND_CENTERWARE.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(OutboundType.OUTBOUND_TYPE_11.getCode(), outboundList, false);
			if (null != pageData) {
				list.add(pageData);
			}
			
			pageData = getPageDataByType(InboundType.PURCHASE_INBOUND_GOOD.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.PURCHASE_INBOUND_BAD.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}
			pageData = getPageDataByType(InboundType.SCRAP_INBOUND.getCode(), inboundList, true);
			if (null != pageData) {
				list.add(pageData);
			}

			break;
		default:
			break;
		}
		return list;
	}

	private static PageData getPageDataByType(String type, List<PageData> list, boolean isInbound) {
		for (int i = 0; i < list.size(); i++) {
			if (type.equals(list.get(i).getString(isInbound ? "inbound_type" : "type"))) {
				if (isInbound) {
					list.get(i).put("io_type", "1");
					list.get(i).put("code", list.get(i).getString("inbound_code"));
					list.get(i).put("notice_code", list.get(i).getString("inbound_notice_code"));
					list.get(i).put("type", InboundType.getByCode(type));
				} else {
					list.get(i).put("io_type", "0");
					list.get(i).put("code", list.get(i).getString("out_bound_code"));
					list.get(i).put("notice_code", list.get(i).getString("outbound_notice_code"));
					list.get(i).put("type", OutboundType.getByCode(type));
				}
				return list.get(i);
			}
		}
		return null;
	}
	
	public static List<PageData> mergeInBoundOutBoundByGoodsStore(List<PageData> inboundList,List<PageData> outboundList) {
		List<PageData> list = new ArrayList<PageData>();
		for(PageData pd : inboundList) {
			pd.put("type", InboundType.getByCode(pd.getString("type")));
			list.add(pd);
		}
		for(PageData pd : outboundList) {
			pd.put("type", OutboundType.getByCode(pd.getString("type")));
			list.add(pd);
		}
		if(CollectionUtils.isNotEmpty(list)) {
			RedisUtil.getUserInfoById(list, "createby", "NAME", "createby");
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			list.sort((PageData p1, PageData p2) -> {
				try {
					return sd.parse(p2.getString("create_time")).compareTo(sd.parse(p1.getString("create_time")));
				} catch (ParseException e) {
					return 0;
				}
			});
		}
		return list;
	}
	
	public static List<PageData> mergeInBoundOutBoundByBatch(String object, List<PageData> inboundList,List<PageData> outboundList) {
		List<PageData> list = new ArrayList<PageData>();
		for(PageData pd : inboundList) {
			pd.put("io_type", "1");
			pd.put("code", pd.getString("inbound_code"));
			pd.put("notice_code", pd.getString("inbound_notice_code"));
			pd.put("type", InboundType.getByCode(pd.getString("inbound_type")));
			list.add(pd);
		}
		for(PageData pd : outboundList) {
			pd.put("io_type", "0");
			pd.put("code", pd.getString("out_bound_code"));
			pd.put("notice_code", pd.getString("outbound_notice_code"));
			pd.put("type", OutboundType.getByCode(pd.getString("type")));
			list.add(pd);
		}
		if(CollectionUtils.isNotEmpty(list)) {
			RedisUtil.getUserInfoById(list, "createby", "NAME", "createby");
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			list.sort((PageData p1, PageData p2) -> {
				try {
					return sd.parse(p1.getString("create_time")).compareTo(sd.parse(p2.getString("create_time")));
				} catch (ParseException e) {
					return 0;
				}
			});
		}
		return list;
	}
}
