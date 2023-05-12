package com.conve.convenote.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.conve.convenote.R;


public class TokenActivity extends AppCompatActivity {
    public String chat_gpt_token;
    private String CHATGPT_TOKEN = "chatgpt_token";
    private String TOKEN = "token";
    private SharedPreferences chatgpt_token_setting;
    private ImageView infoBtn;

    private SharedPreferences.Editor chatgpt_token_setting_editor;
    private Button saveBtn, passBtn;
    private EditText editToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        saveBtn = findViewById(R.id.saveBtn);
        passBtn = findViewById(R.id.passBtn);
        editToken = findViewById(R.id.editToken);
        infoBtn = findViewById(R.id.infoBtnToken);
        chatgpt_token_setting = getSharedPreferences(CHATGPT_TOKEN, MODE_PRIVATE);

        saveBtn.setOnClickListener(listener);
        passBtn.setOnClickListener(listener);
        infoBtn.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.saveBtn) {
                if (editToken.getText().toString().matches("^[a-zA-Z0-9`~!@#$%^&*()-_=+\\[{\\]}\\\\|;:'\",<.>/?]+$")) {
                    chat_gpt_token = editToken.getText().toString();
                    chatgpt_token_setting_editor = chatgpt_token_setting.edit();
                    chatgpt_token_setting_editor.putString(TOKEN, chat_gpt_token);
                    chatgpt_token_setting_editor.apply();
                    Toast.makeText(getApplicationContext(), "Токен успешно сохранён", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Токен содержит непозволительные символы", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId() == R.id.infoBtnToken) {
                startActivity(new Intent(getApplicationContext(), InstructionActivity.class));
            } else if (v.getId() == R.id.passBtn) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }

        }
    };
}