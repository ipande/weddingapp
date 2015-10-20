package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.Constants;


public class ShaadiSplashScreen extends Activity{
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shaadi_splash_screen);

        ImageView imagesView = (ImageView) findViewById(R.id.shaadiAnime);

        imagesView.setBackgroundResource(R.drawable.shaadi_animation_list);

        imagesView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(Constants.APP_TAG,"Animation was clicked, proceed to main activity");
                if(v!=null) {
                    Intent i = new Intent(ShaadiSplashScreen.this, ShaadiActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        final AnimationDrawable splashAnimation = (AnimationDrawable) imagesView.getBackground();

        splashAnimation.start();

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
