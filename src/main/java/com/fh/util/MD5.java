package com.fh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/** 
 * 说明：MD5处理
 * 创建人：FH Q313596790
 * 修改时间：2014年9月20日
 * @version
 */
public class MD5 {

	/**
     * Encodes a string
     * 
     * @param str String to encode
     * @return Encoded String
     * @throws Exception
     */
    public static String crypt(String str)
    {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        
        StringBuffer hexString = new StringBuffer();
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                }               
                else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }               
            }
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return hexString.toString();
    }
	
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	public static void main(String[] args) {
		
		System.out.println(crypt("1000000196"+"18810969495"));
	}
}
