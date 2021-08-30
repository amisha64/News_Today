package com.example.newstoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newstoday.ModelClasses.ArticlesItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Newsholder> {
    Context context;
   // List<News> newsList;
    List<ArticlesItem> newsList;

    //public NewsAdapter(Context context, List<News> newsList) {
    public NewsAdapter(Context context, List<ArticlesItem> newsList) {

        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public Newsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new Newsholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Newsholder holder, int position) {
//        News item=newsList.get(position);
//        String title ="", description="";
//        title = item.getTitle();
//        description = item.getDesc();
//        holder.newstitle.setText(title);
//        holder.newsdesc.setText(description);

        ArticlesItem singleNewsItem=newsList.get(position);
        String title="",description="",imageUrl="";
        imageUrl = singleNewsItem.getUrlToImage();
        title=singleNewsItem.getTitle();
        description=singleNewsItem.getDescription();
        holder.newstitle.setText(title);
        holder.newsdesc.setText(description);

        if (imageUrl!=null && imageUrl.length()!=0)
        Glide.with(context).load(imageUrl).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class Newsholder extends RecyclerView.ViewHolder {
        TextView newstitle , newsdesc;
        ImageView newsImage;
        public Newsholder(@NonNull View itemView) {
            super(itemView);
            newstitle = itemView.findViewById(R.id.title);
            newsdesc = itemView.findViewById(R.id.desc);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
