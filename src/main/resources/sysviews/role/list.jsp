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

    <title>ZYDOS - 员工列表</title>

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
<%@ include file="../setting.jsp" %>

<div class="page-container">
    <!-- 左边侧导航 -->
    <%@ include file="../leftSlider.jsp" %>

    <div class="main-content">
        <!-- 头部通知栏 -->
        <%@ include file="../header.jsp" %>

        <div class="page-title">
            <div class="title-env">
                <h1 class="title">用户组列表</h1>

                <p class="description">Plain text boxes, select dropdowns and other basic form elements</p>
            </div>

            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li>
                        <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
                    </li>
                    <li class="active">
                        <strong>用户组列表</strong>
                    </li>
                </ol>
            </div>
        </div>
        <!-- Removing search and results count filter -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Removing search and results count filter</h3>

                <div class="panel-options">
                    <%--<a href="#" data-toggle="panel">
                        <span class="collapse-icon">&ndash;</span>
                        <span class="expand-icon">+</span>
                    </a>
                    <a href="#" data-toggle="remove">
                        &times;
                    </a>--%>
                    <c:forEach var="childPermission" items="${childPermissions}">
                        <c:if test="${childPermission.isButton eq 1 and childPermission.model ne 'single' }">
                            <a href="/${childPermission.path}.cc" class="btn btn-info btn-sm btn-icon icon-left">
                                    ${childPermission.name}
                            </a>
                        </c:if>
                    </c:forEach>

                </div>
            </div>
            <div class="panel-body">

                <script type="text/javascript">
                    /*$(".btn").click(function(){
                     var href = this.attributes[0].value;
                     $.ajax({
                     type: 'POST',
                     url:href,
                     data:null,
                     dataType: "json",
                     success:function(result){
                     if(result.result){
                     window.location.href = result.data;
                     }else{
                     if(result.data != null){
                     alert(result.message);
                     window.location.href = "sysviews/login/login.jsp";
                     }else{
                     alert(result.message);
                     }
                     }
                     },
                     error : function() {
                     alert("网络故障，请稍后重试！")
                     }
                     })
                     });*/

                    function toDelete(data) {
                        if (confirm("确认删除该客户吗？")) {
                            window.location.href = "delete.cc?id=" + data;
                        }
                        return false;
                    }

                    jQuery(document).ready(function ($) {
                        $("#example-1").dataTable({
                            /* dom: "t" + "<'row'<'col-xs-6'i><'col-xs-6'p>>",*/
                            aoColumns: [
                                null,
                                null,
                                {bSortable: false}
                            ],
                            //改变每页显示数据数量
                            aLengthMenu: [
                                [10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]
                            ],
                            bAutoWidth: true,
                            'language': {
                                'emptyTable': '没有数据',
                                'loadingRecords': '加载中...',
                                'processing': '查询中...',
                                'search': '关键词查找:',
                                'lengthMenu': '每页 _MENU_ 条',
                                'zeroRecords': '没有数据',
                                'paginate': {
                                    'first': '第一页',
                                    'last': '最后一页',
                                    'next': '下一页',
                                    'previous': '上一页'
                                },
                                'info': '第 _PAGE_ 页 / 总 _PAGES_ 页',
                                'infoEmpty': '没有数据',
                                'infoFiltered': '(过滤总件数 _MAX_ 条)'
                            }
                        });
                    });
                </script>

                <table class="table table-bordered table-striped" id="example-1" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody class="middle-align">
                    <c:forEach var="role" items="${childRoles}">
                        <tr>
                            <td>${role.name}</td>
                            <td>${role.desc}</td>
                            <td>
                                <c:forEach var="childPermission" items="${childPermissions}">
                                    <c:if test="${childPermission.isButton eq 1 and childPermission.model eq 'single' }">
                                        <a href="/${childPermission.path}.cc" class="btn btn-secondary btn-sm btn-icon">
                                                ${childPermission.name}
                                        </a>
                                    </c:if>
                                </c:forEach>
                                    <%--  <a href="#" class="btn btn-secondary btn-sm btn-icon">
                                          详情
                                      </a>
                                      <a href="#" class="btn btn-secondary btn-sm btn-icon">
                                          修改
                                      </a>
                                      <a href="#" class="btn btn-danger btn-sm btn-icon" onclick="toDelete(${user.id})">
                                          删除
                                      </a>--%>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

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



