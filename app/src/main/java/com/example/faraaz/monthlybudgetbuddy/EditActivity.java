package com.example.faraaz.monthlybudgetbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    private boolean clicked;
    private EditText type,amount,description,datePicker;
    private Button edit,delete;

    DatabaseReference databaseIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        clicked = false;
        String s="income";
        databaseIncome = FirebaseDatabase.getInstance().getReference(s);

        type = findViewById(R.id.type);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        datePicker = findViewById(R.id.datePicker);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        type.setEnabled(false);
        amount.setEnabled(false);
        description.setEnabled(false);
        datePicker.setEnabled(false);

        Intent intent = getIntent();

        final String type1 = intent.getStringExtra(IncomeActivity.TYPE);
        final String id = intent.getStringExtra(IncomeActivity.ID);
        final String amt = (intent.getStringExtra(IncomeActivity.AMOUNT));
        final String desc = intent.getStringExtra(IncomeActivity.DESCRIPTION);
        final String date = intent.getStringExtra(IncomeActivity.DATE);

        Log.d("AMOUNT","after adding skills"+amt);
        type.setText(type1);
        amount.setText(""+amt);
        description.setText(desc);
        datePicker.setText(date);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked) {

                    type.setEnabled(false);
                    amount.setEnabled(false);
                    description.setEnabled(false);
                    datePicker.setEnabled(false);

                    edit.setText(getResources().getText(R.string.edit));
                    type.setEnabled(true);
                    amount.setEnabled(true);
                    description.setEnabled(true);
                    datePicker.setEnabled(true);

                    edit.setText(getResources().getText(R.string.save));
                    clicked=true;
                }
                else {


                    edit.setText(getResources().getText(R.string.edit));
                    type.setEnabled(true);
                    amount.setEnabled(true);
                    description.setEnabled(true);
                    datePicker.setEnabled(true);

                    edit.setText(getResources().getText(R.string.save));

                    String type2 =type.getText().toString();
                    String desc = description.getText().toString();
                    double amt = Double.parseDouble(amount.getText().toString());
                    //String amt =amount.getText().toString();
                    String date = datePicker.getText().toString();

                    updateDB(id,type2,desc,amt,date);

                    type.setEnabled(false);
                    amount.setEnabled(false);
                    description.setEnabled(false);
                    datePicker.setEnabled(false);

                    edit.setText(getResources().getText(R.string.edit));
                    clicked=false;



                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDB(id);
            }
        });


    }

    private void updateDB(String id, String type, String description, double amount, String date){

        DatabaseReference databaseIncome = FirebaseDatabase.getInstance().getReference("income").child(id);
        Income income = new Income(id,type,description,date,amount);
        databaseIncome.setValue(income);
        Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show();

    }


    private void deleteDB (String id){
        DatabaseReference databaseIncome = FirebaseDatabase.getInstance().getReference("income").child(id);

        databaseIncome.removeValue();
        Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
    }
}
