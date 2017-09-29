package com.udacity.syed.newsapplication.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by shoiab on 2017-09-19.
 */

@ContentProvider(
        authority = NewsProvider.AUTHORITY,
        database = NewsDbHelper.class)
public final class NewsProvider {
    public static final String AUTHORITY = "com.udacity.syed.newsapplication.data.NewsProvider";

    @TableEndpoint(table = NewsDbHelper.CATEGORIES)
    public static class Categories {
        @ContentUri(
                path = "categories",
                type = "vnd.android.cursor.dir/categories",
                defaultSort = NewsContract.CategoryColumns.COLUMN_ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/categories");
    }

    @TableEndpoint(table = NewsDbHelper.SOURCES)
    public static class Sources {
        @ContentUri(
                path = "sources",
                type = "vnd.android.cursor.dir/sources",
                defaultSort = NewsContract.SourceColumns.COLUMN_ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/sources");
    }

    @TableEndpoint(table = NewsDbHelper.ARTICLES)
    public static class Articles {
        @ContentUri(
                path = "articles",
                type = "vnd.android.cursor.dir/articles",
                defaultSort = NewsContract.ArticleColumns.COLUMN_ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/articles");
    }

}
