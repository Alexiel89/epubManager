package it.univaq.mwt.xml.epubmanager.common.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Zilfio
 */
public class GeneralUtility {

    private static final AtomicInteger ai;

    static {
        ai = new AtomicInteger();
    }
    
    public static int generateInt() {
        return ai.incrementAndGet();
    }

}
