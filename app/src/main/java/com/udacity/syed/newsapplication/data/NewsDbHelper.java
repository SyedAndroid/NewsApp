package com.udacity.syed.newsapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by syed on 2017-07-13.
 */


public class NewsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "news.db";

    public static final int version = 1;

    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + NewsContract.CategoryColumns.CATEGORY_TABLE_NAME + "("
                + NewsContract.CategoryColumns.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsContract.CategoryColumns.COLUMN_NAME + " TEXT NOT NULL, "
                + NewsContract.CategoryColumns.COLUMN_STATUS + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);

        final String CREATE_TABLE_SOURCE = " CREATE TABLE " + NewsContract.SourceColumns.SOURCE_TABLE_NAME + "("
                + NewsContract.SourceColumns.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsContract.SourceColumns.COLUMN_SOURCE_ID + " TEXT NOT NULL,"
                + NewsContract.SourceColumns.SOURCE_TABLE_NAME + " TEXT NOT NULL,"
                + NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + " TEXT NOT NULL,"
                + NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE_SOURCE);

        final String CREATE_TABLE_ARTICLE = " CREATE TABLE " + NewsContract.ArticleColumns.ARTICLE_TABLE_NAME + "("
                + NewsContract.ArticleColumns.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsContract.ArticleColumns.COLUMN_NAME + " TEXT NOT NULL,"
                + NewsContract.ArticleColumns.COLUMN_ARTICLE_URL + " TEXT NOT NULL,"
                + NewsContract.ArticleColumns.COLUMN_AUTHOR + " TEXT ,"
                + NewsContract.ArticleColumns.COLUMN_PUBLISHED + " TEXT NOT NULL,"
                + NewsContract.ArticleColumns.COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + NewsContract.ArticleColumns.COLUMN_PIC_URL + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.CategoryColumns.CATEGORY_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.SourceColumns.SOURCE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.ArticleColumns.ARTICLE_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}

