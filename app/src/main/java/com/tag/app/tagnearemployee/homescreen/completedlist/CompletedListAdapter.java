package com.tag.app.tagnearemployee.homescreen.completedlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.pojomodels.CompletedDatum;
import com.tag.app.tagnearemployee.views.ProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CompletedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{   private List<CompletedDatum> pendingData;
    Context context;

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESSBAR = 0;
    private boolean isLoadingAdded=false;

    public CompletedListAdapter(Context context) {
        this.context = context;
        pendingData = new ArrayList<>();
    }

    public List<CompletedDatum> getPackageDataLists() {
        return pendingData;
    }

    public void setPackageDataLists(List<CompletedDatum> pendingData) {
        this.pendingData = pendingData; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    { RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case VIEW_TYPE_PROGRESSBAR:
                View v2 = inflater.inflate( R.layout.layout_verticalprogress, parent, false);
                viewHolder = new ProgressViewHolder(v2);
                break; }
        return viewHolder; }

        @NonNull
        private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater)
        {
            RecyclerView.ViewHolder viewHolder;
            View v1 = inflater.inflate(R.layout.item_completedlist, parent, false);
            viewHolder = new ItemViewHolder(v1);
            return viewHolder; }

    @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CompletedDatum pendingDatum = pendingData.get( position );

        switch (getItemViewType( position )) {
            case VIEW_TYPE_ITEM:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                if (!TextUtils.isEmpty(pendingDatum.getFirstName()) && !TextUtils.isEmpty(pendingDatum.getLastName()))
                    viewHolder.tv_name.setText(String.format("%s %s", pendingDatum.getFirstName(),pendingDatum.getLastName()));
                if (!TextUtils.isEmpty(pendingDatum.getPhone()))
                    viewHolder.tv_phone.setText(pendingDatum.getPhone());
                if ( !TextUtils.isEmpty( pendingDatum.getShopAddress() ) )
                    viewHolder.tv_address.setText( pendingDatum.getShopAddress() );
                if ( !TextUtils.isEmpty( pendingDatum.getOfficeName() ) )
                    viewHolder.tv_shopname.setText( pendingDatum.getTinyshopName() );

                if (!TextUtils.isEmpty(pendingDatum.getProfileImg()))
                { Picasso.get()
                        .load(Constants.IMAGE_URL+pendingDatum.getProfileImg())
                        .centerCrop()
                        .fit()
                        .into(viewHolder.iv_profile); }

                if (!TextUtils.isEmpty(pendingDatum.getOpenTime() ))
                { viewHolder.tv_date.setText( pendingDatum.getOpenTime() ); }

                viewHolder.tv_address.setOnClickListener(new View.OnClickListener()
                {   @Override
                public void onClick(View v)
                {   Uri gmmIntentUri = Uri.parse("google.navigation:q=" + pendingDatum.getLng()+ "," + pendingDatum.getLat() );
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    try
                    { if (mapIntent.resolveActivity(context.getPackageManager()) != null)
                    { context.startActivity(mapIntent); } }
                    catch(NullPointerException e)
                    { } }
                } );

                break;
            case VIEW_TYPE_PROGRESSBAR:
//                Do nothing
                break;
        }
    }


    @Override
    public int getItemCount() {
        return pendingData == null ? 0 : pendingData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pendingData.size() - 1 && isLoadingAdded) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */
    public void add(CompletedDatum mc) {
        pendingData.add(mc);
        notifyItemInserted(pendingData.size() - 1);
    }

    public void addAll(List<CompletedDatum> mcList) {
        for (CompletedDatum mc : mcList) {
            add(mc);
        }
    }

    public void remove(CompletedDatum city) {
        int position = pendingData.indexOf(city);
        if (position > -1) {
            pendingData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CompletedDatum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pendingData.size() - 1;
        CompletedDatum item = getItem(position);

        if (item != null) {
            pendingData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CompletedDatum getItem(int position) {
        return pendingData.get(position);
    }


    protected class ItemViewHolder extends RecyclerView.ViewHolder
        {
            @BindView(R.id.iv_profile)
            CircleImageView iv_profile;

            @BindView(R.id.tv_name)
            TextView tv_name;

            @BindView(R.id.tv_shopname)
            TextView tv_shopname;

            @BindView(R.id.tv_phone)
            TextView tv_phone;

            @BindView(R.id.tv_address)
            TextView tv_address;

            @BindView(R.id.tv_date)
            TextView tv_date;

            @BindView(R.id.cl_card)
            ConstraintLayout cl_card;

        public ItemViewHolder(@NonNull View itemView)
        {   super( itemView );
            ButterKnife.bind(this, itemView); } }

    }
