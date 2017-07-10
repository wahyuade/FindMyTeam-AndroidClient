package id.wahyuade.findmyteam.menu.user.group_chat;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Team.ChatModel;
import id.wahyuade.findmyteam.service.ApiService;
import me.himanshusoni.chatmessageview.ChatMessageView;

/**
 * Created by wahyuade on 10/07/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Chat> {
    ArrayList<ChatModel> data_chat;
    String x_api_key;
    Activity activity;

    public ChatAdapter(ArrayList<ChatModel> data_chat, String x_api_key, Activity activity) {
        this.data_chat = data_chat;
        this.x_api_key = x_api_key;
        this.activity = activity;
    }

    @Override
    public Chat onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent, false);
        return new Chat(itemView);
    }

    @Override
    public void onBindViewHolder(Chat holder, int position) {
        if(data_chat.get(position).get_id_user().matches(x_api_key)){
            Glide.with(activity).load(ApiService.BASE_URL+"/"+data_chat.get(position).getUser_foto()).error(R.drawable.default_profile).into(holder.img_kanan);
            holder.img_kanan.setVisibility(View.VISIBLE);
            holder.img_kiri.setVisibility(View.GONE);
            holder.time_sent_kanan.setReferenceTime(data_chat.get(position).getDate());
            holder.user_nama_kanan.setText(data_chat.get(position).getName());
            holder.isi_chat.setArrowPosition(ChatMessageView.ArrowPosition.RIGHT);
            holder.chat_detail_kiri.setVisibility(View.GONE);
            holder.img_kiri.setVisibility(View.GONE);
            holder.chat_detail_kanan.setVisibility(View.VISIBLE);
            holder.chat.setGravity(Gravity.RIGHT);
            holder.gambar_kiri.setVisibility(View.GONE);

        }else{
            Glide.with(activity).load(ApiService.BASE_URL+"/"+data_chat.get(position).getUser_foto()).error(R.drawable.default_profile).into(holder.img_kiri);
            holder.img_kiri.setVisibility(View.VISIBLE);
            holder.img_kanan.setVisibility(View.GONE);
            holder.time_sent_kiri.setReferenceTime(data_chat.get(position).getDate());
            holder.user_nama_kiri.setText(data_chat.get(position).getName());
            holder.isi_chat.setArrowPosition(ChatMessageView.ArrowPosition.LEFT);
            holder.chat_detail_kanan.setVisibility(View.GONE);
            holder.img_kanan.setVisibility(View.GONE);
            holder.chat_detail_kiri.setVisibility(View.VISIBLE);
            holder.chat.setGravity(Gravity.LEFT);
            holder.gambar_kanan.setVisibility(View.GONE);
        }
        holder.message.setText(data_chat.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return data_chat.size();
    }

    public class Chat extends RecyclerView.ViewHolder{
        CircleImageView img_kanan, img_kiri;
        LinearLayout chat_detail_kiri, chat_detail_kanan, chat, gambar_kiri, gambar_kanan;
        RelativeTimeTextView time_sent_kiri, time_sent_kanan;
        ChatMessageView isi_chat;
        TextView message, user_nama_kiri, user_nama_kanan;
        public Chat(View itemView) {
            super(itemView);
            img_kiri = (CircleImageView)itemView.findViewById(R.id.img_kiri);
            img_kanan = (CircleImageView)itemView.findViewById(R.id.img_kanan);
            chat_detail_kiri = (LinearLayout)itemView.findViewById(R.id.chat_detail_kiri);
            chat_detail_kanan = (LinearLayout)itemView.findViewById(R.id.chat_detail_kanan);
            time_sent_kiri = (RelativeTimeTextView)itemView.findViewById(R.id.time_sent_kiri);
            time_sent_kanan = (RelativeTimeTextView)itemView.findViewById(R.id.time_sent_kanan);
            isi_chat = (ChatMessageView)itemView.findViewById(R.id.isi_chat);
            message = (TextView)itemView.findViewById(R.id.message);
            user_nama_kiri = (TextView)itemView.findViewById(R.id.user_nama_kiri);
            user_nama_kanan = (TextView)itemView.findViewById(R.id.user_nama_kanan);
            chat = (LinearLayout)itemView.findViewById(R.id.chat);
            gambar_kiri = (LinearLayout)itemView.findViewById(R.id.gambar_kiri);
            gambar_kanan = (LinearLayout)itemView.findViewById(R.id.gambar_kanan);
        }
    }
}
