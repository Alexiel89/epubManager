package it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility;

import it.univaq.mwt.xml.epubmanager.business.MyErrorHandler;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author Zilfio
 */
public class XMLUtil {
    
    public static Document createDocument() {
        // creo la factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // setX (che tipo di document builder ci deve produrre)
        
        dbf.setNamespaceAware(false);
        // se metti a true la valiazione il parser fa la validazione (prende la dtd associata e valida l'xml)
        dbf.setValidating(false);
        try {
            // dalla factory creo il DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.newDocument();
        } catch (ParserConfigurationException ex) {
            System.err.println("Impossibile creare il parser XML!");
        }
        
        return null;
    }
    
    /**
     * Metodo per salvare un documento
     * @param d documento da salvare
     * @param w writer generico
     */
    public static void saveDocument(Document d, Writer w){
        // prendo l'interfaccia dell'implementazione
        DOMImplementation i = d.getImplementation();
        // cast per prendere un'altra interfaccia
        DOMImplementationLS ils = (DOMImplementationLS)i;
        
        // LSOutput è lo stream di output che viene utilizzato per scrivere
        LSOutput lso = ils.createLSOutput();
        LSSerializer lss = ils.createLSSerializer();
        // configuro l'output
        lso.setCharacterStream(w);
        // configuro l'encoding
        // lso.setEncoding("ISO-8859-1");
        // setto il pretty-printing (indentazioni)
        lss.getDomConfig().setParameter("format-pretty-print", true);
        
        lss.write(d, lso);
    }
    
    /**
     * Metodo che permette di validare un file XHTML
     * @param file file da validare
     * @param bValidate booleano che indica se abilitare la validazione
     * @return ritorna false se non ho avuto errori, true altrimenti
     */
    public static boolean validateXhtml(File file, boolean bValidate){
        // error handler
        MyErrorHandler h = new MyErrorHandler();
        // creo la factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // setX (che tipo di document builder ci deve produrre)
        dbf.setNamespaceAware(false);
        // se metti a true la validazione il parser fa la validazione (prende la dtd associata e valida l'xml)
        dbf.setValidating(bValidate);
        try {
            // caricamento della DTD (features di xerces: http://xerces.apache.org/xerces-j/features.html)
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", bValidate);
            // dalla factory creo il DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // setto error handler nel DocumentBuilder
            db.setErrorHandler(h);
            db.parse(file);
        } catch (ParserConfigurationException ex) {
            System.err.println("Impossibile creare il parser XML!");
        } catch (SAXException ex) {
        // viene gestita dall'error handler
        } catch (IOException ex) {
            System.err.println("Errore I/O: "+ex.getMessage());
        }
        
        if(bValidate) {
            if(h.hasErrors()) {
                System.err.println("* Il documento NON è valido!");
            } else {
                System.err.println("* Il documento è valido!");
            }
        } else {
            if(h.hasErrors()) {
                System.err.println("* Il documento NON è ben formato!");
            } else {
                System.err.println("* Il documento è ben formato!");
            }
        }
        
        // ritorno lo stato di errore del mio handler
        return h.hasErrors();
    }
    
}
