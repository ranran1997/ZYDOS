<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="Xenon Boostrap Admin Panel"/>
    <meta name="author" content=""/>

    <title>ZYDOS - 用户组权限管理</title>

    <%--<link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">--%>
    <link rel="stylesheet" href="/assets/css/fonts/linecons/css/linecons.css">
    <link rel="stylesheet" href="/assets/css/fonts/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/bootstrap.css">
    <link rel="stylesheet" href="/assets/css/xenon-core.css">
    <link rel="stylesheet" href="/assets/css/xenon-forms.css">
    <link rel="stylesheet" href="/assets/css/xenon-components.css">
    <link rel="stylesheet" href="/assets/css/xenon-skins.css">
    <link rel="stylesheet" href="/assets/css/custom.css">

    <script src="/assets/js/jquery-1.11.1.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.min.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->

    <style>
        .page-title {
            display: table;
            width: 100%;
            border-spacing: 0px;
            table-layout: fixed;
            position: relative;
            z-index: 1;
            vertical-align: top;
            background: #f8f8f8;
            /*margin-bottom: 30px;*/
            padding: 20px 0;
            box-shadow: 0 1px 0 rgba(0, 1, 1, .08), inset 0 1px 0 #ededed;
        }

        .page-title .title-env {
            float: left;
            padding-left: 50px;
        }

        .page-title .breadcrumb-env {
            float: right;
            padding-right: 50px;
        }

        .breadcrumb-env .breadcrumb {
            margin-bottom: 0;
            margin-top: 30px;
        }

        .panel .panel-body .no-padding {
            padding: 0;
            margin-top: 50px;
            margin-left: 50px;
            margin-right: 50px;
            margin-bottom: 0px;
            height: 450px;
        }
    </style>
    <script>
        $(function () {
            var options = {sortable: true};
            $("select#demo1").treeMultiselect(options);
        });
    </script>
</head>
<body class="page-body">

<%--<div class="page-loading-overlay">
    <div class="loader-2"></div>
</div>--%>

<!-- 设置 -->
<%@ include file="../setting.jsp" %>

<div class="page-container">
    <!-- 左边侧导航 -->
    <%@ include file="../leftSlider.jsp" %>

    <div class="main-content">
        <!-- 头部通知栏 -->
        <%@ include file="../header.jsp" %>

        <div class="page-title">
            <div class="title-env">
                <h1 class="title">权限设置</h1>

                <p class="description">Plain text boxes, select dropdowns and other basic form elements</p>
            </div>

            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li>
                        <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
                    </li>
                    <li>
                        <a href="list.cc">用户组列表</a>
                    </li>
                    <li class="active">
                        <strong>权限设置</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Default form inputs</h3>

                <div class="panel-options">
                    <a href="#" data-toggle="panel">
                        <span class="collapse-icon">&ndash;</span>
                        <span class="expand-icon">+</span>
                    </a>
                </div>
            </div>
            <div class="panel-body">
                <form role="form" id="form1" method="post" class="validate form-horizontal" action="${action}">
                    <div class="form-group">
                        <label class="control-label col-sm-3">用户组名称</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="name" value="${role.name}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">简介</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="desc" value="${role.desc}"/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-lg-3 control-label" >权限设置</label>
                        <div class="col-lg-9">
                            <select multiple="multiple">
                                <c:set var="permission" value="${permissions}" scope="request"/>
                                <c:set var="level" value="0"/>
                                <%@include file="permissionTree.jsp"%>
                            </select>
                            <%--<select id="demo1" name="selectMultiple" multiple="multiple">
                                <c:forEach var="node1" items="${ permissions }">
                                    <c:if test="${empty node1.childPermission}">
                                        <option value="${node1.id }" data-section="${node1.name}" <c:if test="${node1.select == 1}">selected="selected"</c:if> data-index="${node1.lev}">${node1.name }</option>
                                    </c:if>
                                    <c:if test="${ not empty node1.childPermission}">
                                        <c:forEach var="node2" items="${ node1.childPermission }">
                                            <c:if test="${empty node2.childPermission}">
                                                <option value="${node2.id }" data-section="${node1.name}/${node2.name}" <c:if test="${node2.select == 1}">selected="selected"</c:if> data-index="${node2.lev}">${node2.name }</option>
                                            </c:if>
                                            <c:if test="${ not empty node2.childPermission}">
                                                <c:forEach var="node3" items="${ node2.childPermission }">
                                                    <option value="${node3.id }" data-section="${node1.name}/${node2.name}" <c:if test="${node3.select == 1}">selected="selected"</c:if> data-index="${node3.lev}">${node3.name }</option>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </select>--%>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-3">店主姓名</label>

                        <p>
                            <label class="radio-inline">
                                <input type="radio" name="radio" class="cbr cbr-secondary selectClient"
                                       <c:if test="${empty clients}">disabled</c:if>>
                                已存在该客户
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="radio" class="cbr cbr-secondary addClient" checked>
                                增添至新客户
                            </label>
                        </p>
                        <script type="text/javascript">
                            $(".addClient").click(function () {
                                // document.getElementById("addClient").style.display = "block";
                                //$(".selectClient").attr("checked",false);
                                $("#addClient").show();
                                $("#selectClient").hide();
                                $(".addToShow").show();
                                $(".addToShow input").attr("data-validate", "required");
                                $('input[name="name"]').attr("data-validate", "required");
                            })
                            $(".selectClient").click(function () {
                                // document.getElementById("addClient").style.display = "block";
                                $(".addClient").attr("checked", false);
                                $("#selectClient").show();
                                $("#addClient").hide();
                                $(".addToShow").hide();
                                $(".addToShow input").removeAttr("data-validate");
                                $('input[name="name"]').removeAttr("data-validate");

                            })

                        </script>
                    </div>
                    <div class="form-group" id="addClient">
                        <label class="control-label col-sm-3"></label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="name" data-validate="required,maxlength[32]"
                                   data-message-required="" placeholder="请输入店主名称"/>
                        </div>
                    </div>
                    <div class="form-group" id="selectClient" style="display: none">
                        <label class="control-label col-sm-3"></label>

                        <div class="col-sm-5">
                            <select class="form-control" id="s2example-1" name="ownerId">
                                <option></option>
                                <c:forEach items="${clients}" var="client">
                                    <option value="${client.id}">${client.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <script type="text/javascript">
                            jQuery(document).ready(function ($) {
                                $("#s2example-1").select2({
                                    placeholder: 'Select your country...',
                                    allowClear: true
                                }).on('select2-open', function () {
                                    // Adding Custom Scrollbar
                                    $(this).data('select2').results.addClass('overflow-hidden').perfectScrollbar();
                                });

                            });
                        </script>
                    </div>


                    <div class="form-group addToShow">
                        <label class="control-label col-sm-3">店主手机号</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="mobile1"
                                   data-validate="number,required,rangelength[8][11]" data-message-required=""
                                   placeholder="请输入店主手机号&hellip;"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-3">店铺电话</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="storePhone" data-validate="number"
                                   placeholder="请输入店铺电话"/>
                        </div>
                    </div>

                    <%--<div class="form-group">
                        <label class="control-label col-sm-3">店铺地址</label>
                        <div class="col-sm-3 " id="distpicker">
                            <div style="position: relative;">
                                <input name="province" id="city-picker" class="form-control" readonly type="text" value="河南省/许昌市/魏都区" data-toggle="city-picker">
                            </div>
                        </div>
                        <div class="col-sm-3 ">
                            <input type="text" class="form-control" name="street" data-validate="required" placeholder="请输入详细地址（街道号）" />
                        </div>
                    </div>--%>

                    <div class="form-group">
                        <label class="control-label col-sm-3">店铺地址</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="address" data-validate="required" readonly
                                   placeholder="点击图片选择商铺地址"/>
                            <input type="hidden" name="lng"/>
                            <input type="hidden" name="lat">
                        </div>
                        <div class="panel-body no-padding">
                            <div id="map" style="height: 450px; width: 100%"></div>

                            <script type="text/javascript">
                                //初始化地图对象，加载地图
                                var map = new AMap.Map("map", {
                                    resizeEnable: true,
                                    zoom: 15,
                                });

                                map.on('click', function (e) {
                                    // alert('您在[ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ]的位置点击了地图！');
                                    document.forms[0].lng.value = e.lnglat.getLng();
                                    document.forms[0].lat.value = e.lnglat.getLat();
                                    var lnglatXY = [e.lnglat.getLng(), e.lnglat.getLat()]
                                    //逆地理编码
                                    AMap.service('AMap.Geocoder', function () {//回调函数
                                        //实例化Geocoder
                                        geocoder = new AMap.Geocoder({
                                            city: "010"//城市，默认：“全国”
                                        });
                                        //TODO: 使用geocoder 对象完成相关功能
                                    })
                                    geocoder.getAddress(lnglatXY, function (status, result) {
                                        if (status === 'complete' && result.info === 'OK') {
                                            document.forms[0].address.value = result.regeocode.formattedAddress;
                                        }
                                    });


                                });

                            </script>

                            <%--Todo--%>
                            <%--异步加载，异步加载可以减少API引用之后脚本的等待执行，https建议使用异步方式：--%>

                        </div>
                    </div>

                    <div class="form-group" align="center">
                        <button type="submit" class="btn btn-success">保存</button>
                        <button type="reset" class="btn btn-white">重置</button>
                    </div>

                    <script type="text/javascript">
                        $("#form1").submit(function () {
                            if ($(".addClient").is(":checked")) {
                                $('[name="ownerId"]').val('');
                            }
                            if ($(".selectClient").is(":checked")) {
                                $('input[name="name"]').val('');
                                $('input[name="mobile1"]').val('');
                            }
                        })

                    </script>

                </form>

            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>

<!-- Imported styles on this page -->
<link rel="stylesheet" href="/assets/js/datatables/dataTables.bootstrap.css">
<script src="/assets/js/datatables/js/jquery.dataTables.min.js"></script>


<!-- Bottom Scripts -->
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/TweenMax.min.js"></script>
<script src="/assets/js/resizeable.js"></script>
<script src="/assets/js/joinable.js"></script>
<script src="/assets/js/xenon-api.js"></script>
<script src="/assets/js/xenon-toggles.js"></script>


<!-- Imported scripts on this page -->
<script src="/assets/js/datatables/dataTables.bootstrap.js"></script>
<script src="/assets/js/datatables/yadcf/jquery.dataTables.yadcf.js"></script>
<script src="/assets/js/datatables/tabletools/dataTables.tableTools.min.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="/assets/js/xenon-custom.js"></script>

</body>
</html>



