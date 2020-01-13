package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.AppUtils;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.business.BusinessProFragment;
import com.tag.app.tagnearemployee.homescreen.business.businessupdate.BusinessUpdateFragment;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MapFragment extends BaseFragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    @BindView( R.id.locationMarkertext )
    TextView mLocationMarkerText;
    @BindView( R.id.Locality )
    TextView mLocationText;
    @BindView( R.id.toolbar )
    Toolbar mToolbar;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String TAG = "MAP LOCATION";
    Context mContext=getContext();
    private LatLng mCenterLatLong;
    private FusedLocationProviderClient fusedLocationClient;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;

    /**
     * The formatted location address.
     */
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    private List<Address> useraddress;
    private double longitude;
    private double latitude;
    private String pincode;
    private String address;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private PendingDatum pendingDatum;
    private BusinessDatum businessDatum;

    @Override
    protected void init(Bundle savedInstanceState)
    {    mToolbar.setTitle( "Select Location" );
         pendingDatum= (PendingDatum) getArguments().getSerializable( "VENDORDETAILS" );
         businessDatum= (BusinessDatum) getArguments().getSerializable( "BUSINESSUPDATE" );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        mResultReceiver=new AddressResultReceiver(new Handler());

        if (checkPlayServices())
        {   // If this check succeeds,proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!AppUtils.isLocationEnabled(getContext()))
            {   // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings",new DialogInterface.OnClickListener()
                {  @Override
                public void onClick(DialogInterface paramDialogInterface,int paramInt)
                { Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent); }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {   @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                { // TODO Auto-generated method stub
                } });
                dialog.show(); }
            buildGoogleApiClient(); }
        else { Toast.makeText(mContext,"Location not supported in this device",Toast.LENGTH_SHORT).show(); }
    }

    @OnClick(R.id.Locality)
    public void locationselect()
    { openAutocompleteActivity(); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_map; }

    @Override
    protected Object getModule()
    { return new MapModule(); }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {   mMap=googleMap;
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {   @Override
            public void onCameraChange(CameraPosition cameraPosition)
            {   Log.d("Camera postion change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;

                mMap.clear();

                try {
                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);

                    startIntentService(mLocation);
                    mLocationMarkerText.setText("Lat : " + mCenterLatLong.latitude + "," + "Long : " + mCenterLatLong.longitude);
                    Geocoder geocoder;
                    geocoder = new Geocoder(getContext(),Locale.getDefault());
                    useraddress = geocoder.getFromLocation(mCenterLatLong.latitude,mCenterLatLong.longitude,1 );
                    if (useraddress != null && useraddress.get(0) != null)
                        longitude=useraddress.get( 0 ).getLongitude();
                        latitude=useraddress.get( 0 ).getLatitude();
                        mLocationText.setText( useraddress.get( 0 ).getAddressLine( 0 ) ); }
                catch (Exception e)
                { e.printStackTrace(); }
            } } );
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {   // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(Bundle bundle)
    {  if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    {   // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return; }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null)
        { changeMap(mLastLocation);
          Log.d(TAG, "ON connected"); }
        else
            try
            { LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient,this); }
            catch (Exception e)
            { e.printStackTrace(); }
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LatLng latLng=new LatLng( mLastLocation.getLatitude(),mLastLocation.getLongitude() );
            LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this); }
        catch (Exception e)
        { e.printStackTrace(); } }

    @Override
    public void onConnectionSuspended(int i)
    {  Log.i(TAG, "Connection suspended");
       mGoogleApiClient.connect(); }

    @Override
    public void onLocationChanged(Location location)
    {   try {
        if (location != null)
            changeMap(location);
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,this); }
    catch (Exception e)
    { e.printStackTrace(); } }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build(); }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) getContext(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show(); }
            else {
                //finish();
            }
            return false; }
        return true; }

    private void changeMap(Location location)
    {   Log.d(TAG, "Reaching map" + mMap);

        if ( ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {   // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return; }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;
            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLong).zoom(19f).tilt(70).build();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mLocationMarkerText.setText("Lat : "+location.getLatitude()+","+"Long : "+location.getLongitude());
            startIntentService(location); }
        else
        { Toast.makeText(getContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show(); } }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver
    {  public AddressResultReceiver(Handler handler)
    { super(handler); }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {   // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString( AppUtils.LocationConstants.RESULT_DATA_KEY);

            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);

            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {
                //  showToast(getString(R.string.address_found));
            } } }

    /**
     * Updates the address in the UI.
     */
    protected void displayAddressOutput()
    {  try
    {  if (mStateOutput != null)
        // mLocationText.setText(mAreaOutput+ "");
        mLocationText.setText(mStateOutput); }
       catch (Exception e)
       { e.printStackTrace(); } }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService(Location mLocation)
    {   // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(getContext(), FetchaddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        getContext().startService(intent); }

    private void openAutocompleteActivity()
    {   try {
        // The autocomplete activity requires Google Play Services to be available. The intent
        // builder checks this and throws an exception if it is not the case.
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized())
        { Places.initialize(getContext(),getString(R.string.places_api_key)); }

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS,Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder( AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult( intent, REQUEST_CODE_AUTOCOMPLETE );
    }
    catch (Exception e)
    {  }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {   super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE)
        {      if (resultCode == RESULT_OK)
        { // Get the user's selected place from the Intent.
            Place place= Autocomplete.getPlaceFromIntent( data );

            Geocoder geocoder;
            geocoder = new Geocoder(getContext(),Locale.getDefault() );
            try
            {   useraddress = geocoder.getFromLocationName( place.getAddress(),1 );
                if (useraddress != null && useraddress.get(0) != null)
                {   longitude= useraddress.get(0).getLongitude() ;
                    latitude= useraddress.get(0).getLatitude();
                    pincode= useraddress.get(0).getPostalCode();
                    address= useraddress.get(0).getAddressLine(0);
                    mLocationText.setText(address); }

                // TODO call location based filter
                LatLng latLong = new LatLng(useraddress.get( 0 ).getLatitude(), useraddress.get( 0 ).getLongitude());

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(19f).tilt(70).build();

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {   // TODO: Consider calling
                    //  ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return; }
                mMap.setMyLocationEnabled(true);
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {   // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i(TAG, status.getStatusMessage()); }
        else if (resultCode == RESULT_CANCELED)
        { // The user cancelled the operation.
        } } }

        @OnClick(R.id.btn_mapdetails)
        public void update_address()
        {   if (getArguments()!=null && getArguments().containsKey( "BUSINESSPRO" ))
            { String mapfetch=mLocationText.getText().toString().trim();
              BusinessProFragment businessProFragment=new BusinessProFragment();
              Bundle bundle=new Bundle(  );
              bundle.putSerializable( "INPUTFIELDS",getArguments().getSerializable("INPUTFIELDS" ) );
              bundle.putString( "MAPDETAIL",mapfetch );
              bundle.putDouble( "latitude",latitude );
              bundle.putDouble( "longitude",longitude );
              businessProFragment.setArguments( bundle );
              ((DashBoardActivity)getActivity()).replaceFragment( businessProFragment,BusinessProFragment.class.getName() );
            }
            else if (getArguments()!=null && getArguments().containsKey( "BUSINESSUPDATEDATA" ))
            { String mapfetchs=mLocationText.getText().toString().trim();
                BusinessUpdateFragment businessProFragment=new BusinessUpdateFragment();
                Bundle bundle=new Bundle(  );
                bundle.putSerializable( "BUSINESSREGDATA",businessDatum );
                bundle.putString( "MAPDETAILUPDATE",mapfetchs );
                bundle.putDouble( "latitude",latitude );
                bundle.putDouble( "longitude",longitude );
                businessProFragment.setArguments( bundle );
                ((DashBoardActivity)getActivity()).replaceFragment( businessProFragment,BusinessUpdateFragment.class.getName() );
            }
            else if(getArguments()!=null && getArguments().containsKey( "PENDINGDETAILS" ) )
            { String mapdetails=mLocationText.getText().toString().trim();
              PendingDetailFragment pendingDetailFragment=new PendingDetailFragment();
              Bundle bundle=new Bundle(  );
              bundle.putSerializable( "VENDORDETAILS",pendingDatum );
              bundle.putDouble( "latitude",latitude );
              bundle.putDouble( "longitude",longitude );
              bundle.putString( "MAPDETAILS",mapdetails );
              pendingDetailFragment.setArguments( bundle );
              ((DashBoardActivity)getActivity()).replaceFragment(pendingDetailFragment,PendingDetailFragment.class.getName());
            }
        }
}