package com.conve.convenote.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.conve.convenote.R;
import com.conve.convenote.model.CommandProcessor;
import com.conve.convenote.viewmodel.DatabaseHelper;

public class UpdateActivity extends AppCompatActivity {

    // создание полей
    private EditText title, description;
    private CommandProcessor commandProcessor;
    private Button updateNote, deleteNote, useBtn;
    private String TOKEN = "token";
    private String CHATGPT_TOKEN = "chatgpt_token";
    private SharedPreferences settings;

    private ImageView backBtn;
    private Handler handler;
    private String id;
    private TextView loadingText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingText = findViewById(R.id.loadingText);
        setContentView(R.layout.activity_update);
        commandProcessor = new CommandProcessor();
        handler = new Handler(Looper.getMainLooper());

        // присваивание id полям
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        updateNote = findViewById(R.id.update_note);
        deleteNote = findViewById(R.id.delete_note);
        backBtn = findViewById(R.id.backBtn);
        useBtn = findViewById(R.id.useBtn);
        description.setHorizontallyScrolling(false);
        description.setMaxLines(Integer.MAX_VALUE);
        settings = getSharedPreferences(CHATGPT_TOKEN, MODE_PRIVATE);

        // считывание данных из переданного намерения Intent
        Intent intent = getIntent();
        // запись этих данных на экран активности
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        // обработка нажатия кнопки
        updateNote.setOnClickListener(listener);
        deleteNote.setOnClickListener(listener);
        backBtn.setOnClickListener(listener);
        useBtn.setOnClickListener(listener);
    }

    private void updateTextView(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Обновляем элемент интерфейса
                description.setText(text);
            }
        });
    }

    // задание слушателя
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.useBtn) {
                loadingText.setText("Обработка...");
                String result = null;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {
                            // Ваш код, который нужно выполнить в отдельном потоке
                            String result = commandProcessor.Process(description.getText().toString(), settings.getString(TOKEN, "2"));
                            updateTextView(result);
                        }
                        loadingText.setText("");

                    }
                });
                thread.start();
                // место для обработки текстовым процессором editText
            }
            // если исправленный текст не пустой, то обновление записи в БД
            else if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
                DatabaseHelper database = new DatabaseHelper(UpdateActivity.this); // создание объекта БД в текущей активности

                // обработка кнопки
                if (view.getId() == R.id.update_note) {
                    // обновление заметки
                    database.updateNotes(title.getText().toString(), description.getText().toString(), id); // обновление записи в БД по id
                } else if (view.getId() == R.id.delete_note) {
                    // удаление заметки
                    database.deleteSingleItem(id); // удаление записи БД по id
                }

                startActivity(new Intent(UpdateActivity.this, SecondActivity.class)); // переключение обратно в активность демонстрации всех записей
            } else { // иначе просто тост об отсутствии изменений
                Toast.makeText(UpdateActivity.this, "Изменений не внесено", Toast.LENGTH_SHORT).show();
            }
        }
    };
}