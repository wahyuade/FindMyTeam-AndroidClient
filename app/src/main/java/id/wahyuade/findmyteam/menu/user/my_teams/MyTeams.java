package id.wahyuade.findmyteam.menu.user.my_teams;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Team.ListMyTeam;
import id.wahyuade.findmyteam.service.ApiService;
import id.wahyuade.findmyteam.service.DatabaseService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTeams extends Fragment {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    RecyclerView list_my_teams;
    ListMyTeamAdapter adapter;
    ArrayList<ListMyTeam> data_team;
    String x_api_key;
    MultipartBody.Part foto_data;
    FloatingActionButton add_my_team;
    AlertDialog add_team;
    Spinner max_team;
    File file;
    ImageView team_foto;
    String max_team_value;
    RequestBody isi_nama_team, isi_max_team;
    String[] team_max;
    Uri selectedImage;

    public MyTeams() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_teams = inflater.inflate(R.layout.fragment_my_teams, container, false);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        list_my_teams = (RecyclerView)my_teams.findViewById(R.id.list_my_teams);
        list_my_teams.setLayoutManager(layout);
        add_my_team = (FloatingActionButton)my_teams.findViewById(R.id.add_my_team);

        team_max = new String[]{"2", "3", "4", "5", "6", "7"};

        DatabaseService db = new DatabaseService(getActivity());
        x_api_key = db.getX_api_key();
        ApiService.service_get.getListMyTeam(x_api_key).enqueue(new Callback<ArrayList<ListMyTeam>>() {
            @Override
            public void onResponse(Call<ArrayList<ListMyTeam>> call, Response<ArrayList<ListMyTeam>> response) {
                data_team = response.body();
                adapter = new ListMyTeamAdapter(data_team, getActivity(), x_api_key);
                list_my_teams.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ListMyTeam>> call, Throwable t) {
                Toast.makeText(getActivity(),"Mohon maaf terjadi gangguan dengan jaringan Anda", Toast.LENGTH_SHORT).show();
            }
        });

        add_my_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog date_last_registation;
                LayoutInflater inflater = getActivity().getLayoutInflater();
                add_team = new AlertDialog.Builder(getActivity()).create();
                add_team.setView(inflater.inflate(R.layout.add_my_team, null));
                add_team.show();

                ArrayAdapter<String> data_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, team_max);

                team_foto  = (ImageView)add_team.findViewById(R.id.team_foto);
                Button put_team_foto = (Button)add_team.findViewById(R.id.put_team_foto);
                final EditText team_name = (EditText)add_team.findViewById(R.id.team_name);
                final Spinner max_team_spinner = (Spinner)add_team.findViewById(R.id.max_team);

                max_team_spinner.setAdapter(data_adapter);

                Button batal_add_team = (Button)add_team.findViewById(R.id.batal_add_team);
                Button post_add_team = (Button)add_team.findViewById(R.id.post_add_team);

                put_team_foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkPermission();
                        CropImage.activity()
                                .start(getContext(), MyTeams.this);
                    }
                });
                batal_add_team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add_team.dismiss();
                    }
                });

                max_team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        max_team_value = team_max[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                post_add_team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!max_team_value.isEmpty() && !team_name.getText().toString().isEmpty()){
                            isi_nama_team =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, team_name.getText().toString());
                            isi_max_team =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, max_team_value);

                            ApiService.service_post.postCreateTeam(x_api_key, isi_nama_team, isi_max_team, foto_data).enqueue(new Callback<ListMyTeam>() {
                                @Override
                                public void onResponse(Call<ListMyTeam> call, Response<ListMyTeam> response) {
                                    adapter.data_team.add(response.body());
                                    adapter.notifyDataSetChanged();
                                    add_team.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ListMyTeam> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Mohon maaf terjadi gangguan dengan jaringan Anda", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        db.close();
        return my_teams;
    }
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Allow to access storage ?");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            selectedImage = result.getUri();
            String picturePath = selectedImage.getPath();

            file = new File(picturePath);

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(selectedImage.toString()),file);

            // MultipartBody.Part is used to send also the actual file name
            foto_data =
                    MultipartBody.Part.createFormData("team_foto", file.getName(), requestFile);
            team_foto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            team_foto.setVisibility(View.VISIBLE);
        }
    }
}
