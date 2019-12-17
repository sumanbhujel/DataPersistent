package com.example.datapersistent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datapersistent.R;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyWordHolder> {

    Context c;
    private Character[] characterList;
    EditText etWord;

    public WordAdapter(Context c, Character[] characterList, EditText etWord) {
        this.c = c;
        this.characterList = characterList;
        this.etWord = etWord;
    }

    @NonNull
    @Override
    public MyWordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_word_layout, parent, false);
        MyWordHolder myWordHolder = new MyWordHolder(view);
        return myWordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyWordHolder holder, final int position) {

        holder.textView.setText(String.valueOf(characterList[position]));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = etWord.getText().toString() + characterList[position];
                etWord.setText(w);
                holder.textView.setTextColor(c.getResources().getColor(R.color.colorBlack));
                holder.textView.setBackgroundColor(c.getResources().getColor(R.color.colorGray));
                holder.textView.setOnClickListener(null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return characterList.length;
    }


    public class MyWordHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;

        public MyWordHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            textView = itemView.findViewById(R.id.tvWord);
        }
    }
}
