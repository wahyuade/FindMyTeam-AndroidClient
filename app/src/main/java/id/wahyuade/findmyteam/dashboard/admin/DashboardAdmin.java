package id.wahyuade.findmyteam.dashboard.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.OpenMenuAdminActivity;
import id.wahyuade.findmyteam.new_app.LoginActivity;
import id.wahyuade.findmyteam.service.DatabaseService;

public class DashboardAdmin extends Activity {
    CardView profile;
    ImageView profile_option;
    LinearLayout manage_competition, user_and_team, user_request, push_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        profile = (CardView)findViewById(R.id.profile);
        profile_option = (ImageView)findViewById(R.id.profile_option);

        manage_competition = (LinearLayout)findViewById(R.id.manage_competition);
        user_and_team = (LinearLayout)findViewById(R.id.user_and_team);
        user_request = (LinearLayout)findViewById(R.id.user_request);
        push_notification = (LinearLayout)findViewById(R.id.push_notification);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        profile_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DashboardAdmin.this, profile_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.profile_option, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (String.valueOf(item.getTitle())){
                            case "Profile":
                                Toast.makeText(DashboardAdmin.this, "Ini profile", Toast.LENGTH_SHORT).show();
                                break;
                            case "About":
                                Toast.makeText(DashboardAdmin.this, "Ini about", Toast.LENGTH_SHORT).show();
                                break;
                            case "Logout":
                                AlertDialog.Builder logout = new AlertDialog.Builder(DashboardAdmin.this);
                                logout.setTitle("Maaf");
                                logout.setMessage("Apakah Anda yakin ingin keluar ?");
                                logout.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent login = new Intent(DashboardAdmin.this, LoginActivity.class);

                                        DatabaseService db = new DatabaseService(DashboardAdmin.this);
                                        db.unSetUser();
                                        db.close();

                                        DashboardAdmin.this.startActivity(login);
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                logout.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                logout.show();
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        final Intent open_menu = new Intent(DashboardAdmin.this, OpenMenuAdminActivity.class);

        manage_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "manage_competition");
                startActivity(open_menu);
            }
        });

        user_and_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "user_and_team");
                startActivity(open_menu);
            }
        });

        user_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "user_request");
                startActivity(open_menu);
            }
        });

        push_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "push_notification");
                startActivity(open_menu);
            }
        });
    }
}
