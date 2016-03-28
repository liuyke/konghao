package org.liuyk.konghao.engine;

public class DataAnalysizerFactory {

	public static DataAnalysizer createAnalysizer() {
		return JsoupHtmlAnalysizer.getInstance();
	}
	
}
