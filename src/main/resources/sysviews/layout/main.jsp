<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="Xenon Boostrap Admin Panel"/>
	<meta name="author" content=""/>

	<title>ZYDOS首页</title>

	<!-- <link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic"> -->
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

<body class="page-body"><!-- add class "sidebar-collapsed" to close sidebar by default, "chat-visible" to make chat appear always -->

<div class="page-loading-overlay">
	<div class="loader-2"></div>
</div>

<!-- 设置 -->
<%@ include file="../setting.jsp" %>

<div class="page-container">
	<!-- 左边侧导航 -->
	<%@ include file="../leftSlider.jsp" %>
	<div class="main-content">
		<%@ include file="../header.jsp" %>
		<div class="content">
			<img src="/assets/images/layouts/home.jpg" style="width:100%;">
		</div>

	</div>
</div>

<%@include file="../footer.jsp"%>

</body>
</html>
