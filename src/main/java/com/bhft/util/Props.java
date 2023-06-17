package com.bhft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {
    private static final Properties PROPERTIES= new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(Props.class);

    static {
        try {
            ClassLoader classLoader = Props.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("application.properties");
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String property = System.getProperty(key);
        if (!(property == null)) {
            return property;
        } else {
            LOG.warn("The properties value is not set");
            throw new IllegalArgumentException();
        }
    }
}
