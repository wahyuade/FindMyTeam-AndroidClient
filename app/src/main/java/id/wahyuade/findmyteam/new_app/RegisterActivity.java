package id.wahyuade.findmyteam.new_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.DefaultModel;
import id.wahyuade.findmyteam.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText firstname = (EditText)findViewById(R.id.firstname);
        final EditText lastname = (EditText)findViewById(R.id.lastname);
        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);

        Button register_button = (Button)findViewById(R.id.register_button);
        TextView login = (TextView)findViewById(R.id.login);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname_data,lastname_data, email_data, password_data;
                firstname_data = firstname.getText().toString();
                lastname_data = lastname.getText().toString();
                email_data = email.getText().toString();
                password_data = password.getText().toString();

                if(firstname_data.isEmpty() && lastname_data.isEmpty() && email_data.isEmpty() && password_data.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Mohon isi field register dengan benar", Toast.LENGTH_SHORT).show();
                }else{
                    ApiService.service_post.postRegister(firstname_data, lastname_data, email_data, password_data).enqueue(new Callback<DefaultModel>() {
                        @Override
                        public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if(response.body().isSuccess()){
                                onBackPressed();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultModel> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Mohon maaf terjadi gangguan pada device Anda", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

    }
}
