<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>model</th>
        <th>price</th>
        <th>manufacturer</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mobile" items="${mobiles}">
        <tr>
            <td scope="row">${mobile.id}</td>
            <td>${mobile.model}</td>
            <td>${mobile.price}</td>
            <td>${mobile.manufacturer}</td>
            <td><a href="${pageContext.request.contextPath}/showmobile?id=${mobile.id}">Show mobile</a></td>
            <td>
                <form action="${pageContext.request.contextPath}/delete" method="post"
                      autocomplete="off">
                    <button type="submit" class="btn btn-primary">Delete mobile</button>
                    <input type="hidden" name="id" class="form-control" id="id"
                           value="${mobile.id}">
                </form>
            </td>

        </tr>
    </c:forEach>
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/addmobile" method="get"
                  autocomplete="off">
                <button type="submit" class="btn btn-primary">Add mobile</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
