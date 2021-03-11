//Declaration of package
package com.iefp.loginsqlitecrud;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Adapters.MyCustomAdapter;
import com.iefp.loginsqlitecrud.Helpers.DbHelper;
import com.iefp.loginsqlitecrud.Models.Student;

//Import Android libs:
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

//Import Java libs:
import java.util.ArrayList;

//This class inherits AppCompatActivity, making it a activity (extends FragmentActivity and implements other important classes for frontend)
public class MainActivity extends AppCompatActivity{

    //Attributes (path imported above)
    DbHelper myDB;
    ListView listViewStudent;
    MyCustomAdapter myAdapter;

    //Object of arraylist to store student data.
    ArrayList<Student> studentArrayList = new ArrayList<>();

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //setContentView(int layoutResID). Associate the the content of this activity to the desired layout.
        setContentView(R.layout.activity_main);

        //Instantiate the class, establishing connection with database.
        myDB = new DbHelper(this);

        //Finds the first descendant in the associated layout with the given ID. In this case callback approach is used.
        //Show the view itself if the ID matches getId().
        listViewStudent = findViewById(R.id.listViewStudent);

        //Call function to show student data
        LoadListView();

        //Method to associate a click on a student in the screen with the activity to edit a new student.
        listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener
            //
            // Callback method to be invoked when an item in this AdapterView has been clicked.
            // Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item.
            //
            // public abstract void onItemClick (AdapterView<?> parent,
            //                View view,
            //                int position,
            //                long id)
            //
            // Parameters
            // AdapterView parent:  The AdapterView where the click happened.
            // View view:           The view within the AdapterView that was clicked (this will be a view provided by the adapter)
            // int position:        The position of the view in the adapter.
            // long id:             The row id of the item that was clicked.

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                //Reveal the intention to go to edit activity after a click in a student.
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                /*
                Instantiate a student with determined id after a click on is position on the screen.
                The id is given by callback function above.
                */
                Student student = (Student) listViewStudent.getItemAtPosition(position);

                //Add extended data (student id) to the intent.
                intent.putExtra("studentSelectedNumber", String.valueOf(student.getNumber()));

                //Follow the intention: go to a activity after click.
                startActivity(intent);
            }
        });

    }

    /*
    This is the state in which the app interacts with the user.
    The app stays in this state until something happens to take focus away from the app.
    */
    @Override
    protected void onResume(){
        super.onResume();

        //Exhibit a message of greetings.
        Toast.makeText(this, R.string.main_greetings, Toast.LENGTH_SHORT).show();

        //Call function to show student data.
        LoadListView();

    }

    //Load view with student data loaded
    public void LoadListView(){

        //Load student data to arraylist
        studentArrayList = myDB.getAllStudent();

        //Instantiate custom view to be presented in frontend
        myAdapter = new MyCustomAdapter(this, studentArrayList);

        //Override view with custom view
        listViewStudent.setAdapter(myAdapter);

        //Exhibit a notification informing user of data change
        myAdapter.notifyDataSetChanged();

    }

    //I/O methods
    //Insert new Student
    public void insertStudentButtonClicked(View view){

        //Reveal the activity to go next after a click
        Intent intent = new Intent(this, InsertActivity.class);

        //Follow the intention: go to a activity after click
        startActivity(intent);
    }

    //Logout button.
    public void returnButtonClicked(View view){

        //Instantiate intent to go to Login
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        //Clear activities on top of activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pass a string
        intent.putExtra("logout", true);

        //Go to login, logout and minimize
        startActivity(intent);
    }

}