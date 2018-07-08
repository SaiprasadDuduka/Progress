package com.example.dsp22.progress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class Graph extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner graphSpinner;
    BarChart barChart;
    List<BarEntry> barEntries = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    String graphSpinnertestName;

    Spinner subjectGraphSpinner;
    LineChart lineChart;
    String spinnerSubjectName;
    List<Entry> lineEntries = new ArrayList<>();
    List<String> testLabels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        //Upper Section
        graphSpinner = (Spinner)findViewById(R.id.graph_spinner);
        graphSpinner.setOnItemSelectedListener(this);
        loadGraphSpinnerData();

        barChart =(BarChart)findViewById(R.id.BarGraph);
        showGraphView();

        //Lower Section

        subjectGraphSpinner = (Spinner)findViewById(R.id.subject_graph_spinner);
        subjectGraphSpinner.setOnItemSelectedListener(this);
        loadSubjectGraphSpinnerData();

        lineChart = (LineChart)findViewById(R.id.LineGraph);
        showSubjectGraphView();

    }

    public void loadGraphSpinnerData(){
        SubjectNameDatabaseHandler db = new SubjectNameDatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> testNameList = db.getTestList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, testNameList);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        graphSpinner.setAdapter(dataAdapter);
    }

    public void showGraphView(){
       if(graphSpinner!=null){
           SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getApplicationContext());

           graphSpinnertestName = graphSpinner.getSelectedItem().toString();

           barEntries = handler.getTestwiseSubjectData(graphSpinnertestName);
           BarDataSet dataSet = new BarDataSet(barEntries, graphSpinnertestName);
           labels = handler.getSubjectLabels(graphSpinnertestName);

           BarData data = new BarData(labels,dataSet);
           barChart.setData(data);

           dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

           barChart.animateY(1000);
           barChart.setDrawValueAboveBar(true);
           barChart.setDescription("");
           barChart.setDragEnabled(true);
           barChart.invalidate();
           barChart.setTouchEnabled(true);
           barChart.setDragEnabled(true);
           barChart.setScaleEnabled(true);
       }
    }

    public void loadSubjectGraphSpinnerData(){
        SubjectNameDatabaseHandler db = new SubjectNameDatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> subjectNameList = db.getSpinnerSubjectList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectNameList);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        subjectGraphSpinner.setAdapter(dataAdapter);
    }

    public void showSubjectGraphView(){
        if(subjectGraphSpinner!=null){
            SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getApplicationContext());

            spinnerSubjectName = subjectGraphSpinner.getSelectedItem().toString();

            lineEntries = handler.getSubjectwiseSubjectData(spinnerSubjectName);
            LineDataSet dataSet = new LineDataSet(lineEntries, spinnerSubjectName);
            testLabels = handler.getTestLabels(spinnerSubjectName);

            LineData data = new LineData(testLabels,dataSet);
            lineChart.setData(data);
            dataSet.setCircleColorHole(R.color.colorPrimary);
            dataSet.setColor(R.color.colorPrimary);
            lineChart.animateY(1000);
            lineChart.animateX(1000);
            lineChart.setDescription("");
            lineChart.setDragEnabled(true);
            lineChart.invalidate();
            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(true);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.graph_spinner:
                showGraphView();
                break;
            case R.id.subject_graph_spinner:
                showSubjectGraphView();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
