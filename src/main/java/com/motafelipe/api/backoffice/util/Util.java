package com.motafelipe.api.backoffice.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.ResourceBundle;

public class Util {

    @Value("resources.path.bundle")
    private static String bundlePath;

    private static final ResourceBundle bundle =
            ResourceBundle.getBundle(bundlePath);

    public static String getMessage(String message) {

        if (bundle.containsKey(message))
            return bundle.getString(message);

        throw new RuntimeException("Bundle not founded.");
    }

    public static String getMessage(String message, Object param) {

        if (bundle.containsKey(message))
            return bundle.getString(message) + param;

        throw new RuntimeException("Bundle not founded.");
    }
}
