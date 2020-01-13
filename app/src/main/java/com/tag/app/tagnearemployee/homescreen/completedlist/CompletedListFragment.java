package com.tag.app.tagnearemployee.homescreen.completedlist;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.CompletedDatum;
import com.tag.app.tagnearemployee.pojomodels.CompletedList;
import com.tag.app.tagnearemployee.views.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CompletedListFragment extends BaseFragment implements CompletedListContract.View
{
    private static final String TAG = "CompletedListFragment";
    @Inject
    CompletedListPresenter completedListPresenter;

    @BindView( R.id.rv_completedlist )
    RecyclerView rv_completedlist;

    @BindView( R.id.toolbar )
    Toolbar toolbar;

    private CompletedList completedLists;
    private List<CompletedDatum> pendingData = new ArrayList<>();
    private CompletedListAdapter completedListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private String viewload;

    @Override
    protected void init(Bundle savedInstanceState) {
        completedListPresenter.setView( this );
        toolbar.setTitle( "Verification Completed" );
        linearLayoutManager = new LinearLayoutManager( getContext() );
        rv_completedlist.setLayoutManager( linearLayoutManager );
        rv_completedlist.setHasFixedSize( true );

        completedListAdapter = new CompletedListAdapter( getContext() );
        rv_completedlist.setAdapter( completedListAdapter );

        rv_completedlist.addOnScrollListener( new PaginationScrollListener( linearLayoutManager ) {
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
                }, 1000 );
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

        // mocking network delay for API call
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000 );
    }

    private void loadFirstPage() {
        Log.d( TAG, "loadFirstPage: " );
        viewload="loadFirstPage";
        completedListPresenter.completed( SharedPreference.getInstance().getAuthToken(), currentPage );
    }

    private void loadNextpage() {
        viewload="loadNextpage";
        completedListPresenter.completed( SharedPreference.getInstance().getAuthToken(), currentPage );
    }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_completed_list; }

    @Override
    protected Object getModule()
    { return new CompletedListModule(); }

    @Override
    public void getCompletedlist(CompletedList completedList)
    {   hideProgress();
        pendingData.clear();
        TOTAL_PAGES = completedList.getTinyshops().getTotal();
        if ( viewload=="loadFirstPage" )
        {   pendingData.addAll( completedList.getTinyshops().getData() );
            completedListAdapter.addAll(completedList.getTinyshops().getData());
            if (currentPage !=TOTAL_PAGES)completedListAdapter.addLoadingFooter();
            else isLastPage = true;
        }
        else if ( viewload=="loadNextpage" )
        {   completedListAdapter.removeLoadingFooter();
            isLoading = false;
            pendingData.addAll( completedList.getTinyshops().getData() );
            completedListAdapter.addAll(pendingData);
            if (currentPage != TOTAL_PAGES) completedListAdapter.addLoadingFooter();
            else isLastPage = true;
        }  completedListAdapter.notifyDataSetChanged(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      getErrorMessage( throwable ); }


    @OnClick(R.id.iv_back)
    public void backpress()
    { if ( getActivity()!=null )
      getActivity().onBackPressed(); }
}
