/**
 * Created by mudzso on 2017.05.10..
 */
public class User {

    private String name;
    private String password;
    private String id;

    public User(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
