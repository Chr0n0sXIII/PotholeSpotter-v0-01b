package com.example.potholespotterse2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Camera_Report extends Fragment {

    private ImageView potholeImage;
    private Button submit_Report_Button;
    private EditText description;
    private RadioGroup severity;
    private int severity_Logo;
    private int s;
    private List<Bitmap> bitmap = new ArrayList<>();

    FirebaseFirestore db;
    private FusedLocationProviderClient fusedLocationClient;
    private PotHole newPothole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera__report, container, false);
        potholeImage = view.findViewById(R.id.imageView_potHole_Report);
        submit_Report_Button = view.findViewById(R.id.button_send_report);
        severity = (RadioGroup) view.findViewById(R.id.radioGroup_severity);
        description = view.findViewById(R.id.editText_Report_Description);
        db = FirebaseFirestore.getInstance();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        submit_Report_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (severity.getCheckedRadioButtonId() == -1 || description.getText().toString() == null){
                    Toast.makeText(getContext(),"Please Fill Out Information",Toast.LENGTH_LONG);
                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                Location location = task.getResult();
                                double longitude = location.getLongitude();
                                double latitude = location.getLatitude();
                                switch (severity.getCheckedRadioButtonId()){
                                    case R.id.radioButton_green:
                                        s = 1;
                                        break;
                                    case R.id.radioButton_yellow:
                                        s = 2;
                                        break;
                                    case R.id.radioButton_red:
                                        s = 3;
                                        break;
                                }
                                newPothole = new PotHole(new GeoPoint(latitude,longitude),description.getText().toString(),s,0,-1,-1,null,null);
                                db.collection("Pothole")
                                        .add(newPothole);
                                Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
        loadImageFromCamera();
        return view;
    }

    private void loadImageFromCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100){
            bitmap.add((Bitmap) data.getExtras().get("data"));
            potholeImage.setImageBitmap(bitmap.get(0));
        }
    }
}