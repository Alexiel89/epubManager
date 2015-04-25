<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tiles:importAttribute scope="request" />
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><spring:message code="common.title" /></title>

<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/resources/frontend/img/favicon.png"/>

<!-- Bootstrap Core CSS -->
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${pageContext.request.contextPath}/resources/modern-business/modern-business.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="${pageContext.request.contextPath}/resources/datatables/css/dataTables.bootstrap.css"
    rel="stylesheet">

<!-- Select2 CSS -->
<link
    href="${pageContext.request.contextPath}/resources/select2/select2.css"
    rel="stylesheet">
<link
    href="${pageContext.request.contextPath}/resources/select2/select2-bootstrap.css"
    rel="stylesheet">
 
 <!-- Datepicker CSS -->
<link
    href="${pageContext.request.contextPath}/resources/datepicker/css/datepicker.css"
    rel="stylesheet" type="text/css">

<!-- File input -->
<link href="${pageContext.request.contextPath}/resources/fileinput/css/fileinput.min.css" 
    media="all" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- jQuery Version 1.11.0 -->
<script
	src="${pageContext.request.contextPath}/resources/jquery/jquery-2.1.0.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables JavaScript -->
<script
	src="${pageContext.request.contextPath}/resources/datatables/js/jquery.dataTables.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/datatables/js/dataTables.bootstrap.js"></script>
        
<script	src="${pageContext.request.contextPath}/resources/datatables/my.js"></script>
        
<!-- Chosen JavaScript -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/select2/select2.min.js"></script>
        
<!-- Datepicker - Bootstrap -->
<script src="${pageContext.request.contextPath}/resources/datepicker/js/bootstrap-datepicker.js"></script>

<!-- File input -->
<script src="${pageContext.request.contextPath}/resources/fileinput/js/fileinput.min.js" type="text/javascript"></script>

</head>

<body>

	<!-- Navigation -->
	<tiles:insertAttribute name="menu" />
	
	<!-- Page Content -->
	<div class="container">

		<tiles:insertAttribute name="body" />

	</div>
	<!-- /.container -->

	<div class="container">

		<hr>
		<!-- Footer -->
		<tiles:insertAttribute name="footer" />

	</div>
	<!-- /.container -->

</body>

</html>