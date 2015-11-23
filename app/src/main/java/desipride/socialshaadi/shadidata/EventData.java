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
    private static final int EVENT_THUMBNAIL_BETHAK = R.drawable.bethak_thumb;
    private static final int EVENT_COVER_BETHAK = R.drawable.bethak;
    private static final String EVENT_FOOD_BETHAK = "Street food";
    private static final String EVENT_DETAIL_BETHAK = "Usher in the new year with dance music food and friends. " +
            "Join us in welcoming the new year and the beginning of the wedding festivities.";

    private static final String EVENT_TITLE_SANGEET = "Family Drama";
    private static final String EVENT_DATE_SANGEET = "1/1/2016 6:00 PM";
    private static final int EVENT_THUMBNAIL_SANGEET = R.drawable.family_drama_thumb;
    private static final int EVENT_COVER_SANGEET = R.drawable.family_drama;
    private static final String EVENT_FOOD_SANGEET = "Ahmedabadi street food specials";
    private static final String EVENT_DETAIL_SANGEET = "This is where we celebrate union of the two souls with music and dance. " +
            "This event features singing sensations from the mehta and shah family and super star dance performances by our friends and family." +
            " After the drama let your hair down and rock to the DJs dance numbers. And get your Gujju on coz we are going to Garba all night!!.";

    private static final String EVENT_TITLE_RING_CEREMONY = "Ring Ceremony & Kathak Dance";
    private static final String EVENT_DATE_RING_CEREMONY = "1/2/2016 7:00 PM";
    private static final int EVENT_THUMBNAIL_RING_CEREMONY = R.drawable.ring_ceremony_thumb;
    private static final int EVENT_COVER_RING_CEREMONY = R.drawable.ring_ceremony;
    private static final String EVENT_FOOD_RING_CEREMONY = "Rajasthani";
    private static final String EVENT_DETAIL_RING_CEREMONY = "Witness the moment when Parth and Priya officially mark their territories " +
            "by putting a ring on each other. Ring ceremony is followed a Kathak Dance presentation - “Angika - Journeys in Love” " +
            "featuring noted Kathak dancer Sanjukta Sinha.";

    private static final String EVENT_TITLE_PRE_WED_DINNER = "Reception";
    private static final String EVENT_DATE_PRE_WED_DINNER = "1/3/2016 7:00 PM";
    private static final int EVENT_THUMBNAIL_PRE_WED_DINNER = R.drawable.reception_1_thumb;
    private static final int EVENT_COVER_PRE_WED_DINNER = R.drawable.reception_1;
    private static final String EVENT_FOOD_PRE_WED_DINNER = "Global and Indian food";
    private static final String EVENT_DETAIL_PRE_WED_DINNER = "Join us in celebrating the wedding with this Grand Wedding Reception. " +
            "Enjoy the unique food with live music. Wish the bride and groom well for their future. Help them gear up for this new chapter in " +
            "their life with your own experiences and innovative wedding jokes ;) !";

    private static final String EVENT_TITLE_GRAH_SHANTI = "Ganesh, Grah Shanti and Mehndi";
    private static final String EVENT_DATE_GRAH_SHANTI_START = "1/4/2016 4:00 PM";
    private static final int EVENT_THUMBNAIL_GRAH_SHANTI = R.drawable.ganesh_thumb;
    private static final int EVENT_COVER_GRAH_SHANTI = R.drawable.ganesh;
    private static final String EVENT_FOOD_GRAH_SHANTI = "Gujarati";
    private static final String EVENT_DETAIL_GRAH_SHANTI = "Every Indian wedding begins with worshipping Lord Ganesh. " +
            "Following this will be a Grah Shanti ceremony and Mehndi for all the girls.";


    private static final String EVENT_TITLE_WEDDING = "Wedding";
    private static final String EVENT_DATE_WEDDING = "1/5/2016 3:00 PM";
    private static final int EVENT_THUMBNAIL_WEDDING = R.drawable.wedding_image;
    private static final int EVENT_COVER_WEDDING = R.drawable.wedding_image;
    private static final String EVENT_FOOD_WEDDING = "Gujarati";
    private static final String EVENT_DETAIL_WEDDING = "Today is the day we have been waiting for. The day both families will officially become one and the bride and groom" +
            " commit to each other for life. Dance in the baarat, fiercely compete for the groom’s shoes, shower the bride and groom " +
            "with flowers and share our joy.";

    private static final String EVENT_ADDRESS_HOME = "15 Arjav Society";
    private static final String EVENT_ADDRESS_HOME_DETAIL = "15 Arjav Society, Opposite GulmorPark Mall, Satellite Road, Ahmedabad 380015";
    private static final String EVENT_ADDRESS_HOME_URI = "google.navigation:q=23.027448,72.509707";

    private static final String EVENT_ADDRESS_KKFARM = "K K Farm";
    private static final String EVENT_ADDRESS_KKFARM_DETAIL = "PRL Colony, Bodakdev, Ahmedabad 380054";
    private static final String EVENT_ADDRESS_KKFARM_URI = "google.navigation:q=23.0410318,72.434358";

    private static final String EVENT_ADDRESS_ARJUNFARM = "Arjun Farms";
    private static final String EVENT_ADDRESS_ARJUNFARM_DETAIL = "Arjun Farm, Shilaj - Rancharda Road, Ahmedabad 382165";
    private static final String EVENT_ADDRESS_ARJUNFARM_URI = "google.navigation:q=23.1029261,72.4332615,15.58z";

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
                        EVENT_THUMBNAIL_BETHAK, EVENT_COVER_BETHAK,EVENT_FOOD_BETHAK,EVENT_DETAIL_BETHAK);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_SANGEET);
                event = new Event(EVENT_TITLE_SANGEET,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_SANGEET,EVENT_COVER_SANGEET,EVENT_FOOD_SANGEET,EVENT_DETAIL_SANGEET);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_RING_CEREMONY);
                event = new Event(EVENT_TITLE_RING_CEREMONY,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_RING_CEREMONY,EVENT_COVER_RING_CEREMONY,EVENT_FOOD_RING_CEREMONY,EVENT_DETAIL_RING_CEREMONY);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_PRE_WED_DINNER);
                event = new Event(EVENT_TITLE_PRE_WED_DINNER,dateStart,null,EVENT_ADDRESS_KKFARM,
                        EVENT_ADDRESS_KKFARM_DETAIL,Uri.parse(EVENT_ADDRESS_KKFARM_URI),
                        EVENT_THUMBNAIL_PRE_WED_DINNER, EVENT_COVER_PRE_WED_DINNER, EVENT_FOOD_PRE_WED_DINNER, EVENT_DETAIL_PRE_WED_DINNER);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_GRAH_SHANTI_START);
                event = new Event(EVENT_TITLE_GRAH_SHANTI,dateStart,null,EVENT_ADDRESS_HOME,
                        EVENT_ADDRESS_HOME_DETAIL,Uri.parse(EVENT_ADDRESS_HOME_URI),
                        EVENT_THUMBNAIL_GRAH_SHANTI,EVENT_COVER_GRAH_SHANTI, EVENT_FOOD_GRAH_SHANTI, EVENT_DETAIL_GRAH_SHANTI);
                events.add(event);

                dateStart = dateFormat.parse(EVENT_DATE_WEDDING);
                event = new Event(EVENT_TITLE_WEDDING,dateStart,null,EVENT_ADDRESS_ARJUNFARM,
                        EVENT_ADDRESS_ARJUNFARM_DETAIL,Uri.parse(EVENT_ADDRESS_ARJUNFARM_URI),
                        EVENT_THUMBNAIL_WEDDING,EVENT_COVER_WEDDING, EVENT_FOOD_WEDDING,EVENT_DETAIL_WEDDING);
                events.add(event);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

}
