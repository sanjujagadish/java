package com.tag.app.tagnearemployee.homescreen;

import android.content.Context;
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
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.views.ProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PendingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{   private List<PendingDatum> pendingData;
    Context context;
    private OnItemSelected onItemSelected;

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESSBAR = 0;
    private boolean isLoadingAdded=false;

    public PendingListAdapter(Context context,OnItemSelected onItemSelected) {
        this.context = context;
        this.onItemSelected=onItemSelected;
        pendingData = new ArrayList<>();
    }

    public List<PendingDatum> getPackageDataLists() {
        return pendingData;
    }

    public void setPackageDataLists(List<PendingDatum> pendingData) {
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
        View v1 = inflater.inflate(R.layout.item_pendinglist, parent, false);
        viewHolder = new ItemViewHolder(v1);
        return viewHolder; }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PendingDatum pendingDatum = pendingData.get( position );

        switch (getItemViewType( position )) {
            case VIEW_TYPE_ITEM:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                if (!TextUtils.isEmpty(pendingDatum.getFirstName()) && !TextUtils.isEmpty(pendingDatum.getLastName()))
                    viewHolder.tv_name.setText(String.format("%s %s", pendingDatum.getFirstName(), pendingDatum.getLastName()));
                if (!TextUtils.isEmpty(pendingDatum.getPhone()))
                    viewHolder.tv_phone.setText(pendingDatum.getPhone());
                if (!TextUtils.isEmpty(pendingDatum.getShopAddress()))
                    viewHolder.tv_address.setText(pendingDatum.getShopAddress());
                if (!TextUtils.isEmpty( pendingDatum.getOfficeName()))
                    viewHolder.tv_shopname.setText( pendingDatum.getOfficeName());
                if (!TextUtils.isEmpty(pendingDatum.getProfileImg()))
                { Picasso.get().
                        load(Constants.IMAGE_URL+pendingDatum.getProfileImg())
                        .centerCrop()
                        .fit()
                        .into(viewHolder.iv_profile); }

                if ( pendingDatum.getVerifiedDoc().equals( 0 ) )
                { viewHolder.tv_status.setText("pending");
                    viewHolder.tv_status.setTextColor( context.getResources().getColor( R.color.bsp_red ) ); }
                else if (pendingDatum.getVerifiedDoc().equals( 1 ) )
                {  viewHolder.tv_status.setText( "verified" );
                    viewHolder.tv_status.setTextColor( context.getResources().getColor( R.color.color_green ) ); }
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
        return (position == pendingData.size() -1 && isLoadingAdded) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */
    public void add(PendingDatum r) {
        pendingData.add(r);
        notifyItemInserted(pendingData.size() - 1);
    }

    public void addAll(List<PendingDatum> pendingData) {
        for (PendingDatum result : pendingData) {
            add(result);
        }
    }

    public void remove(PendingDatum r) {
        int position = pendingData.indexOf(r);
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
        add(new PendingDatum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pendingData.size() - 1;
        PendingDatum result = getItem(position);

        if (result != null) {
            pendingData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PendingDatum getItem(int position) {
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

        @BindView( R.id.tv_status )
        TextView tv_status;

        @BindView( R.id.cl_card )
        ConstraintLayout cl_card;

        public ItemViewHolder(@NonNull View itemView)
        {   super(itemView);
            ButterKnife.bind(this, itemView); }

        @OnClick(R.id.cl_card)
        public void clickcard()
        { onItemSelected.itemselected(pendingData.get(getAdapterPosition()),getAdapterPosition()); }
    }

    public interface OnItemSelected
    {
        void itemselected(PendingDatum pendingDatum, int adapterPosition);
    }

}
