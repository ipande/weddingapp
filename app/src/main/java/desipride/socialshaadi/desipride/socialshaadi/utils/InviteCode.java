package desipride.socialshaadi.desipride.socialshaadi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;

import desipride.socialshaadi.R;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.DEFAULT_INVITE_CODE;

/**
 * Created by parth.mehta on 11/29/15.
 */
public class InviteCode {
    private static String inviteCode;

    final static HashSet<Character> validCharsSet = new HashSet<Character>(Arrays.asList('W','P','D','F','N','S','I'));
    final static HashSet<String> validInviteCodeSet = new HashSet<String>(Arrays.asList("WSPDFN","AWPIBS","CGSWXI","KMIFCJ","LNSDOR","YZTQEP","ORXZYI"));

    public static String getInviteCode(Context context) {
        if(inviteCode == null) {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
            inviteCode = prefs.getString(context.getString(R.string.wedding_code), DEFAULT_INVITE_CODE);
        }
        return inviteCode;
    }

    public static void setInviteCode(String newInviteCode, Context context) {
        if(newInviteCode != null) {
            inviteCode = newInviteCode;
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(context.getString(R.string.wedding_code), newInviteCode);
            editor.commit();
        }
    }

    public static boolean isInviteCodeValid(String newInviteCode) {
        newInviteCode = newInviteCode.toUpperCase();
        return validInviteCodeSet.contains(newInviteCode);
    }

    public static boolean isInviteCharValid(char character) {
        return validCharsSet.contains(character);
    }


}
