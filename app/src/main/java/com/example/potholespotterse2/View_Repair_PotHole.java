package com.example.potholespotterse2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class View_Repair_PotHole extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view__repair__pot_hole, container, false);
        String[] data = getArguments().getStringArray("Info");
        TextView address1 = view.findViewById(R.id.textView_Street);
        TextView address2 = view.findViewById(R.id.textView_Address);
        TextView description = view.findViewById(R.id.textView_Description);
        ImageView severity = view.findViewById(R.id.imageView_severity);
        address1.setText(data[0]);
        address2.setText(data[1]);
        description.setText(data[2]);
        switch (data[3]){
            case "1":
                severity.setImageResource(R.drawable.green_smile);
                break;
            case "2":
                severity.setImageResource(R.drawable.yellow_normal);
                break;
            case "3":
                severity.setImageResource(R.drawable.red_sad);
                break;
        }
        Button repairButton = view.findViewById(R.id.button_Repair_Pothole);
        Button viewButton = view.findViewById(R.id.button_View_Pothole);

        repairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data[4].equals("0")){
                    Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                    intent.putExtra("key","Repair");
                    getActivity().startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                    intent.putExtra("key","View");
                    getActivity().startActivity(intent);
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SecondaryActivity.class);
                intent.putExtra("key","View");
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}