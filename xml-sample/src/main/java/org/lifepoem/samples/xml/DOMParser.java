package org.lifepoem.samples.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {

	public static void main(String[] args) {
		writeXMLByDOM();
	}

	private static void readXMLByDOM() {
		//1.建立DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//2.获取DocumentBuilder
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		//3.定义Document接口对象，通过DocumentBuilder类进行DOM树的转换
		Document doc = null;
		//String file = "D:" + File.separator + "dom_demo_01.xml";
		String file = "dom_demo_01.xml"; 
		try {
			doc = builder.parse(file);
		}
		catch(SAXException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//4.查找节点
		NodeList nl = doc.getElementsByTagName("contact");
		//5.遍历节点内容
		for(int i = 0; i < nl.getLength(); i++) {
			Element e = (Element)nl.item(i);
			//输出NodeList中第一个子节点中文本节点的内容
			//e.getElementsByTagName("name").item(0)返回name节点
			//.getFirstChild()返回文本节点（文本内容也是一个节点）
			System.out.println("姓名：" + e.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
			System.out.println("Email：" + e.getElementsByTagName("email").item(0).getFirstChild().getNodeValue());
		}
	}

	/**
	 * 使用DOM操作不仅可以读取文件，也可以生成和修改XML文件
	 */
	private static void writeXMLByDOM() {
		//1.建立DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//2.获取DocumentBuilder
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		//3.定义Document接口对象
		Document doc = builder.newDocument();
		
		//4. 建立各个节点
		Element addressList = doc.createElement("addresslist");
		Element contact = doc.createElement("contact");
		Element name = doc.createElement("name");
		Element email = doc.createElement("email");
		
		//5. 设置节点内容
		name.appendChild(doc.createTextNode("柯尊龙"));
		email.appendChild(doc.createTextNode("lifepoem@163.com"));
		
		//6. 设置节点关系
		addressList.appendChild(contact);
		contact.appendChild(name);
		contact.appendChild(email);
		doc.appendChild(addressList);
		
		//7. 输出到文件中
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
		}
		catch(TransformerConfigurationException e) {
			e.printStackTrace();
		}
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("dom_demo_02.xml"));
		try {
			t.transform(source, result);
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
