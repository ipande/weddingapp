package desipride.socialshaadi.desipride.socialshaadi.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by parth.mehta on 10/31/15.
 */
public class DateUtil {
    public static int getTimeRemaining(Date date)
    {
        Calendar sDate = toCalendar(date.getTime());
        Calendar eDate = toCalendar(System.currentTimeMillis());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);

        return (int)(diff / (24 * 60 * 60 * 1000));
    }

    private static Calendar toCalendar(long timestamp)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
