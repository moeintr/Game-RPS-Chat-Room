<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/head.jsp"/>
</head>
<body>
<div id="container" class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">Error</div>
        <div class="panel-body">
            ${requestScope.error}
        </div>
    </div>
</div>
</body>
</html>
