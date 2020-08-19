package com.java.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ToJavaUtils {

    public static <T> T setFields(Object source, Class<T> cla) {
        if (source == null || cla == null)
            return null;
        String str = JSON.toJSONString(source);
        JSONObject json = JSONObject.parseObject(str);
        return JSON.toJavaObject(json, cla);
    }

    public static void copyFields(Object source, Object target) {
        Map<String, Object> sourceFields = getFieldInfo(source);
        Class cla = target.getClass();
        Field[] fields = cla.getDeclaredFields();
        if (ObjectUtils.isEmpty(sourceFields)) {
            return;
        }
        for (Field field : fields) {
            try {
                String name = field.getName();
                //源对象属性值为空 则返回继续下一条
                if (sourceFields.containsKey(field.getName()) && sourceFields.get(field.getName()) != null) {

                    field.setAccessible(true);
                    field.set(target, sourceFields.get(field.getName()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //查看其父类属性
                /*try {
                    Field superField = target.getClass().getSuperclass()
                            .getDeclaredField(sourceFieldMap.get("name").toString());
                    superField.setAccessible(true);
                    superField.set(target, sourceFieldMap.get("value"));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    private static Map<String, Object> getFieldInfo(Object o) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (o == null) {
            return null;
        }
        List<Field> fields = new ArrayList<>(Arrays.asList(o.getClass().getDeclaredFields()));
        //如果存在父类，获取父类的属性值，类型，名称并添加到一起
        Class sc = o.getClass().getSuperclass();
        if (sc != null) {
            fields.addAll(Arrays.asList(sc.getDeclaredFields()));
        }
        Map<String, Object> infoMap = new HashMap<>();
        for (Field field : fields) {
            infoMap.put(field.getName(), getFieldValueByName(field.getName(), o));
        }
        return infoMap;
    }
}
