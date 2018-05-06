package com.fh.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fh.util.MD5;
import com.fh.util.NetWorkUtil;



public class SmsHl extends AbsSmsProvider {

	private static Logger logger = Logger.getLogger(SmsHl.class);

	public final static String PROVIDER_NAME = "hongliang";

	public final static String PROVIDER_URL = "http://q.hl95.com:8061";
	public final static String PROVIDER_ACCOUNT = "pgtyzm";
	public final static String PROVIDER_PASSWORD = "Pgtyzm2017";

	public final static String PROVIDER_SPLIT_CHAR = ",";
	public final static String PROVIDER_OK = "00";
	public final static int RETURN_LENGTH = 2;
	public final static String PROVIDER_SIGN = "【彩小秘】";

	@Override
	protected String getProviderName() {
		return PROVIDER_NAME;
	}

	@Override
	protected String getProviderURL() {
		return PROVIDER_URL;
	}

	@Override
	protected String getAccount() {
		return PROVIDER_ACCOUNT;
	}

	@Override
	protected String getPass() {
		return PROVIDER_PASSWORD;
	}

	@Override
	protected String getCharset() {
		return "utf8";
	}

	@Override
	protected Result doSend(String mobile, String content) throws IOException {

		Result result = new Result();
		result.setProviderName(PROVIDER_NAME);
		String formatContent = getSignContent(content);
		Map<String, Object> pmap = new LinkedHashMap<String, Object>();

		// tkey为24小时制的当前时间，所在时区为东八区，格式为14位数字如：20160625130530,
		// 客户时间早于或晚于网关时间超过2分钟，则网关拒绝提交。
		/*
		 * username 是 用户名 tkey 是 当前时间,tkey生成规则参照tkey规则 password 是 加密后密码,加密规则参照密码加密规则
		 * mobile 是 手机号码,支持号段参照号段支持 content 是 发送内容（最好不要包含空格和回车） 编码要求参照编码需求 xh 否 扩展的小号
		 */

		pmap.put("username", this.getAccount());
		pmap.put("password", this.getPassword(this.getPass()));
		pmap.put("phone", mobile);
		pmap.put("message", formatContent);
		pmap.put("epid", "122817");
		pmap.put("linkid", "");
		pmap.put("subcode", "");

		String postResult = NetWorkUtil.doPost(getProviderURL(), pmap, getCharset(), true);
		logger.info(String.format("smsname:%s,params: %s,response:%s", PROVIDER_NAME,
				pmap == null ? "" : pmap.toString(), postResult));
		if (postResult!=null) {
			if (postResult.contains(PROVIDER_OK)) {
				result.setIsSuccess(true);
				result.setResponseContent(postResult);
			} else {
				result.setIsSuccess(false);
				result.setResponseContent(postResult);
			}
		} else {
			result.setIsSuccess(false);
			result.setResponseContent("发送失败");
		}
		return result;
	}

	private String getPassword(String password) {
		// md5( md5(password) + tkey))
		String passreturn = MD5.md5(password);
		return passreturn;
	}

	protected String getSignContent(String content) {
		try {
			return URLEncoder.encode(PROVIDER_SIGN + this.getFormatContent(content), "GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		String mobile = "";
		String content = "国庆节快乐";
		Result result = new SmsHl().send(mobile, content);
		System.out.println(result.getIsSuccess());
		System.out.println(result.getResponseContent());
		
	}
}
