package com.hyy.base;


/**
 * 所有model类的基类,包含每个item应该具有的基本方法
 *
 * 2009-02-11
 */
public interface ItemBase extends java.io.Serializable{
	
	public Integer getId();
	
	public void setId(Integer id);
	/**
	 * 返回model类的基本json格式
	 * @return
	 */
	public String toJSON();
	
	/**
	 * 返回xml格式数据，用与对工作日志的记录和管理
	 * @return
	 */
	public String toXML();
}
