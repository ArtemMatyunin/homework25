package ru.inno.matyunin.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.matyunin.dao.UserDao;
import ru.inno.matyunin.pojo.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Занимается проверкой и авторизацией пользователей
 */

@WebServlet(name = "CheckUserServlet", value = "/checkuser")
public class CheckUserServlet extends HttpServlet {

    //TODO: логгер реалиовать не успеваю.
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckUserServlet.class);

    @Inject
    private UserDao userDao;

    /**
     * Если пользователь не атворизован, то отправояем на страцинцу авторизации login.jsp
     * Иначе переадресует на домашнюю страницу (allmobiles)
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!"Authorized".equals(request.getSession().getAttribute("Message"))) {

            request.setAttribute("PageTitle", "Login");
            request.setAttribute("PageBody", "/login.jsp");
            request.setAttribute("Message", "Unauthorized/Incorrect username or password");
            request.getRequestDispatcher("/layout.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/allmobiles");
        }
    }

    /**
     * Ищем пользователя в БД по имени пользователя и паролю
     * Если такого нет, то оставляем его на странице входа
     * Иначе задаем праметры name, id и Message в текущей сессии, таким образом отмечаем пользователя,
     * как авторизованного.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("name");
        String userPassword = request.getParameter("pass");
        User user = userDao.getUser(new User(0, userName, userPassword, ""));

        if (user == null) {
            this.doGet(request, response);
        } else {
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("id", user.getId());

            request.getSession().setAttribute("Message", "Authorized");
            response.sendRedirect(request.getContextPath() + "/allmobiles");
        }
    }
}
