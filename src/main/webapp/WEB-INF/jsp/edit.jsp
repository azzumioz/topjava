<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Редактирование </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<h3><a href="index.html">Home</a></h3>
<h2>Редактирование еды</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt><label>Дата/время:</label></dt>
        <dd>
            <input type="datetime-local" name="date" value="${meal.dateTime}" size="30" placeholder="Дата/время">
        </dd>
    </dl>
    <dl>
        <dt><label>Описание:</label></dt>
        <dd>
            <input type="text" name="description" value="${meal.description}" size="30" placeholder="Описание">
        </dd>
    </dl>
    <dl>
        <dt><label>Калории:</label></dt>
        <dd>
            <input type="text" name="calories" value="${meal.calories}" size="30" placeholder="Калории">
        </dd>
    </dl>
    <button><a href="meals">Отменить</a></button>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
