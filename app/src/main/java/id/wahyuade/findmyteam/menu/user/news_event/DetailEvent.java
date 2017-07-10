package id.wahyuade.findmyteam.menu.user.news_event;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.DetailCompetitionUserModel;
import id.wahyuade.findmyteam.model.DefaultModel;
import id.wahyuade.findmyteam.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEvent extends Activity {
    String x_api_key;
    String id_competition;

    ImageView competition_foto;
    TextView competition_judul, competition_body, min_team, max_team, comments, competition_deadline_pendaftaran, competition_joined_team, competition_ideas;
    EditText input_comment;
    FloatingActionButton post_comment;
    Button join_event;
    RecyclerView comment_list;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        x_api_key = getIntent().getStringExtra("x_api_key");
        id_competition = getIntent().getStringExtra("id_competition");

        competition_judul = (TextView)findViewById(R.id.competition_judul);
        competition_body = (TextView)findViewById(R.id.competition_body);
        min_team = (TextView)findViewById(R.id.min_team);
        max_team = (TextView)findViewById(R.id.max_team);
        competition_foto = (ImageView)findViewById(R.id.competition_foto);
        input_comment = (EditText)findViewById(R.id.input_comment);
        post_comment = (FloatingActionButton) findViewById(R.id.post_comment);
        comments = (TextView)findViewById(R.id.comments);
        competition_ideas = (TextView)findViewById(R.id.competition_ideas);
        competition_joined_team = (TextView)findViewById(R.id.competition_joined_team);
        comment_list = (RecyclerView)findViewById(R.id.comment_list);
        competition_deadline_pendaftaran = (TextView)findViewById(R.id.competition_deadline_pendaftaran);
        join_event = (Button)findViewById(R.id.join_event);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getApplicationContext());
        comment_list.setLayoutManager(layout);
        comment_list.setNestedScrollingEnabled(false);

        ApiService.service_get.getDetailCompetition(x_api_key, id_competition).enqueue(new Callback<DetailCompetitionUserModel>() {
            @Override
            public void onResponse(Call<DetailCompetitionUserModel> call, Response<DetailCompetitionUserModel> response) {
                Glide.with(DetailEvent.this).load(ApiService.BASE_URL+"/"+response.body().getFoto()).into(competition_foto);
                competition_judul.setText(response.body().getJudul());
                competition_body.setText(response.body().getBody());
                min_team.setText(Integer.toString(response.body().getMin_team()));
                max_team.setText(Integer.toString(response.body().getMax_team()));
                comments.setText(Integer.toString(response.body().getComments().size()));
                competition_deadline_pendaftaran.setText("Deadline Registasi : 12 Jan 2017");
                competition_joined_team.setText(Integer.toString(response.body().getJoined_team().size()));
                competition_ideas.setText(Integer.toString(response.body().getIdeas().size()));
                adapter = new CommentAdapter(response.body().getComments(), DetailEvent.this);
                comment_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DetailCompetitionUserModel> call, Throwable t) {
                Toast.makeText(DetailEvent.this, "Mohon maaf terjadi gangguan jaringan dengan device Anda", Toast.LENGTH_SHORT).show();
            }
        });

        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = input_comment.getText().toString();
                if(comment.isEmpty()){
                    Toast.makeText(DetailEvent.this, "Please fill input comment first", Toast.LENGTH_SHORT).show();
                }else{
                    ApiService.service_post.postComment(x_api_key, id_competition, comment).enqueue(new Callback<DetailCompetitionUserModel.Comments>() {
                        @Override
                        public void onResponse(Call<DetailCompetitionUserModel.Comments> call, Response<DetailCompetitionUserModel.Comments> response) {
                            if(response.isSuccessful()){
                                input_comment.setText(null);
                                adapter.data_comment.add(response.body());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(DetailEvent.this, "Berhasil menambahkan komentar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DetailCompetitionUserModel.Comments> call, Throwable t) {
                            Toast.makeText(DetailEvent.this, "Mohon maaf terjadi gangguan jaringan dengan device Anda", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DetailEvent.this, join_event);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.detal_event_option, popupMenu.getMenu());



                popupMenu.show();
            }
        });
    }
}
