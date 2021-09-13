package com.example.spaceoptima.Models;

public class Venue
{
    private String Title;
    private String Faculty;
    private String Description;
    private String theatres;
    private String students;
    private String Modules;
    private int Thumbnail;


    public Venue() {
    }

    public Venue(String title, String faculty, String description,String theatres, String students, String modules, int thumbnail) {
        Title = title;
        Faculty = faculty;
        this.theatres = theatres;
        this.students = students;
        Modules = modules;
        Description = description;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTheatres() {
        return theatres;
    }

    public void setTheatres(String theatres) {
        this.theatres = theatres;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getModules() {
        return Modules;
    }

    public void setModules(String modules) {
        Modules = modules;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
