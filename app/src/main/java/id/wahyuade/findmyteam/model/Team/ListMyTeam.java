package id.wahyuade.findmyteam.model.Team;

import java.util.ArrayList;

/**
 * Created by wahyuade on 09/07/17.
 */

public class ListMyTeam {
    private String _id;
    private String team_name;
    private String team_foto;
    private String max_member;
    private String admin;
    private ArrayList<MemberModel> member;

    public String getAdmin() {
        return admin;
    }

    public String get_id() {
        return _id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public String getTeam_foto() {
        return team_foto;
    }

    public int getMax_member() {
        return Integer.parseInt(max_member);
    }

    public ArrayList<MemberModel> getMember() {
        return member;
    }

    public class MemberModel{
        private String _id_user;
        private String name;
        private String user_foto;
        private int role;
        private int status;
    }
}
