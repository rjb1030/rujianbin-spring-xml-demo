package rujianbin.jkd.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Driver;
import sun.misc.Launcher;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Created by rujianbin@xinyunlian.com on 2017/9/21.
 * 由子ClassLoader加载的Class  可以依赖   由父ClassLoader加载的class。反过来就不行
 * 举例：
 *      java.sql.DriverManager，java.sql.Driver是核心类库的类，由BootstrapClassLoader加载
 *      而在DriverManager.getConnection方法中需要使用驱动com.mysql.jdbc.Driver 该驱动实现类是第三方组件，由AppClassLoader加载
 *      故正常情况下DriverManager.getConnection方法中是无法new出或依赖到Driver实现类的（因为BootstrapClassLoader只能加载核心类库的class文件）
 *      不过可以通过Class.forName并传入线程上下文类加载器（AppClassLoader），加载到Driver实现类
 *
 */
public class ClassLoader类加载器 {

    static{
        System.out.println("静态块输出");
    }


    public static void main(String[] args) throws Exception{
        System.out.println("");System.out.println("");System.out.println("");


        System.out.println("============================ BootstrapClassLoader 的加载路径 =======================");
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls){
            System.out.println(url);
        }


        System.out.println("============================ ExtClassLoader 加载路径 =======================");
        System.out.println(ClassLoader类加载器.class.getClassLoader().getParent());
        URLClassLoader extClassLoader  = (URLClassLoader)ClassLoader类加载器.class.getClassLoader().getParent();
        urls = extClassLoader.getURLs();
        for(URL url : urls){
            System.out.println(url);
        }


        System.out.println("============================ AppClassLoader 加载路径 =======================");
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println("当前线程ContextClassLoader="+Thread.currentThread().getContextClassLoader());
        URLClassLoader appClassLoader  = (URLClassLoader)ClassLoader.getSystemClassLoader();
        urls = appClassLoader.getURLs();
        for(URL url : urls){
            System.out.println(url);
        }


        System.out.println("========================  自定义ClassLoader。  ========================================");
        String classPath = "D:\\devDirectory\\workspace\\sp_idea_demo\\rujianbin-spring-xml-demo\\rujianbin-jdk-test\\other";
        String className = "rujianbin.jkd.test.Sample";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classPath);
        FileSystemClassLoader fscl2 = new FileSystemClassLoader(classPath);
        Class<?> class1 = fscl1.loadClass(className);
        Object obj1 = class1.newInstance();
        Class<?> class2 = fscl2.loadClass(className);
        Object obj2 = class2.newInstance();
        System.out.println("rujianbin.jkd.test.Sample 类加载器="+class1.getClassLoader());
        System.out.println("rujianbin.jkd.test.Sample 类加载器="+class2.getClassLoader());
        try {

            Method setSampleMethod = class1.getMethod("setSample", Object.class);
            setSampleMethod.invoke(obj1, obj2);
        } catch (InvocationTargetException e) {
            //所以类加载模式采用代理模式，确保核心的java类都是由BootstrapClassLoader加载的，以确保兼容
            System.out.println("Exception Error:相同的class文件被不同实例的classLoader加载，也不是同一个Class对象，不能类型强制转换。");
        }

        System.out.println("");System.out.println("");System.out.println("");
    }




    public static class FileSystemClassLoader extends ClassLoader {

        private String rootDir;

        public FileSystemClassLoader(String rootDir) {
            this.rootDir = rootDir;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData = getClassData(name);
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            else {
                return defineClass(name, classData, 0, classData.length);
            }
        }

        private byte[] getClassData(String className) {
            String path = classNameToPath(className);
            try {
                InputStream ins = new FileInputStream(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesNumRead = 0;
                while ((bytesNumRead = ins.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String classNameToPath(String className) {
            return rootDir + File.separatorChar
                    + className.replace('.', File.separatorChar) + ".class";
        }
    }
}
