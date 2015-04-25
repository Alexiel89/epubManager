package it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility;

import it.univaq.mwt.xml.epubmanager.business.MyErrorHandler;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author Alessandra
 */
public class XMLUtilBiblio {
    
    /**
     * Metodo che valida un documento XML dato il suo Schema
     * @param docxml
     * @param docxsd
     * @return
     */
    public static boolean validateXMLDocument(File docxml, File docxsd){
        MyErrorHandler h = new MyErrorHandler();
        try{
            //creo un'istanza di SchemaFactory passandogli in ingresso lo schema per il quale si vuole il supporto
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            sf.setErrorHandler(h);
            //l'oggetto Schema consiste nella rappresentazione in memoria del documento XML di grammatica
            Schema s = sf.newSchema(docxsd);
            //faccio generare un validatore dallo schema
            Validator v = s.newValidator();
            v.setErrorHandler(h);
            //chiamo il metodo validate passando il documento da validare
            v.validate(new StreamSource(docxml)/*,new StreamResult(new PrintWriter(System.out))*/);
        }catch(IOException ioe){
            System.err.println("Errore di I/O: " + ioe.getMessage());
        }catch(SAXException sxe){
            //stampa l'errore gestito dall'handler
        }
        
         if (h.hasErrors()) {
            System.out.println("* Il documento NON è valido");
        } else {
            System.out.println("* Il documento è valido");
        }
         
        return h.hasErrors();
    }
    
}
