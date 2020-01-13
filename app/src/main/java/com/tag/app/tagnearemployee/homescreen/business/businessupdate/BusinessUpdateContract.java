package com.tag.app.tagnearemployee.homescreen.business.businessupdate;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface BusinessUpdateContract
{
    public interface View extends BaseView
    {
        /**
         *
         * @param businessProResponse
         */
        void businessupdate(BusinessProResponse businessProResponse);

        /**
         * view
         * @param imageResponse
         */
        void imageupload(ImageResponse imageResponse);
    }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param businessPro
         * @param token
         * @param modelCallback
         */
        void businessproupdate(BusinessDatum businessPro, String token, ModelCallback modelCallback);

        /**
         * Model call
         * @param body
         * @param modelCallback
         */
        void uploadsuccess(Map<String, RequestBody> payload,Map<String, RequestBody> payloads, MultipartBody.Part body, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *  @param businessPro
         * @param token
         */
        void businessproupdate(BusinessDatum businessPro, String token);

        /**
         * Image upload
         * @param body
         */
        void uploadvalidate(Map<String, RequestBody> payload,Map<String, RequestBody> payloads, MultipartBody.Part body);
    }

}
