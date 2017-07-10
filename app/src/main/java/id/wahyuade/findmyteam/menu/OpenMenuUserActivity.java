package id.wahyuade.findmyteam.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.user.find_people.FindPeople;
import id.wahyuade.findmyteam.menu.user.my_teams.MyTeams;
import id.wahyuade.findmyteam.menu.user.news_event.NewsEvent;
import id.wahyuade.findmyteam.menu.user.manage_idea.ManageIdea;

public class OpenMenuUserActivity extends FragmentActivity{
    String id_menu;
    TextView header_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_menu_user);
        header_menu = (TextView)findViewById(R.id.header_menu);
        id_menu = getIntent().getStringExtra("id_menu");

        changeContainerUser(id_menu);
    }
    private void changeContainerUser(String menu){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (menu){
            case "news_event":
                header_menu.setText("News Event");
                NewsEvent newsEvent = new NewsEvent();
                ft.replace(R.id.content_menu_user, newsEvent);
                ft.commit();
                break;
            case "my_teams":
                header_menu.setText("My Teams");
                MyTeams myTeams = new MyTeams();
                ft.replace(R.id.content_menu_user, myTeams);
                ft.commit();
                break;
            case "find_people":
                header_menu.setText("Find People");
                FindPeople findPeople = new FindPeople();
                ft.replace(R.id.content_menu_user, findPeople);
                ft.commit();
                break;
            case "request_event":
                header_menu.setText("Request Event");
                ManageIdea manageIdea = new ManageIdea();
                ft.replace(R.id.content_menu_user, manageIdea);
                ft.commit();
                break;
        }
    }
}
