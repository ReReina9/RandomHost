package com.randomhost.logic;

public class Participant {

    private String name;
    private String lastName;
    private boolean absent;

    public Participant(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
        this.absent = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAbsent(){
        this.absent = true;
    }

    public void setPresent(){
        this.absent = false;
    }

    public boolean isAbsent(){
        return absent;
    }

    public String toString(){
        return name + " " + lastName;
    }
}
