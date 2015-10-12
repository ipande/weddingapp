package desipride.socialshaadi.shadidata;

import android.provider.BaseColumns;

/**
 * Created by parth.mehta on 10/10/15.
 */
public final class DbContract {

    public DbContract() {

    }

    public static abstract class NewsFeedTable implements BaseColumns {
        public static final String TABLE_NAME = "newsfeed";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_CAPTION = "caption";
        public static final String COLUMN_NAME_MEDIATYPE = "mediatype";
    }
}
