package com.example.studyassist.user_sign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studyassist.R;

public class RegisterPage extends AppCompatActivity {

    private Button btnReg;
    private EditText inEmail, inPass, inFName, inLName, inCourse;

    private FirebaseAuth fAuth;
    private DatabaseReference fUsersDatabase;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnReg = (Button) findViewById(R.id.btn_reg);
        inFName = (EditText) findViewById(R.id.input_reg_fname);
        inLName = (EditText) findViewById(R.id.input_reg_lname);
        inCourse = (EditText) findViewById(R.id.input_reg_course);
        inEmail = (EditText) findViewById(R.id.input_reg_email);
        inPass = (EditText) findViewById(R.id.input_reg_pass);

        fAuth = FirebaseAuth.getInstance();
        fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ufname = inFName.getEditText().getText().toString().trim();
                String ulname = inLName.getEditText().getText().toString().trim();
                String ucourse = inCourse.getEditText().getText().toString().trim();
                String uemail = inEmail.getEditText().getText().toString().trim();
                String upass = inPass.getEditText().getText().toString().trim();

                registerUser(ufname, ulname, ucourse, uemail, upass);

            }
        });
    }

    private void registerUser(final String fname, String lname, String course, String email, String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            fUsersDatabase.child(fAuth.getCurrentUser().getUid())
                                    .child("basic").child("fname").setValue(fname)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                progressDialog.dismiss();

                                                Intent mainIntent = new Intent(RegisterPage.this, landingPage.class);
                                                MainActivity(mainIntent);
                                                finish();
                                                Toast.makeText(RegisterPage.this, "User created!", Toast.LENGTH_SHORT).show();

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(RegisterPage.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {

                            progressDialog.dismiss();

                            Toast.makeText(RegisterPage.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }
}