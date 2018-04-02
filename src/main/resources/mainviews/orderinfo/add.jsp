<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="Xenon Boostrap Admin Panel"/>
    <meta name="author" content=""/>

    <title>ZYDOS-新建订单</title>

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


</head>
<body class="page-body">

<%--<div class="page-loading-overlay">
    <div class="loader-2"></div>
</div>--%>

<!-- 设置 -->
<%@ include file="../../sysviews/setting.jsp" %>

<div class="page-container">
    <!-- 左边侧导航 -->
    <%@ include file="../../sysviews/leftSlider.jsp" %>

    <div class="main-content">
        <!-- 头部通知栏 -->
        <%@ include file="../../sysviews/header.jsp" %>
        <div class="page-title">
            <div class="title-env">
                <h1 class="title">新建订单</h1>

                <p class="description">Plain text boxes, select dropdowns and other basic form elements</p>
            </div>

            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li>
                        <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
                    </li>
                    <li class="active">
                        <strong>新建订单</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Default form inputs</h3>

                <%--<div class="panel-options">
                    <a href="#" data-toggle="panel">
                        <span class="collapse-icon">&ndash;</span>
                        <span class="expand-icon">+</span>
                    </a>
                </div>--%>
            </div>
            <div class="panel-body">
                <form role="form" id="form1" method="post" class="validate form-horizontal" action="${action}">
                    <div class="form-group">
                        <label class="control-label col-sm-3">店铺名称</label>

                        <div class="col-sm-5">
                            <select class="form-control" id="storeId" name="storeId" data-validate="required">
                                <option></option>
                                <c:forEach items="${stores}" var="store">
                                    <option value="${store.id}" user="${store.delivererId}">${store.storeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <script type="text/javascript">
                            jQuery(document).ready(function ($) {
                                $("#storeId").select2({
                                    placeholder: '选择店铺...',
                                    allowClear: true
                                }).on('select2-open', function () {
                                    // Adding Custom Scrollbar
                                    $(this).data('select2').results.addClass('overflow-hidden').perfectScrollbar();
                                });
                            });
                        </script>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">配送负责人</label>

                        <div class="col-sm-5">
                            <select class="form-control" id="delivererId" name="delivererId" disabled>
                                <option></option>
                                <c:forEach items="${users}" var="user">
                                    <option value="${user.key}">${user.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $("#storeId").change(function () {
                                    var user = $("#storeId").find("option:selected").attr("user");
                                    $("#delivererId").val(user);
                                });
                            });
                        </script>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">是否紧急</label>

                        <p>
                            <label class="radio-inline">
                                <input type="radio" name="isUrgent" value="1" class="cbr cbr-secondary selectClient">
                                紧急
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isUrgent" value="0" class="cbr cbr-secondary addClient"
                                       checked>
                                不紧急
                            </label>
                        </p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">最晚配送时间</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control datepicker" data-start-date="0d" data-end-date="+2w">

                            <p style="color:#cc3f44;display: none">请选定最晚配送时间</p>
                            <input type="hidden" name="deadline">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">附加信息</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control autogrow" name="extraMsg"
                                   placeholder="是否有配送时间及货物种类要求">
                        </div>
                    </div>
                    <div class="form-group-separator"></div>
                    <div class="form-group" align="center">
                        <button type="submit" class="btn btn-success">保存</button>
                        <button type="reset" class="btn btn-white">重置</button>
                    </div>

                    <script type="text/javascript">
                        $("#form1").submit(function () {
                            var oldDate = $(".datepicker").val();
                            if (isNull(oldDate)) {
                                var newDate = new Date(oldDate);
                                $("[name='deadline']").val(newDate);
                            } else {
                                $(".datepicker").css("border-color", "#cc3f44");
                                $("p").show();
                                return false;
                            }
                        })
                        function isNull(data) {
                            return (data == "" || data == undefined || data == null) ? null : data;
                        }

                    </script>

                </form>

            </div>
        </div>
    </div>
</div>

<%--date picker--%>
<link rel="stylesheet" href="/assets/js/daterangepicker/daterangepicker-bs3.css">
<script src="/assets/js/daterangepicker/daterangepicker.js"></script>
<script src="/assets/js/datepicker/bootstrap-datepicker.js"></script>

<!-- Imported scripts on this page -->
<%--页面表单校验--%>
<script src="/assets/js/jquery-validate/jquery.validate.min.js"></script>

<!-- Imported styles on this page 下拉框-->
<link rel="stylesheet" href="/assets/js/select2/select2.css">
<link rel="stylesheet" href="/assets/js/select2/select2-bootstrap.css">
<%--下拉框搜索选择--%>
<script src="/assets/js/select2/select2.min.js"></script>
<script src="/assets/js/selectboxit/jquery.selectBoxIt.min.js"></script>
<script src="/assets/js/tagsinput/bootstrap-tagsinput.min.js"></script>

<!-- Bottom Scripts -->
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/TweenMax.min.js"></script>
<script src="/assets/js/resizeable.js"></script>
<script src="/assets/js/joinable.js"></script>
<script src="/assets/js/xenon-api.js"></script>
<script src="/assets/js/xenon-toggles.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="/assets/js/xenon-custom.js"></script>

</body>
</html>
