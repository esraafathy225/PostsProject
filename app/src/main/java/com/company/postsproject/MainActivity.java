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
