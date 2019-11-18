package com.fh.entity.dto;

import java.util.List;

public class AppSelectDTO {
	private List<String> versionList;
	private List<SystemDTO> paltformList;
	private List<String> channelList;
	public List<String> getVersionList() {
		return versionList;
	}
	public void setVersionList(List<String> versionList) {
		this.versionList = versionList;
	}
	public List<SystemDTO> getPaltformList() {
		return paltformList;
	}
	public void setPaltformList(List<SystemDTO> paltformList) {
		this.paltformList = paltformList;
	}
	public List<String> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}
	
	
}
