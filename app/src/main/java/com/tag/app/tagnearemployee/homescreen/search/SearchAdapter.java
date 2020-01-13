package com.tag.app.tagnearemployee.homescreen.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.pojomodels.OrderDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    List<OrderDetail> searchLists;
    SearchSelected searchSelected;

    public SearchAdapter(List<OrderDetail> searchData, SearchSelected searchSelected)
    {   this.searchLists=searchData;
        this.searchSelected=searchSelected;}

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    { return new SearchHolder( LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_search, viewGroup, false)); }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i)
    {   OrderDetail searchDataList=searchLists.get( i );
//        searchHolder.tv_staffdetails.setText(searchDataList.get() );
    }

    @Override
    public int getItemCount()
    { return searchLists.size(); }

    public class SearchHolder extends RecyclerView.ViewHolder
    {   @BindView( R.id.tv_staffdetails )
        TextView tv_staffdetails;

        public SearchHolder(@NonNull View itemView)
        {   super( itemView );
            ButterKnife.bind( this,itemView ); }

        @OnClick(R.id.ll_main)
        void clicked()
        { searchSelected.onSearchSelect(getAdapterPosition()); } }

    public interface SearchSelected
    { void onSearchSelect(int adapterPosition);}
}
