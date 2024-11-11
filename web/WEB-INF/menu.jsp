<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Game</a>
            <%--<a class="navbar-brand" href="/user/logout.do">Logout</a>--%>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/game/findAll.do">RockPaperScissors</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a class="navbar-brand">${sessionScope.username}</a></li>
            <li><a class="navbar-brand" href="/user/logout.do">Logout</a></li>
        </ul>
    </div>
</nav>