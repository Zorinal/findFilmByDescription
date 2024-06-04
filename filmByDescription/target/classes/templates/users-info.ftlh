<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>E-mail</th>
        <th>Статус</th>
        <th>Роли</th>
        <th>Действия</th>
    </tr> <!--ряд с ячейками заголовков-->
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td><a href="/user/${user.id}">${user.name}</a></td>
            <td>${user.username}</td>
            <td><#if user.active>true<#else>false</#if></td>
            <td><#list user.roles as role>${role}</#list></td>
            <td>
                <form action="/admin/user/ban/${user.id}" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <#if user.active>
                        <input type="submit" value="Бан">
                    <#else>
                        <input type="submit" value="Разбан">
                    </#if>
                </form>
            </td>
        </tr>
    <#else>
        <h3>Пользователей нет</h3>l
    </#list>
</table>
</body>
</html>