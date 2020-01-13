package com.tag.app.tagnearemployee.homescreen.search;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.SearchDetail;
import com.tag.app.tagnearemployee.pojomodels.SearchList;
import com.tag.app.tagnearemployee.pojomodels.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment implements SearchContract.View,SearchAdapter.SearchSelected
{   @Inject
    SearchPresenter searchPresenter;

    @BindView( R.id.searchEditText )
    AppCompatEditText searchEditText;

    @BindView( R.id.rv_search )
    RecyclerView rv_search;

    private String searchStaff;
    private SearchResponse searchResponse;
    private SearchAdapter searchAdapter;
    private ArrayAdapter filterAdapter;
    private List<SearchList> searchLists=new ArrayList<>( );
    private List<SearchDetail> searchDetails=new ArrayList<>( );
    private List<String> filter;
    private String filterselect;

    @Override
    protected void init(Bundle savedInstanceState)
    { searchPresenter.setView( this ); }

    @OnClick(R.id.searchEditText)
    public void clicksearch()
    {  searchEditText.addTextChangedListener( new TextWatcher()
       { @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after)
         { }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count)
         { }

         @Override
         public void afterTextChanged(Editable s)
         { if(s.toString().length()>2)
          { searchStaff=searchEditText.getText().toString().trim();
            showProgress();
            searchPresenter.searchfrom(SharedPreference.getInstance().getAuthToken(),searchStaff);
          } } } ); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_search; }

    @Override
    protected Object getModule()
    { return new SearchModule(); }

    @Override
    public void searchresult(SearchResponse searchResponse)
    {   hideProgress();
        searchLists.clear();
        searchDetails.clear();
        this.searchResponse=searchResponse;
        searchLists.addAll( searchResponse.getOrderlist().getData() );
        searchDetails.addAll( searchResponse.getOrderlist().getData().get(0).getDetails() ); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast( throwable.getMessage() ); }

    @Override
    public void onSearchSelect(int adapterPosition)
    { }

    @OnClick(R.id.iv_back)
    void clickback()
    { if ( getActivity()!=null )
            getActivity().onBackPressed(); }

    @OnClick(R.id.ivClose)
    void clickclose()
    { searchLists.clear();
      searchDetails.clear();
      searchEditText.getText().clear(); }

    @OnClick(R.id.ivfilter)
    public void clickfilter()
    {   AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select");
        filter=new ArrayList<>( );
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice );
        filter.add( "Recent" );
        filter.add( "Upcoming" );
        filter.add( "Completed" );

        arrayAdapter.addAll( filter);
        builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {   @Override
            public void onClick(DialogInterface dialog, int which)
            {   String filters=filter.get( which );
                filterselect=filters;
                searchEditText.setText( filterselect);
                showProgress();
                searchPresenter.searchfrom( SharedPreference.getInstance().getAuthToken(),searchEditText.getText().toString().trim());
            } });
        builderSingle.show();
    }

}
