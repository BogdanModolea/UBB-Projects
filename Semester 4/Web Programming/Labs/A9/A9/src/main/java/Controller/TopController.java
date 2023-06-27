package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TopController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("topNr") == null)
            request.getSession().setAttribute( "topNr", null);
        else {
            int topNr = Integer.parseInt(request.getParameter("topNr"));
            request.getSession().setAttribute("topNr", topNr);
            response.sendRedirect("home.jsp");
        }
    }
}
