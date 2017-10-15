package com.udacity.syed.newsapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.udacity.syed.newsapplication.data.NewsContract;

public class SavedArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        Cursor cursor = getContentResolver().query(NewsContract.ArticleColumns.CONTENT_URI, null, null, null, null);

        ListView listView = (ListView) findViewById(R.id.listview_article);

        SavedArticlesAdapter articlesAdapter = new SavedArticlesAdapter(this, cursor);
        listView.setAdapter(articlesAdapter);
    }
}
