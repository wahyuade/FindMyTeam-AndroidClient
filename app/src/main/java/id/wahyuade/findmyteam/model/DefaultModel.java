package id.wahyuade.findmyteam.model;

/**
 * Created by wahyuade on 07/07/17.
 */

public class DefaultModel {
    private boolean success;
    private String message;

    public DefaultModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
