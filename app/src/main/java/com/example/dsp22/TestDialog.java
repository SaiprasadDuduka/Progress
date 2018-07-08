package com.example.dsp22.progress;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestDialog extends DialogFragment {

    EditText etTestName;
    public String testName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.test_dialog,null);
        etTestName = (EditText)view.findViewById(R.id.etTestName);

        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        testName = String.valueOf(etTestName.getText()).toUpperCase();
                        if (testName.trim().length() > 0) {
                            // database handler
                            SubjectNameDatabaseHandler db = new SubjectNameDatabaseHandler(getContext());
                            // inserting new label into database
                            db.insertTestName(testName);

                        }else {
                            Toast.makeText(getContext(), "Please enter Test name",
                                    Toast.LENGTH_SHORT).show();
                        }

                        ((MainActivity)getActivity()).loadSpinnerData();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }


}
