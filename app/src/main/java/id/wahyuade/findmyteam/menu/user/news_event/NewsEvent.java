package id.wahyuade.findmyteam.menu.user.news_event;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.ListCompetitionUserModel;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsEvent extends Fragment {
    RecyclerView list_event;
    ArrayList<ListCompetitionUserModel> data_event;
    ListCompetitionUserAdapter adapter;
    String x_api_key;
    public NewsEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View news_event = inflater.inflate(R.layout.fragment_news_event, container, false);

        list_event = (RecyclerView)news_event.findViewById(R.id.list_event);

        DatabaseService db = new DatabaseService(getActivity());
        x_api_key = db.getX_api_key();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        list_event.setLayoutManager(layout);

        ApiService.service_get.getListCompetitionUser(x_api_key).enqueue(new Callback<ArrayList<ListCompetitionUserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ListCompetitionUserModel>> call, Response<ArrayList<ListCompetitionUserModel>> response) {
                data_event = response.body();
                adapter = new ListCompetitionUserAdapter(data_event, getActivity(), x_api_key);
                list_event.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ListCompetitionUserModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf terjadi gangguan jaringan pada device Anda", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        db.close();
        return news_event;
    }

}
