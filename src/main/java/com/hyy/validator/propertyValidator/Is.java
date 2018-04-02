package com.hyy.validator.propertyValidator;

import com.hyy.utils.ClassUtil;
import com.hyy.utils.ObjectUtil;
import com.hyy.utils.StringUtils;
import com.hyy.validator.ValidType;

/**
 * 功能描述：是邮件 还是url,暂时只支持这两种
 */
public class Is extends Rule {

    public Is(String propertyName, ValidType type, String errorMessage) {
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
        if (ObjectUtil.isEmpty(value)) return true;
        switch (type) {
            case URL:
                return isUrl(value);
            case EMAIL:
                return isEmail(value);
            case  MOBILE:
                return isMobile(value);
        }
        return false;
    }

    private boolean isUrl(Object value) {
        if (StringUtils.isEmpty((String) value)) return true;
        return ((String) value).matches("[a-zA-z]+://[^\\s]*");
    }

    private boolean isEmail(Object value) {
        if (StringUtils.isEmpty((String) value)) return true;
        return StringUtils.regexString((String) value, "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    private boolean isMobile(Object value) {
        if (StringUtils.isEmpty((String) value)) return true;
        return StringUtils.regexString((String) value,"@\"^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\\\d{8}$\"");
    }
}
