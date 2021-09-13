package com.example.spaceoptima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Building extends AppCompatActivity
{
    private TextView tvtitle, tvdescription, tvFaculty, tvTheatres, tvStudents, tvModules;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        tvtitle = findViewById(R.id.txttitle);
        tvdescription = findViewById(R.id.txtDesc);
        tvFaculty = findViewById(R.id.txtFac);
        tvTheatres = findViewById(R.id.txtLectureTheatres);
        tvStudents = findViewById(R.id.txtStudentCap);
        tvModules = findViewById(R.id.txtModules);
        img = findViewById(R.id.venueThumbnail);


        //Retrieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String Faculty = intent.getExtras().getString("Faculty");
        String Theatres = intent.getExtras().getString("Theatres");
        String Students = intent.getExtras().getString("Students");
        String Modules = intent.getExtras().getString("Modules");
        int image = intent.getExtras().getInt("Thumbnail");


        //Setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        tvFaculty.setText(Faculty);
        tvTheatres.setText(Theatres);
        tvStudents.setText(Students);
        tvModules.setText(Modules);
        img.setImageResource(image);
    }
}
