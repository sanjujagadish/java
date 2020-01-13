package com.tag.app.tagnearemployee.retrofit;

import com.tag.app.tagnearemployee.pojomodels.AboutUs;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.BusinessList;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.CompletedList;
import com.tag.app.tagnearemployee.pojomodels.ContactUs;
import com.tag.app.tagnearemployee.pojomodels.DutyStatus;
import com.tag.app.tagnearemployee.pojomodels.Employee;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;
import com.tag.app.tagnearemployee.pojomodels.OrdersStatus;
import com.tag.app.tagnearemployee.pojomodels.OrdersStatusResponse;
import com.tag.app.tagnearemployee.pojomodels.PaymentHistory;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.PendingList;
import com.tag.app.tagnearemployee.pojomodels.PrivacyPolicy;
import com.tag.app.tagnearemployee.pojomodels.QrCodeResponse;
import com.tag.app.tagnearemployee.pojomodels.SearchResponse;
import com.tag.app.tagnearemployee.pojomodels.SelectShop;
import com.tag.app.tagnearemployee.pojomodels.Terms;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;
import com.tag.app.tagnearemployee.pojomodels.VerifyVendorResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by anjum on 19/11/2019.
 */

public interface Api
{
    //VENDOR LOGIN
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.EMPLOYEE_LOGIN)
    Observable<Employee> validlogin(@Body Employee employee );

    //FORGOT PASSWORD
    @POST(APIConstants.FORGOTPASSWORD)
    Observable<ForgotPassword> forgotpassword( @Body ForgotPassword forgotPassword );

    //VERIFYOTP FOR FORGOT PASSWORD
    @POST(APIConstants.VERIFY_OTP)
    Observable<ForgotPassword> verifyotp( @Body ForgotPassword forgotPassword );

    //CONFIRM RESET PASSWORD
    @POST(APIConstants.RESETPASSWORDCONFIRM)
    Observable<ForgotPassword> resetpassword( @Body ForgotPassword customer );

    //PENDING LIST
    @GET(APIConstants.PENDING_LIST)
    Observable<PendingList> getPendinglist( @Header("authorization") String token,@Query("page") int page );

    //CATEGORY_LIST
    @GET(APIConstants.CATEGORY_LIST)
    Observable<CategoryList> getCategories( @Header("authorization") String token );

    //COMPLETED LIST
    @GET(APIConstants.COMPLETED_LIST)
    Observable<CompletedList> getCompletedlist( @Header("authorization") String token,@Query("page") int page );

    //SEARCH_LIST
    @GET(APIConstants.SEARCH_LIST)
    Observable<SearchResponse> getsearchResponse( @Header("authorization") String token,@Query("searchkey") String searchkey );

    //SHOPSTATUS
    @POST(APIConstants.DUTY_STATUS)
    Observable<DutyStatus> setDutyStatus(@Header("authorization") String token );

    //UPLOADIMAGE
    @Multipart
    @POST(APIConstants.IMAGE_UPLOAD)
    Observable<ImageResponse> uploadProfilePic(@PartMap Map<String, RequestBody> params,@PartMap Map<String,RequestBody> param,@Part MultipartBody.Part file);

    //UPDATE SHOP
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.PENDING_UPDATE_SHOPDETAILS)
    Observable<UpdateShopResponse> updatedetails( @Header( "authorization" ) String token, @Body PendingDatum updateShopInput );

    //VERIFY VENDOR SHOP
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.VERIFY_VENDOR)
    Observable<VerifyVendorResponse> verifyvendor( @Header("authorization") String token, @Body PendingDatum verifyVendor );

    //BUSINESS REGISTER
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.BUSINESS_ADDED)
    Observable<BusinessProResponse> businessregister( @Header("authorization") String token, @Body BusinessPro businessPro );

    //BUSINESS UPDATE
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.BUSINESS_UPDATE)
    Observable<BusinessProResponse> businessupdate( @Header("authorization") String token, @Body BusinessDatum businessDatum );

    //BUSINESS_ADDEDVERIFY
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.BUSINESS_VERIFY)
    Observable<BusinessPro> businessverify( @Header("authorization") String token, @Body BusinessPro businessPro );

    //BUSINESS_ADDEDVERIFY
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET(APIConstants.BUSINESS_LIST)
    Observable<BusinessList> getbusinesslist( @Header("authorization") String token,@Query( "page" ) int page );

    //SELECT_SHOP
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.SELECT_SHOP)
    Observable<SelectShop> shopselected( @Header("authorization") String token, @Query("tinyshop_id") String tinyshop_id );

    //MYPROFILE
    @GET(APIConstants.MYPROFILE)
    Observable<MyProfile> getprofiledetails( @Header("authorization") String token);

    //ORDERS_STATUS
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(APIConstants.ORDERS_STATUS)
    Observable<OrdersStatusResponse> ordersStatus( @Header("authorization") String token, @Body OrdersStatus ordersStatus);

    //QRCODE ORDER DETAILS
    @GET(APIConstants.QRCODE_DETAILS)
    Observable<QrCodeResponse> getqrcode( @Header( "authorization" ) String token, @Query("id") String qr_id);

    //PAYMENT_HISTORY
    @GET(APIConstants.PAYMENT_HISTORY)
    Observable<PaymentHistory> getpaymenthistory( @Header( "authorization" ) String token, @Query("page") String page);

    //ABOUT_US
    @GET(APIConstants.ABOUT_US)
    Observable<AboutUs> getaboutus( @Header( "authorization" ) String token );

    //CONTACTUS
    @GET(APIConstants.CONTACT_US)
    Observable<ContactUs> getContactUs( @Header( "authorization" ) String token );

    //TERMS AND CONDITIONS
    @GET(APIConstants.TERMS_CONDITION)
    Observable<Terms> getTerms( @Header( "authorization" ) String token );

    //PRIVACY_POLICY
    @GET(APIConstants.PRIVACY_POLICY)
    Observable<PrivacyPolicy> getPolicy( @Header( "authorization" ) String token );
}
