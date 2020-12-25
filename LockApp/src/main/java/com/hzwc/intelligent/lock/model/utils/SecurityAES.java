package com.hzwc.intelligent.lock.model.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES对称加密
 * 创建者 张志朋
 * 创建时间	2017年11月22日
 *
 */
public class SecurityAES {





	// 加密
	public  static  String encryptAES(String str) {
//		byte[] b = null;
//		String s = null;
//		try {
//			b = str.getBytes("utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		if (b != null) {
//			s = new BASE64Encoder().encode(b);
//		}
//		return s;

		// 生成一个MD5加密计算摘要
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			String md5Str  = new BigInteger(1, md.digest()).toString(16);
			if(md5Str.length()<32){
				md5Str = 0 + md5Str;
			}
			return md5Str;


		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}


		return  "";


	}

	// 解密
	public String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}


}
