package id.wahyuade.findmyteam.menu.user.my_teams;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.user.group_chat.GroupChatActivity;
import id.wahyuade.findmyteam.service.ApiService;

/**
 * Created by wahyuade on 09/07/17.
 */

public class ListMyTeamAdapter extends RecyclerView.Adapter<ListMyTeamAdapter.ListMyTeam> {
    ArrayList<id.wahyuade.findmyteam.model.Team.ListMyTeam> data_team;
    private Activity activity;
    private String x_api_key;

    public ListMyTeamAdapter(ArrayList<id.wahyuade.findmyteam.model.Team.ListMyTeam> data_team, Activity activity, String x_api_key) {
        this.data_team = data_team;
        this.activity = activity;
        this.x_api_key = x_api_key;
    }

    @Override
    public ListMyTeam onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_team, parent, false);
        return new ListMyTeam(itemView);
    }

    @Override
    public void onBindViewHolder(final ListMyTeam holder, final int position) {
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_team.get(position).getTeam_foto()).into(holder.team_foto);
        holder.team_nama.setText(data_team.get(position).getTeam_name());
        holder.list_my_team_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, holder.list_my_team_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.list_my_team_option, popupMenu.getMenu());

                popupMenu.show();
            }
        });

        holder.my_team_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent group_chat = new Intent(activity, GroupChatActivity.class);
                group_chat.putExtra("x_api_key", x_api_key);
                group_chat.putExtra("id_team", data_team.get(position).get_id());
                activity.startActivity(group_chat);
            }
        });
        holder.admin.setText(data_team.get(position).getAdmin());
        holder.member_total.setText(Integer.toString(data_team.get(position).getMember().size()));
    }

    @Override
    public int getItemCount() {
        return data_team.size();
    }

    class ListMyTeam extends RecyclerView.ViewHolder{
        CircleImageView team_foto;
        TextView team_nama;
        ImageView list_my_team_option;
        LinearLayout my_team_item;
        TextView member_total;
        TextView admin;
        public ListMyTeam(View itemView) {
            super(itemView);
            team_foto = (CircleImageView) itemView.findViewById(R.id.team_foto);
            team_nama = (TextView)itemView.findViewById(R.id.team_nama);
            list_my_team_option = (ImageView)itemView.findViewById(R.id.list_my_team_option);
            my_team_item = (LinearLayout)itemView.findViewById(R.id.my_team_item);
            member_total = (TextView)itemView.findViewById(R.id.member_total);
            admin = (TextView)itemView.findViewById(R.id.admin);
        }
    }
}
