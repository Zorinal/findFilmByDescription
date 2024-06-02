<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Find film by description</title>
</head>
<body>
<h1>Find film by description</h1>
<form action="/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <label for="username">Логин</label>
    <input type="text" id="username" name="username"/><br><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password"/><br><br>
    <input type="submit" value="Войти"/>
</form>
<form action="/registration" method="get">
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>
</html>