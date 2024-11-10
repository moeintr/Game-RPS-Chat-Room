<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/head.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/menu.jsp"/>

<div id="container" class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">Desktop</div>
        <div class="panel-body">
            <label for="playerOneName" style="width: 100%; text-align: center">PlayerOneName</label>
            <input type="text" id="playerOneName" name="playerOneName" maxlength="100" minlength="8" class="form-control" style="width: 100%"/>
            <br/>
            <label for="playerOneMove" style="width: 100%; text-align: center">PlayerOneMove</label>
            <select id="playerOneMove" name="playerOneMove" class="form-control">
                <option value="ROCK">ROCK</option>
                <option value="PAPER">PAPER</option>
                <option value="SCISSORS">SCISSORS</option>
            </select>
            <br/>
            <div>
                <label for="isVsMachine" style="width: 100%; text-align: center">VsMachine</label>
                <input type="checkbox" id="isVsMachine" name="isVsMachine" value="VsMachine" style="width: 100%; align-self: center; cursor: pointer"/>
            </div>
            <br/>
            <input type="button" style="width: 100%" value="START" class="btn btn-primary" onclick="startGame(this)"/>
            <div class="table-responsive">
                <table id="finishTable" class="table table-striped table-responsive table-hover" style="width: 100%">
                    <tr>
                        <%--<td>gameId</td>
                        <td>playerOneId</td>--%>
                        <td>PlayerOneName</td>
                        <td>PlayerOneMove</td>
                        <%--<td>playerTwoId</td>--%>
                        <td>PlayerTwoName</td>
                        <td>PlayerTwoMove</td>
                        <td> GameResult</td>
                        <td> StartDate</td>
                        <td> FinishDate</td>
                        <td>OPER</td>
                        <td>OPER</td>
                    </tr>

                    <c:forEach items="${requestScope.list}" var="game">
                        <tr>
                            <td style="display: none"><input id="gameId" class="form-control table-responsive"
                                                             type="hidden" name="gameId" value="${game.gameId}"
                                                             readonly/></td>
                            <td style="display: none"><input class="form-control table-responsive" type="hidden"
                                                             value="${game.playerOne.playerId}" readonly/></td>
                            <td><input class="form-control table-responsive" type="text"
                                       value="${game.playerOne.playerName}" readonly/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${game.gameResult == null}">
                                        <input class="form-control table-responsive" type="text" value="${null}"
                                               readonly/>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="form-control table-responsive" type="text"
                                               value="${game.playerOne.gameMove}" readonly/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="display: none"><input class="form-control table-responsive" type="hidden"
                                                             value="${game.playerTwo.playerId}" readonly/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${game.gameResult == null}">
                                        <input id="playerTwoName" class="form-control table-responsive" type="text"
                                               name="playerTwoName" maxlength="100" minlength="8"
                                               value="${game.playerTwo.playerName}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="playerTwoName" class="form-control table-responsive" type="text"
                                               name="playerTwoName" value="${game.playerTwo.playerName}" readonly/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <select id="playerTwoMove" name="playerTwoMove" class="form-control table-responsive">
                                    <c:choose>
                                        <c:when test="${game.playerTwo.gameMove == 'ROCK'}">
                                            <option value="ROCK" readonly>ROCK</option>
                                        </c:when>
                                        <c:when test="${game.playerTwo.gameMove == 'PAPER'}">
                                            <option value="PAPER" readonly>PAPER</option>
                                        </c:when>
                                        <c:when test="${game.playerTwo.gameMove == 'SCISSORS'}">
                                            <option value="SCISSORS" readonly>SCISSORS</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="ROCK">ROCK</option>
                                            <option value="PAPER">PAPER</option>
                                            <option value="SCISSORS">SCISSORS</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </td>
                            <td><input class="form-control table-responsive" type="text" value="${game.gameResult}"
                                       readonly/></td>
                            <td><input class="form-control table-responsive" type="text" value="${game.startGameDate}"
                                       readonly/></td>
                            <td><input class="form-control table-responsive" type="text" value="${game.finishGameDate}"
                                       readonly/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${game.gameResult == null}">
                                        <input class="btn btn-primary table-responsive" type="button"
                                               onclick="finishGame(this)" value="FINISH"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="btn btn-primary table-responsive" type="submit" value="FINISH"
                                               disabled/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><input class="btn btn-sm table-responsive" type="button" onclick="sendText()"
                                       value="SEND"></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script>

    const wsUri = "ws://" + document.location.host + "/game-updates";
    const websocket = new WebSocket(wsUri);

    function sendText() {
        websocket.send("");
    }

    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

    function onMessage(evt) {
        alert(evt.data);
        $.ajax({
            type: 'GET',
            url: '/game/findAll.do',
            success: function (response) {
                $('#finishTable').load(location.href + ' #finishTable');
            },
            error: function (error) {

            }
        });
    }

    function startGame(event) {

        var isVsMachine = document.getElementById('isVsMachine').checked;
        var playerOneName = document.getElementById('playerOneName').value;
        var playerOneMove = document.getElementById('playerOneMove').value;

        if (!validateName(playerOneName)){
            return false;
        }

        sendText();

        $.ajax({
            type: 'POST',
            url: '/game/start.do',
            data: {
                isVsMachine: isVsMachine,
                playerOneName: playerOneName,
                playerOneMove: playerOneMove
            },
            success: function (response) {
                $('#finishTable').load(location.href + ' #finishTable');
            },
            error: function (error) {

            }
        });
    }

    function finishGame(event) {

        var row = $(event).closest('tr');
        var gameId = row.find('#gameId').val();
        var playerTwoName = row.find('#playerTwoName').val();
        var playerTwoMove = row.find('#playerTwoMove').val();

        if (!validateName(playerTwoName)){
            return false;
        }
        sendText();


        $.ajax({
            type: 'POST',
            url: '/game/finish.do',
            data: {
                gameId: gameId,
                playerTwoName: playerTwoName,
                playerTwoMove: playerTwoMove
            },
            success: function (response) {

            },
            error: function (error) {

            }
        });
    }
    function validateName(name) {
        if (name == "" || name.length < 3) {
            alert("name should have at least 3 character.");
            return false;
        }
        return true;
    }

</script>

</body>
</html>
