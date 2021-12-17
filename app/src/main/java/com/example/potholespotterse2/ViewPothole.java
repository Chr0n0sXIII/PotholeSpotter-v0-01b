package com.example.potholespotterse2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ViewPothole extends Fragment {

    MapsActivity m = new MapsActivity();
    String[] data;
    PotHole potHole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_pothole, container, false);

        TextView street = view.findViewById(R.id.textView_Street2);
        TextView address = view.findViewById(R.id.textView_Address2);
        TextView description = view.findViewById(R.id.textView_Description2);
        TextView reportDate = view.findViewById(R.id.textView_report_date);
        TextView repairDate = view.findViewById(R.id.textView_repair_date);
        Button repairButton = view.findViewById(R.id.button_Repair_Pothole2);
        potHole = m.getPotHole();
        data = m.getAddress(getContext(),potHole.getGeoPoint().getLatitude(),potHole.getGeoPoint().getLongitude());
        street.setText(data[0]);
        address.setText(data[1]);
        description.setText(potHole.getDescription());
        reportDate.setText(potHole.getDate_Reported().toString());
        if (potHole.getRepair_Status() == 1 ){
            repairDate.setText(potHole.getDate_Repaired().toString());
            repairButton.setText("Already Repaired, Return Home");
        }
        repairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (potHole.getRepair_Status() == 0){
                    Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                    intent.putExtra("key","Repair");
                    getActivity().startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(),MapsActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        return view;
    }
}