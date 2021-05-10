<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! int mobileId(String mobileId) {
    if (mobileId == null) {
        return 0;
    }
    return Integer.parseInt(mobileId);
}
%>

<form method="post"
      action="${pageContext.request.contextPath}/addmobile"
      autocomplete="off">
    <div class="form-group">
        <input type="hidden" name="id" class="form-control" id="id"
               value="<%=mobileId(request.getParameter("id"))%>">
    </div>
    <div class="form-group">
        <label for="model">Model</label>
        <input name="model" type="text" class="form-control" id="model"
               value="${mobile.model}">
    </div>
    <div class="form-group">
        <label for="price">Price</label>
        <input name="price" type="text" class="form-control" id="price"
               value="${mobile.price}">
    </div>
    <div class="form-group">
        <label for="manufacturer">Manufacturer</label>
        <input name="manufacturer" type="text" class="form-control" id="manufacturer"
               value="${mobile.manufacturer}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>

</form>

