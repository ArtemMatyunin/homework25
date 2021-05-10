package ru.inno.matyunin.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.matyunin.dao.MobileDao;
import ru.inno.matyunin.dao.MobileDaoJdbcImpl;
import ru.inno.matyunin.pojo.Mobile;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Артём
 * Занимается тем, что добавляет мобильники, или обновляет данные уже существующих
 * Доступен только авторизованным пользователям.
 */

@WebServlet("/addmobile")
public class AddMobileServlet extends HttpServlet {

    //TODO: логгер реалиовать не успеваю.
    @Inject
    private MobileDao mobileDao;

    /**
     * Проверяем, авторизован ли пользователь. Если не авторизован, то скидыаем на страницу авторизации.
     * Если авторизован, то загружаем форму ввода данных form.jsp
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if ("Authorized".equals(req.getSession().getAttribute("Message"))) {
            req.setAttribute("PageTitle", "New Mobiles");
            req.setAttribute("PageBody", "/form.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
        } else {
            req.getRequestDispatcher("/checkuser")
                    .forward(req, resp);
        }

    }

    /**
     * Проверяем, авторизован ли пользователь. Если не авторизован, то скидыаем на страницу авторизации.
     * Если авторизован, то полчаем данные из формы ввода данных form.jsp и если такой мобильник уже есть,
     * то обновляем его данные. Если нет, то добавляем новый.
     *
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if ("Authorized".equals(req.getSession().getAttribute("Message"))) {

            req.setCharacterEncoding("utf-8");
            int id = Integer.parseInt(req.getParameter("id"));
            String model = req.getParameter("model");
            String price = req.getParameter("price");
            String manufacturer = req.getParameter("manufacturer");
            Mobile mobile = new Mobile(id, model, Integer.valueOf(price), manufacturer);
            if (id == 0) {
                mobileDao.addMobile(mobile);
            } else {
                mobileDao.updateMobile(mobile);
            }
            resp.sendRedirect(req.getContextPath() + "/allmobiles");
        } else {
            req.getRequestDispatcher("/checkuser")
                    .forward(req, resp);

        }
    }
}
