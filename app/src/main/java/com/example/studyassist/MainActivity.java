package com.example.studyassist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studyassist.user_sign.RegisterPage;
import com.example.studyassist.user_sign.signInPage;

public class MainActivity extends AppCompatActivity {

    private Button btnReg, btnSign;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        MainActivity(regIntent);
    }

    private void login(){
        Intent logIntent = new Intent(MainActivity.this, signInPage.class);
        MainActivity(logIntent);
    }

    private void updateUI(){
        if (fAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(MainActivity.this, landingPage.class);
            MainActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }
}