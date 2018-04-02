<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<style>
    .main-content {
        display: block;
        width: 100%;
        height: auto;
    }
    .header_wrapper{}
    .header_box{background-color: #181F23;height: 41px;line-height: 41px;overflow: hidden;position: relative}
    .header_box table{height: 41px;}

    .header_menu{background-color: #181F23;height: 41px;line-height: 41px;position: absolute;left: 20px;top:0;z-index: 10;overflow: hidden;}
    .header_menu .titles{padding: 0;margin: 0;height: 41px; background-color: #434343;position: absolute;left: 0;top:0;}

    .titles li{float: left;list-style: none;color:#b8c7ce;font-size: 14px;height:40px;border-bottom:1px solid #141a1d;white-space:nowrap;border-right:1px solid #141a1d;padding: 0 26px 0 20px;position: relative;cursor: pointer}
    .titles li.on{background-color: #2F2F2F;color: #fff;}
    .titles li:hover{background-color:#2F2F2F;color: #FEFFFD;}
    .titles li span{width: 20px;height: 20px;line-height: 20px;position: absolute;right: 0;top: 9px;color: #8aa4af;font-size: 25px;}
    .titles li span:before{font-size: 12px;}
    .titles li span:hover,.titles li.on span:hover{color:#ffffff;}


    .iframes {min-height: 650px;}
    .iframes iframe{border: 0;}
</style>
<div class="page-container container ">
    <%--<div class="header_box" style="background: #434343;">
        <span class="double-left fa fa-angle-double-left"></span>
        <div class="header_menu" onselectstart="return false;" style="background: #434343;">
            <ul class="titles">
                <li data-href="/" class="on">主页</li>
            </ul>
        </div>
        <span class="double-right fa fa-angle-double-right"></span>
    </div>--%>


</div>
