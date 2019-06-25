package com.example.gazetkapl;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    private TextView text1;
    private PDFView pdfView;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference("url");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_1);

        pdfView=(PDFView)findViewById(R.id.pdfviewer);
        text1=(TextView)findViewById(R.id.text1);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                text1.setText(value);
                Toast.makeText(Main2Activity.this, "Update", Toast.LENGTH_SHORT).show();
                String url=text1.getText().toString();
                new RetrivePdfStream().execute(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this, "Nie udało się wczytać, możliwe że wystąpił problem z połączeniem internetowym, bądź wystapił inny problem.", Toast.LENGTH_SHORT).show();
            }
        });}
//gg
    class RetrivePdfStream extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try{
                URL url = new URL (strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200) {
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                return null;
            }
            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}
