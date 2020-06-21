    <%@ attribute name="title" required="true" %>
        <%@ attribute name="activePage" required="true" %>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
        <%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
        <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
        <bootstrap:bootstrap-metadata/>
        <title>${title}</title>
        <bootstrap:bootstrap-css/>
        <link rel="stylesheet" href="<c:url value="/css/custom.css"/>">
        </head>
        <body>

        <!-- Messages ----------------------------------------------------------- -->

        <!-- Error message ----------------------------------------------------------- -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">${errorMessage}</div>
        </c:if>
        <!-- Error message ----------------------------------------------------------- -->

        <!-- Warning message ----------------------------------------------------------- -->
        <c:if test="${not empty warningMessage}">
            <div class="alert alert-warning" role="warning">${warningMessage}</div>
        </c:if>
        <!-- Warning message ----------------------------------------------------------- -->

        <!-- successful message ----------------------------------------------------------- -->
        <c:if test="${not empty message}">
            <div class="alert alert-success" role="warning">${message}</div>
        </c:if>
        <!-- successful message ----------------------------------------------------------- -->

        <!-- Messages ----------------------------------------------------------- -->
        <jsp:doBody/>

        <bootstrap:bootstrap-js/>
        <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
        <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
        <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.iframe-transport/1.0.1/jquery.iframe-transport.min.js"></script>
        <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-file-upload/10.28.0/js/jquery.fileupload.min.js"></script>
        <script type="text/javascript" src="<c:url value="/js/custom.js"/>"></script>
        </body>
        </html>
