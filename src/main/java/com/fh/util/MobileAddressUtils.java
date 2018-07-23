package com.fh.util;

import java.io.BufferedReader;

import java.io.DataOutputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;

import java.net.URL;

import net.sf.json.JSONObject;

 

/**

__GetZoneResult_ = {    mts:'1367306',    province:'河南',    catName:'中国移动',    telString:'13673066760',	areaVid:'30500',	ispVid:'3236139',	carrier:'河南移动'}
 */

public class MobileAddressUtils {

	 
	/**
	 * 
	 * @param IP
	 * @return 省份
	 * @throws Exception
	 */
	public static String getProvinceByIp(String tel)  {
		
		try {
			JSONObject json = getJsonByIp(tel);
			if(json == null) {
				return "*";
			}
			return json.get("province").toString(); //tpy
		} catch (Exception e) {
			return "*";
		}
	}
	 
	
	 
		 
		private static  JSONObject getJsonByIp(String tel) throws Exception {
			// json_result用于接收返回的json数据

			String json_result = null;

			try {

				json_result = MobileAddressUtils.getAddresses("tel=" + tel);

			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}

			if(json_result == null) {
				return null;
			}
			JSONObject json = JSONObject.fromObject(json_result);
			return json;  
		}
	
	/**
	 * 
	 * @param content 请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getAddresses(String content)

			throws UnsupportedEncodingException {

		// 这里调用淘宝API

		String urlStr = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";


		String returnStr = getResult(urlStr, content, "GBK");

		if (returnStr != null) {

			// 处理返回的省市区信息

			returnStr = returnStr.split("=")[1];

			return returnStr;

		}

		return null;

	}

	 
	
	/**

	 * @param urlStr 请求的地址

	 * @param content 请求的参数 格式为：name=xxx&pwd=xxx

	 * @param encoding 服务器端请求编码。如GBK,UTF-8等

	 * @return

	 */

	private static String getResult(String urlStr, String content, String encoding) {

		URL url = null;

		HttpURLConnection connection = null;

		try {

			url = new URL(urlStr);

			connection = (HttpURLConnection) url.openConnection();// 新建连接实例

			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒

			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒

			connection.setDoOutput(true);// 是否打开输出流 true|false

			connection.setDoInput(true);// 是否打开输入流true|false

			connection.setRequestMethod("POST");// 提交方法POST|GET

			connection.setUseCaches(false);// 是否缓存true|false

			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(connection

					.getOutputStream());// 打开输出流往对端服务器写数据

			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx

			out.flush();// 刷新

			out.close();// 关闭输出流

			BufferedReader reader = new BufferedReader(new InputStreamReader(

					connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据

			// ,以BufferedReader流来读取

			StringBuffer buffer = new StringBuffer();

			String line = "";

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			reader.close();

			return buffer.toString();

		} catch (IOException e) {

			return null;

		} finally {

			if (connection != null) {

				connection.disconnect();// 关闭连接

			}

		}

	}

 

}
