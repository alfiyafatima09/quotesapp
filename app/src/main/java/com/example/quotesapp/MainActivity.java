package com.example.quotesapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView quoteTextView;
    private Button fetchQuoteButton, saveQuoteButton, viewFavoritesButton;

    private static final String[] quotes = {
            "The best way to predict the future is to create it.",
            "Life is 10% what happens to us and 90% how we react to it.",
            "Do not wait; the time will never be 'just right.'",
            "The only limit to our realization of tomorrow is our doubts of today.",
            "Believe you can and you're halfway there.",
            "Success is not the key to happiness. Happiness is the key to success.",
            "Your time is limited, so don't waste it living someone else's life.",
            "The only way to do great work is to love what you do.",
            "Don’t watch the clock; do what it does. Keep going.",
            "The harder the conflict, the greater the triumph.",
            "You miss 100% of the shots you don’t take.",
            "It does not matter how slowly you go as long as you do not stop.",
            "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
            "Happiness is not something ready made. It comes from your own actions.",
            "In the middle of every difficulty lies opportunity.",
            "Don’t wait for opportunity. Create it.",
            "It always seems impossible until it’s done.",
            "Act as if what you do makes a difference. It does.",
            "Dream big and dare to fail.",
            "The secret of getting ahead is getting started.",
            "Everything you’ve ever wanted is on the other side of fear.",
            "Do what you can, with what you have, where you are.",
            "Perseverance is not a long race; it is many short races one after the other.",
            "Success is walking from failure to failure with no loss of enthusiasm.",
            "If you can dream it, you can do it.",
            "The future belongs to those who believe in the beauty of their dreams.",
            "Work hard in silence, let your success make the noise.",
            "Don’t let yesterday take up too much of today.",
            "Opportunities don't happen. You create them.",
            "Limitations live only in our minds. But if we use our imaginations, our possibilities become limitless."
    };

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuotesApp";
    private static final String FAVOURITES_KEY = "favourites";

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate started");

        // Initialize Views
        quoteTextView = findViewById(R.id.quoteTextView);
        fetchQuoteButton = findViewById(R.id.fetchQuoteButton);
        saveQuoteButton = findViewById(R.id.saveQuoteButton);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Request Notification Permission if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Requesting notification permission");
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION
                );
            } else {
                Log.d(TAG, "Notification permission already granted");
                initializeApp(); // Permission already granted
            }
        } else {
            Log.d(TAG, "No runtime permission needed for this Android version");
            initializeApp(); // No runtime permission needed
        }
    }

    private void initializeApp() {
        Log.d(TAG, "Initializing app");

        // Create Notification Channel
        NotificationHelper.createNotificationChannel(this);

        // Check and request alarm permission
        checkAndRequestAlarmPermission();

        // Fetch Quote Button: Randomly fetch a quote
        fetchQuoteButton.setOnClickListener(v -> {
            String randomQuote = quotes[new Random().nextInt(quotes.length)];
            quoteTextView.setText(randomQuote);
        });

        // Save Quote Button: Save the current quote to SharedPreferences
        saveQuoteButton.setOnClickListener(v -> {
            String currentQuote = quoteTextView.getText().toString();
            saveQuote(currentQuote);
        });

        // View Favorites Button: Navigate to FavouritesActivity
        viewFavoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        // Schedule Notifications
        scheduleNotifications();
    }

    private void checkAndRequestAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Log.d(TAG, "Exact alarm permission not granted. Prompting user...");
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                Log.d(TAG, "Exact alarm permission already granted.");
            }
        }
    }

    // Save the current quote to SharedPreferences
    private void saveQuote(String quote) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> favouritesSet = sharedPreferences.getStringSet(FAVOURITES_KEY, new HashSet<>());

        if (favouritesSet.contains(quote)) {
            Toast.makeText(this, "Quote is already saved!", Toast.LENGTH_SHORT).show();
            return;
        }

        favouritesSet = new HashSet<>(favouritesSet); // Create a new copy
        favouritesSet.add(quote);
        editor.putStringSet(FAVOURITES_KEY, favouritesSet);
        editor.apply();

        Toast.makeText(this, "Quote saved successfully!", Toast.LENGTH_SHORT).show();
    }

    // Schedule notifications for every 1 minute
    @SuppressLint("ObsoleteSdkInt")
    private void scheduleNotifications() {
        Log.d(TAG, "Scheduling notifications");

        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            long intervalMillis = Math.max(60 * 1000, 30 * 1000); // Minimum interval: 30 seconds
            long triggerAtMillis = System.currentTimeMillis() + intervalMillis;

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            triggerAtMillis,
                            pendingIntent
                    );
                } else {
                    alarmManager.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            triggerAtMillis,
                            intervalMillis,
                            pendingIntent
                    );
                }
                Log.d(TAG, "Notifications scheduled successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error scheduling notifications: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "AlarmManager is null");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Notification permission granted");
                initializeApp(); // Proceed if the user grants the permission
            } else {
                Log.d(TAG, "Notification permission denied");
                Toast.makeText(this, "Notification permission is required to show quotes.", Toast.LENGTH_LONG).show();
                initializeApp(); // Allow app to run without notifications
            }
        }
    }
}
