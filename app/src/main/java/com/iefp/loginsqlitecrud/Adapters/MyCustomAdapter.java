//Declaration of package:
package com.iefp.loginsqlitecrud.Adapters;

//Import the path of: Adapters, Helpers, Models and Resources:
import com.iefp.loginsqlitecrud.Models.Student;
import com.iefp.loginsqlitecrud.R;

//Import Android libs:
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//Import Java libs:
import java.util.ArrayList;

//Adapter that will be inflated to some component in the layout.
public class MyCustomAdapter extends BaseAdapter{

    //Attributes.
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Student> studentArrayList;
    TextView textViewNumber, textViewName, textViewPhone;

    //Constructor.
    public MyCustomAdapter(Context context, ArrayList<Student> studentArrayList){

        this.context = context;
        this.studentArrayList = studentArrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //Mandatory methods for layout inflater.
    @Override
    public int getCount()
    {
        return this.studentArrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return studentArrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){

        //Instantiate a student object of the arraylist based on is position on the screen.
        Student student = studentArrayList.get(position);

        //If view is null.
        if (view == null){
            view = this.layoutInflater.inflate(R.layout.studentlistview, viewGroup, false);
        }

        //Assign fields in the layout to attributes.
        textViewNumber = view.findViewById(R.id.textViewNumber);
        textViewName = view.findViewById(R.id.textViewName);
        textViewPhone = view.findViewById(R.id.textViewPhone);

        //Get values from object and store them to attributes.
        textViewNumber.setText(String.valueOf(student.getNumber()));
        textViewName.setText(student.getName());
        textViewPhone.setText(student.getPhone());

        //View to show to the user.
        return view;

    }

}
