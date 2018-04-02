package com.hyy.base;

import net.sf.json.JSONObject;

/**
 * 如果status为false，message会提示错误信息，如果status为true，message无信息，data有数据
 * <p>
 * Created by HengYanYan on 2016/4/16  16:55
 */
public class ZydosResponse<T> {

    private Boolean result;
    private String message;
    private T data;

    /**
     * @param <T>
     * @param message 需要返回的文字
     * @param strings 用于替换文字中的 {0} 等
     * @return
     */
    public static <T> ZydosResponse<T> failResponse(String message,
                                                    String... strings) {
        return valueOf(false, parseMessage(message, strings), null);
    }

    /**
     * 返回成功信息
     *
     * @return ZydosResponse<T>
     */
    public static <T> ZydosResponse<T> successResponse() {
        return successResponse(null);
    }

    /**
     * 返回成功信息
     *
     * @return ZydosResponse<T>
     */
    public static <T> ZydosResponse<T> successResponse(String message, T data) {
        return valueOf(true, message, data);
    }

    /**
     * 返回成功信息
     *
     * @param data
     * @return ZydosResponse<T>
     */
    public static <T> ZydosResponse<T> successResponse(T data) {
        return valueOf(true, null, data);
    }

    /**
     * 替换字符串中的参数
     *
     * @param message 需要解析的字符串
     * @param strings 用于替换message中的{0},{1}等
     * @return 解析后的字符串
     */
    public static String parseMessage(String message, String... strings) {
        for (int i = 0; i < strings.length; i++) {
            message = message.replaceAll("\\{" + i + "\\}", strings[i]);
        }
        return message;
    }

    private static <T> ZydosResponse<T> valueOf(Boolean reslut,
                                                String message, T data) {
        ZydosResponse<T> cr = new ZydosResponse<T>();
        cr.result = reslut;
        cr.message = message;
        cr.data = data;
        return cr;
    }

    /*POJO对象要转成Json，则要求POJO中的属性必须都有getter方法，*/
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /*------------------分界线-----------------------*/

    /**
     * @param message 需要返回的文字
     * @param strings 用于替换文字中的 {0} 等
     * @return
     */
    public static String failResponseJson(String message, String... strings) {
        return valueOfJson(false, parseMessage(message, strings), null);
    }

    /**
     * 返回成功信息json
     *
     * @param data 需要返回的信息
     * @return
     */
    public static <T> String successResponseJson(T data) {
        return valueOfJson(true, null, data);
    }

    /**
     * 返回成功信息json
     *
     * @return
     */
    public static <T> String successResponseJson() {
        return successResponseJson(null);
    }


    private static <T> String valueOfJson(Boolean status, String message, T data) {
        return JSONObject.fromObject(valueOf(status, message, data)).toString();
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }




}
