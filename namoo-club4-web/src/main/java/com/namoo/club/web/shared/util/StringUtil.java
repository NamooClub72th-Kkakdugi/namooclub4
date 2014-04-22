package com.namoo.club.web.shared.util;

public class StringUtil {
	//
	
	private StringUtil() {
		//
	}
	
	public static boolean isEmpty(String str) {
		return (str != null && str.length() > 0) ? false : true;
	}
}
