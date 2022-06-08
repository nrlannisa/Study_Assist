package com.example.studyassist.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyassist.R;
import com.example.studyassist.landingPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterPage extends AppCompatActivity {

    private EditText inEmail, inPass, inFName, inLName, inCourse;

    private FirebaseAuth fAuth;
    private DatabaseReference fUsersDatabase;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Button btnReg = (Button) findViewById(R.id.btn_reg);
        inFName = (EditText) findViewById(R.id.input_reg_fname);
        inLName = (EditText) findViewById(R.id.input_reg_lname);
        inCourse = (EditText) findViewById(R.id.input_reg_course);
        inEmail = (EditText) findViewById(R.id.input_reg_email);
        inPass = (EditText) findViewById(R.id.input_reg_pass);

        fAuth = FirebaseAuth.getInstance();
        fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnReg.setOnClickListener(view -> {
            String ufname = inFName.getText().toString().trim();
            String ulname = inLName.getText().toString().trim();
            String ucourse = inCourse.getText().toString().trim();
            String uemail = inEmail.getText().toString().trim();
            String upass = inPass.getText().toString().trim();

            registerUser(ufname, ulname, ucourse, uemail, upass);

        });
    }

    private void registerUser(final String fname, String lname, String course, String email, String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        fUsersDatabase.child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())
                                .child("basic").child("fname").setValue(fname)
                                .addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()){

                                        progressDialog.dismiss();

                                        Intent mainIntent = new Intent(RegisterPage.this, landingPage.class);
                                        startActivity(mainIntent);
                                        finish();
                                        Toast.makeText(RegisterPage.this, "User created!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterPage.this, "ERROR : " + Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                });

                    } else {

                        progressDialog.dismiss();

                        Toast.makeText(RegisterPage.this, "ERROR: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });

    }
}