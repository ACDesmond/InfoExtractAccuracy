import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlRead {
    public static void main(String[] args) {
        xmlRead.xmlRead();
    }
    public static void xmlRead(){
        try {
            // 1. 创建DocumentBuilderFactory对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            // 2. 创建DocumentBuilder对象
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            // 3. 通过DocumentBuilder的parse方法解析xml
            org.w3c.dom.Document doc = dBuilder.parse("(2001)南民初字第148号民事判决书（2001）民判字第148号.RTF.xml");
            // 4. 根据根节点名称获取所有的nameCN节点
            org.w3c.dom.NodeList nameList = doc.getElementsByTagName("QW");
            // 5. 遍历所有的people节点
            for (int i = 0; i < nameList.getLength(); i++) {
//                System.out.println(nameList.item(i).getNodeName());

                Node Node = nameList.item(i);
                // A. 获取所有的属性名称 和 对应的属性值
                org.w3c.dom.NamedNodeMap namedNodeMap = Node.getAttributes();
                // B. 遍历people的所有属性
                String str="";
                for (int j = 0; j < namedNodeMap.getLength(); j++) {
                    if(namedNodeMap.item(j).getNodeName()=="value"){
                        str=namedNodeMap.item(j).getNodeValue();
                        break;
                    }
//                    System.out.println("属性 ="+namedNodeMap.item(j).getNodeName()+"   :值 = "+namedNodeMap.item(j).getNodeValue());
                }

//                NodeList nodeList = Node.getChildNodes();
//                for (int k = 0; k < nodeList.getLength(); k++) {
//                    Node node = nodeList.item(k);
//                    if (node.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println("节点名："+node.getNodeName()+ "    节点值："+node.getFirstChild().getNodeValue());
//                        //                      System.out.println("节点名："+node.getNodeName()+ "    节点值："+node.getTextContent());
//                    }
//                }
                System.out.println(str);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


