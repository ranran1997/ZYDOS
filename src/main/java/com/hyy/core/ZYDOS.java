package com.hyy.core;

/**
 * 静态常量类
 * Created by HengYanYan on 2016/4/19  16:58
 */
public class ZYDOS {


    /**
     * 订单状态：0-未送货  1-已送货 2-订单取消
     */
    public static class OrderStatus{
        public static final Byte UNDONE = 0;
        public static final Byte DONE = 1;
        public static final Byte CANCLED = 2;
    }

    /**
     * 高德地图API
     */
    public static class mapAPI{
        public static final String WEBAPI = "54b0e2fa5772c57f36a7a400601d8524";
        public static String JSAPI = "c067145c6e540bdc44a404d1e2a63299";
        public static String BDWEBAK = "YVTu3vp6mLishByfjuqYPnpmTsL6ScUI";
    }

}
