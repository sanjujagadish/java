package com.tag.app.tagnearemployee.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import com.tag.app.tagnearemployee.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Custom progress dialog.
 * created by anjum on 19-11-2019
 */

public class CustomProgressDialog extends Dialog {
    /**
     * The Progress bar.
     */
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    /**
     * Instantiates a new Custom progress dialog.
     *
     * @param context the context
     */
    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = (View) LayoutInflater.from(getContext()).inflate( R.layout.view_custom_progressbar,null);
        ButterKnife.bind(this,view);
        setContentView(view);
        this.setCanceledOnTouchOutside(false); }

    /**
     * Show progress.
     */
    public void showProgress() {
        this.show();
    }

    /**
     * Hide progress.
     */
    public void hideProgress() {
        this.cancel();
    }

}
