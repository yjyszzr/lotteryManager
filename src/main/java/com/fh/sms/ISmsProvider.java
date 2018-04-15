package com.fh.sms;


public interface ISmsProvider {
	
	Result send(String mobile,String content);
}
