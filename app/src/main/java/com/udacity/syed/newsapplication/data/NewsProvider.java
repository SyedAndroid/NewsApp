package com.udacity.syed.newsapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class NewsProvider extends ContentProvider {

    public static final int PATH_CATEGORY = 100;
    public static final int PATH_CATEGORY_ID = 101;
    public static final int PATH_SOURCE = 102;
    public static final int PATH_SOURCE_ID = 103;
    public static final int PATH_ARTICLE = 104;
    public static final int PATH_ARTICLE_ID = 105;
    private static final UriMatcher matcher = buildUriMatcher();
    NewsDbHelper dbHelper;

    public static UriMatcher buildUriMatcher() {

        final String authority = NewsContract.CONTENT_AUTHORITY;

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(authority, NewsContract.PATH_CATEGORY, PATH_CATEGORY);
        matcher.addURI(authority, NewsContract.PATH_CATEGORY + "/#", PATH_CATEGORY_ID);
        matcher.addURI(authority, NewsContract.PATH_SOURCE + "/#", PATH_SOURCE_ID);
        matcher.addURI(authority, NewsContract.PATH_SOURCE, PATH_SOURCE);
        matcher.addURI(authority, NewsContract.PATH_ARTICLE, PATH_ARTICLE);
        matcher.addURI(authority, NewsContract.PATH_ARTICLE + "/#", PATH_ARTICLE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new NewsDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionARGS, @Nullable String sort) {
        int match = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;
        switch (match) {
            case PATH_CATEGORY:
                cursor = db.query(NewsContract.CategoryColumns.CATEGORY_TABLE_NAME, projection, selection, selectionARGS, null, null, sort);
                break;
            case PATH_CATEGORY_ID:
                long _id = ContentUris.parseId(uri);

                cursor = db.query(NewsContract.CategoryColumns.CATEGORY_TABLE_NAME, projection, NewsContract.CategoryColumns.COLUMN_ID + "=?", new String[]{String.valueOf(_id)}, null, null, sort);
                break;

            case PATH_SOURCE:
                cursor = db.query(NewsContract.SourceColumns.SOURCE_TABLE_NAME, projection, selection, selectionARGS, null, null, sort);
                break;
            case PATH_SOURCE_ID:
                long source_id = ContentUris.parseId(uri);
                cursor = db.query(NewsContract.SourceColumns.SOURCE_TABLE_NAME, projection, NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + "=?", new String[]{String.valueOf(source_id)}, null, null, sort);
                break;
            case PATH_ARTICLE:
                cursor = db.query(NewsContract.ArticleColumns.ARTICLE_TABLE_NAME, projection, selection, selectionARGS, null, null, sort);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;
        long row_inserted;
        switch (match) {
            case PATH_CATEGORY:
                row_inserted = db.insert(NewsContract.CategoryColumns.CATEGORY_TABLE_NAME, null, contentValues);
                if (row_inserted > 0) {
                    returnUri = ContentUris.withAppendedId(NewsContract.CategoryColumns.CONTENT_URI, row_inserted);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case PATH_SOURCE:
                row_inserted = db.insert(NewsContract.SourceColumns.SOURCE_TABLE_NAME, null, contentValues);
                if (row_inserted > 0) {
                    returnUri = ContentUris.withAppendedId(NewsContract.SourceColumns.CONTENT_URI, row_inserted);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case PATH_ARTICLE:
                row_inserted = db.insert(NewsContract.ArticleColumns.ARTICLE_TABLE_NAME, null, contentValues);
                if (row_inserted > 0) {
                    returnUri = ContentUris.withAppendedId(NewsContract.ArticleColumns.CONTENT_URI, row_inserted);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        int match = matcher.match(uri);

        switch (match) {
            case PATH_CATEGORY:
                rows = db.delete(NewsContract.CategoryColumns.CATEGORY_TABLE_NAME, selection, selectionArgs);
                break;
            case PATH_SOURCE:
                rows = db.delete(NewsContract.SourceColumns.SOURCE_TABLE_NAME, selection, selectionArgs);
                break;
            case PATH_ARTICLE:
                rows = db.delete(NewsContract.ArticleColumns.ARTICLE_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        int match = matcher.match(uri);

        switch (match) {
            case PATH_CATEGORY:
                rows = db.update(NewsContract.CategoryColumns.CATEGORY_TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case PATH_SOURCE:
                rows = db.update(NewsContract.SourceColumns.SOURCE_TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case PATH_ARTICLE:
                rows = db.update(NewsContract.ArticleColumns.ARTICLE_TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        return rows;
    }
}