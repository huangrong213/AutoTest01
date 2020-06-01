package com.util;


import java.io.*;
import java.util.Properties;

public class PropertiesHandle {


    public static Properties headerProperties() {
        String path = System.getProperty("user.dir");
        String headerPath = path + "\\config\\header.properties";
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(headerPath), "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;

    }

    public static void setToken(String token) {
        String path = System.getProperty("user.dir");
        String headerPath = path + "\\config\\header.properties";
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(headerPath), "GBK"));
            OutputStream fos = new FileOutputStream(headerPath);
            properties.setProperty("token", token);
            properties.store(fos, token);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
