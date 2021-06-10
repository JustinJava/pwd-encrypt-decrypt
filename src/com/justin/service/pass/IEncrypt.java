package com.justin.service.pass;

import java.io.IOException;

public interface IEncrypt {
	
	/**
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @throws IOException
	 */
	public abstract void createPassfile(String host,String user,String password) throws IOException;
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public abstract String encryptStr(String password);
}
