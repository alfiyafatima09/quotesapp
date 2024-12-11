package com.example.quotesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouritesActivity extends AppCompatActivity {

    private ListView favouritesListView;
    private Button backButton;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuotesApp";
    private static final String FAVOURITES_KEY = "favourites";

    private List<String> favouritesList; // List to store favourite quotes
    private FavouritesAdapter adapter;  // Custom adapter for ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Initialize Views
        favouritesListView = findViewById(R.id.favouritesListView);
        backButton = findViewById(R.id.backButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Retrieve saved favourite quotes
        Set<String> favouritesSet = sharedPreferences.getStringSet(FAVOURITES_KEY, null);
        favouritesList = (favouritesSet != null) ? new ArrayList<>(favouritesSet) : new ArrayList<>();

        // Set up adapter and attach it to the ListView
        adapter = new FavouritesAdapter(this, favouritesList);
        favouritesListView.setAdapter(adapter);

        // Back button functionality
        backButton.setOnClickListener(v -> finish());
    }

    public void removeQuote(String quote) {
        // Remove quote from the list and notify the adapter
        favouritesList.remove(quote);
        adapter.notifyDataSetChanged();

        // Update SharedPreferences to reflect the removal
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> updatedSet = new HashSet<>(favouritesList);
        editor.putStringSet(FAVOURITES_KEY, updatedSet);
        editor.apply();

        // Notify user of successful removal
        Toast.makeText(this, "Quote removed from favourites", Toast.LENGTH_SHORT).show();
    }
}
