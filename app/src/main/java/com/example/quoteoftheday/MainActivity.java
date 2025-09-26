package com.example.quoteoftheday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView quoteText = findViewById(R.id.quoteText);
        Button newQuoteButton = findViewById(R.id.newQuoteButton);
        String[] quotes = {
                "A lion remains a lion, even in a cage.",
                "If you are not a wolf, the wolves will eat you.",
                "The wheel of injustice turns against the oppressor.",
                "Time is gold.",
                "Every journey starts with a misstep."
        };
        newQuoteButton.setOnClickListener(v -> {
            int randomIndex = (int) (Math.random() * quotes.length);
            quoteText.setText(quotes[randomIndex]);
        });
        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, quoteText.getText().toString());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
        Button favoriteButton = findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(v -> {
            String currentQuote = quoteText.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("QuoteOfTheDayPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("favorite_quote", currentQuote);
            editor.apply();
            // A Toast message to confirm saving
            Toast.makeText(this, "Saved as favorite!", Toast.LENGTH_SHORT).show();
        });
        SharedPreferences sharedPreferences = getSharedPreferences("QuoteOfTheDayPrefs", MODE_PRIVATE);
        String favoriteQuote = sharedPreferences.getString("favorite_quote", "");
        if (!favoriteQuote.isEmpty()) {
            quoteText.setText(favoriteQuote);
        }

    }
}