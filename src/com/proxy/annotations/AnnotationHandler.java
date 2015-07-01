package com.proxy.annotations;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import com.service.Service;
import com.service.Service.Config;
import com.utils.StringUtils;

public class AnnotationHandler {

	public static void updateProps (Service service) {
		try {
			Class<?> classType = service.getClass();
			Field field = classType.getDeclaredField("prop");
			Properties prop = (Properties) StringUtils.getMethod(classType, field).invoke(service);
			FileOutputStream fos = new FileOutputStream(Config.COFNIG.getPath());
			prop.store(fos, "");
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public static void readProps (Service service) {
		try {
			Class<?> classType = service.getClass();
			Field field = classType.getDeclaredField("prop");
			Class<?> propClass = field.getType();
			Properties prop = (Properties) propClass.newInstance();
			FileInputStream fis = new FileInputStream(Config.COFNIG.getPath());
			prop.load(fis);
			fis.close();
			StringUtils.setMethod(classType, propClass, field).invoke(service, prop);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public static AnnoType getAnnoType (Service service, Method method) {
		try {
			Class<?> classType = service.getClass();
			Method methodImpl = classType.getMethod(method.getName(), method.getParameterTypes());
			if (methodImpl.isAnnotationPresent(ReadProperties.class)) {
				return AnnoType.READ;
			} else if (methodImpl.isAnnotationPresent(UpdateProperties.class)) {
				return AnnoType.UPDATE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return AnnoType.OTHER;
	}
}
