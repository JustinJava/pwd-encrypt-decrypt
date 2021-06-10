package com.justin.service.pass;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDecrypt {
	
	/**
	 * 
	 * @param host
	 * @param user
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public abstract String getDepass(String host,String user) throws FileNotFoundException, IOException, Exception;
	
	/**
	 * 
	 * @param enpassfileContent
	 * @return
	 * @throws Exception
	 */
	public abstract String getDepass(String enpassfileContent) throws Exception;
	
	/**
	 * 
	 * @param paramString
	 * @return
	 */
	public abstract String decryptStr(String paramString);
}
