<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1><a href="/">Find film by description</a></h1>
<h3>Таблица пользователей</h3>
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
    <a href="/download/data.csv">Скачать data.csv</a>
</table>
<#if films?size == 0>
    <h3>Неодобренных фильмов нет</h3>
<#else>
    <h3>Таблица неодобренных фильмов</h3>
    <table>
        <tr>
            <th>Пользователь</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Действия</th>
        </tr>
        <#list films as film>
            <tr>
                <td><a href="/user/${film.user.id}">${film.user.name}</a></td>
                <td>${film.name}</td>
                <td>${film.description}</td>
                <td>
                    <form action="/admin/film/approve/${film.id}" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" id="isApproved-${film.id}" name="isApproved" value=""/>
                        <input type="submit" value="Одобрить" onclick="setApproval(${film.id}, true)">
                        <input type="submit" value="Отклонить" onclick="setApproval(${film.id}, false)">
                    </form>
                    <script>
                        function setApproval(filmId, isApproved) {
                            document.getElementById('isApproved-' + filmId).value = isApproved;
                        }
                    </script>
                </td>
            </tr>
        </#list>
    </table>
</#if>
</body>
</html>