package com.hyy.validator.propertyValidator;

import com.hyy.utils.ClassUtil;
import com.hyy.utils.ObjectUtil;
import com.hyy.utils.StringUtils;
import com.hyy.validator.ValidType;

import java.util.Date;

/*import org.apache.commons.lang.Stringutil;*/

/**
 *
 * 功能描述：验证在 两值之间
 *
 */
public class Between extends Rule{

	private Object first;
	private Object next;
	
	public Between(String propertyName, ValidType type, String errorMessage) {
		super(propertyName, type, errorMessage);
	}

	public Between(String propertyName, ValidType type,Object first , Object next, String errorMessage) {
		super(propertyName, type, errorMessage);
		this.first = first;
		this.next = next;
	}

	
	@Override
	public boolean validate(Object obj) throws ValidateException {
		Object value;
		try {
			value = ClassUtil.getValueByFieldName(obj, name);
		} catch (Exception e) {
			throw new ValidateException(e);
		}
		if( ObjectUtil.isEmpty( value ) ) return true;
		switch( type ){
		case NUMBER:
			return number( value );
		case DATE:
			return date( value );
		case STRING:
			return string( value );
		}
		return false;
	}

	private boolean number(Object value){
		return ((Number)first).doubleValue() <= ((Number)value).doubleValue()
				&& ((Number)next).doubleValue() >= ((Number)value).doubleValue();
	}
	
	private boolean date(Object value){
		return ((Date)first).compareTo( (Date)value ) <=0 
				&& ((Date)next).compareTo( (Date)value ) >= 0;
	}

	private boolean string(Object value){
		if( StringUtils.isEmpty((String) value) ) return true;
		return number( ( (String)value).length() );
	}
}
