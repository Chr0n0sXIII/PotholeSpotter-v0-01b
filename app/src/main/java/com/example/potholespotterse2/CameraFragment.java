package com.example.potholespotterse2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

public class CameraFragment extends Fragment {

        private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
        private static final int REQUEST_IMAGE_CAPTURE = 12345;
        Button button;
        ImageView imageView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.camera_fragment,
                    container, false);

            button = (Button) rootView.findViewById(R.id.camera_button);
            imageView = (ImageView) rootView.findViewById(R.id.camera_imageview);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    camerainstance();

                }
            });

            return rootView;

        }


        public void camerainstance(){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Fragment frag = this;

            /** Pass your fragment reference **/
            frag.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // REQUEST_IMAGE_CAPTURE = 12345
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    // convert byte array to Bitmap

                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                            byteArray.length);

                    imageView.setImageBitmap(bitmap);

                }
            }
        }


}


