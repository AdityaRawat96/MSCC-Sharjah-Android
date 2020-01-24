package danysam.net.churchdirectory.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Session Management
 * <p>
 * Created by Dany on 18-01-2018.
 */

@SuppressWarnings("FieldCanBeLocal")
public class SessionManager {

    private static final SessionManager ourInstance = new SessionManager();

    private static final String PREF_SESSION = "PREF_SESSION";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_TOKEN = "user_token";
    private static final String USER_IS_MEMBER = "is_member";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String LAST_UPDATE = "LAST_UPDATE";

    private static SharedPreferences pref;
    private static SharedPreferences.Editor edt;

    public static SessionManager getInstance() {
        return ourInstance;
    }


    /**
     * Set auth values for further calls that needs token
     *
     * @param context Activity Context
     * @param uid     user ID
     * @param user    User Name used for signing in
     * @param token   API generated token send as response
     */
    public void setSession(Context context, String uid, String user, String token, Boolean isMember) {
        setLastUpdate(context, "");
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        edt = pref.edit();
        edt.putString(USER_ID, uid);
        edt.putString(USER_NAME, user);
        edt.putString(USER_TOKEN, token);
        edt.putBoolean(USER_IS_MEMBER, isMember);
        edt.putBoolean(IS_LOGGED_IN, true);
        edt.apply();
    }

    public Boolean getIsMember(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getBoolean(USER_IS_MEMBER, false);
    }


    /**
     * Check if user has token
     *
     * @return bool -> is logged in or not
     */
    public Boolean isLoggedIn(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setLoggedOut(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        edt = pref.edit();
        edt.putBoolean(IS_LOGGED_IN, false);
        edt.apply();
    }

    public String getUserName(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getString(USER_NAME, "");
    }

    public String getUserId(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getString(USER_ID, "");
    }

    public String getUserToken(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getString(USER_TOKEN, "");
    }

    public String getLastUpdate(Context context) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return pref.getString(LAST_UPDATE, "");
    }

    public void setLastUpdate(Context context, String lastUpdate) {
        pref = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        edt = pref.edit();
        edt.putString(LAST_UPDATE, lastUpdate);
        edt.apply();
    }
}
