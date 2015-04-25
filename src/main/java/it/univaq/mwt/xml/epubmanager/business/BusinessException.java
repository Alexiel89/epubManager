package it.univaq.mwt.xml.epubmanager.business;

public class BusinessException extends RuntimeException{
    
    
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
