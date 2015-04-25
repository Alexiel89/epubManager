package it.univaq.mwt.xml.epubmanager.business;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Zilfio
 */
public class MyErrorHandler implements ErrorHandler{

    private int nErrors, nWarnings, nFatals;

    public MyErrorHandler() {
        reset();
    }

    public void reset() {
        nErrors = 0;
        nWarnings = 0;
        nFatals = 0;
    }

    public boolean hasErrors() {
        return ((nErrors + nFatals) > 0);
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.err.println(
                "WARNING (" + exception.getSystemId() + ":"
                + exception.getLineNumber() + "," + exception.getColumnNumber() + ") "
                + exception.getMessage()
        );
        nWarnings++;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.err.println(
                "ERRORE (" + exception.getSystemId() + ":"
                + exception.getLineNumber() + "," + exception.getColumnNumber() + ") "
                + exception.getMessage()
        );
        nErrors++;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.err.println(
                "ERRORE FATALE (" + exception.getSystemId() + ":"
                + exception.getLineNumber() + "," + exception.getColumnNumber() + ") "
                + exception.getMessage()
        );
        nFatals++;
        throw exception;
    }
    
}
