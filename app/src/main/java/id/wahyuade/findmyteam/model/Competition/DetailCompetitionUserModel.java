package id.wahyuade.findmyteam.model.Competition;

import java.util.ArrayList;

/**
 * Created by wahyuade on 09/07/17.
 */

public class DetailCompetitionUserModel {
    private String _id;
    private String judul;
    private String body;
    private String deadline_pendaftaran;
    private boolean is_team;
    private String max_team;
    private String min_team;
    private String foto;
    private ArrayList<Comments> comments;
    private ArrayList<Ideas> ideas;
    private ArrayList<JoinedTeam> joined_team;

    public ArrayList<Ideas> getIdeas() {
        return ideas;
    }

    public ArrayList<JoinedTeam> getJoined_team() {
        return joined_team;
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

    public String getDeadline_pendaftaran() {
        return deadline_pendaftaran;
    }

    public boolean is_team() {
        return is_team;
    }

    public int getMax_team(){
        return Integer.parseInt(max_team);
    }

    public int getMin_team() {
        return Integer.parseInt(min_team);
    }

    public String getFoto() {
        return foto;
    }

    public class Comments{
        private String _id_comment;
        private String firstname;
        private String comment;
        private Long date;
        private String user_foto;

        public String get_id_comment() {
            return _id_comment;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getComment() {
            return comment;
        }

        public Long getDate() {
            return date;
        }

        public String getUser_foto() {
            return user_foto;
        }
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public class Ideas{
        private String name;
        private String x_api_key;
        private String body;

        public String getName() {
            return name;
        }

        public String getX_api_key() {
            return x_api_key;
        }

        public String getBody() {
            return body;
        }
    }
    public class JoinedTeam{
        private String _id;
        private String team_name;
        private String team_foto;

        public String get_id() {
            return _id;
        }

        public String getTeam_name() {
            return team_name;
        }

        public String getTeam_foto() {
            return team_foto;
        }
    }
}
