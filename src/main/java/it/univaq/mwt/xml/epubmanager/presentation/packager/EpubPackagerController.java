package it.univaq.mwt.xml.epubmanager.presentation.packager;

import it.univaq.mwt.xml.epubmanager.business.BusinessException;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/packager")
public class EpubPackagerController {

    private static final String TOKEN = "token";
    private static final String METADATA = "metadata";
    
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET})
    public String createMetadataStart() {

        return "metadata.createform";
    }
//
//    @RequestMapping(value = "/create", method = {RequestMethod.POST})
//    public String createMetadata(@ModelAttribute Metadata metadata, BindingResult bindingResult, HttpSession session) throws BusinessException {
//        metadataValidator.validate(metadata, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "metadata.createform";
//        }
//
//	int id = service.startEpub(metadata);
//        System.out.println("ID univoco per le altre chiamate: " +id);
//        // memorizzo in sessione l'id univoco che mi serve per le altre chiamate
//        session.setAttribute(TOKEN, id);
//        session.setAttribute("metadata", metadata);
//	return "redirect:/packager/uploadresources";
//    }
//    @RequestMapping(value="/uploadresources",method={RequestMethod.GET})
//    public String uploadResourcesStart(HttpSession session) {
//        Metadata metadata = (Metadata) session.getAttribute("metadata");
//
//        return "uploadresources.createform";
//    }    
//    
//    @RequestMapping(value="/uploadresources",method={RequestMethod.POST})
//    public String uploadResources(@RequestParam("xhtmlfiles") MultipartFile[] xhtmlfiles, 
//                                  @RequestParam("cssfiles") MultipartFile[] cssfiles,
//                                  @RequestParam("imagefiles") MultipartFile[] imagefiles,
//                                  HttpSession httpSession) throws BusinessException {
//        
//        // gestione upload dei file XHTML
//        for (MultipartFile xhtmlfile : xhtmlfiles) {
//            if (!xhtmlfile.isEmpty()) {
//                try {
//                    byte[] bytes = xhtmlfile.getBytes();
//                    EpubXhtml epubXhtml = new EpubXhtml(FilenameUtils.getBaseName(xhtmlfile.getOriginalFilename()), xhtmlfile.getOriginalFilename(), bytes);
//                    System.out.println(epubXhtml.toString());
//                    service.addXHTML(getToken(httpSession), epubXhtml);
//                } catch (IOException e) {
//                    throw new BusinessException("Errore I/O");
//                }
//            }
//        }
//        
//        // gestione upload dei file CSS
//        for (MultipartFile cssfile : cssfiles) {
//            if (!cssfile.isEmpty()) {
//                try {
//                    byte[] bytes = cssfile.getBytes();
//                    EpubCss epubCss = new EpubCss(FilenameUtils.getBaseName(cssfile.getOriginalFilename()), cssfile.getOriginalFilename(), bytes);
//                    System.out.println(epubCss.toString());
//                    service.addStylesheet(getToken(httpSession), epubCss);
//                } catch (IOException e) {
//                    throw new BusinessException("Errore I/O");
//                }
//            }
//        }
//        
//        // gestione upload delle immagini
//        for (MultipartFile imagefile : imagefiles) {
//            if (!imagefile.isEmpty()) {
//                try {
//                    byte[] bytes = imagefile.getBytes();
//                    EpubImage epubImage = new EpubImage(FilenameUtils.getBaseName(imagefile.getOriginalFilename()), imagefile.getOriginalFilename(), bytes);
//                    System.out.println(epubImage.toString());
//                    service.addImage(getToken(httpSession), epubImage);
//                } catch (IOException e) {
//                    throw new BusinessException("Errore I/O");
//                }
//            }
//        }
//        
//        return "uploadresources.createform";
//    }    
//    
//    private int getToken (HttpSession httpSession) {
//        return (Integer) httpSession.getAttribute(TOKEN);
//    }
////    @RequestMapping(value = "/results", method = {RequestMethod.GET})
////    public String result(HttpSession session) {
////        Metadata metadata = (Metadata) session.getAttribute("metadata");
////        System.out.println(metadata.getIsbn());
////        System.out.println(metadata.getTitle());
////        System.out.println(metadata.getLanguage());
////        return "uploadfiles.createform";
////    }
////
////    @RequestMapping("/endsession")
////    public String endSessionHandlingMethod(SessionStatus status) {
////        status.setComplete();
////        return "sessionsattributepage";
////    }
////
////    private Metadata getMetadataFromSession(HttpSession session) {
////        return (Metadata) session.getAttribute(METADATA);
////    }
}
