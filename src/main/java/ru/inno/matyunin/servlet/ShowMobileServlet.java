package ru.inno.matyunin.servlet;



import ru.inno.matyunin.dao.MobileDao;
import ru.inno.matyunin.pojo.Mobile;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showmobile")
public class ShowMobileServlet extends HttpServlet {
    //TODO: логгер реалиовать не успеваю.
    @Inject
    private MobileDao mobileDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           if ("Authorized".equals(req.getSession().getAttribute("Message"))) {

            String mobileId = req.getParameter("id");

            if (mobileId == null) {
                throw new ServletException("Missing parameter id");
            }
            Mobile mobile = mobileDao.getMobileById(Integer.valueOf(mobileId));
            if (mobile == null) {
                resp.setStatus(404);
                req.setAttribute("PageTitle", "Mobiles");
                req.setAttribute("PageBody", "notfound.jsp");
                req.getRequestDispatcher("/layout.jsp")
                        .forward(req, resp);
                return;
            }
            req.setAttribute("mobile", mobile);
            req.setAttribute("PageTitle", "Mobiles");
            req.setAttribute("PageBody", "form.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
        } else{
            req.getRequestDispatcher("/checkuser")
                    .forward(req, resp);
        }
    }
}
