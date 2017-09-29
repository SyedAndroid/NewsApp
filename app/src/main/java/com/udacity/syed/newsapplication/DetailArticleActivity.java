package com.udacity.syed.newsapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;
import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;

import java.util.zip.Inflater;

public class DetailArticleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent= getIntent();
        setTitle("ARTICLE DETAILS");

        ImageView imageView = (ImageView) findViewById(R.id.image_details) ;
        TextView title = (TextView)  findViewById(R.id.detail_headline);
        TextView  author= (TextView)  findViewById(R.id.detail_leadin);
        TextView detail =(TextView)  findViewById(R.id.article_body);
        final String sendTitle = intent.getStringExtra("title");
        final String sendUrl = intent.getStringExtra("url");
        final  String aut=intent.getStringExtra("author");
        String details =  intent.getStringExtra("detail");
        String pic =intent.getStringExtra("pic");
        Picasso.with(this).load(pic).fit().into(imageView);
         title.setText(sendTitle);
        author.setText(aut);
         detail.setText(details);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton openUrl  =(FloatingActionButton)  findViewById(R.id.open);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                share.putExtra(Intent.EXTRA_SUBJECT,sendTitle );
                share.putExtra(Intent.EXTRA_TEXT,sendUrl );
                 startActivity(Intent.createChooser(share, "Share link!"));
                }
        });
        openUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(sendUrl));
                startActivity(openUrl);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.detail_menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_save) {
            ContentValues contentValues = new ContentValues();
            Intent intent = getIntent();
            String sendTitle = intent.getStringExtra("title");
            String sendUrl = intent.getStringExtra("url");
            String aut = intent.getStringExtra("author");
            String details = intent.getStringExtra("detail");
            String pic = intent.getStringExtra("pic");
            contentValues.put(NewsContract.ArticleColumns.COLUMN_NAME,sendTitle);
            contentValues.put(NewsContract.ArticleColumns.COLUMN_DESCRIPTION,details);
            contentValues.put(NewsContract.ArticleColumns.COLUMN_ARTICLE_URL,sendUrl);
            contentValues.put(NewsContract.ArticleColumns.COLUMN_PIC_URL,pic);
            contentValues.put(NewsContract.ArticleColumns.COLUMN_AUTHOR,aut);
            contentValues.put(NewsContract.ArticleColumns.COLUMN_PUBLISHED,intent.getStringExtra("date"));
            getContentResolver().insert(NewsProvider.Articles.CONTENT_URI,contentValues);
        }
        return super.onOptionsItemSelected(item);
    }
}
