package desipride.socialshaadi.shadiviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.security.InvalidParameterException;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.DateUtil;
import desipride.socialshaadi.desipride.socialshaadi.utils.ImageLoader;
import desipride.socialshaadi.shadidata.Event;
import desipride.socialshaadi.shadidata.EventData;

public class EventActivity extends ActionBarActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String EVENT_INDEX = "EVNT_IDX";
    private static final String TAG = EventActivity.class.getSimpleName();

    TextView dateTime;
    TextView xDaysToGo;
    TextView locationTitle;
    TextView locationDetails;
    TextView menuTitle;

    TextView eventInviteText;
    ImageView eventCoverImage;
    NavigateToEventDialog dialog;
    RelativeLayout addressInfo;
    Event event;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);

        dateTime = (TextView)findViewById(R.id.event_data_time);
        xDaysToGo = (TextView)findViewById(R.id.event_x_days_togo);
        locationTitle = (TextView)findViewById(R.id.event_location_title);
        locationDetails = (TextView)findViewById(R.id.event_location_detail);
        menuTitle = (TextView)findViewById(R.id.event_menu);
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

        addressInfo = (RelativeLayout)findViewById(R.id.address_info);
        addressInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNavigationDialog();
            }
        });

        linearLayout = (LinearLayout)findViewById(R.id.event_info_activity_layout);

        eventInviteText.setText(event.getEventDetails());
        menuTitle.setText(event.getFood());
        int daysTogo = DateUtil.getTimeRemaining(event.getStartTime());
        xDaysToGo.setText(daysTogo + " days to go");
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);

    }


    @Override
    public void onGlobalLayout() {
        linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int width = linearLayout.getWidth();
        int height = linearLayout.getHeight();
        Log.d(TAG, "onGlobalLayout W:" + width + ", H:" + height);
        ImageLoader.loadImage(eventCoverImage, event.getCoverPage(), getResources(), 0, width);

    }

    private void showNavigationDialog() {
        if(dialog == null) {
            dialog = NavigateToEventDialog.getNewDialog(event.getAddressUri());
        }

        dialog.show(getFragmentManager(), "dialog");
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
