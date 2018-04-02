package com.hyy.utils;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 功能描述：所有的反射功能
 * <p>
 * Dec 2, 2010  11:23:28 AM
 */
public class ClassUtil {
    private static ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();//Spring封装的方法，可以获得方法的参数名称

    /**
     * 获取实体的类
     *
     * @param obj
     * @return
     */
    public static Class<? extends Object> getClassez(Object obj) {
        return obj.getClass();
    }

    /**
     * 获取字段值
     *
     * @param obj       实体
     * @param fieldName 字段名
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */

    public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return getValue(obj, getField(obj, fieldName));
    }

    /**
     * 获取字段的值
     *
     * @param obj
     * @param field
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValue(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        return field.get(obj);
    }

    /**
     * 设置字段可见
     *
     * @param field
     */
    public static void setAccessible(Field field) {
        setAccessible(new Field[]{field});
    }

    /**
     * 设置字段可见
     *
     * @param field
     */
    public static void setAccessible(Field field, boolean isVisit) {
        setAccessible(new Field[]{field}, isVisit);
    }

    /**
     * 设置字段可见
     *
     * @param fields
     */
    public static void setAccessible(Field[] fields) {
        setAccessible(fields, true);
    }

    /**
     * 设置字段可见
     *
     * @param fields
     */
    public static void setAccessible(Field[] fields, boolean isVisit) {
        AccessibleObject.setAccessible(fields, isVisit);
    }

    /**
     * 获取字段,默认可见
     *
     * @param obj       实体
     * @param fieldName 字段名
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Field getField(Object obj, String fieldName) throws SecurityException, NoSuchFieldException {
        Field field = getClassez(obj).getDeclaredField(fieldName);
        setAccessible(field, true);
        return field;
    }

    /**
     * 获取字段
     *
     * @param obj       实体
     * @param fieldName 字段名
     * @param isVisit   是否可见
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Field getField(Object obj, String fieldName, boolean isVisit) throws SecurityException, NoSuchFieldException {
        Field field = getClassez(obj).getDeclaredField(fieldName);
        setAccessible(field, isVisit);
        return field;
    }

    /**
     * 获取类的相关字段
     *
     * @param obj
     * @param fieldNames
     * @param isVisit
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Field[] getFields(Object obj, String[] fieldNames, boolean isVisit) throws SecurityException, NoSuchFieldException {
        if (fieldNames == null || fieldNames.length == 0) return null;
        int size = fieldNames.length;
        Field[] fields = new Field[size];
        for (int i = 0; i < size; i++) {
            String fieldName = fieldNames[i];
            fields[i] = getField(obj, fieldName, isVisit);
        }
        return fields;
    }

    /**
     * 获取类的相关字段,默认设置可见
     *
     * @param obj
     * @param fieldNames
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Field[] getFields(Object obj, String[] fieldNames) throws SecurityException, NoSuchFieldException {
        return getFields(obj, fieldNames, true);
    }

    /**
     * 获取类的相关字段,默认设置可见
     *
     * @param obj
     * @return
     */
    public static Field[] getFields(Object obj) {
        return getFields(getClassez(obj), true);
    }

    /**
     * 设置值
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void setValue(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field f = getField(obj, fieldName);
        setValue(obj, f, value);
    }

    /**
     * 设置值
     *
     * @param obj
     * @param field
     * @param value
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void setValue(Object obj, Field field, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * 获取类的相关字段,默认设置可见
     *
     * @param classez 类
     * @return
     */
    public static Field[] getFields(Class<? extends Object> classez) {
        return getFields(classez, true);
    }

    /**
     * 获取类的相关字段
     *
     * @param classez 类
     * @param isVisit 是否可访问
     * @return
     */
    public static Field[] getFields(Class<?> classez, boolean isVisit) {
        if (classez != null) {
            Field[] fields = classez.getDeclaredFields();
            if (isVisit) setAccessible(fields);
            return fields;
        }
        return null;
    }

    /**
     * 查找已什么为开始的方法
     *
     * @param obj
     * @param start
     * @return
     */
    public static Method[] getMethodByStart(Object obj, String start) {
        return getMethodByStart(getClassez(obj), start);
    }

    /**
     * 查找已什么为开始的方法
     *
     * @param classez
     * @param start
     * @return
     */
    public static Method[] getMethodByStart(Class<?> classez, String start) {
        Method[] methods = getMethods(classez);
        List<Method> methodList = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.getName().startsWith(start)) {
                methodList.add(method);
            }
        }
        if (methodList == null || methodList.isEmpty()) return null;
        Method[] responses = new Method[methodList.size()];
        methodList.toArray(responses);
        return responses;
    }


    /**
     * 获取对象的某个方法
     *
     * @param obj
     * @return
     */
    public static Method getMethod(Object obj, String methodName) {
        return getMethod(getClassez(obj), methodName);
    }

    /**
     * 获取类的某个方法
     *
     * @param classez
     * @param methodName
     * @return
     */
    public static Method getMethod(Class<?> classez, String methodName) {
        Method[] methods = getMethods(classez);
        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }


    /**
     * 获取对象的方法
     *
     * @param obj
     * @return
     */
    public static Method[] getMethods(Object obj) {
        return getMethods(getClassez(obj));
    }

    /**
     * 获取类的方法
     *
     * @param classez
     * @return
     */
    public static Method[] getMethods(Class<?> classez) {
        return classez.getDeclaredMethods();
    }

    /**
     * @param obj
     * @param method
     * @param args
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return method.invoke(obj, args);
    }

    /**
     * 获取这个ben的info
     */
    public static BeanInfo getBeanInfo(Class<?> class_) throws IntrospectionException {
        return Introspector.getBeanInfo(class_);
    }

    /***
     * @param pd 一个ben 的 PropertyDescriptor
     *           <p>
     *           由此获得该 Object 的一个   get set 方法对应的 fieldName
     */
    public static String getFieldName(PropertyDescriptor pd) {
        return pd.getWriteMethod().getName();
    }

    /**
     * 获取 一个 ben 的 PropertyDescriptor
     *
     * @param fieldName field 的名称
     * @param class_    的 class
     * @throws IntrospectionException
     */
    public static PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> class_) throws IntrospectionException {

        return new PropertyDescriptor(fieldName, class_);
    }

    /**
     * 通过反射 创建一个 基础的 数据对象,java 的xls 本身 不支持时间，所以在这里不通过反射创建直接判断类型
     *
     * @throws Exception
     */
    public static Object getObject(Class<?> class_, String value) {
        try {
            if (Integer.class.equals(class_) || int.class.equals(class_)) {
                return Integer.valueOf(value.toString());
            } else if (Double.class.equals(class_) || double.class.equals(class_)) {
                return Double.valueOf(value.toString());
            } else if (Float.class.equals(class_) || float.class.equals(class_)) {
                return Float.valueOf(value.toString());
            } else if (Boolean.class.equals(class_) || boolean.class.equals(class_)) {
                return Boolean.valueOf(value.toString());
            } else if (Date.class.equals(class_) || java.sql.Date.class.equals(class_)) {
                return DateUtil.getDateTime(value);
            } else if (BigDecimal.class.equals(class_)) {
                return new BigDecimal(value);
            } else {
                return value.toString();
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    System.err.println("jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1,
                                                name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(
                        packageName + "." + file.getName(),
                        file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' + className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据field获得级联的class
     * @param field
     * @return
     * @Author chenzhenwei
     * @Date 2015-11-01
     */
    public static Class<?> findCascadeClass(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {//是否是Collection子类
            return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        } else {
            return field.getType();
        }
    }

    /**
     * 获得class中的第一个methodName方法中的参数
     * @param clazz
     * @param methodName
     * @return
     * @Author chenzhenwei
     * @Date 2015-11-10
     */
    public static String[] getParameterNames(Class<?> clazz, String methodName) {
        Method method = ClassUtil.getMethod(clazz, methodName);
        return getParameterNames(method);
    }

    /**
     * 获得一个方法中的参数
     * @param method
     * @return
     * @Author chenzhenwei
     * @Date 2015-11-10
     */
    public static String[] getParameterNames(Method method) {
        if (method != null) {
            return parameterNameDiscoverer.getParameterNames(method);
        }
        return null;
    }

    /**
     * 获取 目标对象
     * @param proxy 代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {

        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }

        if(AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }



    }


    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

}
