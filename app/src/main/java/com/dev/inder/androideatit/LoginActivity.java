package com.dev.inder.androideatit;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.inder.androideatit.model.Common;
import com.dev.inder.androideatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText editTextphone,editTextpassword;
    Button buttonsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        editTextpassword = findViewById(R.id.editpassword);
        editTextphone = findViewById(R.id.editphonenumber);
        buttonsignin = findViewById(R.id.buttonsignin);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(editTextphone.getText().toString()).exists()) {

                            //Get User Info
                            User user = dataSnapshot.child(editTextphone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editTextpassword.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Home.class);
                                Common.currentUser = user;
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "User Does not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Login Activity",databaseError.getMessage());
                    }
                });
            }
        });
    }
}
