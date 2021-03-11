//Declaration of package:
package com.iefp.loginsqlitecrud.Helpers;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Models.Student;

//Import Android libs:
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

//Import Java libs:
import java.util.ArrayList;

//Class (Model) to perform operations in database.
public class DbHelper extends SQLiteOpenHelper{

    //Database name.
    public static final String DATABASE_NAME = "loginsqlitecrud.db";
    //Database version. If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;

    //Table users.
    public static final String USERS_TABLE_NAME = "user";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_USERNAME = "username";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_EMAIL = "email";

    //Table students.
    public static final String STUDENTS_TABLE_NAME = "student";
    public static final String STUDENTS_COLUMN_NUMBER = "number";
    public static final String STUDENTS_COLUMN_NAME = "name";
    public static final String STUDENTS_COLUMN_AGE = "age";
    public static final String STUDENTS_COLUMN_PHONE = "phone";
    public static final String STUDENTS_COLUMN_EMAIL = "email";


    //Constructor to initialize database.
    public DbHelper(@Nullable Context context){

        /*
        super(parameters...) below as SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        Create a helper object to create, open, and/or manage a database. The version must be > 0.
        The database is not actually created or opened until one of getWritableDatabase() or getReadableDatabase() is called.
        */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //Allow write access to database. Create the object if not exist. Force access to object onCreate.
        SQLiteDatabase db = this.getWritableDatabase();

    }

    //Actions to be taken onCreate method, when activity is initialized.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        /*
            Create table user - Schema:
        +----+----------+----------+-------+
        | id | username | password | email |
        +----+----------+----------+-------+
        */
        String queryCreateUsers =   "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " (" +
                                    USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    USERS_COLUMN_USERNAME + " TEXT, " +
                                    USERS_COLUMN_PASSWORD + " TEXT, " +
                                    USERS_COLUMN_EMAIL + " TEXT);";

        //Execute Query.
        sqLiteDatabase.execSQL(queryCreateUsers);

        /*
            Create table student - Schema:
        +--------+------+-----+-------+-------+
        | number | name | age | phone | email |
        +--------+------+-----+-------+-------+
        */
        String queryCreateStudents =    "CREATE TABLE IF NOT EXISTS " + STUDENTS_TABLE_NAME + " (" +
                                        STUDENTS_COLUMN_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        STUDENTS_COLUMN_NAME + " TEXT, " +
                                        STUDENTS_COLUMN_AGE + " INTEGER, " +
                                        STUDENTS_COLUMN_PHONE + " TEXT, " +
                                        STUDENTS_COLUMN_EMAIL + " TEXT);";
        //Execute Query.
        sqLiteDatabase.execSQL(queryCreateStudents);

    }

    //Actions to be taken onUpgrade (missing backup feature :( for studies purposes ).
    // This database is only a cache for online data, so its upgrade policy is to simply to discard the data and start over.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int _old, int _new){

        //Query to drop user table.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        //Query to drop student table.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);

        //Start over this activity
        onCreate(sqLiteDatabase);

    }

    //Insert new user in database.
    public boolean insertUser (String username, String password, String email){

        //Instantiate a class that provide temporary storage to values, before save them on database.
        SQLiteDatabase db = this.getWritableDatabase();

        //Append values to contentValues object instantiate above.
        ContentValues contentValues = new ContentValues();

        //Append values to contentValues object instantiate above.
        contentValues.put(USERS_COLUMN_USERNAME, username);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        contentValues.put(USERS_COLUMN_EMAIL, email);

        //Insert the new row, returning the id value of the new row.
        long result = db.insert(USERS_TABLE_NAME, null, contentValues);

        //Insert method return -1 if there was an error inserting the data.
        return result != -1;

    }

    //Validate credentials.
    public int checkUser (String username, String email){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getReadableDatabase();

        /*
        Return the number of rows in the table filtered by the selection.
        (SQLiteDatabase db, String table, String selection, String[] selectionArgs) where ? are the dynamic values.
        */
        long user_entries = DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME, USERS_COLUMN_USERNAME + " = ?;", new String[] {username});
        long email_entries = DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME, USERS_COLUMN_EMAIL + " = ?;", new String[] {email});

        //Return a int to condition alert message when this method is called.
        //User and email already exist on database.
        if(user_entries + email_entries > 1){
            return 1;
        }
            //User already exist on database.
            else if(user_entries > 0){
                return 2;
            }
                //Email already exist on database.
                else if(email_entries > 0){
                    return 3;
                } else{
                    return 0;
                }

    }

    //Validate credentials.
    public boolean checkLogin (String username, String password){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getReadableDatabase();

        // Return the number of rows in the table filtered by the selection.
        long NumEntries = DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME, USERS_COLUMN_USERNAME + " = ? AND " + USERS_COLUMN_PASSWORD + " = ?", new String[] {username, password});

        //No match <=> 0 lines filtered.
        return NumEntries != 0;

    }

    //[C]RUD: Table student create method. Insert a new line of student information on database.
    public boolean insertStudent (int number, String name, int age, String phone, String email){

        //Allow write access to database. Create the object if not exist.
        SQLiteDatabase db = this.getWritableDatabase();

        //Instantiate a class that provide temporary storage to values, before save them on database.
        ContentValues contentValues = new ContentValues();

        //Create operations: Append values to contentValues object instantiate above.
        contentValues.put(STUDENTS_COLUMN_NUMBER, number);
        contentValues.put(STUDENTS_COLUMN_NAME, name);
        contentValues.put(STUDENTS_COLUMN_AGE, age);
        contentValues.put(STUDENTS_COLUMN_PHONE, phone);
        contentValues.put(STUDENTS_COLUMN_EMAIL, email);

        // Return the number of rows inserted on database.
        long result = db.insert(STUDENTS_TABLE_NAME, null, contentValues);

        //Insert method return -1 if there was an error inserting the data.
        return result != -1;

    }

    //Table student read method ( C[R]UD ). Load all students to a arraylist.
    public ArrayList<Student> getAllStudent(){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getReadableDatabase();

        //Instantiate an arraylist to store data.
        ArrayList<Student> studentArrayList = new ArrayList<>();

        //Query to read students list.
        String query = "SELECT * FROM " + STUDENTS_TABLE_NAME;

        /*
        Cursor is an object, which is returned by a query.
        It point to a single row of the result fetched by the query.
        In each cursor (line) there are 5 elements ordered by index.
        It prevents from loading all data into memory.
        Cursor rawQuery (String sql,String[] selectionArgs).
        */
        Cursor cursor = db.rawQuery(query, null);

        /*
        Iterate through columns in students table - Schema:
            +--------+------+-------+-------+-------+
            | number | name |  age  | phone | email |
            +--------+------+-------+-------+-------+
            |   0   ->   1 ->   2  ->   3  ->   4   |
            +--------+----- +-------+-------+-------+
        */

        //While exist lines, get data from cursor positions and store them on arraylist.
        try{
            while (cursor.moveToNext()){

                int number = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String phone = cursor.getString(3);
                String email = cursor.getString(4);

                //Instantiate the student class.
                Student student = new Student(number, name, age, phone, email);

                //Add object above to array list (append operation).
                studentArrayList.add(student);

            }

            //Return arraylist above instantiated.
            return studentArrayList;

        } finally{
            cursor.close();
        }

    }

    //Match the id inserted bt user with database id and get that student information.
    public Student getStudentByNumber(int DatabaseTableItemNumber){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getReadableDatabase();

        //Reset object.
        Student student = null;

        //Select the row that match the input_id with id from database.
        String query = "SELECT * FROM " + STUDENTS_TABLE_NAME +" WHERE " + STUDENTS_COLUMN_NUMBER + " = '" + DatabaseTableItemNumber + "';";

        Cursor cursor = db.rawQuery(query, null);

        try{
            //Insanity check because id is a primary key and autoincrement, but this prevent error if database is broken.
            if (cursor.getCount() == 1){

                //Move the cursor to the first row.
                cursor.moveToFirst();

                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String phone = cursor.getString(3);
                String email = cursor.getString(4);

                //Build a student object with the data got above.
                student = new Student(DatabaseTableItemNumber, name, age, phone, email);
            }

            return student;

        } finally{
            cursor.close();
        }

    }

    //CR[U]D: Table student update method.
    public boolean updateStudent (int DatabaseTableItemNumber, int number, String name, int age, String phone, String email){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getWritableDatabase();

        //Instantiate a contentValue to sore variables.
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENTS_COLUMN_NUMBER, number);
        contentValues.put(STUDENTS_COLUMN_NAME, name);
        contentValues.put(STUDENTS_COLUMN_AGE, age);
        contentValues.put(STUDENTS_COLUMN_PHONE, phone);
        contentValues.put(STUDENTS_COLUMN_EMAIL, email);

        // Return the number of rows in the table filtered by the selection.
        long result = db.update(STUDENTS_TABLE_NAME, contentValues, STUDENTS_COLUMN_NUMBER + " = ?", new String[] {Integer.toString(DatabaseTableItemNumber)});

        //Insert method return -1 if there was an error inserting the data.
        return result != -1;

    }

    //CRU[D]: Table student delete method.
    public Integer deleteStudent (int number){

        //Allow read access to database. Create the object if not exist.
        SQLiteDatabase db = this.getWritableDatabase();

        //Temporary variable.
        int result;

        // Return the number of rows in the table filtered by the selection.
        return result = db.delete(STUDENTS_TABLE_NAME, STUDENTS_COLUMN_NUMBER + " = ?", new String[] {Integer.toString(number)});

    }

}