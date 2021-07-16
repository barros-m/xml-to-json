import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.io.FileWriter;

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

        Scanner in = new Scanner(System.in);
        try {
            ///Users/mattheusbarroos/Desktop/converter/Main.java
            ///Users/mattheusbarroos/Desktop/converter/AddressBook.xml
            System.out.println("Please enter full path for the xml file (including file name):");
            
            String filePath = in.nextLine();
            
            // Prepare document
            File file = new File(filePath.toString());
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            document.getDocumentElement().normalize();
            
            String result = "";

            NodeList contacts = document.getElementsByTagName("Contact");
            result += "{\n\"AddressBook\": {\n\"Contact\": [\n"; 
            
            for(int i = 0, numberOfContacts = contacts.getLength(); i < numberOfContacts; i++) {
                Node node = contacts.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    //System.out.println("\t{");
                    result += "\t{\n";
                    for(int j = 0, numberOfChildren = node.getChildNodes().getLength(); j< numberOfChildren; j++) {
                        Node element = node.getChildNodes().item(j);
                        if(element.getNodeType() == Node.ELEMENT_NODE) {
                            result += "\t\t\"" + element.getNodeName() + "\": \"" + element.getTextContent() + "\"";
                            if(j != numberOfChildren - 2) {
                                //System.out.println(", ");
                                result += ",\n";
                            }
                            else {
                                // System.out.print("\n\t}");
                                result += "\n\t}";
                            }
                        }
                    }
                    if(i != numberOfContacts - 1) {
                        result += ",\n";
                        // System.out.println(", ");
                    }
                }
            }
            result += "\n\t]\n}\n}";

            System.out.println("Do you want to (Please enter 1 or 2):\n1) save to a file or,\n2) display in the terminal?");
            int r = 0;
            do {
                r = Integer.parseInt(in.nextLine());
                if(r == 1) {
                    try {
                        String fileName = "results.json";
                        System.out.println("Saving results to a file...\nDone. Please check " + fileName);
                        FileWriter myWriter = new FileWriter(fileName);
                        myWriter.write(result);
                        myWriter.close();
                    } catch(Exception e) {
                        System.out.println("Please try a different way, or delete the file that you provided");
                    }
                }
                else if (r == 2) {
                    System.out.println("Showing results...");
                    System.out.println(result);
                }
                else {
                    System.out.println("Wrong option. Please try again. 1 or 2 only.");
                }
            } while(r != 1 && r != 2);
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("This was an error; Please try again with a different xml file path");
            in.close();
        }
        in.close();
    }
    
}
