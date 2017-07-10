package id.wahyuade.findmyteam.menu.user.manage_idea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.menu.user.manage_idea.ListIdeaManageAdapter;
import id.wahyuade.findmyteam.model.ManageIdea.ListIdeaManageModel;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageIdea extends Fragment {
    RecyclerView list_my_idea;
    ListIdeaManageAdapter adapter;
    String x_api_key;

    public ManageIdea() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View manage_idea = inflater.inflate(R.layout.fragment_manage_idea, container, false);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        list_my_idea = (RecyclerView)manage_idea.findViewById(R.id.list_my_idea);
        list_my_idea.setLayoutManager(layout);
        DatabaseService db = new DatabaseService(getActivity());
        x_api_key = db.getX_api_key();
        db.close();

        ApiService.service_get.getListMyIdea(x_api_key).enqueue(new Callback<ArrayList<ListIdeaManageModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ListIdeaManageModel>> call, Response<ArrayList<ListIdeaManageModel>> response) {
                adapter = new ListIdeaManageAdapter(response.body(), getActivity(), x_api_key);
                list_my_idea.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ListIdeaManageModel>> call, Throwable t) {

            }
        });
        // Inflate the layout for this fragment
        return manage_idea;
    }

}
