package com.example.faraaz.monthlybudgetbuddy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// REFERENCE: CRUD operations : https://www.simplifiedcoding.net/firebase-realtime-database-crud/

public class IncomeActivity extends AppCompatActivity {

    public static final String TYPE="type";
    public static final String ID="id";
    public static final String AMOUNT="amount";
    public static final String DATE="date";
    public static final String DESCRIPTION="description";
    double total=0,today=0;

   /* DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();*/

    DatePickerDialog datePickerDialog;
    private EditText type,amount,description,datePicker;
    //private DatePicker datePicker;
    private Button btnAdd;
    private ListView listViewIncome;
    private String date,id;
    String inputDate,formattedDate,day1;


    DatabaseReference databaseIncome;
    List <Income> incomeList;

    // Calling Calender class to get current date/month/year from calendar
    final Calendar calendar = Calendar.getInstance();
    //To store the year
    int year = calendar.get(Calendar.YEAR);
    //To store the month
    int month = calendar.get(Calendar.MONTH);
    //To store the day
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        //Code
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);

        SimpleDateFormat dff = new SimpleDateFormat("d/M/yyyy");
         formattedDate = dff.format(c);
        Log.d("Current date is", "onCreate: -------------------------------------------"+formattedDate);
        SimpleDateFormat day2 = new SimpleDateFormat("M");
        day1 = day2.format(c);
        Log.d("Current date is", "onCreate: -------------------------------------------"+day1);


        String s="income";


        databaseIncome = FirebaseDatabase.getInstance().getReference(s);


        type = findViewById(R.id.type);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        datePicker = findViewById(R.id.datePicker);
        btnAdd= findViewById(R.id.btnAdd);
        Intent intent = getIntent();
        listViewIncome = findViewById(R.id.listViewIncome);

        incomeList = new ArrayList<>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIncome();
            }
        });

        listViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Income income = incomeList.get(i);

                Intent intent2 = new Intent(getApplicationContext(),EditActivity.class);

                String amt= ""+income.getAmt();
                intent2.putExtra(TYPE,income.getType1());
                intent2.putExtra(AMOUNT,amt);
                intent2.putExtra(ID,income.getId());
                intent2.putExtra(DATE,income.getDate());
                intent2.putExtra(DESCRIPTION,income.getDesc());

                startActivity(intent2);

            }
        });

        //REFERENCE : https://googleweblight.com/i?u=http%3A%2F%2Fabhiandroid.com%2Fui%2Fdatepicker&hl=en-IN
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog=new DatePickerDialog(IncomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        inputDate =i2+"/"+(i1+1)+"/"+i;
                        fetchDate();
                    }
                },year, month, day);

                datePickerDialog.show();
            }

        });



    //onCreate ends here
    }

    public void fetchDate(){

        datePicker.setText(inputDate);
    }

    public void addIncome(){
        String type1 = type.getText().toString().trim();
        double amt = Double.parseDouble(amount.getText().toString());

        String desc = description.getText().toString();
        date = datePicker.getText().toString();


        if(!TextUtils.isEmpty(type1)||!TextUtils.isEmpty(desc)||!TextUtils.isEmpty(date))
        {
          id = databaseIncome.push().getKey();
          Income income =new Income(id,type1,desc,date,amt);
          databaseIncome.child(id).setValue(income);

          Toast.makeText(this,"Bill added successfully",Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(this, "Enter all the values !!",Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseIncome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                incomeList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Income income1 = dataSnapshot1.getValue(Income.class);

                    //Query query =databaseIncome.orderByChild("date");
                    incomeList.add(income1);
                }

                for (int i=0; i<incomeList.size();i++){
                    Income temp = incomeList.get(i);
                    Income dateInput= incomeList.get(i);
                    total+= temp.getAmt();
                    String dateInput2=dateInput.getDate();
                    String[] dateParts = dateInput2.split("/");
                    String day3 = dateParts[0];
                    String month3 = dateParts[1];
                    String year3 = dateParts[2];

                    Log.d(" ABC", "onDataChange: -----------------------------------------------"+dateInput2 );
                    if (day1.equals(month3)){

                        today+=dateInput.getAmt();
                    }

                }
                Log.d(" ABC", "onDataChange: -----------------------------------------------"+total );
                Log.d(" ABC", "onDataChange: -----------------------------------------------"+today );


                IncomeList adapter = new IncomeList(IncomeActivity.this,incomeList);
                listViewIncome.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
