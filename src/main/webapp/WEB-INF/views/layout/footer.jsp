<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<footer>
    <div class="row">
	<div class="col-lg-12" style="text-align: center;">
            <p>Copyright &copy; <%=Calendar.getInstance().get(Calendar.YEAR)%> -- <spring:message code="common.footer"/></p>
	</div>
    </div>
</footer>