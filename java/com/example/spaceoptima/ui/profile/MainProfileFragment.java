package com.example.spaceoptima.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaceoptima.Adapters.VenueAdapter;
import com.example.spaceoptima.R;
import com.example.spaceoptima.Models.Venue;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainProfileFragment extends Fragment {

    private ProfileViewModel mainprofileViewModel;

    RecyclerView venueRecyclerView;
    VenueAdapter venueAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Venue> venueList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainprofileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View fragmentView = inflater.inflate(R.layout.fragment_main_profile, container, false);
        venueRecyclerView = fragmentView.findViewById(R.id.venueRV);
        venueRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Venues");

        mainprofileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return fragmentView;

    }
}
