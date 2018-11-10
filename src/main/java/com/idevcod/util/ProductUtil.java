package com.idevcod.util;

import java.util.ResourceBundle;

public final class ProductUtil {
    private static final Object LOCK = new Object();

    private static volatile ProductUtil instance;

    private ResourceBundle bundle;

    private ProductUtil() {
        bundle = ResourceBundle.getBundle("product");
    }

    public static ProductUtil getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ProductUtil();
                }
            }
        }

        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return value;
        }

        return defaultValue;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }

}
