<%@ attribute name="path" required="true" %>
<%@ attribute name="value" required="true" type="java.lang.Object" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<input type="file" multiple="true" class="customfileupload form-control"
       data-path="${path}" data-csrftokenname="${_csrf.parameterName}" data-csrftoken="${_csrf.token}"/>
<form:errors path="${path}" cssClass="invalid-feedback d-block"/>

<table role="presentation" class="table table-striped">
    <tbody class="filescontainer" id="tbodyList">
    <c:forEach var="file" items="${value}" varStatus="status">
        <tr>
            <td>
                <form:hidden path="${path}[${status.index}].id"/>
                <form:hidden path="${path}[${status.index}].contentType"/>
                <form:hidden path="${path}[${status.index}].size"/>
                <form:hidden path="${path}[${status.index}].originalFileName"/>
                <a href="/file/${file.id}" class="btn btn-link">${file.originalFileName}</a>
            </td>
            <td>
                    <input type="hidden" name="id" value="<c:out value="${file.id}"/>">

                <input id="renameInput" type="text" class="form-control" value="${file.originalFileName}" onchange="renameFun(this.value, ${file.id})"/>
                <a href="/file/rename/${file.id}?fileName=${linkExtension}" class="btn btn-danger" id="renameLink${file.id}">Rename</a>
                <a href="/file/delete/${file.id}" class="btn btn-danger">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<script>
    function renameFun(val, id){
        var link = "/file/rename/" + id + "?fileName=" + val;
        document.getElementById("renameLink" + id).setAttribute("href", link);
    }
</script>