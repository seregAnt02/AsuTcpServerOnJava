package duma.asu.presents;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;

public class DefaultParser {

    private File file;

    public DefaultParser(File file) {
        this.file = file;
    }


    public File getFile() {
        return file;
    }

    private void xml_parser(){
        try(FileInputStream fileIS = new FileInputStream(this.getFile())){
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();//newSecureDocumentBuilderFactory();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/Tutorials/Tutorial";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private DocumentBuilderFactory newSecureDocumentBuilderFactory(){
        return null;
    }
}
