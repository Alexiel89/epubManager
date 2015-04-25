<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $("#xhtmlfiles").fileinput({
            allowedFileExtensions: ["xhtml", "xht", "xml", "html", "htm"],
            elErrorContainer: "#errorxhtmlfiles"
        });
        $("#cssfiles").fileinput({
            allowedFileExtensions: ["css"],
            elErrorContainer: "#errorcssfiles"
        });
        $("#imagefiles").fileinput({
            previewFileType: "image",
            elErrorContainer: "#errorimagefiles"
        });
    });
</script>
<div class="row">
    <div class="col-lg-12">
	<h1 class="page-header">
            <spring:message code="uploadresources.heading.create" />
	</h1>
    </div>
</div>
<form:form action="${pageContext.request.contextPath}${requestScope.action}" method="POST" enctype="multipart/form-data">
    <div class="well">
        <div class="form-group">
            <label for="xhtmlfiles"><spring:message code="uploadresources.xhtml" /></label>
            <input id="xhtmlfiles" name="xhtmlfiles" type="file" multiple="true" class="file-loading" data-show-upload="false" accept="text/html, application/xhtml+xml" required />
            <div id="errorxhtmlfiles" class="help-block"></div>
        </div>
    </div>
    <div class="well">
        <div class="form-group">
            <label for="cssfiles"><spring:message code="uploadresources.css" /></label>
            <input id="cssfiles" name="cssfiles" type="file" multiple="true" class="file-loading" data-show-upload="false" accept="text/css" />
            <div id="errorcssfiles" class="help-block"></div>
        </div>
    </div>
    <div class="well">
        <div class="form-group">
            <label for="imagefiles"><spring:message code="uploadresources.image" /></label>
            <input id="imagefiles" name="imagefiles" type="file" multiple="true" class="file-loading" data-show-upload="false" accept="image/*" />
            <div id="errorimagefiles" class="help-block"></div>
        </div>
    </div>
    <div class="form-group">
	<button type="submit" class="btn btn-primary">
            <spring:message code="common.next" />
	</button>
    </div>
</form:form>