package id.wahyuade.findmyteam.menu.user.find_people;

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
import id.wahyuade.findmyteam.model.FindPeople.ListFindPeopleModel;
import id.wahyuade.findmyteam.service.ApiService;

/**
 * Created by wahyuade on 10/07/17.
 */

public class ListFindPeopleAdapter extends RecyclerView.Adapter<ListFindPeopleAdapter.ListPeople> {
    ArrayList<ListFindPeopleModel> data_people;
    Activity activity;
    String x_api_key;

    public ListFindPeopleAdapter(ArrayList<ListFindPeopleModel> data_people, Activity activity, String x_api_key) {
        this.data_people = data_people;
        this.activity = activity;
        this.x_api_key = x_api_key;
    }

    @Override
    public ListPeople onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_people, parent, false);
        return new ListPeople(itemView);
    }

    @Override
    public void onBindViewHolder(final ListPeople holder, final int position) {
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_people.get(position).getX_api_key()+".jpg").error(R.drawable.default_profile).into(holder.user_foto);
        holder.user_name.setText(data_people.get(position).getFirstname()+" "+data_people.get(position).getLastname());
        holder.user_skill.setText(data_people.get(position).getSkill_id());
        holder.list_find_people_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, holder.list_find_people_option);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.list_find_people_option, popupMenu.getMenu());

                popupMenu.show();
            }
        });

        holder.find_people_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail_profile = new Intent(activity, DetailPeopleActivity.class);
                detail_profile.putExtra("x_api_key", x_api_key);
                detail_profile.putExtra("id_user", data_people.get(position).get_id());
                activity.startActivity(detail_profile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_people.size();
    }

    public class ListPeople extends RecyclerView.ViewHolder{
        TextView user_name, user_skill;
        CircleImageView user_foto;
        ImageView list_find_people_option;
        LinearLayout find_people_item;
        public ListPeople(View itemView) {
            super(itemView);
            user_name = (TextView)itemView.findViewById(R.id.user_name);
            user_skill = (TextView)itemView.findViewById(R.id.user_skill);
            user_foto = (CircleImageView)itemView.findViewById(R.id.user_foto);
            list_find_people_option = (ImageView)itemView.findViewById(R.id.list_find_people_option);
            find_people_item = (LinearLayout)itemView.findViewById(R.id.find_people_item);
        }
    }
}
