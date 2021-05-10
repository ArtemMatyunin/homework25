<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    private String userName(Object obj) {
        String userName = (String) obj;
        if (userName == null) {
            userName = "Register here";
        }
        return userName;
    }

    private String message(Object obj) {
        String message = (String) obj;
        if (message == null) {
            message = "Unauthorized/Incorrect login or password";
        }
        return message;
    }
%>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>${request.getAttribute("PageTitle")}
    </title>
</head>
<body>
<div class="header">

</div>
<div class="container">
    <table class="table">
        <tbody>
        <tr>
            <td><h2><a href="${pageContext.request.contextPath}/showuser">
                <%= userName(session.getAttribute("name"))%>
            </a>
            </h2></td>
            <td><h2><%=message(session.getAttribute("Message"))%>
            </h2></td>
            <td><h2><a href="${pageContext.request.contextPath}/logout">Sing out</a></h2></td>
        </tr>
        </tbody>
    </table>

    <h1><%=request.getAttribute("PageTitle")%>
    </h1>
    <c:import url="${PageBody}"/>
    <i><c:import url="footer.jsp"/></i>
</div>
<div class="footer">

</div>
</body>
</html>