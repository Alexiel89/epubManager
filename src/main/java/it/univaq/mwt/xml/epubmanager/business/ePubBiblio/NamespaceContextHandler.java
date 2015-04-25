package it.univaq.mwt.xml.epubmanager.business.ePubBiblio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class NamespaceContextHandler implements NamespaceContext{
    
    Map<String, String> map = new HashMap();

    public void addBinding(String prefix, String uri) {
        map.put(prefix, uri);
    }

    public void addBindings(Map _map) {
        map.putAll(_map);
    }

    public boolean hasBindings() {
        return (map.size() > 0);
    }

    public String getNamespaceURI(String prefix) {

        if (prefix.equals("")) {
            return "";
        } else if (prefix.equals(XMLConstants.XML_NS_PREFIX)) {
            return XMLConstants.XML_NS_URI;
        } else if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
            return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
        } else {
            if (map.containsKey(prefix)) {
                return map.get(prefix);
            } else {
                return XMLConstants.NULL_NS_URI;
            }
        }
    }

    public String getPrefix(String namespaceURI) {
        return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
    
}
