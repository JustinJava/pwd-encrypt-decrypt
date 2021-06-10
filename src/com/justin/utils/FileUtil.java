package com.justin.utils;

import java.io.File;
import java.net.URLDecoder;

public class FileUtil {

	public static String getJarPath(){
		File file = getFile();
		if (file == null) return null;
		return file.getAbsolutePath();
	}
	
	public static String getJarDir(){
		File file = getFile();
		if (file == null) return null;
		return file.getParent();
	}
	
	public static String getJarName(){
		File file = getFile();
		if (file == null) return null;
		return file.getName();
	}

	private static File getFile() {
		return new File(getFileName());
	}
	
	private static String getFileName() {
		String path = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		try {
			path = URLDecoder.decode(path,"UTF-8");
		} catch (Exception e) {
			return null;
		}
		return path;
	}
	
	public static void main(String[] args) {
		System.out.println(getFileName());
		System.out.println(getJarPath());
		System.out.println(getJarDir());
		System.out.println(getJarName());
	}
	
}
