package com.example.dsp22.progress;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Subject> {

    private Context context;

    private List<Subject> subjectDetailsList = new ArrayList<>();

    public CustomListAdapter(@NonNull Context context, List<Subject> subjectDetailsList) {
        super(context, 0, subjectDetailsList);
        this.context = context;
        this.subjectDetailsList = subjectDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_view_layout,null);
        }
        Subject subject = subjectDetailsList.get(position);

        TextView tvSubjectElement = (TextView)listItem.findViewById(R.id.tvSubjectElement);
        tvSubjectElement.setText(subject.getSubjectName());

        TextView tvMarksElement = (TextView)listItem.findViewById(R.id.tvMarksElement);
        tvMarksElement.setText(String.valueOf(subject.getMarks()));

        TextView tvTotalMarksElement = (TextView)listItem.findViewById(R.id.tvTotalMarksElement);
        tvTotalMarksElement.setText(String.valueOf(subject.getTotalMarks()));

        return listItem;
    }
}
