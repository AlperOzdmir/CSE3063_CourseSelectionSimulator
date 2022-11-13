package iteration1.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Person {

    private String id;
    private int registerDate;
    private int semesterNo;
    private Transcript transcript;
    private ArrayList<String> errors;
    private Advisor advisor;
    private HashMap<Course, Boolean> selectedCourses;


    public void generateStudentID() {

    }

    public String getId() {
        return id;
    }

    public int getRegisterDate() {
        return registerDate;
    }

    public int getSemesterNo() {
        return semesterNo;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public HashMap<Course, Boolean> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSemesterNo(int semesterNo) {
        this.semesterNo = semesterNo;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public void setSelectedCourses(HashMap<Course, Boolean> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }

    @Override
    public String getSsn() {
        return super.getSsn();
    }

    @Override
    public Character getGender() {
        return super.getGender();
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}