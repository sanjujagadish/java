package com.tag.app.tagnearemployee.views;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tag.app.tagnearemployee.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    public ProgressViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
