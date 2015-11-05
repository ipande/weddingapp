package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.Constants;


public class ShaadiSplashScreen extends Activity{
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 9000;
    private Handler timeoutHandler;
    private Runnable timeoutRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shaadi_splash_screen);

        LinearLayout splashScreenLayout = (LinearLayout)findViewById(R.id.splashscreen_layout);

        ImageView imagesView = (ImageView) findViewById(R.id.shaadiAnime);

        imagesView.setBackgroundResource(R.drawable.shaadi_animation_list);



        final AnimationDrawable splashAnimation = (AnimationDrawable) imagesView.getBackground();

        splashAnimation.start();
        timeoutRunnable = new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(ShaadiSplashScreen.this, ShaadiActivity.class);
                startActivity(i);

                finish();
            }
        };
        timeoutHandler = new Handler();
        timeoutHandler.postDelayed(timeoutRunnable, SPLASH_TIME_OUT);

        splashScreenLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(Constants.APP_TAG, "Animation was clicked, proceed to main activity");
                if (v != null) {
                    if (timeoutHandler != null) {
                        timeoutHandler.removeCallbacks(timeoutRunnable);
                    }

                    Intent i = new Intent(ShaadiSplashScreen.this, ShaadiActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
