package id.wahyuade.findmyteam.menu.user.news_event;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.DetailCompetitionUserModel;
import id.wahyuade.findmyteam.service.ApiService;

/**
 * Created by wahyuade on 09/07/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentList> {
    ArrayList <DetailCompetitionUserModel.Comments> data_comment;
    Activity activity;

    public CommentAdapter(ArrayList<DetailCompetitionUserModel.Comments> data_comment, Activity activity) {
        this.data_comment = data_comment;
        this.activity = activity;
    }

    @Override
    public CommentList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment, parent, false);
        return new CommentList(itemView);
    }

    @Override
    public void onBindViewHolder(CommentList holder, int position) {
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_comment.get(position).getUser_foto()).error(R.drawable.default_profile).into(holder.user_foto);
        holder.user_nama.setText(data_comment.get(position).getFirstname());
        holder.user_comment.setText(data_comment.get(position).getComment());
        holder.comment_time.setReferenceTime(data_comment.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return data_comment.size();
    }

    public class CommentList extends RecyclerView.ViewHolder{
        ImageView user_foto;
        TextView user_nama, user_comment;
        RelativeTimeTextView comment_time;
        public CommentList(View itemView) {
            super(itemView);
            user_foto = (ImageView)itemView.findViewById(R.id.user_foto);
            user_nama = (TextView)itemView.findViewById(R.id.user_nama);
            user_comment = (TextView)itemView.findViewById(R.id.user_comment);
            comment_time = (RelativeTimeTextView)itemView.findViewById(R.id.comment_time);
        }
    }
}
