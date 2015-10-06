package desipride.socialshaadi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import desipride.socialshaadi.shadiviews.ShaadiActivity;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button signInButton;
    LoginButton loginButton;
    private GraphUser user;
    private Button displayPicsButton;
    private Context context;

    static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "SocialShaadi On Create");

        signInButton = (Button) findViewById(R.id.sign_in_button);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
                Log.d(TAG,"Set User info changed user is "  + user);

            }
        });
        signInButton.setOnClickListener(this);
        displayPicsButton = (Button) findViewById(R.id.displayPicsButton);
        context = getApplicationContext();

    }

    @Override
    public void onResume() {
        super.onResume();


        // Start the display pics activity
        displayPicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayPicsIntent = new Intent(context,HelloGridView.class);
                MainActivity.this.startActivity(displayPicsIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Intent intent = new Intent(this, ShaadiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
