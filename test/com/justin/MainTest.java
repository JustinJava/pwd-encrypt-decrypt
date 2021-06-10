package com.justin;

/**
 * pwd-encrypt-decrypt Test
 */
public class MainTest {
	private final static String HOST="password";
	private final static String USER = "root";
	private final static String PASSWORD = "abc@123456";

	public static void main(String[] args) {
		//init encrypt param
		//args = encryptInitParam(args);

		//init decrypt param
		args = decryptInitParam(args);

		//execute
		Main.main(args);
	}

	private static String[] encryptInitParam(String[] args) {
		args = new String[3];
		args[0] = HOST;
		args[1] = USER;
		args[2] = PASSWORD;
		return args;
	}

	private static String[] decryptInitParam(String[] args) {
		args = new String[2];
		args[0] = HOST;
		args[1] = USER;
		return args;
	}
}
