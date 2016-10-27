package com.memory.codegen.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * nice ~
 * 
 * @author memoryaxis@gmail.com
 */
public class NiceUtil {

    public static final String SEP = System.getProperty("file.separator");

    private static long autoIncrease = 1;

    public static long getIncreaseLong() {
        return autoIncrease++;
    }

    public static void dissect(Object obj) {
        if (obj == null) {
            System.out.println("obj : null");
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(field.getName() + " : " + field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T map2obj(Map<String, Object> map, Class<T> clazz) throws Exception {
        if (map == null || clazz == null) {
            return null;
        }
        T t = clazz.newInstance();
        if (map.isEmpty()) {
            return t;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            String fieldName = descriptor.getName();
            if (!fieldName.equals("class") && map.containsKey(fieldName)) {
                Method setter = descriptor.getWriteMethod();
                setter.invoke(t, map.get(fieldName));
            }
        }
        return t;
    }

    public static Map<String, Object> obj2map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.trim().equals("");
    }

    public static String getRootPath() {
        return new File("").getAbsolutePath() + System.getProperty("file.separator");
    }

    public static String getResourcesPath(String resName) {
        return NiceUtil.class.getClassLoader().getResource(resName).getPath();
    }
}
