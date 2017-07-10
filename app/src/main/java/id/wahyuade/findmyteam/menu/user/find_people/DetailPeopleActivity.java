package id.wahyuade.findmyteam.menu.user.find_people;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.FindPeople.DetailPeopleModel;
import id.wahyuade.findmyteam.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPeopleActivity extends Activity {
    String x_api_key;
    String id_user;

    CircleImageView user_foto;
    TextView user_nama;
    TextView user_skill;
    TextView user_profile_description;
    ImageView detail_find_people_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);
        x_api_key = getIntent().getStringExtra("x_api_key");
        id_user = getIntent().getStringExtra("id_user");

        user_foto = (CircleImageView)findViewById(R.id.user_foto);
        user_nama = (TextView)findViewById(R.id.user_nama);
        user_skill = (TextView)findViewById(R.id.user_skill);
        user_profile_description = (TextView)findViewById(R.id.user_profile_description);
        detail_find_people_option = (ImageView)findViewById(R.id.detail_people_option);

        ApiService.service_get.getDetailPeople(x_api_key, id_user).enqueue(new Callback<DetailPeopleModel>() {
            @Override
            public void onResponse(Call<DetailPeopleModel> call, Response<DetailPeopleModel> response) {
                Glide.with(DetailPeopleActivity.this).load(ApiService.BASE_URL+"/"+response.body().getX_api_key()+".jpg").error(R.drawable.default_profile).into(user_foto);
                user_nama.setText(response.body().getFirstname()+" "+response.body().getLastname());
                user_skill.setText(response.body().getSkill_id());
            }

            @Override
            public void onFailure(Call<DetailPeopleModel> call, Throwable t) {

            }
        });

        detail_find_people_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DetailPeopleActivity.this, detail_find_people_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.detail_find_people_option, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }
}
