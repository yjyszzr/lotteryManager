package com.fh.service.lottery.checklottery.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fh.common.ProjectConstant;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.enums.MatchBetTypeEnum;
import com.fh.enums.MatchPlayTypeEnum;
import com.fh.enums.MatchResultCrsEnum;
import com.fh.enums.MatchResultHadEnum;
import com.fh.enums.MatchResultHafuEnum;
import com.fh.service.lottery.checklottery.CheckLotteryManager;
import com.fh.util.PageData;
import com.fh.util.WeekDateUtil;
@Service("checkLotteryService")
public class CheckLotteryService implements CheckLotteryManager{
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	@Value("${imgShowUrl}")
	private String imgFilePreUrl;
	@Override
	public List<String> findShopIDByUserId(String userId) throws Exception {
		return (List<String>) dao.findForList("CheckLotteryMapper.findShopIDByUserId", userId);
	}
	
	@Override
	public List<PageData> findShops(List<String> shopId) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.findShops", shopId);
	}
	
	@Override
	public HashMap<String,Object> getNumAndMon(PageData pd) throws Exception{
		return (HashMap<String, Object>) dao.findForObject("CheckLotteryMapper.getNumAndMon", pd);
	}
	
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.datalistPage", page);
	}

	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.dataAllList", pd);
	}

	@Override
	public PageData findById(String orderno) throws Exception {
		return (PageData) dao.findForObject("CheckLotteryMapper.getOrderById", orderno);
	}

	@Override
	public void checkOrder(PageData pd) throws Exception {
		dao.update("CheckLotteryMapper.checkOrder", pd);
	}
	
	public PageData getTicketScheme(PageData param) throws Exception {
		PageData pd_1 = new PageData();
		pd_1.put("programmeSn", param.get("programmeSn"));
		PageData orderInfoByOrderSn = (PageData) dao.findForObject("CheckLotteryMapper.getOrderInfoByOrderSn", param.get("orderSn").toString());
		Integer orderStatus = Integer.parseInt(orderInfoByOrderSn.get("order_status").toString());
		Integer times = Integer.parseInt(orderInfoByOrderSn.get("cathectic").toString());
		if (orderStatus < 10 && orderStatus > 2) {
			List<PageData> ticketSchemeDetailDTOs = new ArrayList<PageData>();
			Integer lotteryClassifyId = Integer.parseInt(orderInfoByOrderSn.get("lottery_classify_id").toString());
			Integer lotteryPlayClassifyId = Integer.parseInt(orderInfoByOrderSn.get("lottery_play_classify_id").toString());
			if (8 == lotteryPlayClassifyId && 1 == lotteryClassifyId) {
				List<PageData> orderDetails = (List<PageData>) dao.findForList("CheckLotteryMapper.selectByOrderId", orderInfoByOrderSn.get("order_id").toString());
				int i = 0;
				for (PageData detail : orderDetails) {
					PageData ticketSchemeDetailDTO = new PageData();
					ticketSchemeDetailDTO.put("number", String.valueOf(++i));
					String playType = "冠军";
					if ("T57".equals(detail.get("changci"))) {
						playType = "冠亚军";
					}
					String ticketContent = playType + "(" + detail.get("matchTeam") + "[" + detail.get("ticketData").toString().split("@")[1] + "])";
					ticketSchemeDetailDTO.put("tickeContent",ticketContent);
					ticketSchemeDetailDTO.put("passType","");
					ticketSchemeDetailDTO.put("multiple",times.toString());
					ticketSchemeDetailDTO.put("status",1);
					ticketSchemeDetailDTOs.add(ticketSchemeDetailDTO);
				}
			} else {
				PageData dLZQBetInfoDTO = getBetInfoByOrderSn(param.get("orderSn").toString());
				if (null != dLZQBetInfoDTO) {
					List<PageData> orderLotteryBetInfos = (List<PageData>) dLZQBetInfoDTO.get("betCells");
					if (CollectionUtils.isNotEmpty(orderLotteryBetInfos)) {
						int cn = 0;
						for (PageData betInfo : orderLotteryBetInfos) {
							Integer status = Integer.parseInt(betInfo.get("status").toString());
							List<PageData> dLBetMatchCellDTOs = (List<PageData>) betInfo.get("betCellList1");
							if (CollectionUtils.isNotEmpty(dLBetMatchCellDTOs)) {
								for (PageData pageData : dLBetMatchCellDTOs) {
									PageData dto = new PageData();
									dto.put("number",String.valueOf(++cn));
									dto.put("tickeContent",pageData.get("betContent"));
									dto.put("passType",pageData.get("betType"));
									dto.put("multiple",pageData.get("times"));
									dto.put("status",status);
									ticketSchemeDetailDTOs.add(dto);
								}
							}
						}
					}
				}
			}
			pd_1.put("ticketSchemeDetail", ticketSchemeDetailDTOs);
		} else if (orderStatus.equals(0)||orderStatus.equals(1)) {
			List<PageData> ticketSchemeDetailDTOs = new ArrayList<PageData>(1);
			PageData dto = new PageData();
			dto.put("number","1");
			dto.put("tickeContent","-");
			dto.put("passType","-");
			dto.put("multiple","-");
			dto.put("status",0);
			ticketSchemeDetailDTOs.add(dto);
			pd_1.put("ticketSchemeDetail", ticketSchemeDetailDTOs);
		} else {
			List<PageData> ticketSchemeDetailDTOs = new ArrayList<PageData>(1);
			PageData dto = new PageData();
			dto.put("number","1");
			dto.put("tickeContent","-");
			dto.put("passType","-");
			dto.put("multiple","-");
			dto.put("status",2);
			ticketSchemeDetailDTOs.add(dto);
			pd_1.put("ticketSchemeDetail", ticketSchemeDetailDTOs);
		}
		return pd_1;
	}
	public PageData getBetInfoByOrderSn(String orderSn) throws Exception {
		List<PageData> allPlays = (List<PageData>) dao.findForList("CheckLotteryMapper.getOrderInfoByOrderSn", "1");
		Map<Integer, String> playTypeNameMap = new HashMap<Integer, String>();
		if(allPlays!=null) {
			for(PageData type: allPlays) {
				playTypeNameMap.put(Integer.parseInt(type.get("play_type").toString()), getCathecticData(Integer.parseInt(type.get("play_type").toString())));
				//play_name没值
			}
		}
		List<PageData> orderLotteryBetInfos = new ArrayList<PageData>();
		List<PageData> prints = (List<PageData>) dao.findForList("CheckLotteryMapper.getByOrderSn", orderSn);
		for(PageData lPrint: prints) {
			String printSp = lPrint.get("print_sp").toString();
			String stakes = lPrint.get("stakes").toString();
			String betType = lPrint.get("bet_type").toString();
			int num = Integer.parseInt(betType)/10;
			String[] stakeList = stakes.split(";");
			Map<String, String> map = this.printspMap(printSp);
			List<PageData> subList = new ArrayList<PageData>(stakeList.length);
			for(String stake: stakeList) {
				String[] arr = stake.split("\\|");
				String playType = arr[0];
				String playCode = arr[1];
				String cells = map.get(playCode)!=null&&!"".equals(map.get(playCode))?map.get(playCode):arr[2];
				List<PageData> betCells = this.betCells(cells.split(","), playType);
				int weekNum = Integer.parseInt(String.valueOf(playCode.charAt(8)));
				String changci = WeekDateUtil.weekDays[weekNum-1] + playCode.substring(9);
				PageData matchBetPlayCellDto = new PageData();
				matchBetPlayCellDto.put("changci",changci);
				matchBetPlayCellDto.put("playType",playType);
				matchBetPlayCellDto.put("betCells",betCells);
				subList.add(matchBetPlayCellDto);
			}
			List<PageData> betCellList1 = new ArrayList<PageData>();
			PageData dto = new PageData();
			dto.put("betType",betType);
			dto.put("times",lPrint.get("times"));
			dto.put("betContent","");
			dto.put("betStakes","");
			dto.put("amount",2.0*Integer.parseInt(lPrint.get("times").toString()));
			this.betNum2(dto, num, subList, betCellList1, playTypeNameMap);
			
			PageData dtop = new PageData();
			dtop.put("stakes", stakes);
			dtop.put("betCellList1", betCellList1);
			dtop.put("status", lPrint.get("status"));
			orderLotteryBetInfos.add(dtop);
		}
		PageData dto = new PageData();
		dto.put("betCells",orderLotteryBetInfos);
		return dto;
	}
	private void betNum2(PageData str, int num, List<PageData> list, List<PageData> betList, Map<Integer, String> playTypeNameMap) {
		LinkedList<PageData> link = new LinkedList<PageData>(list);
		while(link.size() > 0) {
			PageData remove = link.remove(0);
			String changci = remove.get("changci").toString();
			String playType = remove.get("playType").toString();
			String playName = playTypeNameMap.get(Integer.valueOf(playType));
			if(Integer.valueOf(playType).equals(MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode())) {
				playName = remove.get("fixedodds")!=null&&!"".equals(remove.get("fixedodds"))?"["+remove.get("fixedodds")+"]"+playName:playName;
			}
			List<PageData> betCells = (List<PageData>) remove.get("betCells");
			for(PageData betCell: betCells) {
				PageData dto = new PageData();
				dto.put("PlayType",playType);
				String cellOdds = betCell.get("cellOdds")!=null&&!"".equals(betCell.get("cellOdds"))?betCell.get("cellOdds").toString():"-";
				String betContent = str.get("betContent") + changci + "(" + playName + "_"+ betCell.get("cellName") + " " + cellOdds +")X";
				if(num == 1) {
					betContent = betContent.substring(0, betContent.length()-1);
				}
				dto.put("betContent",betContent);
				dto.put("betType",str.get("betType"));
				dto.put("times",str.get("times"));
				if(num == 1) {
					betList.add(dto);
				}else {
					betNum2(dto,num-1,link, betList, playTypeNameMap);
				}
			}
		}
	}
	private List<PageData> betCells(String[] cells, String playTypeStr) {
		int playType = Integer.parseInt(playTypeStr);
		List<PageData> dtos = new ArrayList<PageData>(cells.length);
		for(String cell: cells) {
			PageData dto = new PageData();
			String cellName = cell;
			if(cell.contains("@")) {
				String[] split = cell.split("@");
				cellName = split[0];
				dto.put("cellOdds",split[1]);
			}
			dto.put("cellCode",cellName);
			if(playType == MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode()) {
				String name = MatchResultHadEnum.getName(Integer.parseInt(cellName));
				dto.put("cellName",name);
				dtos.add(dto);
			}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_HAD.getcode()) {
				String name = MatchResultHadEnum.getName(Integer.parseInt(cellName));
				dto.put("cellName",name);
				dtos.add(dto);
			}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_CRS.getcode()) {
				String name = MatchResultCrsEnum.getName(cellName);
				dto.put("cellName",name);
				dtos.add(dto);
			}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_TTG.getcode()) {
				String name = cellName;
				if(cellName.equals("7")) {
					name = "7+";
				}
				dto.put("cellName",name);
				dtos.add(dto);
			}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_HAFU.getcode()) {
				String name = MatchResultHafuEnum.getName(cellName);
				dto.put("cellName",name);
				dtos.add(dto);
			}
		}
		return dtos;
	}
	private String getCathecticData(int playType) {
		String msg = "";
		if(playType == MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode()) {
			msg="让球胜平负";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_HAD.getcode()) {
			msg="胜平负";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_CRS.getcode()) {
			msg="比分";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_TTG.getcode()) {
			msg="总进球";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_HAFU.getcode()) {
			msg="半全场";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_MIX.getcode()) {
			msg="混合过关";
		}else if(playType == MatchPlayTypeEnum.PLAY_TYPE_TSO.getcode()) {
			msg="2选1";
		}
		return msg;
	}
	private Map<String, String> printspMap(String printSp) {
		Map<String, String> map = new HashMap<String, String>();
		if(printSp==null || "".equals(printSp)) {
			return map;
		}
		String[] split = printSp.split(";");
		for(String str: split) {
			String[] split2 = str.split("\\|");
			map.put(split2[0], split2[1]);
		}
		return map;
	}
	
	/**
	 * 查询订单详情
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<PageData> getManualOrderList(PageData param) throws Exception {
		if(param.get("order_sn")==null) {
			return null;
		}
		List<PageData> orderManualDTOList = new ArrayList<>();
		List<PageData> orderList = (List<PageData>) dao.findForList("CheckLotteryMapper.getOrderInfoByOrderSn", param.get("order_sn").toString());
		for(PageData order:orderList) {
			PageData manualOrderDTO = new PageData();
			PageData orderDetailDTO = new PageData();
			String lotteryClassifyId = order.get("lottery_classify_id").toString();
			String lotteryPlayClassifyId = order.get("lottery_play_classify_id").toString();
			PageData lotteryClassify = (PageData) dao.findForObject("CheckLotteryMapper.lotteryClassify", lotteryClassifyId);
			if (lotteryClassify != null) {
				orderDetailDTO.put("lotteryClassifyImg",imgFilePreUrl+lotteryClassify.get("lotteryImg"));
				orderDetailDTO.put("lotteryClassifyName",lotteryClassify.get("lotteryName"));
			} else {
				orderDetailDTO.put("lotteryClassifyImg","");
				orderDetailDTO.put("lotteryClassifyName","");
			}
			orderDetailDTO.put("moneyPaid",order.get("money_paid") != null ? order.get("money_paid").toString() : "");// 2018-05-13前端不变参数的情况下暂时使用原有参数,1.0.3更新为moneyPaid
			orderDetailDTO.put("ticketAmount",order.get("ticket_amount") != null ? order.get("ticket_amount").toString() : "");
			orderDetailDTO.put("surplus",order.get("surplus") != null ? order.get("surplus").toString() : "");
			orderDetailDTO.put("userSurplusLimit",order.get("user_surplus_limit") != null ? order.get("user_surplus_limit").toString() : "");
			orderDetailDTO.put("userSurplus",order.get("user_surplus") != null ? order.get("user_surplus").toString() : "");
			orderDetailDTO.put("thirdPartyPaid",order.get("third_party_paid") != null ? order.get("third_party_paid").toString() : "");
			orderDetailDTO.put("bonus",order.get("bonus") != null ? order.get("bonus").toString() : "");
			orderDetailDTO.put("betNum",order.get("bet_num"));
			orderDetailDTO.put("payName",order.get("pay_name"));
			orderDetailDTO.put("passType",getPassType(order.get("pass_type").toString()));
			orderDetailDTO.put("cathectic",order.get("cathectic"));
			orderDetailDTO.put("playType",order.get("play_type").toString().replaceAll("0", ""));
			orderDetailDTO.put("lotteryClassifyId",String.valueOf(lotteryClassifyId));
			orderDetailDTO.put("lotteryPlayClassifyId",String.valueOf(lotteryPlayClassifyId));
			orderDetailDTO.put("checkRemark",order.get("check_remark"));
			orderDetailDTO.put("printLotteryStatus",order.get("print_lottery_status"));
			orderDetailDTO.put("orderStatus",order.get("order_status"));
			orderDetailDTO.put("storeName",order.get("store_name"));
			orderDetailDTO.put("programmeSn",order.get("order_sn"));
			orderDetailDTO.put("createTime",WeekDateUtil.getCurrentTimeString(Long.parseLong(order.get("add_time").toString()), WeekDateUtil.datetimeFormat));
			long acceptTime = Long.parseLong(order.get("accept_time").toString());
			if (acceptTime > 0) {
				orderDetailDTO.put("acceptTime",WeekDateUtil.getCurrentTimeString(acceptTime, WeekDateUtil.datetimeFormat));
			} else {
				orderDetailDTO.put("acceptTime","--");
			}
			long ticketTime = Long.parseLong(order.get("ticket_time").toString());
			if (ticketTime > 0) {
				orderDetailDTO.put("ticketTime",WeekDateUtil.getCurrentTimeString(ticketTime, WeekDateUtil.datetimeFormat));
			} else {
				orderDetailDTO.put("ticketTime","--");
			}
			List<PageData> playTypes = (List<PageData>) dao.findForList("CheckLotteryMapper.getPlayTypes", lotteryClassifyId);
			Map<Integer, String> playTypeNameMap = new HashMap<Integer, String>();
			if (playTypes!=null) {
				for (PageData type : playTypes) {
					playTypeNameMap.put(Integer.valueOf(type.get("playType").toString()), type.get("playName").toString());
				}
			}
			orderDetailDTO.put("detailType",0);
			boolean isWorlCup = false;
			if ("1".equals(lotteryClassifyId) && "8".equals(lotteryPlayClassifyId)) {
				isWorlCup = true;
			}
			String redirectUrl = "";
			PageData parm = new PageData();
			parm.put("classifyId", lotteryClassifyId);
			parm.put("playClassifyId", lotteryPlayClassifyId);
			PageData pdmap = (PageData) dao.findForObject("CheckLotteryMapper.lotteryPlayClassifyStatusAndUrl", parm);
			if (pdmap != null) {
				String status = pdmap.get("status").toString();
				if ("0".equals(status)) {
					redirectUrl = pdmap.get("redirectUrl").toString();
				}
			}
			orderDetailDTO.put("redirectUrl",redirectUrl);
			List<PageData> orderDetails = (List<PageData>) dao.findForList("CheckLotteryMapper.selectByOrderId", order.get("order_id").toString());
			if (CollectionUtils.isNotEmpty(orderDetails)) {
				List<PageData> matchInfos = new ArrayList<PageData>();
				for (PageData orderDetail : orderDetails) {
					PageData matchInfo = new PageData();
					matchInfo.put("changci",orderDetail.get("changci"));
					String match = orderDetail.get("matchTeam").toString();
					match = match.replaceAll("\r\n", "");
					matchInfo.put("match",match);
					String isDanStr = orderDetail.get("isDan") == null ? "0" : orderDetail.get("isDan").toString();
					matchInfo.put("isDan",isDanStr);
					String playType = orderDetail.get("playType").toString();
					String playName = playTypeNameMap.getOrDefault(Integer.valueOf(playType), playType);
					String fixedodds = orderDetail.get("fixedodds").toString();
					if (Integer.valueOf(playType).equals(MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode())) {
						playName = StringUtils.isBlank(fixedodds) ? playName : ("[" + fixedodds + "]" + playName);
					}
					matchInfo.put("playType",playName);
					String matchResult = orderDetail.get("matchResult").toString();
					if (isWorlCup) {
						matchInfo.put("cathecticResults",this.getWorldCupCathecticResults(orderDetail));
					} else {
						matchInfo.put("cathecticResults",this.getCathecticResults(fixedodds, String.valueOf(orderDetail.get("ticketData")), matchResult, playTypeNameMap));
					}
					matchInfos.add(matchInfo);
				}
				if (isWorlCup) {
					String changci = orderDetails.get(0).get("changci").toString();
					int detailType = 1;
					if ("T57".equals(changci)) {
						detailType = 2;
					}
					orderDetailDTO.put("detailType",detailType);
				}
				
				orderDetailDTO.put("matchInfos",matchInfos);
				orderDetailDTO.put("orderSn",order.get("order_sn"));
				orderDetailDTO.put("fail_msg",dao.findForObject("CheckLotteryMapper.findFailMsgByOrderSn", order.get("order_sn").toString()));
				
				PageData ticketSchemeParam = new PageData();
				ticketSchemeParam.put("orderSn",order.get("order_sn"));
				ticketSchemeParam.put("programmeSn",order.get("order_sn"));
				PageData ticketDto = this.getTicketScheme(ticketSchemeParam);
				manualOrderDTO.put("orderDetail",orderDetailDTO);
				manualOrderDTO.put("ticketScheme",ticketDto);
			}
			orderManualDTOList.add(manualOrderDTO);
		}

		return orderManualDTOList;
	}
	/**
	 * 世界杯
	 * 
	 * @param orderDetail
	 * @return
	 */
	private List<PageData> getWorldCupCathecticResults(PageData orderDetail) {
		List<PageData> rsts = new ArrayList<PageData>(1);
		PageData ccRst = new PageData();
		String ticketData = String.valueOf(orderDetail.get("ticketData"));
		String changci = String.valueOf(orderDetail.get("changci"));
		String playType = "冠军";
		if ("T57".equals(changci)) {
			playType = "冠亚军";
		}
		String[] split = ticketData.split("@");
		String isGuess = "0";
		String matchResult = String.valueOf(orderDetail.get("matchResult"));
		if (StringUtils.isBlank(matchResult)) {
			matchResult = "待定";
			isGuess = "";
		} else if (split[0].equals(matchResult)) {
			isGuess = "1";
		}
		ccRst.put("playType",playType);
		ccRst.put("matchResult",matchResult);
		List<PageData> cathectics = new ArrayList<PageData>(1);
		PageData cathectic = new PageData();
		cathectic.put("cathectic",String.format("%.2f", Double.valueOf(split[1])));
		cathectic.put("isGuess",isGuess);
		cathectics.add(cathectic);
		ccRst.put("cathectics",cathectics);
		rsts.add(ccRst);
		return rsts;
	}

	/**
	 * 组装投注、赛果列数据
	 * 
	 * @param ticketData
	 * @param matchResult
	 * @return
	 */
	private List<PageData> getCathecticResults(String fixedodds, String ticketData, String matchResult, Map<Integer, String> types) {
		List<PageData> cathecticResults = new LinkedList<PageData>();
		if (StringUtils.isEmpty(ticketData))
			return cathecticResults;
		List<String> ticketDatas = Arrays.asList(ticketData.split(";"));
		List<String> matchResults = null;
		if (StringUtils.isNotEmpty(matchResult) && !ProjectConstant.ORDER_MATCH_RESULT_CANCEL.equals(matchResult)) {
			matchResults = Arrays.asList(matchResult.split(";"));
		}
		if (CollectionUtils.isNotEmpty(ticketDatas)) {
			for (String temp : ticketDatas) {
				PageData cathecticResult = new PageData();
				List<PageData> cathectics = new LinkedList<PageData>();
				String matchResultStr = "";
				String playType = temp.substring(0, temp.indexOf("|"));
				String playCode = temp.substring(temp.indexOf("|") + 1, temp.lastIndexOf("|"));
				String betCells = temp.substring(temp.lastIndexOf("|") + 1);
				String[] betCellArr = betCells.split(",");
				for (int i = 0; i < betCellArr.length; i++) {
					PageData cathectic = new PageData();
					String betCellCode = betCellArr[i].substring(0, betCellArr[i].indexOf("@"));
					String betCellOdds = betCellArr[i].substring(betCellArr[i].indexOf("@") + 1);
					String cathecticStr = getCathecticData(playType, betCellCode);
					cathectic.put("cathectic",cathecticStr + "[" + String.format("%.2f", Double.valueOf(betCellOdds)) + "]");
					if (null != matchResults) {
						for (String matchStr : matchResults) {
							String rstPlayType = matchStr.substring(0, matchStr.indexOf("|"));
							String rstPlayCode = matchStr.substring(matchStr.indexOf("|") + 1, matchStr.lastIndexOf("|"));
							String rstPlayCells = matchStr.substring(matchStr.lastIndexOf("|") + 1);
							if (playType.equals(rstPlayType) && playCode.equals(rstPlayCode)) {
								if (rstPlayCells.equals(betCellCode)) {
									cathectic.put("isGuess","1");
								} else {
									cathectic.put("isGuess","0");
								}
								matchResultStr = getCathecticData(playType, rstPlayCells);
							}
						}
					} else {
						cathectic.put("isGuess","0");
					}
					cathectics.add(cathectic);
				}
				if (StringUtils.isBlank(matchResultStr)) {
					matchResultStr = "待定";
				}
				if (ProjectConstant.ORDER_MATCH_RESULT_CANCEL.equals(matchResult)) {
					matchResultStr = "#?";
				}
				String playName = types.getOrDefault(Integer.valueOf(playType), playType);
				if (Integer.valueOf(playType).equals(MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode())) {
					playName = StringUtils.isBlank(fixedodds) ? playName : ("[" + fixedodds + "]" + playName);
				}
				cathecticResult.put("playType",playName);
				cathecticResult.put("cathectics",cathectics);
				cathecticResult.put("matchResultStr",matchResultStr);
				cathecticResults.add(cathecticResult);
			}
		}
		return cathecticResults;
	}
	/**
	 * 通过玩法code与投注内容，进行转换
	 * 
	 * @param playCode
	 * @param cathecticStr
	 * @return
	 */
	private String getCathecticData(String playTypeStr, String cathecticStr) {
		int playType = Integer.parseInt(playTypeStr);
		String cathecticData = "";
		if (MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode() == playType || MatchPlayTypeEnum.PLAY_TYPE_HAD.getcode() == playType) {
			cathecticData = MatchResultHadEnum.getName(Integer.valueOf(cathecticStr));
		} else if (MatchPlayTypeEnum.PLAY_TYPE_CRS.getcode() == playType) {
			cathecticData = MatchResultCrsEnum.getName(cathecticStr);
		} else if (MatchPlayTypeEnum.PLAY_TYPE_TTG.getcode() == playType) {
			cathecticData = cathecticStr + "球";
			if ("7".equals(cathecticStr)) {
				cathecticData = cathecticStr + "+球";
			}
		} else if (MatchPlayTypeEnum.PLAY_TYPE_HAFU.getcode() == playType) {
			cathecticData = MatchResultHafuEnum.getName(cathecticStr);
		}
		return cathecticData;
	}
	/**
	 * 组装通过方式字符串
	 * 
	 * @param passType
	 * @return
	 */
	private String getPassType(String passType) {
		String[] passTypes = passType.split(",");
		String passTypeStr = "";
		for (int i = 0; i < passTypes.length; i++) {
			passTypeStr += MatchBetTypeEnum.getName(passTypes[i]) + ",";
		}
		return passTypeStr.substring(0, passTypeStr.length() - 1);
	}
}
