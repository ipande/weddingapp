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

    private static final String EVENT_TITLE_BETHAK = "Bethak";
    private static final String EVENT_DATE_BETHAK = "12/31/2015  9:00 PM";
    private static final int EVENT_THUMBNAIL_BETHAK = R.drawable.parth_priya;
    private static final int EVENT_COVER_BETHAK = R.drawable.parth_priya;

    private static final String EVENT_TITLE_SANGEET = "Family Drama";
    private static final String EVENT_DATE_SANGEET = "1/1/2016 6:00 PM";
    private static final int EVENT_THUMBNAIL_SANGEET = R.drawable.sangeet_thumbnail;
    private static final int EVENT_COVER_SANGEET = R.drawable.sangeet;

    private static final String EVENT_TITLE_RING_CEREMONY = "Ring Ceremony & Kathak Dance - Angika";
    private static final String EVENT_DATE_RING_CEREMONY = "1/2/2015 7:00 PM";
    private static final int EVENT_THUMBNAIL_RING_CEREMONY = R.drawable.parth_priya;
    private static final int EVENT_COVER_RING_CEREMONY = R.drawable.parth_priya;

    private static final String EVENT_TITLE_PRE_WED_DINNER = "Reception";
    private static final String EVENT_DATE_PRE_WED_DINNER = "1/3/2015 7:00 PM";
    private static final int EVENT_THUMBNAIL_PRE_WED_DINNER = R.drawable.parth_priya;
    private static final int EVENT_COVER_PRE_WED_DINNER = R.drawable.parth_priya;

    private static final String EVENT_TITLE_GRAH_SHANTI = "Ganesh, Grah Shanti and Mehndi";
    private static final String EVENT_DATE_GRAH_SHANTI_START = "1/4/2015 4:00 PM";
    private static final int EVENT_THUMBNAIL_GRAH_SHANTI = R.drawable.ganesh;
    private static final int EVENT_COVER_GRAH_SHANTI = R.drawable.ganesh;


    private static final String EVENT_TITLE_WEDDING = "Wedding";
    private static final String EVENT_DATE_WEDDING = "5/1/2015 3:00 PM";
    private static final int EVENT_THUMBNAIL_WEDDING = R.drawable.wedding_image;
    private static final int EVENT_COVER_WEDDING = R.drawable.wedding_image;

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
            Event event = null;
            try {

                dateStart = dateFormat.parse(EVENT_DATE_BETHAK);
                event = new Event(EVENT_TITLE_BETHAK,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_BETHAK, EVENT_COVER_BETHAK);
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
