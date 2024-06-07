<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1><a href="/">Find film by description</a></h1>
<#if errorMessage??>
    <div style="color: red;">${errorMessage}</div>
</#if>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Выйти из аккаунта"/>
</form>
<#if isAdmin>
    <form action="/admin" method="get">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Панель администратора"/>
    </form>
</#if>
<form action="/" method="get">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="text" id="description" name="description"/>
    <input type="submit" value="🔍"/>
</form>
<#if suggestedFilms??>
    <#list suggestedFilms as film>
        <a href="/film/${film}">${film}</a><br>
    </#list>
</#if>
История поиска:<br>
<#if films??>
    <#list films as film>
        ${film.name}<br>
    </#list>
</#if>
<button onclick="document.getElementById('addFilmForm').showModal()">Не нашли то, что искали?</button>
<dialog id="addFilmForm">
    <button type="button" onclick="document.getElementById('addFilmForm').close()">✖</button>
    <br><br>
    Предложите описание и название фильма<br><br>
    <form action="/film/suggest" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="text" id="name" name="name" placeholder="Название фильма"/><br><br>
        <input type="text" id="description" name="description" placeholder="Описание"/><br><br>
        <input type="submit" value="Отправить"/>
    </form>
</dialog>

</body>
</html>