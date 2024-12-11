package com.example.quotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

public class FavouritesAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> quotes;

    public FavouritesAdapter(Context context, List<String> quotes) {
        super(context, R.layout.item_favourite, quotes);
        this.context = context;
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);
        }

        // Get quote and views
        String quote = quotes.get(position);
        TextView quoteTextView = convertView.findViewById(R.id.quoteTextView);
        ImageView deleteIcon = convertView.findViewById(R.id.deleteIcon);

        // Set quote text
        quoteTextView.setText(quote);

        // Set delete icon functionality
        deleteIcon.setOnClickListener(v -> {
            if (context instanceof FavouritesActivity) {
                ((FavouritesActivity) context).removeQuote(quote);
            }
        });

        return convertView;
    }
}
