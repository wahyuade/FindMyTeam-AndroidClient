package id.wahyuade.findmyteam.service;

import java.util.ArrayList;

import id.wahyuade.findmyteam.model.Competition.DetailCompetitionUserModel;
import id.wahyuade.findmyteam.model.Competition.ListCompetitionUserModel;
import id.wahyuade.findmyteam.model.DefaultModel;
import id.wahyuade.findmyteam.model.FindPeople.DetailPeopleModel;
import id.wahyuade.findmyteam.model.FindPeople.ListFindPeopleModel;
import id.wahyuade.findmyteam.model.Login.LoginModel;
import id.wahyuade.findmyteam.model.ManageIdea.ListIdeaManageModel;
import id.wahyuade.findmyteam.model.Team.ChatModel;
import id.wahyuade.findmyteam.model.Team.DetailTeamModel;
import id.wahyuade.findmyteam.model.Team.ListMyTeam;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by wahyuade on 07/07/17.
 */

public class ApiService {
    public static String BASE_URL = "http://192.168.43.10:3000";

    public static PostService service_post = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.PostService.class);

    public static GetService service_get = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.GetService.class);

    public static DeleteService service_delete = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.DeleteService.class);

    public interface GetService{
        // ADMIN
        @GET("/api_admin/list_competition")
        Call<ArrayList<ListCompetitionUserModel>> getListCompetitionAdmin(@Header("x_api_key") String x_api_key);

        // USER
        @GET("/api_user/list_competition")
        Call<ArrayList<ListCompetitionUserModel>> getListCompetitionUser(@Header("x_api_key") String x_api_key);

        @GET("/api_user/list_my_team")
        Call<ArrayList<ListMyTeam>> getListMyTeam(@Header("x_api_key") String x_api_key);

        @GET("/api_user/detail_competition")
        Call<DetailCompetitionUserModel> getDetailCompetition(@Header("x_api_key") String x_api_key, @Query("_id") String id);

        @GET("/api_user/detail_team")
        Call<DetailTeamModel> getDetailTeam(@Header("x_api_key") String x_api_key, @Query("_id") String id_team);

        @GET("/api_user/list_user")
        Call<ArrayList<ListFindPeopleModel>> getListFindPeople(@Header("x_api_key") String x_api_key, @Query("skill_id") String skill_id);

        @GET("/api_user/detail_user")
        Call<DetailPeopleModel> getDetailPeople(@Header("x_api_key") String x_api_key, @Query("_id") String id_profile);

        @GET("/api_user/list_my_idea")
        Call<ArrayList<ListIdeaManageModel>> getListMyIdea(@Header("x_api_key") String x_api_key);
    }

    public interface PostService{
        @FormUrlEncoded
        @POST("/login")
        Call<LoginModel> postLogin(@Field("email") String email, @Field("password") String password);

        // ADMIN

        @Multipart
        @POST("/api_admin/upload_competition")
        Call<ListCompetitionUserModel> postCompetition(
                @Header("x_api_key") String x_api_key,
                @Part("judul") RequestBody judul,
                @Part("body") RequestBody body,
                @Part("deadline_pendaftaran") RequestBody deadline_pendaftaran,
                @Part("is_team") RequestBody is_team,
                @Part("max_team") RequestBody max_team,
                @Part("min_team") RequestBody min_team,
                @Part MultipartBody.Part foto);

        // USER
        @FormUrlEncoded
        @POST("/register")
        Call<DefaultModel> postRegister(@Field("firstname") String firstname, @Field("lastname") String lastname, @Field("email") String email, @Field("password") String password);

        @Multipart
        @POST("/api_user/create_team")
        Call<ListMyTeam> postCreateTeam(@Header("x_api_key") String x_api_key, @Part("team_name") RequestBody team_name, @Part("max_member") RequestBody max_member, @Part MultipartBody.Part team_foto);

        @FormUrlEncoded
        @POST("/api_user/post_comment")
        Call<DetailCompetitionUserModel.Comments> postComment(@Header("x_api_key") String x_api_key, @Field("_id_competition") String id_competition, @Field("comment") String comment);

        @FormUrlEncoded
        @POST("/api_user/group_chat")
        Call<ChatModel> postChatGroup(@Header("x_api_key") String x_api_key, @Field("_id_team") String id_team, @Field("message") String message);

        @FormUrlEncoded
        @POST("/api_user/register_my_team")
        Call<DefaultModel> postRegisterMyTeam(@Header("x_api_key") String x_api_key, @Field("_id_team") String id_comp, @Field("_id_competition") String id_competition);
    }

    public interface DeleteService{
        // ADMIN
        @DELETE("/api_admin/delete_competition")
        Call<DefaultModel> deleteCompetition(@Header("x_api_key") String x_api_key, @Query("_id") String _id);

        // USER
    }

}
