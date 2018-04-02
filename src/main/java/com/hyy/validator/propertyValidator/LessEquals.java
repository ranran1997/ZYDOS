package com.hyy.validator.propertyValidator;

import com.hyy.utils.ClassUtil;
import com.hyy.utils.ObjectUtil;
import com.hyy.validator.ValidType;

import java.util.Date;

/**
 * 
 * 功能描述：判断小于等于
 *
 */
public class LessEquals extends Rule{

	private Object other;
	public LessEquals(String propertyName, ValidType type, String errorMessage) {
		super(propertyName, type, errorMessage);
	}
	
	public LessEquals(String propertyName, ValidType type, Object other , String errorMessage) {
		super(propertyName, type, errorMessage);
		this.other = other;
	}
	
	

	@Override
	public boolean validate(Object obj) throws ValidateException {
		Object value;
		try {
			value = ClassUtil.getValueByFieldName(obj, name);
		} catch (Exception e) {
			throw new ValidateException();
		}
		if( ObjectUtil.isEmpty( value ) ) return true;
		switch( type ){
		case NUMBER:
			return number( value );
		case DATE:
			return date( value );
		}
		return false;
	}

	private boolean number(Object value){
		return ((Number)other).doubleValue() >= ((Number)value).doubleValue();
	}
	
	private boolean date(Object value){
		return ((Date)other).compareTo( (Date)value ) >= 0;
	}
	
}
