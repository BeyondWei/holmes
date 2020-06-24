package com.shuzheng.holmes.common.utils;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

public class ClassloadUtils extends ClassLoader {

    private static Logger logger = LoggerFactory.getLogger(ClassloadUtils.class);

    private ClassloadUtils() {

    }

    private static ClassloadUtils classloadUtils;

    public static synchronized ClassloadUtils getClassloadUtils() {
        if (classloadUtils == null) {
            classloadUtils = new ClassloadUtils();
        }
        return classloadUtils;
    }

    /**
     * 从jar中加载class至JVM，并返回class
     */
    public static Class<?> classLoadFromJar(String className, String jarPath) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //null
        }

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{new URL(jarPath)};
        } catch (MalformedURLException e) {
            logger.error("文件在：{} 地址中不存在;{}", jarPath, e.getMessage());
        }
        URLClassLoader loader = new URLClassLoader(urls);
        Class<?> aClass = null;
        try {
            aClass = loader.loadClass(className);
        } catch (ClassNotFoundException e) {
            logger.error("class{}在{}中不存在;{}", className, jarPath, e.getMessage());
        }
        return aClass;
    }

    /**
     * class加载至JVM，并返回class
     */
    public Class<?> classLoadFromClassFile(String className, String javaPath) {
        File classFile = new File(javaPath);
        if (!classFile.exists()) {
            logger.error("{}不存在", javaPath);
            return null;
        }
        Class<?> aClass = null;
        try {
            aClass = classLoadFromInput(className, new FileInputStream(classFile));
        } catch (FileNotFoundException e) {
            logger.error("{}解析失败", className);
            e.printStackTrace();
        }
        return aClass;
    }

    /**
     * 从流中获取class信息
     *
     * @param className
     * @param inputStream
     * @return
     */
    public synchronized Class<?> classLoadFromInput(String className, InputStream inputStream) {
        Class<?> aClass1 = tryGetClass(className);
        if (aClass1 != null) {
            return aClass1;
        }

        byte[] classBytes = new byte[0];
        try {
            classBytes = ByteStreams.toByteArray(inputStream);
        } catch (Exception e) {
            logger.error("{}解析失败", className);
            e.printStackTrace();
        }
        return super.defineClass(className, classBytes, 0, classBytes.length);
    }


    /**
     * 判断类是否已经加载
     */
    private static boolean isExist(String className) {
        try {
            Class<?> aClass1 = Class.forName(className, false, classloadUtils);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * 尝试获取类
     *
     * @param className
     * @return
     */
    public static Class<?> tryGetClass(String className) {
        if (isExist(className)) {
            try {
                return Class.forName(className, true, classloadUtils);
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        ClassloadUtils classloadUtils = ClassloadUtils.getClassloadUtils();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Class<?> aClass = classloadUtils.classLoadFromClassFile("com.beyond.run.Application", "C:\\Users\\WIN10\\Desktop\\Application.class");
            }).start();
        }
        isExist("com.beyond.run.Application");
    }

}
