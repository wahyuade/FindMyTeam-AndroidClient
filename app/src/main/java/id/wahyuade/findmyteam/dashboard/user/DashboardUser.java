package id.wahyuade.findmyteam.dashboard.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.OpenMenuUserActivity;
import id.wahyuade.findmyteam.new_app.LoginActivity;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;

public class DashboardUser extends Activity {
    TextView user_nama;
    TextView user_email;
    ImageView profile_option;
    CircleImageView user_foto;
    LinearLayout news_event, my_team, find_people, request_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        DatabaseService db = new DatabaseService(this);
        profile_option = (ImageView)findViewById(R.id.profile_option);
        user_foto = (CircleImageView)findViewById(R.id.user_foto);
        user_nama = (TextView)findViewById(R.id.user_nama);
        user_email = (TextView)findViewById(R.id.user_email);
        news_event = (LinearLayout)findViewById(R.id.news_event);
        my_team = (LinearLayout)findViewById(R.id.my_team);
        find_people = (LinearLayout)findViewById(R.id.find_people);
        request_event = (LinearLayout)findViewById(R.id.request_event);



        user_email.setText(db.getEmail());
        user_nama.setText(db.getNama());

        Glide.with(this).load(ApiService.BASE_URL+"/"+db.getUserFoto()).error(R.drawable.default_profile).into(user_foto);

        profile_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DashboardUser.this, profile_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.profile_option, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (String.valueOf(item.getTitle())){
                            case "Profile":
                                Toast.makeText(DashboardUser.this, "Ini profile", Toast.LENGTH_SHORT).show();
                                break;
                            case "About":
                                Toast.makeText(DashboardUser.this, "Ini about", Toast.LENGTH_SHORT).show();
                                break;
                            case "Logout":
                                AlertDialog.Builder logout = new AlertDialog.Builder(DashboardUser.this);
                                logout.setTitle("Maaf");
                                logout.setMessage("Apakah Anda yakin ingin keluar ?");
                                logout.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent login = new Intent(DashboardUser.this, LoginActivity.class);

                                        DatabaseService db = new DatabaseService(DashboardUser.this);
                                        db.unSetUser();
                                        db.close();

                                        DashboardUser.this.startActivity(login);
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
        db.close();

        final Intent open_menu = new Intent(DashboardUser.this, OpenMenuUserActivity.class);
        news_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "news_event");
                startActivity(open_menu);
            }
        });
        my_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "my_teams");
                startActivity(open_menu);
            }
        });
        find_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "find_people");
                startActivity(open_menu);
            }
        });
        request_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_menu.putExtra("id_menu", "request_event");
                startActivity(open_menu);
            }
        });
    }
}
