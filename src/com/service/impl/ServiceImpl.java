package com.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JTextField;

import com.proxy.annotations.UpdateProperties;
import com.proxy.annotations.ReadProperties;
import com.service.Service;
import com.utils.StringUtils;

public class ServiceImpl implements Service {
	private String projectName;
	private Properties prop;

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public Properties getProp() {
		return prop;
	}

	public void createIncrementsFloder(String filePath, String projectPath, String createPath) throws Exception {
		projectName = projectPath.substring(projectPath.lastIndexOf(SpecialSymbol.BACK_SLANT.getSymbol()) + 1, projectPath.length());
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (StringUtils.isNotBlank(line)) {
			String packagePath = makePackagePath(line);
			String classPath = projectPath + packagePath;
			String[] split = classPath.substring(classPath.indexOf(SpecialSymbol.BACK_SLANT.getSymbol() + projectName + SpecialSymbol.BACK_SLANT.getSymbol()) + 1, classPath.length()).split(SpecialSymbol.BACK_SLANT.getSymbol() + SpecialSymbol.BACK_SLANT.getSymbol());
			StringBuilder curPath = new StringBuilder(createPath + SpecialSymbol.BACK_SLANT.getSymbol());
			for (int i = 0; i < split.length; i++) {
				String curStr = split[i];
				curPath.append(curStr);
				switch (toType(i, split)) {
				case FILE:
					try {
						copyFile(classPath, curPath.toString());
						//若当前文件是.class文件，遍历当前目录，寻找是否存在当前文件的内部类文件
						if (FileType.JAVA.equals(fileTypeAfterCompile(curStr))) {
							File parentFile = new File(classPath).getParentFile();
							String curClassStr = curStr;
							for (File f : parentFile.listFiles()) {
								if (f.getName().contains(curStr.substring(0, curStr.length() - 6) + SpecialSymbol.INNER_CLASS_SEPARATOR.getSymbol())) {
									classPath = classPath.replace(curClassStr, f.getName());
									copyFile(classPath, curPath.toString().replace(curStr, f.getName()));
									curClassStr = f.getName();
								}
							}
						}
					} catch (FileNotFoundException e) {
						System.out.println(e);
						continue;
					}
					break;
				case DIR:
					File dir = new File(curPath.toString());
					dir.mkdir();
					curPath.append(SpecialSymbol.BACK_SLANT.getSymbol());
					break;
				default:
					break;
				}
			}
			line = br.readLine();
		}
		br.close();
	}
	
	@Override
	@UpdateProperties
	public void updateProps(String projectPath, String createPath) {
		prop.setProperty(Config.PROJECTPATH.getPath(), projectPath);
		prop.setProperty(Config.CREATEPATH.getPath(), createPath);
	}
	
	@Override
	@ReadProperties
	public void fillInText(JTextField projectPath, JTextField createPath) {
		projectPath.setText(prop.getProperty(Config.PROJECTPATH.getPath()).replace(SpecialSymbol.SLANT.getSymbol(), SpecialSymbol.BACK_SLANT.getSymbol()));
		createPath.setText(prop.getProperty(Config.CREATEPATH.getPath()).replace(SpecialSymbol.SLANT.getSymbol(), SpecialSymbol.BACK_SLANT.getSymbol()));
	}
	
	private Type toType (int index, String[] split) {
		return index == split.length - 1 ? Type.FILE : Type.DIR;
	}
	
	private FileType fileTypeBeforeCompile (String line) {
		if (line.indexOf(FileType.JSP.getMatcherBeforeCompile()) != -1) {
			return FileType.JSP;
		} else if (line.indexOf(FileType.STATIC.getMatcherBeforeCompile()) != -1) {
			return FileType.STATIC;
		} else if (line.indexOf(FileType.JAVA.getMatcherBeforeCompile()) != -1) {
			return FileType.JAVA;
		}
		return FileType.NULL;
	}
	
	private FileType fileTypeAfterCompile (String line) {
		if (line.indexOf(FileType.JSP.getMatcherAfterCompile()) != -1) {
			return FileType.JSP;
		} else if (line.indexOf(FileType.JAVA.getMatcherAfterCompile()) != -1) {
			return FileType.JAVA;
		} else {
			return FileType.STATIC;
		}
	}

	private void copyFile(String oldPath, String newPath) throws IOException {
		InputStream inStream = new FileInputStream(oldPath); // 读入原文件
		FileOutputStream fs = new FileOutputStream(newPath);
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 文件存在时
			byte[] buffer = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}
		inStream.close();
		fs.close();
	}
	
	private String makePackagePath (String line) {
		String packagePath = "";
		switch (fileTypeBeforeCompile(line)) {
		case JAVA:
			packagePath = ("\\WEB-INF\\classes\\" + line.substring(line.indexOf("java") + 5, line.length() - 5) + FileType.JAVA.getMatcherAfterCompile()).replace(SpecialSymbol.SLANT.getSymbol(), SpecialSymbol.BACK_SLANT.getSymbol());
			break;
		case JSP:
			packagePath = ("\\WEB-INF\\" + line.substring(line.indexOf("WEB-INF") + 8, line.length())).replace(SpecialSymbol.SLANT.getSymbol(), SpecialSymbol.BACK_SLANT.getSymbol());
			break;
		case STATIC:
			packagePath = ("\\static\\" + line.substring(line.indexOf("static") + 7, line.length())).replace(SpecialSymbol.SLANT.getSymbol(), SpecialSymbol.BACK_SLANT.getSymbol());
			break;
		default:
			break;
		}
		return packagePath;
	}
}
