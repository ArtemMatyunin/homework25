package ru.inno.matyunin.servlet;

import ru.inno.matyunin.dao.UserDao;
import ru.inno.matyunin.pojo.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Занимается добавлением, редактированием и удалением пользователей
 */

@WebServlet(name = "ShowUserServlet", value = "/showuser")
public class ShowUserServlet extends HttpServlet {
    //TODO: логгер реалиовать не успеваю.

    @Inject
    private UserDao userDao;

    /**
     * Если пользователь авторизован, то получаем id пользователя из сессии
     * И отображаем на форме account.jsp данные текущего пользователя для редактирования
     * Если пользователь не авторизован, то показываем пустую форму для регистрации
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Authorized".equals(request.getSession().getAttribute("Message"))) {
            Integer id = (Integer) request.getSession().getAttribute("id");

            if (id != null) {
                User user = userDao.getUserById(id);
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getSession().setAttribute("name", user.getName());
                    request.getSession().setAttribute("Message", "Authorized");

                }
            }
        }
        request.setAttribute("PageTitle", "Account");
        request.setAttribute("PageBody", "/account.jsp");
        request.getRequestDispatcher("/layout.jsp")
                .forward(request, response);
    }

    /**
     * Получаем данные из формы account.jsp
     * Если пользователь авторизован и в сессии есть id, то обновляем текущую запись в бД
     * Если не авторизован и id в сессии нет, то добавляем нового пользователя
     * Затем перезагружаем страницу(вызываем doPost для загрузки данных из БД) для проверки изменений
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        int id = 0;
        if (req.getSession().getAttribute("id") != null) {
            id = (Integer) req.getSession().getAttribute("id");
        }
        String name = req.getParameter("name");
        String pass = req.getParameter("pass");
        String prompt = req.getParameter("prompt");
        User user = new User(id, name, pass, prompt);
        if (!"Authorized".equals(req.getSession().getAttribute("Message"))) {
            id = userDao.addUser(user);
            if (id > 0) {
                req.getSession().setAttribute("id", id);
                req.getSession().setAttribute("Message", "Authorized");
            }
        } else {
            userDao.updateUser(user);
        }
        this.doGet(req, resp);

    }
}
