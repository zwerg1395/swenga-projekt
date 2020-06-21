<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<layout:page-container-empty title="Home" activePage="home">



    <div class="container">

        <h1>Welcome to our Cloud Service!</h1>
        <div class="col-md-8 col-md-offset-2">
                <%--@elvariable id="user" type="at.fhj.ima.swengaprojekt.cloud.entity.User"--%>
            <form:form modelAttribute="user" class="needs-validation form-horizontal" method="post" action="registerUser" novalidate="novalidate">
                <fieldset>
                    <! ---------------- buttons ---------------- -->
                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">
                            <a href="/register" class="btn btn-primary">Register</a>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">
                            <a href="/cloud?username=user" class="btn btn-primary">Login</a>
                        </div>
                    </div>
                </fieldset>



            </form:form>
        </div>
    </div>
</layout:page-container-empty>
