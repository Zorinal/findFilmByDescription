<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<form id="addNewFilm" action="/film/create" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="text" id="description" name="description"/><br><br>
    <input type="submit" value="Найти фильм"/>
</form>
<#list films as film>
    ${film.name}: ${film.description}<br>
</#list>
</body>
</html>