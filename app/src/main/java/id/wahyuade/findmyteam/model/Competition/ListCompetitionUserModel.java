package id.wahyuade.findmyteam.model.Competition;

/**
 * Created by wahyuade on 07/07/17.
 */

public class ListCompetitionUserModel {
    private String _id;
    private String judul;
    private String body;
    private String deadline_pendaftaran;
    private boolean is_team;
    private String max_team;
    private String min_team;
    private String foto;
    private int comments;
    private int ideas;

    public int getJoined_team() {
        return joined_team;
    }

    private int joined_team;

    public int getIdeas() {
        return ideas;
    }

    public int getComments() {
        return comments;
    }

    public String get_id() {
        return _id;
    }

    public String getJudul() {
        return judul;
    }

    public String getBody() {
        return body;
    }

    public Long getDeadline_pendaftaran() {
        return Long.parseLong(deadline_pendaftaran);
    }

    public boolean is_team() {
        return is_team;
    }

    public int getMax_team() {
        return Integer.parseInt(max_team);
    }

    public int getMin_team() {
        return Integer.parseInt(min_team);
    }

    public String getFoto() {
        return foto;
    }
}
