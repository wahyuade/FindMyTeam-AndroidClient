package id.wahyuade.findmyteam.model.Login;

/**
 * Created by wahyuade on 07/07/17.
 */

public class LoginModel {
    private boolean success;
    private String message;
    private DataLogin data;

    public LoginModel(boolean success, String message, DataLogin data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataLogin getData() {
        return data;
    }
}
