package com.example.gazetkapl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GnssMeasurementsEvent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;



import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //public static final String KEY_VERTICAL_ACCURACY ="";
    private GoogleMap mMap;
    private double N,E;
    private static final String TAG = "MapsActivity";
    private static final int MY_REQUEST_INT = 177;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION},MY_REQUEST_INT);
            }
            return;
        }
        else {
            mMap.setMyLocationEnabled(true);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            final Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    currentLocation = (Location) task.getResult();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude()), 15F));
                    N = currentLocation.getLatitude();
                    E = currentLocation.getLongitude();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude())).visible(true).title("Jesteś tutaj!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    searchShops(N, E);
                }
            });
        }
    }
    void searchShops(double N2, double E2)
    {
       // String napis = "Lidl szczecin";
        String napis = "Biedronka szczecin";
        Geocoder geocenter = new Geocoder(this);
        List<Address> lista = null;

        try
        {
            lista = geocenter.getFromLocationName(napis,2, (N2- 0.030), (E2-0.030 ),  (N2+ 0.030),(E2+0.030));
        }catch(IOException e){
            e.printStackTrace();
        }

        Address sklep;
        LatLng s;

        for(int i=0;i<lista.size();i++)
        {
            sklep = lista.get(i);
            s = new LatLng(sklep.getLatitude(),sklep.getLongitude());
            mMap.addMarker(new MarkerOptions().position(s).title("Biedronka!"));
        }
    }

}