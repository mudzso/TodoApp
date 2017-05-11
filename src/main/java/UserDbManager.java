import java.sql.*;
import java.util.Random;

/**
 * Created by mudzso on 2017.05.10..
 */
public class UserDbManager implements UsersDao {
    private final String DATABASE = "jdbc:mysql://localhost:3306/ToDos?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "lajos";
    private static final String DB_PASSWORD = "titkos";
    private final String TABLENAME;
    private Connection connection;
    private String querry;
    Random rand;

    public UserDbManager(String TABLENAME) {
        this.TABLENAME = TABLENAME;
        rand = new Random();
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
    public User registerUser(String name, String password) throws SQLException{
        String id = name+rand.nextInt(100)+1;
        querry = "INSERT INTO "+TABLENAME+"(Name,Password,UserId) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,name);
        ps.setString(2,password);
        ps.setString(3,id);
        ps.executeUpdate();


        return new User(name,password,id);
    }
    @Override
    public boolean checkDatas(String name, String password)throws SQLException {
        boolean result = false;
        querry = "SELECT * FROM "+TABLENAME+" WHERE Password = ? AND Name = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,password);
        ps.setString(2,name);
        ResultSet rs = ps.executeQuery();

        if(rs.next())result = true;

        return result;
    }

    @Override
    public User getUser(String password)throws SQLException {
        querry = "SELECT * FROM "+TABLENAME+" WHERE password = ?";
        PreparedStatement ps = connection.prepareStatement(querry);
        ps.setString(1,password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new User(
            rs.getString("Name"),
            rs.getString("Password"),
            rs.getString("UserId")
        );
    }
}
