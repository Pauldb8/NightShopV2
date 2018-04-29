package nightshop.debuck.info.nightshop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import nightshop.debuck.info.nightshop.AppClass.Building;
import nightshop.debuck.info.nightshop.Tools.BuildingAdapter;
import nightshop.debuck.info.nightshop.Tools.GsonRequest;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements LocationListener, SeekBar.OnSeekBarChangeListener {


    private List<Building> mBuildings = new ArrayList<>();
    private RecyclerView recyclerView;
    private BuildingAdapter mAdapter;
    private RequestQueue mRequestQueue;
    public int mDistance = 10;
    private SeekBar skDistance;
    private TextView tv_change_distance;
    private LocationManager locationManager;
    public double lat;
    public double lng;
    private String city;
    private Location location;
    private String mCity;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2001;
    private AdView mAdView;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private boolean canGetLocation;

    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-1381021891754984~1442609929");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) //remove before publish app
                .build();
        mAdView.loadAd(adRequest);
        Log.i("AdsView",adRequest.toString());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        mAdapter = new BuildingAdapter(mBuildings, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        mRequestQueue = MySingleton.getInstance(this).getRequestQueue();
        mRequestQueue.start();

        //getBuildings(50.7513824, 4.4984512, mDistance);

       // getLocalisation();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "We check your localisation", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getLocalisation();
            }
        });

        getLocalisation();
    }


    /**
     * This method gets all the buildings near the user
     */
    public void getBuildings(double lat, double lng, double distance) {
        String urlRequest = getString(R.string.webservice_url) + "/buildings/nearest/";
        urlRequest+= lat + "/" + lng + "/" + distance;
        GsonRequest<Building[]> getBuildingsRequest = new GsonRequest<>(
                urlRequest,
                Building[].class,
                null,
                new Response.Listener<Building[]>() {
                    @Override
                    public void onResponse(Building[] response) {
                        Log.d("MainActivity", "GOOD");
                        updateBuildings(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity", "Error connecting to webserver..");
                        error.printStackTrace();
                    }
                }
        );

        mRequestQueue.add(getBuildingsRequest);
    }


    private void updateBuildings(Building[] buildings) {
        List<Building> list = new ArrayList(Arrays.asList(buildings));
        List<Building> listDistance = new ArrayList();
        Building distance = new Building();
        distance.setId(999999);
        listDistance.add(distance);
        for(int i = 0; i < list.size(); i++){
            listDistance.add(list.get(i));
        }


        mAdapter = new BuildingAdapter(listDistance);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ini var Lat, Lng and city
     *
     */

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        Log.d("TAG", "permissionsresults");
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            getLocalisation();
        }
    }

    public void getLocalisation() {
        Log.d("MainActivity", "Init.: Geolocalizing..");
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checking self permission");
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        && ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no GPS Provider and no network provider is enabled
            Toast.makeText(this,"Please enable your GPS or data Network",Toast.LENGTH_LONG).show();
        } else {   // Either GPS provider or network provider is enabled
            Log.d("MainActivity", "GPS or network is active..");


                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding

                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            // First get location from Network Provider
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null)
                {
                   Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null)
                    {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        this.canGetLocation = true;
                    }
                }
                Log.d("MainActivity", "Network is enabled..");
            }// End of IF network enabled

            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled)
            {
                Log.d("MainActivity", "GPS is enabled, fetching..");
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null)
                {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null)
                    {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        this.canGetLocation = true;
                    }
                }

                Log.d("MainActivity", lat + " and " + lng);
                getBuildings(lat, lng, mDistance);

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCity = addresses.get(0).getLocality();

                setTitle(mCity);

                if(addresses != null){
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                    Log.i("GEOCODER", mCity + " and " + stateName+" and "+countryName);
                }


            }// End of if GPS Enabled
        }// End of Either GPS provider or network provider is enabled



    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.i("Location onChanged", location.getLatitude() + " and " + location.getLongitude());
        }else{
            Log.i("Location KO","Location KO");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mDistance = i;
        getBuildings(lat, lng, mDistance);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
