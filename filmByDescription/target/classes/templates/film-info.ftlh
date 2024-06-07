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
<#list info as param>
    ${param}<br><br>
</#list>
<form action="/film/create" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" id="name" name="name" value="${name}"/><br><br>
    <input type="hidden" id="description" name="description" value="${description}"/><br><br>
    <input type="submit" value="Добавить фильм"/>
</form>
</body>
</html>