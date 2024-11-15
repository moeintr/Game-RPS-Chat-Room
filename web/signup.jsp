<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: Moein_Torogh
  Date: 11/6/2024
  Time: 10:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/head.jsp"/>
</head>
<body>
<%--<jsp:include page="/WEB-INF/menu.jsp"/>--%>

<div id="container" class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">SIGNUP</div>
        <div class="panel-body">
            <form action="/user/signup.do" method="post" onsubmit="return validateForm()">
                <label for="username" style="width: 100%; text-align: center">Username</label>
                <input type="text" id="username" minlength="4" name="username" class="form-control"/>
                <br/>
                <label for="password" style="width: 100%; text-align: center">Password</label>
                <input type="password" id="password" minlength="8" name="password" class="form-control"/>
                <br/>
                <label for="repassword" style="width: 100%; text-align: center">RepeatPassword</label>
                <input type="password" id="repassword" minlength="8" name="repassword" class="form-control"/>
                <br/>
                <i id="showRePasswords" style="width: 100%; text-align: center" class="fas fa-eye-slash form-control" onclick="changeType()">ShowPasswords</i>
                <br/>
                <br/>
                <input type="submit" style="width: 100%" value="Signup" class="btn btn-primary"/>
                <br/>
                <br/>
                <a type="button" class="btn btn-primary" style="width: 100%" href="/login.jsp">Login</a>
            </form>
        </div>
    </div>
</div>
<script>
    function validateForm() {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const repassword = document.getElementById("repassword").value;

        if (password != repassword) {
            alert("Passwords do not match. Please try again.");
            return false;
        }
        if (password == "" || repassword == "") {
            alert("Passwords should be filled.");
            return false;
        }
        if (username == "") {
            alert("Username should be filled.");
            return false;
        }
        return true;
    }

    const passwordInput = document.getElementById("password");
    const repasswordInput = document.getElementById("repassword");
    const showPasswordsIcon = document.getElementById("showPasswords");

    function changeType() {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            repasswordInput.type = "text";
            showPasswordIcon.classList.remove("fa-eye-slash");
            showPasswordIcon.classList.add("fa-eye");
        } else {
            passwordInput.type = "password";
            repasswordInput.type = "password";
            showPasswordIcon.classList.remove("fa-eye");
            showPasswordIcon.classList.add("fa-eye-slash");
        }
    }
</script>
</body>
</html>
