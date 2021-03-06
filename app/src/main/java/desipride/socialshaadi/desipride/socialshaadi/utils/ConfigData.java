package desipride.socialshaadi.desipride.socialshaadi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import desipride.socialshaadi.R;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.DEFAULT_HOSTNAME;

/**
 * Created by parth.mehta on 10/12/15.
 */
public final class ConfigData {

    private static String serverHostName;
    public static String getServerHostname(Context context) {
        if(serverHostName == null) {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
            serverHostName = prefs.getString(context.getString(R.string.server_hostname), DEFAULT_HOSTNAME);
        }
        return serverHostName;
    }

    public static void setServerHostname(String newServerHostname, Context context) {
        if(newServerHostname != null) {
            serverHostName = newServerHostname;
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(context.getString(R.string.server_hostname), newServerHostname);
            editor.commit();
        }
    }


}
