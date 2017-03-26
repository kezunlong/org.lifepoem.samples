package org.lifepoem.samples.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MySAXHandler extends DefaultHandler {
	@Override
	public void startDocument() throws SAXException {
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("\n读取文档结束");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		System.out.print("<");
		System.out.print(qName);
		if(attributes != null) {
			for(int i=0;i<attributes.getLength();i++) {
				System.out.print(" " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"");
			}
		}
		System.out.print(">");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.print(new String(ch, start, length));
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.print("</");
		System.out.print(qName);
		System.out.print(">");
	}
}
