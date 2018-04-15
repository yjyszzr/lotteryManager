package com.fh.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import Decoder.BASE64Encoder;

public class BarcodeUtil {

	public static String create39Code(String code) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// Create the barcode bean
			Code39Bean bean = new Code39Bean();
			final int dpi = 140;
			// Configure the barcode generator
			bean.setModuleWidth(UnitConv.in2mm(1.0f / 140)); // makes the narrow
			bean.setHeight(10);
			bean.setWideFactor(3);// width exactly one pixel
			bean.doQuietZone(false);
			bean.setQuietZone(2);// 两边空白区
			// bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
			bean.setFontSize(2);
			bean.setDisplayStartStop(true);
			// Set up the canvas provider for monochrome JPEG output
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/png", dpi,
					BufferedImage.TYPE_BYTE_BINARY, false, 0);
			// Generate the barcode
			bean.generateBarcode(canvas, code);
			// Signal end of generation
			canvas.finish();
		} catch (Exception e) {
			System.out.println("单号：{}生成条形码出错" + code + e);
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(baos.toByteArray());// 返回Base64编码过的字节数组字符串
	}

	public static void createBarcode(List<PageData> list, String src, String tar) {

		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put(tar, create39Code(list.get(i).getString(src)));
			}
		}

	}
}
