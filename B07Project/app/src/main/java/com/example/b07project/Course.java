package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    public String code;
    public String sessions;
    public List<String> prerequisite;

    public Course() {
        prerequisite = new ArrayList<>();
    }
}
