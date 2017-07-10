package id.wahyuade.findmyteam.menu.admin.manage_competition;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.wahyuade.findmyteam.R;
import id.wahyuade.findmyteam.model.Competition.ListCompetitionUserModel;
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
public class ManageCompetition extends Fragment {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    RecyclerView list_competition;
    FloatingActionButton add_competition;
    ArrayList<ListCompetitionUserModel> data_competition = new ArrayList<>();
    String x_api_key;
    AlertDialog add_comp;
    Uri selectedImage;
    File file;
    ImageView competition_foto;
    MultipartBody.Part foto_data;
    String data_deadline_pendaftaran;
    ListCompetitionAdminAdapter adapter;
    RequestBody judul_form,body_form, deadline_form, is_team_form, min_team_form, max_team_form;
    ProgressDialog progressDialog;

    public ManageCompetition() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View manage_competition = inflater.inflate(R.layout.fragment_manage_competition, container, false);

        DatabaseService db = new DatabaseService(getActivity());
        x_api_key = db.getX_api_key();
        db.close();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        list_competition = (RecyclerView)manage_competition.findViewById(R.id.list_competition);

        list_competition.setLayoutManager(layout);

        ApiService.service_get.getListCompetitionAdmin(x_api_key).enqueue(new Callback<ArrayList<ListCompetitionUserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ListCompetitionUserModel>> call, Response<ArrayList<ListCompetitionUserModel>> response) {
                data_competition = response.body();
                adapter = new ListCompetitionAdminAdapter(data_competition, getActivity());
                list_competition.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ListCompetitionUserModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf terjadi gangguan jaringan pada device Anda", Toast.LENGTH_SHORT).show();
            }
        });

        add_competition = (FloatingActionButton) manage_competition.findViewById(R.id.add_competition);

        add_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog date_last_registation;
                LayoutInflater inflater = getActivity().getLayoutInflater();
                add_comp = new AlertDialog.Builder(getActivity()).create();
                add_comp.setView(inflater.inflate(R.layout.add_competition, null));
                add_comp.show();
                final LinearLayout lin_min_team = (LinearLayout)add_comp.findViewById(R.id.lin_min_team);
                final LinearLayout lin_max_team = (LinearLayout)add_comp.findViewById(R.id.lin_max_team);
                final Switch is_team_switch = (Switch)add_comp.findViewById(R.id.competition_is_team);
                final EditText competition_last_reg = (EditText)add_comp.findViewById(R.id.competition_deadline_pendaftaran);

                final EditText competition_judul = (EditText)add_comp.findViewById(R.id.competition_judul);
                final EditText competition_body = (EditText)add_comp.findViewById(R.id.competition_body);
                EditText competition_deadline_pendaftaran = (EditText)add_comp.findViewById(R.id.competition_deadline_pendaftaran);
                final EditText max_team = (EditText)add_comp.findViewById(R.id.competition_max_team);
                final EditText min_team = (EditText)add_comp.findViewById(R.id.competition_min_team);


                Button put_competition_foto = (Button)add_comp.findViewById(R.id.put_competition_foto);
                Button batal_add_competition = (Button)add_comp.findViewById(R.id.batal_add_competition);
                Button post_add_competition = (Button)add_comp.findViewById(R.id.post_add_competition);

                competition_foto = (ImageView)add_comp.findViewById(R.id.competition_foto);

                put_competition_foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkPermission();
                        CropImage.activity()
                                .start(getContext(), ManageCompetition.this);
                    }
                });

                post_add_competition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.show();
                        if(is_team_switch.isChecked()){
                            if(competition_judul.getText().toString().isEmpty() &&
                                    competition_body.getText().toString().isEmpty() &&
                                    data_deadline_pendaftaran.isEmpty() &&
                                    max_team.getText().toString().isEmpty() &&
                                    min_team.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), "Mohon lengkapi data Event", Toast.LENGTH_SHORT).show();
                            }else{
                                judul_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, competition_judul.getText().toString());
                                body_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, competition_body.getText().toString());
                                deadline_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, data_deadline_pendaftaran);
                                is_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, String.valueOf(is_team_switch.isChecked()));
                                min_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, max_team.getText().toString());
                                max_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, min_team.getText().toString());
                            }
                        }else{
                            if(competition_judul.getText().toString().isEmpty() &&
                                    competition_body.getText().toString().isEmpty() &&
                                    data_deadline_pendaftaran.isEmpty()){
                                Toast.makeText(getActivity(), "Mohon lengkapi data Event", Toast.LENGTH_SHORT).show();
                            }else{
                                judul_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, competition_judul.getText().toString());
                                body_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, competition_body.getText().toString());
                                deadline_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, data_deadline_pendaftaran);
                                is_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, String.valueOf(is_team_switch.isChecked()));
                                min_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, max_team.getText().toString());
                                max_team_form =
                                        RequestBody.create(
                                                okhttp3.MultipartBody.FORM, min_team.getText().toString());
                            }
                        }

                        ApiService.service_post.postCompetition(x_api_key, judul_form, body_form, deadline_form, is_team_form, max_team_form, min_team_form, foto_data).enqueue(new Callback<ListCompetitionUserModel>() {
                            @Override
                            public void onResponse(Call<ListCompetitionUserModel> call, Response<ListCompetitionUserModel> response) {
                                data_competition.add(response.body());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Event Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                add_comp.dismiss();

                            }

                            @Override
                            public void onFailure(Call<ListCompetitionUserModel> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Mohon maaf terjadi gangguan dengan device Anda", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                batal_add_competition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add_comp.dismiss();
                    }
                });

                competition_last_reg.setInputType(InputType.TYPE_NULL);
                competition_last_reg.requestFocus();

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);

                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                competition_last_reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog date_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String last_reg_date = Integer.toString(i2)+"/"+Integer.toString(i1+1)+"/"+Integer.toString(i);
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                                try {
                                    Date date = formatter.parse(last_reg_date);
                                    data_deadline_pendaftaran = Long.toString(date.getTime());
                                    String formattedDate = df.format(date.getTime());
                                    competition_last_reg.setText(formattedDate);
                                }catch (Exception e){

                                }
                            }
                        }, year, month, day);
                        date_dialog.show();
                    }
                });

                is_team_switch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(is_team_switch.isChecked()){
                            lin_max_team.setVisibility(View.VISIBLE);
                            lin_min_team.setVisibility(View.VISIBLE);
                        }else {
                            lin_max_team.setVisibility(View.GONE);
                            lin_min_team.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



        return manage_competition;
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
                    MultipartBody.Part.createFormData("foto", file.getName(), requestFile);
            competition_foto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            competition_foto.setVisibility(View.VISIBLE);
        }
    }
}
