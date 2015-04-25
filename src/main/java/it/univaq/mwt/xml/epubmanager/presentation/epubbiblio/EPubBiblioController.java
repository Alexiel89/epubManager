package it.univaq.mwt.xml.epubmanager.presentation.epubbiblio;

import it.univaq.mwt.xml.epubmanager.business.BusinessException;
import it.univaq.mwt.xml.epubmanager.business.RequestGrid;
import it.univaq.mwt.xml.epubmanager.business.ResponseGrid;
import it.univaq.mwt.xml.epubmanager.business.impl.ePubBiblio.EPubBiblioServiceImpl;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPub;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPubBiblio;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EPubBiblioBuilder;
import it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio.EpubFile;
import it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility.FileUtility;
import it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility.StatisticUtility;
import it.univaq.mwt.xml.epubmanager.common.ePubBiblioUtility.ZipUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import static java.security.AccessController.getContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;


@Controller
@RequestMapping("/ePubBiblio")
public class EPubBiblioController {

    @Autowired
    ServletContext servletContext;
    
    @Autowired
    private EPubBiblioServiceImpl service;
    
    @Value("#{cfgproperties.pathEPubBiblio}")
    private String pathEPubBiblio;
    
    /**
     * Gestione della tabella degli EPub
     */
    @RequestMapping(value="/findAllEpubsPaginated.do")
    public @ResponseBody ResponseGrid<EPub> findAllBrandsPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException, XPathExpressionException, FileNotFoundException {
        File biblioXml = FileUtility.findFile(pathEPubBiblio + "EPubBiblio.xml");
	ResponseGrid<EPub> responseGrid = service.findAllEpubsPaginated(requestGrid, biblioXml);
	return responseGrid;
    }

    /**
     * Gestione del download del singolo Epub
     */
    @RequestMapping(value="/downloadEpub.do")
    public void dowloadEpub(@RequestParam("id") Long id, HttpServletResponse response) throws BusinessException, XPathExpressionException, FileNotFoundException, IOException {
        File biblioXml = FileUtility.findFile(pathEPubBiblio + "EPubBiblio.xml");
        String downloadPath = service.getEpubInBiblioPath(id, biblioXml);
        File epubToDownlod = new File(downloadPath);
        InputStream input = new FileInputStream(epubToDownlod);
        response.setContentType("application/force-download");
	response.setHeader("Content-Disposition", "attachment; filename=" + epubToDownlod.getName());
        IOUtils.copy(input, response.getOutputStream());
        response.flushBuffer();
    }
    
    /**
     * Gestione dell'eliminazione dell'Epub dalla biblioteca
     */
    @RequestMapping(value="/deleteEpub.do")
    public String deleteEpub(@RequestParam("id") Long id) throws FileNotFoundException, XPathExpressionException, IOException, TransformerException, ParserConfigurationException, SAXException {
        //File biblioXml = FileUtility.findFile(pathEPubBiblio + "EPubBiblio.xml");
        String biblioXml = pathEPubBiblio;
        service.deleteEpubInBiblioFile(id, biblioXml);
        return "ePubBiblio.uploadEPub";
    }

    @RequestMapping(value="/upload-epub", method={RequestMethod.GET})
    public String createEpubManagerStart() {
        return "ePubBiblio.uploadEPub";
    }
    
    /**
     * Metodo che gestisce la trasformazione del file XML in file XSL
     */
    @RequestMapping(value="/epubBiblioXSLT.do")
    public @ResponseBody ModelAndView transformIntoXSLT(){
        String biblioXml = pathEPubBiblio + "EPubBiblio.xml";
        Source source = new StreamSource(new File(biblioXml));
        ModelAndView model = new ModelAndView("EpubXSLT");
        model.addObject("EpubBiblio", source);
        return model;
    }
    
    @RequestMapping(value = "/upload-epub", method = RequestMethod.POST)
    public String inserisciEPub(@RequestParam("eFile") CommonsMultipartFile[] ePubBiblioUploaded)throws BusinessException{
       
        int numUpload = ePubBiblioUploaded.length;
        System.out.println(numUpload);
        //Salva ePub nella cartella ePubBiblioUpload
        FileUtility.salvaFile(ePubBiblioUploaded, pathEPubBiblio);
        //Ora devo unzippare il file
        List<EpubFile> listaEPub;
        listaEPub = new ArrayList<EpubFile>();
        String dir;
        String dir2 = null;
        String path = null;

        int id = StatisticUtility.addID(pathEPubBiblio + File.separator + "EPubBiblio.xml");
        System.out.println(id);
       try {
            dir = pathEPubBiblio;
            System.out.println(dir);
            for (int i = 0; i < numUpload; i++) {
                EpubFile ePubF = new EpubFile();
                ePubF.setNomeEPubFile(ePubBiblioUploaded[i].getOriginalFilename());
                dir2 = pathEPubBiblio + "\\" + id + "\\";
                System.out.println(dir2);
                path = dir + File.separator + "ePubBiblioUploaded" + File.separator + ePubBiblioUploaded[i].getOriginalFilename();
                System.out.println(path);
                ePubF.setListaF(ZipUtil.unzipArchive(path, dir2));
                listaEPub.add(ePubF);
                id += 1;
            }

        } catch (IOException ioe) {
        }

        //Ora il file o i file ePub sono stati salvati ed unpizzati
        //Dobbiamo quindi andare a gestire la biblioteca creandola se non esiste ancora
        //oppure aggiornandola.

        String nomeBib = pathEPubBiblio + File.separator + "EPubBiblio.xml";
        File f = new File(nomeBib);
        String pathB= pathEPubBiblio;

       //Controllo se EPubBiblio.xml esiste o meno
        if (!f.exists()) {
            EPubBiblioBuilder.creaEPubBiblio(nomeBib,listaEPub,pathB);

        } else {
            //Se la biblioteca è stata già creata in precedenza aggiorniamo soltanto
            EPubBiblio ePubBiblio = StatisticUtility.analizza(listaEPub, pathEPubBiblio);
            EPubBiblioBuilder.aggiornaEPubBiblio(nomeBib, ePubBiblio.getePubList());
        }

        return "common.ok";
    }

}
