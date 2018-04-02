package com.hyy.validator.propertyValidator;

import com.hyy.utils.ClassUtil;
import com.hyy.utils.ObjectUtil;
import com.hyy.validator.ValidType;


public class Required extends Rule {

	public Required(String propertyName, ValidType type, String errorMessage) {
		super(propertyName, type, errorMessage);
	}
	

	@Override
	public boolean validate(Object obj) throws ValidateException {
		Object value;
		try {
			value = ClassUtil.getValueByFieldName(obj, name);
		} catch (Exception e) {
			throw new ValidateException();
		}
		if( ObjectUtil.isEmpty( value ) ) return false;
		return true;
	}

}
