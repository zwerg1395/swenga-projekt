<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="file" tagdir="/WEB-INF/tags/file" %>

<layout:page-container-cloud title="Cloud" activePage="cloud">

    <h1>Cloud Service</h1>

    <%--@elvariable id="user" type="at.fhj.ima.cloudUser.cloudUsermanager.entity.User"--%>
        <form:form modelAttribute="user" class="needs-validation form-horizontal" method="post" action="saveFile" novalidate="novalidate" id="mainForm">

        <div class="col-md-4">
            <input class="form-control mr-sm-2" type="text" id="searchInput" onkeyup="searchFunction()" placeholder="Search for names..">
        </div>

        <div class="form-group">
            <label for="inputDate" class="col-md-2 control-label">Files</label>
            <div class="col-md-10">
                <file:file path="files" value="${user.files}"/>
            </div>
        </div>

    </form:form>

    <script>

        if (window.location.href.includes("error=storage")){
            alert("Not enough storage space. Delete existing files or change subscription");
        }


        function searchFunction() {

            var input, filter, a, i, txtValue, tr, tbody;
            input = document.getElementById('searchInput');
            filter = input.value.toUpperCase();
            tbody = document.getElementById("tbodyList");
            tr = tbody.getElementsByTagName('tr');

            for (i = 0; i < tr.length; i++) {
                a = tr[i].getElementsByTagName("a")[0];
                txtValue = a.textContent || a.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].hidden = false;
                } else {
                    tr[i].hidden = true;
                }
            }
        }
        //function renameFun(val, id){
        //    var link = "/file/rename/" + id + "?fileName=" + val
        //    document.getElementById("renameLink").setAttribute("href", link)
        //}

    </script>

</layout:page-container-cloud>
