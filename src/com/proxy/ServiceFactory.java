package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.proxy.annotations.AnnotationHandler;
import com.service.Service;
import com.service.impl.ServiceImpl;

public class ServiceFactory implements InvocationHandler {
	private Service service;

	private ServiceFactory() {
	}

	private ServiceFactory(Service service) {
		this.service = service;
	}

	public static Service createService() {
		Service service = (Service) Proxy.newProxyInstance(
				Service.class.getClassLoader(), new Class[] { Service.class },
				new ServiceFactory(new ServiceImpl()));
		return service;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result;
		switch (AnnotationHandler.getAnnoType(service, method)) {
		case UPDATE:
			result = method.invoke(service, args);
			AnnotationHandler.updateProps(service);
			break;
		case READ:
			AnnotationHandler.readProps(service);
			result = method.invoke(service, args);
			break;
		default:
			result = method.invoke(service, args);
			break;
		}
		return result;
	}

}
