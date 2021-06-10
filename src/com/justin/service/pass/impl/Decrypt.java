package com.justin.service.pass.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.justin.constants.Global;
import com.justin.service.pass.IDecrypt;
import com.justin.utils.PwdUtil;

public class Decrypt implements IDecrypt {

	private static String ALGORITHM = "DESede";
	final byte[] initKeyBytes;
	private Cipher encip1;
	private Cipher decip1;
	private Cipher encip;
	private Cipher decip;
	private String desKey;
	private String dateKey;
	
	public Decrypt() {
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
	}
	
	@Override
	public String getDepass(String host, String user) throws FileNotFoundException, IOException, Exception {
		String enPass = getEnPassFromFile(host,user);
		init3DES();
		String dePass = decryptStr(enPass);
		return dePass;
	}
	
	@Override
	public String getDepass(String passfileContent) throws Exception  {
		String enPass = getEnpassFromContent(passfileContent);
		init3DES();
		String dePass = decryptStr(enPass);
		return dePass;
	}
	
	@Override
	public String decryptStr(String str) {
		try {
			byte[] hex2md = hex2md(str);
			byte[] doFinal = this.decip.doFinal(hex2md);
			return new String(doFinal);
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
			
			byte[] keyBytes = md5Buffer(decrypt1Str(this.dateKey)).getBytes();
			
			DESedeKeySpec desKeySpec2 = new DESedeKeySpec(keyBytes);
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

	private String decrypt1Str(String in) {
		try {
			return new String(this.decip1.doFinal(hex2md(in)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	private byte[] hex2md(String in) {
		if (in.length() % 2 != 0 ) in = in + "0";
		byte[] bytes = new byte[in.length() / 2];
		for (int i = 0; i < in.length() / 2; ++i) {
			byte[] temp = new byte[2];
			temp[0] = (byte) in.charAt(i * 2);
			temp[1] = (byte) in.charAt(i * 2 + 1);
			bytes[i] = (byte) Integer.parseInt(new String(temp),16);
		}
		return bytes;
	}
	
	/**
	 * 
	 * @param host
	 * @param user
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private String getEnPassFromFile(String host, String user) throws FileNotFoundException,IOException,Exception {
		String passFileName = getFileName();
		File passFile = new File(passFileName);
		try {
			LineNumberReader read = new LineNumberReader(new FileReader(passFile));
			String enPass = null;
			boolean flag = true;
			while (flag) {
				String readLine = read.readLine();
				if (null == readLine) {
					flag = false;
					break;
				}
				enPass = readLine;
			}
			
			if ((null == enPass) || (null != enPass && !enPass.startsWith(host + ":" + user + ":"))) throw new Exception("The request password not found!");
			
			String[] enPassArr = enPass.split(":");
			boolean isMd5OK = md5StrOk(enPassArr[0] + ":" + enPassArr[1] + ":" + enPassArr[2] + ":"+ enPassArr[3],enPassArr[4]);
			if (!isMd5OK) throw new Exception("The password file is change!");
			
			read.close();
			this.dateKey = enPassArr[2];
			return enPassArr[3];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param passfileContent
	 * @return
	 * @throws Exception
	 */
	private String getEnpassFromContent(String passfileContent) throws Exception {
		if (null == passfileContent) throw new Exception("The request password not found!");
		
		String[] contentArry = passfileContent.split(":");
		boolean isMd5OK = md5StrOk(contentArry[0] + ":" + contentArry[1] + ":" + contentArry[2] + ":"+ contentArry[3],contentArry[4]);
		
		if (!isMd5OK) throw new Exception("The password file is change!");
		
		this.dateKey = contentArry[2];
		
		return contentArry[3];
	}

	private boolean md5StrOk(String from,String md5) {
		if (md5 == null) return false;
		return md5Buffer(from).equals(md5);
	}

	private String md5Buffer(String strSrc) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strSrc.getBytes());
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

	private String getFileName() {
		String passFile = PwdUtil.getPassFile();
		System.out.println("The password file path is:" + passFile);
		return passFile;
	}

}
