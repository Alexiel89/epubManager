package it.univaq.mwt.xml.epubmanager.business.ePubBiblio;

import it.univaq.mwt.xml.epubmanager.business.BusinessException;
import it.univaq.mwt.xml.epubmanager.business.RequestGrid;
import it.univaq.mwt.xml.epubmanager.business.ResponseGrid;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPub;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public interface EPubBiblioService {
    
    ResponseGrid<EPub> findAllEpubsPaginated(RequestGrid requestGrid, File f) throws BusinessException;
    
    String getEpubInBiblioPath(Long id, File f) throws FileNotFoundException, XPathExpressionException;
    
    void deleteEpubInBiblioFile(Long id, String f) throws FileNotFoundException, XPathExpressionException, IOException, TransformerException, ParserConfigurationException, SAXException;
    
}
