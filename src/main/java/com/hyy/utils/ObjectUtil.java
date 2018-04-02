package com.hyy.utils;


import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 
 * 功能描述：所有类的基础方法实现和一些相关的功能方法
 * 
 * @author jiushixuefeng
 * Dec 2, 2010  2:09:34 PM
 */
public class ObjectUtil {
	protected final static Logger logger = Logger.getLogger(Pager.class);
	/**
	 * 判断对象是否为null
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty( Object obj){
		return obj == null;
	}
	
	/**
	 * 判断对象是否都为null
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty( Object... obj){
		if( obj == null ) return true ;
		for( Object o : obj ) {
			
			if( isNotEmpty( o )) return false ;
		}
		return true ;
	}
	
	/**
	 * 判断对象不是否为null
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty( Object obj){
		return !isEmpty( obj );
	}
	
	/**
	 * 按字段匹配的equals
	 * @param own
	 * @param otherObject
	 * @param fieldNames
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean equals( Object own , Object otherObject , String[] fieldNames ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		if( !equalsFilter( own , otherObject )) return false;
		Field[] ownFields = ClassUtil.getFields( own , fieldNames );
		Field[] otherFields = ClassUtil.getFields( otherObject , fieldNames );
		return equals( own , otherObject , ownFields , otherFields );
	}
	
	
	/**
	 * 实体的equals , 全匹配模式 , 所有的字段都需要相等.
	 * @param own
	 * @param otherObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean equals( Object own , Object otherObject ) throws IllegalArgumentException, IllegalAccessException{
		if( !equalsFilter( own , otherObject )) return false;
		Field[] ownFields = ClassUtil.getFields( own );
		Field[] otherFields = ClassUtil.getFields( otherObject );
		return equals( own , otherObject , ownFields , otherFields );
	}
	
	/**
	 * equals的filter类,证明两个类有可比性
	 * @param own
	 * @param otherObject
	 * @return
	 */
	public static boolean equalsFilter( Object own , Object otherObject ){
		if( own == otherObject) {
			return true;
		}
		if(otherObject == null || own == null ) {
			return false;
		}
		if(own.getClass() != otherObject.getClass()){
			return false;
		}
		return true;
	}
	/**
	 * 实体的equals , 全匹配模式 , 所有的字段都需要相等.
	 * @param own
	 * @param otherObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean equals( Object own , Object otherObject , Field[] ownFields , Field[] otherFields ) throws IllegalArgumentException, IllegalAccessException{
		
		if( !equalsFilter( own , otherObject )) return false;
		for( int i=0; i<ownFields.length;i++ ){
			Object ownValue = ClassUtil.getValue( own , ownFields[i]);
			Object otherValue = ClassUtil.getValue( otherObject , otherFields[i]);
			if( ObjectUtil.isEmpty( ownValue ) && ObjectUtil.isEmpty( otherValue ) )return true;
			if( ObjectUtil.isEmpty( ownValue ) || ObjectUtil.isEmpty( otherValue ) )return false;
			
			if( ClassUtil.getClassez( ownValue ).isArray() ){
				int ownLength = Array.getLength( ownValue );
				if( ownLength != Array.getLength( otherValue ) ) return false;
				for(int j = 0; j < Array.getLength( ownValue );j++){
					if(!Array.get( otherValue , j).equals( Array.get( ownValue ,j))) return false;
				}
			}else if( !ownValue.equals( otherValue ) ) return false;
		}
		return true;
	}
	
	/**
	 * 类的深拷贝
	 * @param <T>
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepClone( Object obj ) throws IOException, ClassNotFoundException {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(obj);
			out.close();

			ByteArrayInputStream bin = new ByteArrayInputStream(bout
					.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();
			return (T) ret;
		
	}
	
	/**
	 * 实体的toString
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj,String... fieldName){
		if (obj == null) return "null";
		Class<?> cl = ClassUtil.getClassez( obj );
		if(cl == String.class) return (String) obj;
		if(cl.isArray()){
			String r = cl.getComponentType() + "[]{";
			for(int i = 0; i < Array.getLength(obj);i++){
				if(i>0) r +=",";
				Object val = Array.get(obj,i);
				if(cl.getComponentType().isPrimitive()){
					r += val;
				} else {
					r += toString(val);
				}
			}
			return r + "}";
		}
		
		String r = cl.getName();
		r +="{";
		boolean first = true;
		
		Field[] fields = ClassUtil.getFields( cl );
		if( !isEmpty( fieldName ) ){
			try {
				fields = ClassUtil.getFields(obj, fieldName);
			} catch (Exception e) {
				logger.error( e );
			}
		}else{
			fields = ClassUtil.getFields( cl );
		}
		boolean hasField = false;
		for(Field f : fields ){
			if(!Modifier.isStatic(f.getModifiers())){
				if(!r.endsWith("[")){ 
					if( !first ) {
						r+=",";
					}else {
						r+="[";
						first = false;
					}
					
					r += f.getName() + "=";
					hasField = true;
				}
				try{
					Class<?> t = f.getType();
					Object val = ClassUtil.getValue( obj , f );
					if(t.isPrimitive())
						r += val;
					else 
						r += val;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if( hasField ) r += "]}";

		return r;
	}
}
