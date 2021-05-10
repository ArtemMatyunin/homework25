package ru.inno.matyunin.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * "Разлогиниавет" пользователей
 */

@WebServlet(name = "LogOutServlet", value = "/logout")
public class LogOutServlet extends HttpServlet {
    //TODO: логгер реалиовать не успеваю.

    /**
     * Удаляем из сессии параметры name и id, а параметру Message меняем значение на Unauthorized...
     * Переадресуем на страницу входа
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Authorized".equals(request.getSession().getAttribute("Message"))) {
            request.getSession().setAttribute("Message", "Unauthorized/Incorrect username or password");
            request.getSession().removeAttribute("name");
            request.getSession().removeAttribute("id");
            request.getRequestDispatcher("/checkuser")
                    .forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/allmobiles");
        }
    }
}
