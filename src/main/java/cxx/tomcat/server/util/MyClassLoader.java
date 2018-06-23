package cxx.tomcat.server.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;


/**
 * @Author: cxx
 * @Date: 2018/6/23 17:17
 * @Describtion: 自定义的类加载器
 */
public class MyClassLoader extends ClassLoader{
    protected Class<?> findClass(String name) {
        String path = System.getProperty("user.dir")+File.separator+"webapps"+File.separator+"WEB-INF"+File.separator+"classes";
        File classpath = new File(path);
        System.out.println(path);
        URL[] urls = new URL[1];
        URLClassLoader loader = null;
        try {
            String repository =(new URL("file", null, classpath.getCanonicalPath() + File.separator)).toString() ;
            System.out.println(repository);
            URLStreamHandler streamHandler = null;
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
            Class clazz = null;
            try {
                clazz = loader.loadClass(name);
                return clazz;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.loadClass("cxx.test.LoginServlet");
        try {
            Object obj = aClass.newInstance();
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                System.out.println(method);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
