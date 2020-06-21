<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<layout:page-container-cloud title="Profile Settings" activePage="changeProfile">



    <div class="container">

        <h1>Profile Settings</h1>
        <div class="col-md-8 col-md-offset-2">
                <%--@elvariable id="user" type="at.fhj.ima.swengaprojekt.cloud.entity.User"--%>
            <form:form modelAttribute="user" class="needs-validation form-horizontal" method="post" action="updateProfile" novalidate="novalidate">
                <fieldset>

                    <! ---------------- Subscription ---------------- -->
                    <h2>Manage your Subscription</h2>

                    <div class="form-group">
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="subscription" class="form-check-input" name="inlineRadioOptions" id="inlineRadio1" value="1"/>
                        <label class="form-check-label" for="inlineRadio1">Starter (50 Byte)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="subscription" class="form-check-input" name="inlineRadioOptions" id="inlineRadio2" value="2"/>
                        <label class="form-check-label" for="inlineRadio2">Advanced (10 KByte)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="subscription" class="form-check-input" name="inlineRadioOptions" id="inlineRadio3" value="3"/>
                        <label class="form-check-label" for="inlineRadio3">Pro (150 MByte)</label>
                    </div>
                        <form:errors path="subscription" cssClass="invalid-feedback d-block"/>
                    </div>

                    <! ---------------- Current Password ---------------- -->
                    <h2>Manage your Password</h2>
                    <div class="form-group">
                        <label for="inputPassword" class="col-md-2 control-label">Current Password*</label>
                        <div class="col-md-10">
                            <form:input id="inputPasswordOld" path="passwordOld" type="password" class="form-control" required="required"/>
                            <form:errors path="passwordOld" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- New Password ---------------- -->
                    <div class="form-group">
                        <label for="inputPassword" class="col-md-2 control-label">New Password*</label>
                        <div class="col-md-10">
                            <form:input id="inputPassword" path="password" type="password" class="form-control" required="required"/>
                            <form:errors path="password" cssClass="invalid-feedback d-block"/>

                        </div>
                    </div>

                    <! ---------------- Repeat Password ---------------- -->
                    <div class="form-group">
                        <label for="inputPasswordRepeat" class="col-md-2 control-label">Repeat Password*</label>
                        <div class="col-md-10">
                            <input id="inputPasswordRepeat" type="password" class="form-control" required="required"/>
                        </div>
                    </div>

                    <! ---------------- buttons ---------------- -->
                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">
                            <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>

                </fieldset>



            </form:form>
        </div>
    </div>
    <script>
        var password = document.getElementById("inputPassword")
            , confirm_password = document.getElementById("inputPasswordRepeat");
        var subButton = document.getElementById("submitButton");
        var passwordOld = document.getElementById("inputPasswordOld")

        function validate(){
            if(password.value != confirm_password.value || password.value == "") {
                subButton.disabled = true;
            } else {
                subButton.disabled = false;
            }
        }

        passwordOld.onkeyup = validate;
        password.onkeyup = validate;
        confirm_password.onkeyup = validate;
    </script>
</layout:page-container-cloud>
