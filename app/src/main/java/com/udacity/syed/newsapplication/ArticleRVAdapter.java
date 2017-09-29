package com.udacity.syed.newsapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    List<ImageView> images = new ArrayList<>();

     private ArticleClickListener mArticleclickListener;

    public ArticleRVAdapter(List<Article> articles, ArticleClickListener articleClickListener) {
        this.articles = articles;
             this.mArticleclickListener = articleClickListener;
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
        Context context = holder.itemView.getContext();
        Article simpleArticle = articles.get(position);

        if (simpleArticle.getImgURL() == null) {
            holder.thumbnail.setVisibility(View.GONE);
        } else {

            Picasso.with(context).load(simpleArticle.getImgURL()).fit().into(holder.thumbnail);
        }
        holder.headline.setText(simpleArticle.getTitle());


        holder.leadin.setText(simpleArticle.getAuthor());
        images.add(position, holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

      public interface ArticleClickListener {
          void articleClickListener(int position, Article article);
        }

    public class ArticleViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView thumbnail;
        @BindView(R.id.teaser_headline)
        TextView headline;
        @BindView(R.id.teaser_leadin)
        TextView leadin;



        public ArticleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Article simpleArticle = articles.get(adapterPosition);

             mArticleclickListener.articleClickListener(adapterPosition,simpleArticle);
        }

    }
}

