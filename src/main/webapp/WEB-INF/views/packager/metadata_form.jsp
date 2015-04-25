<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {

    });
</script>
<div class="row">
    <div class="col-lg-12">
	<h1 class="page-header">
            <spring:message code="metadata.heading.create" />
	</h1>
    </div>
</div>
<form:form modelAttribute="metadata"
    action="${pageContext.request.contextPath}${requestScope.action}"
    method="POST">
    <div class="form-group">
        <label for="identifier"><spring:message code="metadata.identifier" /></label>
        <form:input path="identifier" cssClass="form-control" autofocus="true" />
        <form:errors path="identifier" cssClass="alert-danger" />
    </div>
    <div class="form-group">
	<label for="title"><spring:message code="metadata.title" /></label>
            <form:input path="title" cssClass="form-control" autofocus="true" />
            <form:errors path="title" cssClass="alert-danger" />
    </div>
    <div class="form-group">
	<label for="language"><spring:message code="metadata.language" /></label>
            <form:input path="language" cssClass="form-control" />
            <form:errors path="language" cssClass="alert-danger" />
    </div>
    <div class="form-group">
	<button type="submit" class="btn btn-primary">
		<spring:message code="common.next" />
	</button>
    </div>
</form:form>