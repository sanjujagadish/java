package com.tag.app.tagnearemployee.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.tag.app.tagnearemployee.BuildConfig;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.appUtils.TagNear;
import com.tag.app.tagnearemployee.boardingscreens.login.LoginFragment;
import com.tag.app.tagnearemployee.views.CustomProgressDialog;

import org.json.JSONObject;
import java.io.IOException;
import java.net.SocketTimeoutException;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Base activity.
 */
public abstract class BaseActivity extends AppCompatActivity
{   private CustomProgressDialog dialogProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(getContentResource());

        TagNear.injectModules(this, getModule());
        SharedPreference.getInstance().initialize(this);
        ButterKnife.bind(this);
        init(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler((Thread paramThread, Throwable paramThrowable) -> paramThrowable.printStackTrace()); }

    /**
     * Layout resource to be inflated
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();

    /**
     * Init.
     *
     * @param savedInstanceState the saved instance state
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * Gets module.
     *
     * @return the module
     */
    protected abstract Object getModule();

    /**
     * Add or replace fragment
     * @param fragment
     * @param tag
     */
    public void replaceFragment(final Fragment fragment, final String tag)
    { try { if (!isFinishing())
              { FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (fragment instanceof LoginFragment )
                {   fragmentTransaction.replace( R.id.content_boardingframe, fragment, tag);
                    fragmentTransaction.setCustomAnimations( R.anim.slide_from_right,R.anim.slide_to_right );
                    fragmentTransaction.addToBackStack(tag); }
                else
                {   fragmentTransaction.add(R.id.content_boardingframe, fragment, tag);
                    fragmentTransaction.setCustomAnimations( R.anim.slide_from_right,R.anim.slide_to_right );
                    fragmentTransaction.addToBackStack(tag); }

                fragmentTransaction.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions(); } }

        catch (Exception e)
        { e.printStackTrace(); } }

    /**
     * Show toast.
     *
     * @param stringResource the string resource
     */
     protected final void showToast(int stringResource)
     { Toast.makeText(this, getString(stringResource), Toast.LENGTH_SHORT).show(); }

    /**
     * Show toast.
     *
     * @param message the message
     */
     protected final void showToast(String message)
     { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }

    /**
     * Show something went wrong toast.
     */
     protected final void showSomethingWentWrongToast() {
        Toast.makeText(this, this.getString( R.string.something_went_wrong), Toast.LENGTH_SHORT).show(); }

    /**
     * Log.
     *
     * @param key     the key
     * @param message the message
     */
    public final void log(String key, String message)
    { if ( BuildConfig.DEBUG)
            Log.e(key, message); }

    /**
     * Hide action bar.
     */
    public final void hideActionBar()
    { if (getSupportActionBar() != null)
            getSupportActionBar().hide(); }

    /**
     * Sets custom action bar.
     *
     * @param actionBar the action bar
     */
    public final void setCustomActionBar(Toolbar actionBar) {
        setSupportActionBar(actionBar);
    }

    /**
     * Show progress.
     */
    public void showProgress() {
        dialogProgress = new CustomProgressDialog(BaseActivity.this);
        dialogProgress.show(); }

    /**
     * Hide progress.
     */
    public final void hideProgress() {
        if (dialogProgress != null)
            dialogProgress.cancel(); }

    /**
     * Check empty text boolean.
     *
     * @param textView     the text view
     * @param errorMessage the error message
     * @return the boolean
     */
    public final boolean checkEmptyText(TextView textView, String errorMessage) {
        if (textView.getText().toString().isEmpty()) {
            if (errorMessage != null) {
                textView.setError(errorMessage);
                showToast(errorMessage); }
            else {
                textView.setError(getString(R.string.field_cant_be_empty));
                showToast(getString(R.string.field_cant_be_empty)); }
            return true; }
        else
            return false; }

    /**
     * Is valid email boolean.
     *
     * @param textView     the text view
     * @param errorMessage the error message
     * @return the boolean
     */
    public final boolean isValidEmail(TextView textView, String errorMessage) {
        if ((!TextUtils.isEmpty(textView.getText()) && Patterns.EMAIL_ADDRESS.matcher(textView.getText()).matches())) {
            return true; }
        else
            { textView.setError(errorMessage);
            showToast(errorMessage);
            return false; } }

    /**
     * Is valid mobile boolean.
     *
     * @param textView     the text view
     * @param errorMessage the error message
     * @return the boolean
     */
    public final boolean isValidMobile(TextView textView, String errorMessage) {
        if ( Patterns.PHONE.matcher(textView.getText()).matches())
            return true;
        else {
            textView.setError(errorMessage);
            showToast(errorMessage);
            return false; } }

    /**
     * Api exception status code int.
     *
     * @param throwable the throwable
     * @return the int
     */
    public int apiExceptionStatusCode(Throwable throwable)
    {   if (throwable == null)
            return Constants.SERVER_EXCEPTION_CODE;
        else if (throwable instanceof HttpException)
        { return ((HttpException) throwable).response().code(); }
        else
            return Constants.SERVER_EXCEPTION_CODE; }

    /**
     * Api exception message string.
     *
     * @param throwable the throwable
     * @return the string
     */
    public String apiExceptionMessage(Throwable throwable) {
        if (throwable == null)
            return getString(R.string.something_went_wrong);
        else if (throwable instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
            return (getErrorMessage(responseBody));
        } else if (throwable instanceof SocketTimeoutException) {
            return "Sorry! timeout, please check your internet connection";
        } else if (throwable instanceof IOException) {
            return getString(R.string.something_went_wrong);
        } else {
            return (throwable.getMessage());
        } }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            if (jsonObject.get("error") instanceof JSONObject) {
                Error error = new Gson().fromJson(jsonObject.getJSONObject("error").toString(), Error.class);
                log("error response", error.getMessage());
                return error.getMessage();
            } else if (jsonObject.get("error") instanceof String && jsonObject.has("message")) {
                return jsonObject.getString("message");
            } else
                return getString(R.string.something_went_wrong); }
        catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.something_went_wrong); } }

    /**
     * Handle exception throwables.
     *
     * @param throwable the throwable
     */
    public void handleExceptionThrowables(Throwable throwable)
    { if (throwable != null) {
            showToast(apiExceptionMessage(throwable));
            throwable.printStackTrace(); }
      else
            showToast(getString(R.string.something_went_wrong)); }

    @Override
    public boolean onSupportNavigateUp()
    {   onBackPressed();
        return true; }

}
