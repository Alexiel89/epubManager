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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class EPubBiblioServiceImpl implements EPubBiblioService{
    
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
     * Metodo che permette di eliminare un Epub dalla Biblio
     */
    @Override
    public void deleteEpubInBiblioFile(Long id, String biblio) throws FileNotFoundException, XPathExpressionException, IOException, TransformerException, ParserConfigurationException, SAXException{
        MyErrorHandler eh = new MyErrorHandler();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(eh);
        //uso il DOM per fare le operazioni di rimozione del documento XML
        String biblioXml = biblio + "EPubBiblio.xml";
	Document doc = db.parse(biblioXml);
        
        XPathFactory xf = XPathFactory.newInstance();
        XPath x = xf.newXPath();
        //NamespaceContextHandler nch = new NamespaceContextHandler();
        //nch.addBinding("n", "http://it.univaq.mwt.xml/epubmanager");
        //x.setNamespaceContext(nch);
            //cancello il file che si trova nella cartella dei file uploadati
            //InputSource file = new InputSource(new FileReader(f));
            Object filePath = x.evaluate("//EPub[@id="+ (id.intValue()) +"]/percorsofile/text()", doc, XPathConstants.STRING);
            String path = (String) filePath;
            String pathToDelete = path + ".epub";
            System.out.println("Il file da eliminare è:" + pathToDelete);
            File fileToDelete = new File(pathToDelete);
            fileToDelete.delete();
            System.out.println("Il file" + fileToDelete.getName() + "è stato eliminato con successo!");
            String dirFileToDelete = biblio + File.separator + id.toString();
            File dirToDelete = new File(dirFileToDelete);
            delete(dirToDelete);
            //Elimino l'epub dal file XML
            //file = new InputSource(new FileReader(f));
            //Object epubListElement = x.evaluate("//n:ePubList", doc, XPathConstants.NODE);
            //Node el = (Node) epubListElement;
            //file = new InputSource(new FileReader(doc));
            
            Object epub = x.evaluate("//EPub[@id="+ (id.intValue()) +"]", doc, XPathConstants.NODE);
            Element e = (Element) epub;         
            System.out.println("Eliminazione del nodo all'interno del file EpubBiblio.xml: " + e.getNodeName());
            e.getParentNode().removeChild(e);
            doc.normalize();
            doc.getDocumentElement().normalize();
            System.out.println("Nodo " + e.getNodeName() + " eliminato con successo!");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(biblioXml));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        transformer.transform(source, result);
    }
    
    void delete(File f) throws IOException {
	if (f.isDirectory()) {
            for (File c : f.listFiles())
	      delete(c);
	}
	if (!f.delete())
	    throw new FileNotFoundException("Failed to delete file: " + f);
    }
    
    /*
    @Override
    public void deleteEpubInBiblioFile(Long id, String path) throws TransformerException {*/
		/* Ci sono tre fasi:
		 * 1) Eliminare il file ePub da uploadedFile 
		 * 2) Eliminare i file dell'ePub scompattato nella cartella utente
		 * 3) Eliminare il tag Epub dal file XML
		 * */
		/*File fileToDelete = null;
		String pathFileToDelete = null;
		String expressionEpub = null;
		Node x = null;
		Node xFather = null;*/
		//Operare in questo modo:
		//ho solo l'id quindi dall'id devo ricavare il nome del file ePub
		/*DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String pathDirDel = path+ File.separator + id.toString();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);
			doc.getDocumentElement().normalize();
			XPath xPath =  XPathFactory.newInstance().newXPath();
			*/
			//String expressionEpub = "//ePub["+(id.intValue())+"]/percorsofile";
			/*expressionEpub = "//EPub[@id="+(id.intValue())+"]/percorsofile";
			System.out.println("======Eliminazione File Epub======");
			System.out.println("Espressione da validare = " + expressionEpub);
			x = (Node) xPath.compile(expressionEpub).evaluate(doc, XPathConstants.NODE);
			System.out.println("Contenuto = " + x.getTextContent());
			xFather = x.getParentNode();
			System.out.println("Padre è: " + xFather.getNodeName());
			pathFileToDelete = x.getTextContent();
			pathFileToDelete += ".epub";
			System.out.println("Eliminazione file : " + pathFileToDelete + " in corso...");
			fileToDelete = new File(pathFileToDelete);
			fileToDelete.delete();
			
			delete(new File(pathDirDel));
			System.out.println("Eliminazione Completata");
			System.out.println("======FINE======");
			System.out.println("======Inizio Eliminazione da file XML======");*/
			//NodeList prova = doc.getElementsByTagName("ePub");
			
			//Node XMeta = prova.item(id.intValue()-1);
			/*String expressionDeleteEpub = "//ePub[@id="+(id.intValue())+ "]";
			System.out.println("Espressione da validare " + expressionDeleteEpub);
			Node XMeta = (Node) xPath.compile(expressionDeleteEpub).evaluate(doc, XPathConstants.NODE); 
			System.out.println("Node XMETA selezionato = > " + XMeta.getNodeName());
			Node XMetaEpub = XMeta.getParentNode();
			System.out.println("Il parent node è = > " + XMetaEpub.getNodeName());
			XMetaEpub.removeChild(XMeta);
			doc.normalize();
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc); 
			StreamResult result = new StreamResult(new File(path)); 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.transform(source, result); 
			System.out.println("====FINE=====");*/
			
			//String expressionPercorsoFile = "/ePubBiblio/ePub["+id+ "]/percorsofile";
			
		//} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		/*}
	}
	void delete(File f) throws IOException {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
	}
        */

}
