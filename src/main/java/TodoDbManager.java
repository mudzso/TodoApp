import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mudzso on 2017.05.09..
 */
public class TodoDbManager implements TodoDao{

    private final String DATABASE = "jdbc:mysql://localhost:3306/ToDos?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "lajos";
    private static final String DB_PASSWORD = "titkos";
    private final String TABLENAME;
    private List<Todo>todos;
    private String querry;
    private Connection connection;

    public TodoDbManager(String table) {
        TABLENAME = table;

        todos = new CopyOnWriteArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DATABASE,
            DB_USER,
            DB_PASSWORD);
    }

    @Override
    public List<Todo> getTodoList() throws SQLException {
        todos.clear();
        querry = "SELECT * FROM "+TABLENAME+" ";
        Todo todo = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);
        while (rs.next()){
            todo = new Todo(
                rs.getString("ID"),
                rs.getString("Text"),
                Status.valueOf(rs.getString("Status"))
            );
            todos.add(todo);
        }


        return todos;
    }

    @Override
    public Todo addTodo(String text, Status status,String userId)throws SQLException {
        querry = "INSERT INTO "+TABLENAME+"(ID,Text,Status,UserId) VALUES(?,?,?,?)";
        String id = UUID.randomUUID().toString();
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,id );
        ps.setString(2,text);
        ps.setString(3,status.toString());
        ps.setString(4,userId);
        ps.executeUpdate();

        return new Todo(id,text,status);
    }

    @Override
    public void deleteTodo(String id) throws SQLException{
        querry = "DELETE FROM "+TABLENAME+" WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,id);
        ps.executeUpdate();

    }

    @Override
    public Todo updateTodo(String id, String text) throws SQLException{
        querry ="UPDATE "+TABLENAME+" SET Text = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,text);
        ps.setString(2,id);
        ps.executeUpdate();
        return getTodoById(id);

    }

    public Todo getTodoById(String id)throws SQLException{
        querry = "SELECT * FROM "+TABLENAME+" WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new Todo(
            rs.getString("ID"),
            rs.getString("Text"),
            Status.valueOf(rs.getString("Status"))
        );

    }

    @Override
    public Todo changeTodoStatus(String id, Status status)throws SQLException {
        querry = "UPDATE "+TABLENAME+" SET Status = ? WHERE ID = ? ";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,status.toString());
        ps.setString(2,id);
        ps.executeUpdate();
        return getTodoById(id);
    }

    @Override
    public List<Todo> getSortedListByStatus(Status status,String userId) throws SQLException{
        todos.clear();
        querry = "SELECT * FROM "+TABLENAME+" WHERE Status = ? AND UserId = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,status.toString());
        ps.setString(2,userId);
        ResultSet rs = ps.executeQuery();
        Todo todo = null;
        while (rs.next()){
            todo = new Todo(
                rs.getString("ID"),
                rs.getString("Text"),
                Status.valueOf(rs.getString("Status"))
            );
            todos.add(todo);
        }

        return todos;
    }

    @Override
    public List<Todo> getSortedListByUser(String userId) throws SQLException {
        todos.clear();
        querry = "SELECT * FROM "+TABLENAME+" WHERE UserId = ?";
        PreparedStatement pr = connection.prepareStatement(querry);
        pr.setString(1,userId);
        ResultSet rs = pr.executeQuery();
        Todo todo = null;
        while (rs.next()){
            todo = new Todo(
                rs.getString("ID"),
                rs.getString("Text"),
                Status.valueOf(rs.getString("Status"))
            );
            todos.add(todo);
        }
        return todos;
    }
}
