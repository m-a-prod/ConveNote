package com.conve.convenote.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conve.convenote.model.Notebook;
import com.conve.convenote.viewmodel.Adapter;
import com.conve.convenote.viewmodel.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.conve.convenote.R;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    // создание полей
    private RecyclerView recyclerView; // поле для списка RecyclerView
    private FloatingActionButton fabAdd; // поле для кнопки добавить новую заметку

    private ImageView btnSettings, btnInfo;

    private List<Notebook> notesList; // поле для контейнера списка заметок

    private DatabaseHelper database; // поле работы с БД
    private Adapter adapter; // поле для адаптера


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // присваивание id полям
        recyclerView = findViewById(R.id.recycler_list);
        fabAdd = findViewById(R.id.fabAdd);
        btnSettings = findViewById(R.id.settingsBtnSecond);
        btnInfo = findViewById(R.id.infoBtnSecond);

        notesList = new ArrayList<>(); // выделение памяти и задание типа контейнера для списка заметок
        database = new DatabaseHelper(this); // выделение памяти и задание текущего контекста работы с БД

        // считывание данных из БД и запись их в коллекцию notesList
        fetchAllNotes();

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // задание структуры вывода данных в recyclerView
        adapter = new Adapter(this, SecondActivity.this, notesList); // инициализация адаптера и передача в рего данных из БД
        recyclerView.setAdapter(adapter); // передача в recyclerView адаптер

        // обработка нажатия кнопки создания новой заметки
        fabAdd.setOnClickListener(listener);
        btnInfo.setOnClickListener(listener);
        btnSettings.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // переключение на новую активность
            if (view.getId() == R.id.fabAdd) {
                startActivity(new Intent(SecondActivity.this, AddNotesActivity.class));
            } else if (view.getId() == R.id.infoBtnSecond) {
                startActivity(new Intent(getApplicationContext(), InstructionActivity.class));
            } else if (view.getId() == R.id.settingsBtnSecond) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

            }
        }
    };

    // метод считывания из БД всех записей
    public void fetchAllNotes() {
        // чтение БД и запись данных в курсор
        Cursor cursor = database.readNotes();

        if (cursor.getCount() == 0) { // если данные отсутствую, то вывод на экран об этом тоста
            Toast.makeText(this, "Заметок нет", Toast.LENGTH_SHORT).show();
        } else { // иначе помещение их в контейнер данных notesList
            while (cursor.moveToNext()) {
                // помещение в контейнер notesList из курсора данных
                notesList.add(new Notebook(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}