package com.newcosoft.scm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SvnMain {

	private String batPath; // bat文件路径
	private String version; // 版本号

	public SvnMain() {
		batPath = System.getProperty("user.dir") + File.separator
				+ "runSvn.bat";
		version = ReadConf.getPro().getProperty("version");
	}

	public static void main(String[] args) {
		SvnMain svnMain = new SvnMain();
		Map<String,String> svnMap = new HashMap<String,String>();
		String  cmdParam = null; //命令参数
		
		// 临时测试参数
//		String[] tempArgs = new String[1];
//		 tempArgs[0] = "bcg:ear";
		
		 if(args!=null && args.length>0 && !args[0].trim().equals("")&&!args[0].equals("all") ){   
			//局部打tag
			cmdParam = args[0];
			System.out.println(cmdParam);
			svnMap = svnMain.parseParam(cmdParam);
			for(Entry<String,String> eachEntry:svnMap.entrySet()){
				String[] valueArray = eachEntry.getValue().split(",");
				String key = eachEntry.getKey();
				String param = null;
				for(String valueStr:valueArray){
					param = " "+key + " "+valueStr;
					svnMain.doCmd(param,svnMain.getVersion());
				}
			}
		}else if(args!=null && args.length>0 && args[0].equals("all")){
			//全量打tag
			cmdParam = ReadConf.getPro().getProperty("svnparam");
			svnMap = svnMain.parseParam(cmdParam);
			for(Entry<String,String> eachEntry:svnMap.entrySet()){
				String[] valueArray = eachEntry.getValue().split(",");
				String key = eachEntry.getKey();
				String param = null;
				for(String valueStr:valueArray){
					param = " "+key + " "+valueStr;
					svnMain.doCmd(param,svnMain.getVersion());
				}
			}
		}else{
			System.out.println("请输入参数后在重新执行");
		}
		
		
	}

	/**
	 * 解析输入的参数,保存为Map类型
	 * 
	 * @param param
	 *            参数值约定:如 bcg:bank,business;mcg:ims
	 */
	public Map<String, String> parseParam(String param) {
		String[] paramArray = param.split(";");
		Map<String, String> paramMap = new HashMap<String, String>();

		for (String each : paramArray) {
			String[] entryStr = each.split(":"); // 如: key:bcg
													// value:bank,business
			if (entryStr.length == 2) {
				paramMap.put(entryStr[0], entryStr[1]);
			} else {
				System.out.println("输入参数有误");
			}
		}

		return paramMap;
	}

	/**
	 * 调用bat脚本
	 * 
	 * @param param
	 *            输入参数格式为 bcg bank
	 * @param version
	 *            版本号
	 */
	public void doCmd(String param, String version) {
		Process process = null;
		String line = null;

		InputStream fis = null;
		BufferedReader reader = null;
		try {
			process = Runtime.getRuntime().exec(
					batPath + " " + param + " " + version); // bat执行文件路径和参数
			fis = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(fis));
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					System.out.print(line.trim()); // 输出信息
					if (line.contains("Committed revision")) {
						System.out.print("       提交成功!");
					}
					System.out.print("\n");
				}
			}
			if (process.waitFor() != 0) {
				System.out.println("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (process != null) {
				process.destroy();
			}
		}
	}

	public String getBatPath() {
		return batPath;
	}

	public String getVersion() {
		return version;
	}
}
