<%@ attribute name="path" required="true" %>
<%@ attribute name="value" required="true" type="java.lang.Object" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<table role="presentation" class="table table-striped">
    <tbody class="filescontainer"></tbody>
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
                <button type="button" class="btn btn-danger deletefile">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>

<input type="file" multiple="true" class="customfileupload form-control"
       data-path="${path}" data-csrftokenname="${_csrf.parameterName}" data-csrftoken="${_csrf.token}"/>
<form:errors path="${path}" cssClass="invalid-feedback d-block"/>
