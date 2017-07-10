package id.wahyuade.findmyteam.model.Team;

import java.util.ArrayList;

/**
 * Created by wahyuade on 09/07/17.
 */

public class DetailTeamModel {
    private String _id;
    private String team_name;
    private String team_foto;
    private String admin;
    private String max_member;
    private ArrayList<ListMyTeam.MemberModel> member;
    private ArrayList<ChatModel> chat;

    public String get_id() {
        return _id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public String getTeam_foto() {
        return team_foto;
    }

    public String getMax_member() {
        return max_member;
    }

    public ArrayList<ListMyTeam.MemberModel> getMember() {
        return member;
    }

    public ArrayList<ChatModel> getChat() {
        return chat;
    }

    public String getAdmin() {
        return admin;
    }
}
