package com.hyy.validator;


import com.hyy.utils.CollectionsUtil;
import com.hyy.utils.ObjectUtil;
import com.hyy.utils.StringUtils;
import com.hyy.validator.propertyValidator.Rule;
import com.hyy.validator.propertyValidator.ValidateException;

/**
 * 
 * 功能描述：验证适配器
 *
 */
public class Validator{
	
	/**
	 * 通过rule验证实体是否可用
	 * @param obj
	 * @param rules
	 * @return 返回empty 表示无错误
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws ValidateException 
	 */
	public static String validate( Object obj , Rule ... rules ) throws ValidateException{
		if( ObjectUtil.isEmpty( obj ) || CollectionsUtil.isEmpty( rules ) ) return StringUtils.EMPTY;
		String errorMessage = StringUtils.EMPTY;
		
		for( Rule rule : rules ){
			if( !rule.validate( obj ) ){
				errorMessage += rule.getErrorMessage();
			}
		}
		return errorMessage;
	}

}
