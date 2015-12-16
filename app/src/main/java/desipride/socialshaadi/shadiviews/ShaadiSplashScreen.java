package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.Constants;
import desipride.socialshaadi.desipride.socialshaadi.utils.InviteCode;

import static desipride.socialshaadi.R.drawable.shaadi_animation_list;


public class ShaadiSplashScreen extends Activity implements View.OnClickListener, TextWatcher{
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 9000;
    private Handler timeoutHandler;
    private Runnable timeoutRunnable;
    private LinearLayout inviteCodeInputLayout;
    private TextView inviteCodeText;
    private EditText inviteCodeFirstHalf;
    private EditText inviteCodeSecHalf;
    private TextWatcher textWatcher;
    private ImageView invalidInviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shaadi_splash_screen);

        LinearLayout splashScreenLayout = (LinearLayout)findViewById(R.id.splashscreen_layout);

        ImageView imagesView = (ImageView) findViewById(R.id.shaadiAnime);

        imagesView.setBackgroundResource(shaadi_animation_list);

        final AnimationDrawable splashAnimation = (AnimationDrawable) imagesView.getBackground();

        splashAnimation.start();

        inviteCodeInputLayout = (LinearLayout)findViewById(R.id.inviteCodeInput);
        inviteCodeText = (TextView)findViewById(R.id.enterInviteCode);

        String inviteCode = InviteCode.getInviteCode(this);
        if(inviteCode.equals(Constants.DEFAULT_INVITE_CODE)) {
            setInviteCodeInputVisibility(true);

            inviteCodeFirstHalf = (EditText)findViewById(R.id.inviteCodeFirstHalf);
            inviteCodeSecHalf = (EditText)findViewById(R.id.inviteCodeSecHalf);
            inviteCodeFirstHalf.addTextChangedListener(this);
            inviteCodeSecHalf.addTextChangedListener(this);
            invalidInviteCode = (ImageView)findViewById(R.id.invalidInviteCode);

        } else {
            setInviteCodeInputVisibility(false);
            setSplashTimeOut();
            splashScreenLayout.setOnClickListener(this);
        }

    }

    private void setInviteCodeInputVisibility(boolean visibility) {
        if(visibility) {
            inviteCodeInputLayout.setVisibility(View.VISIBLE);
            inviteCodeText.setVisibility(View.VISIBLE);
        } else {
            inviteCodeInputLayout.setVisibility(View.GONE);
            inviteCodeText.setVisibility(View.GONE);
        }

    }

    private void setSplashTimeOut() {
        timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                launchShaadiActivity();
            }
        };
        timeoutHandler = new Handler();
        timeoutHandler.postDelayed(timeoutRunnable, SPLASH_TIME_OUT);

    }

    private void launchShaadiActivity() {
        Intent i = new Intent(ShaadiSplashScreen.this, ShaadiActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        Log.d(Constants.APP_TAG, "Animation was clicked, proceed to main activity");
        if (v != null) {
            if (timeoutHandler != null) {
                timeoutHandler.removeCallbacks(timeoutRunnable);
            }

            launchShaadiActivity();
        }
    }

    private void checkInviteCode() {
        String inviteCode1 = inviteCodeFirstHalf.getText().toString();
        String inviteCode2 = inviteCodeSecHalf.getText().toString();
        String inviteCode = inviteCode1 + inviteCode2;
        if(inviteCode1.length() == 3) {
            inviteCodeSecHalf.requestFocus();
        }
        if(InviteCode.isInviteCodeValid(inviteCode)) {
            invalidInviteCode.setVisibility(View.GONE);
            InviteCode.setInviteCode(inviteCode, this);
            launchShaadiActivity();
        } else {
            invalidInviteCode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkInviteCode();
    }
}
