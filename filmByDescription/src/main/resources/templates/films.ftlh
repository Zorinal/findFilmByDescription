<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<input type="text" id="name" name="name"/><br><br>
<form id="addNewFilm" action="/film/create" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <label for="name">Название:</label>
    <input type="text" id="name" name="name"/><br><br>
    <label for="description">Описание:</label>
    <input type="text" id="description" name="description"/><br><br>
    <input type="submit" value="Добавить фильм"/>
</form>
<#list films as film>
    ${film.name}: ${film.description}
</#list>
</body>
</html>