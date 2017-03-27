package org.lifepoem.samples.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DOM4JDemo {
	
	private static final String DEMO_FILE = "xmlfiles" + File.separator + "dom4j_demo.xml";

	public static void main(String[] args) {
		writeXMLByDOM4J();
		readXMLByDOM4J();
	}

	private static void readXMLByDOM4J() {
		File file = new File(DEMO_FILE);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		}
		catch(DocumentException e) {
			e.printStackTrace();
		}

	    // iterate through child elements of root
		Element root = doc.getRootElement();
		Iterator iter = root.elementIterator();
	    while (iter.hasNext()) {
	        Element contact = (Element)iter.next();
	        printContact(contact);
	    }
	    
	    // Navigation with XPath
	    // 在使用XPath时，需要导入Jaxen， Jaxen is a universal Java XPath engine. 
	    List list = doc.selectNodes("//addresslist/contact");
	    for(int i = 0; i < list.size(); i++) {
	    	Element contact = (Element)list.get(i);
	    	printContact(contact);
	    }
	    
	    // 针对示例文档，下面三行语句等价
	    Node node1 = doc.selectSingleNode( "//addresslist/contact/name" );
	    Node node2 = doc.selectSingleNode( "//contact/name" );
	    Node node3 = doc.selectSingleNode( "//name" );
	    System.out.println("Id1: " + node1.valueOf("@id"));
	    System.out.println("Id2: " + node2.valueOf("@id"));
	    System.out.println("Id3: " + node3.valueOf("@id"));
	}
	
	private static void printContact(Element contact) {
		System.out.println("Name: " + contact.elementText("name"));
        System.out.println("Id: " + contact.element("name").attributeValue("id"));
        System.out.println("Email: " + contact.elementText("email"));
	}

	private static void writeXMLByDOM4J() {
		Document doc = DocumentHelper.createDocument();
		Element addresslist = doc.addElement("addresslist");
		Element contact = addresslist.addElement("contact");
		Element name = contact.addElement("name").addAttribute("id", "vincent");
		Element email = contact.addElement("email");
		name.setText("柯尊龙");
		email.setText("lifepoem@163.com");
		
		// createPrettyPrint将会创建自动缩进和换行的格式化XML文件
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File(DEMO_FILE)), format);
			writer.write(doc);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
