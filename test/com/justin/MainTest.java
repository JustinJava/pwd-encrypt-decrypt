package com.justin;

/**
 * @program: DataStructures
 * @description: 密码加解密测试类
 * @author: JustinQin
 * @create: 2021/7/18 11:11
 * @version: v1.0.0
 **/
public class MainTest {
	private final static String HOST="127.0.0.1"; //数据库IP
	private final static String USER = "root"; //数据库用户
	private final static String PASSWORD = "abc@123456"; //数据库密码

	public static void main(String[] args) {
		//初始化加密需要的参数
		args = encryptInitParam(args);

		//初始化解密需要的参数
		//args = decryptInitParam(args);

		//加解密的主入口
		Main.main(args);
	}

	/**
	 * 初始化加密需要的参数
	 * @param args
	 * @return
	 */
	private static String[] encryptInitParam(String[] args) {
		args = new String[3];
		args[0] = HOST;
		args[1] = USER;
		args[2] = PASSWORD;
		return args;
	}

	/**
	 * 初始化解密需要的参数
	 * @param args
	 * @return
	 */
	private static String[] decryptInitParam(String[] args) {
		args = new String[2];
		args[0] = HOST;
		args[1] = USER;
		return args;
	}
}
