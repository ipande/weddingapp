package desipride.socialshaadi.shadiviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.InvalidParameterException;

import desipride.socialshaadi.R;
import desipride.socialshaadi.shadidata.Event;
import desipride.socialshaadi.shadidata.EventData;

public class EventActivity extends ActionBarActivity {

    public static final String EVENT_INDEX = "EVNT_IDX";

    TextView dateTime;
    TextView xDaysToGo;
    TextView locationTitle;
    TextView locationDetails;
    TextView menuTitle;
    TextView menuDetails;
    TextView eventInviteText;
    ImageView eventCoverImage;
    NavigateToEventDialog dialog;
    RelativeLayout addressInfo;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);

        dateTime = (TextView)findViewById(R.id.event_date_time);
        xDaysToGo = (TextView)findViewById(R.id.event_x_days_togo);
        locationTitle = (TextView)findViewById(R.id.event_location_title);
        locationDetails = (TextView)findViewById(R.id.event_location_detail);
        menuTitle = (TextView)findViewById(R.id.event_menu);
        menuDetails = (TextView)findViewById(R.id.event_menu_detail);
        eventInviteText = (TextView)findViewById(R.id.event_invite_details);
        eventCoverImage = (ImageView)findViewById(R.id.event_cover);

        Intent i = getIntent();
        int eventIndex = i.getIntExtra(EVENT_INDEX, -1);
        if(eventIndex == -1) {
            throw new InvalidParameterException("The correct event index is not passed");
        }
        event = EventData.getEvent(eventIndex);

        setTitle(event.getTitle());
        dateTime.setText(event.getDateTimeString());
        locationTitle.setText(event.getAddressTitle());
        locationDetails.setText(event.getAddressDetails());
        Picasso.with(this).load(event.getCoverPage()).into(eventCoverImage);
        addressInfo = (RelativeLayout)findViewById(R.id.address_info);
        addressInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNavigationDialog();
            }
        });
    }

    private void showNavigationDialog() {
        if(dialog == null) {
            dialog = NavigateToEventDialog.getNewDialog(event.getAddressUri());
        }

        dialog.show(getFragmentManager(),"dialog");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

    public static class NavigateToEventDialog extends DialogFragment {

        public static NavigateToEventDialog getNewDialog(Uri locationUri) {
            NavigateToEventDialog fragment = new NavigateToEventDialog();
            Bundle args = new Bundle();
            args.putParcelable("uri", locationUri);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Uri locationUri = getArguments().getParcelable("uri");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.navigate_to_event)
                    .setPositiveButton(R.string.navigate,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW,
                                            locationUri);
                                    startActivity(intent);
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dismiss();
                                }
                            }
                    )
                    .create();
        }
    }
}
