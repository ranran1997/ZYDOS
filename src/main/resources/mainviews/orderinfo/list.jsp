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

    <title>ZYDOS - 订单列表</title>

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

</head>
<body class="page-body">

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
                <h1 class="title">订单列表</h1>

                <p class="description">Plain text boxes, select dropdowns and other basic form elements</p>
            </div>

            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li>
                        <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
                    </li>
                    <li class="active">
                        <strong>订单列表</strong>
                    </li>
                </ol>
            </div>
        </div>
        <!-- Removing search and results count filter -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Removing search and results count filter.</h3>

                <div class="panel-options">
                    <%--<a href="#" data-toggle="panel">
                        <span class="collapse-icon">&ndash;</span>
                        <span class="expand-icon">+</span>
                    </a>
                    <a href="#" data-toggle="remove">
                        &times;
                    </a>--%>
                    <a href="add.cc" class="btn btn-info btn-sm btn-icon icon-left">
                        新建
                    </a>
                </div>
            </div>
            <div class="panel-body">

                <script type="text/javascript">

                    jQuery(document).ready(function ($) {
                        $("#example-1").dataTable({
                            /* dom: "t" + "<'row'<'col-xs-6'i><'col-xs-6'p>>",*/
                            aaSorting: [ [0,'desc'], [2,'desc'] ],
                            aoColumns: [
                                null,
                                null,
                                null,
                                null,
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
                                'lengthMenu': '每页 _MENU_ 件',
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
                    $(function(){
                        $(".parseTime").each(function () {
                            var timestamp = $(this).data().timestamp;
                            $(this).text(new Date(parseInt(timestamp)).format("yyyy-MM-dd HH:mm"));
                        });

                    })
                    function cancel(data){
                        if(confirm("确认取消该订单吗？")){
                            window.location.href="cancel.cc?id="+data;
                        }
                        return false;
                    }
                    function done(data){
                        window.location.href="done.cc?id="+data;
                    }

                </script>

                <table class="table table-bordered table-striped" id="example-1" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>订单序号</th>
                        <th>店铺名称</th>
                        <th>下单时间</th>
                        <th>配送时间</th>
                        <th>订单状态</th>
                        <th>配送负责人</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody class="middle-align">
                    <c:forEach var="orderInfo" items="${orderInfos}">
                        <tr>
                            <td>${orderInfo.id}</td>
                            <td>${orderInfo.storeName}</td>
                            <td><fmt:formatDate value="${orderInfo.addTime}" pattern="yyyy-MM-dd HH-mm-ss"/> </td>
                                <%--<td class="parseTime" data-timestamp="${orderInfo.addTime}000"></td>--%>
                                <%--<td><fmt:formatDate value="${orderInfo.addTime}" pattern="yyyy-MM-dd"/></td>--%>
                            <td><fmt:formatDate value="${orderInfo.deliverTime}" pattern="yyyy-MM-dd HH-mm-ss"/> </td>
                            <td>${orderInfo.orderStatus == 1 ?"已配送":orderInfo.orderStatus== 0 ?"未配送":orderInfo.orderStatus == 2 ? "已取消" :"状态异常"}</td>
                            <td>${orderInfo.delivererName}</td>
                            <td>
                                <%--<c:choose>
                                    <c:when test="${orderInfo.orderStatus eq 0}">
                                        <a href="edit.cc" class="btn btn-secondary btn-sm btn-icon">
                                            编辑
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm btn-icon" onclick="cancel(${orderInfo.id});">
                                            取消订单
                                        </a>
                                        <a href="#" class="btn btn-secondary btn-sm btn-icon" onclick="done(${orderInfo.id});">
                                            配送完成
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        不可操作
                                    </c:otherwise>
                                </c:choose>--%>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>
    </div>


</div>

<%@include file="../../sysviews/footer.jsp"%>


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



