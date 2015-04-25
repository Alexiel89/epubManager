<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<br />
<div class="alert alert-danger" role="alert">
	<spring:message code="common.error" />
	<hr />
	<p><strong><spring:message code="exceptions.cause" />: </strong>${errorCause}</p>
	<p><strong><spring:message code="exceptions.message" />: </strong>${errorMessage}</p>
</div>