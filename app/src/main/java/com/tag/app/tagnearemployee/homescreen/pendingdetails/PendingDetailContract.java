package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PendingDetailContract
{
    public interface View extends BaseView
    {
        /**
         * Set view for update response
         * @param updateShopResponse
         */
        void updatedetails(UpdateShopResponse updateShopResponse);

        /**
         * view
         * @param imageResponse
         */
        void imageupload(ImageResponse imageResponse);
    }

    public interface Model extends BaseModel
    {
        /**
         * update details
         * @param token
         * @param updateShopInput
         */
        void setdetails(String token, PendingDatum updateShopInput, ModelCallback modelCallback);

        /**
         * Model call
         * @param body
         * @param modelCallback
         */
        void uploadsuccess(Map<String, RequestBody> payload,Map<String, RequestBody> type, MultipartBody.Part body, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         * details update
         * @param token
         * @param updateShopInput
         */
        void detailsupdate(String token,PendingDatum updateShopInput);

        /**
         * Image upload
         * @param stringRequestBodyMap
         * @param body
         */
        void uploadvalidate(Map<String, RequestBody> stringRequestBodyMap, Map<String, RequestBody> payload, MultipartBody.Part body);
    }
}
