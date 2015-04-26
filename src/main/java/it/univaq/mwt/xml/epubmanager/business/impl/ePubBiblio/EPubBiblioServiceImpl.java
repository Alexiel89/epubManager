package it.univaq.mwt.xml.epubmanager.business.impl.ePubBiblio;

import it.univaq.mwt.xml.epubmanager.business.BusinessException;
import it.univaq.mwt.xml.epubmanager.business.MyErrorHandler;
import it.univaq.mwt.xml.epubmanager.business.RequestGrid;
import it.univaq.mwt.xml.epubmanager.business.ResponseGrid;
import it.univaq.mwt.xml.epubmanager.business.ePubBiblio.EPubBiblioService;
import it.univaq.mwt.xml.epubmanager.business.ePubBiblio.NamespaceContextHandler;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPub;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class EPubBiblioServiceImpl implements EPubBiblioService{
    
    /**
     * Metodo che permette di gestire la tabella con la lista degli Epub
     */
    @Override
    public ResponseGrid<EPub> findAllEpubsPaginated(RequestGrid requestGrid, File f) throws BusinessException {
        List<EPub> epubList = new ArrayList<EPub>();
       
        XPathFactory xf = XPathFactory.newInstance();
        XPath x = xf.newXPath();
        NamespaceContextHandler nch = new NamespaceContextHandler();
        nch.addBinding("n", "http://it.univaq.mwt.xml/epubmanager");
        x.setNamespaceContext(nch);
        //carico il file
        InputSource file;
        try {
            file = new InputSource(new FileReader(f));
        if(file == null){
            System.err.println("File inesistente!");
        } else {
            try {
                Object children = x.evaluate("//n:ePubList/*", file, XPathConstants.NODESET);
                NodeList epubs = (NodeList) children;
                for(int j=0; j < epubs.getLength(); j++){
                Node epub = epubs.item(j);
                //Mi prendo i valori che mi interessano per ogni nodo <epub>
                Object idEpub = x.evaluate("@id", epub, XPathConstants.STRING);
                String iid = (String) idEpub;
                int id = Integer.parseInt(iid);
                System.out.println("ID: " + id);
                Object t = x.evaluate("n:titolo/text()", epub, XPathConstants.STRING);
                String titolo = (String) t;
                System.out.println("TITOLO: " + t);
                Object c = x.evaluate("n:autore/text()", epub, XPathConstants.STRING);
                String autore = (String) c;
                System.out.println("AUTORE: " + c);
                Object l = x.evaluate("n:lingua/text()", epub, XPathConstants.STRING);
                String lingua = (String) l;
                System.out.println("LINGUA: " + l);
                Object p = x.evaluate("n:editore/text()", epub, XPathConstants.STRING);
                String editore = (String) p;
                System.out.println("EDITORE: " + p);
                EPub e = new EPub(id, titolo, autore, lingua, editore);
                epubList.add(e);
                System.out.println("Attributi Epub: " + e.getId() + "," +e.getTitolo() + "," + e.getAutore() + ","
                  + e.getLingua() + "," + e.getEditore());
                //PER GESTIRE LA TABELLA:
                if(requestGrid.getSortCol().equals(iid)){
                     requestGrid.setSortCol(iid);
                        if(e.getTitolo().equals(requestGrid.getSortCol())) {
                            requestGrid.setSortCol(e.getTitolo());
                                if(e.getAutore().equals(requestGrid.getSortCol())) {
                                    requestGrid.setSortCol(e.getAutore());
                                        if(e.getLingua().equals(requestGrid.getSortCol())) {
                                            requestGrid.setSortCol(e.getEditore());
                                        }
                                }
                            }else{
                         requestGrid.setSortCol(e.getEditore());  
                      }
                    }
                }
            } catch (XPathExpressionException ex) {
                System.err.println("Errore XPath: " + ex.getMessage());
            } 
          }
        }catch (FileNotFoundException ex) {
            System.err.println("File non trovato: " + ex.getMessage());
        }
        long records = epubList.size();
        
        return new ResponseGrid<EPub>(requestGrid.getsEcho(), records, records, epubList);
    }
    
    /**
     * Metodo che permette di fare il download del file
     */
    @Override
    public String getEpubInBiblioPath(Long id, File f) throws FileNotFoundException, XPathExpressionException{
        XPathFactory xf = XPathFactory.newInstance();
        XPath x = xf.newXPath();
        NamespaceContextHandler nch = new NamespaceContextHandler();
        nch.addBinding("n", "http://it.univaq.mwt.xml/epubmanager");
        x.setNamespaceContext(nch);
        
        InputSource file = new InputSource(new FileReader(f));
        Object path = x.evaluate("//n:EPub[@id="+ (id.intValue()) +"]/n:percorsofile/text()", file, XPathConstants.STRING);
        String percorsoFile = (String) path;
        String filePath = percorsoFile + ".epub";
        System.out.println("Il percorso del file da scaricare è: " + percorsoFile);

        return filePath;
    }
    
    /**
     * Metodo che permette di eliminare un Epub selezionato tramite un pulsante
     * visualizzato dalla tabella, che si trova nella Biblio
     */
    @Override
    public void deleteEpubInBiblioFile(Long id, String biblio) throws FileNotFoundException, XPathExpressionException, IOException, ParserConfigurationException, SAXException{
        MyErrorHandler eh = new MyErrorHandler();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(eh);
        //uso il DOM per fare le operazioni di rimozione del nodo nel documento XML
        String biblioXml = biblio + "EPubBiblio.xml";
	Document doc = db.parse(biblioXml);
        
        XPathFactory xf = XPathFactory.newInstance();
        XPath x = xf.newXPath();
            //cancello il file che si trova nella cartella dei file uploadati
            Object filePath = x.evaluate("//EPub[@id="+ (id.intValue()) +"]/percorsofile/text()", doc, XPathConstants.STRING);
            String path = (String) filePath;
            String pathToDelete = path + ".epub";
            System.out.println("Il file da eliminare è: " + pathToDelete);
            File fileToDelete = new File(pathToDelete);
            if(fileToDelete.exists()){
                fileToDelete.delete();
            }
            System.out.println("Il file " + fileToDelete.getName() + "è stato eliminato con successo!");
            //cancello la cartella dove si trovano i file dell'epub scompattato
            String dirFileToDelete = biblio + id.toString();
            File dirToDelete = new File(dirFileToDelete);
            EPubBiblioServiceImpl.delete(dirToDelete);
            System.out.println("La cartella " + dirToDelete + "è stata eliminata con successo!");
            //cancello l'elemento Epub che si trova all'interno del file XML
            Object epub = x.evaluate("//EPub[@id="+ (id.intValue()) +"]", doc, XPathConstants.NODE);
            Node e = (Node) epub; 
            System.out.println("Eliminazione del nodo all'interno del file EpubBiblio.xml: " + e.getNodeName());
            e.getParentNode().removeChild(e);
            System.out.println("Nodo " + e.getNodeName() + id.toString() + " eliminato con successo!");
            //aggiorno le statistiche relative agli Epub
            Object children = x.evaluate("//n:ePubList/*", doc, XPathConstants.NODESET);
            NodeList epubs = (NodeList) children;
            EPubBiblioServiceImpl.aggiornaStatistiche(doc, epubs, biblio);
            System.out.println("Le statistiche sono state aggiornate con successo!");
            doc.getDocumentElement().normalize();
        try {
            //aggiorno il file XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(biblioXml));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            ex.getMessage();
        } catch (TransformerException ex) {
            ex.getMessage();
        }
        
    }
    
    /* 
    * senza questo metodo la cartella non viene eliminata perché
    * contiene vari file ovviamente, quindi il semplice metodo delete
    * può essere applicato solo al singolo file!
    */
    private static void delete(File dir) throws IOException {
        if(dir.isDirectory()){
            for(File file : dir.listFiles())
                delete(file);
            }
        if(!dir.delete())
            throw new FileNotFoundException("Il file" + dir + "non è stato cancellato!");
    }
    
    /* 
    * Metodo che permette di aggiornare le statistiche durante l'eliminazione dell'Epub
    * oppure di eliminare il file Epub nel caso in cui viene svuotata la biblioteca
    */
    private static void aggiornaStatistiche(Document doc, NodeList epubs, String biblio) throws IOException {
        XPathFactory xf = XPathFactory.newInstance();
        XPath x = xf.newXPath();
            for(int j=0; j < epubs.getLength(); j++){
        try {
            Element epub = (Element) epubs.item(j);
            Object aus = x.evaluate("//lista-statistiche/StatisticheAutori/author[nome=\"" + epub.getElementsByTagName("autore") + "\"]", doc, XPathConstants.NODESET);
            NodeList authors = (NodeList) aus;
            NodeList authorsEpubs = doc.getElementsByTagName("author");
            //se c'è l'elemento author
            if(authors.getLength()!=0){
                Element auth = (Element) authors.item(0);
                System.out.println("L'autore è: " + auth);
                String nome = auth.getElementsByTagName("nome").item(0).getTextContent();
                String frequenza = auth.getElementsByTagName("frequenza").item(0).getTextContent();
                
                System.out.println("Il nome dell'autore è: " + nome);
                System.out.println("La frequenza dell'autore è: " + frequenza);
                
                boolean trovato = false;
                for (int k= 0; k < authorsEpubs.getLength() - 1 || !trovato; k++) {
                    Element author = (Element) authorsEpubs.item(k);
                    String nuovoNomeAutore = author.getElementsByTagName("nome").item(0).getTextContent();
                    System.out.println(nuovoNomeAutore);
                    //se autore e author combaciano
                    if (nuovoNomeAutore == nome) {
                        if (!author.getElementsByTagName("frequenza").item(0).getTextContent().equals(null)) {
                            int y = Integer.parseInt(author.getElementsByTagName("frequenza").item(0).getTextContent());
                            y--;
                            author.getElementsByTagName("frequenza").item(0).setTextContent(Integer.toString(y));
                            trovato = true;
                        }
                    }
                }
            }else {
                //se la frequenza dell'autore è pari a 0
                String biblioXml = biblio + "EPubBiblio.xml";
                File fileBiblio = new File(biblioXml);
                if(fileBiblio.exists()){
                    fileBiblio.delete();
                }
                String epubUploadedDir = biblio + "ePubBiblioUploaded";
                File dirUploadedFiles = new File(epubUploadedDir);
                EPubBiblioServiceImpl.delete(dirUploadedFiles);
             }
        } catch (XPathExpressionException ex) {
            ex.getMessage();
        }
      }
    }
    
}
    

