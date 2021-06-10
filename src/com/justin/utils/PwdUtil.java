package com.justin.utils;

import java.io.File;
import java.util.ResourceBundle;

public class PwdUtil {
	
	public final static String FILE = "config";
	public final static String FILE_PATH = "filepath";
	public final static String FILE_NAME = "encrypt.pass";
	
	/**
	 * Get encrypt.pass absolute path by config.properties
	 * @return
	 */
	public static String getPassFile(){
		ResourceBundle bundle = ResourceBundle.getBundle(FILE);
		String passFile = bundle.getString(FILE_PATH);
		if (passFile.startsWith(".")) {
			passFile = passFile.replace(".", FileUtil.getJarDir());
		}
		
		File pass = new File(passFile);
		if (!pass.getParentFile().exists()) {
			passFile  = FileUtil.getJarDir() + File.separator + FILE_NAME;
			pass= new File(passFile);
		}
		
		return pass.getAbsolutePath();
	}
	
	public static void main(String[] args) {
		System.out.println(getPassFile());
	}
}
