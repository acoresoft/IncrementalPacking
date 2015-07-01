package com.controller;

import javax.swing.JOptionPane;

import com.actionlistener.AbstractActionListener;
import com.proxy.ServiceFactory;
import com.service.Service;

public class Controller extends AbstractActionListener {
	private Service service = ServiceFactory.createService();
	
	{
		service.fillInText(text2, text3);
	}
	
	@Override
	public void onSubmit(String filePath, String projectPath, String createPath) {
		try {
			service.updateProps(projectPath, createPath);
			service.createIncrementsFloder(filePath, projectPath, createPath);
			JOptionPane.showMessageDialog(null, "增量包创建成功！", "提示", 2);
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "增量包创建失败！", "提示", 2);
		}
	}
	
}
