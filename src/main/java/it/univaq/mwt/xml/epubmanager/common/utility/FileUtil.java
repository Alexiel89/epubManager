package it.univaq.mwt.xml.epubmanager.common.utility;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Zilfio
 */
public class FileUtil {
    
    /**
     * Metodo che salva una risorsa da URL a file
     * @param url indirizzo dove è situata la risorsa
     */
    public static void saveResourceURLToFile(URL url) {
        try {
            url = new URL("http://blog-assets.newrelic.com/wp-content/uploads/javalogo.png");
            FileUtils.copyURLToFile(url, new File("D:/" + url.getPath()));
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }
    }
    
}
