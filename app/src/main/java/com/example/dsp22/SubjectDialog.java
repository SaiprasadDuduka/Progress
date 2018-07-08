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

public class SubjectDialog extends DialogFragment {

    EditText etSubjectName,etMarks,etTotalMarks;

    String subjectName;
    float marks,totalMarks;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.subject_dialog,null);

        etSubjectName = (EditText)view.findViewById(R.id.etSubjectName);
        etMarks = (EditText)view.findViewById(R.id.etMarks);
        etTotalMarks = (EditText)view.findViewById(R.id.etTotalMarks);

        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            subjectName = etSubjectName.getText().toString().toUpperCase();
                            marks = Float.parseFloat(etMarks.getText().toString());
                            totalMarks = Float.parseFloat(etTotalMarks.getText().toString());

                            MainActivity.TEST_NAME_TABLE = MainActivity.spinner.getSelectedItem().toString();
                            SubjectNameDatabaseHandler db = new SubjectNameDatabaseHandler(getContext());
                            db.insertSubjectDetails(MainActivity.TEST_NAME_TABLE,subjectName,marks,totalMarks);

                            ((MainActivity)getActivity()).populateListView();
                            Toast.makeText(
                                    getContext(),"Successful!",Toast.LENGTH_SHORT
                            ).show();

                        }catch (Exception e){
                            Toast.makeText(
                                    getContext(),"Enter Details properly!",Toast.LENGTH_SHORT
                            ).show();
                        }

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
