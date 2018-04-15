package com.fh.util;


import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;


public class JsonUtils {
  private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
  private static  Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
  
  public static String beanToJSONString(Object bean) {
	  return  gson.toJson(bean);
  }
  public static JsonObject StringToJsonObject(String strjson) {
	 // JsonObject jsonObject = new JsonParser().parse(strjson).getAsJsonObject();
	  //不转义报错 如：／<等，modify by lvwei 
	  JsonObject jsonObject = new JsonParser().parse(HtmlUtils.htmlEscape(strjson)).getAsJsonObject();
	  return jsonObject;
  }
  
  public static JsonObject NewStringToJsonObject(String strjson) {
	  JsonObject jsonObject = new JsonParser().parse(strjson).getAsJsonObject();
	  return jsonObject;
  }  
  
  public static<T> T jsonToBean(String json,Class<T> obj) { 
      T t = null;
      t = gson.fromJson(json, obj);
      return t;
  }
 
  public static boolean contains(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return false;
    }
    return JsonObject.has(key);
  }

  public static String toJsonValue(String str) {
    return str == null ? "" : str.replace("'", "\\'");
  }

  public static String retrieveStringValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    String rtn = null;

    try {
      if (JsonObject.has(key)) {
         rtn = JsonObject.get(key).getAsString().trim();
      }
    } catch (Exception e) {
    	logger.warn("----->>"+JsonObject.toString());
    	logger.warn("----->>key:"+key);
        logger.warn("get string key exception");
    }

    return rtn;
  }

  public static Boolean retrieveBooleanValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    Boolean rtn = null;

    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsBoolean();
      }
    } catch (Exception e) {
      logger.warn("get boolean key exception");
    }

    return rtn;
  }

  public static Double retrieveDoubleValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    Double rtn = null;
    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsDouble();
      }
    } catch (Exception e) {
      logger.warn("get double key exception");
    }

    return rtn;
  }

  public static Byte retrieveByteValue(JsonObject JsonObject, String key) {
	    if ((JsonObject == null) || (key == null)) {
	      return null;
	    }

	    Byte rtn = null;
	    try {
	      if (JsonObject.has(key)) {
	        rtn = JsonObject.get(key).getAsByte();
	      }
	    } catch (Exception e) {
	      logger.warn("get int key exception");
	    }

	    return rtn;
	  }

  public static Integer retrieveIntValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    Integer rtn = null;
    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsInt();
      }
    } catch (Exception e) {
      logger.warn("get int key exception");
    }

    return rtn;
  }

  public static Long retrieveLongValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    Long rtn = null;
    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsLong();
      }
    } catch (Exception e) {
      logger.warn("get log key exception");
    }

    return rtn;
  }



 
  public static Object retrieveObjectValue(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    Object rtn = null;

    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key);
      }
    } catch (Exception e) {
        logger.warn("get object key exception");
    }

    return rtn;
  }

  public static JsonArray retrieveJsonArray(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    JsonArray rtn = null;

    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsJsonArray();
      }
    } catch (Exception e) {
        logger.warn("get JsonArray key exception");
    }

    return rtn;
  }

  public static JsonObject retrieveJsonObject(JsonObject JsonObject, String key) {
    if ((JsonObject == null) || (key == null)) {
      return null;
    }

    JsonObject rtn = null;

    try {
      if (JsonObject.has(key)) {
        rtn = JsonObject.get(key).getAsJsonObject();
      }
    } catch (Exception e) {
        logger.warn("get JsonObject key exception");
    }

    return rtn;
  }

  public static String retrieveStringValue(JsonObject JsonObject, String key, String defaultValue) {
    String value = retrieveStringValue(JsonObject, key);
    return (value != null) ? value : defaultValue;
  }

  public static Integer retrieveIntValue(JsonObject JsonObject, String key, Integer defaultValue) {
    Integer value = retrieveIntValue(JsonObject, key);
    return (value != null) ? value : defaultValue;
  }

  public static Double retrieveDoubleValue(JsonObject JsonObject, String key, Double defaultValue) {
    Double value = retrieveDoubleValue(JsonObject, key);
    return (value != null) ? value : defaultValue;
  }

 public static void main(String[] args) {
	String s = "{unit_name=件, inbound_notice_code=(INN00000499, notice_detail_id=397, quantity=100.0, sku_id=378, sku_name=内蒙古羊肉前小腿, sku_barcode=2016110600006, batch_code=171106, inbound_notice_stock_batch_id=473, spec=500盒, sku_encode=2017110600006, status=0}";
	JsonParser p = new JsonParser();
	s = HtmlUtils.htmlEscape(s);
	JsonObject jo = StringToJsonObject(s);
	System.out.println(jo.get("inbound_notice_code").getAsString());
}
 
}
