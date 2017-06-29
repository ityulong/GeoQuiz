package javaTest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by chenyulong on 2017/6/29.
 */

public class ParseXml {
    private final static String chinesePath = "/Users/chenyulong/Downloads/chines.txt";
    private final static String englishPath = "/Users/chenyulong/Downloads/english.txt";
    private final static String NODE_NAME = "string";
    private final static String NODE_ATTRIBUTE = "name";


    public static void main(String[] args) {
        File fileChinese = new File(chinesePath);
        File fileEnglish = new File(englishPath);

        Map<String, String> cMap = readFiles(fileChinese);
        Map<String, String> eMap = readFiles(fileEnglish);
        for (Map.Entry<String, String> entry : cMap.entrySet()) {
            System.out.println(entry.getKey());
        }

        System.out.println("///////////////////////////////////////////////////////////////");

        for (Map.Entry<String, String> entry : cMap.entrySet()) {
            System.out.println(entry.getValue());
        }

        System.out.println("///////////////////////////////////////////////////////////////");

        for (Map.Entry<String, String> entry : cMap.entrySet()) {
            System.out.println(eMap.get(entry.getKey()));
        }
    }

    public static Map<String,String> readFiles(File file){
        Map<String, String> result = new LinkedHashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList stringList = doc.getElementsByTagName(NODE_NAME);
            for (int i = 0; i <stringList.getLength() ; i++) {
                Node node = stringList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element str = (Element) node;
                    String key = str.getAttribute(NODE_ATTRIBUTE);
                    String value = str.getTextContent();
                    result.put(key, value);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
