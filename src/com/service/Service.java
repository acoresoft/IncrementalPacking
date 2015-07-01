package com.service;

import javax.swing.JTextField;

public interface Service {
	
	enum Type {
		DIR, FILE
	}
	
	enum FileType {
		JAVA("/src/main/java/", ".class"), JSP("/src/main/webapp/WEB-INF/", ".jsp"), STATIC("/src/main/webapp/static/", ""), NULL("", "");
		private final String matcherBeforeCompile;
		private final String matcherAfterCompile;
		private FileType (String matcherBeforeCompile, String matcherAfterCompile) {
			this.matcherBeforeCompile = matcherBeforeCompile;
			this.matcherAfterCompile = matcherAfterCompile;
		}
		public String getMatcherBeforeCompile() {
			return matcherBeforeCompile;
		}
		public String getMatcherAfterCompile() {
			return matcherAfterCompile;
		}
	}
	
	enum SpecialSymbol {
		SLANT("/"), BACK_SLANT("\\"), INNER_CLASS_SEPARATOR("$");
		private final String symbol;
		private SpecialSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getSymbol() {
			return symbol;
		}
	}
	
	enum Config {
		COFNIG("config.properties"), PROJECTPATH("projectPath"), CREATEPATH("createPath");
		private final String path;
		private Config(String path) {
			this.path = path;
		}
		public String getPath() {
			return path;
		}
	}
	
	void createIncrementsFloder(String filePath, String projectPath, String createPath) throws Exception;
	
	void updateProps(String projectPath, String createPath);
	
	void fillInText(JTextField projectPath, JTextField createPath);
}
