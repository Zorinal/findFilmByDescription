<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1><a href="/">Find film by description</a></h1>
Пользователь: ${user.name}<br>
E-mail: ${user.username}<br>
История поиска:<br>
<#if films??>
    <#list films as film>
        ${film.name}<br>
    </#list>
</#if>
</body>
</html>