package com.fh.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.LongAdder;

/**
 *
 * 获取sn序结构为： 年月日时分+毫秒+业务编码1位+当前线程id%99 2位+自增序列4位
 */
public class SNGenerator {

	private static LongAdder inc = new LongAdder();
	private static final ThreadLocal<DecimalFormat> DF_0000 = ThreadLocal.withInitial(() -> new DecimalFormat("0000"));
	private static final ThreadLocal<DecimalFormat> DF_00 = ThreadLocal.withInitial(() -> new DecimalFormat("00"));
	private static final int INC_MAX = 1000;

	/**
	 * 产生下一个SN
	 * 
	 * @return
	 */

	public static String nextSN(int bsCode) {
		StringBuilder nextId = new StringBuilder();
		nextId.append(DateUtilNew.getCurrentDateTime(LocalDateTime.now(), DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS")));
		nextId.append(bsCode);// 业务码
		nextId.append(DF_00.get().format(Thread.currentThread().getId() % 99));
		inc.increment();
		if (inc.intValue() > INC_MAX) {
			inc.reset();
			inc.increment();
		}
		nextId.append(DF_0000.get().format(inc.intValue()));
		return nextId.toString();
	}

}
