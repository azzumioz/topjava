<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:useBean id="listMeals" type="java.util.List<ru.javawebinar.topjava.model.MealTo>" scope="request"/>

    <title>Meals</title>
</head>
<jsp:include page="fragments/header.jsp"/>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Моя еда</h2>
<button><a href="meals?action=add"><img src="img/add.png" style="vertical-align:middle">Добавить</a></button>
<p/>
<table border="1" cellspacing="0">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="listEntry" items="${listMeals}">
        <jsp:useBean id="listEntry" type="ru.javawebinar.topjava.model.MealTo"/>
        <fmt:parseDate value="${listEntry.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <tr style="color:${listEntry.excess ? 'red' : 'green'}">
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
            <td>${listEntry.description}</td>
            <td>${listEntry.calories}</td>
            <td><a href="meals?id=${listEntry.id}&action=edit"><img src="img/pencil.png"></a></td>
            <td><a href="meals?id=${listEntry.id}&action=delete"><img src="img/delete.png"></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>