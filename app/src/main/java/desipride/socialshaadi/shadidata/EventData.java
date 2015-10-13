package desipride.socialshaadi.shadidata;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.Constants;

/**
 * Created by parth.mehta on 9/15/15.
 */
public class EventData {
    static private ArrayList<Event> events = null;
    private static final String EVENT_TITLE_BETHAK1 = "Bethak";
    private static final String EVENT_DATE_BETHAK1 = "12/30/2015 8:30 PM";
    private static final int EVENT_THUMBNAIL_BETHAK1 = R.drawable.parth_priya;
    private static final int EVENT_COVER_BETHAK1 = R.drawable.sangeet;

    private static final String EVENT_TITLE_BETHAK2 = "New Year Party";
    private static final String EVENT_DATE_BETHAK2 = "12/31/2015  9:00 PM";
    private static final int EVENT_THUMBNAIL_BETHAK2 = R.drawable.parth_priya;
    private static final int EVENT_COVER_BETHAK2 = R.drawable.parth_priya;

    private static final String EVENT_TITLE_SANGEET = "Sangeet";
    private static final String EVENT_DATE_SANGEET = "1/1/2016 8:00 PM";
    private static final int EVENT_THUMBNAIL_SANGEET = R.drawable.sangeet_thumbnail;
    private static final int EVENT_COVER_SANGEET = R.drawable.sangeet;

    private static final String EVENT_TITLE_RING_CEREMONY = "Ring Ceremony";
    private static final String EVENT_DATE_RING_CEREMONY = "2/1/2015 7:00 PM";
    private static final int EVENT_THUMBNAIL_RING_CEREMONY = R.drawable.parth_priya;
    private static final int EVENT_COVER_RING_CEREMONY = R.drawable.parth_priya;

    private static final String EVENT_TITLE_PRE_WED_DINNER = "Pre Wedding Dinner";
    private static final String EVENT_DATE_PRE_WED_DINNER = "3/1/2015 7:00 PM";
    private static final int EVENT_THUMBNAIL_PRE_WED_DINNER = R.drawable.parth_priya;
    private static final int EVENT_COVER_PRE_WED_DINNER = R.drawable.parth_priya;

    private static final String EVENT_TITLE_GRAH_SHANTI = "Grah Shanti";
    private static final String EVENT_DATE_GRAH_SHANTI_START = "4/1/2015 10:00 AM";
    private static final String EVENT_DATE_GRAH_SHANTI_END = "4/1/2015 12:00 AM";
    private static final int EVENT_THUMBNAIL_GRAH_SHANTI = R.drawable.parth_priya;
    private static final int EVENT_COVER_GRAH_SHANTI = R.drawable.parth_priya;


    private static final String EVENT_TITLE_WEDDING = "Wedding";
    private static final String EVENT_DATE_WEDDING = "5/1/2015 5:30 PM";
    private static final int EVENT_THUMBNAIL_WEDDING = R.drawable.parth_priya;
    private static final int EVENT_COVER_WEDDING = R.drawable.parth_priya;

    private static final String EVENT_ADDRESS_HOME = "15 Arjav Society";
    private static final String EVENT_ADDRESS_HOME_DETAIL = "15 Arjav Society, Opposite GulmorPark Mall, Satellite Road, Ahmedabad 380015";
    private static final String EVENT_ADDRESS_HOME_URI = "google.navigation:q=23.027448,72.509707";

    public static Event getEvent(int index) {
        return getEvents().get(index);
    }

    public static ArrayList<Event> getEvents() {
        if(events == null) {
            events = new ArrayList<Event>(10);
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.MM_DD_YY);
            Date dateStart = null;
            Date dateEnd = null;
            Uri uri = null;
            try {
                dateStart = dateFormat.parse(EVENT_DATE_BETHAK1);
                Event event = new Event(EVENT_TITLE_BETHAK1,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_BETHAK1,EVENT_COVER_BETHAK1);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_BETHAK2);
                event = new Event(EVENT_TITLE_BETHAK2,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_BETHAK2,EVENT_COVER_BETHAK2);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_SANGEET);
                event = new Event(EVENT_TITLE_SANGEET,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_SANGEET,EVENT_COVER_SANGEET);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_RING_CEREMONY);
                event = new Event(EVENT_TITLE_RING_CEREMONY,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_RING_CEREMONY,EVENT_COVER_RING_CEREMONY);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_PRE_WED_DINNER);
                event = new Event(EVENT_TITLE_PRE_WED_DINNER,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_PRE_WED_DINNER, EVENT_COVER_PRE_WED_DINNER);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_GRAH_SHANTI_START);
                dateEnd = dateFormat.parse(EVENT_DATE_GRAH_SHANTI_END);
                event = new Event(EVENT_TITLE_GRAH_SHANTI,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_GRAH_SHANTI,EVENT_COVER_GRAH_SHANTI);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_WEDDING);
                event = new Event(EVENT_TITLE_WEDDING,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_WEDDING,EVENT_COVER_WEDDING);
                events.add(event);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return events;
    }

}
