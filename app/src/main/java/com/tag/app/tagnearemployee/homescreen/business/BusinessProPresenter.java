package com.tag.app.tagnearemployee.homescreen.business;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BusinessProPresenter implements BusinessProContract.Presenter {
    private final BusinessProModel businessProModel;
    private BusinessProContract.View view;

    public BusinessProPresenter(BusinessProModel businessProModel)
    { this.businessProModel=businessProModel; }

    @Override
    public void setView(Object view)
    { this.view= (BusinessProContract.View) view; }

    @Override
    public void clearView()
    { businessProModel.destroy(); }

    @Override
    public void businesspro(BusinessPro businessPro, String token) {
        businessProModel.businessprosubmit( businessPro, token, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.businesspro( (BusinessProResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }


    public void uploadvalidate(Map<String, RequestBody> payload,Map<String, RequestBody> type, MultipartBody.Part body) {
        businessProModel.uploadsuccess(payload,type,body, new ModelCallback() {
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
