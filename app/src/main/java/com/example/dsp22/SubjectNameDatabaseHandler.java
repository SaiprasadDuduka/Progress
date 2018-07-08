package com.example.dsp22.progress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class SubjectNameDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "subjectDetails";
    private static final String SUBJECT_TABLE_NAME = "subjectList";

    //Column Names
    public static final String ID = "id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String TEST_NAME = "test_name";
    public static final String MARKS = "marks";
    public static final String TOTAL_MARKS = "total_marks";

    private List<Subject> subjectList = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    private List<BarEntry> data = new ArrayList<>();

    private List<String> spinnerSubjectList = new ArrayList<>();
    private List<String> testLabels = new ArrayList<>();
    private List<Entry> lineChartdata = new ArrayList<>();

    public SubjectNameDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEST_TABLE = "CREATE TABLE "+ SUBJECT_TABLE_NAME +"(" +
                ID + " INTEGER PRIMARY KEY," +  TEST_NAME + " TEXT,"+
                SUBJECT_NAME + " TEXT," + MARKS + " REAL," +
                TOTAL_MARKS + " REAL)";
        db.execSQL(CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertSubjectDetails(String testName,String subjectName,float marks,float totalMarks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEST_NAME,testName);
        values.put(SUBJECT_NAME, subjectName);
        values.put(MARKS,marks);
        values.put(TOTAL_MARKS,totalMarks);

        // Inserting Row
        db.insert(SUBJECT_TABLE_NAME, null, values);
        db.close();
    }

    public List<Subject> getSubjectList(String testName){

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        subjectList.clear();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(testName) && cursor.getString(2)!= null){
                    Log.e("Table","Inside if statement wow!");
                    subjectList.add(new Subject(cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getFloat(3),
                                    cursor.getFloat(4)));
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return subjectList;
    }

    public List<String> getSpinnerSubjectList(){
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        spinnerSubjectList.clear();

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(2)!= null && !spinnerSubjectList.contains(cursor.getString(2))){
                    spinnerSubjectList.add(cursor.getString(2));
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return spinnerSubjectList;
    }

    public List<String> getSubjectLabels(String testName){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        labels.clear();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(testName) && cursor.getString(2)!= null){
                    labels.add(cursor.getString(2));
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return labels;
    }

    public List<BarEntry> getTestwiseSubjectData(String testName){
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        data.clear();
        int i = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(testName) && cursor.getString(2)!= null ){
                    float percent = (cursor.getFloat(3)/cursor.getFloat(4))*100;
                    data.add(new BarEntry(percent,i));
                    i++;
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return data;
    }

    public List<String> getTestLabels(String subjectName){
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        testLabels.clear();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(2)!=null){
                    if(cursor.getString(2).equals(subjectName)) {
                        testLabels.add(cursor.getString(1));
                    }
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return testLabels;
    }

    public List<Entry> getSubjectwiseSubjectData(String subjectName){
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        lineChartdata.clear();
        int i = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(2)!=null){
                    if(cursor.getString(2).equals(subjectName)) {
                        float percent = (cursor.getFloat(3) / cursor.getFloat(4)) * 100;
                        lineChartdata.add(new Entry(percent, i));
                        i++;
                    }
                }
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return lineChartdata;
    }

    public void deleteSubjectName(String testName, String subjectName){
        String deleteQuery = "DELETE FROM " + SUBJECT_TABLE_NAME + " WHERE " + TEST_NAME +
                                " LIKE '" + testName +"' AND " + SUBJECT_NAME + " LIKE '" +
                                    subjectName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
    }

    public void deleteTestName(String testName){
        String deleteQuery = "DELETE FROM " + SUBJECT_TABLE_NAME + " WHERE " + TEST_NAME +
                " LIKE '" + testName +"'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
    }

    public void insertTestName(String testName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEST_NAME, testName);

        // Inserting Row
        db.insert(SUBJECT_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public List<String> getTestList(){
        List<String> testList = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + SUBJECT_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        testList.clear();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(!testList.contains(cursor.getString(1))){
                    testList.add(0,cursor.getString(1));
                }

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return testList;
    }

}
