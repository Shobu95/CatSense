package com.shobu.catsense.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.shobu.catsense.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddActivityDialogFullScreenFragment extends DialogFragment {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.CAMERA};


    ImageButton closeButton;
    Button saveButton;
    ImageView reportingSheetImage;

    int TAKE_BA_REPORTING_SHEET_IMAGE = 1001;



    public AddActivityDialogFullScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_activity_dialog_full_screen, container, false);

        if(!hasPermissions(getActivity(), PERMISSIONS))
        {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }

        closeButton = view.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        reportingSheetImage = view.findViewById(R.id.img_reporting_sheet_image);
        reportingSheetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_BA_REPORTING_SHEET_IMAGE);
            }
        });



        saveButton = view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Activity Saved", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_BA_REPORTING_SHEET_IMAGE)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                reportingSheetImage.setImageBitmap(bitmap);
            }
        }

    }

    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
