package com.hyy.base;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 所有model类的抽象类,包含每个item应该具有的基本方法,及一些方法 如 toString的实现
 * 
 * @author huangxing
 * 
 *         2013-11-12
 */
public abstract class AbstractItem implements ItemBase, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public Object clone()  throws CloneNotSupportedException {
		try {
			return super.clone();
			
			} catch (Exception e) {
			e.printStackTrace();
			
			logger.error(getClass().getName() + "[clone],浅拷贝未成功");
		}
		return  null ;
	}
	/**
	 * 返回model类的基本json格式
	 * @return
	 */
	public String toJSON() {
		return JSONObject.fromObject(this).toString();
	}
	
	/**
	 * 返回xml格式数据，用与对工作日志的记录和管理
	 * @return
	 */
	public String toXML() {
		return null;
	}
}
