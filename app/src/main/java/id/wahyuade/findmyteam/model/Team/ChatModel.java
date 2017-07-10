package id.wahyuade.findmyteam.model.Team;

/**
 * Created by wahyuade on 10/07/17.
 */

public class ChatModel {
    private String _id_user;
    private String name;
    private String user_foto;
    private String message;
    private Long date;

    public String getName() {
        return name;
    }

    public String getUser_foto() {
        return user_foto;
    }

    public String getMessage() {
        return message;
    }

    public Long getDate() {
        return date;
    }

    public String get_id_user() {
        return _id_user;
    }
}
