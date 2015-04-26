package it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility;

import it.univaq.mwt.xml.epubmanager.business.BusinessException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtility {

     /**
     * Metodo che prende in ingresso un file dato il suo path
     * @param fileDir
     * @return
     * @throws BusinessException
     */
    public static File findFile(String fileDir) throws BusinessException {
        File file = FileUtils.getFile(fileDir);
        if(!file.exists()){
            System.out.println("Il file non esiste!");
        } else {
            System.out.println("Ecco il file " + file.getName() + ": " + fileDir);
        }
        return file;
    }
    
    /**
     * Questo metodo serve per salvare l'epub uplodato sulla cartella con il path giusto
     * @param fileF
     * @param path 
     */
    public static void salvaFile(CommonsMultipartFile[] fileF, String path) {
        String current = null;
        CommonsMultipartFile[] f = fileF;
        int tot = f.length;
        if (path.equals("") || path == "" || path == null) {
            try {
                path = new java.io.File(".").getCanonicalPath();
                current = path;
            } catch (IOException e) {
            }
        } else {
            current = path;
            current += "\\" + "ePubBiblioUploaded";
            new File (current).mkdir();
        }
        
        for (int i = 0; i < tot; i++){
            byte dataToWrite[] = f[i].getBytes();
            try {
                BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream(new File(current + File.separator + f[i].getOriginalFilename())));
                stream.write(dataToWrite);
                stream.close();
            } catch (FileNotFoundException e) {
            }catch (IOException e) {
            }
        }
    }

}
