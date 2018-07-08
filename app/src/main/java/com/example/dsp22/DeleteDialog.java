package com.example.dsp22.progress;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete " +
                MainActivity.spinner.getSelectedItem().toString() + " and its subject details ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.TEST_NAME_TABLE = MainActivity.spinner.getSelectedItem().toString();
                        SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getContext());
                        handler.deleteTestName(MainActivity.TEST_NAME_TABLE);
                        ((MainActivity)getActivity()).loadSpinnerData();
                        ((MainActivity)getActivity()).populateListView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
