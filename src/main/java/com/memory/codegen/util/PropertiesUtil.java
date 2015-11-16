package com.memory.codegen.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * load properties configuration file
 *
 * @author memoryaxis@gmail.com
 */
public class PropertiesUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/conf/config.properties");
            properties.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static Integer getIntegerValue(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static String getStringValue(String key) {
        try {
            return ((String) properties.getProperty(key)).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean getBooleanValue(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static Object getValue(String key) {
        return properties.getProperty(key);
    }
}
