//Declaration of package
package com.iefp.loginsqlitecrud;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Helpers.DbHelper;

//Import Android libs:
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Import Java libs:
import static java.lang.Integer.parseInt;


//This class inherits AppCompatActivity, making it a activity (extends FragmentActivity and implements other important classes for frontend).
public class InsertActivity extends AppCompatActivity{

    //Attributes.
    DbHelper myDB;
    EditText editTextNumber, editTextName, editTextAge, editTextPhone, editTextEmail;
    String temp_number, temp_age, name, phone, email;
    int number, age;

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Associate the the content of this activity to the desired layout.
        setContentView(R.layout.activity_insert);

        //Instantiate the class, establishing connection with database.
        myDB = new DbHelper(this);

        //Assign fields in the layout to attributes.
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
    }

    //I/O methods.
    //Insert button.
    public void insertStudentButtonClicked(View view){

        try{
            //Get values from form fields and store them to attributes.
            temp_number = editTextNumber.getText().toString();
            number = parseInt(temp_number);
            name = editTextName.getText().toString();
            temp_age = editTextAge.getText().toString();
            age = parseInt(temp_age);
            phone = editTextPhone.getText().toString();
            email = editTextEmail.getText().toString();

            //Check values inserted and show a toast alert if conditions are not fulfilled.
            //Number value condition.
            if (number < 1){
                Toast.makeText(this, R.string.edit_error_1, Toast.LENGTH_LONG).show();
                return;
            }
            //Name length condition.
            if (name.length() < 3){
                Toast.makeText(this, R.string.edit_error_2, Toast.LENGTH_LONG).show();
                return;
            }
            //Age value condition.
            if (age < 6){
                Toast.makeText(this, R.string.edit_error_3, Toast.LENGTH_LONG).show();
                return;
            }
            //Phone length condition.
            if (phone.length() <9 || phone.length() >14){
                Toast.makeText(this, R.string.edit_error_4, Toast.LENGTH_LONG).show();
                return;
            }
            //Email length condition.
            if (email.length() <6){
                Toast.makeText(this, R.string.edit_error_5, Toast.LENGTH_LONG).show();
                return;
            }

            //If conditions above are respected the student is inserted in database.
            if (myDB.insertStudent(number, name, age, phone, email)){
                //Empty the form field to register a new student.
                editTextNumber.setText("");
                editTextName.setText("");
                editTextAge.setText("");
                editTextPhone.setText("");
                editTextEmail.setText("");

                //Message of success.
                Toast.makeText(this, R.string.insert_success, Toast.LENGTH_SHORT).show();
                finish();

            //Messages of failure.
            } else {
                Toast.makeText(this, R.string.insert_failure, Toast.LENGTH_SHORT).show();
            }
        //Check datatype of number and age inserted by user.
        }catch (NumberFormatException e) {
            Toast.makeText(this, R.string.edit_error_6, Toast.LENGTH_LONG).show();
        }
    }

    //Logout button.
    public void returnButtonClicked(View view){
        finish();
    }

}