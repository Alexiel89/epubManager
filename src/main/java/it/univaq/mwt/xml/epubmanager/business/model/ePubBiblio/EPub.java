package it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio;

public class EPub {
    
    private int id;
    private String titolo;
    private String lingua;
    private String isbn;
    private String autore;
    private String editore;
    private String formato;
    private String origine;
    private String tipo;
    private String data;
    private String collaboratore;
    private String relazione;
    private String dirittiAutore;
    private String destinazione;
    private String descrizione;
    private String soggetto;
    private int immagini;
    private int parole;
    private int capitoli;
    private String pathFile;
   
    public EPub() {
        super();
    }

    public EPub(int id, String titolo, String autore, String lingua, String editore) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.lingua = lingua;
        this.editore = editore;
    }
    
    public EPub(int id, String titolo, String lingua, String isbn, String autore, String editore, String formato, String origine, String tipo, String data, String collaboratore, String relazione, String dirittiAutore, String destinazione, String descrizione, String soggetto, int immagini, int parole, int capitoli, String pathFile) {
        this.id = id;
        this.titolo = titolo;
        this.lingua = lingua;
        this.isbn = isbn;
        this.autore = autore;
        this.editore = editore;
        this.formato = formato;
        this.origine = origine;
        this.tipo = tipo;
        this.data = data;
        this.collaboratore = collaboratore;
        this.relazione = relazione;
        this.dirittiAutore = dirittiAutore;
        this.destinazione = destinazione;
        this.descrizione = descrizione;
        this.soggetto = soggetto;
        this.immagini = immagini;
        this.parole = parole;
        this.capitoli = capitoli;
        this.pathFile = pathFile;
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(String collaboratore) {
        this.collaboratore = collaboratore;
    }

    public String getRelazione() {
        return relazione;
    }

    public void setRelazione(String relazione) {
        this.relazione = relazione;
    }

    public String getDirittiAutore() {
        return dirittiAutore;
    }

    public void setDirittiAutore(String dirittiAutore) {
        this.dirittiAutore = dirittiAutore;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(String soggetto) {
        this.soggetto = soggetto;
    }

    public int getImmagini() {
        return immagini;
    }

    public void setImmagini(int immagini) {
        this.immagini = immagini;
    }

    public int getParole() {
        return parole;
    }

    public void setParole(int parole) {
        this.parole = parole;
    }

    public int getCapitoli() {
        return capitoli;
    }

    public void setCapitoli(int capitoli) {
        this.capitoli = capitoli;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
   
}
