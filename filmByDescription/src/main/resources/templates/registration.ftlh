<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<input type="text" id="name" name="name"/><br><br>
<form action="/registration" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <label for="username">Логин</label>
    <input type="text" id="username" name="username"/><br><br>
    <label for="name">Имя пользователя</label>
    <input type="text" id="name" name="name"/><br><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password"/><br><br>
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>
</html>