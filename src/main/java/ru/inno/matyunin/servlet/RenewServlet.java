package ru.inno.matyunin.servlet;



import ru.inno.matyunin.dao.MobileDao;
import ru.inno.matyunin.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сбрасывает БД. Был создан до того, как в docker-compose был добавлен
 * файл script.sql для инициализации БД до запуска приложения. Оставил для принудительного сброса.
 * Работает вне основной логики программы (без авторизации и проверок).
 *
 */

@WebServlet(urlPatterns = "/renew")
public class RenewServlet extends HttpServlet {
    //TODO: логгер реалиовать не успеваю.
    @Inject
    private MobileDao mobileDao;

    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        mobileDao.createTable();
        userDao.createTable();
        resp.sendRedirect(req.getContextPath() + "/logout");
    }
}
