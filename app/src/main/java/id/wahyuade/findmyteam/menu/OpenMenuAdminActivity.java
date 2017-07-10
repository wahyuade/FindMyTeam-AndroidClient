package id.wahyuade.findmyteam.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.admin.manage_competition.ManageCompetition;
import id.wahyuade.findmyteam.menu.admin.PushNotification;
import id.wahyuade.findmyteam.menu.admin.UserAndTeam;

public class OpenMenuAdminActivity extends FragmentActivity{
    String id_menu;
    TextView header_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_menu_admin);
        header_menu = (TextView)findViewById(R.id.header_menu);
        id_menu = getIntent().getStringExtra("id_menu");

        changeContainerAdmin(id_menu);
    }

    private void changeContainerAdmin(String menu){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (menu){
            case "manage_competition":{
                header_menu.setText("Manage Competition");
                ManageCompetition manageCompetition = new ManageCompetition();
                ft.replace(R.id.content_menu_admin, manageCompetition);
                ft.commit();
                break;
            }
            case "user_and_team":{
                header_menu.setText("User and ListMyTeam");
                UserAndTeam userAndTeam = new UserAndTeam();
                ft.replace(R.id.content_menu_admin, userAndTeam);
                ft.commit();
                break;
            }
            case "user_request":{

                break;
            }
            case "push_notification":{
                header_menu.setText("Push Notification");
                PushNotification pushNotification = new PushNotification();
                ft.replace(R.id.content_menu_admin, pushNotification);
                ft.commit();
                break;
            }
        }
    }
}
