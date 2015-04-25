package it.univaq.mwt.xml.epubmanager.common.utility;

import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EpubFile;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Zilfio
 */
public class DirectoryUtil {
    
    /**
     * Metodo che crea una directory
     * @param parentDirectory 
     * @param directoryName nome della directory
     * @return Ritorna true se la directory è stata creata, false altrimenti 
     */
    public static boolean mkdir(String parentDirectory, String directoryName){
        String fullDirectoryPath = parentDirectory + directoryName;
        File directory = new File(fullDirectoryPath);
        return directory.mkdir();
    }
}
