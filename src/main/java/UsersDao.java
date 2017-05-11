import java.sql.SQLException;

/**
 * Created by mudzso on 2017.05.10..
 */
public interface UsersDao {

    User registerUser(String name,String password)throws SQLException;
    boolean checkDatas(String name,String password)throws SQLException;
    User getUser(String password)throws SQLException;
}
