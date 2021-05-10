package ru.inno.matyunin.servlet;

import ru.inno.matyunin.dao.MobileDao;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Удаляет записи из БД(мобильники)
 * Доступен только авторизованным пользователям.
 */

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    //TODO: логгер реалиовать не успеваю.

    @Inject
    private MobileDao mobileDao;

    /**
     * Если пользователь не авторизован, то переадсуем его не страницу входа, иначе на домашнюю страницу
     * (allmobiles)
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Authorized".equals(request.getSession().getAttribute("Message"))) {
            response.sendRedirect(request.getContextPath() + "/allmobiles");
        } else {
            request.getRequestDispatcher("/checkuser")
                    .forward(request, response);
        }
    }

    /**
     * Если пользователь не авторизован, то переадсуем его не страницу входа(передаем управление сервлету checkuser,
     * который сам переадресует на нужную страницу), иначе получаем id записи, которую
     * следует удалить, удаляем и переадресуем на (allmobiles)
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Authorized".equals(request.getSession().getAttribute("Message"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            mobileDao.deleteMobileById(id);
            this.doGet(request, response);
        } else {
            request.getRequestDispatcher("/checkuser")
                    .forward(request, response);
        }
    }

}
