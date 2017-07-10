package id.wahyuade.findmyteam.model.FindPeople;

/**
 * Created by wahyuade on 10/07/17.
 */

public class DetailPeopleModel {
    private String _id;
    private String firstname;
    private String lastname;
    private String email;
    private String skill_id;
    private String x_api_key;

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

    public String getSkill_id() {
        return skill_id;
    }

    public String getX_api_key() {
        return x_api_key;
    }
}
