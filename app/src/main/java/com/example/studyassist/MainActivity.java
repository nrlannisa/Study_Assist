package com.example.studyassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.studyassist.user_sign.RegisterPage;
import com.example.studyassist.user_sign.signInPage;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnReg, btnSign;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        btnReg = (Button) findViewById(R.id.main_reg_btn);
        btnSign = (Button) findViewById(R.id.main_sign_btn);

        fAuth = FirebaseAuth.getInstance();

        updateUI();

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register(){
        Intent regIntent = new Intent(MainActivity.this, RegisterPage.class);
        startActivity(regIntent);
    }

    private void login(){
        Intent logIntent = new Intent(MainActivity.this, signInPage.class);
        startActivity(logIntent);
    }

    private void updateUI(){
        if (fAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
            Intent startIntent = new Intent(MainActivity.this, landingPage.class);
            startActivity(startIntent);
            finish();
        } else {

            Log.i("MainActivity", "fAuth == null");
        }
    }
}