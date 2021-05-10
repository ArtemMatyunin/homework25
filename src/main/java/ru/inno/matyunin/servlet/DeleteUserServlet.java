package ru.inno.matyunin.servlet;

import ru.inno.matyunin.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Удаляет пользователей
 */

@WebServlet(name = "DeleteUserServlet", value = "/deleteuser")
public class DeleteUserServlet extends HttpServlet {

    //TODO: логгер реалиовать не успеваю.

    @Inject
    private UserDao userDao;

      /**
     * Если пользователь не авторизован, то переадресуем на страницу входа
     * Иначе получаем Id записи, которую нужно удалить, удаляем и переадресуем на страницу входа.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if ("Authorized".equals(request.getSession().getAttribute("Message"))) {

            userDao.deleteUserById(id);
            response.sendRedirect(request.getContextPath() + "/logout");
        } else {
            request.getRequestDispatcher("/checkuser")
                    .forward(request, response);

        }
    }
}
