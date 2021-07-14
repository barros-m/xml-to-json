import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/*********************************************************** 
 *  Create java command line utility which is capable to:
 *  - convert address book xml to json
 *  - convert to xml
 *  - validate it against schema (not provided)
 * 
 * The data source is supplied in an XML file:
 * http://www.bindows.net/documentation/download/ab.xml
 * 
***********************************************************/


public class Main {
    public static void main(String [] args) {

        try {
            File file = new File("/Users/mattheusbarroos/Desktop/converter/AddressBook.xml");
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            document.getDocumentElement().normalize();

            NodeList contacts = document.getElementsByTagName("Contact");
            
            for(int i = 0, numberOfContacts = contacts.getLength(); i < numberOfContacts; i++) {
                Node node = contacts.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println("{");
                    for(int j = 0, numberOfChildren = node.getChildNodes().getLength(); j< numberOfChildren; j++) {
                        Node element = node.getChildNodes().item(j);
                        if(element.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.print("\t\"" + element.getNodeName() + "\": \"" + element.getTextContent() + "\"");
                            if(j != numberOfChildren - 2) {
                                System.out.println(", ");
                            }
                            else {
                                System.out.print("\n}");
                            }
                        }
                    }
                    if(i != numberOfContacts - 1) {
                        System.out.println(", ");
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("This was an error");
        }
    }
}
