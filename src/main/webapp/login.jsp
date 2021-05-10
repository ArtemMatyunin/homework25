<%--
  Created by IntelliJ IDEA.
  User: Артём
  Date: 07.05.2021
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post"
      action="${pageContext.request.contextPath}/checkuser"
      autocomplete="off">

    <div class="form-group">
        <label for="name">User name</label>
        <input name="name" type="text" class="form-control" id="name">
    </div>
    <div class="form-group">
        <label for="pass">Password</label>
        <input name="pass" type="password" class="form-control" id="pass">
    </div>

    <button type="submit" class="btn btn-primary">Log in</button>

</form>



