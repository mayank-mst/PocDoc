package com.example.priyanka.mapsdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import android.support.annotation.NonNull;




//import static com.google.android.gms.common.api.Status.si;
//import static com.prep.sih.R.id.passwd;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText email_id1;
    private EditText pass_id;
    private Button login_id;
    private Button newsignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();


       if (firebaseAuth.getCurrentUser() != null) {
            //profile activity can be started
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }


        progressDialog = new ProgressDialog(this);

        login_id = (Button) findViewById(R.id.login_id);
        newsignup = (Button) findViewById(R.id.acc_id);

        email_id1 = (EditText) findViewById(R.id.email_id1);
        pass_id = (EditText) findViewById(R.id.pass_id);

        login_id.setOnClickListener(this);
        newsignup.setOnClickListener(this);


    }

    private void userLogin() {
        String email_id10 = email_id1.getText().toString().trim();
        //String name1=name.getText().toString().trim();
        String pass_id1 = pass_id.getText().toString().trim();


        if (TextUtils.isEmpty(email_id10)) {
            //email is empty
            Toast.makeText(this, "Please enter mail", Toast.LENGTH_SHORT).show();
            //stopping from executing further
            return;
        }
        if (TextUtils.isEmpty(pass_id1)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email_id10, pass_id1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            //start the home activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Main3Activity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        if (v == login_id) {
            userLogin();
        }

        if (v == newsignup) {
            finish();
            startActivity(new Intent(this, Main2Activity.class));
        }
//        if (v == feedback) {
//            finish();
//            startActivity(new Intent(MainActivity.this,UserActivity.class));
//        }
    }


}

