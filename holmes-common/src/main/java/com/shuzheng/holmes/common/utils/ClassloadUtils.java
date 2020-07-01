package com.shuzheng.holmes.common.utils;

import com.google.common.io.ByteStreams;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.avro.util.Utf8;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public Class<?> classLoadFromJar(String className, String jarPath) {
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
     * java加载至JVM，并返回class
     */
    public Class<?> classLoadFromJavaFile(String className, String javaPath, String classPath) {
        File classFile = new File(javaPath);
        if (!classFile.exists()) {
            logger.error("{}不存在", javaPath);
            return null;
        }
        String cmd = "javac -classpath " + classPath + " " + javaPath;
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            int status = exec.waitFor();
            if (status != 0) {
                logger.error("{}编译失败:{}", javaPath, new String(Byte2InputStream.inputStream2byte(exec.getErrorStream())));
            }
        } catch (IOException | InterruptedException e) {
            logger.error("{}编译失败", javaPath);
            e.printStackTrace();
        }


        File file = new File(classFile.getParent());
        File[] tempList = file.listFiles();

        String[] split = className.split("\\.");
        String realName = split[split.length - 1];
        Class<?> returnClass = classLoadFromClassFile(className, javaPath.substring(0, javaPath.length() - 4) + "class");
        // 加载内部类
        Arrays.asList(tempList).forEach(filePath -> {
            try {
                String name = filePath.getName().split("\\$")[1].split("\\.")[0];
                if (filePath.getName().contains(realName) && !name.equals(realName)) {
                    classLoadFromClassFile(className + "$" + name, filePath.toString());
                }
            } catch (Exception e) {

            }
        });
        return returnClass;
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
    private boolean isExist(String className) {
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
    public Class<?> tryGetClass(String className) {
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

}
