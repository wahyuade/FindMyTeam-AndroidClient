package id.wahyuade.findmyteam.menu.user.group_chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Team.ChatModel;
import id.wahyuade.findmyteam.model.Team.DetailTeamModel;
import id.wahyuade.findmyteam.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupChatActivity extends Activity {
    String x_api_key;
    String id_team;

    EditText input_chat;
    FloatingActionButton post_chat;
    RecyclerView chat_list;
    ImageView group_chat_option, team_foto;
    TextView team_admin, team_name;

    ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        x_api_key = getIntent().getStringExtra("x_api_key");
        id_team = getIntent().getStringExtra("id_team");

        input_chat = (EditText)findViewById(R.id.input_chat);
        post_chat = (FloatingActionButton)findViewById(R.id.post_chat);
        chat_list = (RecyclerView)findViewById(R.id.chat_list);
        group_chat_option = (ImageView)findViewById(R.id.grub_chat_option);
        team_foto = (ImageView)findViewById(R.id.team_foto);
        team_admin = (TextView)findViewById(R.id.team_admin);
        team_name = (TextView)findViewById(R.id.team_name);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getApplicationContext());
        chat_list.setLayoutManager(layout);

        ApiService.service_get.getDetailTeam(x_api_key,id_team).enqueue(new Callback<DetailTeamModel>() {
            @Override
            public void onResponse(Call<DetailTeamModel> call, Response<DetailTeamModel> response) {
                adapter = new ChatAdapter(response.body().getChat(), x_api_key, GroupChatActivity.this);
                chat_list.setAdapter(adapter);
                Glide.with(GroupChatActivity.this).load(ApiService.BASE_URL+"/"+response.body().getTeam_foto()).into(team_foto);
                team_name.setText(response.body().getTeam_name());
                team_admin.setText(response.body().getAdmin());
            }

            @Override
            public void onFailure(Call<DetailTeamModel> call, Throwable t) {
                Toast.makeText(GroupChatActivity.this, "Mohon maaf terjadi gangguan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
        post_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chat = input_chat.getText().toString();
                if(!chat.isEmpty()){
                    ApiService.service_post.postChatGroup(x_api_key,id_team, chat).enqueue(new Callback<ChatModel>() {
                        @Override
                        public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                            input_chat.setText(null);
                            adapter.data_chat.add(response.body());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ChatModel> call, Throwable t) {
                            Toast.makeText(GroupChatActivity.this, "Mohon maaf terjadi gangguan jaringan pada device Anda", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(GroupChatActivity.this, "Tidak dapat mengirim pesan kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
