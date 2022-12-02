package com.example.b07project;

import java.util.Arrays;

public class Courses {
    private String code;
    private String course_name;
    private String semesters_offered;
    private String prerequesities;
    public Courses()
    {

    }
    public Courses(String code, String course_name, String semesters_offered, String prerequesities)
    {
        this.code = code;
        this.course_name = course_name;
        this.semesters_offered= semesters_offered;
        this.prerequesities= prerequesities;
    }
    @Override
    public String toString()
    {
        return this.course_name + "\t\t" + this.code;
    }


    public String getCode() {
        return code;
    }
    public String getCourseName() {
        return course_name;
    }
    public String[] getSemesters_offered() {
        return semesters_offered.split(",");
    }
    public String[] getPrereq() {
        return prerequesities.split(",");
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }
    public void setSemesters_offered(String [] arr) {
        this.semesters_offered = Arrays.toString(arr);
    }
    public void setPrereq(String [] arr) {
        this.prerequesities = Arrays.toString(arr);
    }
}
