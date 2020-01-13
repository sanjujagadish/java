package com.tag.app.tagnearemployee.appUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tag.app.tagnearemployee.pojomodels.Employee;
import com.tag.app.tagnearemployee.retrofit.APIConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by anjum on 14-10-2019
 * The type Shared preference.
 */

public class SharedPreference {
    private static final String UNITTYPES = "unittypes";
    private static SharedPreference mInstance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidTagNearApp_Prefs";

    private static final String IS_PUSH_NOTIFICATIONS_ENABLED = "isPushNotificationsEnabled";
    private static final String IS_LOCAL_NOTIFICATIONS_ENABLED = "isLocalNotificationsEnabled";
    private static final String LOCAL_NOTIFICATIONS_TITLE = "localNotificationsTitle";
    private static final String LOCAL_NOTIFICATIONS_DURATION = "localNotificationsDuration";
    private static final String LOCAL_NOTIFICATIONS_DESCRIPTION = "localNotificationsDescription";

    private SharedPreference()
    { }

    public SharedPreference(Context context)
    { sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
      prefsEditor = sharedPreferences.edit(); }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SharedPreference getInstance()
    {   if (mInstance == null)
        { mInstance = new SharedPreference(); }
        return mInstance; }

    /**
     * Initialize.
     *
     * @param mContext the m context
     */
    public void initialize(Context mContext)
    { sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
      prefsEditor=sharedPreferences.edit(); }

    /**
     * Put string.
     *  @param key   the key
     * @param value the value
     */
    public void putString(String key,String value)
    {   SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(key, value);
        e.apply(); }

    /**
     * Put object.
     *
     * @param key   the key
     * @param value the value
     */
    public void putObject(String key, Object value)
    {   SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(key, new Gson().toJson(value));
        e.apply(); }

    public void putArray(String key,List<String> value)
    {   SharedPreferences.Editor e=sharedPreferences.edit();
        e.putStringSet( key, Collections.singleton( new Gson().toJson( value ) ) );
        e.apply(); }

    public void setStringList(List<String> list)
    { prefsEditor.putString( UNITTYPES, new Gson().toJson(list));
      prefsEditor.apply(); }

    public List<String> getStringList()
    { return new Gson().fromJson(sharedPreferences.getString(UNITTYPES, ""), new TypeToken<ArrayList<String>>() {}.getType()); }

    /**
     * Remove object.
     * @param key the key
     */
    public void removeObject(String key)
    { SharedPreferences.Editor e = sharedPreferences.edit();
      e.remove(key).apply(); }

    /**
     * Gets string.
     * @param key the key
     * @return the string
     */
    public String getString(String key)
    {   if (sharedPreferences.contains(key))
        return sharedPreferences.getString(key, "");
        return null; }

    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    public String getAuthToken() {
        return sharedPreferences.getString((Constants.AuthToken),"");
    }

    /**
     * Gets user details.
     * @return the user details
     */
    public Employee getUserDetails()
    { return new Gson().fromJson(sharedPreferences.getString(APIConstants.GET_USER_DETAILS,null), Employee.class); }

    /**
     * Sets user.
     *
     * @param key  the key
     * @param user the user
     */
    public void setUser(String key, Employee user)
    {   SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(key, new Gson().toJson(user));
        e.apply();
        e.putString((Constants.AuthToken),"bearer"+(user.getToken()));
        e.commit(); }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch)
    {   prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTimeLaunch);
        prefsEditor.commit(); }

    public boolean isFirstTimeLaunch()
    { return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH,true); }

    //NOTIFICATION
    public void setPushNotificationsEnabled(boolean isPushNotificationsEnabled)
    {   prefsEditor.putBoolean(IS_PUSH_NOTIFICATIONS_ENABLED, isPushNotificationsEnabled);
        prefsEditor.commit(); }

    public boolean isPushNotificationsEnabled()
    { return sharedPreferences.getBoolean(IS_PUSH_NOTIFICATIONS_ENABLED,true); }

    public void setLocalNotificationsEnabled(boolean isLocalNotificationsEnabled)
    { prefsEditor.putBoolean(IS_LOCAL_NOTIFICATIONS_ENABLED, isLocalNotificationsEnabled );
      prefsEditor.commit(); }

    public boolean isLocalNotificationsEnabled()
    { return sharedPreferences.getBoolean(IS_LOCAL_NOTIFICATIONS_ENABLED,true); }

    public void setLocalNotificationsTitle(String localNotificationsTitle)
    { prefsEditor.putString(LOCAL_NOTIFICATIONS_TITLE, localNotificationsTitle);
      prefsEditor.commit(); }

    public String getLocalNotificationsTitle()
    { return sharedPreferences.getString(LOCAL_NOTIFICATIONS_TITLE, "Android Ecommerce"); }

    public void setLocalNotificationsDuration(String localNotificationsDuration)
    {   prefsEditor.putString(LOCAL_NOTIFICATIONS_DURATION, localNotificationsDuration);
        prefsEditor.commit(); }

    public String getLocalNotificationsDuration()
    { return sharedPreferences.getString(LOCAL_NOTIFICATIONS_DURATION, "day"); }

    public void setLocalNotificationsDescription(String localNotificationsDescription)
    { prefsEditor.putString(LOCAL_NOTIFICATIONS_DESCRIPTION, localNotificationsDescription);
        prefsEditor.commit(); }

    public String getLocalNotificationsDescription()
    { return sharedPreferences.getString(LOCAL_NOTIFICATIONS_DESCRIPTION,"Check bundle of new Products"); }

}
