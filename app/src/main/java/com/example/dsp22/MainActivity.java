package com.example.dsp22.progress;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FloatingActionButton fab_plus,addTestFab,addSubjectFab,showGraphsFab, fabDelete;
    LinearLayout layoutAddSubject, layoutAddTest, LayoutShowGraphs, layoutDeleteTest;
    Animation FabOpen,FabClose,FabRC,FabRAC;

    public static Spinner spinner;
    public ListView subjectListView;
    public static String TEST_NAME_TABLE;
    EditText etTestName;
    boolean isOpen = false;
    public List<Subject> subjects = new ArrayList<>();
    public CustomListAdapter subjectDataAdapter;
    public List<Subject> subjectElement = new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

        subjectListView = (ListView)findViewById(R.id.lvSubject);
        populateListView();

        subjectDataAdapter = new CustomListAdapter(getApplicationContext(),subjects);
        subjectListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        subjectListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
               if(!subjectElement.contains(subjects.get(position))) {
                   count = count + 1;
                   mode.setTitle(count + " Item(s) Selected");
                   subjectListView.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                   subjectElement.add(subjects.get(position));
               }else{
                   count = count - 1;
                   mode.setTitle(count + " Item(s) Selected");
                   subjectListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                   subjectElement.remove(subjects.get(position));
               }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.delete_id:
                        SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getApplicationContext());
                        for (Subject element : subjectElement) {
                            handler.deleteSubjectName(element.getTestName(),element.getSubjectName());
                        }
                        populateListView();
                        count = 0;
                        mode.finish();
                        return true;

                    default:
                        return false;

                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        fab_plus = (FloatingActionButton)findViewById(R.id.fabAdd);
        addTestFab = (FloatingActionButton)findViewById(R.id.TestFab);
        addSubjectFab = (FloatingActionButton)findViewById(R.id.SubjectFab);
        showGraphsFab = (FloatingActionButton)findViewById(R.id.ShowGraphsFab);
        fabDelete = (FloatingActionButton)findViewById(R.id.fabDelete);

        layoutAddTest = (LinearLayout) findViewById(R.id.AddTestLayout);
        layoutAddSubject = (LinearLayout) findViewById(R.id.AddSubjectLayout);
        LayoutShowGraphs = (LinearLayout) findViewById(R.id.ShowGraphsLayout);
        layoutDeleteTest = (LinearLayout) findViewById(R.id.deleteLayout);

        //Setting up the Animations for Floating Action Button
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRC = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_clockwise);
        FabRAC = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_anticlockwise);

        //On Click Listeners for Floating Action Buttons
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    closeFabIcon();
                }else{
                    openFabIcon();
                }
            }
        });

        addTestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFabIcon();
                TestDialog dialog = new TestDialog();
                dialog.show(getFragmentManager(),"TestDialog");
            }
        });

        addSubjectFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem() != null) {
                    closeFabIcon();
                    TEST_NAME_TABLE = spinner.getSelectedItem().toString();
                    SubjectDialog dialog = new SubjectDialog();
                    dialog.show(getFragmentManager(), "SubjectDialog");
                }else{
                    Toast.makeText(MainActivity.this,"Add Test Name First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem() != null) {
                    closeFabIcon();
                    DeleteDialog deleteDialog = new DeleteDialog();
                    deleteDialog.show(getFragmentManager(),"DeleteDialog");
                }else{
                    Toast.makeText(MainActivity.this,"Add Test Name First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        showGraphsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFabIcon();
                Intent intent = new Intent(MainActivity.this, Graph.class);
                startActivity(intent);
            }
        });

    }

    public void loadSpinnerData() {
        // database handler
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
        spinner.setAdapter(dataAdapter);
    }

    public void populateListView(){
        SubjectNameDatabaseHandler handler = new SubjectNameDatabaseHandler(getApplicationContext());

        if(spinner.getSelectedItem()!=null ) {

            TEST_NAME_TABLE = spinner.getSelectedItem().toString();
            subjects = handler.getSubjectList(TEST_NAME_TABLE);
        }else{
            subjects.clear();
        }

        subjectDataAdapter = new CustomListAdapter(getApplicationContext(),subjects);

        subjectDataAdapter.notifyDataSetChanged();
        subjectListView.setAdapter(subjectDataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        populateListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void closeFabIcon(){
        layoutDeleteTest.startAnimation(FabClose);
        layoutAddSubject.startAnimation(FabClose);
        layoutAddTest.startAnimation(FabClose);
        LayoutShowGraphs.startAnimation(FabClose);
        fab_plus.startAnimation(FabRAC);
        layoutDeleteTest.setClickable(false);
        layoutAddTest.setClickable(false);
        layoutAddSubject.setClickable(false);
        LayoutShowGraphs.setClickable(false);
        isOpen = false;
    }

    public void openFabIcon(){
        layoutDeleteTest.startAnimation(FabOpen);
        layoutAddSubject.startAnimation(FabOpen);
        LayoutShowGraphs.startAnimation(FabOpen);
        layoutAddTest.startAnimation(FabOpen);
        fab_plus.startAnimation(FabRC);
        layoutDeleteTest.setClickable(true);
        layoutAddTest.setClickable(true);
        layoutAddSubject.setClickable(true);
        LayoutShowGraphs.setClickable(true);
        isOpen = true;
    }

}

