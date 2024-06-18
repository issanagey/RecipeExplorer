package com.example.recipeexplorer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeexplorer.database.DatabaseManager;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // go to login page when starting app
        Login(null);

    }

    // Login page
    public void Login(View view){
        setContentView(R.layout.login);

        // input references
        EditText nameInputEditText = findViewById(R.id.nameInput);
        EditText pwInputEditText = findViewById(R.id.pwInput);

        // get button reference
        Button button = (Button) findViewById(R.id.loginButton);

        // set button on click listener
        button.setOnClickListener(new View.OnClickListener() {

            // verify login
            public void onClick(View v) {

                // get name and pw
                String name = nameInputEditText.getText().toString();
                String pw = pwInputEditText.getText().toString();

                // create DatabaseManager object (has all the function we need inside)
                DatabaseManager dbm = new DatabaseManager(getApplicationContext());

                // login successful
                if (dbm.LoginVerification(name,pw)) {
                    setContentView(R.layout.activity_main);
                }
                else{
                    Snackbar.make(v, "Account does not exist or Password mismatched", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Create Account page
    public void CreateAccount(View view) {
        setContentView(R.layout.create_account);

        // input references
        EditText nameInputEditText = findViewById(R.id.nameInput);
        EditText pwInputEditText = findViewById(R.id.pwInput);
        EditText confirmPwInputEditText = findViewById(R.id.confirmPwInput);

        // get button reference
        Button button = (Button) findViewById(R.id.createAccBtn);

        // set button on click listener
        button.setOnClickListener(new View.OnClickListener() {

            // verify account creation
            public void onClick(View v) {

                // get name, pw, confirm pw
                String name = nameInputEditText.getText().toString();
                String pw = pwInputEditText.getText().toString();
                String confirmPw = confirmPwInputEditText.getText().toString();

                // error checking
                if (name.isEmpty() || pw.isEmpty() || confirmPw.isEmpty()) {
                    Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!pw.equals(confirmPw)) {
                    Snackbar.make(v, "Passwords do not match", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (pw.length() < 8) {
                    Snackbar.make(v, "Password must be at least 8 characters", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (pw.contains(" ")) {
                    Snackbar.make(v, "Password cannot contain spaces", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (name.contains(" ")) {
                    Snackbar.make(v, "Name cannot contain spaces", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (name.length() < 3) {
                    Snackbar.make(v, "Name must be at least 3 characters", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // create DatabaseManager object (has all the function we need inside)
                DatabaseManager dbm = new DatabaseManager(getApplicationContext());

                // name check
                if (dbm.CreateAccountVerification(name)) {
                    // create account
                    dbm.addUser(name,"123@gmail.com",pw, "".getBytes());
                    Snackbar.make(v, "Account Created", Snackbar.LENGTH_SHORT).show();

                    // back to login page
                    setContentView(R.layout.login);
                }
                // name already exists
                else{
                    Snackbar.make(v, "Name already taken", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Profile(View view){
        setContentView(R.layout.profile);
        DatabaseManager dbm = new DatabaseManager(getApplicationContext());
        ImageView myImage = findViewById(R.id.user_avatar);
        myImage.setImageBitmap(dbm.GetUserProfilePicture(dbm.GetCurrentUserID()));

    }

}
