package com.bluewind.boot.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射工具类
 *
 * @author liuxingyu01
 * @since 2023-07-26
 **/
public class ReflectUtils {

    /**
     * 创建实体
     *
     * @param classPath 类的路径
     * @return 对象示例
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object createInstance(String classPath) {
        Object o;
        try {
            Class<?> clazz = Class.forName(classPath);
            o = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("the class " + classPath + " can not be found");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("load class " + classPath + " fail");
        }
        return o;
    }

    /**
     * 创建类实例
     *
     * @param clazz 类对象
     * @return 对象示例
     */
    public static Object createInstance(Class<?> clazz) {
        Object o;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("load class " + clazz.getCanonicalName() + " fail");
        }
        return o;
    }

    /**
     * 获取Class类
     *
     * @param classPath 类的路径
     * @return
     */
    public static Class<?> getClass(String classPath) {
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("the class " + classPath + "can not be found");
        }
    }

    /**
     * 直接注入属性值
     *
     * @param o          对象
     * @param fieldName  对象属性名
     * @param fieldValue 对象属性值
     */
    public static void injectFieldValue(Object o, String fieldName, Object fieldValue) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, fieldValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("there are no field named " + fieldName + " in class " + o.getClass().getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("inject field value fail : " + fieldName);
        }
    }

    /**
     * 通过set注入属性值
     *
     * @param o          对象
     * @param fieldName  对象属性名
     * @param fieldValue 对象属性值
     */
    public static void setFieldValue(Object o, String fieldName, Object fieldValue) {
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equalsIgnoreCase("set" + capitalize(fieldName))) {
                try {
                    declaredMethod.invoke(o, fieldValue);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException("set field value fail : " + fieldName);
                }
            }
        }
    }

    /**
     * 字符串首字母转换为大写
     *
     * @param str 字符串
     * @return 转换好的字符串
     */
    private static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }


    /**
     * 校验改类上面有没有该注解或者子注解
     *
     * @param clazz           类对象
     * @param annotationClass 注解的类对象
     * @return true 有，false 无
     */
    public static boolean hasAnnotationOrSubAnnotation(Class<?> clazz, Class annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return true;
        }
        Annotation[] annotations = clazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].annotationType().isAnnotationPresent(annotationClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取clazz的接口集合
     *
     * @param clazz 类对象
     * @return 接口对象集合
     */
    public static List<Class> getInterfaces(Class clazz) {
        List<Class> interfaces = new ArrayList<>();
        Class[] interfaceArr = clazz.getInterfaces();
        if (interfaceArr != null && interfaceArr.length > 0) {
            interfaces = Arrays.asList(interfaceArr);
        }
        return interfaces;
    }

    /**
     * 获取子注解
     *
     * @param clazz           类对象
     * @param annotationClass 注解的类对象
     * @return 子注解
     */
    public static Annotation getSubAnnotation(Class clazz, Class annotationClass) {
        Annotation[] annotations = clazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].annotationType().isAnnotationPresent(annotationClass)) {
                return annotations[i];
            }
        }
        throw new RuntimeException("there are no subType matched the annotation " + annotationClass.getCanonicalName());
    }

}
