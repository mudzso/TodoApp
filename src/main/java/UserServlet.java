import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by mudzso on 2017.05.11..
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    UsersDao usersDao = new UserDbManager("Users");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean result = false;
        User user = null;
        HttpSession session;
        if (request.getParameterMap().containsKey("Name") && request.getParameterMap().containsKey("Password")) {
            try {
                result = usersDao.checkDatas(
                    request.getParameter("Name"),
                    request.getParameter("Password")
                );
                user = usersDao.getUser(request.getParameter("Password"));
            } catch (SQLException e) {
                response.sendError(500, "There was an error during database operations");
            }

            if (result) {

                session = request.getSession(true);
                session.setAttribute("UserID", user.getId());
                response.sendRedirect("./main.jsp");
            } else {
                response.sendRedirect("./register.jsp");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean result = false;
        String name = "";
        String password = "";
        if (request.getParameterMap().containsKey("Name") && request.getParameterMap().containsKey("Password")) {
            name = request.getParameter("Name");
            password = request.getParameter("Password");
            try {
                result = usersDao.checkDatas(name,password);

                if (result){
                    response.sendError(500, "password or name already exists");
                }else{
                    usersDao.registerUser(name,password);
                    response.sendRedirect("./login.jsp");
                }
            } catch (SQLException e) {
                response.sendError(500, "There was an error during database operations");
            }

        }
    }
}
