package com.example.studyassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class landingPage extends AppCompatActivity {

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button button = (Button) findViewById(R.id.taskbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTaskPage();
            }
        });

        Button button1 = (Button) findViewById(R.id.upcomingpaperbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openUpcomingPage();
            }
        });

        Button button2 = (Button) findViewById(R.id.timetablebtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimetablePage();
            }
        });

        Button button3 = (Button) findViewById(R.id.myprofilebtn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openProfilePage();
            }
        });




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
    private void openTaskPage(){
        Intent intent = new Intent(this, taskPage.class);
        startActivity(intent);
    }

    private void openUpcomingPage(){
        Intent intent = new Intent(this, upcomingPaperPage.class);
        startActivity(intent);
    }

    private void openTimetablePage(){
        Intent intent = new Intent(this, timetablePage.class);
        startActivity(intent);
    }
    private void openProfilePage(){
        Intent intent = new Intent(this, profilePage.class);
        startActivity(intent);
    }
}



