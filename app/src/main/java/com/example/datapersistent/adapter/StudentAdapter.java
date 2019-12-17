package com.example.datapersistent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datapersistent.R;
import com.example.datapersistent.UpdateStudentActivity;
import com.example.datapersistent.database.DbHelper;
import com.example.datapersistent.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyHolder> {

    Context c;
    List<Student> studentList;

    public StudentAdapter(Context c, List<Student> studentList) {
        this.c = c;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_recyclerview, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final Student student = studentList.get(position);
        holder.tName.setText(student.getName());
        holder.tEmail.setText(student.getEmail());
        holder.tPhone.setText(student.getPhone());
        holder.imageView.setImageResource(R.drawable.image);
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, UpdateStudentActivity.class);
                intent.putExtra("id", student.getId());
                intent.putExtra("name", student.getName());
                intent.putExtra("email", student.getEmail());
                intent.putExtra("phone", student.getPhone());

                c.startActivity(intent);

            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(c);
                if (dbHelper.deleteData(student)) {
                    studentList.remove(student);
                    notifyItemRemoved(position);
                    Toast.makeText(c, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tName, tEmail, tPhone;
        Button buttonEdit, buttonDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            tName = itemView.findViewById(R.id.textName);
            tEmail = itemView.findViewById(R.id.textEmail);
            tPhone = itemView.findViewById(R.id.textPhone);
            buttonDelete = itemView.findViewById(R.id.btnDelete);
            buttonEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
