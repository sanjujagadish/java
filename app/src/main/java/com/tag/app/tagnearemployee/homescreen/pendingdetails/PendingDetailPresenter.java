package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PendingDetailPresenter implements PendingDetailContract.Presenter
{
    private PendingDetailContract.View view;
    private final PendingDetailModel pendingDetailModel;

    public PendingDetailPresenter(PendingDetailModel pendingDetailModel)
    { this.pendingDetailModel=pendingDetailModel; }

    @Override
    public void setView(Object view)
    { this.view= (PendingDetailContract.View) view; }

    @Override
    public void clearView()
    { pendingDetailModel.destroy(); }

    @Override
    public void detailsupdate(String token, PendingDatum updateShopInput)
    { pendingDetailModel.setdetails( token,updateShopInput, new ModelCallback()
        {
            @Override
            public void onFailure(Throwable throwable)
            { view.onFailure( throwable ); }

            @Override
            public void onSuccess(Object object)
            { view.updatedetails( (UpdateShopResponse) object ); }
        } );
    }

    public void uploadvalidate(Map<String, RequestBody> payload, Map<String, RequestBody> type, MultipartBody.Part body) {
        pendingDetailModel.uploadsuccess(payload,type,body, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.imageupload( (ImageResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }
}
