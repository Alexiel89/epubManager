<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">
            Lista dei file XHTML
        </h1>
    </div>
</div>
<c:if test="${not empty sortingXhtmlFiles}">
<table class="table table-hover">
    <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Path</th>
            <th>Ordine</th>
            <th>Azioni</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${sortingXhtmlFiles}" var="sortingXhtmlFile">
        <tr>
            <th scope="row">${sortingXhtmlFile.id}</th>
            <td>${sortingXhtmlFile.name}</td>
            <td>${sortingXhtmlFile.path}</td>
            <td>${sortingXhtmlFile.index}</td>
            <td><a href="${pageContext.request.contextPath}/packager/orderresources/update?id=${sortingXhtmlFile.id}">Modifica</a> - <a href="${pageContext.request.contextPath}/packager/orderresources/delete?id=${sortingXhtmlFile.id}">Elimina</a></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</c:if>