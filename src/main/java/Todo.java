import java.util.UUID;

/**
 * Created by mudzso on 2017.05.09..
 */
public class Todo {

    private String id;
    private String text;
    private Status status;

    public Todo(String text, Status status) {
        id = UUID.randomUUID().toString();
        this.text = text;
        this.status = status;
    }

    public Todo(String id, String text, Status status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
