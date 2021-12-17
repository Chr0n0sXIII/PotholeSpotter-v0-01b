package com.example.potholespotterse2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.potholespotterse2.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PrimitiveIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private String[] address = new String[4];
    private Fragment fragment;
    private static PotHole potHole;
    private ArrayList<PotHole> ph = new ArrayList<>();
    private int image_icon;
    FirebaseFirestore db, dbQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        db = FirebaseFirestore.getInstance();
        FloatingActionButton getLocationButton = findViewById(R.id.floatingActionButton_getLocation);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
        fragment = new Report_Pothole();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    updateCameraPosition(new LatLng(latitude, longitude), 15f);
                    getAddress(MapsActivity.this,latitude,longitude);
                    load_Pothole_Data_From_Database();
                    loadFragment(fragment);
                }
            }
        });

    }

    private void updateCameraPosition(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public String[] getAddress(Context context, double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            Address addressOBJ = addresses.get(0);
            String[] check = addressOBJ.getAddressLine(0).split(",",2);
            address[0] = check[0];
            if (addressOBJ.getSubLocality() != null)
                address[1] = addressOBJ.getSubLocality();
            else
            {
                String[] separate = addressOBJ.getAdminArea().split("/",2);
                address[1] = separate[1];
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return address;
    }

    public void getAllPotholes(){
        db.collection("Pothole").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()){
                    ph.add((document.toObject(PotHole.class)));
                }
                load_Pothole_Data_From_Database();
            }
        });
    }

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        getAllPotholes();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                LatLng latLng = marker.getPosition();
                GeoPoint geoPoint = new GeoPoint(latLng.latitude,latLng.longitude);
                getAddress(getApplicationContext(),latLng.latitude,latLng.longitude);
                address[2] = marker.getTitle();
                for (int i = 0; i < ph.size(); i++)
                {
                    potHole = ph.get(i);
                    if (potHole.getGeoPoint().getLatitude() == geoPoint.getLatitude() && potHole.getGeoPoint().getLongitude() == geoPoint.getLongitude())
                        break;
                }
                address[3] = String.valueOf(potHole.getSeverity());
                loadFragment(new View_Repair_PotHole());
                return false;
            }
        });

    }

    private void load_Pothole_Data_From_Database() {
        for (int i = 0; i < ph.size(); i++){
            potHole = ph.get(i);
            switch (potHole.getSeverity()){
                case 1:
                    image_icon = R.drawable.greeny;
                    break;
                case 2:
                    image_icon = R.drawable.yellowy;
                    break;
                case 3:
                    image_icon = R.drawable.redy;
                    break;
            }
            LatLng latLng = new LatLng(potHole.getGeoPoint().getLatitude(),potHole.getGeoPoint().getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(potHole.getDescription()).icon(bitmapDescriptor(getApplicationContext(),image_icon)));
        }
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    };

    private void loadFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putStringArray("Info", address);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Fragment_View, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    public PotHole getPotHole() {
        return potHole;
    }
}