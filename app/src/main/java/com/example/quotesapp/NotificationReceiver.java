package com.example.quotesapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String[] quotes = {
                    "The best way to predict the future is to create it.",
                    "Life is 10% what happens to us and 90% how we react to it.",
                    "Do not wait; the time will never be 'just right.'",
                    "The only limit to our realization of tomorrow is our doubts of today.",
                    "Believe you can and you're halfway there.",
                    "Success is not the key to happiness. Happiness is the key to success."
            };

            String randomQuote = quotes[new Random().nextInt(quotes.length)];
            Log.d(TAG, "Attempting to show notification with quote: " + randomQuote);

            NotificationHelper.showNotification(context, "Daily Motivation", randomQuote);
            Log.d(TAG, "Notification sent successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error showing notification: " + e.getMessage(), e);
        }
    }
}