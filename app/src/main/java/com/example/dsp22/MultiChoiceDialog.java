package com.example.dsp22.progress;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MultiChoiceDialog extends DialogFragment {

    List<String> listItems = new ArrayList<>();
    String[] stringListItems;
    boolean[] checkedItems;
    List<Integer> userItems = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getContext());
        listItems = handler.getSpinnerSubjectList();
        stringListItems = listItems.toArray(new String[listItems.size()]);
        checkedItems = new boolean[stringListItems.length];

        builder.setTitle("Select subjects to import:");
        builder.setMultiChoiceItems(stringListItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    if (!userItems.contains(position)) {
                        userItems.add(position);
                    } else {
                        userItems.remove(position);
                    }
                }
            }
        });

        builder.setCancelable(false);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < userItems.size(); i++){
                    handler.insertSubjectDetails(MainActivity.spinner.getSelectedItem().toString(),
                            stringListItems[userItems.get(i)],0,0);
                    ((MainActivity)getActivity()).populateListView();
                    Toast.makeText(
                            getContext(),"Successful!",Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0; i < checkedItems.length; i++){
                    checkedItems[i] =false;
                }
                userItems.clear();
            }
        });
        return builder.create();
    }
}
