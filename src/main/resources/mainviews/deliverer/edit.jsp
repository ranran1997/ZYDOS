<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="Xenon Boostrap Admin Panel"/>
    <meta name="author" content=""/>

    <title>ZYDOS-添加新员工</title>

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
            box-shadow: 0 1px 0 rgba(0,1,1,.08), inset 0 1px 0 #ededed;
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
<body>
<div class="page-title">
    <div class="title-env">
        <h1 class="title">添加新员工</h1>
        <p class="description">Plain text boxes, select dropdowns and other basic form elements</p>
    </div>

    <div class="breadcrumb-env">
        <ol class="breadcrumb bc-1">
            <li>
                <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
            </li>
            <li>
                <a href="list.cc">员工列表</a>
            </li>
            <li class="active">
                <strong>添加新员工</strong>
            </li>
        </ol>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">Default form inputs</h3>

       <%-- <div class="panel-options">
            <a href="#" data-toggle="panel">
                <span class="collapse-icon">&ndash;</span>
                <span class="expand-icon">+</span>
            </a>
        </div>--%>
    </div>
    <div class="panel-body">
        <form role="form" id="form1" method="post" class="validate form-horizontal" action="${action}">
            <div class="form-group">
                <label class="control-label col-sm-3">系统登录帐号</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="jobNumber" data-validate="required,maxlength[32]" data-message-required="" placeholder="建议格式：zydos+数字" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">初始密码</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="password" data-validate="required,maxlength[32]" data-message-required="" placeholder="如：123456" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">姓名</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="name" data-validate="required,maxlength[32]" data-message-required="" placeholder="请输入员工姓名" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">性别</label>
                <p>
                    <label class="radio-inline">
                        <input type="radio" name="sex" class="cbr cbr-secondary selectClient" checked value="1"/>
                        男
                    </label>
                    <label class="radio-inline">
                        <input  type="radio" name="sex" class="cbr cbr-secondary addClient" value="0"/>
                        女
                    </label>
                </p>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">手机号</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="mobile" data-validate="required,number,maxlength[32]" data-message-required="" placeholder="" />
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-sm-3">职位</label>
                <div class="col-sm-5">
                    <select class="form-control" id="s2example-3" name="roleId" data-validate="required">
                        <option></option>
                        <c:set var="roles" value="${roles}" scope="request"/>
                        <c:set var="level" value="0" scope="request"/>
                        <jsp:include page="roleTree.jsp"/>
                    </select>
                </div>

                <script type="text/javascript">
                    jQuery(document).ready(function($)
                    {
                        $("#s2example-3").select2({
                            placeholder: 'Select your country...',
                            allowClear: true
                        }).on('select2-open', function()
                        {
                            // Adding Custom Scrollbar
                            $(this).data('select2').results.addClass('overflow-hidden').perfectScrollbar();
                        });

                    });
                </script>
            </div>

            <%--<div class="form-group">
                <label class="control-label col-sm-3">家庭住址</label>
                <div class="col-sm-3 " id="distpicker">
                    <div style="position: relative;">
                        <input name="address" id="city-picker" class="form-control" readonly type="text" value="河南省/许昌市/魏都区" data-toggle="city-picker">
                    </div>
                </div>
                <div class="col-sm-3 ">
                    <input type="text" class="form-control" name="street" data-validate="required" placeholder="请输入详细地址（街道号）" />
                </div>
            </div>--%>

            <div class="form-group" align="center">
                <button type="submit" class="btn btn-success">保存</button>
                <button type="reset" class="btn btn-white">重置</button>
            </div>

        </form>

    </div>
</div>



<!--地址选择三级联动插件-->
<%--<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet" type="text/css" />--%>
<link href="/assets/js/addresspicker/city-picker.css" rel="stylesheet" type="text/css" />
<script src="/assets/js/addresspicker/jquery.js"></script>
<script src="/assets/js/addresspicker/bootstrap.js"></script>
<script src="/assets/js/addresspicker/city-picker.data.js"></script>
<script src="/assets/js/addresspicker/city-picker.js"></script>
<script src="/assets/js/addresspicker/main.js"></script>

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
