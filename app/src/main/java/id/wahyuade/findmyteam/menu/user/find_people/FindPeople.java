package id.wahyuade.findmyteam.menu.user.find_people;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.FindPeople.ListFindPeopleModel;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPeople extends Fragment {
    Spinner list_skill;
    RecyclerView list_user;
    String x_api_key;
    ListFindPeopleAdapter list_people_adapter;

    public FindPeople() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View find_people = inflater.inflate(R.layout.fragment_find_people, container, false);
        DatabaseService db = new DatabaseService(getActivity());
        x_api_key = db.getX_api_key();
        db.close();

        list_skill = (Spinner)find_people.findViewById(R.id.list_skill);
        list_user = (RecyclerView)find_people.findViewById(R.id.list_user);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        list_user.setLayoutManager(layout);

        String[] data_skill = {"All","Design", "Front End Developer", "Back End Developer", "Project Manager", "Networking"};
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, data_skill);
        list_skill.setAdapter(adapter);
        
        list_skill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ApiService.service_get.getListFindPeople(x_api_key, Integer.toString(i)).enqueue(new Callback<ArrayList<ListFindPeopleModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ListFindPeopleModel>> call, Response<ArrayList<ListFindPeopleModel>> response) {
                        list_people_adapter = new ListFindPeopleAdapter(response.body(), getActivity(), x_api_key);
                        list_user.setAdapter(list_people_adapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ListFindPeopleModel>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        return find_people;
    }

}
