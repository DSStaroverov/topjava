<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>meals</h2>
<table>
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
<c:forEach var="meal" items="${meals}">

    <tr style="color:${meal.isExcess() ? 'red' : 'green'}">
        <td><javatime:format value="${meal.getDateTime()}" style="MS" /></td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
