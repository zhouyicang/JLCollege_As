package com.engc.jlcollege.pay.config;

import com.engc.jlcollege.support.utils.MessageManager;


public class ICBCConfig {

	public static String seller_email = "";
	public static String partner = "";
	public static String key = "";
	
	
	//工行接口提交地址
	public static String ICBC_GATEWAY_NEW = "";

	//字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "gbk";
	
	//签名方式 不需修改
	public static String sign_type = "MD5";

	//服务器异步通知页面路径
	public static String notify_url = "";
	
	public static String interfaceName = "";
	public static String interfaceVersion = "";
	public static String merID = "";
	public static String merAcct = "";
	public static String keypass = "";
	public static String merkey = "";
	public static String mercrt = "";
	public static String ebb2cpubliccrt = "";
	public static String merReference = "";
	
	static {
		MessageManager env = new MessageManager("env");
		
		ICBC_GATEWAY_NEW = env.getMessage("ICBC_GATEWAY_NEW");
		notify_url = env.getMessage("ICBC_NOTIFY_URL");
		interfaceName = env.getMessage("interfaceName");
		interfaceVersion = env.getMessage("interfaceVersion");
		merID = env.getMessage("merID");
		merAcct = env.getMessage("merAcct");
		keypass = env.getMessage("keypass");
		merkey = env.getMessage("merkey");
		mercrt = env.getMessage("mercrt");
		ebb2cpubliccrt = env.getMessage("ebb2cpubliccrt");
		merReference = env.getMessage("merReference");
		
		
	}

}
