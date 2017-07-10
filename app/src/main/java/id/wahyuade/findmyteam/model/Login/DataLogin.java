package id.wahyuade.findmyteam.model.Login;

/**
 * Created by wahyuade on 07/07/17.
 */

public class DataLogin {
    private String _id;
    private String firstname;
    private String lastname;
    private String email;
    private int role;
    private String x_api_key;
    private String user_foto;

    public DataLogin(String _id, String firstname, String lastname, String email, int role, String x_api_key, String user_foto) {
        this._id = _id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.x_api_key = x_api_key;
        this.user_foto = user_foto;
    }

    public String get_id() {
        return _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }

    public String getX_api_key() {
        return x_api_key;
    }

    public String getUser_foto() {
        return user_foto;
    }
}
