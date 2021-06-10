package com.justin.service.pass.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.justin.constants.Global;
import com.justin.service.pass.IEncrypt;
import com.justin.utils.PwdUtil;

public class Encrypt implements IEncrypt {

	private static String ALGORITHM = "DESede";
	final byte[] initKeyBytes;
	private Cipher encip1;
	private Cipher decip1;
	private Cipher encip;
	private Cipher decip;
	private String desKey;
	private String dateKey;
	
	public Encrypt() {
		byte[] byteArray = new byte[32];
		byteArray[0] = 12;
		byteArray[1] = 42;
		byteArray[2] = -33;
		byteArray[3] = 25;
		byteArray[4] = 126;
		byteArray[5] = -25;
		byteArray[6] = 66;
		byteArray[7] = 74;
		byteArray[8] = 19;
		byteArray[9] = 2;
		byteArray[10] = 51;
		byteArray[11] = 15;
		byteArray[12] = 11;
		byteArray[13] = 88;
		byteArray[14] = 22;
		byteArray[15] = 34;
		byteArray[16] = 12;
		byteArray[17] = 42;
		byteArray[18] = -33;
		byteArray[19] = 25;
		byteArray[20] = 126;
		byteArray[21] = -25;
		byteArray[22] = 66;
		byteArray[23] = 74;
		byteArray[24] = 19;
		byteArray[25] = 2;
		byteArray[26] = 51;
		byteArray[27] = 15;
		byteArray[28] = 11;
		byteArray[29] = 88;
		byteArray[30] = 22;
		byteArray[31] = 34;
		this.initKeyBytes = byteArray;
		init3DES();
	}
	
	@Override
	public void createPassfile(String host, String user, String password) throws IOException{
		try {
			String passFile = getPassFile();
			File file = new File(passFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			String enPass = encryptStr(password);
			String md5Date = encrypt1Str(this.dateKey);
			
			String fileContent = host + ":" + user + ":" + md5Date + ":" + enPass;
			String md5FileContent = md5Buffer(fileContent);
			
			fileContent = fileContent + ":" + md5FileContent;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
			bufferedWriter.write(fileContent + "\n");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public String encryptStr(String password) {
		try {
			return md2hex(this.encip.doFinal(password.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean init3DES() {
		if (Global.DES_KEY != null) this.desKey = Global.DES_KEY;
		try {
			SecretKeyFactory secKeyFac1 = SecretKeyFactory.getInstance(ALGORITHM);
			DESedeKeySpec desKeySpec1 = new DESedeKeySpec(this.initKeyBytes);
			SecretKey desKey1 = secKeyFac1.generateSecret(desKeySpec1);
			this.encip1 = Cipher.getInstance(ALGORITHM);
			this.decip1 = Cipher.getInstance(ALGORITHM);
			this.encip1.init(1, desKey1);
			this.decip1.init(2, desKey1);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			this.dateKey = dateFormat.format(new Date());
			this.desKey = md5Buffer(this.dateKey);
			
			DESedeKeySpec desKeySpec2 = new DESedeKeySpec(this.desKey.getBytes());
			SecretKey desKey2 = secKeyFac1.generateSecret(desKeySpec2);
			this.encip = Cipher.getInstance(ALGORITHM);
			this.decip = Cipher.getInstance(ALGORITHM);
			this.encip.init(1, desKey2);
			this.decip.init(2, desKey2);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String md5Buffer(String dateKey) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(dateKey.getBytes());
			return md2hex(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String md2hex(byte[] digest) {
		String hexString = "";
		String tmp = "";
		
		for (int i = 0; i < digest.length; ++i) {
			tmp = Integer.toHexString(digest[i] & 0xFF);
			if (tmp.length() == 1) {
				hexString = hexString + "0" + tmp;
			}else{
				hexString = hexString + tmp;
			}
		}
		return hexString.toUpperCase();
	}

	
	private String encrypt1Str(String in) {
		try {
			return md2hex(this.encip1.doFinal(in.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getPassFile() {
		String getPassFile = PwdUtil.getPassFile();
		System.out.println("The passFile's is:" + getPassFile);
		return getPassFile;
	}

}
