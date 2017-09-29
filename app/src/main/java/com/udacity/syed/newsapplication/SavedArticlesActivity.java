package com.udacity.syed.newsapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ListView;

import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;

import java.util.List;

public class SavedArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        Cursor cursor=getContentResolver().query(NewsProvider.Articles.CONTENT_URI,null,null,null,null);

        ListView listView  =(ListView) findViewById(R.id.listview_article);

        SavedArticlesAdapter articlesAdapter = new SavedArticlesAdapter(this,cursor);
        listView.setAdapter(articlesAdapter);
    }
}
