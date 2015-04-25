/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.mwt.xml.epubmanager.presentation.frontEnd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Zilfio
 */

@Controller
public class HomeController {
    
    @RequestMapping(value={"/", "/index"})
    public String homeview () {
        return "common.index";
    }
    
}
