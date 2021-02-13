package com.example.datehack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText ausername, adob, apassword, apassword2;
    Button aRegisterBtn;
    TextView aloginbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ausername = findViewById(R.id.username);
        adob = findViewById(R.id.edob);
        apassword = findViewById(R.id.epassword);
        apassword2 = findViewById(R.id.epassword2);
        aRegisterBtn = findViewById(R.id.registerBtn);
        aloginbtn = findViewById(R.id.loginBtn);
        fAuth = FirebaseAuth.getInstance();


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        Spinner spinner = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        aRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = ausername.getText().toString().trim();
                String password = apassword.getText().toString().trim();
                String epassword = apassword2.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    ausername.setError("Full Name is Required.");
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    apassword.setError("Password is required.");
                    return;

                }
                if (password.length() < 6) {
                    apassword.setError(("Password Must be >= 6 Characters"));
                    return;
                }
                if (TextUtils.isEmpty(epassword)) {
                    apassword2.setError("Please re-enter password");

                }

                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,"User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), dashboard.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                });

            }


        });
        aloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String gender_spinner = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), gender_spinner, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

