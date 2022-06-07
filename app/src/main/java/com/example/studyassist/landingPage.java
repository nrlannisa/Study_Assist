package com.example.studyassist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class landingPage extends AppCompatActivity {

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();
    }

    private void updateUI(){

        if (fAuth.getCurrentUser() != null){
            Log.i("landingPage", "fAuth != null");
        } else {
            Intent startIntent = new Intent(landingPage.this, MainActivity.class);
            MainActivity(startIntent);
            finish();
            Log.i("landingPage", "fAuth == null");
        }

    }
}