package org.lifepoem.samples.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserDemo {

	public static void main(String[] args) throws Exception {
		//1. 建立SAX解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2. 建立解析器
		SAXParser parser = factory.newSAXParser();
		//3. 使用自定义Handler解析XML文档
		parser.parse("sax_demo_01.xml", new MySAXHandler());
	}

}
