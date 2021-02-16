package com.company.postsproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String link="https://pastebin.com/raw/wgkJgazE";
    URL url;
    InputStream inputStream;
    String result;
    HttpURLConnection urlConnection;
    ArrayList<UserDetails> users;
    int likes;
    String name;
    String profileImage;
    UserAdapter adapter;
    Button button,button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.list);
        button=findViewById(R.id.btn);
        button1=findViewById(R.id.btn1);
        button2=findViewById(R.id.btn2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit= new Retrofit.Builder().baseUrl("https://pastebin.com/raw/")
                        .addConverterFactory(GsonConverterFactory.create()).build();

                RetrofitService retrofitService=retrofit.create(RetrofitService.class);

                retrofitService.getPosts().enqueue(new Callback<ArrayList<UserData>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserData>> call, Response<ArrayList<UserData>> response) {

                        PostAdapter postAdapter=new PostAdapter(MainActivity.this,response.body());
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(postAdapter);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserData>> call, Throwable t) {

                    }
                });

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            url=new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {

                            urlConnection= (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setReadTimeout(15000);


                            inputStream=urlConnection.getInputStream();


                            int c=0;
                            StringBuffer buffer=new StringBuffer();
                            int responseCode = urlConnection.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                while ((c = inputStream.read()) != -1) {
                                    buffer.append((char) c);
                                }
                            }
                            result=buffer.toString();

                            users=new ArrayList<>();

                            JSONArray array=new JSONArray(result);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);

                                likes=object.getInt("likes");
                                JSONObject object1=object.getJSONObject("user");
                                name=object1.getString("name");
                                JSONObject object2=object1.getJSONObject("profile_image");
                                profileImage=object2.getString("medium");

                                users.add(new UserDetails(name,likes,profileImage));
                            }

                            inputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter=new UserAdapter(MainActivity.this,users);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        });
    }

}
