package com.dev.inder.androideatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.inder.androideatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText editTextphone,editTextpassword,editTextname,editTextemail;
    Button buttonsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        editTextpassword = findViewById(R.id.editpassword);
        editTextphone = findViewById(R.id.editphonenumber);
        editTextname = findViewById(R.id.editname);
        editTextemail = findViewById(R.id.editemail);
        buttonsignup = findViewById(R.id.buttonsignup);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(editTextphone.getText().toString()).exists())
                        {
                            Toast.makeText(SignUp.this, "Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user = new User(editTextname.getText().toString(),editTextpassword.getText().toString(),editTextemail.getText().toString());
                            table_user.child(editTextphone.getText().toString()).setValue(user);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignUp",databaseError.getMessage());

                    }
                });
            }
        });
    }
}
