package id.wahyuade.findmyteam.menu.admin.manage_competition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.ListCompetitionUserModel;
import id.wahyuade.findmyteam.model.DefaultModel;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wahyuade on 08/07/17.
 */

public class ListCompetitionAdminAdapter extends RecyclerView.Adapter<ListCompetitionAdminAdapter.ListCompetition> {
    ArrayList<ListCompetitionUserModel> data_competition;
    Activity activity;
    String x_api_key;

    public ListCompetitionAdminAdapter(ArrayList<ListCompetitionUserModel> data_competition, Activity activity) {
        this.data_competition = data_competition;
        this.activity = activity;
    }

    @Override
    public ListCompetitionAdminAdapter.ListCompetition onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_competition_admin, parent, false);
        return new ListCompetition(itemView);
    }

    @Override
    public void onBindViewHolder(ListCompetitionAdminAdapter.ListCompetition holder, final int position) {
        holder.competition_judul.setText(data_competition.get(position).getJudul());
        holder.competition_body.setText(data_competition.get(position).getBody());
        Glide.with(activity).load(ApiService.BASE_URL+"/"+data_competition.get(position).getFoto()).into(holder.competition_foto);

        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(data_competition.get(position).getDeadline_pendaftaran());
        holder.competition_deadline_pendaftaran.setText("Deadline Pendaftaran : "+formattedDate);

        holder.delete_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder delete_event = new AlertDialog.Builder(activity);
                delete_event.setTitle("Perhatian");
                delete_event.setMessage("Apakah Anda yakin untuk menghapus event ini ?");
                delete_event.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseService db = new DatabaseService(activity);
                        x_api_key = db.getX_api_key();

                        ApiService.service_delete.deleteCompetition(x_api_key, data_competition.get(position).get_id()).enqueue(new Callback<DefaultModel>() {
                            @Override
                            public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                                if(response.body().isSuccess()){
                                    data_competition.remove(position);
                                    ListCompetitionAdminAdapter.this.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultModel> call, Throwable t) {
                                Toast.makeText(activity, "Mohon maaf terjadi gangguan jaringan pada device Anda", Toast.LENGTH_SHORT).show();
                            }
                        });
                        db.close();
                    }
                });
                delete_event.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                delete_event.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_competition.size();
    }

    class ListCompetition extends RecyclerView.ViewHolder{
        ImageView competition_foto;
        TextView competition_judul, competition_body, competition_deadline_pendaftaran;
        Button edit_competition, delete_competition;
        public ListCompetition(View itemView) {
            super(itemView);
            competition_foto = (ImageView)itemView.findViewById(R.id.competition_foto);
            competition_judul = (TextView)itemView.findViewById(R.id.competition_judul);
            competition_body = (TextView)itemView.findViewById(R.id.competition_body);
            competition_deadline_pendaftaran = (TextView)itemView.findViewById(R.id.competition_deadline_pendaftaran);
            delete_competition = (Button)itemView.findViewById(R.id.delete_competition);
        }
    }
}
