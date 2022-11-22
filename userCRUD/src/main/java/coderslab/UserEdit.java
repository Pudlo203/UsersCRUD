package coderslab;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserEdit", value = "/user/edit")
public class UserEdit extends HttpServlet {
    User user = new User();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        int id = Integer.parseInt(idString);
        request.getSession().setAttribute("id", id);
        request.setAttribute("user", UserDao.read(id));

        getServletContext().getRequestDispatcher(request.getContextPath() + "/users/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] name = request.getParameterValues("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(name == null || email == null ||
                email.isBlank() || password == null || password.isBlank()){
            response.sendRedirect("/user/list");
            return;
        }

        user.setName(String.valueOf(name));
        user.setPassword(password);
        user.setEmail(email);
        user.setId((int) request.getSession().getAttribute("id"));
        UserDao.update(user);
        String message = "Użytkownik zmieniony";
        //request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/user/list");
    }
}
