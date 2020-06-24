package core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class UrlClassloadTest {
    static String path="D:\\Java\\java_pro\\holmes\\holmes-alert\\target\\holmes-alert-1.0-SNAPSHOT.jar";
    static String classpath="co";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JarFile jarFile = new JarFile(new File(path));
        URL url = new URL("file:" + path);
        ClassLoader loader = new URLClassLoader(new URL[]{url});
        Class<?> aClass = loader.loadClass("com.shuzheng.holmes.alert.HolmesAlert.class");
    }
}
