package com.ecc.liana.weixin.communicate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassTool {
	private static final String MODIFY = "Modified : ";
	private static final String ADD = "Added : ";

	public static void parseFilesToTable() throws Exception {
		String fileName = "E:\\SVNfile\\version1.0.txt";
		String fileNameOut = "E:\\SVNfile\\outversion1.0.txt";
		String replacePath = "/src2015.01.27/branchs/branch1/";
		String line = "";
		BufferedReader in = null;
		FileInputStream inFile = null;
		BufferedWriter out = null;
		FileOutputStream outFile = null;
		try {
			inFile = new FileInputStream(fileName);
			in = new BufferedReader(new InputStreamReader(inFile));
			outFile = new FileOutputStream(fileNameOut);
			out = new BufferedWriter(new OutputStreamWriter(outFile));
			StringBuffer strbf = null;
			String str = null;
			Map map = new HashMap();
			while ((line = in.readLine()) != null) {
//				System.out.println(line);
				if (line.startsWith(MODIFY) && line.lastIndexOf(".")>0 && !line.endsWith(".DS_Store")) {
					System.out.println(line);
					strbf = new StringBuffer().append("<include name=\"").append(line.substring(line.indexOf(MODIFY)
							+ MODIFY.length())).append("\"/>").append("\n");
					str = strbf.toString().replace(replacePath, "");

					if(!map.containsKey(str)){
						map.put(str, str);
					}
				}else if (line.startsWith(ADD) && line.lastIndexOf(".")>0 && !line.endsWith(".DS_Store")) {
					System.out.println(line);
					strbf = new StringBuffer().append("<include name=\"").append(line.substring(line.indexOf(ADD)
							+ ADD.length())).append("\"/>").append("\n");
					str = strbf.toString().replace(replacePath, "");

					if(!map.containsKey(str)){
						map.put(str, str);
					}
				}

			}
			
			System.out.println("map.size="+map.size());
			Iterator it = map.keySet().iterator();
			
			while(it.hasNext()){
				String src = (String)map.get((String)it.next());
				out.write(src);
			}

			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (inFile != null) {
					inFile.close();
				}
				if (in != null) {
					in.close();
				}
				if (outFile != null) {
					outFile.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ClassTool.parseFilesToTable();

	}
}
