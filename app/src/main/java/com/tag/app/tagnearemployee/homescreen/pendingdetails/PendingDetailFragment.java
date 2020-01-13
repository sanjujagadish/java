package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;
import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.BuildConfig;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.appUtils.PermissionHelper;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.boardingscreens.forgototpverify.OtpForgotPasswordFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.Tinycategorylist;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;
import com.tag.app.tagnearemployee.views.cropper.CropImage;
import com.tag.app.tagnearemployee.views.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
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

public class PendingDetailFragment extends BaseFragment implements PendingDetailContract.View, GridTimePickerDialog.OnTimeSetListener,
        PermissionHelper.PermissionResultCallback
{   @Inject
    PendingDetailPresenter pendingDetailPresenter;

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
    @BindView( R.id.tv_referencedoc )
    TextView tv_referencedoc;
    @BindView( R.id.tv_idproof )
    TextView tv_idproof;
    @BindView( R.id.tv_intime )
    AppCompatTextView tv_opentime;
    @BindView( R.id.tv_outtime )
    AppCompatTextView tv_closetime;

    @BindView( R.id.toolbar )
    Toolbar toolbar;
    @BindView( R.id.signature_pad )
    SignaturePad signature_pad;
    @BindView( R.id.clear_button )
    Button clear_button;
    @BindView( R.id.save_button )
    Button save_button;

    @BindView( R.id.cb_category )
    CheckBox cb_category;
    @BindView( R.id.cb_name )
    CheckBox cb_name;
    @BindView( R.id.cb_lastname )
    CheckBox cb_lastname;
    @BindView( R.id.cb_email )
    CheckBox cb_email;
    @BindView( R.id.cb_mobile )
    CheckBox cb_mobile;
    @BindView( R.id.cb_shopname )
    CheckBox cb_shopname;
    @BindView( R.id.cb_shopmobile )
    CheckBox cb_shopmobile;

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

    private List<Tinycategorylist> tinycategorylists = new ArrayList<>( );
    private CategoryList categoryLists;
    private String categoryname,categoryListname,categoryId;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String selectedType,selectedvalue,pincode;
    private String longitude;
    private String latitude;
    private List<Address> useraddress;
    private PermissionHelper permissionHelper;
    private ArrayList<String> permissions = new ArrayList<>();

    private static final int REFERENCE_DOC = 3;
    private final int STORAGE_AND_CAMERA_REQUEST_CODE = 4000;
    private static final int IDENTITY_PROOF = 4;
    private static final int PICK_SHOP_PHOTO = 2000;

    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private String selectedTime,shoptime;
    private String imagereq;
    private Bitmap bitmap;
    private PendingDatum pendingDatum;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init(Bundle savedInstanceState)
    {   pendingDetailPresenter.setView(this);
        toolbar.setTitle( "Details" );

        permissionHelper = new PermissionHelper(getContext(), this);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        pendingDatum = new PendingDatum();
        pendingDatum = (PendingDatum) getArguments().getSerializable("VENDORDETAILS" );
        pendingDatum.setShopId( pendingDatum.getId() );
        pendingDatum.setId( pendingDatum.getId() );

        et_name.setEnabled( false );
        et_lastname.setEnabled( false );
        et_phone.setEnabled( false );
        et_email.setEnabled( false );
        et_business.setEnabled( false );
        et_shopphone.setEnabled( false );
        tv_category.setFocusable( false );

        if ( getArguments()!=null )
        { if ( getArguments().getString("MAPDETAILS") !=null)
        {   String addressess=getArguments().getString("MAPDETAILS" );
            tv_city.setText( addressess);
            pendingDatum= (PendingDatum) getArguments().getSerializable("VENDORDETAILS" );
            pendingDatum.setOfficeAddress( addressess );
            pendingDatum.setShopAddress( addressess );
            Geocoder geocoder;
            geocoder = new Geocoder( getContext(),Locale.getDefault() );
            try
            {   useraddress = geocoder.getFromLocationName(addressess,1 );
                longitude= String.valueOf( useraddress.get( 0 ).getLongitude() );
                pendingDatum.setLng( longitude );
                latitude= String.valueOf( useraddress.get( 0 ).getLatitude() );
                pendingDatum.setLat( latitude );
                pincode=useraddress.get( 0 ).getPostalCode();
                pendingDatum.setShopPincode( Integer.valueOf( pincode ) );
                pendingDatum.setShopCity( useraddress.get( 0 ).getLocality() );
                pendingDatum.setShopCountry( useraddress.get( 0 ).getCountryName() );
                pendingDatum.setShopState( useraddress.get( 0 ).getAdminArea() );
            }
            catch (IOException e)
            { e.printStackTrace(); } } }
        else
        {   tv_city.setText( pendingDatum.getOfficeAddress() );
            longitude= String.valueOf( Double.parseDouble( pendingDatum.getLng() ) );
            latitude= String.valueOf( Double.parseDouble( pendingDatum.getLat() ) );
            pincode= String.valueOf( pendingDatum.getShopPincode() ); }

        setviewvalues(pendingDatum);
        categoryLists = new Gson().fromJson( SharedPreference.getInstance().getString("CATEGORIES"), CategoryList.class );
        tinycategorylists.addAll( categoryLists.getTinycategorylist() );

        for (Tinycategorylist tinycategorylist:tinycategorylists)
        { if ( tinycategorylist.getId().equals( pendingDatum.getCategoryId() ) )
        {   categoryListname = tinycategorylist.getTinycatName();
            tv_category.setText( categoryListname );
            categoryId=tinycategorylist.getId(); } }
            verifyStoragePermissions(this);

        signature_pad.setOnSignedListener( new SignaturePad.OnSignedListener()
        {   @Override
        public void onStartSigning() { }

            @Override
            public void onSigned()
            { save_button.setEnabled(true);
                clear_button.setEnabled(true); }

            @Override
            public void onClear()
            { save_button.setEnabled(false);
                clear_button.setEnabled(false); } } );

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
        addTextChangedListeners(tv_opentime);
        addTextChangedListeners( tv_closetime );
        addTextChangedListeners(tv_category);
    }

    @Override
    protected void doOnTextChanged()
    {   String firstName = checkEmptyNull(et_name.getText().toString()) ? null : et_name.getText().toString();
        String shopmobile = checkEmptyNull(et_shopphone.getText().toString()) && isValidMobile(et_shopphone) ? null : et_shopphone.getText().toString();
        String mobile = checkEmptyNull(et_phone.getText().toString()) && isValidMobile(et_phone) ? null : et_phone.getText().toString();
        String address = checkEmptyNull(tv_city.getText().toString()) ? null : tv_city.getText().toString();
        String lastName = checkEmptyNull(et_lastname.getText().toString()) ? null : et_lastname.getText().toString();
        String email = checkEmptyNull(et_email.getText().toString()) ? null : et_email.getText().toString();
        String shopname = checkEmptyNull(et_business.getText().toString()) ? null : et_business.getText().toString();

        pendingDatum.setFirstName(firstName);
        pendingDatum.setLastName(lastName);
        pendingDatum.setPhone(mobile);
        pendingDatum.setEmail(email);
        pendingDatum.setCountryCode(91);
        pendingDatum.setShopId(pendingDatum.getId() );
        if ( !TextUtils.isEmpty(categoryId) )
        pendingDatum.setCategoryId( Integer.valueOf( categoryId ) );
        pendingDatum.setTinyshopName(shopname);
        pendingDatum.setTinyshopName( shopname );
        pendingDatum.setShopPhone(shopmobile);
        pendingDatum.setShopAddress(address); }

    private void setviewvalues(PendingDatum pendingDatum)
    {   et_name.setText( pendingDatum.getFirstName() );
        et_lastname.setText( pendingDatum.getLastName() );
        et_business.setText( pendingDatum.getTinyshopName() );
        et_email.setText( pendingDatum.getEmail() );
        et_phone.setText( pendingDatum.getPhone());
        tv_city.setText( pendingDatum.getOfficeAddress( ));
        et_shopphone.setText( pendingDatum.getShopPhone() );
        tv_opentime.setText( pendingDatum.getOpenTime() );
        tv_closetime.setText( pendingDatum.getCloseTime() );
        pincode= String.valueOf( pendingDatum.getShopPincode() );
        tv_referencedoc.setText( pendingDatum.getRef1doc_copy() );
        tv_idproof.setText( pendingDatum.getIdproofCopy() );
        latitude=pendingDatum.getLat();
        longitude=pendingDatum.getLng();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Constants.IMAGE_URL+pendingDatum.getSignature());
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    signature_pad.setSignatureBitmap( image );
                    onStop(); }
                catch(IOException e) {
                    System.out.println(e);
                } } });

        if ( !TextUtils.isEmpty( pendingDatum.getProfileImg() ) )
            Picasso.get()
                    .load(Constants.IMAGE_URL+pendingDatum.getProfileImg())
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

        if ( pendingDatum.getPay_later().equals( 0 ) )
        { rb_paylateroff.setChecked( true ); }
        else
        { rb_paylateron.setChecked( true ); }

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
        { }  }

    @OnClick({R.id.rb_parceloff, R.id.rb_parcelon})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_parceloff:
                if (checked) {
                    pendingDatum.setParcelAllowed( 0 );
                }
                break;
            case R.id.rb_parcelon:
                if (checked) {
                    pendingDatum.setParcelAllowed( 1 );
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
                    pendingDatum.setInsideAllocate( 0 );
                }
                break;
            case R.id.rb_tableon:
                if (checked) {
                    pendingDatum.setInsideAllocate( 1 );
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
                    pendingDatum.setServiceDeliveryAthome( 0 );
                }
                break;
            case R.id.rb_serviceoon:
                if (checked) {
                    pendingDatum.setServiceDeliveryAthome( 1 );
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
                    pendingDatum.setPay_later( String.valueOf( 0 ) );
                }
                break;
            case R.id.rb_paylateron:
                if (checked) {
                    pendingDatum.setPay_later( String.valueOf( 1 ) );
                }
                break;
        }
    }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_pending_detail; }

    @Override
    protected Object getModule()
    { return new PendingDetailModule(); }

    @Override
    public void onFailure(Throwable throwable)
    {  hideProgress(); }
    @OnClick(R.id.clear_button)
    public void clickclear()
    { signature_pad.clear(); }

    @OnClick(R.id.save_button)
    public void clicksave()
    {   Bitmap signaturebitmap=signature_pad.getSignatureBitmap();
        if (addJpgSignatureToGallery(signaturebitmap))
        {   Toast.makeText(getContext(),"Signature saved into the Gallery", Toast.LENGTH_SHORT).show();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            signaturebitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            uploadimage( bytes,"signature" ); }
        else
        { Toast.makeText(getContext(),"Unable to store the signature", Toast.LENGTH_SHORT).show(); }

        if (addSvgSignatureToGallery(signature_pad.getSignatureSvg()))
        { Toast.makeText(getContext(),"SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show(); }
        else
        { Toast.makeText(getContext(),"Unable to store the SVG signature", Toast.LENGTH_SHORT).show(); } }

    public boolean addJpgSignatureToGallery(Bitmap signature)
    {   boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"),String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true; }
        catch (IOException e)
        { e.printStackTrace(); }
        return result; }

    private void scanMediaFile(File photo)
    {   Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent); }

    public boolean addSvgSignatureToGallery(String signatureSvg)
    {   boolean result = false;
        try
        {   File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true; }
        catch (IOException e)
        { e.printStackTrace(); }
        return result; }

    @OnClick(R.id.tv_category)
    public void clickcategory()
    {   AlertDialog.Builder builderSingle = new AlertDialog.Builder( getContext() );
        builderSingle.setTitle("Select Category");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( getContext(), android.R.layout.select_dialog_singlechoice );

        for (Tinycategorylist tinycategorylist : tinycategorylists)
        { arrayAdapter.add(tinycategorylist.getTinycatName()); }

        builderSingle.setNegativeButton("cancel",(dialog,which)-> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter,new DialogInterface.OnClickListener()
        {   @Override
            public void onClick(DialogInterface dialog, int which)
        {   Tinycategorylist tinycategorylist = tinycategorylists.get(which);
            categoryname = tinycategorylist.getTinycatName();
            categoryId=tinycategorylist.getId();
            tv_category.setText(categoryname);
        } } );

        builderSingle.show(); }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName );
        if (!file.mkdirs())
        { Log.e("SignaturePad","Directory not created"); }
        return file; }

    public void saveBitmapToJPG(Bitmap bitmap,File photo) throws IOException
    {   Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor( Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        stream.close(); }

    public void verifyStoragePermissions(PendingDetailFragment pendingDetailFragment)
    {   // Check if we have write permission
        { if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
           {   // We don't have permission so prompt the user
               requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },REQUEST_EXTERNAL_STORAGE);
           } } }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    { switch(requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
               { // If request is cancelled, the result arrays are empty.
                 if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                 { Toast.makeText(getContext(),"Cannot write images to external storage", Toast.LENGTH_SHORT).show(); } } } }

    @OnClick(R.id.btn_updateshop)
    public void clickverify()
    {  if ( validatefields() )
       {   if ( !TextUtils.isEmpty( latitude ) )
           latitude=String.valueOf(checkEmptyNull(latitude)?null:latitude);
           pendingDatum.setLat( latitude );
           if ( !TextUtils.isEmpty( longitude ) )
           longitude= String.valueOf(checkEmptyNull(longitude)?null:longitude);
           pendingDatum.setLng( longitude );
           if ( !TextUtils.isEmpty( pincode ) )
           pincode=checkEmptyNull(pincode)?null:pincode;
           pendingDatum.setShopPincode( Integer.valueOf( pincode ) );
           pendingDatum.setNote( "Note" );
           if ( selectedType=="mobility" )
           { pendingDatum.setIsMoving(1); }
           else if ( selectedType=="shop" )
           { pendingDatum.setIsMoving(0); }
           pendingDetailPresenter.detailsupdate(SharedPreference.getInstance().getAuthToken(),pendingDatum); }
        else
        { Toast.makeText(getContext(),"Please check all fields",Toast.LENGTH_SHORT ).show(); } }

    private boolean validatefields()
    {   if (isChecked(cb_name,getString(R.string.Please_check_fields)))
            return false;
        else if(isChecked(cb_lastname,getString(R.string.Please_check_fields)))
            return false;
        else if(isChecked(cb_email,getString(R.string.Please_check_fields)))
            return false;
        else if (isChecked(cb_mobile,getString(R.string.Please_check_fields)))
            return false;
        else if (isChecked(cb_shopname,getString(R.string.Please_check_fields)))
            return false;
        else if (isChecked(cb_shopname,getString(R.string.Please_check_fields)))
            return false;
        else if (checkEmptyText(et_business,getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText(et_lastname,getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText(et_name,getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText(et_phone,getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText(et_shopphone,getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkempty(pendingDatum.getProfileImg(),"Please select image"))
            return false;
        else
            return !isChecked(cb_shopmobile,getString(R.string.Please_check_fields)); }

    @Override
    public void updatedetails( UpdateShopResponse updateShopResponse )
    {   hideProgress();
        OtpForgotPasswordFragment otpForgotPasswordFragment = new OtpForgotPasswordFragment();
        Bundle bundle=new Bundle( );
        bundle.putInt("DETAILSPAGEKEY", 2 );
        bundle.putSerializable("details", updateShopResponse );
        otpForgotPasswordFragment.setArguments( bundle );
        ((DashBoardActivity) getActivity()).replaceFragment( otpForgotPasswordFragment, OtpForgotPasswordFragment.class.getName() ); }

    @OnClick(R.id.iv_back)
    public void clickback()
    {   if ( getActivity()!=null )
        getActivity().onBackPressed(); }

   @OnClick(R.id.tv_editcity)
   public void clickchange()
   {   MapFragment mapFragment=new MapFragment();
       Bundle bundle = new Bundle( );
       bundle.putString( "PENDINGDETAILS", "2" );
       bundle.putSerializable( "VENDORDETAILS", pendingDatum );
       mapFragment.setArguments( bundle );
       ((DashBoardActivity) getActivity()).replaceFragment( mapFragment, MapFragment.class.getName() ); }

   @OnClick(R.id.iv_nameedit)
   public void editname()
   { et_name.setEnabled( true );
       et_name.setFocusable( true ); }

   @OnClick(R.id.iv_lnameedit)
   public void editlname()
   { et_lastname.setEnabled( true );
        et_lastname.setFocusable( true ); }

   @OnClick(R.id.iv_emailedit)
   public void editemail()
   { et_email.setEnabled( true );
        et_email.setFocusable( true ); }

   @OnClick(R.id.iv_mobileedit)
   public void editmobile()
   { et_phone.setEnabled( true );
        et_phone.setFocusable( true ); }

   @OnClick(R.id.iv_busedit)
   public void editbus()
   { et_business.setEnabled( true );
        et_business.setFocusable( true ); }

   @OnClick(R.id.iv_shoopmobedit)
   public void editshopmob()
   { et_shopphone.setEnabled( true );
        et_shopphone.setFocusable( true ); }

   @OnClick(R.id.tv_mobility)
   public void onYesClick()
   {   resetView();
        selectedType = "mobility";
        selectedvalue="1";
        tv_shop.setBackground( null );
        tv_mobility.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_mobility.setTextColor( getResources().getColor( R.color.colorWhite ) ); }

   @OnClick(R.id.tv_shop)
   public void onNoClick()
   {    resetView();
        selectedType = "shop";
        selectedvalue = "0";
        tv_mobility.setBackground( null );
        tv_shop.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_shop.setTextColor( getResources().getColor( R.color.colorWhite ) ); }

   private void resetView()
   {    tv_shop.setTextColor( getResources().getColor( R.color.quantum_grey600 ) );
        tv_mobility.setTextColor( getResources().getColor( R.color.quantum_grey600 ) );
        tv_shop.setBackgroundColor( getResources().getColor( R.color.colorWhite ) );
        tv_mobility.setBackgroundColor( getResources().getColor( R.color.colorWhite ) ); }

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
        { return FileProvider.getUriForFile(Objects.requireNonNull(getContext()),BuildConfig.APPLICATION_ID+".myfileprovider",file); }
        return null; }

    private File getOutputMediaFile() {
        File mediaStorageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir( Environment.DIRECTORY_PICTURES);
        if (!Objects.requireNonNull(mediaStorageDir).exists())
        { if (!mediaStorageDir.mkdirs())
        { return null; } }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg"); }

    private byte[] byteLevelCompression(byte[] payload) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(payload,0, payload.length);
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
        { RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
          RequestBody uploadpath = RequestBody.create( MultipartBody.FORM, "tinyshop/docimages");

        Map<String, RequestBody> payload = new HashMap<>();
        payload.put("uploadpath",uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "public");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

        showProgress();

        pendingDetailPresenter.uploadvalidate( payload,type, body ); }

        else if ( imagereq=="signature" )
        {
            RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/signature");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody requesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",requesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg",requestFile);

            showProgress();

            pendingDetailPresenter.uploadvalidate( payload,type,body ); }

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
            pendingDetailPresenter.uploadvalidate( payload,type, body ); }
    }

    /**
     * Override the activity's onActivityResult(), check the request code, and
     * do something with the returned place data (in this example it's place name and place ID).
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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
        else if ( requestCode==REFERENCE_DOC )
        {   if ( resultCode==RESULT_OK )
            {  Uri datas = data.getData();
                File file = new File(datas.getPath());
                InputStream iStream = null;
                try
                {   iStream = getContext().getContentResolver().openInputStream(datas);
                    bitmap=BitmapFactory.decodeStream( iStream );
                    byte[] inputData =getBytes( iStream );

                    uploadRefDocument( inputData,"refdocument" );
                    tv_referencedoc.setText( file.getName() ); }
                catch (Exception e)
                { e.printStackTrace(); } }

            else
            { } }

        else if ( requestCode==IDENTITY_PROOF )
        {   if ( resultCode==RESULT_OK ) {
            Uri datas = data.getData();
            File file = new File(datas.getPath());
            InputStream iStream = null;
            try
            {   iStream = getContext().getContentResolver().openInputStream(datas);
                bitmap = BitmapFactory.decodeStream( iStream );
                byte[] inputData =getBytes( iStream );

                uploadRefDocument( inputData,"idproof" );
                tv_idproof.setText( file.getName() ); }
            catch (Exception e)
            { e.printStackTrace(); } }
        else
        { } }
        else
        {   EasyImage.handleActivityResult( requestCode, resultCode, data, getActivity(), new DefaultCallback()
        {   @Override
        public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type )
        {   super.onImagePickerError( e, source, type );
            e.printStackTrace();
            showToast( getString(R.string.something_went_wrong) ); }

            @Override
            public void onImagePicked( File imageFile, EasyImage.ImageSource source, int type )
            {
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                Uri uri=data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(uri));
                    byte[] bytes = new byte[0];
                    bytes = byteLevelCompression(getBytes( Objects.requireNonNull(inputStream)));
                    uploadimage(bytes,"shopphoto");
                    setShopPhoto(uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } }); } }

    private void setShopPhoto(Uri uri)
    {
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

    private void uploadRefDocument(byte[] byteArray, String documentupload)
    {   imagereq=documentupload;

        if ( imagereq=="refdocument" )
        { RequestBody requestFile = RequestBody.create( MediaType.parse("image/png"), byteArray);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/referencedocument");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();
            pendingDetailPresenter.uploadvalidate( payload,type,body ); }

        else if ( imagereq=="idproof" )
        { RequestBody requestFile = RequestBody.create( MediaType.parse("image/png"), byteArray);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/identityproof");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();
            pendingDetailPresenter.uploadvalidate( payload,type,body ); } }

   @Override
   public void imageupload(ImageResponse imageResponse)
   {    hideProgress();
       if ( imagereq=="profile" )
       {
           Toast.makeText( getContext(), "Image Uploaded", Toast.LENGTH_SHORT ).show();
           pendingDatum.setProfileImg( imageResponse.getImageURL() ); }
       else if ( imagereq=="signature" )
       {
           Toast.makeText( getContext(), "Signature captured", Toast.LENGTH_SHORT ).show();
           pendingDatum.setSignature( imageResponse.getImageURL() ); }
       else if ( imagereq=="refdocument" )
       {
           Toast.makeText( getContext(), "Reference Document Uploaded", Toast.LENGTH_SHORT ).show();
           pendingDatum.setRef1doc_copy( imageResponse.getImageURL() ); }
       else if ( imagereq=="idproof" )
       {
           Toast.makeText( getContext(),"Id proof Uploaded",Toast.LENGTH_SHORT ).show();
           pendingDatum.setIdproofCopy( imageResponse.getImageURL() ); }
       else if ( imagereq=="shopphoto" )
       {
           Toast.makeText( getContext(),"Shop photo uploaded",Toast.LENGTH_SHORT ).show();
           pendingDatum.setLivePhoto( imageResponse.getImageURL() ); }  }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    //SELECT TIME FROM SELECTOR
   @OnClick(R.id.tv_intime)
   void selecttime()
    {   shoptime="opentime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
            (this,calendar.get( Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    @OnClick(R.id.tv_outtime)
    void selectouttime()
    {   shoptime="closetime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
                (this,calendar.get( Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    //Interface for timesetlistener
    @Override
    public void onTimeSet(ViewGroup viewGroup,int hourOfDay,int minute)
    {   if ( shoptime=="opentime" )
     {  Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        tv_opentime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
        selectedTime = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
        pendingDatum.setOpenTime( selectedTime );
     }
       else if ( shoptime=="closetime" )
       {   Calendar cal = new java.util.GregorianCalendar();
           cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
           cal.set(Calendar.MINUTE, minute);
           tv_closetime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
           selectedTime = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
           pendingDatum.setCloseTime( selectedTime ); }
     }

    @OnClick(R.id.tv_referencedoc)
    void uploaddoc()
    {   if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    {   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,REFERENCE_DOC); }
    else
    {   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {   if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    { Toast.makeText(getContext(), "Permission Needed.", Toast.LENGTH_LONG).show(); } }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        { requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REFERENCE_DOC); } } }

    @OnClick(R.id.tv_idproof)
    void uploadid()
    {   if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    {   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,IDENTITY_PROOF); }
    else
    {   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {   if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    { Toast.makeText(getContext(), "Permission Needed.", Toast.LENGTH_LONG).show(); } }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        { requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, IDENTITY_PROOF); } } }

    @OnClick(R.id.iv_shopphoto)
    public void selectshopphoto()
    {   if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
    { EasyImage.openChooserWithGallery(this,"Select Image",1 ); }
    else
    { if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
    { Toast.makeText(getContext(),"Permission Needed", Toast.LENGTH_LONG).show(); }
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_SHOP_PHOTO); } }

}
