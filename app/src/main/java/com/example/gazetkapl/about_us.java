package com.example.gazetkapl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
    public void back(View v)
    {
        Intent intencja = new Intent(this, MainActivity.class);
        startActivity(intencja);
    }
}
