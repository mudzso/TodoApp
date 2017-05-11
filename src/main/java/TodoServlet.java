import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mudzso on 2017.05.10..
 */
@WebServlet("/TodoServlet")
public class TodoServlet extends HttpServlet {
    TodoDao todoDao = new TodoDbManager("Todos");
    ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("Todo"));
        if(request.getParameterMap().containsKey("TodoID")&&request.getParameterMap().containsKey("Status")){

            try {
                todoDao.changeTodoStatus(
                    request.getParameter("TodoID"),
                    Status.valueOf(request.getParameter("Status"))
                    );

            } catch (SQLException e) {
                response.sendError(500, "There was an error during database operations");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        String id = (String)request.getSession().getAttribute("UserID");
        List<Todo>todos= null;
        try {
            todos = todoDao.getSortedListByUser(id);
        } catch (SQLException e) {
            response.sendError(500, "There was an error during database operations");
        }
        objectMapper.writeValue(response.getOutputStream(),todos);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String)req.getSession().getAttribute("UserID");
        if(req.getParameterMap().containsKey("Text")&&req.getParameterMap().containsKey("Status")){
            try {
                todoDao.addTodo(
                    req.getParameter("Text"),
                    Status.valueOf(req.getParameter("Status")),
                    id
                );
            } catch (SQLException e) {
                resp.sendError(500, "There was an error during database operations");
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameterMap().containsKey("TodoID")){

            try {
               todoDao.deleteTodo(
                   req.getParameter("TodoID")
               );
            } catch (SQLException e) {

                resp.sendError(500, "There was an error during database operations");
            }
        }

    }
}
