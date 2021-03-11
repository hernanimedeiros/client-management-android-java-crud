//Declaration of package:
package com.iefp.loginsqlitecrud;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Helpers.DbHelper;
import com.iefp.loginsqlitecrud.Models.Student;

//Import Android libs:
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Import Java libs:
import static java.lang.Integer.*;

//This class inherits AppCompatActivity, making it a activity (extends FragmentActivity and implements other important classes for frontend).
public class EditActivity extends AppCompatActivity{

    //Attributes.
    DbHelper myDB;
    EditText editTextNumber, editTextName, editTextAge, editTextPhone, editTextEmail;
    int DatabaseTableItemNumber, temp_int_number,temp_int_age, number, age;
    String temp_number, temp_age, name, phone, email;

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Associate the the content of this activity to the desired layout.
        setContentView(R.layout.activity_edit);

        //Assign fields in the layout to attributes.
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        //Instantiate the class, establishing connection with database.
        myDB = new DbHelper(this);

        //Instantiate a intent to receive data.
        Intent intent = getIntent();

        //Parse string (screen info) to integer to make database query.
        DatabaseTableItemNumber = parseInt(intent.getStringExtra("studentSelectedNumber"));

        //Instantiate a object student based on the id of student that was selected on the screen.
        Student student = myDB.getStudentByNumber(DatabaseTableItemNumber);

        //Get values from object and store them to form fields.
        temp_int_number = student.getNumber();
        editTextNumber.setText(String.valueOf(temp_int_number));
        editTextName.setText(student.getName());
        temp_int_age = student.getAge();
        editTextAge.setText(String.valueOf(temp_int_age));
        editTextPhone.setText(student.getPhone());
        editTextEmail.setText(student.getEmail());

    }

    //I/O methods.
    public void buttonPressed(View view){

        //Update button.
        if (view.getId() == R.id.buttonUpdate){
            try{
                //Get values from form fields and store them to attributes.
                temp_number = editTextNumber.getText().toString();
                number = parseInt(temp_number);
                temp_age = editTextAge.getText().toString();
                age = parseInt(temp_age);
                name = editTextName.getText().toString();
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

                //Call method to update database with data collected above.
                boolean isUpdated = myDB.updateStudent(DatabaseTableItemNumber, number, name, age, phone, email);

                //Message of success.
                if (isUpdated)
                    Toast.makeText(this, R.string.edit_success, Toast.LENGTH_SHORT).show();

                //Message of failure.
                else {
                    Toast.makeText(this, R.string.edit_failure, Toast.LENGTH_SHORT).show();
                }
            //Check datatype of number and age inserted by user.
            } catch (NumberFormatException e) {
                Toast.makeText(this, R.string.edit_error_6, Toast.LENGTH_LONG).show();
            }
        }

        //Read button.
        else if (view.getId() == R.id.buttonRead){
            //Show user information.
            String alert_message =  getString(R.string.number) + " -> " + editTextNumber.getText().toString() + "\n" +
                                    getString(R.string.name) + " -> " + editTextName.getText().toString() + "\n" +
                                    getString(R.string.age) + " -> " + editTextAge.getText().toString() + "\n" +
                                    getString(R.string.phone) + " -> " + editTextPhone.getText().toString() + "\n" +
                                    getString(R.string.email) + " -> " + editTextEmail.getText().toString() + "\n\n";

            //Call AlertDialog method to show a message to the user.
            ShowAlertMessage(getString(R.string.edit_resume_title), alert_message );

        }

        //Delete button.
        else if (view.getId() == R.id.buttonDelete){

            //Number of rows deleted.
            Integer DeletedRows = myDB.deleteStudent(DatabaseTableItemNumber);

            //Toast message of success and failure.
            if (DeletedRows > 0)
                Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.delete_failure, Toast.LENGTH_SHORT).show();
        }

        //Return button.
        else if (view.getId() == R.id.buttonReturn){
            finish();
        }
    }

    //Dialog message method (Read button).
    private void ShowAlertMessage(String Title, String Message){

        //Instantiate a AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Alert Dialog mandatory instructions
        builder.setTitle(Title);
        builder.setMessage(Message);
        //Allow user to touch outside the dialog box and cancel the process
        builder.setCancelable(true);
        //Show on screen
        builder.show();
    }

}