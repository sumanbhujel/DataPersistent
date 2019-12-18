package com.example.datapersistent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datapersistent.adapter.WordAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class WordGameActivity extends AppCompatActivity {

    TextView textViewLevel;
    EditText editTextChar;
    Button buttonOk, buttonClear, buttonReset;
    RecyclerView recyclerView;

    String[] words = {"BANANA","GUAVA","PEACH","APPLE","PINEAPPLE", "ORANGE", "GRAPES"};
    SharedPreferences sharedPreferences;
    int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);

        textViewLevel = findViewById(R.id.tvLevel);
        editTextChar = findViewById(R.id.edWords);
        buttonOk = findViewById(R.id.btnOk);
        buttonClear = findViewById(R.id.btnClear);
        buttonReset = findViewById(R.id.btnReset);
        recyclerView = findViewById(R.id.rvWords);

        SharedPreferences sp = getSharedPreferences("word_game", Context.MODE_PRIVATE);
        if (sp.getInt("Level", 0) == 0) {
            showWord(level);
        } else {
            level = sp.getInt("Level", 0);
            showWord(level);
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usr_word = editTextChar.getText().toString();
                if (level + 1 < words.length) {
                    if (usr_word.equals(words[level])) {
                        level++;
                        showWord(level);
                        sharedPreferences = getSharedPreferences("word_game", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("Level", level);
                        editor.commit();
                        editTextChar.setText("");
                        Toast.makeText(WordGameActivity.this, "Matched : Level Increased", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(WordGameActivity.this, "Not Matched", Toast.LENGTH_SHORT).show();
                        editTextChar.setText("");
                        showWord(level);
                    }
                } else {
                    level = 0;
                    sharedPreferences = getSharedPreferences("word_game", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Level", level);
                    showWord(level);
                    Toast.makeText(WordGameActivity.this, "All Level Completed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextChar.setText("");
                showWord(level);
            }
        });

    }

    private Character[] shuffleWords(int level) {
        char[] word = words[level].toCharArray();

        ArrayList<Character> chars = new ArrayList<>(word.length);
        for (char c : word) {
            chars.add(c);
        }

        Collections.shuffle(chars);
        Character[] shuffledWord = new Character[chars.size()];

        for (int i = 0; i < shuffledWord.length; i++) {
            shuffledWord[i] = chars.get(i);
        }
        return shuffledWord;
    }

    private void showWord(int i) {
        WordAdapter wordAdapter = new WordAdapter(WordGameActivity.this, shuffleWords(i), editTextChar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(layoutManager);
        textViewLevel.setText("LEVEL : " + (i + 1));
    }
}
