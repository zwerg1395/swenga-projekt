<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<layout:page-container-empty title="Register" activePage="register">



    <div class="container">

        <h1>Create new Account</h1>
        <div class="col-md-8 col-md-offset-2">
                <%--@elvariable id="user" type="at.fhj.ima.swengaprojekt.cloud.entity.User"--%>
            <form:form modelAttribute="user" class="needs-validation form-horizontal" method="post" action="registerUser" novalidate="novalidate">
                <fieldset>
                    <! ---------------- Username ---------------- -->
                    <div class="form-group">
                        <label for="inputUsername" class="col-md-2 control-label">Username*</label>
                        <div class="col-md-10">
                            <form:input id="inputUsername" path="username" type="text" class="form-control"  required="required"/>
                            <form:errors path="username" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- Password ---------------- -->
                    <div class="form-group">
                        <label for="inputPassword" class="col-md-2 control-label">Password*</label>
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
                            <form:errors path="password" cssClass="invalid-feedback d-block"/>
                        </div>
                    </div>

                    <! ---------------- Subscription ---------------- -->
                    <h2>Choose your Subscription</h2>
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

                    <! ---------------- buttons ---------------- -->
                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">
                            <button id="submitButton" type="submit" class="btn btn-primary" disabled>Submit</button>
                        </div>
                    </div>

                </fieldset>



            </form:form>
        </div>
    </div>
    <script>
        var password = document.getElementById("inputPassword")
            , confirm_password = document.getElementById("inputPasswordRepeat");
        var username = document.getElementById("inputUsername");
        var subButton = document.getElementById("submitButton");
        var radioButton1 = document.getElementById("inlineRadio1");
        var radioButton2 = document.getElementById("inlineRadio2");
        var radioButton3 = document.getElementById("inlineRadio3");


        function validate(){
            if(password.value != confirm_password.value || password.value == "" || username.value == "" || (radioButton1.checked == false && radioButton2.checked == false && radioButton3.checked == false)) {
                subButton.disabled = true;
            } else {
                subButton.disabled = false;
            }
        }

        password.onkeyup = validate;
        confirm_password.onkeyup = validate;
        username.onkeyup = validate;
        radioButton1.onchange = validate;
        radioButton2.onchange = validate;
        radioButton3.onchange = validate;

    </script>
</layout:page-container-empty>
