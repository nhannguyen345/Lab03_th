package com.example.lab03_th;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String major = intent.getStringExtra("major");
        float average = intent.getFloatExtra("average", 0);

        final EditText txt_id = findViewById(R.id.txt_id);
        final EditText txt_name = findViewById(R.id.txt_name);
        final EditText txt_major = findViewById(R.id.txt_major);
        final EditText txt_average = findViewById(R.id.txt_average);
        final Button btn_update = findViewById(R.id.btn_update);

        txt_id.setText(String.valueOf(id));
        txt_name.setText(name);
        txt_major.setText(major);
        txt_average.setText(String.valueOf(average));


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Student st = new Student();
                //st.setId(id);
                //st.setName(name);
                //st.setMajor(major);
                //st.setAverage(average);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", Integer.parseInt(txt_id.getText().toString()));
                resultIntent.putExtra("name", txt_name.getText().toString());
                resultIntent.putExtra("major", txt_major.getText().toString());
                resultIntent.putExtra("average", Float.parseFloat(txt_average.getText().toString()));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });



    }
}