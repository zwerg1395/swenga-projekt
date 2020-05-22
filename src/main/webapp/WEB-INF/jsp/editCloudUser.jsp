<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<layout:page-container title="CloudUsers" activePage="editCloudUser">

    <h1>Modify CloudUser</h1>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
                <%--@elvariable id="cloudUser" type="at.fhj.ima.cloudUser.cloudUsermanager.entity.CloudUser"--%>
            <form:form modelAttribute="cloudUser" class="needs-validation form-horizontal" method="post" action="changeCloudUser" novalidate="novalidate">
                <input type="hidden" name="id" value="<c:out value="${cloudUser.id}"/>">
                <fieldset>
                    <! ---------------- ssn ---------------- -->
                    <div class="form-group">
                        <label for="inputSSN" class="col-md-2 control-label">SSN*</label>
                        <div class="col-md-10">
                            <form:input id="inputSSN" path="ssn" type="number" readonly="${not empty cloudUser.id}" required="required" max="2147483647" cssClass="form-control"/>
                            <form:errors path="ssn" cssClass="invalid-feedback d-block" />
                            <div class="invalid-feedback">
                                SSN may not be larger than 2147483647
                            </div>
                        </div>
                    </div>

                    <! ---------------- first Name ---------------- -->
                    <div class="form-group">
                        <label for="inputFirstName" class="col-md-2 control-label">First Name*</label>
                        <div class="col-md-10">
                            <form:input id="inputFirstName" path="firstName" type="text" class="form-control" required="required"/>
                            <form:errors path="firstName" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- last Name ---------------- -->
                    <div class="form-group">
                        <label for="inputLastName" class="col-md-2 control-label">Last Name*</label>
                        <div class="col-md-10">
                            <form:input id="inputLastName" path="lastName" type="text" class="form-control" required="required"/>
                            <form:errors path="lastName" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- dayOfBirth ---------------- -->
                    <div class="form-group">
                        <label for="inputDate" class="col-md-2 control-label">Date*</label>
                        <div class="col-md-10">
                            <form:input id="inputDate" path="dayOfBirth" type="date" class="form-control" required="required" value="${cloudUser.dayOfBirth}"/>
                            <form:errors path="dayOfBirth" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- department ---------------- -->
                    <div class="form-group">
                        <label for="inputDate" class="col-md-2 control-label">Department</label>
                        <div class="col-md-10">
                            <form:select path="department" cssClass="form-control">
                                <form:option value="">&nbsp;</form:option>
                                <form:options items="${departments}" itemValue="id" itemLabel="name" />
                            </form:select>
                            <form:errors path="department" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- projects ---------------- -->
                    <div class="form-group">
                        <label for="inputDate" class="col-md-2 control-label">Projects</label>
                        <div class="col-md-10">
                            <form:select path="projects" items="${projects}" itemValue="id" itemLabel="name" cssClass="form-control"/>
                            <form:errors path="projects" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- files ---------------- -->
                    <div class="form-group">
                        <label for="inputDate" class="col-md-2 control-label">Files</label>
                        <div class="col-md-10">
                            <file:file path="files" value="${cloudUser.files}"/>
                        </div>
                    </div>

                    <! ---------------- buttons ---------------- -->
                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <a href="listCloudUsers" class="btn btn-default">Cancel</a>
                        </div>
                    </div>

                </fieldset>

                <form:hidden path="version"/>

            </form:form>
        </div>
    </div>
</layout:page-container>
