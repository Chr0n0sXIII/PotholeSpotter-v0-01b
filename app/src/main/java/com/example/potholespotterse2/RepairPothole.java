package com.example.potholespotterse2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class RepairPothole extends Fragment {

    private ImageView potholeImage;
    private List<Bitmap> bitmap = new ArrayList<>();
    private MapsActivity m = new MapsActivity();
    private PotHole potHole;
    private int num1, num2;
    FirebaseFirestore db;
    private DocumentReference documentReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_pothole, container, false);
        potholeImage = view.findViewById(R.id.imageView_potHole_Repair);
        potHole = m.getPotHole();
        db = FirebaseFirestore.getInstance();
        getDocument();
        loadImageFromCamera();

        RadioGroup r_Condition = view.findViewById(R.id.radioGroup_condition);
        RadioGroup r_Repair = view.findViewById(R.id.radioGroup_repair_type);

        Button submitButton = view.findViewById(R.id.button_send_repair);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (r_Condition.getCheckedRadioButtonId()){
                    case R.id.radioButton_par_repaired:
                        num1 = 1;
                        break;
                    case R.id.radioButton_full_repaired:
                        num1 = 2;
                        break;
                }
                switch (r_Repair.getCheckedRadioButtonId()){
                    case R.id.radioButton_homemade:
                        num2 = 1;
                        break;
                    case R.id.radioButton_pro:
                        num2 = 2;
                        break;
                }
                documentReference.update("repair_Status",1);
                documentReference.update("repair_Condition",num1);
                documentReference.update("repair_Type",num2);
                documentReference.update("date_Reported", FieldValue.serverTimestamp()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getActivity(),MapsActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
            }
        });
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

    public void getDocument(){
        db.collection("Pothole").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()){
                    if (document.toObject(PotHole.class).getGeoPoint().getLatitude() == potHole.getGeoPoint().getLatitude() && document.toObject(PotHole.class).getGeoPoint().getLongitude() == potHole.getGeoPoint().getLongitude()){
                        documentReference = document.getReference();
                        break;
                    }
                }
            }
        });
    }
}