
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by mudzso on 2017.05.09..
 */
class TodoDaoTest {
    private static final String DATABASE = "jdbc:mysql://localhost:3306/ToDos?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "lajos";
    private static final String DB_PASSWORD = "titkos";
    private TodoDao todoDao;
    private  Connection connection;

    private static String querry;

    public TodoDaoTest() {
        todoDao = new TodoDbManager("TestTodo");
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DATABASE,
            DB_USER,
            DB_PASSWORD);
    }
    @AfterAll
    private static void afterall(){
        querry="DELETE FROM TestTodo";

        try {
            Statement statement = getConnection().createStatement();
            statement.execute(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @BeforeEach
    private  void dropdb(){
        querry="DELETE FROM TestTodo";

        try {
            Statement statement = connection.createStatement();
            statement.execute(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getTodoList() throws SQLException {
        int size = 2;
        List<Todo> todos = todoDao.getTodoList();
        assertEquals(0, todos.size());
        todoDao.addTodo("asd1",Status.ACTIVE,"józsi");
        todoDao.addTodo("asd2",Status.DONE,"józsi");
        todos = todoDao.getTodoList();
        assertEquals(size, todos.size());
    }

    @Test
    void addTodo() throws SQLException {
        todoDao.addTodo("asd3",Status.ACTIVE,"józsi");
        Todo todo = todoDao.getTodoList().get(0);
        assertEquals("asd3",todo.getText());
        assertEquals(Status.ACTIVE,todo.getStatus());
        assertNotNull(todo.getId());
    }

    @Test
    void deleteTodo() throws SQLException{

        Todo todo = todoDao.addTodo("asd4",Status.ACTIVE,"józsi");
        assertEquals(1,todoDao.getTodoList().size());
        todoDao.deleteTodo(todo.getId());
        assertEquals(0,todoDao.getTodoList().size());
    }

    @Test
    void updateTodo() throws SQLException{
        todoDao.addTodo("asd5",Status.BACKLOG,"józsi");
        Todo todo = todoDao.getTodoList().get(0);
        Todo result = todoDao.updateTodo(todo.getId(),"asd6");
        assertEquals("asd6",result.getText());

    }

    @Test
    void changeTodoStatus() throws SQLException{
       Todo todo = todoDao.addTodo("asd5",Status.ACTIVE,"józsi");
       Todo result = todoDao.changeTodoStatus(todo.getId(),Status.DONE);
       assertEquals(Status.DONE,result.getStatus());
    }

    @Test
    void getSortedList()throws SQLException {

        todoDao.addTodo("asd7",Status.ACTIVE,"józsi");
        todoDao.addTodo("asd8",Status.BACKLOG,"józsi");
        todoDao.addTodo("asd9",Status.DONE,"józsi");
        todoDao.addTodo("asd10",Status.ACTIVE,"józsi");

        int result = todoDao.getSortedListByStatus(Status.ACTIVE,"józsi").size();
        assertEquals(2,result);
    }

}
