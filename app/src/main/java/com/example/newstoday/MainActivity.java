package com.example.newstoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newstoday.ModelClasses.ArticlesItem;
import com.example.newstoday.ModelClasses.MainResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ActionBar actionBar;
    private RecyclerView newsrecycler;
    private Retrofit retrofit;
    private Newsinterface newsinterface;
    private BottomNavigationView bottomNavigationView;
    private final String general="general",technology="technology",business="business",health="health",sports="sports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsrecycler=findViewById(R.id.newsrecyclerview);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        progressBar=findViewById(R.id.progressBar);
        //String category=general;
        //News news1 = new News("Covid-19", "Third Wave");
        //newsList.add(news1)


//        List<News> newsList= new ArrayList<>();
//        newsList.add(new News("Covid-19", "Third Wave"));
//        newsList.add(new News("Covid-19", "Third Wave"));
        progressBar.setVisibility(View.VISIBLE);
        newsrecycler.setVisibility(View.INVISIBLE);
        actionBar=getSupportActionBar();
        actionBar.setTitle("General");
        setNavigationListener();
        setNewsRetrofit(general);



    }

    private void setNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                        switch(id){
                            case R.id.general:
                                setNewsRetrofit(general);
                                    return true;
                            case R.id.tech:
                                setNewsRetrofit(technology);
                                return true;
                            case R.id.business:
                                setNewsRetrofit(business);
                                return true;
                            case R.id.sports:
                                setNewsRetrofit(sports);
                                return true;
                            case R.id.health:
                                setNewsRetrofit(health);
                                return true;
                            default:
                                return false;
                        }
            }
        });    }

    private void setNewsRetrofit(String category) {
        char ch=category.charAt(0);
        ch=Character.toUpperCase(ch);
        String rest=category.substring(1);
        progressBar.setVisibility(View.VISIBLE);
        newsrecycler.setVisibility(View.INVISIBLE);
        actionBar.setTitle(ch+rest);
        retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build();
        newsinterface = retrofit.create(Newsinterface.class);
        Call<MainResponse> responseCall = newsinterface.getNewsData(category);
        responseCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.isSuccessful()){
                    MainResponse mainResponse=response.body();
                    List<ArticlesItem> news=mainResponse.getArticles();

                    //for testing
                    //when we don't have data , we use this list of news from news.java

//                    for(ArticlesItem articlesItem : newslist){
//                        Log.d("TAG",articlesItem.getDesc());
//                    }


                    //Adapter set at position where we have the list
                    newsrecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    NewsAdapter adapter= new NewsAdapter(MainActivity.this, news);
                    newsrecycler.setAdapter(adapter); //HERE DATA IS SET
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    newsrecycler.setVisibility(View.VISIBLE);

                }else{

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }

        });
    }
}