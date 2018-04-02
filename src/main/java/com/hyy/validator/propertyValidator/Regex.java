package com.hyy.validator.propertyValidator;

import com.hyy.utils.ClassUtil;
import com.hyy.validator.ValidType;

import java.util.regex.Pattern;


public class Regex extends Rule {

    private String regex;

    public Regex(String propertyName, ValidType type, String errorMessage) {
        super(propertyName, type, errorMessage);
    }

    public Regex(String propertyName, ValidType type, String errorMessage, String regex) {
        super(propertyName, type, errorMessage);
        this.regex = regex;
    }

    @Override
    public boolean validate(Object obj) throws ValidateException {
        Object value;
        try {
            value = ClassUtil.getValueByFieldName(obj, name);
        } catch (Exception e) {
            throw new ValidateException();
        }
        if(value == null) value = "";
        return Pattern.compile(regex).matcher(value.toString()).matches();
    }


}
