package com.shobu.catsense.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shobu.catsense.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddActivityDialogFullScreenFragment extends DialogFragment {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.CAMERA};

    private String[] YESNO = new String[]{"Yes", "No"};
    private String[] RATING = new String[]{"Poor", "Average", "Good", "Excellent"};


    ImageButton closeButton;
    Button saveButton;
    ImageView reportingSheetImage, sheetImage, storeInImage, storeOutImage;
    TextView briefingSheetText, baGroomingText, uniformConditionText, baPerformanceText;

    int TAKE_BA_REPORTING_SHEET_IMAGE = 1001;
    int TAKE_SHEET_IMAGE = 1002;
    int TAKE_STORE_IN_IMAGE = 1003;
    int TAKE_STORE_OUT_IMAGE = 1004;

    String briefingSheetChoice, baGroomingChoice, uniformConditionChoice, baPerformanceChoice;

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


        briefingSheetText = view.findViewById(R.id.txt_briefing_sheet);
        briefingSheetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                briefingSheetChoice = YESNO[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Briefing Sheet");
                builder.setSingleChoiceItems(YESNO, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        briefingSheetChoice = YESNO[i];
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                       briefingSheetText.setText(briefingSheetChoice);
                       dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        baGroomingText = view.findViewById(R.id.txt_ba_grooming);
        baGroomingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                baGroomingChoice = RATING[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("BA Grooming");
                builder.setSingleChoiceItems(RATING, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        baGroomingChoice = RATING[i];
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        baGroomingText.setText(baGroomingChoice);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });



        uniformConditionText = view.findViewById(R.id.txt_uniform_condition);
        uniformConditionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uniformConditionChoice = RATING[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Uniform Condition");
                builder.setSingleChoiceItems(RATING, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uniformConditionChoice = RATING[i];
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        uniformConditionText.setText(uniformConditionChoice);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });


        baPerformanceText = view.findViewById(R.id.txt_ba_performance);
        baPerformanceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                baPerformanceChoice = RATING[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Uniform Condition");
                builder.setSingleChoiceItems(RATING, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        baPerformanceChoice = RATING[i];
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        baPerformanceText.setText(baPerformanceChoice);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });


        reportingSheetImage = view.findViewById(R.id.img_reporting_sheet);
        reportingSheetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_BA_REPORTING_SHEET_IMAGE);
            }
        });


        sheetImage = view.findViewById(R.id.img_sheet);
        sheetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_SHEET_IMAGE);

            }
        });

        storeInImage = view.findViewById(R.id.img_time_in_store);
        storeInImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_STORE_IN_IMAGE);

            }
        });

        storeOutImage = view.findViewById(R.id.img_time_out_of_store);
        storeOutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_STORE_OUT_IMAGE);

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

        if (resultCode == RESULT_OK)
        {
            if (requestCode == TAKE_BA_REPORTING_SHEET_IMAGE)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                reportingSheetImage.setImageBitmap(bitmap);
            }
            else if (requestCode == TAKE_SHEET_IMAGE)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                sheetImage.setImageBitmap(bitmap);
            }
            else if (requestCode == TAKE_STORE_IN_IMAGE)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeInImage.setImageBitmap(bitmap);
            }
            else if (requestCode == TAKE_STORE_OUT_IMAGE)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeOutImage.setImageBitmap(bitmap);
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
