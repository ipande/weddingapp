package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import desipride.socialshaadi.R;


public class ShaadiSplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 7000;
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shaadi_splash_screen);

        ImageView gyroView = (ImageView) findViewById(R.id.shaadiAnime);

        gyroView.setBackgroundResource(R.drawable.shaadi_animation_list);

        final AnimationDrawable gyroAnimation = (AnimationDrawable) gyroView.getBackground();

        gyroAnimation.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i = new Intent(ShaadiSplashScreen.this, ShaadiActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
