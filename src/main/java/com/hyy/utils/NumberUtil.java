package com.hyy.utils;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 功能描述：number的操作类
 * 
 */
public class NumberUtil extends org.apache.commons.lang3.math.NumberUtils {


	/**
	 * 产生一定范围内num个随机数 并排序
	 * @param num
	 * @param max
	 * @return
	 */
	public static Integer[] getRandonNumber(int num, int max){
		List<Integer> position = new ArrayList<Integer>();
		do{
			//Todo 验证随机性是否均匀，算法对随机性要求是否足够严格？？？？？
			int pos = (int) ((Math.round(Math.random()*1000))%max);
			if(!position.contains(pos)){
				position.add(pos);
			}
		}while (position.size()<num);
		Integer[] positions = new Integer[num];
        position.toArray(positions);
		Arrays.sort(positions);
		return positions;
	}


	/**
	 * 判断数字是否为null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Number number) {
		return ObjectUtil.isEmpty(number);
	}

	/**
	 * 判断数字不是否为null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Number number) {
		return !isEmpty(number);
	}

	/**
	 * 判断两个数是否相等,如果其中一个为null的话都会返回false
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean equals(Number first, Number second) {
		if (isEmpty(first))
			return false;
		if (isEmpty(second))
			return false;
		return first.equals(second);
	}

	/**
	 * 默认保留两位小数
	 * 
	 * @param Float
	 *            value
	 * @return
	 */
	public static Float toFloat(Float value) {
		return toFloat(value, 2);
	}
	
	public static Float strToFloat(String obj) {
		return obj==null?null:toFloat(obj);
	}

	/**
	 * 保留point前的小数位
	 * 
	 * @param value
	 * @param point
	 * @return
	 */
	public static Float toFloat(Float value, int point) {
		NumberFormat formater = NumberFormat.getInstance();
		formater.setMaximumFractionDigits(point);
		formater.setMinimumFractionDigits(2);

		try {
			return formater.parse(formater.format(value)).floatValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * 保留point前的小数位
	 * 
	 * @param value
	 * @param point
	 * @return
	 */
	public static Double toDouble(Double value, int point) {
		NumberFormat formater = NumberFormat.getInstance();
		formater.setMaximumFractionDigits(point);
		formater.setMinimumFractionDigits(2);
		try {
			return formater.parse(formater.format(value)).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**  
	 * 默认保留2位小数
	 * @param value
	 * @return  
	 */
	public static Double toDouble(Double value){
		return toDouble(value, 2);
	}
	
	public static Double strToDouble(String obj) {
		return obj==null?null:toDouble(obj);
	}
	
	/**
	 * 去除为 null的 值
	 * 
	 * 
	 * */
	public static  Integer [] toDeleteIsNull ( Integer [] ns ) {
		List <Integer> list = new ArrayList<Integer>();
		for( Integer nu : ns ) {
			if( nu != null ) {
				
				list.add( nu );
			}
		}
		Integer [] nl = new Integer[list.size()];
		return list.toArray (nl);
	}

}
