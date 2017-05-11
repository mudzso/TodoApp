import java.sql.SQLException;
import java.util.List;

/**
 * Created by mudzso on 2017.05.09..
 */
public interface TodoDao {

    List<Todo> getTodoList()throws SQLException;
    Todo addTodo(String text,Status status,String userId)throws SQLException;
    void deleteTodo(String id)throws SQLException;
    Todo updateTodo(String id, String text)throws SQLException;
    Todo changeTodoStatus(String id, Status status)throws SQLException;
    List<Todo> getSortedListByStatus(Status status,String id)throws SQLException;
    List<Todo>getSortedListByUser(String userId)throws SQLException;

}
