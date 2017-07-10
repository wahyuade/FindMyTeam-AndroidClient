package id.wahyuade.findmyteam.menu.user.manage_idea;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.ManageIdea.ListIdeaManageModel;
import id.wahyuade.findmyteam.service.ApiService;

/**
 * Created by wahyuade on 10/07/17.
 */

public class ListIdeaManageAdapter extends RecyclerView.Adapter<ListIdeaManageAdapter.ListIdea>{
    ArrayList<ListIdeaManageModel> data_ide;
    Activity activity;
    String x_api_key;

    public ListIdeaManageAdapter(ArrayList<ListIdeaManageModel> data_ide, Activity activity, String x_api_key) {
        this.data_ide = data_ide;
        this.activity = activity;
        this.x_api_key = x_api_key;
    }

    @Override
    public ListIdea onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_idea_manage, parent, false);
        return new ListIdea(itemView);
    }

    @Override
    public void onBindViewHolder(ListIdea holder, int position) {
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_ide.get(position).getX_api_key()+".jpg").error(R.drawable.default_profile).into(holder.user_foto);
        holder.user_idea.setText(data_ide.get(position).getBody());
        holder.user_nama.setText(data_ide.get(position).getName());
        holder.competition_judul.setText(data_ide.get(position).getJudul());
    }

    @Override
    public int getItemCount() {
        return data_ide.size();
    }

    public class ListIdea extends RecyclerView.ViewHolder{
        CircleImageView user_foto;
        TextView user_nama, user_idea, competition_judul;
        Button edit_button, delete_button;
        public ListIdea(View itemView) {
            super(itemView);
            user_foto = (CircleImageView)itemView.findViewById(R.id.user_foto);
            user_nama = (TextView)itemView.findViewById(R.id.user_nama);
            user_idea = (TextView)itemView.findViewById(R.id.user_idea);
            edit_button = (Button)itemView.findViewById(R.id.edit_button);
            delete_button = (Button)itemView.findViewById(R.id.delete_button);
            competition_judul = (TextView)itemView.findViewById(R.id.competition_judul);
        }
    }

}
