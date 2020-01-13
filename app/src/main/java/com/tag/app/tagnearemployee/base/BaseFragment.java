package com.tag.app.tagnearemployee.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.appUtils.TagNear;
import com.tag.app.tagnearemployee.appUtils.Utils;

import java.io.IOException;

import butterknife.ButterKnife;
import retrofit2.HttpException;

public abstract class BaseFragment extends Fragment
{   private BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        TagNear.injectModules(this, getModule());
        SharedPreference.getInstance().initialize(getContext());
        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> paramThrowable.printStackTrace()); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {   View view = inflater.inflate(getContentResource(), container, false);
        ButterKnife.bind(this, view);
        this.activity = ((BaseActivity) getActivity());
        init(savedInstanceState);
        return view; }

    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Layout resource to be inflated
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();

    protected abstract Object getModule();

    public final boolean checkempty(String textView,String message)
    { if ( textView.isEmpty() || textView == null )
      { Toast.makeText( activity, message, Toast.LENGTH_SHORT ).show(); }
            return textView.isEmpty() || textView == null; }

    public final boolean checkEmptyText(TextView textView, String errorMessage)
    {   if (textView.getText().toString().isEmpty())
        { if (errorMessage != null)
             { textView.setError(errorMessage);
               textView.requestFocus();
//                showToast(errorMessage);
             }
            else
                { textView.requestFocus();
                  textView.setError(getString( R.string.field_cant_be_empty)); }
            return true; }
        else
            return false; }

    public final boolean isChecked(CheckBox layout, String errorMessage)
    {   if (layout.isChecked()==false)
        {   if (errorMessage != null)
            {  layout.setError(errorMessage);
               layout.requestFocus(); }
            else
            { layout.requestFocus();
              layout.setError("Please check fields ");
            }
            return true; }
        else
            return false; }

    public final boolean isValidEmail(TextView textView, String errorMessage)
    {    if (TextUtils.isEmpty(textView.getText()))
        { textView.setError(errorMessage);
          return false; }
        else if (!Patterns.EMAIL_ADDRESS.matcher(textView.getText()).matches())
        { textView.setError(getString(R.string.enter_valid_email));
          return false; }
        else
        { return true; }
    }

    public final boolean isValidEmails(TextView textView, String errorMessage)
    {   if (TextUtils.isEmpty(textView.getText()))
         { textView.setError(errorMessage);
           return false; }
        else if (!Patterns.PHONE.matcher(textView.getText()).matches())
        { textView.setError("Enter valid mobile number");
          return false; }
        else
        { return true; } }

    protected void showToast(int stringResource)
    { Toast.makeText(getContext(), getString(stringResource), Toast.LENGTH_SHORT).show(); }

    protected void showToast(String message)
    { Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show(); }

    protected void log(String key, String message)
    { activity.log(key, message); }

    public void handleThrowable(Throwable throwable)
    { activity.handleExceptionThrowables(throwable); }

    public void showProgress()
    { activity.showProgress(); }

    public void hideProgress()
    { activity.hideProgress(); }

    protected void customToast(String msg, boolean isLong)
    { if (getContext() != null && !isDetached())
      { if (isAdded())
        {       LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("InflateParams") View customToastroot = inflater.inflate(R.layout.custom_toast, null);
                TextView tvToastMsg = customToastroot.findViewById(R.id.tvToastMsg);
                tvToastMsg.setText(msg);
                Toast customtoast = new Toast(getContext());
                customtoast.setView(customToastroot);
                customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 40);
                customtoast.setDuration(Toast.LENGTH_LONG);
                customtoast.show();
            } } }

    protected void addTextChangedListener(AppCompatEditText editText) {
        editText.addTextChangedListener(new TextWatcher()
        {   @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {   if (!checkEmptyText(editText,getString(R.string.field_cant_be_empty)))
                {   doOnTextChanged();
                    editText.setError(null); }
                else
                    { editText.setError(getString(R.string.field_cant_be_empty)); }
            }}); }

    protected void addTextChangedListeners(AppCompatTextView textView)
     {  textView.addTextChangedListener(new TextWatcher()
          { @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {   if(!checkEmptyText(textView, getString(R.string.field_cant_be_empty) )) {
                    doOnTextChanged();
                    textView.setError(null); }
                else
                { textView.setError(getString(R.string.field_cant_be_empty)); } }}); }

    protected boolean checkEmptyNull(String text)
    { return text.isEmpty() || text == null; }

    protected boolean isValidMobile(AppCompatEditText editText)
    { return editText.getText().toString().length() == 10; }

    protected void doOnTextChanged()
    { }

    public String getErrorMsg(Throwable throwable)
    {   String errorMsg = "Something went wrong";
        try {
            if (throwable instanceof HttpException)
            {   if (((HttpException) throwable).code() == 401)
                { showToast( errorMsg ); }
                else {
                    String js = ((HttpException) throwable).response().errorBody().string();
                    ErrorData errorData = new Gson().fromJson(js, ErrorData.class);
                    if (errorData != null)
                    { errorMsg = errorData.message; }
                    else { errorMsg = "Something went wrong"; }
                    Log.d("error body", "" + js); } }
            return errorMsg; }
        catch (IOException e1)
        { e1.printStackTrace();
            return errorMsg; } }

    public String getErrorMessage(Throwable throwable)
    {    String errorMsg = "No data";
         try
         {  if (throwable instanceof HttpException)
            {   if (((HttpException) throwable).code() == 400)
                {
                    Toast.makeText( activity, throwable.getMessage(), Toast.LENGTH_SHORT ).show();
                }
                else if ( ((HttpException)throwable).code()==401 )
               {
                   Utils.performLogout( (Activity) getContext() );
               }
                else if ( ((HttpException)throwable).code()==404 )
                { Toast.makeText( activity,"Try again later", Toast.LENGTH_SHORT ).show(); }
                else if (((HttpException) throwable).code() == 422) {  // showToast( "User already exist,please login" );
//                   ((BoardingActivity) getActivity()).replaceFragment( new LoginFragment(),LoginFragment.class.getName() ); }
                Toast.makeText( activity, throwable.getMessage(), Toast.LENGTH_SHORT ).show(); }
                else
                {   String js = ((HttpException) throwable).response().errorBody().string();
                    ErrorData errorData = new Gson().fromJson(js,ErrorData.class);
                    if (errorData != null)
                    { errorMsg = errorData.message; }
                    else
                    { errorMsg = "Something went wrong"; }
                    Log.d("error body","" + js); } }
            return errorMsg; }
        catch (IOException e1)
        {   e1.printStackTrace();
            return errorMsg; } }

    private class ErrorData implements Parcelable
    {   public final Creator<ErrorData> CREATOR = new Creator<ErrorData>()
        {   @Override
            public ErrorData createFromParcel(Parcel in) {
                return new ErrorData(in);
            }

            @Override
            public ErrorData[] newArray(int size) {
                return new ErrorData[size];
            } };

        public String status;
        @SerializedName(value = "message", alternate = {"user_msg","msg"})
        public String message;

        protected ErrorData(Parcel in)
        {   status = in.readString();
            message = in.readString(); }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {   dest.writeString(status);
            dest.writeString(message); }

        @Override
        public int describeContents() {
            return 0;
        } }
}
