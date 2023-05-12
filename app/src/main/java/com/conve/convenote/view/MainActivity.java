package com.conve.convenote.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.conve.convenote.R;


public class MainActivity extends AppCompatActivity {

    private Button yesButton, noButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("yesButtonClicked", false)) {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("yesButtonClicked", true);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                startActivity(intent);
            }
        });
    }
}
