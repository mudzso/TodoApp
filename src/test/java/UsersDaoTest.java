import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mudzso on 2017.05.10..
 */
class UsersDaoTest {

    private static final String DATABASE = "jdbc:mysql://localhost:3306/ToDos?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "lajos";
    private static final String DB_PASSWORD = "titkos";
    private UsersDao usersDao;
    private Connection connection;

    private static String querry;

    public UsersDaoTest() {
        usersDao = new UserDbManager("TestUsers");
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
        querry="DELETE FROM TestUsers";

        try {
            Statement statement = getConnection().createStatement();
            statement.execute(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @BeforeEach
    private  void dropdb(){
        querry="DELETE FROM TestUsers";

        try {
            Statement statement = connection.createStatement();
            statement.execute(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    void registerUser() throws SQLException{

        User user = usersDao.registerUser("aranka","titkos");
        assertNotNull(user.getId());
        assertNotNull(usersDao.getUser(user.getPassword()));

    }

    @Test
    void checkDatas()throws SQLException {
        User user = usersDao.registerUser("bela","titkos");
        assertTrue(usersDao.checkDatas("bela","titkos"));
        assertFalse(usersDao.checkDatas("bel","titkos"));
        assertFalse(usersDao.checkDatas("bela","titko"));
    }

    @Test
    void getUser()throws SQLException {
        User user = usersDao.registerUser("gabor","secret");
        User result = usersDao.getUser(user.getPassword());
        assertEquals(user.getName(),result.getName());
        assertEquals(user.getPassword(),result.getPassword());
        assertEquals(user.getId(),result.getId());

    }

}
