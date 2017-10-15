package com.udacity.syed.newsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CardView businessCV = (CardView) findViewById(R.id.businessview);
        businessCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("business");
            }
        });
        CardView politicsCV = (CardView) findViewById(R.id.politicsview);
        politicsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("politics");
            }
        });
        CardView entertainmentCV = (CardView) findViewById(R.id.enterview);
        entertainmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("entertainment");
            }
        });
        CardView gamesCV = (CardView) findViewById(R.id.gameview);
        gamesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("gaming");
            }
        });
        CardView sportCV = (CardView) findViewById(R.id.sportsview);
        sportCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("sport");
            }
        });
        CardView techCV = (CardView) findViewById(R.id.techview);
        techCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("technology");
            }
        });
        CardView scienceCV = (CardView) findViewById(R.id.scienceview);
        scienceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("science-and-nature");
            }
        });
        CardView musicCV = (CardView) findViewById(R.id.musicview);
        musicCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("music");
            }
        });
        CardView generalCV = (CardView) findViewById(R.id.generalview);
        generalCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSourceFragment("general");
            }
        });
    }

    public void startSourceFragment(String categoryName) {
        Intent intent = new Intent(this, SourceActivity.class);
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }
}
