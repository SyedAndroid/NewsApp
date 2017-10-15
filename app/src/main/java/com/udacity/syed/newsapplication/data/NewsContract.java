package com.udacity.syed.newsapplication.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by shoiab on 2017-09-19.
 */

public class NewsContract {

    public final static String CONTENT_AUTHORITY = "com.udacity.syed.newsapplication";

    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public final static String PATH_CATEGORY = "/category";
    public final static String PATH_SOURCE = "/source";
    public final static String PATH_ARTICLE = "/article";

    public static final class CategoryColumns implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + CONTENT_AUTHORITY + PATH_CATEGORY);
        public static final int CATEGORY_SELECTED = 2;
        public static final int CATEGORY_NOT_SELECTED = 1;
        public static final String CATEGORY_TABLE_NAME = "category";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "category";
        public static final String COLUMN_STATUS = "status";


    }

    public static final class SourceColumns implements BaseColumns {

        public static final Uri CONTENT_URI =
                Uri.parse("content://" + CONTENT_AUTHORITY + PATH_SOURCE);
        public static final int SOURCE_SELECTED = 2;
        public static final int SOURCE_NOT_SELECTED = 1;
        public static final String SOURCE_TABLE_NAME = "source";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SOURCE_NAME = "source";
        public static final String COLUMN_SOURCE_ID = "source_id";
        public static final String COLUMN_CATEGORY_NAME = "source_category_name";
        public static final String COLUMN_COLUMN_STATUS = "status";


    }

    public static final class ArticleColumns implements BaseColumns {

        public static final Uri CONTENT_URI =
                Uri.parse("content://" + CONTENT_AUTHORITY + PATH_ARTICLE);
        public static final String COLUMN_ID = "_id";
        public static final String ARTICLE_TABLE_NAME = "article";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_AUTHOR = "author";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ARTICLE_URL = "article_url";
        public static final String COLUMN_PIC_URL = "pic_url";
        public static final String COLUMN_PUBLISHED = "published";


    }
}
