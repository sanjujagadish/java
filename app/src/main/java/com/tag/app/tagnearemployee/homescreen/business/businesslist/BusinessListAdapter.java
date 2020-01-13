package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.views.ProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{   private List<BusinessDatum> businessData;
    Context context;
    private OnItemSelected onItemSelected;

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESSBAR = 0;
    private boolean isLoadingAdded=false;

    public BusinessListAdapter(Context context,OnItemSelected onItemSelected) {
        this.context = context;
        this.onItemSelected=onItemSelected;
        businessData = new ArrayList<>();
    }

    public List<BusinessDatum> getPackageDataLists() {
        return businessData;
    }

    public void setPackageDataLists(List<BusinessDatum> businessData) {
        this.businessData = businessData; }

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
        View v1 = inflater.inflate(R.layout.item_businesslist, parent,false);
        viewHolder = new ItemViewHolder(v1);
        return viewHolder; }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BusinessDatum pendingDatum = businessData.get( position );

        switch (getItemViewType( position )) {
            case VIEW_TYPE_ITEM:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                if (!TextUtils.isEmpty(pendingDatum.getFirstName()) && !TextUtils.isEmpty(pendingDatum.getLastName()))
                    viewHolder.tv_name.setText(String.format("%s %s", pendingDatum.getFirstName(), pendingDatum.getLastName()));
                if (!TextUtils.isEmpty(pendingDatum.getPhone()))
                    viewHolder.tv_phone.setText(pendingDatum.getPhone());
                if ( !TextUtils.isEmpty( pendingDatum.getShopAddress() ) )
                    viewHolder.tv_address.setText( pendingDatum.getShopAddress() );
                if ( !TextUtils.isEmpty( pendingDatum.getTinyshopName() ) )
                    viewHolder.tv_shopname.setText( pendingDatum.getTinyshopName() );
                if ( !TextUtils.isEmpty( pendingDatum.getUpdatedAt() ) )
                    viewHolder.tv_date.setText( pendingDatum.getUpdatedAt() );
                if (!TextUtils.isEmpty(pendingDatum.getProfileImg()))
                { Picasso.get()
                        .load( Constants.IMAGE_URL+pendingDatum.getProfileImg())
                        .into(viewHolder.iv_profile); }
                if ( !TextUtils.isEmpty( pendingDatum.getOpenTime() ) )
                    viewHolder.tv_date.setText( pendingDatum.getOpenTime() );
                if (pendingDatum.getTinyrating()!=null )
                    viewHolder.rb_business.setRating( pendingDatum.getTinyrating() );

                break;
            case VIEW_TYPE_PROGRESSBAR:
//                Do nothing
                break;
        }
    }


    @Override
    public int getItemCount() {
        return businessData == null ? 0 : businessData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == businessData.size() - 1 && isLoadingAdded) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */
    public void add(BusinessDatum r) {
        businessData.add(r);
        notifyItemInserted(businessData.size() - 1);
    }

    public void addAll(List<BusinessDatum> businessData) {
        for (BusinessDatum result : businessData) {
            add(result);
        }
    }

    public void remove(BusinessDatum r) {
        int position = businessData.indexOf(r);
        if (position > -1) {
            businessData.remove(position);
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
        add(new BusinessDatum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = businessData.size() - 1;
        BusinessDatum result = getItem(position);

        if (result != null) {
            businessData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public BusinessDatum getItem(int position) {
        return businessData.get(position);
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

        @BindView( R.id.tv_date )
        TextView tv_date;

        @BindView( R.id.cl_card )
        ConstraintLayout cl_card;

        @BindView( R.id.rb_business )
        AppCompatRatingBar rb_business;

        public ItemViewHolder(@NonNull View itemView)
        {   super(itemView);
            ButterKnife.bind(this, itemView); }

        @OnClick(R.id.tv_edit)
        public void clickcard()
        { onItemSelected.itemselected(businessData.get(getAdapterPosition()),getAdapterPosition()); }
    }

    public interface OnItemSelected
    {
        void itemselected(BusinessDatum pendingDatum, int adapterPosition);
    }
}
