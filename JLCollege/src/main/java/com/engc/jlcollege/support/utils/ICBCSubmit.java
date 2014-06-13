package com.engc.jlcollege.support.utils;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


import cn.com.infosec.icbc.ReturnValue;

import com.engc.jlcollege.pay.config.ICBCConfig;




/* *
 *类名：ICBCSubmit
 *功能：工行B2C接口请求提交类
 *详细：构造工行接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 */

public class ICBCSubmit {
    
    /**
     * 工行接口地址
     */
	private static final String ICBC_GATEWAY_NEW = ICBCConfig.ICBC_GATEWAY_NEW;

    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName)  throws Exception{

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"icbcsubmit\" action=\"" + ICBC_GATEWAY_NEW + "\" method=\"" + strMethod + "\">");
        sbHtml.append("<input type=\"hidden\" name=\"interfaceName\" value=\"" + ICBCConfig.interfaceName + "\"/>");
        sbHtml.append("<input type=\"hidden\" name=\"interfaceVersion\" value=\"" + ICBCConfig.interfaceVersion + "\"/>");
        
        
        String tranData = buildTranDataXML(sParaTemp);
        System.out.println(tranData);
        String password = ICBCConfig.keypass;
        byte[] byteSrc = tranData.getBytes();
    	char[] keyPass = password.toCharArray();
    	
		String bastPath = ICBCSubmit.class.getResource("").getPath();
		bastPath = URLDecoder.decode(bastPath, "utf-8");// 
		String filePath = bastPath.substring(0, bastPath.indexOf("WEB-INF"));
		filePath = filePath.substring(1, filePath.length());
		String realPath = filePath + "WEB-INF/cert/";// 完整本地路径
		realPath = realPath.replace("/", "\\");
		
		FileInputStream in1 = new FileInputStream(realPath+ICBCConfig.mercrt);
    	byte[] bcert = new byte[in1.available()];
    	in1.read(bcert);
    	in1.close();
    	FileInputStream in2 = new FileInputStream(realPath+ICBCConfig.merkey);
    	byte[] bkey = new byte[in2.available()];
    	in2.read(bkey);
    	in2.close();
    	
    	
        byte[] sign =ReturnValue.sign(byteSrc,byteSrc.length,bkey,keyPass);
        if (sign==null) {
        	System.out.println("签名失败,签名返回为空，请检查证书私钥和私钥保护口令是否正确。");
        }else{
        	System.out.println("签名成功");
        	
        	byte[] EncTran = ReturnValue.base64enc(byteSrc);
    	    String TranDataBase64=new String(EncTran).toString();
//    	    System.out.println("tranData BASE64编码："+TranDataBase64);
        	
    	    byte[] EncSign = ReturnValue.base64enc(sign);
    	    String SignMsgBase64=new String(EncSign).toString();
//    	    System.out.println("签名信息BASE64编码："+SignMsgBase64);
    	    
    		byte[] EncCert=ReturnValue.base64enc(bcert);
    		String CertBase64=new String(EncCert).toString();
//    		System.out.println("证书公钥BASE64编码："+CertBase64);
    		
//    		String aa = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><B2CReq><interfaceName>ICBC_PERBANK_B2C</interfaceName><interfaceVersion>1.0.0.11</interfaceVersion><orderInfo><orderDate>20140531102510</orderDate><curType>001</curType><merID>4301EC23750709</merID><subOrderInfoList><subOrderInfo><orderid>20140520095619000029</orderid><amount>100000</amount><installmentTimes>1</installmentTimes><merAcct>4301018419100356118</merAcct><goodsID></goodsID><goodsName>test</goodsName><goodsNum>1</goodsNum><carriageAmt>100000</carriageAmt></subOrderInfo></subOrderInfoList></orderInfo><custom><verifyJoinFlag>0</verifyJoinFlag><Language>ZH_CN</Language></custom><message><creditType>2</creditType><notifyType>AG</notifyType><resultType>1</resultType><merReference>172.16.17.23</merReference><merCustomIp></merCustomIp><goodsType>0</goodsType><merCustomID></merCustomID><merCustomPhone></merCustomPhone><goodsAddress></goodsAddress><merOrderRemark></merOrderRemark><merHint></merHint><remark1></remark1><remark2></remark2><merURL>http://www.baidu.com</merURL><merVAR></merVAR></message></B2CReq>";
//    		String aa = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><B2CReq><interfaceName>ICBC_PERBANK_B2C</interfaceName><interfaceVersion>1.0.0.11</interfaceVersion><orderInfo><orderDate>20140531100555</orderDate><curType>001</curType><merID>4301EC23750709</merID><subOrderInfoList><subOrderInfo><orderid>201009011621421</orderid><amount>120</amount><installmentTimes>1</installmentTimes><merAcct>4301018419100356118</merAcct><goodsID>001</goodsID><goodsName>威尼熊</goodsName><goodsNum>2</goodsNum><carriageAmt></carriageAmt></subOrderInfo></subOrderInfoList></orderInfo><custom><verifyJoinFlag>0</verifyJoinFlag><Language>ZH_CN</Language></custom><message><creditType>2</creditType><notifyType>AG</notifyType><resultType>1</resultType><merReference></merReference><merCustomIp>211.103.78.13</merCustomIp><goodsType>1</goodsType><merCustomID>123456</merCustomID><merCustomPhone>13900000000</merCustomPhone><goodsAddress>三里屯</goodsAddress><merOrderRemark>防欺诈接口专用</merOrderRemark><merHint>请保留包装</merHint><remark1></remark1><remark2></remark2><merURL>http://localhost/EbizSimulate/emulator/Newb2c_Pay_Mer.jsp</merURL><merVAR>test</merVAR></message></B2CReq>";
//    		byte[] EncTran = ReturnValue.base64enc(aa.getBytes());
//    	    String TranDataBase64=new String(EncTran).toString();
//    	    System.out.println("tranData BASE64编码："+TranDataBase64);
    		
    		sbHtml.append("<input type=\"hidden\" name=\"tranData\" value=\"" + TranDataBase64 + "\"/>");
    		sbHtml.append("<input type=\"hidden\" name=\"merSignMsg\" value=\"" + SignMsgBase64 + "\"/>");
    		sbHtml.append("<input type=\"hidden\" name=\"merCert\" value=\"" + CertBase64 + "\"/>");
    	}
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['icbcsubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
   public static String buildTranDataXML(Map<String,String> sParaTemp) throws Exception{
		// 构造tranData
		Document doc = DocumentHelper.createDocument();
		Element rootElmt = doc.addElement("B2CReq");
		// 构造子元素
		Element interfaceName = rootElmt.addElement("interfaceName");
		interfaceName.setText(ICBCConfig.interfaceName);
		Element interfaceVersion = rootElmt.addElement("interfaceVersion");
		interfaceVersion.setText(ICBCConfig.interfaceVersion);
		// 订单明细信息
		Element orderInfo = rootElmt.addElement("orderInfo");
		// 订单日期
		Element orderDate = orderInfo.addElement("orderDate");
		orderDate.setText("20140601135520");
		// orderDate.setText(UtilDate.getOrderNum());
		// 支付币种
		Element curType = orderInfo.addElement("curType");
		curType.setText("001");
		// 商户代码
		Element merID = orderInfo.addElement("merID");
		merID.setText(ICBCConfig.merID);
		// 子订单列表
		Element subOrderInfoList = orderInfo.addElement("subOrderInfoList");
		// 子订单明细
		Element subOrderInfo = subOrderInfoList.addElement("subOrderInfo");
		// 订单编号
		Element orderid = subOrderInfo.addElement("orderid");
		orderid.setText(sParaTemp.get("order_no"));
		// 订单金额
		Element amount = subOrderInfo.addElement("amount");
		BigDecimal bg = new BigDecimal(sParaTemp.get("total_fee"));
		BigDecimal b2 = new BigDecimal(100);
		amount.setText(String.valueOf(bg.multiply(b2).intValue()));
		// 分期付款期数
		Element installmentTimes = subOrderInfo.addElement("installmentTimes");
		installmentTimes.setText("1");
		// 商户账号
		Element merAcct = subOrderInfo.addElement("merAcct");
		merAcct.setText(ICBCConfig.merAcct);
		// 商品编号-不填
		Element goodsID = subOrderInfo.addElement("goodsID");
		goodsID.setText("");
		// 商品名称
		Element goodsName = subOrderInfo.addElement("goodsName");
		goodsName.setText(sParaTemp.get("body"));
		// 商品数量
		Element goodsNum = subOrderInfo.addElement("goodsNum");
		goodsNum.setText("1");
		// 已含运费金额
		Element carriageAmt = subOrderInfo.addElement("carriageAmt");
		carriageAmt.setText(String.valueOf(bg.multiply(b2).intValue()));
		// 订单明细信息
		Element custom = rootElmt.addElement("custom");
		// 检验联名标志
		Element verifyJoinFlag = custom.addElement("verifyJoinFlag");
		verifyJoinFlag.setText("0");
		// 语言版本
		Element Language = custom.addElement("Language");
		Language.setText("ZH_CN");
		// 订单明细信息
		Element message = rootElmt.addElement("message");
		// 支持订单支付的银行卡种类
		Element creditType = message.addElement("creditType");
		creditType.setText("2");
		// 通知类型-通知商户
		Element notifyType = message.addElement("notifyType");
		notifyType.setText("HS");
		// 结果发送类型
		Element resultType = message.addElement("resultType");
		resultType.setText("1");
		// 商户reference
		Element merReference = message.addElement("merReference");
		merReference.setText(ICBCConfig.merReference);
		// 客户端IP
		Element merCustomIp = message.addElement("merCustomIp");
		merCustomIp.setText("");
		// 虚拟商品/实物商品标志位
		Element goodsType = message.addElement("goodsType");
		goodsType.setText("0");
		// 买家用户号
		Element merCustomID = message.addElement("merCustomID");
		merCustomID.setText("");
		// 买家联系电话
		Element merCustomPhone = message.addElement("merCustomPhone");
		merCustomPhone.setText("");
		// 收货地址
		Element goodsAddress = message.addElement("goodsAddress");
		goodsAddress.setText("");
		// 订单备注
		Element merOrderRemark = message.addElement("merOrderRemark");
		merOrderRemark.setText("");
		// 商城提示
		Element merHint = message.addElement("merHint");
		merHint.setText("");
		// 备注字段1
		Element remark1 = message.addElement("remark1");
		remark1.setText("");
		// 备注字段2
		Element remark2 = message.addElement("remark2");
		remark2.setText("");
		// 返回商户URL
		Element merURL = message.addElement("merURL");
		merURL.setText(ICBCConfig.notify_url);
		// 返回商户变量
		Element merVAR = message.addElement("merVAR");
		merVAR.setText("");

		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("GBK");// 根据需要设置编码
		StringWriter out = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(out, format);
		// 打印doc
		xmlWriter.write(doc);
		xmlWriter.flush();
		// 关闭输出器的流，即是printWriter
		String s = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>" + doc.getRootElement().asXML();
		// System.out.println( s);

		// 明文tranData
		String tranData = s;
		return tranData;	
	}
   
	/**
    * @description 将xml字符串转换成map
    * @param xml
    * @return Map
    */
	public static Map<String,String> readStringXmlOut(String xml) {
       Map<String,String> map = new HashMap<String,String>();
       Document doc = null;
       try {
           // 将字符串转为XML
           doc = DocumentHelper.parseText(xml); 
           // 获取根节点
           Element rootElt = doc.getRootElement(); 
           //interfaceName
           String interfaceName = rootElt.element("interfaceName").getText();
           map.put("interfaceName", interfaceName);
           //interfaceVersion
           String interfaceVersion = rootElt.element("interfaceVersion").getText();
           map.put("interfaceVersion", interfaceVersion);        
           //orderInfo
           Element orderEle = rootElt.element("orderInfo");
           //orderDate
           String orderDate = orderEle.element("orderDate").getText();
           map.put("orderDate", orderDate);        
           //curType
           String curType = orderEle.element("curType").getText();
           map.put("curType", curType);        
           //merID
           String merID = orderEle.element("merID").getText();
           map.put("merID", merID);        
           //subOrderInfoList
           Element subOrderListEle = orderEle.element("subOrderInfoList");
           List<Element> subOrderList =  subOrderListEle.elements("subOrderInfo");
           for(Element subOrderInfoEle:subOrderList){
           	 //orderid
               String orderid = subOrderInfoEle.element("orderid").getText();
               map.put("orderid", orderid);
               //amount
               String amount = subOrderInfoEle.element("amount").getText();
               map.put("amount", amount);
               //installmentTimes
               String installmentTimes = subOrderInfoEle.element("installmentTimes").getText();
               map.put("installmentTimes", installmentTimes);
               //merAcct
               String merAcct = subOrderInfoEle.element("merAcct").getText();
               map.put("merAcct", merAcct);                
               //tranSerialNo
               String tranSerialNo = subOrderInfoEle.element("tranSerialNo").getText();
               map.put("tranSerialNo", tranSerialNo);                       
           }
           
           
           //custom
           Element customEle = rootElt.element("custom");
           //verifyJoinFlag
           String verifyJoinFlag = customEle.element("verifyJoinFlag").getText();
           map.put("verifyJoinFlag", verifyJoinFlag);            
           //JoinFlag
           String JoinFlag = customEle.element("JoinFlag").getText();
           map.put("JoinFlag", JoinFlag);            
           //UserNum
           String UserNum = customEle.element("UserNum").getText();
           map.put("UserNum", UserNum);                        
           
           //bank
           Element bankEle = rootElt.element("bank");
           //TranBatchNo
           String TranBatchNo = bankEle.element("TranBatchNo").getText();
           map.put("TranBatchNo", TranBatchNo);              
           //notifyDate
           String notifyDate = bankEle.element("notifyDate").getText();
           map.put("notifyDate", notifyDate);                     
           //tranStat
           String tranStat = bankEle.element("tranStat").getText();
           map.put("tranStat", tranStat);                     
           //comment
           String comment = bankEle.element("comment").getText();
           map.put("comment", comment);    
           
       } catch (DocumentException e) {
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return map;
   }
}
