package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    //convert object to serial of string...

    public String email;
    public String name;
    //need  to be mod

    public List<String> coursesTaken;

    public User() {
        coursesTaken = new ArrayList<>();
    }
}
