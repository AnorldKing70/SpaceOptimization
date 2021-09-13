package com.example.spaceoptima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.spaceoptima.Adapters.VenueAdapter;
import com.example.spaceoptima.Models.Venue;

import java.util.ArrayList;
import java.util.List;

public class VenueBuildings extends AppCompatActivity
{
    List<Venue> venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_buildings);

        //Venue objects
        venueList = new ArrayList<>();
        venueList.add(new Venue("BHP", "Faculty of Engineering and IT", "Technology and Sciences studies","Number of Theatres : 85","Students : 3,507", "Modules : 105", R.drawable.bhp));
        venueList.add(new Venue("Library", "All Faculties", "Study and Preparation","Number of Theatres : 40","Students : 16,202", "Modules : 175", R.drawable.cut));
        venueList.add(new Venue("Hotel School", "Faculty of Health and Environmental Sciences", "Health Studies","Number of Theatres : 85","Students : 3,507", "Modules : 100", R.drawable.hotel));
        venueList.add(new Venue("Lapeng Building", "Faculty of Resources", "Resources Allocation and Accomodating","Number of Theatres : 65","Students : 3,507", "Modules : 61", R.drawable.theatre));
        venueList.add(new Venue("Humanities Building", "Faculty of Management Sciences", "Humanity Studies","Number of Theatres : 90","Students : 4,507", "Modules : 225", R.drawable.management));
        venueList.add(new Venue("Social Studies Building", "Faculty Humanities and Arts", "Arts and Humanities studies","Number of Theatres : 75","Students : 3,507", "Modules : 445", R.drawable.support));
        venueList.add(new Venue("BHP Side-B", "Faculty of Engineering and Building", "Building Sciences studies","Number of Theatres : 34","Students : 1,802", "Modules : 115", R.drawable.bhp));
        venueList.add(new Venue("Library", "All Faculties", "Study and Preparation","Number of Theatres : 85","Students : 2,507", "Modules : 105", R.drawable.cut));
        venueList.add(new Venue("Computer Studies", "All Faculties", "Studies","Number of Theatres : 65","Students : 2,307", "Modules : 185", R.drawable.support));
        venueList.add(new Venue("Humanities Building", "Faculty of Management Sciences", "Humanity Studies","Number of Theatres : 100","Students : 6,200", "Modules : 225", R.drawable.management));
        venueList.add(new Venue("All Venues", "All Faculties", "Studies","Number of Theatres : 25","Students : 13,507", "Modules : 425", R.drawable.entrace));
        venueList.add(new Venue("Education Building", "Education & Teaching", "Studies","Number of Theatres : 103","Students : 5,790", "Modules : 155", R.drawable.entrace));

        RecyclerView venueRecyclerView = findViewById(R.id.venueRV);
        VenueAdapter myAdapter = new VenueAdapter(this, venueList);
        venueRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        venueRecyclerView.setAdapter(myAdapter);

    }
}
