package id.wahyuade.findmyteam.new_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.dashboard.admin.DashboardAdmin;
import id.wahyuade.findmyteam.dashboard.user.DashboardUser;
import id.wahyuade.findmyteam.model.Login.LoginModel;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    Call<LoginModel> postLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        TextView register = (TextView)findViewById(R.id.register);

        Button login_button = (Button) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_data = email.getText().toString();
                String password_data = password.getText().toString();

                if(email_data.isEmpty() && password_data.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi field login dengan benar :)", Toast.LENGTH_SHORT).show();
                }else{
                    postLogin = ApiService.service_post.postLogin(email_data, password_data);

                    postLogin.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            DatabaseService db = new DatabaseService(LoginActivity.this);
                            if(response.body().isSuccess()){
                                if(db.setUsersData(response.body().getData())){
                                    if(response.body().getData().getRole() == 0){
                                        Intent admin_dashboard = new Intent(LoginActivity.this, DashboardAdmin.class);
                                        startActivity(admin_dashboard);
                                        finish();
                                    }else{
                                        Intent user_dashboard = new Intent(LoginActivity.this, DashboardUser.class);
                                        startActivity(user_dashboard);
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Mohon maaf terjadi gangguan pada jaringan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regis);
            }
        });
    }
}
