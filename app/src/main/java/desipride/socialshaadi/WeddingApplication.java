package desipride.socialshaadi;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by parth.mehta on 11/25/15.
 */
public class WeddingApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
