package com.example.priyanka.mapsdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button signup;
    private EditText name;
    private EditText confirm;
    private EditText passwd;
    private EditText email;
    private TextView signuplogin;
    private Spinner floor;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    /*    Spinner spinner = (Spinner) findViewById(R.id.floor_id);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Floor, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
*/

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);



        signup = (Button) findViewById(R.id.signup_id);

        confirm = (EditText)findViewById(R.id.confirm_id);
        name=(EditText)findViewById(R.id.name_id);
        email=(EditText)findViewById(R.id.email_id);
        passwd=(EditText)findViewById(R.id.pass1_id);
        //floor=(Spinner)findViewById(R.id.floor_id);

        signuplogin=(TextView)findViewById(R.id.signuplogin);

        signup.setOnClickListener(this);
        signuplogin.setOnClickListener(this);
    }

    private void registerUser(){
        final String email1=email.getText().toString().trim();
        final String name1=name.getText().toString().trim();
        String passwd_1=passwd.getText().toString().trim();
        String confirm_1 = confirm.getText().toString().trim();

       /* final String fl = (String) floor.getSelectedItem();
        Map<String,String> map1 = new HashMap<>();
        if(fl.equalsIgnoreCase("1"))
        {

            map1.put("ID","704326");
            map1.put("Phone","7299930321");

        }
        else if(fl.equalsIgnoreCase("2"))
        {
            map1.put("ID","714434");
            map1.put("Phone","9384826732");
        }
*/

        if(TextUtils.isEmpty(email1)){
            //email is empty
            Toast.makeText(this,"Please enter mail",Toast.LENGTH_SHORT).show();
            //stopping from executing further
            return;
        }
        if(TextUtils.isEmpty(passwd_1)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirm_1)){
            //password is empty
            Toast.makeText(this,"Please enter password again",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passwd_1.equals(confirm_1))
        {
            Toast.makeText(this,"Password mismatch",Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwd_1==confirm_1){
            if((passwd_1.length()<8)){
                Toast.makeText(this,"Enter a valid password (min 8 Characters)",Toast.LENGTH_SHORT).show();
                return;
            }
        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email1,passwd_1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){


                            finish();

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            //success registration
                            Toast.makeText(Main2Activity.this,"Registered Successfuly",Toast.LENGTH_SHORT).show();
                            Map<String,String> map = new HashMap<>();
                            map.put("Email",email1);
                            map.put("Name",name1);
                            //map.put("Floor",fl);
//                            user User=new user(
//                                    name1,
//                                    email1,desig
//                            );

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "").substring(0, FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf('@')))
                                    .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Main2Activity.this, "success", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(Main2Activity.this, "failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }

                        else{

                            Toast.makeText(Main2Activity.this,"Registered Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //if validations are ok
        //we first show a progress

        // FirebaseDatabase.getInstance().getReference("Floors").child(fl).setValue(map1);



    }


    @Override
    public void onClick(View v) {
        if(v==signup) {
            registerUser();
        }
        if(v==signuplogin){
            startActivity(new Intent(Main2Activity.this,Main3Activity.class));
        }
    }



}
