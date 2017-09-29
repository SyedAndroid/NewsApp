package com.udacity.syed.newsapplication.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by shoiab on 2017-09-19.
 */

public class NewsContract {

    public interface CategoryColumns {

        public static final int CATEGORY_SELECTED = 1;
        public static final int CATEGORY_NOT_SELECTED = 0;


        @DataType(DataType.Type.INTEGER)
        @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
        @AutoIncrement
        public static final String COLUMN_ID = "_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_NAME = "category";

        @DataType(DataType.Type.INTEGER)
        @DefaultValue("0")
        public static final String COLUMN_STATUS = "status";
    }

    public interface SourceColumns {

        @DataType(DataType.Type.INTEGER)
        @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
        @AutoIncrement
        public static final String COLUMN_ID = "_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_SOURCE_NAME = "source";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_SOURCE_ID = "source_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_CATEGORY_NAME = "source_category_name";

    }

    public interface ArticleColumns {

        @DataType(DataType.Type.INTEGER)
        @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
        @AutoIncrement
        public static final String COLUMN_ID = "_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_NAME = "name";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_AUTHOR = "author";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_DESCRIPTION = "description";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_ARTICLE_URL = "article_url";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_PIC_URL = "pic_url";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_PUBLISHED = "published";
    }
}
