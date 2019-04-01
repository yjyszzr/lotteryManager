package com.fh.service.lottery.checklottery.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
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
	
	@Override
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
}
