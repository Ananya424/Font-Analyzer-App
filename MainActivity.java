package com.example.fontanalyzermadlab;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isBold = false;   // Track Bold state
    private boolean isItalic = false; // Track Italic state
    private Typeface currentTypeface; // Track current Typeface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        EditText inputText = findViewById(R.id.inputText);
        Button toggleBoldButton = findViewById(R.id.toggleBoldButton);
        Button toggleItalicButton = findViewById(R.id.toggleItalicButton);
        SeekBar fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        Button arialFontButton = findViewById(R.id.arialFontButton);
        Button parkbtn = findViewById(R.id.timesFontButton);
        Button robotoFontButton = findViewById(R.id.robotoFontButton);
        Button analyzeButton = findViewById(R.id.analyzeButton);
        TextView outputText = findViewById(R.id.outputText);

        // Initialize current Typeface
        currentTypeface = inputText.getTypeface();

        // Toggle Bold Button
        toggleBoldButton.setOnClickListener(v -> {
            isBold = !isBold; // Toggle bold state
            updateFontStyle(inputText);
        });

        // Toggle Italic Button
        toggleItalicButton.setOnClickListener(v -> {
            isItalic = !isItalic; // Toggle italic state
            updateFontStyle(inputText);
        });

        // SeekBar for Font Size
        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Change font size dynamically
                inputText.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Font Style Buttons
        arialFontButton.setOnClickListener(v -> changeFont(inputText, "font/Jaro-Regular-VariableFont_opsz.ttf"));
        parkbtn.setOnClickListener(v -> changeFont(inputText, "font/Parkinsans-VariableFont_wght.ttf"));
        robotoFontButton.setOnClickListener(v -> changeFont(inputText, "font/Roboto-Black.ttf"));

        // Analyze Button
        analyzeButton.setOnClickListener(v -> {
            Typeface typeface = inputText.getTypeface();
            float fontSize = inputText.getTextSize(); // Size in pixels
            String analysis = "Font Analysis:\n" +
                    "Bold: " + isBold + "\n" +
                    "Italic: " + isItalic + "\n" +
                    "Font Size: " + (int) fontSize + " px";
            outputText.setText(analysis);
        });
    }

    /**
     * Update font style with current bold/italic settings.
     */
    private void updateFontStyle(EditText inputText) {
        int style;
        if (isBold && isItalic) {
            style = Typeface.BOLD_ITALIC;
        } else if (isBold) {
            style = Typeface.BOLD;
        } else if (isItalic) {
            style = Typeface.ITALIC;
        } else {
            style = Typeface.NORMAL;
        }
        inputText.setTypeface(currentTypeface, style);
    }

    /**
     * Change the font of the EditText dynamically.
     *
     * @param inputText The EditText whose font will change.
     * @param fontPath  Path to the font file in the assets directory.
     */
    private void changeFont(EditText inputText, String fontPath) {
        try {
            currentTypeface = Typeface.createFromAsset(getAssets(), fontPath);
            updateFontStyle(inputText); // Apply current bold/italic settings
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
