package ru.inno.matyunin.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.matyunin.dao.MobileDao;

import ru.inno.matyunin.pojo.Mobile;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/***
 * Отборажает список всезх записей из БД
 * Доступен только авторизованным пользователям.
 */

@WebServlet(urlPatterns = "/allmobiles", name = "Mobiles")
public class AllMobilesServlet extends HttpServlet {

    @Inject
    private MobileDao mobileDao;
    //TODO: логгер реалиовать не успеваю.

    /**
     * Проверяем, авторизован ли пользователь. Если не авторизован, то скидыаем на страницу авторизации.
     * Если авторизован, то показываем список всех записей из БД на странице allmobiles.jsp
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException      *
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("Authorized".equals(req.getSession().getAttribute("Message"))) {
            Collection<Mobile> mobiles = mobileDao.getAllMobile();
            req.setAttribute("mobiles", mobiles);
            req.setAttribute("PageTitle", "Mobiles");
            req.setAttribute("PageBody", "/allmobiles.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
        } else {
            req.getRequestDispatcher("/checkuser")
                    .forward(req, resp);
        }


    }
}
