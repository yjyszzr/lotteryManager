package com.fh.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	public final static String STORE_INFO = "S";
	public final static String STORE_INFO_STORE_ID = "store_id";
	public final static String STORE_INFO_STORE_SN = "store_sn";
	
	public final static String GOODS_INFO = "G";
	public final static String GOODS_INFO_GOODS_ID = "goods_id";
	public final static String GOODS_INFO_GOODS_BARCODE = "goods_barcode";
	public final static String GOODS_INFO_SHOP_GOODS_BARCODE = "shop_goods_barcode";
	public final static String GOODS_INFO_SKU_BARCODE = "sku_barcode";
	public final static String GOODS_INFO_SKU_ENCODE = "sku_encode";
	
	public final static String USER_INFO = "U";
	public final static String USER_INFO_USER_ID = "USER_ID";
	public final static String USER_INFO_USERNAME = "USERNAME";
	
	public final static String SUPPLIER_INFO = "P";
	public final static String SUPPLIER_INFO_SUPPLIER_ID = "supplier_id";
	public final static String SUPPLIER_INFO_SUPPLIER_SN = "supplier_sn";

	// Redis服务器IP
	private static String ADDR = "120.132.80.200";
	// Redis的端口号
	private static int PORT = 6380;
	// 访问密码
	private static String AUTH = "";
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			// config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			// config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setPageDataInfo(Jedis jedisObj, String type, PageData pd, String param) {
		Jedis jedis = null;
		if (null == jedisObj) {
			jedis = jedisPool.getResource();
		} else {
			jedis = jedisObj;
		}
		for (Object map : pd.map.entrySet()){
			pd.map.put(((Map.Entry)map).getKey(), ((Map.Entry)map).getValue().toString());
			System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());  
		} 
		jedis.hmset(type + param + pd.getString(param), pd.map);
		

		if (null == jedisObj) {
			jedis.close();
		}

	}

	private static void setPageDataListInfo(Jedis jedisObj, String type, List<PageData> pdList, String param) {
		Jedis jedis = null;
		if (null == jedisObj) {
			jedis = jedisPool.getResource();
		} else {
			jedis = jedisObj;
		}
		for (int i = 0; i < pdList.size(); i++) {
			setPageDataInfo(jedis, type, pdList.get(i), param);
		}
		if (null == jedisObj) {
			jedis.close();
		}
	}

	private static void getPageDataInfo(Jedis jedisObj, PageData pd, String type, String param, String srcName,
			String tarName) {
		Jedis jedis = null;
		if (null == jedisObj) {
			jedis = jedisPool.getResource();
		} else {
			jedis = jedisObj;
		}

		pd.put(tarName, jedis.hgetAll(type + pd.getString(param)).get(srcName));

		if (null == jedisObj) {
			jedis.close();
		}
	}

	private static void getPageDataInfoList(Jedis jedisObj, List<PageData> pdList, String type, String param,
			String srcName, String tarName) {
		Jedis jedis = null;
		if (null == jedisObj) {
			jedis = jedisPool.getResource();
		} else {
			jedis = jedisObj;
		}
		for (int i = 0; i < pdList.size(); i++) {
			getPageDataInfo(jedis, pdList.get(i), type, param, srcName, tarName);
		}
		if (null == jedisObj) {
			jedis.close();
		}

	}

	private static void delPageDataInfo(Jedis jedisObj, String key) {
		Jedis jedis = null;
		if (null == jedisObj) {
			jedis = jedisPool.getResource();
		} else {
			jedis = jedisObj;
		}

		Set<String> set = jedis.keys(key + "*");
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String keyStr = it.next();
			jedis.del(keyStr);

		}
		if (null == jedisObj) {
			jedis.close();
		}
	}

	public static void setGoodsInfo(List<PageData> pdList) {
		Jedis jedis = jedisPool.getResource();
		delPageDataInfo(jedis, GOODS_INFO);
		setPageDataListInfo(jedis, GOODS_INFO, pdList, GOODS_INFO_GOODS_ID);
		setPageDataListInfo(jedis, GOODS_INFO, pdList, GOODS_INFO_GOODS_BARCODE);
		setPageDataListInfo(jedis, GOODS_INFO, pdList, GOODS_INFO_SHOP_GOODS_BARCODE);
		setPageDataListInfo(jedis, GOODS_INFO, pdList, GOODS_INFO_SKU_ENCODE);
		setPageDataListInfo(jedis, GOODS_INFO, pdList, GOODS_INFO_SKU_BARCODE);
		jedis.close();
	}
	public static void setUserInfo(List<PageData> pdList) {
		Jedis jedis = jedisPool.getResource();
		delPageDataInfo(jedis, USER_INFO);
		setPageDataListInfo(jedis, USER_INFO, pdList, USER_INFO_USER_ID);
		setPageDataListInfo(jedis, USER_INFO, pdList, USER_INFO_USERNAME);
		jedis.close();
	}
	public static void setSupplierInfo(List<PageData> pdList) {
		Jedis jedis = jedisPool.getResource();
		delPageDataInfo(jedis, SUPPLIER_INFO);
		setPageDataListInfo(jedis, SUPPLIER_INFO, pdList, SUPPLIER_INFO_SUPPLIER_ID);
		setPageDataListInfo(jedis, SUPPLIER_INFO, pdList, SUPPLIER_INFO_SUPPLIER_SN);
		jedis.close();		
	}
	
	public static void setStoreInfo(List<PageData> pdList) {
		Jedis jedis = jedisPool.getResource();
		delPageDataInfo(jedis, STORE_INFO);
		setPageDataListInfo(jedis, STORE_INFO, pdList, STORE_INFO_STORE_ID);
		setPageDataListInfo(jedis, STORE_INFO, pdList, STORE_INFO_STORE_SN);
		jedis.close();
	}

	public static void getStoreInfoById(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, STORE_INFO + STORE_INFO_STORE_ID, param, srcName, tarName);
		jedis.close();
	}

	public static void getStoreInfoBySn(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, STORE_INFO + STORE_INFO_STORE_SN, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getStoreBySn(PageData pd, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfo(jedis, pd, STORE_INFO + STORE_INFO_STORE_SN, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getUserInfoById(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, USER_INFO + USER_INFO_USER_ID, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getUserInfoByUsername(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, USER_INFO + USER_INFO_USERNAME, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getSupplierInfoById(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, SUPPLIER_INFO + SUPPLIER_INFO_SUPPLIER_ID, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getSupplierById(PageData pd, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfo(jedis, pd, SUPPLIER_INFO + SUPPLIER_INFO_SUPPLIER_ID, param, srcName, tarName);
		jedis.close();
	}
	
	public static void getSupplierInfoBySn(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, SUPPLIER_INFO + SUPPLIER_INFO_SUPPLIER_SN, param, srcName, tarName);
		jedis.close();
	}

	public static void getGoodsInfoById(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, GOODS_INFO + GOODS_INFO_GOODS_ID, param, srcName, tarName);
		jedis.close();
	}	
	
	public static void getGoodsInfoByGoodBarcode(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, GOODS_INFO + GOODS_INFO_GOODS_BARCODE, param, srcName, tarName);
		jedis.close();
	}	
	
	public static void getGoodsInfoByShopGoodBarcode(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, GOODS_INFO + GOODS_INFO_SHOP_GOODS_BARCODE, param, srcName, tarName);
		jedis.close();
	}	
	
	public static void getGoodsInfoBySkuBarcode(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, GOODS_INFO + GOODS_INFO_SKU_BARCODE, param, srcName, tarName);
		jedis.close();
	}	
	
	public static void getGoodsInfoBySkuEncode(List<PageData> pdList, String param, String srcName, String tarName) {
		Jedis jedis = jedisPool.getResource();
		getPageDataInfoList(jedis, pdList, GOODS_INFO + GOODS_INFO_SKU_ENCODE, param, srcName, tarName);
		jedis.close();
	}	
	

}
