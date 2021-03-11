//Declaration of package
package com.iefp.loginsqlitecrud;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Helpers.DbHelper;

//Import Android libs:
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

//This class inherits AppCompatActivity, making it a activity (extends FragmentActivity and implements other important classes for frontend).
public class LoginActivity extends AppCompatActivity{

    //Attribute.
    DbHelper appDB;
    EditText editTextUsername, editTextPassword;
    String username, password;

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Associate the the content of this activity to the desired layout.
        setContentView(R.layout.activity_login);

        //Instantiate the class, establishing connecting with database.
        appDB = new DbHelper(this);

        //Logout and minimize if "logout" intention is present on Create
        if (getIntent().getBooleanExtra("logout", false)) {
            this.finishAffinity();
        }

    }

    //I/O methods.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void buttonPressed(View view){

        //Login button.
        if (view.getId() == R.id.do_login){

            //Assign fields in the layout to attributes.
            editTextUsername = findViewById(R.id.username);
            editTextPassword = findViewById(R.id.password);

            //Get values from fields and store them to attributes.
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();

            // Validate credentials.
            if (appDB.checkLogin(username, password)){

                //Success message.
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();

                //Instantiate a intent.
                Intent intent = new Intent(this, MainActivity.class);

                //Start the intent instantiate above.
                startActivity(intent);

            //Failure message.
            } else{

                Toast.makeText(this, R.string.login_failure, Toast.LENGTH_SHORT).show();
            }
        }

        //Register button.
        else if (view.getId() == R.id.goto_register){

            //Instantiate a register of a new user (Register activity).
            Intent intent = new Intent(this, RegisterActivity.class);
            //Start the intent instantiate above.
            startActivity(intent);
        }

        //Minimize button.
        else if (view.getId() == R.id.do_minimize){
            this.finishAffinity();

        }

        //Exit button.
        else if (view.getId() == R.id.do_exit){
            finishAndRemoveTask();
        }

    }

}