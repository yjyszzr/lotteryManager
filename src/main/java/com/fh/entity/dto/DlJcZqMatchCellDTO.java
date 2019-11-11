package com.fh.entity.dto;

import java.io.Serializable;
import java.util.List;

public class DlJcZqMatchCellDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public String cellCode;

	public String cellName;

	public String cellOdds;

	public List<DlJcZqMatchCellDTO> cellSons;

	public DlJcZqMatchCellDTO(String cellCode, String cellName, String cellOdds) {
		this.cellCode = cellCode;
		this.cellName = cellName;
		this.cellOdds = cellOdds;
	}

	public DlJcZqMatchCellDTO() {
	}

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellOdds() {
		return cellOdds;
	}

	public void setCellOdds(String cellOdds) {
		this.cellOdds = cellOdds;
	}

	public List<DlJcZqMatchCellDTO> getCellSons() {
		return cellSons;
	}

	public void setCellSons(List<DlJcZqMatchCellDTO> cellSons) {
		this.cellSons = cellSons;
	}

	@Override
	public String toString() {
		return "DlJcZqMatchCellDTO [cellCode=" + cellCode + ", cellName=" + cellName + ", cellOdds=" + cellOdds + ", cellSons=" + cellSons + "]";
	}

}
