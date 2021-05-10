<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/date.tld" prefix="datetag" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<myTags:template>
    <jsp:attribute name="header">
        <h1>Mobiles</h1>
        (<datetag:DateTag plus="1"/>)
    </jsp:attribute>
    <jsp:body>
        <ul>
            <comment><h1>Please, log in or register, if your not have login and password</h1></comment>

            <li><h2><a href="${pageContext.request.contextPath}/checkuser">Sing in</a></h2></li>
            <li><h2><a href="${pageContext.request.contextPath}/showuser">Register</a></h2></li>
        </ul>
    </jsp:body>
</myTags:template>