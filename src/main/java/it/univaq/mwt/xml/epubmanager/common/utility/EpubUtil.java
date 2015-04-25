package it.univaq.mwt.xml.epubmanager.common.utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Zilfio
 */
public class EpubUtil {
    
    /**
     * Metodo che crea il file mimetype situato alla root
     * @param path percorso dove salvare il file
     */
    public static void createMimetypeFile(String path) {
        try {
            FileUtils.writeStringToFile(new File(path + "mimetype"), Config.getSetting("mimetype"));
        } catch (IOException ex) {
            Logger.getLogger(EpubUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo che crea la struttura del file container.xml
     * @return Document che rappresenta il documento
     */
    public static Document createContainerXMLFile() {
        Document d = XMLUtil.createDocument();
        if (d != null) {
            Element c = d.createElement("container");
            c.setAttribute("version", "1.0");
            c.setAttribute("xmlns", "urn:oasis:names:tc:opendocument:xmlns:container");
            Element r = d.createElement("rootfiles");
            Element r1 = d.createElement("rootfile");
            r1.setAttribute("full-path", "content.opf");
            r1.setAttribute("media-type", "application/oebps-package+xml");
            d.appendChild(c);
            c.appendChild(r);
            r.appendChild(r1);
        }
        return d;
    }
    
}
