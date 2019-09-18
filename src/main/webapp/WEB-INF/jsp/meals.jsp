<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>

        <div class="card border-dark">
            <div class="card-body pb-0">

                <form id="filter" method="get" action="meals/filter">
                    <div class="row">

                        <div class="offset-1 col-2">
                            <spring:message code="meal.startDate"/>
                            <input type="date" class="form-control" name="startDate" value="${param.startDate}">
                        </div>

                        <div class="col-2">
                            <spring:message code="meal.endDate"/>
                            <input type="date" class="form-control" name="endDate" value="${param.endDate}">
                        </div>

                        <div class="offset-2 col-2">
                            <spring:message code="meal.startTime"/>
                            <input type="time" class="form-control" name="startTime" value="${param.startTime}">
                        </div>

                        <div class="col-2">
                            <spring:message code="meal.endTime"/>
                            <input type="time" class="form-control" name="endTime" value="${param.endTime}">
                        </div>

                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-secondary" onclick="cancel()"><span
                        class="fa fa-close"></span><spring:message code="common.cancel"/></button>
                <button class="btn btn-primary" onclick="filter()"><spring:message code="meal.filter"/></button>
            </div>
        </div>
        <br>
        <button class="btn btn-primary" onclick="add()"><span class="fa fa-plus"><spring:message
                code="meal.add"/></span></button>
        <div id="datatable_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped" id="datatable">
                        <thead>
                        <tr>
                            <th><spring:message code="meal.dateTime"/></th>
                            <th><spring:message code="meal.description"/></th>
                            <th><spring:message code="meal.calories"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach items="${meals}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                            <tr data-mealExcess="${meal.excess}">
                                <td>
                                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                        ${fn:formatDateTime(meal.dateTime)}
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><spring:message code="common.update"/></td>
                                <td><a class="delete" id="${meal.id}"><span class="fa fa-remove"><spring:message
                                        code="common.delete"/></span></a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>
            </div>
            <hr>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" name="id" value="${meal.id}">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" value="${meal.dateTime}"
                               name="dateTime" required>
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" value="${meal.description}" size=40 name="description"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" value="${meal.calories}" name="calories" required>
                    </div>

                </form>
                <div class="modal-footer">
                    <button onclick="window.history.back()" type="button" class="btn btn-secondary"><span
                            class="fa fa-close"></span><spring:message
                            code="common.cancel"/></button>
                    <button onclick="saveMeal()" type="submit" class="btn btn-primary"><span
                            class="fa fa-check"></span><spring:message code="common.save"/></button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>