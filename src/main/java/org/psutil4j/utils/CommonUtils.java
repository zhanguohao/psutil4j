package org.psutil4j.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author zhangguohao
 */
public class CommonUtils {

    public static final Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    public static Boolean isExistFieldName(String fieldName, Object obj) {
        if (obj == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean flag = false;
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static byte[] getFileContentWithByte(String filePath) {
        File file = new File(filePath);
        if (!file.getAbsoluteFile().exists()) {
            return new byte[0];
        }
        long fileSize = file.length();
        byte[] buffer = new byte[(int) fileSize];
        if (fileSize > Integer.MAX_VALUE) {
            return new byte[0];
        }
        try (FileInputStream fi = new FileInputStream(file)) {
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset != buffer.length) {
                return new byte[0];
            }
        } catch (Exception e) {
            return new byte[0];
        }
        return buffer;
    }

    public static List<String> getFileContentWithString(String filePath) {
        File file = new File(filePath);
        if (!file.getAbsoluteFile().exists()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String line;

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
            while ((line = fileReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Read a configuration file from the sequence of context classloader, system
     * classloader and classloader of the current class, and return its properties
     *
     * @param propsFilename The filename
     * @return Properties
     */
    public static Properties readPropertiesFromFilename(String propsFilename) {
        Properties archProps = new Properties();
        // Load the configuration file from the different classloaders
        if (!readPropertiesFromClassLoader(propsFilename, archProps, Thread.currentThread().getContextClassLoader()) && !readPropertiesFromClassLoader(propsFilename, archProps, ClassLoader.getSystemClassLoader())) {
            readPropertiesFromClassLoader(propsFilename, archProps, CommonUtils.class.getClassLoader());
        }
        return archProps;
    }

    private static boolean readPropertiesFromClassLoader(String propsFilename, Properties archProps,
                                                         ClassLoader loader) {
        if (loader == null) {
            return false;
        }
        // Load the configuration file from the classLoader
        try {
            List<URL> resources = Collections.list(loader.getResources(propsFilename));
            if (resources.isEmpty()) {
                return false;
            }
            if (resources.size() > 1) {
                return true;
            }
            try (InputStream in = resources.get(0).openStream()) {
                if (in != null) {
                    archProps.load(in);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        return INTEGER_PATTERN.matcher(str).matches();
    }

}
