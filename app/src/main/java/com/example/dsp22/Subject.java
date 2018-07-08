package com.example.dsp22.progress;

public class Subject {

    private String subjectName;
    private String testName;
    public Float marks,totalMarks;

    public Subject(String testName,String subjectName, Float marks, Float totalMarks){
        this.testName = testName;
        this.subjectName = subjectName;
        this.marks = marks;
        this.totalMarks = totalMarks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Float getMarks() {
        return marks;
    }

    public void setMarks(Float marks) {
        this.marks = marks;
    }

    public Float getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Float totalMarks) {
        this.totalMarks = totalMarks;
    }
}
