package com.conve.convenote.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.conve.convenote.R;


public class SettingsActivity extends AppCompatActivity {
    private ImageView back_view, info_view;
    private TextView currentTokenView;
    private Button saveBtn;
    private EditText editTokenSettings;
    private String CHATGPT_TOKEN = "chatgpt_token";
    private String TOKEN = "token";
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back_view = findViewById(R.id.backBtnSettings);
        saveBtn = findViewById(R.id.saveBtnSettings);
        editTokenSettings = findViewById(R.id.editTokenSettings);
        info_view = findViewById(R.id.infoBtnSettings);
        currentTokenView = findViewById(R.id.currentTokenView);

        settings = getSharedPreferences(CHATGPT_TOKEN, MODE_PRIVATE);

        currentTokenView.setText(currentTokenView.getText().toString() + settings.getString(TOKEN, "токен не был введён"));

        back_view.setOnClickListener(listener);
        saveBtn.setOnClickListener(listener);
        info_view.setOnClickListener(listener);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.backBtnSettings) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.saveBtnSettings) {
                if (editTokenSettings.getText().toString().matches("^[a-zA-Z0-9`~!@#$%^&*()-_=+\\[{\\]}\\\\|;:'\",<.>/?]+$")) {
                    settingsEditor = settings.edit();
                    settingsEditor.putString(TOKEN, editTokenSettings.getText().toString());
                    settingsEditor.apply();
                    editTokenSettings.setText("");
                    currentTokenView.setText("Текущий токен: \n" + settings.getString(TOKEN, "токен не был введён"));
                    Toast.makeText(SettingsActivity.this, "Токен успешно сохранён", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Токен содержит непозволительные символы", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId() == R.id.infoBtnSettings) {
                startActivity(new Intent(getApplicationContext(), InstructionActivity.class));
            }
        }
    };
}