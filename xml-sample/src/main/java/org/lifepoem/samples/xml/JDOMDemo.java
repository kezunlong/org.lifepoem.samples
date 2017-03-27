package org.lifepoem.samples.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class JDOMDemo {

	private static final String DEMO_FILE = "xmlfiles" + File.separator + "jdom_demo.xml";

	public static void main(String[] args) throws Exception {
		writeXMLByJDOM();
		readXMLByJDOM();
	}

	private static void readXMLByJDOM() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(DEMO_FILE);
		
		Element addresslist = doc.getRootElement();
		List<Element> list = addresslist.getChildren("contact");
		for(Element contact : list) {
			Element nameElement = contact.getChild("name");
			String name = nameElement.getText();
			String id = nameElement.getAttribute("id").getValue();
			String email = contact.getChildText("email");
			
			System.out.println("----------------------");
			System.out.println("Name: " + name + ", id: " + id);
			System.out.println("email: " + email);
			System.out.println("----------------------");
			System.out.println();
		}
	}

	private static void writeXMLByJDOM() {
		Element addresslist = new Element("addresslist");
		Element contact = new Element("contact");
		Element name = new Element("name");
		Element email = new Element("email");
		Attribute id = new Attribute("id", "vincent");
		
		name.setText("柯尊龙");
		email.setText("lifepoem@163.com");
		name.setAttribute(id);
		contact.addContent(name);
		contact.addContent(email);
		addresslist.addContent(contact);
		Document doc = new Document(addresslist);
		
		XMLOutputter out = new XMLOutputter();
		out.setFormat(out.getFormat().setEncoding("UTF-8"));
		try {
			out.output(doc, new FileOutputStream(DEMO_FILE));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
