package com.example.lab03_th;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DbAdapter db;
    private List<Student> list;

    private StudentAdapter adapter;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Student st = new Student();
            st.setId(data.getIntExtra("id", 0));
            st.setName(data.getStringExtra("name"));
            st.setMajor(data.getStringExtra("major"));
            st.setAverage(data.getFloatExtra("average", 0));
            Updatest(st);
            adapter.addItem(st);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txt_name = findViewById(R.id.txt_name);
        final EditText txt_major = findViewById(R.id.txt_major);
        final EditText txt_average = findViewById(R.id.txt_average);
        final Button btn_insert = findViewById(R.id.btn_insert);
        final RecyclerView recyc_list = findViewById(R.id.list_view);
        recyc_list.setLayoutManager(new LinearLayoutManager((this)));
        db = new DbAdapter(this);
        db.open();
        list = db.getAllStudents();
        adapter = new StudentAdapter(this, list, db);
        recyc_list.setAdapter(adapter);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student st = new Student();
                st.setName(txt_name.getText().toString());
                st.setMajor(txt_major.getText().toString());
                st.setAverage(Float.parseFloat(txt_average.getText().toString()));
                long f = db.addStudent(st);
                    Toast.makeText(MainActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                   list.add(db.getStudent(Long.valueOf(f).intValue()));
                    adapter.notifyDataSetChanged();
            }
        });

    }

    private void Updatest(Student st){
        db.UpdateStudent(st);
    }
}