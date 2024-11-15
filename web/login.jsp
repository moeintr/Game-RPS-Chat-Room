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
    <div class="panel-heading">LOGIN</div>
    <div class="panel-body">
      <form action="/user/login.do">
        <label for="username" style="width: 100%; text-align: center">Username</label>
        <input type="text" id="username" name="username" class="form-control"/>
        <br/>
        <label for="password" style="width: 100%; text-align: center">Password</label>
        <input type="password" id="password" name="password" class="form-control"/>
        <br/>
        <i id="showPassword" style="width: 100%; text-align: center" class="fas fa-eye-slash form-control" onclick="changeType()">ShowPassword</i>
        <br/>
        <br/>
        <input type="submit" style="width: 100%" value="Login" class="btn btn-primary"/>
        <br/>
        <br/>
        <a type="button" class="btn btn-primary" style="width: 100%" href="/signup.jsp">Signup</a>
      </form>
    </div>
  </div>
</div>
<script>
  const passwordInput = document.getElementById("password");
  const showPasswordIcon = document.getElementById("showPassword");

  function changeType() {
    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      showPasswordIcon.classList.remove("fa-eye-slash");
      showPasswordIcon.classList.add("fa-eye");
    } else {
      passwordInput.type = "password";
      showPasswordIcon.classList.remove("fa-eye");
      showPasswordIcon.classList.add("fa-eye-slash");
    }
  }
</script>
</body>
</html>
