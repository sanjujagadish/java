package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.business.BusinessProFragment;
import com.tag.app.tagnearemployee.homescreen.business.businessupdate.BusinessUpdateFragment;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.BusinessList;
import com.tag.app.tagnearemployee.views.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BusinessListFragment extends BaseFragment implements BusinessListContract.View,BusinessListAdapter.OnItemSelected
{
    @Inject
    BusinessListPresenter businessListPresenter;

    @BindView( R.id.rv_businesslist )
    RecyclerView rv_businesslist;

    @BindView( R.id.fab_addbusiness )
    FloatingActionButton fab_addbusiness;

    @BindView( R.id.toolbar )
    Toolbar toolbar;

    private List<BusinessDatum> pendingData=new ArrayList<>( );
    private LinearLayoutManager linearLayoutManager;
    private BusinessListAdapter businessListAdapter;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private String viewload;

    @Override
    protected void init(Bundle savedInstanceState)
    {   businessListPresenter.setView( this );
        toolbar.setTitle( "Business List" );

        linearLayoutManager = new LinearLayoutManager( getContext() );
        rv_businesslist.setLayoutManager( linearLayoutManager );
        rv_businesslist.setHasFixedSize( true );

        businessListAdapter = new BusinessListAdapter( getContext(),this );
        rv_businesslist.setAdapter( businessListAdapter );

        rv_businesslist.addOnScrollListener( new PaginationScrollListener( linearLayoutManager ) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        loadNextpage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        } );
        loadFirstPage();
    }

    private void loadFirstPage() {
        viewload="loadFirstPage";
        businessListPresenter.fetchlist( SharedPreference.getInstance().getAuthToken(), currentPage );
        }

    private void loadNextpage() {
        viewload="loadNextpage";
         businessListPresenter.fetchlist( SharedPreference.getInstance().getAuthToken(), currentPage );
        }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_business_list; }

    @Override
    protected Object getModule()
    { return new BusinessListModule(); }

    @Override
    public void getBusinessList(BusinessList businessList)
    {   hideProgress();
        TOTAL_PAGES = businessList.getTinyshops().getTotal();
        pendingData.clear();
        if ( viewload=="loadFirstPage" )
        {   pendingData.addAll( businessList.getTinyshops().getData() );
            businessListAdapter.addAll(businessList.getTinyshops().getData());
            if (currentPage <=TOTAL_PAGES)businessListAdapter.addLoadingFooter();
              else isLastPage = true;
        }
        else if ( viewload=="loadNextpage" )
        {   businessListAdapter.removeLoadingFooter();
            isLoading = false;
            pendingData.addAll( businessList.getTinyshops().getData() );
            businessListAdapter.addAll(pendingData);
            if (currentPage != TOTAL_PAGES) businessListAdapter.addLoadingFooter();
            else isLastPage = true;
        }
        businessListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable throwable)
    {  hideProgress();
       getErrorMessage( throwable ); }

    @Override
    public void itemselected(BusinessDatum pendingDatum, int adapterPosition)
    {   BusinessUpdateFragment businessProFragment=new BusinessUpdateFragment();
        Bundle bundle=new Bundle(  );
        bundle.putSerializable( "BUSINESSREGDATA",pendingDatum );
        bundle.putString( "UPDATEBUSINESS","2" );
        businessProFragment.setArguments( bundle );
        ((DashBoardActivity)getActivity()).replaceFragment( businessProFragment,BusinessUpdateFragment.class.getName() ); }

    @OnClick(R.id.iv_back)
    public void backpress()
    {  if ( getActivity()!=null )
        getActivity().onBackPressed(); }

    @OnClick(R.id.fab_addbusiness)
    public void addbusiness()
    {   ((DashBoardActivity)getActivity()).replaceFragment( new BusinessProFragment(), BusinessProFragment.class.getName() ); }
}
