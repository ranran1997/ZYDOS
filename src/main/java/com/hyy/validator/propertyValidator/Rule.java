package com.hyy.validator.propertyValidator;

import com.hyy.validator.ValidType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;



public abstract class Rule {
    protected final Log logger = LogFactory.getLog(getClass());
    protected String errorMessage;
    protected String name;
    protected ValidType type;

    public Rule(String propertyName, ValidType type, String errorMessage) {
        this.name = propertyName;
        this.type = type;
        this.errorMessage = errorMessage;
    }


    /**
     * 验证
     * 工厂模式
     *
     * @param obj
     * @return
     * @throws ValidateException
     */
    public abstract boolean validate(Object obj) throws ValidateException;

    /**
     * errorMessage
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 字符长度介于多少与多少之间
     *
     * @param propertyName
     * @param min
     * @param max
     * @param errorMessage
     * @return
     */
    public static Rule length(String propertyName, Number min, Number max, String errorMessage) {
        return new Between(propertyName, ValidType.STRING, min, max, errorMessage);
    }

    /**
     * 数字介于多少与多少之间
     *
     * @param propertyName
     * @param min
     * @param max
     * @param errorMessage
     * @return
     */
    public static Rule between(String propertyName, Number min, Number max, String errorMessage) {
        return new Between(propertyName, ValidType.NUMBER, min, max, errorMessage);
    }

    /**
     * 日期介于多少与多少之间
     *
     * @param propertyName
     * @param min
     * @param max
     * @param errorMessage
     * @return
     */
    public static Rule between(String propertyName, Date min, Date max, String errorMessage) {
        return new Between(propertyName, ValidType.DATE, min, max, errorMessage);
    }

    /**
     * 是否非null
     *
     * @param propertyName
     * @param errorMessage
     * @return
     */
    public static Rule required(String propertyName, String errorMessage) {
        return new Required(propertyName, null, errorMessage);
    }

    /**
     * <= 多少
     *
     * @param first
     * @return
     */
    public static Rule lessEquals(String propertyName, Number first, String errorMessage) {
        return new LessEquals(propertyName, ValidType.NUMBER, first, errorMessage);
    }

    /**
     * <= 多少
     *
     * @param first
     * @return
     */
    public static Rule lessEquals(String propertyName, Date first, String errorMessage) {
        return new LessEquals(propertyName, ValidType.DATE, first, errorMessage);
    }

    /**
     * >= 多少
     *
     * @param first
     * @return
     */
    public static Rule moreEquals(String propertyName, Number first, String errorMessage) {
        return new MoreEquals(propertyName, ValidType.NUMBER, first, errorMessage);
    }

    /**
     * >= 多少
     *
     * @param first
     * @return
     */
    public static Rule moreEquals(String propertyName, Date first, String errorMessage) {
        return new MoreEquals(propertyName, ValidType.DATE, first, errorMessage);
    }

    /**
     * < 多少
     *
     * @param first
     * @return
     */
    public static Rule less(String propertyName, Number first, String errorMessage) {
        return new Less(propertyName, ValidType.NUMBER, first, errorMessage);
    }

    /**
     * < 多少
     *
     * @param first
     * @return
     */
    public static Rule less(String propertyName, Date first, String errorMessage) {
        return new Less(propertyName, ValidType.DATE, first, errorMessage);
    }

    /**
     * > 多少
     *
     * @param first
     * @return
     */
    public static Rule more(String propertyName, Number first, String errorMessage) {
        return new More(propertyName, ValidType.NUMBER, first, errorMessage);
    }

    /**
     * > 多少
     *
     * @param first
     * @return
     */
    public static Rule more(String propertyName, Date first, String errorMessage) {
        return new More(propertyName, ValidType.DATE, first, errorMessage);
    }

    /**
     * 是什么
     *
     * @param propertyName
     * @param type
     * @return
     */
    public static Rule is(String propertyName, ValidType type, String errorMessage) {
        return new Is(propertyName, type, errorMessage);
    }

    /**
     * 是什么
     *
     * @param propertyName
     * @param regex
     * @return
     */
    public static Rule regex(String propertyName, String regex, String errorMessage) {
        return new Regex(propertyName, ValidType.STRING, errorMessage, regex);
    }
}
