package com.tag.app.tagnearemployee.homescreen.business.businessupdate;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;
import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.BuildConfig;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.appUtils.PermissionHelper;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.HomeScreenFragment;
import com.tag.app.tagnearemployee.homescreen.pendingdetails.MapFragment;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.Tinycategorylist;
import com.tag.app.tagnearemployee.views.cropper.CropImage;
import com.tag.app.tagnearemployee.views.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;

public class BusinessUpdateFragment extends BaseFragment implements BusinessUpdateContract.View, PermissionHelper.PermissionResultCallback,GridTimePickerDialog.OnTimeSetListener
{   @Inject
    BusinessUpdatePresenter businessProPresenter;

    @BindView( R.id.iv_profile)
    CircleImageView iv_profile;

    @BindView( R.id.iv_shopphoto)
    ImageView iv_shopphoto;

    @BindView( R.id.et_name)
    AppCompatEditText et_name;
    @BindView( R.id.et_lastname)
    AppCompatEditText et_lastname;
    @BindView( R.id.et_email )
    AppCompatEditText et_email;
    @BindView( R.id.et_phone )
    AppCompatEditText et_phone;
    @BindView( R.id.et_business )
    AppCompatEditText et_business;
    @BindView( R.id.et_shopphone )
    AppCompatEditText et_shopphone;

    @BindView( R.id.tv_editcity )
    AppCompatTextView tv_editcity;
    @BindView( R.id.tv_city )
    AppCompatTextView tv_city;
    @BindView( R.id.tv_category )
    AppCompatTextView tv_category;
    @BindView( R.id.tv_mobility )
    TextView tv_mobility;
    @BindView( R.id.tv_shop )
    TextView tv_shop;
    @BindView( R.id.tv_intime )
    AppCompatTextView tv_intime;
    @BindView( R.id.tv_outtime )
    AppCompatTextView tv_outtime;

    @BindView( R.id.rb_parcelon )
    RadioButton rb_parcelon;
    @BindView( R.id.rb_parceloff )
    RadioButton rb_parceloff;
    @BindView( R.id.rb_tableoff )
    RadioButton rb_tableoff;
    @BindView( R.id.rb_tableon )
    RadioButton rb_tableon;
    @BindView( R.id.rb_serviceoff )
    RadioButton rb_serviceoff;
    @BindView( R.id.rb_serviceoon )
    RadioButton rb_serviceon;
    @BindView( R.id.rb_paylateroff )
    RadioButton rb_paylateroff;
    @BindView( R.id.rb_paylateron )
    RadioButton rb_paylateron;


    @BindView( R.id.toolbar )
    Toolbar toolbar;

    private String latitude,longitude;
    private static final int REQUEST_EXTERNAL_STORAGE = 100;
    private List<Address> useraddress;

    private CategoryList categoryList;
    private List<Tinycategorylist> tinycategorylists = new ArrayList<>( );
    private CategoryList categoryLists;
    private String categoryname,categoryListname,categoryId;
    private String selectedType,pincode;
    private int selectedvalue;
    private BusinessDatum updateShopInput;

    private final int STORAGE_AND_CAMERA_REQUEST_CODE = 4000;
    private static final int PICK_SHOP_PHOTO = 2000;

    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private String selectedopenTime,selectedcloseTime,timeselected;
    private String imagereq;
    private Bitmap bitmap;
    private PermissionHelper permissionHelper;
    private ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState)
    {   businessProPresenter.setView(this);
        toolbar.setTitle( "Business Pro" );

        permissionHelper = new PermissionHelper(getContext(),this);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        updateShopInput = new BusinessDatum();

        if ( getArguments()!=null )
        updateShopInput = (BusinessDatum) getArguments().getSerializable("BUSINESSREGDATA" );
        updateShopInput.setShopId( String.valueOf( updateShopInput.getId() ) );
        updateShopInput.setId( updateShopInput.getId() );

        et_name.setEnabled( false );
        et_lastname.setEnabled( false );
        et_phone.setEnabled( false );
        et_email.setEnabled( false );
        et_business.setEnabled( false );
        et_shopphone.setEnabled( false );
        tv_category.setFocusable( false );

        if ( getArguments()!=null )
        { if ( getArguments().getString("MAPDETAILUPDATE") !=null)
        {   String addressess=getArguments().getString("MAPDETAILUPDATE" );
            tv_city.setText( addressess);
            updateShopInput= (BusinessDatum) getArguments().getSerializable("BUSINESSREGDATA" );
            updateShopInput.setOfficeAddress( addressess );
            updateShopInput.setShopAddress( addressess );
            Geocoder geocoder;
            geocoder = new Geocoder( getContext(), Locale.getDefault() );
            try
            {   useraddress = geocoder.getFromLocationName(addressess,1 );
                longitude= String.valueOf( useraddress.get( 0 ).getLongitude() );
                updateShopInput.setLng( String.valueOf( longitude ) );
                latitude= String.valueOf( useraddress.get( 0 ).getLatitude() );
                updateShopInput.setLat( String.valueOf( latitude ) );
                pincode=useraddress.get( 0 ).getPostalCode();
                updateShopInput.setShopPincode( Integer.valueOf( pincode ) );
                updateShopInput.setShopCity( useraddress.get( 0 ).getLocality() );
                updateShopInput.setShopCountry( useraddress.get( 0 ).getCountryName() );
                updateShopInput.setShopState( useraddress.get( 0 ).getAdminArea() );
            }
            catch (IOException e)
            { e.printStackTrace(); } } }
        else
        {   tv_city.setText( updateShopInput.getOfficeAddress() );
            longitude=updateShopInput.getLng();
            latitude=updateShopInput.getLat();
            pincode= String.valueOf( updateShopInput.getShopPincode() ); }

        setvaluestoviews(updateShopInput);
        categoryLists = new Gson().fromJson( SharedPreference.getInstance().getString("CATEGORIES"), CategoryList.class );
        tinycategorylists.addAll( categoryLists.getTinycategorylist() );

        for (Tinycategorylist tinycategorylist:tinycategorylists)
        { if ( tinycategorylist.getId().equals( updateShopInput.getCategoryId() ) )
        {   categoryListname = tinycategorylist.getTinycatName();
            tv_category.setText( categoryListname );
            categoryId=tinycategorylist.getId(); } }
        verifyStoragePermissions(this);

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        addTextChangedListener(et_name);
        addTextChangedListener(et_email);
        addTextChangedListener(et_phone);
        addTextChangedListener(et_lastname);
        addTextChangedListener(et_business);
        addTextChangedListener(et_shopphone);
        addTextChangedListeners(tv_intime);
        addTextChangedListeners(tv_outtime);
        addTextChangedListeners(tv_category);
    }

    @OnClick(R.id.iv_nameedit)
    public void editname()
    {   et_name.setEnabled( true );
        et_name.setFocusable( true ); }

    @OnClick(R.id.iv_lastnameedit)
    public void editlname()
    {   et_lastname.setEnabled( true );
        et_lastname.setFocusable( true ); }

    @OnClick(R.id.iv_emailedit)
    public void editemail()
    {   et_email.setEnabled( true );
        et_email.setFocusable( true ); }

    @OnClick(R.id.iv_phoneedit)
    public void editmobile()
    {   et_phone.setEnabled( true );
        et_phone.setFocusable( true ); }

    @OnClick(R.id.iv_shopnameedit)
    public void editbus()
    {   et_business.setEnabled( true );
        et_business.setFocusable( true ); }

    @OnClick(R.id.iv_shopphoneedit)
    public void editshopmob()
    {   et_shopphone.setEnabled( true );
        et_shopphone.setFocusable( true ); }

    @Override
    protected void doOnTextChanged()
    {   String firstName = checkEmptyNull(et_name.getText().toString()) ? null : et_name.getText().toString();
        String shopmobile = checkEmptyNull(et_shopphone.getText().toString()) && isValidMobile(et_shopphone) ? null : et_shopphone.getText().toString();
        String mobile = checkEmptyNull(et_phone.getText().toString()) && isValidMobile(et_phone) ? null : et_phone.getText().toString();
        String address = checkEmptyNull(tv_city.getText().toString()) ? null : tv_city.getText().toString();
        String lastName = checkEmptyNull(et_lastname.getText().toString()) ? null : et_lastname.getText().toString();
        String email = checkEmptyNull(et_email.getText().toString()) ? null : et_email.getText().toString();
        String shopname = checkEmptyNull(et_business.getText().toString()) ? null : et_business.getText().toString();

        updateShopInput.setFirstName(firstName);
        updateShopInput.setLastName(lastName);
        updateShopInput.setPhone(mobile);
        updateShopInput.setEmail(email);
        updateShopInput.setCountryCode(91);
        updateShopInput.setId( Integer.valueOf( String.valueOf(updateShopInput.getId()) ) );
        if ( !TextUtils.isEmpty(categoryId) )
        updateShopInput.setCategoryId( Integer.valueOf( categoryId ) );
        updateShopInput.setTinyshopName(shopname);
        updateShopInput.setTinyshopName( shopname );
        updateShopInput.setShopPhone(shopmobile);
        updateShopInput.setShopAddress(address); }

    private void setvaluestoviews(BusinessDatum pendingDatum)
    {   et_name.setText( pendingDatum.getFirstName() );
        et_lastname.setText( pendingDatum.getLastName() );
        et_business.setText( pendingDatum.getTinyshopName() );
        et_email.setText( pendingDatum.getEmail() );
        et_phone.setText( pendingDatum.getPhone());
        tv_city.setText( pendingDatum.getShopAddress( ));
        et_shopphone.setText( pendingDatum.getShopPhone() );
        tv_intime.setText( pendingDatum.getOpenTime() );
        tv_outtime.setText( pendingDatum.getCloseTime() );
        pincode= String.valueOf( pendingDatum.getShopPincode() );
        latitude=pendingDatum.getLat() ;
        longitude=pendingDatum.getLng();

        if ( pendingDatum.getParcelAllowed().equals( 0 ) )
        { rb_parceloff.setChecked( true ); }
        else
        { rb_parcelon.setChecked( true ); }

        if ( pendingDatum.getInsideAllocate().equals( 0 ) )
        { rb_tableoff.setChecked( true ); }
        else
        { rb_tableon.setChecked( true ); }

        if ( pendingDatum.getServiceDeliveryAthome().equals( 0 ) )
        { rb_serviceoff.setChecked( true ); }
        else
        { rb_serviceon.setChecked( true ); }

        if ( pendingDatum.getPayLater().equals( 0 ) )
        { rb_paylateroff.setChecked( true ); }
        else
        { rb_paylateron.setChecked( true ); }

        if ( !TextUtils.isEmpty( pendingDatum.getProfileImg() ) )
            Picasso.get()
                    .load( Constants.IMAGE_URL+pendingDatum.getProfileImg())
                    .centerCrop()
                    .fit()
                    .placeholder( R.drawable.notavailable )
                    .into(iv_profile);

        if ( !TextUtils.isEmpty( pendingDatum.getLivePhoto() ) )
            Picasso.get()
                    .load(Constants.IMAGE_URL+pendingDatum.getLivePhoto())
                    .centerCrop()
                    .fit()
                    .placeholder( R.drawable.notavailable )
                    .into(iv_shopphoto);

        if ( pendingDatum.getIsMoving()!=null )
        { if (pendingDatum.getIsMoving().equals( 1 ))
        { tv_shop.setBackground(null);
            selectedType="mobility";
            tv_mobility.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
            tv_mobility.setTextColor( getResources().getColor( R.color.colorWhite ) ); }
        else if (pendingDatum.getIsMoving().equals( 0 ))
        { tv_mobility.setBackground(null);
            selectedType="shop";
            tv_shop.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
            tv_shop.setTextColor( getResources().getColor( R.color.colorWhite ) ); } }
        else
        { }   }

    @OnClick({R.id.rb_parceloff, R.id.rb_parcelon})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_parceloff:
                if (checked) {
                    updateShopInput.setParcelAllowed( 0 );
                }
                break;
            case R.id.rb_parcelon:
                if (checked) {
                    updateShopInput.setParcelAllowed( 1 );
                }
                break;
        }
    }

    @OnClick({R.id.rb_tableoff, R.id.rb_tableon})
    public void radiotable(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_tableoff:
                if (checked) {
                    updateShopInput.setInsideAllocate( 0 );
                }
                break;
            case R.id.rb_tableon:
                if (checked) {
                    updateShopInput.setInsideAllocate( 1 );
                }
                break;
        }
    }

    @OnClick({R.id.rb_serviceoon, R.id.rb_serviceoff})
    public void radioservice(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_serviceoff:
                if (checked) {
                    updateShopInput.setServiceDeliveryAthome( 0 );
                }
                break;
            case R.id.rb_serviceoon:
                if (checked) {
                    updateShopInput.setServiceDeliveryAthome( 1 );
                }
                break;
        }
    }

    @OnClick({R.id.rb_paylateroff, R.id.rb_paylateron})
    public void radiopaylater(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_paylateroff:
                if (checked) {
                    updateShopInput.setPayLater( 0 );
                }
                break;
            case R.id.rb_paylateron:
                if (checked) {
                    updateShopInput.setPayLater( 1 );
                }
                break;
        }
    }

    @OnClick(R.id.tv_category)
    public void clickcategory()
    {   AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select City");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);

        for (Tinycategorylist tinycategorylist : tinycategorylists)
        { arrayAdapter.add(tinycategorylist.getTinycatName()); }

        builderSingle.setNegativeButton("cancel",(dialog,which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {   @Override
            public void onClick(DialogInterface dialog, int which)
        {   Tinycategorylist tinycategorylist = tinycategorylists.get(which);
            categoryname = tinycategorylist.getTinycatName();
            categoryId=tinycategorylist.getId();
            tv_category.setText(categoryname);
        } } );

        builderSingle.show(); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_businessupdate; }

    @Override
    protected Object getModule()
    { return new BusinessUpdateModule(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress(); }

    @OnClick(R.id.iv_back)
    public void clickback()
    {   if ( getActivity()!=null )
        getActivity().onBackPressed(); }

    @OnClick(R.id.tv_editcity)
    public void clickchange()
    {   MapFragment mapFragment=new MapFragment();
        Bundle bundle=new Bundle(  );
        bundle.putString("BUSINESSUPDATEDATA","1000");
        bundle.putSerializable("BUSINESSUPDATE",updateShopInput);
        mapFragment.setArguments( bundle );
        ((DashBoardActivity)getActivity()).replaceFragment(mapFragment,MapFragment.class.getName() ); }

    @OnClick(R.id.tv_mobility)
    public void onYesClick()
    {   resetView();
        selectedType = "mobility";
        selectedvalue=1;
        tv_shop.setBackground(null);
        tv_mobility.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_mobility.setTextColor( getResources().getColor( R.color.colorWhite ) ); }

    @OnClick(R.id.tv_shop)
    public void onNoClick() {
        resetView();
        selectedType = "shop";
        selectedvalue=0;
        tv_mobility.setBackground( null );
        tv_shop.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_shop.setTextColor( getResources().getColor( R.color.colorWhite ) ); }

    private void resetView()
    {   tv_shop.setTextColor( getResources().getColor( R.color.quantum_grey600 ) );
        tv_mobility.setTextColor( getResources().getColor( R.color.quantum_grey600 ) );
        tv_shop.setBackgroundColor( getResources().getColor( R.color.colorWhite ) );
        tv_mobility.setBackgroundColor( getResources().getColor( R.color.colorWhite ) ); }

    @OnClick(R.id.btn_updateshop)
    public void clickverify()
    { if ( validateAllFields() )
     {  if ( !TextUtils.isEmpty( latitude ) )
            latitude=checkEmptyNull( String.valueOf( latitude ) )?null:latitude;
             updateShopInput.setLat(latitude ) ;
        if ( !TextUtils.isEmpty( longitude ) )
            longitude= checkEmptyNull(longitude)?null:longitude;
            updateShopInput.setLng( longitude );
        if ( !TextUtils.isEmpty( pincode ) )
            pincode=checkEmptyNull(pincode)?null:pincode;
        updateShopInput.setShopPincode( Integer.valueOf( pincode ) );
        updateShopInput.setNote( "Note" );
        if ( selectedType=="mobility" )
        { updateShopInput.setIsMoving(1); }
        else if ( selectedType=="shop" )
        { updateShopInput.setIsMoving(0); }
        businessProPresenter.businessproupdate(updateShopInput,SharedPreference.getInstance().getAuthToken()); }
    else
    { Toast.makeText(getContext(),"Please check all fields",Toast.LENGTH_SHORT ).show(); } }

    private boolean validateAllFields()
    {   if (checkEmptyText( et_name, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_lastname, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_phone, getString(R.string.field_cant_be_empty)))
            return false;
        else if (!isValidEmail(  et_email, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_business, getString(R.string.field_cant_be_empty)))
            return false;
        else
            return !checkEmptyText( et_shopphone, getString(R.string.field_cant_be_empty));    }


    public void verifyStoragePermissions(BusinessUpdateFragment businessProFragment)
    {   // Check if we have write permission
        { if ( ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {   // We don't have permission so prompt the user
            requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },REQUEST_EXTERNAL_STORAGE);
        }}}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    { switch(requestCode) {
        case REQUEST_EXTERNAL_STORAGE:
        { // If request is cancelled, the result arrays are empty.
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            { Toast.makeText(getContext(),"Cannot write images to external storage", Toast.LENGTH_SHORT).show(); } } } }


    @OnClick(R.id.iv_profile)
    public void clickimage()
    { permissionHelper.check_permission( permissions,"Need Storage and Camera permission to pick image from gallery", STORAGE_AND_CAMERA_REQUEST_CODE ); }


    @Override
    public void PermissionGranted(int request_code) {
        if (request_code == STORAGE_AND_CAMERA_REQUEST_CODE)
        {   Uri destination = getOutputMediaFileUri();
            if (destination == null)
            { Toast.makeText(getContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                return; }
            CropImage.isImageIntentRequired = true;
            CropImage.activity()
                    .setGuidelines( CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start( Objects.requireNonNull(getContext()),this); } }

    private Uri getOutputMediaFileUri()
    {   File file = getOutputMediaFile();
        if (file != null)
        { return FileProvider.getUriForFile(Objects.requireNonNull(getContext()), BuildConfig.APPLICATION_ID + ".myfileprovider", file); }
        return null; }

    private File getOutputMediaFile() {
        File mediaStorageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir( Environment.DIRECTORY_PICTURES);
        if (!Objects.requireNonNull(mediaStorageDir).exists())
        { if (!mediaStorageDir.mkdirs())
        { return null; } }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg"); }

    private byte[] byteLevelCompression(byte[] payload) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(payload, 0, payload.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60, out);
        return out.toByteArray(); }

    private byte[] getBytes(InputStream is) throws IOException
    {   ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len;
        while ((len = is.read(buff)) != -1)
        { byteBuff.write(buff, 0, len); }
        return byteBuff.toByteArray(); }

    private void uploadimage(byte[] imageBytes, String imagefrom)
    {   imagereq=imagefrom;
        if ( imagereq=="profile" )
        {  RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM, "tinyshop/docimages");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath",uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "public");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();

            businessProPresenter.uploadvalidate( payload,type, body ); }

        else if ( imagereq=="shopphoto" )
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/shopphoto");
            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "public");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg",requestFile);
            showProgress();
            businessProPresenter.uploadvalidate( payload,type, body ); } }

    /**
     * Override the activity's onActivityResult(), check the request code, and
     * do something with the returned place data (in this example it's place name and place ID).
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {   CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {   InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(result).getUri());
                    byte[] bytes = byteLevelCompression(getBytes(Objects.requireNonNull(inputStream)));
                    uploadimage(bytes,"profile" );
                    setSelectedImageToView(result.getUri()); }
                catch (IOException e)
                { e.printStackTrace(); } }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                error.printStackTrace(); } }

        else
        {   EasyImage.handleActivityResult( requestCode, resultCode, data, getActivity(), new DefaultCallback()
        {   @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type )
        {   super.onImagePickerError( e, source, type );
            e.printStackTrace();
            showToast( getString(R.string.something_went_wrong) ); }

            @Override
            public void onImagePicked( File imageFile, EasyImage.ImageSource source, int type )
            {   // CALL THIS METHOD TO GET THE ACTUAL PATH
                Uri uri=data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(uri));
                    byte[] bytes = new byte[0];
                    bytes = byteLevelCompression(getBytes( Objects.requireNonNull(inputStream)));
                    uploadimage(bytes,"shopphoto");
                    setSelectedShopToView(uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } }); } }

    private void setSelectedShopToView(Uri uri) {
        Picasso.get()
                .load(uri)
                .centerCrop()
                .fit()
                .into(iv_shopphoto); }

    private void setSelectedImageToView(Uri uri)
    {  Picasso.get()
            .load(uri)
            .centerCrop()
            .fit()
            .into(iv_profile); }

    @Override
    public void imageupload(ImageResponse imageResponse)
    {   hideProgress();
        if ( imagereq=="profile" )
        {   Toast.makeText( getContext(), "Image Uploaded", Toast.LENGTH_SHORT ).show();
            updateShopInput.setProfileImg( imageResponse.getImageURL() ); }

        else if ( imagereq=="shopphoto" )
        {   Toast.makeText( getContext(),"Shop photo uploaded",Toast.LENGTH_SHORT ).show();
            updateShopInput.setLivePhoto( imageResponse.getImageURL() ); } }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) { }

    @Override
    public void PermissionDenied(int request_code) { }

    @Override
    public void NeverAskAgain(int request_code) { }

    //SELECT TIME FROM SELECTOR
    @OnClick(R.id.tv_intime)
    void selecttime()
    {   timeselected="opentime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
            (this,calendar.get( Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    @OnClick(R.id.tv_outtime)
    void selectouttime()
    {   timeselected="closetime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
                (this,calendar.get( Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    //Interface for timesetlistener
    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute)
    {
        if ( timeselected=="opentime" )
      { Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        tv_intime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
        selectedopenTime = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
        updateShopInput.setOpenTime( selectedopenTime );
      }
        else if ( timeselected=="closetime" )
        {   Calendar cal = new java.util.GregorianCalendar();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            tv_outtime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
            selectedcloseTime = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
            updateShopInput.setCloseTime( selectedcloseTime ); }
        }

    @OnClick(R.id.iv_shopphoto)
    public void selectshopphoto()
    { if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
    { EasyImage.openChooserWithGallery(this,"Select Image",1 ); }
    else
    { if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
    { Toast.makeText(getContext(),"Permission Needed", Toast.LENGTH_LONG).show(); }
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_SHOP_PHOTO); } }

    @Override
    public void businessupdate(BusinessProResponse businessProResponse)
    {   hideProgress();
        Toast.makeText( getContext(),"Updated Successfully", Toast.LENGTH_SHORT ).show();
        ((DashBoardActivity)getActivity()).replaceFragment( new HomeScreenFragment(),HomeScreenFragment.class.getName() ); } }
