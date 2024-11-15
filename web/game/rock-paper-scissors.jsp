<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/head.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/menu.jsp"/>

<div id="container" class="container">
    <div id="myModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <div class="row">
                    <div class="col-md-9">
                        <h5 class="modal-title" id="gameChat">GameChat</h5>
                    </div>
                    <div class="col-md-2">
                        <span class="close">&times;</span>
                    </div>
                </div>
            </div>

            <div id="chatBox" class="chat-box">
                <div class="chat-messages">
                    <table id="gameChatsHeader" class="table table-striped table-responsive table-hover" style="width: 100%">
                        <tr>
                            <td><input type="button" class="form-control btn btn-primary" value="ShowAll..." onclick="getAllGameChatsByGameId()"/></td>
                        </tr>
                        <tr>
                            <td><input type="button" class="form-control btn btn-primary" value="ShowMore..." onclick="getAllPagingGameChatsByGameId()"/></td>
                        </tr>
                    </table>
                    <table id="gameChats" class="table table-striped table-responsive table-hover" style="width: 100%">
                        <c:forEach items="${sessionScope.listGameChats}" var="gameChat">
                            <tr>
                                <td class="chat-row" style="width: 15%;">
                                    <label for="userChat" class="form-control" style="display: inline-block; white-space: nowrap; max-width: fit-content;">
                                            ${gameChat.user.username}
                                    </label>
                                </td>
                                <td class="chat-row" style="width: 85%">
                                    <textarea id="userChat" class="form-control" name="userChat" style="width: available; text-align: left; word-break: break-all; white-space: normal;" readonly>
                                            ${gameChat.message}
                                    </textarea>
                                </td>
                                <%--<c:choose>
                                    <c:when test="${gameChat.user.username} == ${sessionScope.username}">
                                        <td class="chat-row" style="direction: rtl;">
                                            <label for="userChat" class="form-control username-label" style="display: inline-block; white-space: nowrap; max-width: fit-content; direction: rtl;">
                                                    ${gameChat.user.username}
                                            </label>
                                            <textarea id="userChat" class="form-control" name="userChat" style="max-width: fit-content; max-height: fit-content; min-width: fit-content; min-height: fit-content;margin-top: 0; direction: rtl;" readonly>
                                                    ${gameChat.message}
                                            </textarea>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="chat-row" style="direction: ltr">
                                            <label for="userChat1" class="form-control username-label" style="display: inline-block; white-space: nowrap; max-width: fit-content;">
                                                    ${gameChat.user.username}
                                            </label>
                                            <textarea id="userChat1" class="form-control" name="userChat1" style="max-width: content-box; max-height: content-box; min-width: content-box; min-height: content-box;" readonly>
                                                    ${gameChat.message}
                                            </textarea>
                                        </td>
                                    </c:otherwise>
                                </c:choose>--%>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <input type="text" id="message" class="form-control" placeholder="Write your message..." minlength="1" onkeydown="sendTextByEnter(event)"/>
                    </div>
                    <div class="col-md-2">
                        <input type="button" class="form-control btn btn-primary" value="Send" onclick="sendText(this)"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">Desktop</div>
        <div class="panel-body">
            <label for="playerOneName" style="width: 100%; text-align: center">PlayerOneName</label>
            <input type="text" id="playerOneName" name="playerOneName" maxlength="100" minlength="8"
                   class="form-control" style="width: 100%"/>
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
                <input type="checkbox" id="isVsMachine" name="isVsMachine" value="VsMachine"
                       style="width: 100%; align-self: center; cursor: pointer"/>
            </div>
            <br/>
            <input type="button" style="width: 100%" value="START" class="btn btn-primary" onclick="startGame(this)"/>
            <br/>
            <br/>
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
                            <td><input class="btn btn-sm table-responsive" type="button" onclick="openModal(this, ${game.gameId})" value="CHAT"></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    var modal = document.getElementById("myModal");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];


    function openModal(evt, gameId) {
        modal.dataset.gameId = gameId;
        modal.dataset.maxSizeRows = 0;
        getAllPagingGameChatsByGameId();
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
        modal.dataset.gameId = "";
        modal.dataset.maxSizeRows = 0;
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        closeModal();
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            closeModal();
        }
    }

    function getAllGameChatsByGameId() {
        $.ajax({
            type: 'GET',
            url: '/game/findAllGameChats.do',
            data: {
                gameId: modal.dataset.gameId
            },
            success: function (response) {
                refreshGameChats();
            },
            error: function (error) {

            }
        });
    }

    function getAllPagingGameChatsByGameId() {
        var gameId = modal.dataset.gameId;
        var maxSizeRows = Number(modal.dataset.maxSizeRows) + 10;
        modal.dataset.maxSizeRows = maxSizeRows;

        $.ajax({
            type: 'GET',
            url: '/game/findAllPagingChats.do',
            data: {
                gameId: gameId,
                maxSizeRows: maxSizeRows
            },
            success: function (response) {
                refreshGameChats();
            },
            error: function (error) {

            }
        });
    }
    function sendTextByEnter(event) {
        if (event.key === 'Enter') {
            sendText();
        }
    }

    function sendText() {
        var gameId = modal.dataset.gameId;
        var username = "${sessionScope.username}";
        var message = document.getElementById("message").value;
        var maxSizeRows = modal.dataset.maxSizeRows;

        $.ajax({
            type: 'POST',
            url: '/game/saveChat.do',
            data: {
                username: username,
                gameId: gameId,
                message: message,
                maxSizeRows: maxSizeRows
            },
            success: function (response) {
                document.getElementById("message").value = "";
                refreshGameChats();
            },
            error: function (error) {

            }
        });
    }

    //ctrl + alt + lS
    const wsUri = "ws://" + document.location.host + "/game-updates";
    const websocket = new WebSocket(wsUri);

    websocket.onopen = function () {
        websocket.send("${sessionScope.username}");
    };

    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

    function onMessage(evt) {
        if (evt.data == modal.dataset.gameId) {
            var gameId = modal.dataset.gameId;
            var maxSizeRows = Number(modal.dataset.maxSizeRows);

            $.ajax({
                type: 'GET',
                url: '/game/findAllPagingChats.do',
                data: {
                    gameId: gameId,
                    maxSizeRows: maxSizeRows
                },
                success: function (response) {
                    refreshGameChats();
                },
                error: function (error) {

                }
            });
        } else if (evt.data == "" || evt.data.toString().includes("Game Result")) {
            if (evt.data != "") {
                alert(evt.data);
            }
            $.ajax({
                type: 'GET',
                url: '/game/findAll.do',
                success: function (response) {
                    refreshFinishTable();
                },
                error: function (error) {

                }
            });
        }
    }

    function refreshGameChats() {
        $('#gameChats').load(location.href + ' #gameChats');
    }

    function refreshFinishTable() {
        $('#finishTable').load(location.href + ' #finishTable');
    }

    function startGame(event) {

        var isVsMachine = document.getElementById('isVsMachine').checked;
        var playerOneName = document.getElementById('playerOneName').value;
        var playerOneMove = document.getElementById('playerOneMove').value;
        var username = "${sessionScope.username}";

        if (!validateName(playerOneName)) {
            return false;
        }

        //sendText();

        $.ajax({
            type: 'POST',
            url: '/game/start.do',
            data: {
                isVsMachine: isVsMachine,
                playerOneName: playerOneName,
                playerOneMove: playerOneMove,
                username: username
            },
            success: function (response) {
                refreshFinishTable();
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
        var username = "${sessionScope.username}";

        if (!validateName(playerTwoName)) {
            return false;
        }
        //sendText();


        $.ajax({
            type: 'POST',
            url: '/game/finish.do',
            data: {
                gameId: gameId,
                playerTwoName: playerTwoName,
                playerTwoMove: playerTwoMove,
                username: username
            },
            success: function (response) {

            },
            error: function (error) {

            }
        });
    }

    function validateName(name) {
        if (name == "" || name.length < 3) {
            alert("name should have atleast 3 character.");
            return false;
        }
        return true;
    }

</script>

</body>
</html>
