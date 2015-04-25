package it.univaq.mwt.xml.epubmanager.business.model.ePubBiblio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Statistiche {
 
    private static Map<String, Integer> autMap = new HashMap<>();
/**
 * Per poter fare la statistica sugli autori della biblioteca
 * ovvero sapere quanti libri di uno stesso autore ci sono
 * creiamo una mappa con il nome autore e la frequenza.
 * Questo metodo dato come parametro la lista degli ePub appena uploadati 
 * dà come risultato 
 * @param listEPub
 * @return 
 */
    public static Map<String, Map<String, Integer>> mappaStatistiche(List<EPub> listEPub) {
        for (Iterator iterator = listEPub.iterator(); iterator.hasNext();) {
            EPub ePub = (EPub) iterator.next();
            String autore = ePub.getAutore();
            //Se non c'è un autore e quindi il campo è a null mettiamo frequenza 1 altrimenti inseriamo l'autore
            if (autMap.get(autore) == null) {
                autMap.put(autore, new Integer(1));
            } else {
                Integer x = autMap.get(autore);
                autMap.put(autore, x+1);
            }
        }
        
        Map<String, Map<String, Integer>> statistiche = new HashMap<>();
        statistiche.put("autori", autMap);
        return statistiche;

    }
}
