package com.fh.entity.dto;

import java.io.Serializable;
import java.util.List;

public class MatchBetCellDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String playType;
	private List<DlJcZqMatchCellDTO> betCells;
	private Integer single;
	private String fixedOdds;

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public List<DlJcZqMatchCellDTO> getBetCells() {
		return betCells;
	}

	public void setBetCells(List<DlJcZqMatchCellDTO> betCells) {
		this.betCells = betCells;
	}

	public Integer getSingle() {
		return single;
	}

	public void setSingle(Integer single) {
		this.single = single;
	}

	public String getFixedOdds() {
		return fixedOdds;
	}

	public void setFixedOdds(String fixedOdds) {
		this.fixedOdds = fixedOdds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MatchBetCellDTO(String playType, List<DlJcZqMatchCellDTO> betCells, Integer single, String fixedOdds) {
		super();
		this.playType = playType;
		this.betCells = betCells;
		this.single = single;
		this.fixedOdds = fixedOdds;
	}

	public MatchBetCellDTO() {
	}

	@Override
	public String toString() {
		return "MatchBetCellDTO [playType=" + playType + ", betCells=" + betCells + ", single=" + single + ", fixedOdds=" + fixedOdds + "]";
	}

}
