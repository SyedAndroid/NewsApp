package com.udacity.syed.newsapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shoiab on 2017-09-18.
 */


/**
 * Created by syed on 2017-09-01.
 */


public class ArticleRVAdapter extends RecyclerView.Adapter<ArticleRVAdapter.ArticleViewHolder> {
    List<Article> articles;
    Activity activity;


    public ArticleRVAdapter(List<Article> articles, Activity activity) {
        this.activity = activity;
        this.articles = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final Article simpleArticle = articles.get(position);
        holder.headline.setText(simpleArticle.getTitle());


        holder.leadin.setText(simpleArticle.getAuthor());
        if (simpleArticle.getImgURL() == null) {
            holder.thumbnail.setVisibility(View.GONE);
        } else {

            Picasso.with(context).load(simpleArticle.getImgURL()).fit().into(holder.thumbnail);
            holder.thumbnail.setContentDescription(simpleArticle.getTitle());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailArticleActivity.class);
                intent.putExtra("title", simpleArticle.getTitle());
                intent.putExtra("detail", simpleArticle.getDescription());
                intent.putExtra("pic", simpleArticle.getImgURL());
                intent.putExtra("url", simpleArticle.getArticleURL());
                intent.putExtra("date", simpleArticle.getPublishedTime());
                intent.putExtra("author", simpleArticle.getAuthor());

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, (View) holder.thumbnail, "picture");
                context.startActivity(intent, options.toBundle());
            }
        });

    }


    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView thumbnail;
        @BindView(R.id.teaser_headline)
        TextView headline;
        @BindView(R.id.teaser_leadin)
        TextView leadin;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
}

