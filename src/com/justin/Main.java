package com.justin;

import java.io.IOException;

import com.justin.service.pass.IDecrypt;
import com.justin.service.pass.IEncrypt;
import com.justin.service.pass.impl.Decrypt;
import com.justin.service.pass.impl.Encrypt;

/**
 * @program: pwd-encrypt-decrypt
 * @description: pwd-encrypt-decrypt
 * @author: JustinQin
 * @create: 2021/6/10 11:11
 * @version: v1.0.0
 **/
public class Main {
	public static void main(String[] args) {
		int paramLength = args.length;
		if((args == null) || ((paramLength != 3) && (paramLength != 2))){
			System.out.println("The param's length must be 2 or 3 !!!");
			System.out.println("Such as:");
			System.out.println("1.encrypt command.");
			System.out.println("java -jar pwd-encrypt-decrypt.jar host user password");
			System.out.println("2.decrypt command.");
			System.out.println("java -jar pwd-encrypt-decrypt.jar host user");
			System.exit(1);
		}
		
		String host = args[0];
		String user = args[1];
		String password = (paramLength == 3) ? password = args[2] : null;

		Main main = new Main();
		try {
			if (null != password) {
				System.out.println(host + "," + user +"," + password);
				//encrypt entrance
				main.enPass(host,user,password);
				System.out.println("Encrypt password is ok.");
			}else{
				System.out.println(host + "," + user);
				//decrypt entrance
				password = main.dePass(host,user);
				System.out.println("Decrypt password is ok.");
				System.out.println(password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void enPass(String host, String user, String password) throws IOException {
		IEncrypt iEncrypt = new Encrypt();
		iEncrypt.createPassfile(host, user, password);
	}
	
	private String dePass(String host, String user) throws Exception {
		IDecrypt iDecrypt = new Decrypt();
		return iDecrypt.getDepass(host, user);
	}

}
