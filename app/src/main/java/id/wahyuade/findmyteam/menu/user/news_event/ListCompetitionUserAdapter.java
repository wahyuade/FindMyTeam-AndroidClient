package id.wahyuade.findmyteam.menu.user.news_event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.ListCompetitionUserModel;
import id.wahyuade.findmyteam.model.Team.ListMyTeam;
import id.wahyuade.findmyteam.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wahyuade on 09/07/17.
 */

public class ListCompetitionUserAdapter extends RecyclerView.Adapter<ListCompetitionUserAdapter.ListCompetition> {
    ArrayList<ListCompetitionUserModel> data_event;
    Activity activity;
    String x_api_key;
    AlertDialog register_my_team;

    public ListCompetitionUserAdapter(ArrayList<ListCompetitionUserModel> data_event, Activity activity, String x_api_key) {
        this.data_event = data_event;
        this.activity = activity;
        this.x_api_key = x_api_key;
    }

    @Override
    public ListCompetition onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_competition_user, parent, false);
        return new ListCompetition(itemView);
    }

    @Override
    public void onBindViewHolder(final ListCompetition holder, final int position) {
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_event.get(position).getFoto()).into(holder.competition_foto);
        holder.competition_judul.setText(data_event.get(position).getJudul());
        holder.competition_body.setText(data_event.get(position).getBody());
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(data_event.get(position).getDeadline_pendaftaran());
        holder.competition_deadline_pendaftaran.setText("Deadline Pendaftaran : "+formattedDate);

        holder.event_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail_event = new Intent(activity, DetailEvent.class);
                detail_event.putExtra("id_competition", data_event.get(position).get_id());
                detail_event.putExtra("x_api_key", x_api_key);
                activity.startActivity(detail_event);
            }
        });

        holder.event_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, holder.event_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.event_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getTitle().toString()){
                            case "Register my team":
                                ApiService.service_get.getListMyTeam(x_api_key).enqueue(new Callback<ArrayList<ListMyTeam>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<ListMyTeam>> call, Response<ArrayList<ListMyTeam>> response) {
                                        LayoutInflater inflater = activity.getLayoutInflater();
                                        register_my_team = new AlertDialog.Builder(activity).create();
                                        register_my_team.setView(inflater.inflate(R.layout.show_my_team, null));
                                        register_my_team.show();

                                        RecyclerView list_my_team = (RecyclerView)register_my_team.findViewById(R.id.list_my_team);

                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<ListMyTeam>> call, Throwable t) {

                                    }
                                });
                                break;
                            case "View Detail":
                                Intent detail_event = new Intent(activity, DetailEvent.class);
                                detail_event.putExtra("id_competition", data_event.get(position).get_id());
                                detail_event.putExtra("x_api_key", x_api_key);
                                activity.startActivity(detail_event);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.comments.setText(Integer.toString(data_event.get(position).getComments()));
        holder.competition_ideas.setText(Integer.toString(data_event.get(position).getIdeas()));
        holder.competition_joined_team.setText(Integer.toString(data_event.get(position).getJoined_team()));
    }

    @Override
    public int getItemCount() {
        return data_event.size();
    }

    class ListCompetition extends RecyclerView.ViewHolder{
        ImageView competition_foto;
        TextView competition_judul, competition_body, competition_deadline_pendaftaran, comments, competition_ideas, competition_joined_team;
        LinearLayout event_item;
        Button event_option;
        public ListCompetition(View itemView) {
            super(itemView);
            event_item = (LinearLayout)itemView.findViewById(R.id.event_item);
            event_option = (Button) itemView.findViewById(R.id.event_option);
            competition_foto = (ImageView)itemView.findViewById(R.id.competition_foto);
            competition_judul = (TextView)itemView.findViewById(R.id.competition_judul);
            competition_body = (TextView)itemView.findViewById(R.id.competition_body);
            competition_deadline_pendaftaran = (TextView)itemView.findViewById(R.id.competition_deadline_pendaftaran);
            comments = (TextView)itemView.findViewById(R.id.comments);
            competition_ideas = (TextView)itemView.findViewById(R.id.competition_ideas);
            competition_joined_team = (TextView)itemView.findViewById(R.id.competition_joined_team);
        }
    }
}
