<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${pageContext.request.contextPath}"><spring:message code="frontend.menu.home" /></a>
	</div>
        <!-- Collect the nav links, forms, and other content for toggling -->
	<div class="collapse navbar-collapse"
		id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav navbar-right">
                    <li><a href="${pageContext.request.contextPath}/packager/create"><spring:message code="frontend.menu.packager" /></a></li>
                    <li><a href="${pageContext.request.contextPath}/ePubBiblio/upload-epub"><spring:message code="frontend.menu.biblio" /></a></li>
        	</li>
	</div>
	<!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>