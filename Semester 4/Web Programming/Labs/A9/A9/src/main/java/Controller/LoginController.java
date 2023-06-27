package Controller;

import DB.DBManager;
import Domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/LoginController")
public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DBManager dbmanager = new DBManager();
        User user = dbmanager.authenticate(username, password);
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("fail", false);
            response.sendRedirect("home.jsp");

        } else {
            session.setAttribute("fail", true);
            response.sendRedirect("index.jsp");
        }

    }
}
