<%@ attribute name="file" type="org.springframework.web.multipart.MultipartFile" required="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="d-flex">
    <c:url var="fileUrl" value="/file/${file.name}"/>
    <a href="${fileUrl}" class="btn btn-link" target="_blank">${file.originalFilename}</a>
    <button type="button" class="btn btn-danger" data-file-delete-url="${fileUrl}">Delete</button>
</div>
