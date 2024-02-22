package com.example.final_project.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.final_project.DatabaseHelper;
import com.example.final_project.NotificationService;
import com.example.final_project.R;
public class MainActivity extends AppCompatActivity {

    private TextView isSignUp, textLabel;
    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private DatabaseHelper databaseHelper;

    private CardView cardView;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);
        Intent testIntent = new Intent("countdown_finished");
        sendBroadcast(testIntent);

        textLabel = findViewById(R.id.textLabel);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        cardView = findViewById(R.id.cardView);
        //Set Confirm password hidden
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextConfirmPassword.setVisibility(View.GONE);
        isSignUp = findViewById(R.id.isSignUp);

        databaseHelper = new DatabaseHelper(this);

        btnSubmit = findViewById(R.id.btnLogin);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                int currentVisibility = editTextConfirmPassword.getVisibility();
                if (currentVisibility == View.VISIBLE) {
                    if(password.equals(confirmPassword)){
                        register(username, password);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password not same!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login(v);
                }
            }
        });
        //Change between login and register
        isSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentVisibility = editTextConfirmPassword.getVisibility();
                if (currentVisibility == View.VISIBLE) {
                    textLabel.setText("Sign In");
                    btnSubmit.setText("Submit");
                    editTextConfirmPassword.setVisibility(View.GONE);
                } else {
                    textLabel.setText("Resgiter");
                    btnSubmit.setText("Send");
                    editTextConfirmPassword.setVisibility(View.VISIBLE);
                }
            }
        });


    }
    private void login(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            if (columnIndex != -1 && usernameIndex != -1) {
                int userId = cursor.getInt(columnIndex);
                String usernameInDB = cursor.getString(usernameIndex);
                editor.putString("userId", Integer.toString(userId));
                editor.putString("username", usernameInDB);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, GetStart.class);
                startActivity(intent);
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                // Handle the case where "id" column doesn't exist
                Toast.makeText(this, "User ID column not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Invalid credentials
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    public void register(String username, String password) {
        Log.d("LoginDebug", username + " Password: " + password);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);

        if (result != -1) {
            // Successfully registered

            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            // Check if data is saved by querying the database
//            checkSavedData();
        } else {
            // Registration failed
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    private void checkSavedData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");

            do {
                // Check if columns exist before retrieving data
                if (idIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    editor.putString("userId", Integer.toString(id));
                    Log.d("SavedData", "ID: " + id);
                }
                if (usernameIndex != -1) {
                    String username = cursor.getString(usernameIndex);
                    editor.putString("username", username);
                }
                if (passwordIndex != -1) {
                    String password = cursor.getString(passwordIndex);
                    Log.d("SavedData", "Password: " + password);
                }
            } while (cursor.moveToNext());
        }
        editor.apply();
        cursor.close();
        db.close();
    }


}