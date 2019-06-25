package com.example.gazetkapl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class shops extends AppCompatActivity {

    public void back(View v)
    {
        Intent intencja = new Intent(this, MainActivity.class);
        startActivity(intencja);
    }

    public void next(View v)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void bied(View v)
    {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

    public void mapa(View v)
    {
        Intent intencja = new Intent(this, MapsActivity.class);
        startActivity(intencja);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shops);
    }
}
