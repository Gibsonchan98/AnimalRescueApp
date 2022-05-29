package com.animalrescueapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class UserRegisterActivity extends AppCompatActivity {

    private static final String TAG = "Registerctivity";

    TextView alreadyHaveAccount;
    EditText firstName, lastName, inputEmail, inputPassword, passwordConfirm, zipcode;
    Button registerBtn;
    //validation pattern
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Initialize variables - assign value to declared variable - connect to xml variables
        alreadyHaveAccount=findViewById(R.id.alreadyAccountView);
//        firstName.findViewById(R.id.firstNameInput);
   //     lastName.findViewById(R.id.lastNameInput);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        passwordConfirm=findViewById(R.id.confirmPassword);
        //zipcode=findViewById(R.id.zipcodeInput);
        registerBtn=findViewById(R.id.registerButton);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //User clicks on Already Have an Account optiion
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(UserRegisterActivity.this,UserLoginActivtiy.class));

                //Used for testing purposes
                startActivity(new Intent(UserRegisterActivity.this,MainActivity.class));
            }
        });

        //User clicks on button to register account
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //Calls function AuthonAcct
            public void onClick(View v) {
                AuthonAcct();
            }
        });

    }

    //Function that checks user input and authonticates using firebase
    private void AuthonAcct(){
        //Set values from input to string
        String email=inputEmail.toString();
        String password=inputPassword.toString();
        String passwordCon=passwordConfirm.toString();
        //String zipc=zipcode.toString();


        //Check if does not email matches pattern
       if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           inputEmail.setError("Enter Correct Email");
       }
       else if(password.isEmpty() || password.length()<6){ //check if password does not meet requirements
            inputPassword.setError("Invalid Password: Password must contain");
        }
        else if(!password.matches(passwordCon)){ //check if passwords do not match
            passwordConfirm.setError("Passwords do not match");
        }
        else{ //if authon works then show this
            progressDialog.setMessage("Please Wait For Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToMainMenu();
                        Toast.makeText(UserRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(UserRegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    //User is sent to Main Activity
    private void sendUserToMainMenu(){
        Intent intent =new Intent(UserRegisterActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}