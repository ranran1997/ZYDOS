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

    <title>ZYDOS - 个人中心</title>

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
            margin-bottom: 30px;
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
<%--页面loading--%>
<%--<div class="page-loading-overlay">
    <div class="loader-2"></div>
</div>--%>
<%--页面loading--%>

<div class="page-title">
    <div class="title-env">
        <h1 class="title">个人中心</h1>

        <p class="description">User profile and story timeline</p>
    </div>

    <div class="breadcrumb-env">
        <ol class="breadcrumb bc-1">
            <li>
                <a href="dashboard-1.html"><i class="fa-home"></i>Home</a>
            </li>
            <li class="active">
                <strong>Profile</strong>
            </li>
        </ol>
    </div>
</div>
<section class="profile-env">
    <div class="row">
        <div class="col-sm-3">
            <!-- User Info Sidebar -->
            <div class="user-info-sidebar">
                <a href="#" class="user-img">
                    <img src="/assets/images/user-4.png" alt="user-img"
                         class="img-cirlce img-responsive img-thumbnail"/>
                </a>
                <a href="#" class="user-name">

                    <span class="user-status is-online"></span>
                </a>
							<span class="user-title">
								<strong>${user.name}</strong>
							</span>
                            <span class="user-title">
								${user.mobile}
							</span>
                <hr/>
                <ul class="list-unstyled user-info-list">
                    <li>
                        <i class="fa-home"></i>
                        您是我们的：${user.role}
                    </li>
                    <li>
                        <i class="fa-briefcase"></i>
                        <a href="/store/list.cc">直接管辖店铺：${user.storeNum}</a>
                    </li>
                    <li>
                        <i class="fa-graduation-cap"></i>
                        入职时间：${user.joinTime}
                    </li>
                </ul>
                <hr/>
                <ul class="list-unstyled user-friends-count">
                    <li>
                        <span>${storeNum}</span>
                        管辖店铺
                    </li>
                    <li>
                        <span>${orderNum}</span>
                        历史配送量
                    </li>
                </ul>
                <button type="button" class="btn btn-success btn-block text-left">
                    Following
                    <i class="fa-check pull-right"></i>
                </button>
            </div>
        </div>

        <div class="col-sm-9">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Standard Bar</h3>

                    <div class="panel-options">
                        <a href="#" data-toggle="panel">
                            <span class="collapse-icon">&ndash;</span>
                            <span class="expand-icon">+</span>
                        </a>
                        <a href="#" data-toggle="remove">
                            &times;
                        </a>
                    </div>
                </div>
                <div class="panel-body">
                    <script type="text/javascript">
                        jQuery(document).ready(function ($) {
                            $("#bar-1").dxChart({
                                dataSource: [
                                    {day: "Monday", sales: 3},
                                    {day: "Tuesday", sales: 2},
                                    {day: "Wednesday", sales: 3},
                                    {day: "Thursday", sales: 4},
                                    {day: "Friday", sales: 6},
                                    {day: "Saturday", sales: 11},
                                    {day: "Sunday", sales: 4}
                                ],

                                series: {
                                    argumentField: "day",
                                    valueField: "sales",
                                    name: "Sales",
                                    type: "bar",
                                    color: '#68b828'
                                }
                            });

                            $("#bar-1-randomize").on('click', function (ev) {
                                ev.preventDefault();

                                $('#bar-1').dxChart('instance').option('dataSource', [
                                    {day: "Monday", sales: between(1, 25)},
                                    {day: "Tuesday", sales: between(1, 25)},
                                    {day: "Wednesday", sales: between(1, 25)},
                                    {day: "Thursday", sales: between(1, 25)},
                                    {day: "Friday", sales: between(1, 25)},
                                    {day: "Saturday", sales: between(1, 25)},
                                    {day: "Sunday", sales: between(1, 25)}
                                ]);
                            });
                        });

                        function between(randNumMin, randNumMax) {
                            var randInt = Math.floor((Math.random() * ((randNumMax + 1) - randNumMin)) + randNumMin);

                            return randInt;
                        }
                    </script>
                    <div id="bar-1" style="height: 440px; width: 100%;"></div>
                    <br/>
                    <a href="#" id="bar-1-randomize" class="btn btn-primary btn-small">Randomize</a>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Line Charts</h3>

                    <div class="panel-options">
                        <a href="#" data-toggle="panel">
                            <span class="collapse-icon">&ndash;</span>
                            <span class="expand-icon">+</span>
                        </a>
                        <a href="#" data-toggle="remove">
                            &times;
                        </a>
                    </div>
                </div>
                <div class="panel-body">
                    <script type="text/javascript">
                        jQuery(document).ready(function ($) {
                            var dataSource = [
                                {year: 1950, europe: 546, americas: 332, africa: 227},
                                {year: 1960, europe: 705, americas: 417, africa: 283},
                                {year: 1970, europe: 856, americas: 513, africa: 361},
                                {year: 1980, europe: 1294, americas: 614, africa: 471},
                                {year: 1990, europe: 321, americas: 721, africa: 623},
                                {year: 2000, europe: 730, americas: 1836, africa: 1297},
                                {year: 2010, europe: 728, americas: 935, africa: 982},
                                {year: 2020, europe: 721, americas: 1027, africa: 1189},
                                {year: 2030, europe: 704, americas: 1110, africa: 1416},
                                {year: 2040, europe: 680, americas: 1178, africa: 1665},
                                {year: 2050, europe: 650, americas: 1231, africa: 1937}
                            ];

                            $("#bar-3").dxChart({
                                dataSource: dataSource,
                                commonSeriesSettings: {
                                    argumentField: "year"
                                },
                                series: [
                                    {valueField: "europe", name: "Europe", color: "#40bbea"},
                                    {valueField: "americas", name: "Americas", color: "#cc3f44"},
                                    {valueField: "africa", name: "Africa", color: "#8dc63f"}
                                ],
                                argumentAxis: {
                                    grid: {
                                        visible: true
                                    }
                                },
                                tooltip: {
                                    enabled: true
                                },
                                title: "Historic, Current and Future Population Trends",
                                legend: {
                                    verticalAlignment: "bottom",
                                    horizontalAlignment: "center"
                                },
                                commonPaneSettings: {
                                    border: {
                                        visible: true,
                                        right: false
                                    }
                                }
                            });
                        });
                    </script>
                    <div id="bar-3" style="height: 400px; width: 100%;"></div>
                </div>
            </div>

        </div>


    </div>

</section>


<!-- Imported scripts on this page -->
<script src="/assets/js/devexpress-web-14.1/js/globalize.min.js"></script>
<script src="/assets/js/devexpress-web-14.1/js/dx.chartjs.js"></script>


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
