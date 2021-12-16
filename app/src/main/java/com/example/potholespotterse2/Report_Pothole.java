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


public class Report_Pothole extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report__pothole, container, false);
        String[] data = getArguments().getStringArray("Info");
        TextView address1 = view.findViewById(R.id.textView_Street);
        TextView address2 = view.findViewById(R.id.textView_Address);
        address1.setText(data[0]);
        address2.setText(data[1]);

        Button reportButton = view.findViewById(R.id.button_Report_Pothole);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                intent.putExtra("key","Report");
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}