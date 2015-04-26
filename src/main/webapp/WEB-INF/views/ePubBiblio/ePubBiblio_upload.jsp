<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $("#eFile").fileinput({
            allowedFileExtensions: ["epub"],
            elErrorContainer: "#errorepubfile"
        });
    });
</script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#epubs_table').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxDataProp": "rows",
			"aoColumns":[
		                {"mData":"id"},
                                {"mData":"titolo"},
		                {"mData":"autore"},
		                {"mData":"lingua"},
                                {"mData":"editore"},
		                {"sName": "id",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a href='${pageContext.request.contextPath}/ePubBiblio/downloadEpub.do?id=" + oObj.aData['id'] + "'><span class='glyphicon glyphicon-download'></span></a>" + " | "+ 
                                              "<a href='${pageContext.request.contextPath}/ePubBiblio/deleteEpub.do?id=" + oObj.aData['id'] + "'><span class='glyphicon glyphicon-trash'></span></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/ePubBiblio/findAllEpubsPaginated.do",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},
            "fnServerParams": addsortparams
		});
	    
	});
</script>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">
            <spring:message code="epubbiblio.heading" />
        </h1>
    </div>
</div>
<form:form method="POST" 
           action="${pageContext.request.contextPath}${requestScope.action}"
           enctype="multipart/form-data">
    <div class="well">
        <div class="form-group">
            <label for="eFile"><spring:message code="epubbiblio.upload.epubfiles" /></label>
            <input name="eFile" id="eFile" type="file" class="file-loading" data-show-upload="false"
            accept=".epub" multiple="multiple" required />
            <div id="errorepubfile" class="help-block"></div>
        </div>
    </div>	
    <div class="form-group">
        <a href="${pageContext.request.contextPath}" class="btn btn-primary">
            <spring:message code="common.home" /></a>
        <button type="submit" class="btn btn-primary">
            <spring:message code="common.next" />
        </button>
    </div>
</form:form>
<!-- STATISTICHE -->   
<div class="row">
    <div class="form-group">
        <div class="col-md-8"><p>Cliccare qui <a href="${pageContext.request.contextPath}/ePubBiblio/epubBiblioXSLT.do">
        <span class="glyphicon glyphicon-stats"></span>
        </a> per visualizzare le statistiche dell'EpubBiblio</p></div>
    </div>
</div>
<!-- TABELLA EPUB PRESENTI NELLA BIBLIOTECA -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
		<spring:message code="epubbiblio.table.heading" />
            </div>
		<div class="panel-body">
                    <div class="table-responsive">
			<table class="table table-striped table-bordered table-hover" id="epubs_table">
                            <thead>
				<tr>
                                    <th>ID</th>
                                    <th><spring:message code="epubbiblio.title" /></th>
                                    <th><spring:message code="epubbiblio.creator" /></th>
                                    <th><spring:message code="epubbiblio.language" /></th>
                                    <th><spring:message code="epubbiblio.publisher" /></th>
                                    <th><spring:message code="common.actions" /></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
			</table>
                    </div>
		</div>
	</div>
    </div>
</div>