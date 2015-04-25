package it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EpubFile implements Serializable{
    String nomeEPubFile;
    List<File> listaF = new ArrayList<File>();

    public EpubFile() {
        super();
    }

    public EpubFile(String nomeEPubFile, List<File> listaF) {
        this.nomeEPubFile = nomeEPubFile;
        this.listaF= listaF;
    }

    public String getNomeEPubFile() {
        return nomeEPubFile;
    }

    public void setNomeEPubFile(String nomeEPubFIle) {
        this.nomeEPubFile = nomeEPubFIle;
    }

    

    public List<File> getListaF() {
        return listaF;
    }

    public void setListaF(List<File> listaF) {
        this.listaF = listaF;
    }
   
}
