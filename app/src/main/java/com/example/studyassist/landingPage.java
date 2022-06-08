package com.example.studyassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class landingPage extends AppCompatActivity {

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            DatabaseReference fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();
    }

    private void updateUI(){

        if (fAuth.getCurrentUser() != null){
            Log.i("landingPage", "fAuth != null");
        } else {
            Intent startIntent = new Intent(landingPage.this, MainActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("landingPage", "fAuth == null");
        }

    }
}