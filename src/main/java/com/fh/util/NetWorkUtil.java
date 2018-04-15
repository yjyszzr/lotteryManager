package com.fh.util;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;


public class NetWorkUtil {
  private static final Logger logger = LoggerFactory.getLogger(NetWorkUtil.class);
  public static final int DefaultSocketTimeoutMs = 25000;
  public static final int LongSocketTimeoutMs = 60000;
  public static final int DefaultConnectTimeoutMs = 20000;
  
  public static final String HttpRequestSentExceptionResponse = "HTTP_REQUEST_SENT_EXCEPTION";

  /**
   * 执行一个HTTP POST请求，返回请求响应的HTML
   * @param url 请求的URL地址
   * @param params 请求的查询参数,可以为null
   * @param charset 字符集
   * @param pretty 是否美化
   * @return 返回请求响应的HTML
   */
  public static String doPost(String url, Map<String, Object> params, String charset, boolean pretty) {
    return doPost(url, params, charset, DefaultSocketTimeoutMs, DefaultConnectTimeoutMs, pretty);  
  }
  public static String doPost(String url, Map<String, Object> params, String charset, int socketTimeoutMs, int connectTimeoutMs, boolean pretty) {
	    logger.info(String.format("web post: %s", url));
	    logger.info(String.format("params: %s", params == null ? "" : params.toString()));
	    StringBuffer response = new StringBuffer();
	    HttpClient client = new HttpClient();
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeoutMs);
	    client.getHttpConnectionManager().getParams().setSoTimeout(socketTimeoutMs);
	    PostMethod method = new PostMethod(url);
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
	    // 设置Http Post数据
	    if (params != null) {
	      // 填入各个表单域的值
	      NameValuePair[] data = null;
	      Set<String> sets = params.keySet();
	      Object[] arr = sets.toArray();
	      int mxsets = sets.size();
	      if (mxsets > 0) {
	        data = new NameValuePair[mxsets];
	      }
	      for (int i = 0; i < mxsets; i++) {
	        String key = (String) arr[i];
	        Object value = params.get(key);
	        String val = null;
	        if (value instanceof Map) {
	          val = value.toString();
	        }else {
	          val = (String)value;
	        }
	        data[i] = new NameValuePair(key, val);
	      }
	      if (data!=null){
	        method.setRequestBody(data);
	      }
	      method.getParams().setContentCharset(charset);
	    }
	    BufferedReader reader = null;
	    try {
	      int statusCode = client.executeMethod(method);
	      if (statusCode == HttpStatus.SC_OK) {
	        reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          if (pretty) {
	            response.append(line).append("\n");
	          } else {
	            response.append(line);
	          }
	        }

	      }
			} catch (HttpException e) {
				// System.out.println("Http错误原因：" + e.getMessage());
				logger.warn("Http错误原因：", e);
				
			} catch (IOException e) {
				logger.warn("IO错误原因：" + e.getMessage());
				
			} catch (Exception ex) {
				logger.info(ex.getMessage());
				
		} finally {
	      if (reader != null) {
	        try {
	          reader.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	      method.releaseConnection();
	      ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
	    }

	    String res=response.toString();
	    logger.info(String.format("params: %s,response:%s", params == null ? "" : params.toString(), res));
	    return res;
	  }
  public static String doPostToWeiXin(String url, String jsonString, String charset, boolean pretty) {
	    StringBuffer response = new StringBuffer();
	    HttpClient client = new HttpClient();
	    int socketTimeoutMs=DefaultSocketTimeoutMs;
	    int connectTimeoutMs=DefaultConnectTimeoutMs;
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeoutMs);
	    client.getHttpConnectionManager().getParams().setSoTimeout(socketTimeoutMs);
	    PostMethod method = new PostMethod(url);
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
	    method.getParams().setContentCharset(charset);
	   
	    BufferedReader reader = null;
	    try {
	      if (null != jsonString) {
	 	    	InputStream  in = new ByteArrayInputStream(jsonString.toString().getBytes(charset));  
	 	    	method.setRequestBody(in); 
	       }
	      int statusCode = client.executeMethod(method);
	      if (statusCode == HttpStatus.SC_OK) {
	        reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          if (pretty) {
	            response.append(line).append("\n");
	          } else {
	            response.append(line);
	          }
	        }

	      }
	    } catch (HttpException e) {
	      // System.out.println("Http错误原因：" + e.getMessage());
	      logger.warn("Http错误原因：",e);
	      
	    } catch (IOException e) {
	      logger.warn("IO错误原因：" + e.getMessage());
	     
	    } catch (Exception ex) {
	       logger.info(ex.getMessage());
	     
	    } finally {
	      if (reader != null) {
	        try {
	          reader.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	      method.releaseConnection();
	      ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
	    }
	    return response.toString();
	  }
  

  
  public static String getHostName(){
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return e.getMessage();
    }
  }
  
  
  /**
   * 执行一个HTTP GET请求，返回请求响应的HTML
   * @param url 请求的URL地址
   * @param params 请求的查询参数,可以为null
   * @param charset 字符集
   * @return 返回请求响应的HTML
   */
  @SuppressWarnings("rawtypes")
  public static String doGet(String url, Map params, String charset) {
    String ret = "";
    try {
      ret = doGet(url, params, charset, 10000, 10000, true);
    } catch (HttpException e) {
      logger.warn("", e);
    } catch (IOException e) {
      logger.warn("", e);
    }
    return ret;
  }

  @SuppressWarnings("rawtypes")
  public static String doGet(String url, Map params, String charset, int socketTimeoutMs, int connectTimeoutMs, boolean pretty) throws HttpException,
      IOException {
    StringBuffer response = new StringBuffer();
    HttpClient client = new HttpClient();
    client.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeoutMs);
    client.getHttpConnectionManager().getParams().setSoTimeout(socketTimeoutMs);
    GetMethod method = new GetMethod(url);
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
    if (params != null) {
      NameValuePair[] data = null;
      Set sets = params.keySet();
      Object[] arr = sets.toArray();
      int mxsets = sets.size();
      if (mxsets > 0) {
        data = new NameValuePair[mxsets];
      }
      for (int i = 0; i < mxsets; i++) {
        String key = (String) arr[i];
        String val = (String) params.get(key);
        data[i] = new NameValuePair(key, val);
      }
      if (data!=null){
        method.setQueryString(data);
      }
      method.getParams().setContentCharset(charset);
    }
    BufferedReader reader = null;
    try {
      int statusCode = client.executeMethod(method);
      if (statusCode == HttpStatus.SC_OK) {
        reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
        String line;
        while ((line = reader.readLine()) != null) {
          if (pretty) {
            response.append(line).append("\n");
          } else {
            response.append(line);
          }
        }
      }
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      method.releaseConnection();
      ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
    }
    return response.toString();
  }
  
    public static void doGetFile(String url, Map params, String charset, int socketTimeoutMs, int connectTimeoutMs, String fileName) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeoutMs);
        client.getHttpConnectionManager().getParams().setSoTimeout(socketTimeoutMs);
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        if (params != null) {
            NameValuePair[] data = null;
            Set sets = params.keySet();
            Object[] arr = sets.toArray();
            int mxsets = sets.size();
            if (mxsets > 0) {
                data = new NameValuePair[mxsets];
            }
            for (int i = 0; i < mxsets; i++) {
                String key = (String) arr[i];
                String val = (String) params.get(key);
                data[i] = new NameValuePair(key, val);
            }
            if (data != null) {
                method.setQueryString(data);
            }
            method.getParams().setContentCharset(charset);
        }
        
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                is = method.getResponseBodyAsStream();
                byte[] data = new byte[1024];
                int len = 0;
                fos = new FileOutputStream(fileName);
                while ((len = is.read(data)) != -1) {
                    fos.write(data, 0, len);
                }
            }
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            method.releaseConnection();
            ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
        }
    }
    
    public static void doGet(String url, Map params, String charset, String fileName) throws HttpException, IOException {
        doGetFile(url, params, charset, 10000, 10000, fileName);
    }

    @SuppressWarnings("rawtypes")
    public static String doGet(String url, Map params, String charset, int socketTimeoutMs, int connectTimeoutMs) throws HttpException, IOException {
        return doGet(url, params, charset, 10000, 10000, true);
    }
    
	
    public static void main(String[] args) {
        try {
            /*
             * doGet("http://139.129.76.202",new HashMap(),"UTF-8",3000,1000);
             * doPost("http://www.baidu.com",new HashMap(),"UTF-8",true);
             * NetWorkUtil.doPost("http://www.baidu.com", new HashMap(),
             * "UTF-8", true, new HisPostEvent());
             */
            
            /*String response = doPostWithExceptionThrown("http://139.128.76.202",new HashMap(),"UTF-8",3000,1000, false, null);
            System.out.println(response);*/
           
            // doGetFile(url, new HashMap(), "UTF-8", 3000, 1000);

        } catch (Exception e) {
            logger.error("exception");
            e.printStackTrace();
        }
    }
}
