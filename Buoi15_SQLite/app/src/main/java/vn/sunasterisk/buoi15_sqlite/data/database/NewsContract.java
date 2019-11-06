package vn.sunasterisk.buoi15_sqlite.data.database;

import android.provider.BaseColumns;

public final class NewsContract {

    private NewsContract() {
    }

    public static class NewEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
    }
}
