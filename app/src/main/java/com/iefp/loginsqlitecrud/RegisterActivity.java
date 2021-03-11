//Declaration of package
package com.iefp.loginsqlitecrud;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Helpers.DbHelper;

//Import Android libs:
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//This class inherits AppCompatActivity, making it a activity (extends FragmentActivity and implements other important classes for frontend).
public class RegisterActivity extends AppCompatActivity{

    //Attribute.
    DbHelper appDB;
    EditText editTextUsername, editTextPassword, editTextEmail;
    String username, password, email;

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Associate the the content of this activity to the desired layout.
        setContentView(R.layout.activity_register);

        //Instantiate the class, establishing connection with database.
        appDB = new DbHelper(this);

    }

    @Override
    protected void onResume(){
        super.onResume();

        String rules_message =  (getString(R.string.register_error_4) + ";\n" +
                                getString(R.string.register_error_5) + ";\n" +
                                getString(R.string.register_error_6) + ".");
        ShowAlertRules(getString(R.string.register_rules_title), rules_message );

    }

    //I/O methods.
    public void buttonPressed(View view){

        //Register button.
        if (view.getId() == R.id.do_register){

            //Assign fields in the layout to attributes.
            editTextUsername = findViewById(R.id.username);
            editTextPassword = findViewById(R.id.password);
            editTextEmail = findViewById(R.id.email);

            //Get values from form fields and store them to attributes.
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
            email = editTextEmail.getText().toString();

            //Check values inserted and show a toast alert if rules are not fulfilled.

            //Check if the user or email inserted for register are already in the database.
            if (appDB.checkUser(username, email) == 1){
                Toast.makeText(this, R.string.register_error_1, Toast.LENGTH_LONG).show();
                return;
            }

            //Check if the user inserted for register are already in the database.
            if (appDB.checkUser(username, email) == 2){
                Toast.makeText(this, R.string.register_error_2, Toast.LENGTH_LONG).show();
                return;
            }

            //Check if the email inserted for register are already in the database.
            if (appDB.checkUser(username, email) == 3){
                Toast.makeText(this, R.string.register_error_3, Toast.LENGTH_LONG).show();
                return;
            }

            //Username length condition.
            if (username.length() < 4) {
                Toast.makeText(this, R.string.register_error_4, Toast.LENGTH_LONG).show();
                return;
            }

            //Password length condition.
            if (password.length() <9) {
                Toast.makeText(this, R.string.register_error_5, Toast.LENGTH_LONG).show();
                return;
            }

            //Email length condition.
            if (email.length() <6) {
                Toast.makeText(this, R.string.register_error_6, Toast.LENGTH_LONG).show();
                return;
            }

            //Invoke insert user method to store new user on database.
            if (appDB.insertUser(username, password, email)){
                //Success message.
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
                finish();
            }
            //Failure  message.
            else{
                Toast.makeText(this, R.string.register_failure, Toast.LENGTH_SHORT).show();
            }
        }
        //Cancel button.
        else if (view.getId() == R.id.do_cancel){
            finish();
        }
    }

    //Read button (Dialog message method).
    private void ShowAlertRules(String Title, String Message){

        //Call a new AlertDialog.Builder object.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Alert Dialog mandatory instructions.
        builder.setTitle(Title);
        builder.setMessage(Message);
        //Allow user to touch outside the dialog box and cancel the process.
        builder.setCancelable(true);
        //Show on screen.
        builder.show();
    }
}