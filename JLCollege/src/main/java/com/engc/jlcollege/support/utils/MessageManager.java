package com.engc.jlcollege.support.utils;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	protected String messagePropertyFile;
	protected ResourceBundle bundle;

	public MessageManager(String messageFile) {
		this.messagePropertyFile = messageFile;
	}

	public String getMessage(String tag) {
		try{
			if (this.bundle == null) {
				this.bundle = getResourceBundle(this.messagePropertyFile);
				if (this.bundle == null) {
					return "";
				}
			}
			return this.bundle.getString(tag).trim();
		}catch(Exception e){
			e.printStackTrace();
			return tag;
		}
		
	}

	private ResourceBundle getResourceBundle(String baseName) {
		return getResourceBundle(baseName, null);
	}

	private ResourceBundle getResourceBundle(String baseName, Locale locale) {
		if (locale == null) {
			return ResourceBundle.getBundle(baseName);
		}
		return ResourceBundle.getBundle(baseName, locale);
	}

}
