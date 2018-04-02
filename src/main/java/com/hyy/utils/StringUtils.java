package com.hyy.utils;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 功能描述：字符串的操作类
 * 
 * 2010-12-30  上午11:37:27
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 检查指定的字符串列表是否不为空。
	 * 
	 * @param values 字符串列表
	 * @return true/false
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
	
	/**
	 * 获取非null的字符串
	 * @param str
	 * @return
	 */
	public static String getNotNull( String str ){
		return str == null ? "" : str;
	}
	
	/**
	 * 获取非null的字符串
	 * @param str
	 * @return
	 */
	public static String getNotNull( Object str ){
		return str == null ? "" : str.toString();
	}
	
	/**
	 * 计算流水号
	 * @param curr
	 * @param length
	 * @return
	 */
	public static String getSerialNumber( int curr , int length ){
		return getSerialNumber( EMPTY , curr , length );
	}
	
	/**
	 * 截取字符串，以字节截取。
	 * {@code return substring(s, length, true)}
	 * 
	 * @param s
	 * @param length
	 * @param more ....
	 * @return
	 */
	public static String substring(String s, int length,String more) {
		return substring(s, length, true, more);
	}
	/**
	 * 截取字符串，以字节截取。
	 * {@code return substring(s, length, true)}
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String substring(String s, int length) {
		return substring(s, length, true, "");
	}
	
	
	/**
	 * 截取字符串，以字节截取，如果截取的最后一个字符为双字节，根据ignore决定是否省略
	 * 
	 * @param s
	 * @param length
	 * @param ignore
	 * @return
	 */
	public static String substring(String s, int length, boolean ignore,String more) {
		if( s == null || isEmpty( s ) ) return EMPTY;
		int oldLength = s.length();
		if( oldLength <= length / 2 ) return s;
		
		char[] c = s.toCharArray();
		StringBuilder builder = new StringBuilder(length);
		int len = 0;
		for (int i = 0; i < length && i < c.length; i++) {
			if (len < length) {
				if (c[i] / 0x80 == 0) { // 单字节
					builder.append(c[i]);
					len++;
				} else { // 双字节
					if (len + 2 <= length || !ignore) {
						builder.append(c[i]);
						len += 2;
					} else {
						break;
					}
				}
			} 
		}
		
		if( !isEmpty(more) || oldLength != builder.toString().length() )builder.append( more );
		return builder.toString();
	}
	
	/**
	 * 正则表达式
	 * 	
	 * @param s
	 * @param regEx
	 * @return
	 */
	public static Boolean regexString(String s, String regEx) {
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(s);
		boolean result = matcher.matches();		
		return result;
	}
	
	
	/**
	 * 过滤掉所有空白<br>
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String trimAllSpace(String str) {
		if (isEmpty(str))
			return EMPTY;
		return trim(str).replace("\t", StringUtils.EMPTY).replaceAll("\\s", StringUtils.EMPTY).replaceAll("　", StringUtils.EMPTY);
	}
	

	/**
	 * 通过正则表达式对字符串进行分割
	 * 
	 * @param str
	 *            字符串
	 * @param regex
	 *            正则表达式
	 * @return
	 */
	public static String[] splitRegex(String str, String regex) {
		String[] array = new String[0];
		if (isEmpty(str) || isEmpty(regex))
			return array;
		array = str.split(regex);
		if (array == null)
			return new String[0];
		return array;
	}
	/**
	 * 连接字符串,默认没有分隔符
	 * @param str
	 * @return
	 */
	public static String join( String[] str  ){
		return join("", str);
	}
	
	/**
	 * 连接字符串
	 * @param joinAt 分隔符
	 * @param str
	 * @return
	 */
	public static String join(  String joinAt,String[] str  ){
		String response = "";
		boolean first = true;
		for(String s : str ){
			if( first ){
				response += s;
				first = false;
			}else{
				response += ( joinAt + s );
			}
		}
		return response;
	}

	/**
	 * 计算流水号
	 * @param curr
	 * @param length
	 * @return
	 */
	public static String getSerialNumber(String prefix, int curr , int length ){
		StringBuffer sb = new StringBuffer();
		
		if( String.valueOf( curr ).length() == length ){
			return prefix + String.valueOf( curr );
		}
		sb.append( curr );
		while( sb.length() < length ){
			sb.insert(0, "0" );
		}
		sb.insert( 0 , prefix );
		return sb.toString();
	}
	
	/**
	 * 通过当前的code计算下一个code
	 * @param code
	 * @param lastLength 最后的code长度
	 * for example: StringUtils.nextCode( "0102",2 ); 返回0103
	 * @return
	 */
	public static String nextCode( String code , int lastLength ){
		if( StringUtils.isEmpty( code ) ) return EMPTY;
		if( code.length() < lastLength ) return EMPTY;
		
		String prefix = code.substring( 0 ,  code.length() - lastLength );
		String last = code.substring( code.length() - lastLength ,  code.length() );
		
		return getSerialNumber(prefix, NumberUtil.toInt(last) + 1, lastLength);
	}
	
	/**
	 * 按照长度分隔字符串 如: splitByLength( "aaaaaa" , 4) 返回结果 : String[]{"aaaa","aa"}
	 * @param length
	 * @return
	 */
	public static String[] splitByLength(String str , int length ){
		if( StringUtils.isEmpty( str ) ) return null;
		int size = str.length();
		List<String> response = new ArrayList<String>();
		for(int i=0;i<size;i+=length){
			response.add( StringUtils.substring( str , i , i+length ) );
		}
		String[] strArray = new String[ response.size() ];
		response.toArray(strArray);
		return strArray;
	}
	
	/**
	 * 按照长度获取编号   如: getCodes( "aaaaaa" , 4) 返回结果 : String[]{"aaaa","aaaaaa"}
	 * @param length
	 * @return
	 */
	public static String[] getCodes(String str , int length ){
		if( StringUtils.isEmpty( str ) ) return null;
		int size = str.length();
		List<String> response = new ArrayList<String>();
		for(int i=0;i<size;i+=length){
			response.add( StringUtils.substring( str , 0 , i+length ) );
		}
		String[] strArray = new String[ response.size() ];
		response.toArray( strArray );
		return strArray;
	}
	/**
	 *   中文字符检验   
     *   @param   s   String   
     *   @return  包含中文字符返回true,否则返回false   
     *   
     */   
   public static boolean chineseValid(String s){
	   if(ObjectUtil.isEmpty(s)){
		   return false;
	   }
       int   length   =   s.length();   
       byte   []   b;   
       for(int   i   =   0;i<length;i++)   
       {   
       b   =     s.substring(i).getBytes();   
       if   ((b[0]&0xff)>128)   
       return   true;   
       }   
       return   false;   
   }
   
   
   public static Pager urlToPager( String url ) {
	   Pager pager = new Pager();
	   if( StringUtils.isEmpty( url )) {
		   return pager;
	   }
	   else {
		   Map<String,Object> pagerQuesy = new HashMap<String,Object>();
		   String [] querys =url.split("&");
		   for( String query : querys ) {
			   query = query.replace("query[", "").replace("]", "");
			   String [] queryStr = query.split("=");
			   if(queryStr.length!=2) continue;
			   pagerQuesy.put(queryStr[0], queryStr[1]);
		   }
		   try{
			   if( pagerQuesy.get("page") != null&&!"".equals(pagerQuesy.get("page")))
			   pager.setPage(Integer.valueOf((String) pagerQuesy.get("page")));
			   if( pagerQuesy.get("pageSize") != null&&!"".equals(pagerQuesy.get("pageSize")))
			   pager.setPageSize(Integer.valueOf((String) pagerQuesy.get("pageSize")));
			   if( pagerQuesy.get("totalNum") != null&&!"".equals(pagerQuesy.get("totalNum")))
			   pager.setTotalNum(Integer.valueOf((String) pagerQuesy.get("totalNum")));
			   if( pagerQuesy.get("totalPage") != null&&!"".equals(pagerQuesy.get("totalPage")))
			   pager.setTotalPage(Integer.valueOf((String) pagerQuesy.get("totalPage")));
		   }catch (Exception e) {
		}
		   pager.setQuery(pagerQuesy);
		   return pager;
	   }
   }
   
   
   public static  String mapToURL ( Map<String,Object >  map  ) {
	   if( map == null || map.isEmpty() ) return "";
	   StringBuffer sb = new StringBuffer("?");
	   Iterator<Entry<String, Object  >> map_iterator=  map.entrySet().iterator();
	   while ( map_iterator .hasNext() ) {
		  Entry<String, Object> mapObjct  = map_iterator.next();
		  String [] value = (String [])mapObjct.getValue() ;
		  if(  value != null && value.length > 0 )
		  sb.append( mapObjct.getKey() ).append("=").append( value[0] ).append("&");
	   }
	   return sb.toString();
   }
   
   
   public static String intListToUrl(Integer [] list ) {
	   StringBuffer sb = new StringBuffer();
	   for( Integer id : list ) {
		   if(id !=null)
		 sb.append(id).append(",");
	   }
	return sb.toString();
   }

	/**
	 *
	 * 写一个简单的 解析 方法
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *
	 *
	 * */
	public static String  reString ( String model , Map< String, Object >  map ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		model = model.replace("}", "} ");
		List< String > objModel = getModelViewFile(model);
		for( String model_obj  : objModel ) {
			String patch = model_obj.replace("{", "").replace("}", "");
			String obj_string = getObjString( map.get( patch.split("\\.")[0] ),  StringUtils.join( "." , removeListThis( patch.split("\\.") ) ) );
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;
			try{
				model = model.replace( "#$" + model_obj +" ", obj_string == null ? "" : sdf.format(new Date(Long.valueOf(obj_string) * 1000)) );
			}
			catch(Exception ex){
			}
			model = model.replace( "$" + model_obj +" ", obj_string == null ? "" : obj_string );
		}
		return model;
	}

	public static List<String> getModelViewFile(String s) {
		List<String> PString = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\$(\\S)*");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String ss = matcher.group();
			String[] res = ss.split("\\$");
			String a = res[1];
			String b = a.split("\"")[0];
			b = b.split(" ")[0];
			b = b.split(">")[0];
			b = b.split(",")[0];
			PString.add(b);
		}
		return PString;
	}

	private static String getObjString( Object obj ,String objStr ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if( obj == null ){
			return null;
		}
		//如果是字符串类型，则直接返回
		if( obj instanceof String ) {
			return (String) obj;
		}
		if( obj instanceof Map<?,?> ) {
			Map< ? , ? > map = (Map<?,?>)obj;
			return "" + map.get( objStr );
		}
		String objs [] = objStr.split("\\.");
		Object object__ = getValue( obj , objs[0] );
		String [] objs__ = removeListThis(objs);
		String objStr__ = join(".", objs__);
		if( objs.length == 1  ) {
			return object__.toString();
		}
		return getObjString( object__ , objStr__ );//递归
	}

	private static Object getValue( Object obj , String filedName ) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PropertyDescriptor descriptor = new PropertyDescriptor(filedName, obj.getClass());
		return descriptor.getReadMethod().invoke(obj);
	}

	/**
	 *
	 * 丢掉   当前 也就是  第一个元素
	 *
	 * */
	private static String [] removeListThis ( String [] objs ) {
		if( objs .length == 1 ) {
			return objs ;
		}
		else {
			String [] objs__ = new String [ objs.length -1 ];
			for( int i = 0 ; i< objs.length-1 ; i++ ) {
				objs__ [i] = objs [ i+1 ];
			}
			return objs__;
		}
	}

	public static String toUpCaseFirstChar(String data) {
		return data.substring(0,1).toUpperCase() + data.substring(1);
	}
}
