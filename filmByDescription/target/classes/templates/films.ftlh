<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<#if errorMessage??>
    <div style="color: red;">${errorMessage}</div>
</#if>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Выйти из аккаунта"/>
</form>
<form action="/" method="get">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="text" id="description" name="description"/><br><br>
    <input type="submit" value="Найти фильм"/>
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
</body>
</html>