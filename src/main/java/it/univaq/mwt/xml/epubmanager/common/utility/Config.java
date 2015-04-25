package it.univaq.mwt.xml.epubmanager.common.utility;

import java.io.IOException;
import java.util.Properties;

public final class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            properties.load(loader.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    public static String getSetting(String key) {
        return properties.getProperty(key);
    }
}