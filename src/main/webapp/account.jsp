<%--
  Created by IntelliJ IDEA.
  User: Артём
  Date: 08.05.2021
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<form method="post"
      action="${pageContext.request.contextPath}/showuser"
      autocomplete="off">
    <div class="form-group">
        <label for="name">Username</label>
        <input name="name" type="text" class="form-control" id="name"
               value="${user.name}">
    </div>
    <div class="form-group">
        <label for="pass">Password</label>
        <input name="pass" type="password" class="form-control" id="pass"
               value="${user.pass}">
    </div>
    <div class="form-group">
        <label for="prompt">Prompt</label>
        <input name="prompt" type="text" class="form-control" id="prompt"
               value="${user.prompt}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<form action="${pageContext.request.contextPath}/deleteuser"
      autocomplete="off" method="post">
    <button type="submit" class="btn btn-primary">To alive</button>
</form>

