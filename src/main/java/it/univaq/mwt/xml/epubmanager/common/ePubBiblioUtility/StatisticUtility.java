package it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility;

import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPub;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPubBiblio;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EpubFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StatisticUtility {

    static int totParole;
    static int totImg;
    static int totCapitoli;

    /**
     * Questo metodo serve per assegnare l'ID di valore i all'i-esimo ePub
     * uplodato
     *
     * @param url
     * @return
     */
    public static int addID(String url) {

        File f = new File(url);
        int id = 0;

        if (f.exists()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docB;
            String exprEPub = null;
            String idT;
            try {
                docB = factory.newDocumentBuilder();
                Document doc = docB.parse(url);
                doc.getDocumentElement().normalize();
                //buildBiblio--> writer.writeStartElement("ePubBiblio"); creaEPub--> writer.writeStartElement("EPub");
                exprEPub = "//ePubList/EPub[position() = last ()]/@id";

                XPath xPath = XPathFactory.newInstance().newXPath();
                Node x = (Node) xPath.compile(exprEPub).evaluate(doc, XPathConstants.NODE);
                idT = x.getTextContent();
                id = Integer.parseInt(idT);
                id += 1;
            } catch (SAXException | IOException | XPathExpressionException | ParserConfigurationException ex) {
                Logger.getLogger(StatisticUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            id = 1;
        }
        return id;
    }

    public static int contaImmagini(File f) {

        int risultato = 0;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docB;

        try {
            docB = factory.newDocumentBuilder();
            Document doc = docB.parse(new File(f.getPath()));
            //se ePab ver3?
            NodeList nodeL = doc.getElementsByTagName("ns0:item");
            if (nodeL.getLength() == 0) //se ePab ver2?
            {
                nodeL = doc.getElementsByTagName("item");
            }

            for (int i = 0; i < nodeL.getLength(); i++) {
                Element element = (Element) nodeL.item(i);
                String attribute = element.getAttribute("media-type");
                if (attribute.contains("image")) {
                    risultato++;
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {

            e.printStackTrace();
        }
        return risultato;

    }

    public static int contaCapitoli(File f) {

        int risultato = 0;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docB;

        try {
            docB = factory.newDocumentBuilder();
            Document doc = docB.parse(new File(f.getPath()));
            NodeList nodeL = doc.getElementsByTagName("ns0:item");
            if (nodeL.getLength() == 0) {
                nodeL = doc.getElementsByTagName("item");
            }
            for (int i = 0; i < nodeL.getLength(); i++) {
                Element element = (Element) nodeL.item(i);
                String attribute = element.getAttribute("media-type");
                if (attribute.contains("xhtml")) {
                    risultato++;
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {

            e.printStackTrace();
        }
        return risultato;

    }
    /**
     * 
     * @param listaEPub
     * @param pathEPubBiblio
     * @return 
     */
    public static EPubBiblio analizza(List<EpubFile> listaEPub, String pathEPubBiblio) {
      
        Iterator<EpubFile> i = listaEPub.iterator();
        
        EPubBiblio ePubBiblio = new EPubBiblio();
        List<EPub> ePubs = new ArrayList<EPub>();
        int x = addID(pathEPubBiblio + File.separator + "EPubBiblio.xml");
        int conta;
        String pathFile;
        String pathEPub;
        //Nel ciclo per ogni EPubFile creo un EPub che inizio a popolare
        while (i.hasNext()) {
            EpubFile temp = i.next();
            EPub ePub = new EPub();
            //setto l'attributo id di EPub
            ePub.setId(x);
            x++;
            //conto le parole presenti nei capitoli (ovvero nei vari file .xhtml per ePub 3.0 e .html per ePub 2.0
            //poi setto l'attributo Parole di EPub
            conta = contaParoleCapitoli(temp.getListaF());
            pathEPub = temp.getNomeEPubFile();
            ePub.setParole(conta);         
            
            //Qui cerco il path dove è salvato lo zip dell'ePub e salvo il percorso nell'attributo PathFile di EPub
            int lessT = pathEPub.lastIndexOf(".");
            String titoloLibro = pathEPub.substring(0, lessT);
            pathFile = pathEPubBiblio;
            pathEPubBiblio += File.separator + "ePubBiblioUploaded" + File.separator + titoloLibro;
            ePub.setPathFile(pathEPubBiblio);
            //Resetto pathEPubBiblio per il prossimo file altrimenti nel path ci sarebbero tutti i path della lista di file.
            pathEPubBiblio = pathFile;
            
            ePubs.add(ePub);
        }
        //Aggiungo ad ePubBiblio la lista di ePub parzialmente popolati (ovvero hanno ID, PathName e Parole
        ePubBiblio.setePubList(ePubs);
        //Ciclo per il numero di EPubfile presenti in listaEPub
        for (int j = 0; j < listaEPub.size(); j++) {
            // devo estrarre il j-esimo epub da entrambe le stutture dati
            //Cre un ePunFile e gli assegno il j-esimo ePub presente nella lista 
            EpubFile ePubFile = listaEPub.get(j);
            //devo trovare content.opf che serve per poter contare immagini e capitoli quindi scorro tutti i file
            for (int a = 0; a < ePubFile.getListaF().size(); a++) {

                File fileT = ePubFile.getListaF().get(a);
                if (fileT.getName().endsWith(".opf") || fileT.getName().contains(".opf")) {
                    int contaImg;
                    int contaCapitoli;
                    //Richiamo i metodi per contare 
                    //I metodi sono stati ottimizzati per contare immagini e capitoli sia di ePub 2.0 che di ePub 3.0
                    contaImg = contaImmagini(fileT);
                    contaCapitoli = contaCapitoli(fileT);
                    ePubBiblio.getePubList().get(j).setImmagini(contaImg);
                    ePubBiblio.getePubList().get(j).setCapitoli(contaCapitoli);

                    ePubBiblio.setMetadata(j, fileT);
                }
            }
        }
        return ePubBiblio;
    }

    /**
     * contale parole nei capitoli
     *
     * @return
     */
    public static int contaParoleCapitoli(List<File> lista) {

        List<File> lFile = lista;
        int ris = 0;
        DocumentBuilderFactory docF = DocumentBuilderFactory.newInstance();
        docF.setNamespaceAware(false);
        docF.setValidating(false);
        DocumentBuilder docB = null;
        NodeList body = null;
        try {
            docF.setFeature("http://xml.org/sax/features/namespaces", false);
            docF.setFeature("http://xml.org/sax/features/validation", false);
            docF.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            docF.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            docB = docF.newDocumentBuilder();

            for (Iterator iterator = lFile.iterator(); iterator.hasNext();) {
                File newFile = (File) iterator.next();
                File newnewFile = new File(newFile.getPath());
                if (newnewFile.getCanonicalFile().toString().contains(".xhtml") || newnewFile.getCanonicalFile().toString().contains(".html")) {
                    Document doc = docB.parse(newnewFile);
                    body = doc.getElementsByTagName("body");
                    //Può avere al massimo 1 body
                    Node x = body.item(0);
                    //Se non c'è il body non ci sono parole da contare
                    if (x != null) {
                        String testo = x.getTextContent();
                        testo = testo.replaceAll("\\W", ",");
                        testo = testo.replaceAll("[,]+", ",");
                        testo = testo.replaceAll("\\w", "");
                        ris = ris + testo.length();
                } 
              }
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return ris;
    }
}
