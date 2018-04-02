package com.hyy.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 功能描述：所有的反射功能
 * 
 * @author jiushixuefeng
 * Dec 2, 2010  11:23:28 AM
 */
public class Reflect {

	/**
	 * 获取实体的类
	 * @param obj
	 * @return
	 */
	public static Class<? extends Object> getClassez(Object obj ){
		return obj.getClass();
	}
	
	/**
	 * 获取字段值
	 * @param obj   实体
	 * @param fieldName 字段名
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	
	public static Object getValueByFieldName( Object obj , String fieldName ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		return getValue( obj , getField( obj , fieldName ) );
	}
	
	/**
	 * 获取字段的值
	 * @param obj
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValue( Object obj , Field field ) throws IllegalArgumentException, IllegalAccessException{
		return field.get( obj );
	}
	
	/**
	 * 设置字段可见
	 * @param field
	 */
	public static void setAccessible(Field field){
		setAccessible( new Field[]{field});
	}
	/**
	 * 设置字段可见
	 * @param field
	 */
	public static void setAccessible(Field field, boolean isVisit ){
		setAccessible( new Field[]{field}, isVisit );
	}
	
	/**
	 * 设置字段可见
	 * @param fields
	 */
	public static void setAccessible( Field[] fields ){
		setAccessible(fields,true );
	}
	
	/**
	 * 设置字段可见
	 * @param fields
	 */
	public static void setAccessible( Field[] fields , boolean isVisit ){
		AccessibleObject.setAccessible(fields,isVisit );
	}

	/**
	 * 获取字段,默认可见
	 * @param obj 实体
	 * @param fieldName 字段名
	 * @param isVisit 是否可见
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Field getField( Object obj , String fieldName ) throws SecurityException, NoSuchFieldException{
		Field field = getClassez( obj ).getDeclaredField( fieldName );
		setAccessible( field , true );
		return field;
	}
	
	/**
	 * 获取字段
	 * @param obj 实体
	 * @param fieldName 字段名
	 * @param isVisit 是否可见
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Field getField( Object obj , String fieldName , boolean isVisit ) throws SecurityException, NoSuchFieldException{
		Field field = getClassez( obj ).getDeclaredField( fieldName );
		setAccessible( field , isVisit );
		return field;
	}
	
	/**
	 * 获取类的相关字段
	 * @param classez 类
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Field[] getFields( Object obj , String[] fieldNames , boolean isVisit ) throws SecurityException, NoSuchFieldException{
		if( fieldNames == null || fieldNames.length == 0 ) return null;
		int size = fieldNames.length;
		Field[] fields = new Field[ size ];
		for(int i=0;i<size;i++){
			String fieldName = fieldNames[i];
			fields[i] = getField( obj , fieldName , isVisit );
		}
		return fields;
	}
	
	/**
	 * 获取类的相关字段,默认设置可见
	 * @param classez 类
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Field[] getFields( Object obj , String[] fieldNames ) throws SecurityException, NoSuchFieldException{
		return getFields( obj , fieldNames , true );
	}
	
	/**
	 * 获取类的相关字段,默认设置可见
	 * @param classez 类
	 * @return
	 */
	public static Field[] getFields( Object obj ){
		return getFields( getClassez( obj ) , true );
	}
	
	/**
	 * 设置值
	 * @param obj
	 * @param field
	 * @param value
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setValue( Object obj , String fieldName , Object value ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Field f = getField( obj , fieldName );
		setValue( obj , f , value );
	}
	
	/**
	 * 设置值
	 * @param obj
	 * @param field
	 * @param value
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setValue( Object obj , Field field , Object value ) throws IllegalArgumentException, IllegalAccessException{
		field.setAccessible( true );
		field.set( obj , value);
	}
	/**
	 * 获取类的相关字段,默认设置可见
	 * @param classez 类
	 * @return
	 */
	public static Field[] getFields( Class<? extends Object> classez ){
		return getFields(classez , true );
	}
	/**
	 * 获取类的相关字段
	 * @param classez 类
	 * @param isVisit 是否可访问
	 * @return
	 */
	public static Field[] getFields( Class<?> classez , boolean isVisit ){
		if( classez != null ){
			Field[] fields = classez.getDeclaredFields();
			if( isVisit ) setAccessible( fields );
			return fields;
		}
		return null;
	}

	/**
	 * 查找已什么为开始的方法
	 * @param classez
	 * @param start
	 * @return
	 */
	public static Method[] getMethodByStart( Object obj , String start){
		return getMethodByStart( getClassez( obj ) , start );
	}
	/**
	 * 查找已什么为开始的方法
	 * @param classez
	 * @param start
	 * @return
	 */
	public static Method[] getMethodByStart( Class<?> classez , String start){
		Method[] methods = getMethods( classez );
		List<Method> methodList = new ArrayList<Method>();
		for( Method method : methods ){
			if( method.getName().startsWith( start ) ) {
				methodList.add( method );
			}
		}
		if( CollectionsUtil.isEmpty( methodList ) ) return null;
		Method[] responses = new Method[ methodList.size() ];
		methodList.toArray( responses );
		return responses;
	}
	

	/**
	 * 获取对象的某个方法
	 * @param obj
	 * @return
	 */
	public static Method getMethod( Object obj , String methodName){
		return getMethod( getClassez( obj ) , methodName );
	}
	
	/**
	 * 获取类的某个方法
	 * @param obj
	 * @return
	 */
	public static Method getMethod( Class<?> classez , String methodName){
		Method[] methods = getMethods( classez );
		for( Method method : methods ){
			if( method.getName().equals( methodName ) ) 
				return method;
		}
		return null;
	}
	
	
	/**
	 * 获取对象的方法
	 * @param obj
	 * @return
	 */
	public static Method[] getMethods( Object obj ){
		return getMethods( getClassez( obj ) );
	}
	
	/**
	 * 获取类的方法
	 * @param obj
	 * @return
	 */
	public static Method[] getMethods( Class<?> classez ){
		return classez.getDeclaredMethods();
	}
	
	/**
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object invokeMethod( Object obj , Method method , Object... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		return method.invoke(obj, args);
	}
	
}
