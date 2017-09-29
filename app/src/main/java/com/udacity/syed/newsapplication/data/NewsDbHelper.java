package com.udacity.syed.newsapplication.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by shoiab on 2017-09-19.
 */
@Database(version = NewsDbHelper.VERSION)
public class NewsDbHelper {
    public static final int VERSION = 1;

    @Table(NewsContract.CategoryColumns.class)
    public static final String CATEGORIES = "categories";

    @Table(NewsContract.SourceColumns.class)
    public static final String SOURCES = "sources";

    @Table(NewsContract.ArticleColumns.class)
    public static final String ARTICLES = "articles";
}
