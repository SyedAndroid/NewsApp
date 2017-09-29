package com.udacity.syed.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.udacity.syed.newsapplication.data.NewsContract;

/**
 * Created by shoiab on 2017-09-25.
 */

public class SavedArticlesAdapter extends CursorAdapter {

    public SavedArticlesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_cursor, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ImageView imageView = (ImageView) view.findViewById(R.id.cursor_image);
        TextView title = (TextView)view.findViewById(R.id.cursor_headline);
        TextView author = (TextView)view.findViewById(R.id.cursor_leadin);
        TextView detail = (TextView)view.findViewById(R.id.detail_cursor);
        final String url = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleColumns.COLUMN_PIC_URL));
        final String articleUrl = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleColumns.COLUMN_ARTICLE_URL));

        Picasso.with(context).load(url).fit().into(imageView);
        title.setText(cursor.getString(cursor.getColumnIndex(NewsContract.ArticleColumns.COLUMN_NAME)));
        author.setText(cursor.getString(cursor.getColumnIndex(NewsContract.ArticleColumns.COLUMN_AUTHOR)));
        detail.setText(cursor.getString(cursor.getColumnIndex(NewsContract.ArticleColumns.COLUMN_DESCRIPTION)));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(articleUrl));
                context.startActivity(intent);
            }
        });

    }
}
