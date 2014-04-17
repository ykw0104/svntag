package com.newcosoft.scm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConf {
	
	public static String propretiesPath = "conf/svn.properties";
//	public static String propretiesPath = System.getProperty("user.dir") + File.separator+"svn.properties";
	
	public static Properties getPro(){
		InputStream in = ReadConf.class.getClassLoader().getResourceAsStream(propretiesPath);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(in !=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}
}
