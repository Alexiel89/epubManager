package it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class EPubBiblio {

    private List<EPub> ePubList;

    public EPubBiblio() {
        super();
    }

    public EPubBiblio(List<EPub> ePubList) {
        this.ePubList = ePubList;
    }

    public List<EPub> getePubList() {
        return ePubList;
    }

    public void setePubList(List<EPub> ePubList) {
        this.ePubList = ePubList;
    }

    public void setMetadata(int a, File tempFile) {

        File xFile = tempFile;
        DocumentBuilderFactory docBFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docBFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xFile);
            doc.getDocumentElement().normalize();

            String expression = "/package/metadata/*";
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList XMeta;
            try {
                XMeta = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < XMeta.getLength(); i++) {

                    if (XMeta.item(i).getNodeName().equals("dc:title") || XMeta.item(i).getNodeName().equals("ns1:title")) {
                        this.ePubList.get(a).setTitolo(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:identifier")|| XMeta.item(i).getNodeName().equals("ns1:identifier")) {
                        this.ePubList.get(a).setIsbn(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:language")|| XMeta.item(i).getNodeName().equals("ns1:language")) {
                        this.ePubList.get(a).setLingua(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:creator")|| XMeta.item(i).getNodeName().equals("ns1:creator")) {
                        this.ePubList.get(a).setAutore(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:coverage")|| XMeta.item(i).getNodeName().equals("ns1:coverage")) {
                        this.ePubList.get(a).setCapitoli(Integer.parseInt(XMeta.item(i).getTextContent()));
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:description")|| XMeta.item(i).getNodeName().equals("ns1:description")) {
                        this.ePubList.get(a).setDescrizione(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:date")|| XMeta.item(i).getNodeName().equals("ns1:date")) {
                        this.ePubList.get(a).setData(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:contributor")|| XMeta.item(i).getNodeName().equals("ns1:contributor")) {
                        this.ePubList.get(a).setCollaboratore(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:publisher")|| XMeta.item(i).getNodeName().equals("ns1:publisher")) {
                        this.ePubList.get(a).setEditore(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:format")|| XMeta.item(i).getNodeName().equals("ns1:format")) {
                        this.ePubList.get(a).setFormato(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:relation")|| XMeta.item(i).getNodeName().equals("ns1:title")) {
                        this.ePubList.get(a).setRelazione(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:type")|| XMeta.item(i).getNodeName().equals("ns1:type")) {
                        this.ePubList.get(a).setTipo(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:subject")|| XMeta.item(i).getNodeName().equals("ns1:subject")) {
                        this.ePubList.get(a).setSoggetto(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:source")|| XMeta.item(i).getNodeName().equals("ns1:source")) {
                        this.ePubList.get(a).setOrigine(XMeta.item(i).getTextContent());
                    }
                    if (XMeta.item(i).getNodeName().equals("dc:rights")|| XMeta.item(i).getNodeName().equals("ns1:rights")) {
                        this.ePubList.get(a).setDirittiAutore(XMeta.item(i).getTextContent());
                    }
                }
                }catch (XPathExpressionException e) {
            }

            } catch (ParserConfigurationException | SAXException | IOException e) {
            }

        }

}
