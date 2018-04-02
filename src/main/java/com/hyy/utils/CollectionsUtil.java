package com.hyy.utils;

import java.util.*;

/**
 * 
 * 功能描述：集合和map的操作类
 * 
 */
public class CollectionsUtil {

	/**
	 * 获取空的map
	 * 
	 * @return
	 */
	public static Map<?, ?> emptyMap() {
		return Collections.EMPTY_MAP;
	}

	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean notEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * map是否不为空
	 * 
	 * @param
	 * @return
	 */
	public static boolean notEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Object[] objects) {
		if (ObjectUtil.isEmpty(objects))
			return true;
		if (objects.length == 0)
			return true;
		else 
			return false;
	}

	public static boolean notEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * 从第一个集合copy到第二个集合,不改变第二个集合的地址
	 * 
	 * @param <T>
	 * @param source
	 * @param target
	 */
	public static <T> void copy(Collection<T> source, Collection<T> target) {
		Iterator<T> i = source.iterator();
		while (i.hasNext()) {
			T t = i.next();
			if (!target.contains(t)) {
				target.add(t);
			}
		}
		Set<T> remove = new HashSet<T>();
		Iterator<T> i1 = target.iterator();
		while (i1.hasNext()) {
			T t = i1.next();
			if (!source.contains(t)) {
				remove.add(t);
			}
		}
		target.removeAll(remove);
	}
	
	public static List<String>  copyToList ( Collection<String> source ) {
		return new ArrayList<String>( source );
	}
}
