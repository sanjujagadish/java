package com.tag.app.tagnearemployee.homescreen.business;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.tag.app.tagnearemployee.homescreen.pendingdetails.MapFragment;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;
import com.tag.app.tagnearemployee.pojomodels.Tinycategorylist;
import com.tag.app.tagnearemployee.views.cropper.CropImage;
import com.tag.app.tagnearemployee.views.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

public class BusinessProFragment extends BaseFragment implements BusinessProContract.View,PermissionHelper.PermissionResultCallback,GridTimePickerDialog.OnTimeSetListener
{   @Inject
    BusinessProPresenter businessProPresenter;

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
    @BindView( R.id.tv_uploadmenu )
    AppCompatTextView tv_uploadmenu;

    @BindView( R.id.btn_updateshop )
    AppCompatButton btn_updateshop;

    @BindView( R.id.toolbar )
    Toolbar toolbar;
    @BindView( R.id.signature_pad )
    SignaturePad signature_pad;
    @BindView( R.id.clear_button )
    Button clear_button;
    @BindView( R.id.save_button )
    Button save_button;

    private static final int REFERENCE_DOC = 3;
    private final int STORAGE_AND_CAMERA_REQUEST_CODE = 4000;
    private static final int IDENTITY_PROOF = 4;
    private static final int PICK_SHOP_PHOTO = 2000;
    private static final int UPLOAD_MENU= 3000;

    private double latitude,longitude;
    private static final int REQUEST_EXTERNAL_STORAGE = 100;
    private List<Address> useraddress;

    private List<Tinycategorylist> tinycategorylists = new ArrayList<>( );
    private CategoryList categoryLists;
    private String categoryname,categoryId;
    private String selectedType,pincode,timeselected;
    private int selectedvalue;
    private BusinessPro updateShopInput;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private String selectedTime;
    private String imagereq;
    private Bitmap bitmap;
    private PermissionHelper permissionHelper;
    private ArrayList<String> permissions = new ArrayList<>();
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void init(Bundle savedInstanceState)
    {   businessProPresenter.setView(this);
        toolbar.setTitle( "Business Pro" );
        updateShopInput = new BusinessPro();

        permissionHelper = new PermissionHelper(getContext(),this);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        categoryLists = new Gson().fromJson( SharedPreference.getInstance().getString("CATEGORIES"), CategoryList.class );
        tinycategorylists.addAll( categoryLists.getTinycategorylist() );

        verifyStoragePermissions(this );
        //By default set tv_shop selected
        tv_mobility.setBackground(null);
        selectedType="shop";
        selectedvalue=0;
        tv_shop.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_shop.setTextColor( getResources().getColor( R.color.colorWhite ) );

        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener()
        {   @Override
            public void onStartSigning()
            { }

            @Override
            public void onSigned()
            { save_button.setEnabled(true);
              clear_button.setEnabled(true); }

            @Override
            public void onClear()
            {   save_button.setEnabled(false);
                clear_button.setEnabled(false); } } );

        if ( getArguments()!=null)
        {   if ( getArguments().getString("MAPDETAIL") !=null)
            {   String address=getArguments().getString("MAPDETAIL" );
                updateShopInput= (BusinessPro) getArguments().getSerializable("INPUTFIELDS" );
                tv_city.setText( address);
                et_name.setText(updateShopInput.getFirstName());
                et_lastname.setText( updateShopInput.getLastName() );
                et_email.setText( updateShopInput.getEmail());
                et_phone.setText( updateShopInput.getPhone() );
                et_business.setText( updateShopInput.getShop_name() );
                et_shopphone.setText( updateShopInput.getShopPhone() );
                tv_opentime.setText( updateShopInput.getOpenTime() );
                tv_closetime.setText( updateShopInput.getCloseTime() );
                tv_category.setText( updateShopInput.getCategoryName());
                tv_uploadmenu.setText( updateShopInput.getShopimage1() );
                tv_idproof.setText( updateShopInput.getIdproof_copy() );
                tv_referencedoc.setText( updateShopInput.getRef1doc_copy() );


                if ( !TextUtils.isEmpty( updateShopInput.getProfileImg() ) )
                 Picasso.get()
                        .load( Constants.IMAGE_URL+updateShopInput.getProfileImg())
                        .centerCrop()
                        .fit()
                        .into(iv_profile);

                if ( !TextUtils.isEmpty( updateShopInput.getLivePhoto() ) )
                 Picasso.get()
                        .load(Constants.IMAGE_URL+updateShopInput.getLivePhoto())
                        .centerCrop()
                        .fit()
                        .into(iv_shopphoto);

                Geocoder geocoder;
                geocoder = new Geocoder(getContext(),Locale.getDefault());
                try
                {   useraddress = geocoder.getFromLocationName(address,1);
                    longitude = useraddress.get(0).getLongitude();
                    latitude = useraddress.get(0).getLatitude();
                    pincode = useraddress.get(0).getPostalCode();
                    updateShopInput.setShopPincode( useraddress.get( 0 ).getPostalCode() );
                    updateShopInput.setShopCity( useraddress.get( 0 ).getLocality() );
                    updateShopInput.setShopCountry( useraddress.get( 0 ).getCountryName() );
                    updateShopInput.setShopState( useraddress.get( 0 ).getAdminArea() );
                }
                catch (IOException e)
                { e.printStackTrace(); }  } }
        else
        { tv_city.setText("Select location" ); }

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick(R.id.tv_category)
    public void clickcategory()
    {   AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select Category");

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
            updateShopInput.setCategoryId( categoryId );
            updateShopInput.setCategoryName(categoryname);
            tv_category.setText( categoryname );
        } } );

        builderSingle.show(); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_business; }

    @Override
    protected Object getModule()
    { return new BusinessProModule(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      getErrorMessage( throwable ); }

    @OnClick(R.id.iv_back)
    public void clickback()
    {   if ( getActivity()!=null )
        getActivity().onBackPressed(); }

    @OnClick(R.id.tv_editcity)
    public void clickchange()
    {   updateShopInput.setFirstName( checkEmptyNull( et_name.getText().toString() ) ? null : et_name.getText().toString() );
        updateShopInput.setLastName( checkEmptyNull( et_lastname.getText().toString() ) ? null : et_lastname.getText().toString() );
        updateShopInput.setPhone( checkEmptyNull( et_phone.getText().toString() ) ? null : et_phone.getText().toString() );
        updateShopInput.setEmail( checkEmptyNull( et_email.getText().toString() ) ? null : et_email.getText().toString() );
        updateShopInput.setShop_name( et_business.getText().toString().trim() );
        updateShopInput.setTinyshopName( checkEmptyNull( et_business.getText().toString() ) ? null : et_business.getText().toString().trim() );
        updateShopInput.setShopPhone( checkEmptyNull( et_shopphone.getText().toString() ) ? null : et_shopphone.getText().toString().trim() );

        if ( selectedType == "mobility" )
        { updateShopInput.setIsMoving( 1 ); }
        else if ( selectedType == "shop" )
        { updateShopInput.setIsMoving( 0 ); }

        MapFragment mapFragment=new MapFragment();
        Bundle bundle=new Bundle(  );
        bundle.putString("BUSINESSPRO","1");
        bundle.putSerializable("INPUTFIELDS",updateShopInput);
        mapFragment.setArguments( bundle );
        ((DashBoardActivity)getActivity()).replaceFragment( mapFragment, MapFragment.class.getName() ); }

    @OnClick(R.id.tv_mobility)
    public void onYesClick()
    {   resetView();
        selectedType = "mobility";
        selectedvalue=1;
        tv_shop.setBackground(null);
        tv_mobility.setBackground( getResources().getDrawable( R.drawable.rectangle_back ) );
        tv_mobility.setTextColor( getResources().getColor( R.color.colorWhite ) ); }

    @OnClick(R.id.tv_shop)
    public void onNoClick()
    {   resetView();
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
         {  showProgress();
            updateShopInput.setFirstName( checkEmptyNull( et_name.getText().toString() ) ? null : et_name.getText().toString() );
            updateShopInput.setLastName( checkEmptyNull( et_lastname.getText().toString() ) ? null : et_lastname.getText().toString() );
            updateShopInput.setPhone( checkEmptyNull( et_phone.getText().toString() ) ? null : et_phone.getText().toString() );
            updateShopInput.setEmail( checkEmptyNull( et_email.getText().toString() ) ? null : et_email.getText().toString() );
            updateShopInput.setCountryCode( 91 );
            updateShopInput.setShop_name( et_business.getText().toString().trim() );
            updateShopInput.setIsMoving( Integer.valueOf( selectedvalue ) );
            updateShopInput.setTinyshopName( checkEmptyNull( et_business.getText().toString() ) ? null : et_business.getText().toString().trim() );
            updateShopInput.setShopPhone( checkEmptyNull( et_shopphone.getText().toString() ) ? null : et_shopphone.getText().toString().trim() );
            updateShopInput.setShopAddress( tv_city.getText().toString().trim() );
            updateShopInput.setLat( String.valueOf( checkEmptyNull( String.valueOf( latitude ) ) ? null : latitude ) );
            updateShopInput.setLng( String.valueOf( checkEmptyNull( String.valueOf( longitude ) ) ? null : longitude ) );
            updateShopInput.setStructured_type( "1" );
            if ( selectedType == "mobility" )
            { updateShopInput.setIsMoving( 1 ); }
            else if ( selectedType == "shop" )
            { updateShopInput.setIsMoving( 0 ); }

            callBottomSgeetDialog(updateShopInput);
         }
        else
        { Toast.makeText( getContext(),"Please fill all fields", Toast.LENGTH_SHORT ).show(); } }

    private void callBottomSgeetDialog(BusinessPro updateShopInput) {
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.layout_checkavailblity,null);
        mBottomSheetDialog.setContentView(sheetView);
        RadioGroup rg_parcel = mBottomSheetDialog.findViewById( R.id.rg_parcel );
        RadioGroup rg_table = mBottomSheetDialog.findViewById( R.id.rg_table );
        RadioGroup rg_service = mBottomSheetDialog.findViewById( R.id.rg_service );
        RadioGroup rg_paylater = mBottomSheetDialog.findViewById( R.id.rg_paylater );
        AppCompatButton btn_Register = mBottomSheetDialog.findViewById( R.id.btn_registershop );
        btn_Register.setVisibility( View.VISIBLE );

        rg_parcel.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId==R.id.rb_parcelon )
                { updateShopInput.setParcelAllowed(1); }
                else if ( checkedId==R.id.rb_parceloff )
                {  updateShopInput.setParcelAllowed(0); } } } );

        rg_table.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId==R.id.rb_tableon )
                { updateShopInput.setTableAllotment(1); }
                else if ( checkedId==R.id.rb_tableoff )
                {  updateShopInput.setTableAllotment(0); } } } );

        rg_service.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId==R.id.rb_serviceoon )
                { updateShopInput.setDelivery_service(1); }
                else if ( checkedId==R.id.rb_serviceoff )
                {  updateShopInput.setDelivery_service(0); } } } );

        rg_paylater.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId==R.id.rb_paylateron )
                { updateShopInput.setPaylater(1); }
                else if ( checkedId==R.id.rb_paylateroff )
                {  updateShopInput.setPaylater(0); } } } );

        btn_Register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessProPresenter.businesspro(updateShopInput, SharedPreference.getInstance().getAuthToken());
            }
        } );

        mBottomSheetDialog.show();
    }

    private boolean validateAllFields()
    {   if (checkEmptyText( et_name, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_lastname, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_phone, getString(R.string.field_cant_be_empty)))
            return false;
        else if (!isValidEmail( et_email, getString(R.string.field_cant_be_empty)))
            return false;
        else if (checkEmptyText( et_business, getString(R.string.field_cant_be_empty)))
            return false;
        else
            return !checkEmptyText( et_shopphone, getString(R.string.field_cant_be_empty));   }


    @OnClick(R.id.clear_button)
    public void clickclear()
    { signature_pad.clear(); }

    @OnClick(R.id.save_button)
    public void clicksave()
    {   Bitmap signaturebitmap=signature_pad.getSignatureBitmap();
        if (addJpgSignatureToGallery(signaturebitmap))
        {   Toast.makeText(getContext(),"Signature saved into the Gallery", Toast.LENGTH_SHORT).show();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            signaturebitmap.compress(Bitmap.CompressFormat.JPEG,60, byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            uploadimage( bytes,"signature" );
        }
        else
        { Toast.makeText(getContext(),"Unable to store the signature", Toast.LENGTH_SHORT).show(); }
        if (addSvgSignatureToGallery(signature_pad.getSignatureSvg()))
        { Toast.makeText(getContext(),"SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show(); }
        else
        { Toast.makeText(getContext(),"Unable to store the SVG signature", Toast.LENGTH_SHORT).show(); } }


    public boolean addJpgSignatureToGallery(Bitmap signature)
    {   boolean result = false;
        try
        {   File photo = new File(getAlbumStorageDir("SignaturePad"),String.format("Signature_%d.jpg", System.currentTimeMillis()));
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
        getContext().sendBroadcast( mediaScanIntent ); }

    public boolean addSvgSignatureToGallery(String signatureSvg)
    {   boolean result = false;
        try
        {   File svgFile = new File(getAlbumStorageDir("SignaturePad"),String.format("Signature_%d.svg", System.currentTimeMillis()));
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

    public void verifyStoragePermissions(BusinessProFragment businessProFragment)
    {   // Check if we have write permission
        { if ( ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {   // We don't have permission so prompt the user
            requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },REQUEST_EXTERNAL_STORAGE);
        }}}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {   switch(requestCode) {
        case REQUEST_EXTERNAL_STORAGE:
        { // If request is cancelled, the result arrays are empty.
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            { Toast.makeText(getContext(),"Cannot write images to external storage", Toast.LENGTH_SHORT).show(); } } } }

    public File getAlbumStorageDir(String albumName)
    {   // Get the directory for the user's public pictures directory.
        File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs())
        { Log.e("SignaturePad","Directory not created"); }
        return file; }

    public void saveBitmapToJPG(Bitmap bitmap,File photo) throws IOException
    {   Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor( Color.WHITE);
        canvas.drawBitmap(bitmap,0,0,null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG,80, stream);
        stream.close(); }

    @Override
    public void businesspro(BusinessProResponse businessProResponse)
    {   hideProgress();
        mBottomSheetDialog.dismiss();
        OtpForgotPasswordFragment otpForgotPasswordFragment=new OtpForgotPasswordFragment();
        Bundle bundle=new Bundle(  );
        bundle.putString( "BUSINESSPRO","3");
        bundle.putSerializable( "BUSINESSRESPONSE",businessProResponse );
        otpForgotPasswordFragment.setArguments( bundle );
        ((DashBoardActivity)getActivity()).replaceFragment( otpForgotPasswordFragment,OtpForgotPasswordFragment.class.getName() ); }

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
        { return FileProvider.getUriForFile(Objects.requireNonNull(getContext()),BuildConfig.APPLICATION_ID + ".myfileprovider", file); }
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
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
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

        else if ( imagereq=="signature" )
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/jpeg"), imageBytes);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/signature");
            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody requesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",requesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg",requestFile);
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
     * do something with the returned place data (in this example it's image and documents ).
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {   CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            { try
                  { InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(result).getUri());
                    byte[] bytes = byteLevelCompression(getBytes(Objects.requireNonNull(inputStream)));
                    uploadimage(bytes,"profile" );
                    setSelectedImageToView(result.getUri()); }
                catch (IOException e)
                { e.printStackTrace(); } }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {   assert result != null;
                Exception error = result.getError();
                error.printStackTrace(); } }

        else if ( requestCode==REFERENCE_DOC ) {
            if ( resultCode==RESULT_OK )
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

        else if ( requestCode==IDENTITY_PROOF ) { if ( resultCode==RESULT_OK )
          { Uri datas = data.getData();
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

        else if ( requestCode==UPLOAD_MENU ) { if ( resultCode==RESULT_OK )
        {   Uri datas = data.getData();
            File file = new File(datas.getPath());
            InputStream iStream = null;
            try
            {   iStream = getContext().getContentResolver().openInputStream(datas);
                bitmap = BitmapFactory.decodeStream( iStream );
                byte[] inputData =getBytes( iStream );

                uploadRefDocument( inputData,"uploadmenu" );
                tv_uploadmenu.setText( file.getName() ); }
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

    private void setShopPhoto(Uri uri) {
        Picasso.get()
                .load(uri)
                .centerCrop()
                .fit()
                .into(iv_shopphoto); }

    private void uploadRefDocument(byte[] byteArray, String documentupload)
    {   imagereq=documentupload;

        if ( imagereq=="refdocument" )
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/png"), byteArray);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/referencedocument");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();
            businessProPresenter.uploadvalidate( payload,type, body ); }

        else if ( imagereq=="idproof" )
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/png"), byteArray);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/identityproof");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody reuesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",reuesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();
            businessProPresenter.uploadvalidate( payload,type, body ); }

        else if ( imagereq=="uploadmenu" )
        {   RequestBody requestFile = RequestBody.create( MediaType.parse("image/png"), byteArray);
            RequestBody uploadpath = RequestBody.create( MultipartBody.FORM,"tinyshop/menu");

            Map<String, RequestBody> payload = new HashMap<>();
            payload.put("uploadpath", uploadpath);

            RequestBody requesttype = RequestBody.create( MultipartBody.FORM, "private");

            Map<String, RequestBody> type = new HashMap<>();
            payload.put("type",requesttype);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("image","image.jpg", requestFile);

            showProgress();
            businessProPresenter.uploadvalidate( payload,type, body ); }
    }

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
        {
            Toast.makeText( getContext(),"Image Uploaded", Toast.LENGTH_SHORT ).show();
            updateShopInput.setProfileImg( imageResponse.getImageURL() ); }
        else if ( imagereq=="signature" )
        {
            Toast.makeText( getContext(),"Signature captured", Toast.LENGTH_SHORT ).show();
            updateShopInput.setSignature( imageResponse.getImageURL() ); }
        else if ( imagereq=="refdocument" )
        {
            Toast.makeText( getContext(),"Reference Document Uploaded", Toast.LENGTH_SHORT ).show();
            updateShopInput.setRef1doc_copy( imageResponse.getImageURL() ); }
        else if ( imagereq=="idproof" )
        {
            Toast.makeText( getContext(),"Id proof Uploaded",Toast.LENGTH_SHORT ).show();
            updateShopInput.setIdproof_copy( imageResponse.getImageURL() ); }
        else if ( imagereq=="shopphoto" )
        {
            Toast.makeText( getContext(),"Shop photo uploaded",Toast.LENGTH_SHORT ).show();
            updateShopInput.setLivePhoto( imageResponse.getImageURL() ); }
        else if (imagereq=="uploadmenu")
        {
            Toast.makeText( getContext(),"Menu List uploaded",Toast.LENGTH_SHORT ).show();
            updateShopInput.setShopimage1( imageResponse.getImageURL() ); }
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions)  {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    //SELECT TIME FROM SELECTOR
    @OnClick(R.id.tv_intime)
    void selectopentime()
    {   timeselected="opentime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
            (this,calendar.get( Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    //SELECT TIME FROM SELECTOR
    @OnClick(R.id.tv_outtime)
    void selectclosetime()
    {   timeselected="closetime";
        GridTimePickerDialog griddialog = GridTimePickerDialog.newInstance
            (this,calendar.get( Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(getContext()));
        griddialog.show(getActivity().getSupportFragmentManager(),TAG); }

    //Interface for timesetlistener
    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute)
    {   if ( timeselected == "opentime" )
        {  Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        tv_opentime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
        selectedTime = new SimpleDateFormat("HH:mm:ss aa").format(cal.getTime());
        updateShopInput.setOpenTime( selectedTime );
        updateShopInput.setTimings( selectedTime ); }
        else if ( timeselected == "closetime" )
        {   Calendar cal = new java.util.GregorianCalendar();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            tv_closetime.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
            selectedTime = new SimpleDateFormat("HH:mm:ss aa").format(cal.getTime());
            updateShopInput.setCloseTime( selectedTime ); } }

    @OnClick(R.id.tv_referencedoc)
    void uploaddoc()
    {   if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    {   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,REFERENCE_DOC); }
    else
    {   if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {   if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    { Toast.makeText(getContext(),"Permission Needed.", Toast.LENGTH_LONG).show(); } }
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
    { Toast.makeText(getContext(),"Permission Needed.", Toast.LENGTH_LONG).show(); } }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        { requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, IDENTITY_PROOF); } } }

    @OnClick(R.id.tv_uploadmenu)
    public void menuadd()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    {   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,UPLOAD_MENU); }
    else
    {   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {   if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    {   Toast.makeText(getContext(),"Permission Needed.", Toast.LENGTH_LONG).show(); } }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        { requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, UPLOAD_MENU); } } }

    @OnClick(R.id.iv_shopphoto)
    public void selectshopphoto()
    {   if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
        { EasyImage.openChooserWithGallery(this,"Select Image",1 ); }
        else
        { if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
        { Toast.makeText(getContext(),"Permission Needed", Toast.LENGTH_LONG).show(); }
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_SHOP_PHOTO); } }
}
