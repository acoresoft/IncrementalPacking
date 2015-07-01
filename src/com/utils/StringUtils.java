package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StringUtils {
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtils.isBlank(cs);
	}
	
	public static Method setMethod (Class<?> classType, Class<?> fieldClass, Field field) throws Exception {
		String fieldName = field.getName();
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + fieldName.substring(1);
		Method setMethod = classType.getMethod(setMethodName, fieldClass);
		return setMethod;
	}
	
	public static Method getMethod (Class<?> classType, Field field) throws Exception {
		String fieldName = field.getName();
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getMethodName = "get" + firstLetter + fieldName.substring(1);
		Method getMethod = classType.getMethod(getMethodName);
		return getMethod;
	}
}
