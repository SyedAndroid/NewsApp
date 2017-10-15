package com.udacity.syed.newsapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.syed.newsapplication.data.NewsContract;

import java.util.ArrayList;
import java.util.List;

public class SourceActivity extends AppCompatActivity {
    FrameLayout sourceFragmentLayout;
    List<Source> storedSources;
    String categoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        categoryName = getIntent().getStringExtra("categoryName");
        ImageView imageView = (ImageView) findViewById(R.id.category_pic);
        int drawableId = getResources().getIdentifier(categoryName.replaceAll("-", "_"), "drawable", getPackageName());

        Picasso.with(this).load(drawableId).into(imageView);
        String selection = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "= ?";
        Cursor cursor = getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, selection, new String[]{categoryName}, null);
        List<Source> sources = new ArrayList<>();
        int nameIndex = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_NAME);
        int idIndex = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_ID);
        int statusId = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            sources.add(new Source(cursor.getString(nameIndex), cursor.getString(idIndex), cursor.getInt(statusId)));
            cursor.moveToNext();
        }
        storedSources = new ArrayList<>();
        cursor.close();
        MySourceItemRecyclerViewAdapter rv = new MySourceItemRecyclerViewAdapter(this, sources, new MySourceItemRecyclerViewAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Source item) {
                storedSources.add(item);
                String selection = NewsContract.SourceColumns.COLUMN_SOURCE_ID + "='" + item.getId() + "'";
                ContentValues values1 = new ContentValues();
                values1.put(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS, NewsContract.SourceColumns.SOURCE_SELECTED);
                long rows = getContentResolver().update(NewsContract.SourceColumns.CONTENT_URI, values1, selection, null);
            }

            @Override
            public void onItemUncheck(Source item) {
                storedSources.remove(item);
                String condition = NewsContract.SourceColumns.COLUMN_SOURCE_ID + "='" + item.getId() + "'";
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS, NewsContract.SourceColumns.SOURCE_NOT_SELECTED);
                long rows2 = getContentResolver().update(NewsContract.SourceColumns.CONTENT_URI, contentValues2, condition, null);
            }

        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view_source);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(rv);
    }


    public void SaveSources(View view) {

        String condition1 = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + categoryName + "' AND ";
        String condition2 = NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + "  =  ' " + NewsContract.SourceColumns.SOURCE_SELECTED + "'";
        Cursor cursor = getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, condition1 + condition2, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int count = cursor.getCount();
        if (count != 0) {
            String removeCategory = NewsContract.CategoryColumns.COLUMN_NAME + "='" + categoryName + "'";
            ContentValues removeValues = new ContentValues();
            removeValues.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_SELECTED);
            getContentResolver().update(NewsContract.CategoryColumns.CONTENT_URI, removeValues, removeCategory, null);
        } else {
            String removeCategory = NewsContract.CategoryColumns.COLUMN_NAME + "='" + categoryName + "'";
            ContentValues removeValues = new ContentValues();
            removeValues.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_NOT_SELECTED);
            getContentResolver().update(NewsContract.CategoryColumns.CONTENT_URI, removeValues, removeCategory, null);
        }


        cursor.close();
        finish();

    }
}
