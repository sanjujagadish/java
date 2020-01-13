package com.tag.app.tagnearemployee.homescreen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.business.businesslist.BusinessListFragment;
import com.tag.app.tagnearemployee.homescreen.completedlist.CompletedListFragment;
import com.tag.app.tagnearemployee.homescreen.pendingdetails.PendingDetailFragment;
import com.tag.app.tagnearemployee.homescreen.search.SearchFragment;
import com.tag.app.tagnearemployee.navigationview.NavigationFragment;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.DutyStatus;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.PendingList;
import com.tag.app.tagnearemployee.pojomodels.Tinyshops;
import com.tag.app.tagnearemployee.qrcode.QrcodeFragment;
import com.tag.app.tagnearemployee.views.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class HomeScreenFragment extends BaseFragment implements HomeScreenContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener,PendingListAdapter.OnItemSelected
{   @Inject
    HomeScreenPresenter homeScreenPresenter;

    @BindView(R.id.btmnavigation_home)
    BottomNavigationView bottomNavigationView;

    @BindView( R.id.rv_pendinglist )
    RecyclerView rv_pendinglist;

    @BindView( R.id.shop_status )
    Switch shop_status;

    @BindView( R.id.tv_shopname )
    TextView tv_shopname;

    @BindView( R.id.nodatafound )
    GifImageView nodatafound;

    @BindView( R.id.text_orders )
    TextView text_orders;

    @BindView( R.id.tv_nodata )
    TextView tv_nodata;

    private Tinyshops tinyshops;
    private List<PendingDatum> pendingData=new ArrayList<>( );
    private LinearLayoutManager linearLayoutManager;
    private PendingListAdapter pendingListAdapter;


    private int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;
    private String viewload;

    @Override
    protected void init(Bundle savedInstanceState)
    {   homeScreenPresenter.setView(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        homeScreenPresenter.categories( SharedPreference.getInstance().getAuthToken() );

        /**
         * API CALL
         */
        homeScreenPresenter.profilefetch( SharedPreference.getInstance().getAuthToken() );

        linearLayoutManager = new LinearLayoutManager( getContext() );
        rv_pendinglist.setLayoutManager( linearLayoutManager );
        rv_pendinglist.setHasFixedSize( true );

        pendingListAdapter = new PendingListAdapter( getContext(),this::itemselected );
        rv_pendinglist.setAdapter( pendingListAdapter );

        rv_pendinglist.addOnScrollListener( new PaginationScrollListener( linearLayoutManager ) {
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
            public boolean isLastPage() { return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        } );
        loadFirstPage();
    }

    private void loadFirstPage() {
        Log.d( TAG, "loadFirstPage: " );
        viewload="loadFirstPage";
        homeScreenPresenter.fetchinglist( SharedPreference.getInstance().getAuthToken(), currentPage );     }

    private void loadNextpage() {
        viewload="loadNextpage";
        homeScreenPresenter.fetchinglist( SharedPreference.getInstance().getAuthToken(), currentPage );
    }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_home_screen; }

    @Override
    protected Object getModule()
    { return new HomeScreenModule(); }

    @Override
    public void onFailure(Throwable throwable)
    {  hideProgress();
       getErrorMessage( throwable ); }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {   int id = menuItem.getItemId();
        if (id == R.id.btmnavigation_home)
        { ((DashBoardActivity) getActivity()).replaceFragment( new QrcodeFragment(),QrcodeFragment.class.getName() ); }
        else if (id == R.id.btmnavigation_completed)
        {  ((DashBoardActivity)getActivity()).replaceFragment( new CompletedListFragment(),CompletedListFragment.class.getName()); }
        else if (id == R.id.btmnavigation_business)
        { ((DashBoardActivity)getActivity()).replaceFragment( new BusinessListFragment(),BusinessListFragment.class.getName() ); }
        else if (id == R.id.btmnavigation_menu)
        { ((DashBoardActivity)getActivity()).replaceFragment( new NavigationFragment(),NavigationFragment.class.getName() ); }
        return true; }

    @OnClick(R.id.shop_status)
    public void checkshop()
    {   if ( shop_status.isChecked()==true )
        {   shop_status.setChecked( false );
            homeScreenPresenter.checkshopstatus( SharedPreference.getInstance().getAuthToken() ); }
        else if ( shop_status.isChecked()==false )
        {   shop_status.setChecked( true );
            homeScreenPresenter.checkshopstatus( SharedPreference.getInstance().getAuthToken() ); } }

    @Override
    public void getList(PendingList pendingList)
    {   hideProgress();
        pendingData.clear();
        TOTAL_PAGES = pendingList.getTinyshops().getTotal();
        if ( viewload=="loadFirstPage" )
        {   pendingData.addAll( pendingList.getTinyshops().getData() );
            pendingListAdapter.addAll(pendingList.getTinyshops().getData());
           if (currentPage !=TOTAL_PAGES)pendingListAdapter.addLoadingFooter();
            else isLastPage = true;
        }
        else if ( viewload=="loadNextpage" )
        {   pendingListAdapter.removeLoadingFooter();
            isLoading = false;
            pendingData.addAll( pendingList.getTinyshops().getData() );
            pendingListAdapter.addAll(pendingData);
            if (currentPage != TOTAL_PAGES) pendingListAdapter.addLoadingFooter();
            else isLastPage = true;
        } }

    @Override
    public void setStatus(DutyStatus shopstatus)
    {   if ( shopstatus.getShopOpen()==true )
        {   shop_status.setChecked( true ); }
        else
        {   shop_status.setChecked( false ); } }

    @Override
    public void getProfiel(MyProfile myProfile)
    { hideProgress();
      SharedPreference.getInstance().removeObject("MYPROFILE");
      SharedPreference.getInstance().putObject("MYPROFILE",myProfile ); }

    @Override
    public void getCategories(CategoryList categoryList)
    {   hideProgress();
        SharedPreference.getInstance().putObject("CATEGORIES",categoryList); }

    @OnClick(R.id.tv_search)
    public void search()
    { ((DashBoardActivity)getActivity()).replaceFragment(new SearchFragment(),SearchFragment.class.getName()); }

    @OnClick(R.id.search_view)
    public void clicksearch()
    { ((DashBoardActivity)getActivity()).replaceFragment(new SearchFragment(),SearchFragment.class.getName()); }

    @Override
    public void itemselected(PendingDatum pendingDatum,int adapterPosition)
    {   PendingDetailFragment pendingDetailFragment=new PendingDetailFragment();
        Bundle bundle=new Bundle( );
        bundle.putSerializable("VENDORDETAILS",pendingDatum);
        bundle.putString( "VENDORPOSITION", String.valueOf(adapterPosition) );
        pendingDetailFragment.setArguments( bundle );
        ((DashBoardActivity)getActivity()).replaceFragment(pendingDetailFragment,PendingDetailFragment.class.getName() ); }

}
