package com.fh.sms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SmsFactoySingleton {
    private SmsFactoySingleton() {
    	
    }
	private static final Map<String,AbsSmsProvider> instance = new ConcurrentHashMap<String,AbsSmsProvider>();
	static {
		instance.put(SmsHl.PROVIDER_NAME, new SmsHl());
	}
    public static AbsSmsProvider getSmsProvider() {
        return instance.get(SmsHl.PROVIDER_NAME);
    }

}
