package com.fh.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;

import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

public class GetOwnStore {

	@SuppressWarnings("unchecked")
	public static List<PageData> getOwnStore(String[] types){
		List<PageData> list = new ArrayList<PageData>();
		if(types == null || types.length == 0) return list;
		Session session = Jurisdiction.getSession();
		List<PageData> stores = (List<PageData>) session.getAttribute(Const.SESSION_DATA_RIGHTS);
		if(CollectionUtils.isNotEmpty(stores)) {
			List<String> typeList = Arrays.asList(types);
			for (PageData store : stores) {
				if(typeList.contains(store.get("store_type_sort").toString())) {
					list.add(store);
				}
			}
		}
		return list;
	} 
}
