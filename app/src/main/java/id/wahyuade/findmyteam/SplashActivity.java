package id.wahyuade.findmyteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import id.wahyuade.findmyteam.dashboard.admin.DashboardAdmin;
import id.wahyuade.findmyteam.dashboard.user.DashboardUser;
import id.wahyuade.findmyteam.new_app.LoginActivity;
import id.wahyuade.findmyteam.service.DatabaseService;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        final Thread thread = new Thread(){
            public void run() {
                try{
                    sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    DatabaseService db = new DatabaseService(SplashActivity.this);
                    if(db.checkUsersTable()){
                        if(db.isAdmin()){
                            Intent dashboard = new Intent(SplashActivity.this, DashboardAdmin.class);
                            startActivity(dashboard);
                        }else{
                            Intent dashboard = new Intent(SplashActivity.this, DashboardUser.class);
                            startActivity(dashboard);
                        }
                        finish();
                    }else{
                        Intent new_app = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(new_app);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}
